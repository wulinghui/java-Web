package com.cn.wlh.boot.complier.doman;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.wlh.boot.complier.doman.XmlEntity.InnerXml;

import cn.wlh.util.base._File;
import cn.wlh.util.base._File.HandlerFile;
import cn.wlh.util.base._Path;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author wlh 递归获得父级,生成标签模板.(Freemark框架) 这个属于Maven的模板。
 */
public class BuilderModle {
	/** 模板来源方式:和class同级,类似mybatis */
	public static byte CLASS_TONGJI = 0;
	/** 循环的根文件夹 */
	File forFileRoot;
	/** src目录,代表项目的路径. */
	String src;
	/** 生成模板文件夹的路径. */
	String tager;
	private Log log = LogFactory.getLog(this.getClass());

	public void log(Object e) {
		System.out.println(
				"=================================================================================================================================");
		log.info(String.valueOf(e));
	}

	public BuilderModle() {
		init();
	}

	String root = "one";
	String globalVar = "global";
	String suffix = ".xml";
	HandlerFile h;

	/** 解析所有最终的xml */
	HandlerFile<Throwable> h2;

	public void init() {
		setH(new HandlerFile() {
			public void handler(File file) throws Throwable {
				if (!file.getName().endsWith(suffix))
					return;
				SAXReader read = new SAXReader();
				Document document = read.read(file);
				// Map<String, Object> forInvoker = _Class.forInvoker(document,
				// MethodFilter.GET);
				// for (Entry<String, Object> element : forInvoker.entrySet()) {
				// System.out.println(element.getKey() + "\t" + element.getValue() );
				// }
				XmlEntity xml = new XmlEntity();
				Element rootElement = document.getRootElement();
				// 如果不为one就代表是顶级模板.
				if (!root.equals(rootElement.getName()))
					return;
				System.out.println("rootElement===" + rootElement);
				List<Element> elements = rootElement.elements();
				// 这是第二层
				for (Element element : elements) {
					// 默认的
					if ("global".equals(element.getName())) {
						List<Element> subElements = element.elements();
						for (Element element1 : subElements) {
							xml.setSuper(element1.getName(), element1.getTextTrim());
						}
						;
						System.out.println("xml.global===" + xml.global);
					} else {
						// 方法名,加里面的内容.
						String name = element.getName();
						InnerXml innerXml = xml.new InnerXml(name);
						// 父类模板路径
						Element superPath = (Element) element.elements().get(0);
						innerXml.setSuperPath(superPath.getName());
						// 父类里面的各个key - value
						List<Element> sub = superPath.elements();
						for (Element element2 : sub) {
							Element sql = element2.element("sql");
							Element check = element2.element("check");
							String inner = element2.asXML();// 获得整个元素的表示方法.
							String _inner = getXmlInnerSource(inner); // 获得里面的所有标签
							// 如果没有就从父类里面获得。
							String _sql = sql == null ? xml.getSuper("sql") : sql.getTextTrim();
							String _check = check == null ? xml.getSuper("check") : check.getText();
							innerXml.set(element2.getName(), _inner, _sql, _check);
						}
						System.out.println("innerXml===" + innerXml);
						/*
						 * 接下来就开始.生成一个完整的模板了.我们这里规定父类必须在上一级包中定义。这样我们在先序遍历的时候就会先遍历父类。
						 * 这样我们就可以直接判断父类是否有值了。不需要在子类里面递归判断了
						 */
						Configuration config = new Configuration();
						config.setDefaultEncoding("UTF-8");
						Template template = null;
						File supFile = new File(
								tager + File.separator + _File.toSeparator(innerXml.superPath) + suffix);
						System.out.println("supFile0000==" + supFile);
						if (!supFile.exists()) { // 如果父类模板不存在就说明 ，他是定级模板
							// String package2SourcePath = _File.package2SourcePath(src , innerXml.superPath
							// );//父模板的路径
							String package2SourcePath = _Path.className2AbsolutePath(innerXml.superPath);
							// supFile = new File(tager + File.separator + package2SourcePath );
							supFile = new File(package2SourcePath + suffix);
							System.out.println("package2SourcePath======" + package2SourcePath);
							config.setDirectoryForTemplateLoading(new File(_Path.getSrc_absolute()));
							template = config.getTemplate(_Path.toSeparator(innerXml.superPath) + suffix);
						} else {
							config.setDirectoryForTemplateLoading(new File(tager));
							template = config.getTemplate(_File.toSeparator(innerXml.superPath) + suffix);
						}
						System.out.println("supFile111==" + supFile);
						// 当前的类名 + 方法名 => 唯一标识
						// String absolutePath2ClassName =
						// _File.absolutePath2ClassName(file.getAbsolutePath()) + name;
						String absolutePath2ClassName = _Path.absolutePath2ClassName(file) + '.' + name;
						System.out.println("absolutePath2ClassName======" + absolutePath2ClassName);
						// 生成的模板文件.
						File tagFile = new File(
								tager + File.separator + _Path.toSeparator(absolutePath2ClassName) + suffix);
						System.out.println("tagFile======" + tagFile);
						_File.createFile(tagFile);
						// 开始生成模板.
						// Configuration config = new Configuration();

						// ServletContext servletContext = null;
						// WebappTemplateLoader templateLoader = new
						// WebappTemplateLoader(servletContext,"WEB-INF / templates");
						// templateLoader.setURLConnectionUsesCaches(false);
						// templateLoader.setAttemptFileAccess(false);
						// config.setTemplateLoader(templateLoader);
						// config.setDefaultEncoding("UTF-8");
						// config.setDirectoryForTemplateLoading(_Path.WOKE_FILE);
						//
						// Template template = config.getTemplate(supFile.getAbsolutePath());
						template.process(innerXml.inner, new FileWriter(tagFile));
					}
				}

				// int len = rootElement.nodeCount();
				// for (int i = 0; i < len; i++) {
				// Node node = rootElement.node(1);
				//
				// if( "global".equals(node.getName()) ) {
				//// List<Node> selectNodes = node.selectNodes("//*");
				//// for (Node object : selectNodes) {
				//// System.out.println(_String.lineBetween("===================", "global"));
				//// System.out.println(object.getName() + "||" + node.getText());
				//// }
				// System.out.println("node.detach().getName()=="+node.detach().getName());
				// Document document2 = node.getDocument();
				//// System.out.println("document2.getRootElement()=="+document2.getName());;
				// }
				// List<Element> elements = rootElement.elements();
				// System.out.println( "elements =======" + elements);
				// for (Element element : elements ) {
				// if( "global".equals(element.getName()) ) {
				// System.out.println("element=="+element.getName());
				// }
				// }
				// }
				// List list0 = document.selectNodes("//*");//不用导航.
				// System.out.println("======list0=======" + list0.size());
				// for (int i = 0; i < list0.size(); i++) {
				// Node node = (Node)list0.get(i);
				// Document document2 = node.getDocument();
				// System.out.println(
				// _String.join(" ||\n", node.getName() , node.getText() , document2
				// ,node.getNodeType() , node.getNodeTypeName() , node.getParent()
				// ,node.getPath() , node.getStringValue() , node.getUniquePath())
				// );
				// System.out.println("=============node.getDocument()===============" );
				// System.out.println( _String.join(" ||\n" , document2.getName() ,
				// document2.get ));
				// }

			}

		});

		setH2(new HandlerFile<Throwable>() {
			public void handler(File file) throws Throwable {
				if (!file.isFile())
					return;
				SAXReader read = new SAXReader();
				Document document = read.read(file);
				/**
				 * 我们这里的处理方式是采用替换的方式. 逐级替换,逐个解析
				 */
				Element rootElement = document.getRootElement();
				List<Element> elements = rootElement.elements();
				for (Element element : elements) {
					
				}
			}
		});
	}

