package cn.wlh.util.extend.freemaker.fm;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.TemplateException;


public class FreemarkerTest {

	public static void main(String[] args) throws IOException, TemplateException, ParseException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("username", "${username321}");//在ftl中要赋值的变量
		root.put("user", new User(1,"name1",11,new Group("组1")));
		List<User> users = new ArrayList<User>();
		users.add(new User(1,"name1",11));
		users.add(new User(2,"name2",15));
		
		users.add(new User(3,"name3",20)); 
		users.add(new User(4,"name4",30)); 
		root.put("users", users);
		
		FreemarkerUtil util = new FreemarkerUtil();
//		util.fprint("001.jsp", root, "0001.jsp");
		
		util.fprint("01.ftl", root, "01.ftl");//能生成ftl文件...
		/*TODO 我们这里可以生成ftl模板,所以我们用模板可以生成模板..  里面也可以放入${username321}
		接下来,就制定规则了:从一个模板文件01.ftl再通过01找文件夹通过FileMap特性生成Map.
		并生成ftl...这里好像没有必要.
		 *    
		 */
		util.fprint("02.ftl", root, "02.ftl");
		util.fprint("02.ftl", root, "02.ftl");
		util.fprint("02.ftl", root);
		util.fprint("03.ftl", root, "03.html");
		util.fprint("04.ftl", root, "04.html");
		util.fprint("05.ftl", root, "05.html");
		util.fprint("06.ftl", root, "06.html");
		util.fprint("02.ftl", "05.html", "src","ftl","a01");
	}
	
}