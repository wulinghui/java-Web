package com.cn.wlh.boot.complier.doman;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.catalina.startup.Bootstrap;
import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.wlh.boot.complier.Application;

import cn.wlh.util.base._File;
import cn.wlh.util.base._File.HandlerFile;

/**
 * @author 吴灵辉
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
//@SpringApplicationConfiguration(Bootstrap.class)
@SpringBootTest(classes = Bootstrap.class)
//@ContextConfiguration(locations = "file:src/main/java/com/cn/wlh/boot/complier/doman/BuilderModle.xml")
public class ApplicationTests {
	
	
	
	
	private  final Log log = LogFactory.getLog(this.getClass());
	public void log(Object e) {
		System.out.println("=================================================================================================================================");
		log.info(String.valueOf(e));
	}  
	String file = Application.class.getResource("/").getFile();
	@Test //在测试里面classes里面的xml文件。
	public void test0() throws Throwable {
		log("随机数测试输出：");
		log(String.class.getResource("/").getFile());
		_File.forFile(new File(file), new HandlerFile() {
			@Override
			public void handler(File file) throws Throwable {
				log(file);
			}
		});
		/**    
		 * 在测试里面无法获得,classes里面的xml文件。或许在是和部署不同的一点。
		 */
	}
	@Test //查看测试运行目录下的所有文件
	public void test1() throws Throwable {
		_File.forFile(new File(file), new HandlerFile() {
			@Override
			public void handler(File file) throws Throwable {
				if(file.getName().equals("Dom4jTest.xml")) {
//				if(file.isFile()) {
//				if(file.getName().endsWith(".xml")) {
					SAXReader read = new SAXReader();
					log("file="+file);
					Document document = read.read(file);
//					Document document = read.read("target/test-classes/zzz/Dom4jTest.xml");
//					Node selectSingleNode = document.selectSingleNode("/sql");
					log( document.getName() + "||" + document.getParent() + "||" + document.getPath());
					log( document.getText() );
//					List nodes = document.selectNodes("/bookstore//book/title");
//					for (int i = 0; i < nodes.size(); i++) {
//						Node node = (Node) nodes.get(i);
//						System.out.println(node.getText());
//					}
					List list0 = document.selectNodes("//*");
					for (int i = 0; i < list0.size(); i++) {
						Node node = (Node)list0.get(i);
						System.out.println(node.getName()+"\t"+node.getText());
					}
					
					Element root = document.getRootElement();
				    /* Element bookNode = root.element("书");
				     System.out.println(bookNode.getName());*/
				     List list = root.elements();//得到当前节点的所有子节点
				     Element secondBook = (Element) list.get(1);//得到第二本书对象
				     String name = secondBook.element("title").getText();//得到当前节点的文本内容
				     System.out.println(name);
				}
			}
		});
	}
	/**
	 * 关于 在测试里面无法获得,classes里面的xml文件。或许在是和部署不同的一点。  的问题
	 * 看他的运行机制就知道,在 target\test-classes里面不会把src里面的东西给放进来。  
	 * 这就说明打包和test的文件类型和文件路径不对应。这里的测试是单独运行的，只是简单的引用了src里面class
	 * 他要想找到src里面的配置文件只能在test-classes里面引用 , 或者在src的class引用的配置文件里面进行关联,否则将无法引用。
	 * Boot给出了几个办法:
	 * 		<li>@SpringBootTest(classes = Bootstrap.class)</li>
	 * 		<li>@ContextConfiguration(locations = "file:src/main/java/com/cn/wlh/boot/complier/doman/BuilderModle.xml") </li>
	 * 		<li>在src/test/resources/application-test.properies配置文件中的有关.xml文件的都写成file:///${user.dir}/src/main/java(resources) 百度spring+mybatis(xml模式)单元测试报错。
	 *           理解为boot框架在junit的时候会先test里面的配置。根据加载application-test.properies为主。
	 *           这里定义了mybatis获得xml的方式。file:///${user.dir} -- 绝对路径 ${user.dir}项目跟路径。
	 *      </li>
	 *      <li>我们就采用原始的通过相对路径来获得file==new File("src/main/java/com/cn/wlh/boot/complier/doman/BuilderModle.xml")</li>
	 * @throws Throwable
	 */
	@Test //在测试中获得main中的文件
	public void test2() throws Throwable {
//		File f = new File("file:src/main/java/com/cn/wlh/boot/complier/doman/BuilderModle.xml");
		File f = new File("src/main/java/com/cn/wlh/boot/complier/doman/BuilderModle.xml");
		System.out.println( "File f =="+ f.isFile() + "||"+ f.exists() );
	}
	@Test //获得完整的xml
	public void test3() throws Throwable {
		BuilderModle modle = new BuilderModle();
		File f = new File("src/main/java/com");
		modle.setForFileRoot(f);
		modle.setSrc("src/main/java");
		modle.setTager("src/main/java/temp");
		modle.complier();
	}
	@Test //编译生成新的html
	public void test4() throws Throwable {
		_File.forFile(new File(file), new HandlerFile() {
			@Override
			public void handler(File file) throws Throwable {
				if(file.getName().equals("Dom4jTest.xml")) {
					log ( "---getAbsoluteFile------"+ file.getAbsoluteFile() );
					File dest = new File("F:/html.txt");	// zzz/Dom4jTest.xml
					/**我们这里的处理方式是采用替换的方式.
					 * 逐级替换,逐个解析*/
					dest.delete();
					dest.createNewFile();
//					FileUtils.copyFile(file, dest);
					SAXReader read = new SAXReader();
					Document document = read.read(dest);
					Element rootElement = document.getRootElement();
					rootElement.setName("我不是html");
					log ( "---getName------"+ rootElement.getName() );
					List<Element> elements = rootElement.elements();
					/**
					 * TODO 
					 * 这里报错..需要测试Dom4j的增删改查.
					 */
//					FileOutputStream os = new FileOutputStream(dest);
//					XMLWriter xmlWriter = new XMLWriter(os);
//					xmlWriter.write(document);
//					xmlWriter.close();
					
					
				}
			}
		});
	} 
	//测试Dom4j的增删改查.
	@Test
	public void test5() throws Throwable {
		File file2 = new File( STUDENTS_XML );
		Document doc = new SAXReader().read(file2);
	}
	/**
	 * method1 没有处理乱码问题.不往下测了.
	 * DOM4J 实现对XML文档的增、删、改、查:
	 * https://blog.csdn.net/fightfaith/article/details/50302437
	 * */
	private static final String STUDENTS_XML = "target/test-classes/zzz/Dom4jTest.xml";
	private static final String STUDENTS_COPY_XML = "F:/html.txt";
	@Test
	public void method1() throws Exception {
		// 创建解析器
		SAXReader reader = new SAXReader();//这个是用来读取文件内容的
		Document doc = reader.read(new File(STUDENTS_XML)); //指定要读取的文件
		System.out.println(doc.asXML()); //打印出文件
	}
	
	//实现对XML文件的复制
	@Test
	public void method2() throws Exception {
		// 得到Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(STUDENTS_XML));
		
		// 保存Document，指定将写入的目的文件（复制功能）
		XMLWriter writer = new XMLWriter(new FileOutputStream(STUDENTS_COPY_XML));
		
		writer.write(doc); //开始写入
	}
	
	//遍历Document
	@Test
	public void method3() throws Exception {
		// 要遍历文档，首先要得到Document对象
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(STUDENTS_XML));
	    
		//获取根元素
		Element root = doc.getRootElement();
		//获取根元素中所有student元素
		List<Element> stuEleList = root.elements("student");
		// 循环遍历所有学生元素
		for(Element stuEle : stuEleList) {
			//获取学生元素的number
			String number = stuEle.attributeValue("number");
			//获取学生元素名为name的子元素的文本内容
			String name = stuEle.elementText("name");
			//获取学生元素名为age的子元素的文本内容
			String age = stuEle.elementText("age");
			//获取学生元素名为sex的子元素的文本内容
			String sex = stuEle.elementText("sex");		
			System.out.println(number + ", " + name + ", " + age + ", " + sex);
		}
	}
	
	//添加元素
	@Test
	public void method4() throws Exception {
		// 得到Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("src/students.xml"));

		//获取root元素
		Element root = doc.getRootElement();
		Element stuEle = root.addElement("student"); //添加了student元素
		// 给stuEle添加属性，名为number，值为1003
		stuEle.addAttribute("number", "1003"); 
		// 分别为stuEle添加名为name、age、sex的子元素，并为子元素设置文本内容
		stuEle.addElement("name").setText("wangWu");
		stuEle.addElement("age").setText("18");
		stuEle.addElement("sex").setText("male");
		
		// 设置保存的格式化器  1. \t，使用什么来完成缩进 2. true, 是否要添加换行
		OutputFormat format = new OutputFormat("\t", true);
		format.setTrimText(true); //去掉空白
		// 在创建Writer时，指定格式化器
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/students_copy.xml"), format);
		writer.write(doc);
	}
	
	//修改元素
	@Test
	public void method5() throws Exception {

		// 得到Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("src/students_copy.xml"));

		//使用XPath找到符合条件的元素
		// 查询student元素，条件是number属性的值为1003
		Element stuEle = (Element) doc.selectSingleNode("//student[@number='ITCAST_1003']");
		//修改stuEle的age子元素内容为81
		stuEle.element("age").setText("81");
		//修改stuEle的sex子元素的内容为female
		stuEle.element("sex").setText("female");

	}
	
	//删除元素
	@Test
	public void method6() throws Exception {

		// 得到Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("src/students_copy.xml"));
		
		// 查找student元素，条件是name子元素的文本内容为wangWu
		Element stuEle = (Element) doc.selectSingleNode("//student[name='wangWu']");
		
		// 2. 获取父元素，使用父元素删除指定子元素
		stuEle.getParent().remove(stuEle);
	}
	/**
	 * 
	 * 06_XML的写入_dom4j添加、删除、修改Xml文件内容
	 * https://www.cnblogs.com/HigginCui/p/5896846.html
	 */
	private static final String D_STUDENT_XML = "F:/student.txt";
	private static final String SRC_PERSON_XML = "target/test-classes/zzz/student.xml";
	
	
	@Test /**
	 *  读出原有xml文件的内容数据，然后将数据写入到磁盘上的XML文件
	 *  【读取原有Xml文件的内容，然后将数据写入到磁盘上】
	 */
	public void student() throws Exception {
		//读取已存在的Xml文件person.xml
        Document doc=new SAXReader().read(new File(SRC_PERSON_XML));
        
        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream(D_STUDENT_XML);
        
        //1.创建写出对象
       XMLWriter writer=new XMLWriter(out);
       
       //2.写出Document对象
       writer.write(doc);
       
       //3.关闭流
       writer.close();
	}
	@Test/**【按照设定的格式将xml内容输出保存到对应的磁盘地址上】
	 *  1.读出原有xml文件的内容数据，保存至Document对象
	 *  2.修改输出的format（输出格式、编码...）
	 *  3.然后将document包含数据写入到磁盘上的XML文件
	 *  XMLWriter writer=new XMLWriter(out,format);
	 */
	public void student1() throws Exception {
		 //读取已存在的Xml文件person.xml
        Document doc=new SAXReader().read(new File(SRC_PERSON_XML));
        
        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream(D_STUDENT_XML);
        /**
         *  指定文本的写出的格式：
         *      紧凑格式 ：项目上线时候使用
         *      漂亮格式：开发调试的时候使用
         */
        //OutputFormat format=OutputFormat.createCompactFormat();  //紧凑格式:去除空格换行
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        
        /**
         * 指定生成的xml文档的编码影响了：
         *         1.xml文档保存时的编码
         *         2.xml文档声明的encoding编码（Xml解析时的编码）
         * 结论：使用该方法生成Xml文档可以避免中文乱码问题
         */
        format.setEncoding("UTF-8");
        
        //1.创建写出对象
       XMLWriter writer=new XMLWriter(out,format);
       
       //2.写出Document对象
       writer.write(doc);
       
       //3.关闭流
       writer.close();
	}
	
