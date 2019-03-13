package com.cn.wlh.boot.complier.doman;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cn.wlh.boot.complier.doman.XmlEntity.InnerXml;

import cn.wlh.util.base._File;
import cn.wlh.util.base._Path;
import cn.wlh.util.base._File.HandlerFile;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class BuilderModle1 extends BuilderModle {

	protected String htmlRoot = "html";

	@Override
	public void init() {
		/**
		 * 没变..
		 */
		setH( new HandlerFile() {
			public void handler(File file) throws Throwable {
				if (!file.getName().endsWith(suffix))
					return;
				SAXReader read = new SAXReader();
				Document document = read.read(file);
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
							// );//父模板的路径
							String package2SourcePath = _Path.className2AbsolutePath(innerXml.superPath);
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
						String absolutePath2ClassName = _Path.absolutePath2ClassName(file) + '.' + name;
						System.out.println("absolutePath2ClassName======" + absolutePath2ClassName);
						// 生成的模板文件.
						File tagFile = new File(
								tager + File.separator + _Path.toSeparator(absolutePath2ClassName) + suffix);
						System.out.println("tagFile======" + tagFile);
						_File.createFile(tagFile);
						// 开始生成模板.
						template.process(innerXml.inner, new FileWriter(tagFile));
					}
				}
			}
			
		});
		
		/*
		 * 接下来,我们开始解析生成的xmls
		 */
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
				if (!htmlRoot.equalsIgnoreCase(rootElement.getName()))
					return;
				List<Element> elements = rootElement.elements();
				// TODO 没有具体的框架无法进行封装.       先用boot写一个cms框架.
				for (Element element : elements) {
					
				}
			}
		});
	}
	
}