	/**
	 * 获得xml标签内部的原始数据.
	 * 
	 * @param inner 整个xml标签
	 * @return
	 */
	public static String getXmlInnerSource(String inner) {
		return inner.substring(inner.indexOf('>') + 1, inner.lastIndexOf('<'));
	}
	
	/** 入口
	 * @throws Throwable
	 */
	public void complier() throws Throwable {
		// String root = String.class.getResource("/").getFile();
		// _File.forFile(new File(root), new cn.wlh.util.base._File.Filter() {
		System.out.println("======complier=======");
		// 生成模板.
		_File.forFile(forFileRoot, /*
									 * new cn.wlh.util.base._File.Filter() {
									 * 
									 * @Override public boolean accept(File pathname) {
									 * System.out.println("pathname=="+pathname); 这里他把文件夹都直接过滤了. return
									 * pathname.getName().endsWith(".xml") || pathname.isDirectory(); } },
									 */ h);

		// 模板的内容开始转化..
		_File.forFile(new File(tager), h2);
	}

	public File getForFileRoot() {
		return forFileRoot;
	}

	public void setForFileRoot(File forFileRoot) {
		this.forFileRoot = forFileRoot;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public static byte getClassTongji() {
		return CLASS_TONGJI;
	}

	public static void setClassTongji(byte classTongji) {
		CLASS_TONGJI = classTongji;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getGlobalVar() {
		return globalVar;
	}

	public void setGlobalVar(String globalVar) {
		this.globalVar = globalVar;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public HandlerFile getH() {
		return h;
	}

	public void setH(HandlerFile h) {
		this.h = h;
	}

	public HandlerFile<Throwable> getH2() {
		return h2;
	}

	public void setH2(HandlerFile<Throwable> h2) {
		this.h2 = h2;
	}

	public String getTager() {
		return tager;
	}

	public void setTager(String tager) {
		this.tager = tager;
	}

}