//	【增、删、改 Xml文件
//
//	/**
//	* 1.创建Document对象，
//	* 添加：各种标签、属性、文本
//	* 修改：属性值、文本
//	* 删除：标签、属性
//	* 2.修改输出的format（输出格式、编码...）
//	* 3.然后将document包含数据写入到磁盘上的XML文件
//	*/
//
//	】
	
	
	@Test/**【增加：文档、标签、属性】
     * 创建新文档下的增加：文档、标签、属性 
     */
	public void student2() throws Exception {
		 //1.创建文档
        Document doc=DocumentHelper.createDocument();
        //2.添加标签
        Element rootElem=doc.addElement("root");
        Element stuElem=rootElem.addElement("student");
        stuElem.addElement("name");
        //4.增加属性
        stuElem.addAttribute("id", "88888");
        stuElem.addAttribute("sex", "男");
        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream(D_STUDENT_XML);
        
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        
        //1.创建写出对象
        XMLWriter writer=new XMLWriter(out,format);
           
        //2.写出Document对象
        writer.write(doc);
        
        //3.关闭流
        writer.close();
	}
	@Test /** 【修改：属性值、文本】
     * 修改：属性值、文本
这里说明doc对象是xml在内存中的一个副本.
不管怎么操作都不会影响原xml的内容.
只有在指定写入覆盖的时候会
     */
	public void student3() throws Exception {
		 //创建Document对象，读取已存在的Xml文件person.xml
        Document doc=new SAXReader().read(new File(SRC_PERSON_XML));
        
        /**
         * 修改属性值（方案一）   
         * 方法：使用Attribute类(属性类)的setValue()方法
         * 1.得到标签对象
         * 2.得到属性对象
         * 3.修改属性值
         */
    /*    //1.1 得到标签对象
        Element stuElem=doc.getRootElement().element("student");
        //1.2 得到id属性对象
        Attribute idAttr=stuElem.attribute("id");
        //1.3 修改属性值
        idAttr.setValue("000001"); 
    */
        
        /**
         *  修改属性值（方案二）
         *  方法：Element标签类的addAttribute("attr","value")方法
         */
    /*    //1.得到属性值标签
        Element stuElem=doc.getRootElement().element("student");
        //2.通过增加同名属性的方法，修改属性值
        stuElem.addAttribute("id", "000009");  //key相同，覆盖；不存在key，则添加
     */
        /**
         * 修改文本
         * 方法：Element标签类的setTest("新文本值")方法
         * 1.得到标签对象
         * 2.修改文本
         */
        Element nameElem=doc.getRootElement().element("student").element("name");
        nameElem.setText("王二麻子");
        
        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream(D_STUDENT_XML);
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        //1.创建写出对象	-- 输出位置   	格式
        XMLWriter writer=new XMLWriter(out,format);
        //2.写出Document对象
        writer.write(doc);
        //3.关闭流
        writer.close();
	}
	@Test
	public void student4() throws Exception {
		//创建Document对象，读取已存在的Xml文件person.xml
        Document doc=new SAXReader().read(new File(SRC_PERSON_XML));
        
        /**
         * 删除标签
         * 1.得到标签对象
         * 2.删除标签对象（可以通过父类删除子标签，也可以自己删自己）
         */
/*        Element ageElement=doc.getRootElement().element("student").element("age");
         ageElement.detach();  //直接detch()删除
        //ageElement.getParent().remove(ageElement); //先获取父标签，然后remove()删除子标签
*/
        /**
         * 删除属性
         */
        //1.得到标签对象
        //等同于Element stuElem=doc.getRootElement().element("student");
        Element stuElem=(Element) doc.getRootElement().elements().get(0);
        //2.得到属性对象
        Attribute idAttr=stuElem.attribute("id");
        //3.删除属性
        idAttr.detach();
        
        
        //指定文件输出的位置
        FileOutputStream out =new FileOutputStream(D_STUDENT_XML);
        // 指定文本的写出的格式：
        OutputFormat format=OutputFormat.createPrettyPrint();   //漂亮格式：有空格换行
        format.setEncoding("UTF-8");
        //1.创建写出对象
        XMLWriter writer=new XMLWriter(out,format);
        //2.写出Document对象
        writer.write(doc);
        //3.关闭流
        writer.close();
	}
}
