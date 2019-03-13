package springboot.com.wlh.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.novalue.Util;

import dao.sql.Word;    
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springboot.com.wlh.biz.mdj.Test;
import springboot.com.wlh.dao.jpa.entity.User;
import springfox.documentation.annotations.ApiIgnore;
import util.base._File;

@Controller
//测试类...
public class ControllerModel {      
	private Logger logger = Logger.getLogger(getClass());
	
//	@RequestMapping(value="/{bean}/{method}.do", method=RequestMethod.GET)
//    public String my(@PathVariable String bean , @PathVariable String method ,HttpServletRequest request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
////    	String className = "com.didispace.biz.mdj."+ bean;
//    	String className = Test.class.getPackage().getName() + "."+ bean;
//    	method = "txn" + method;
//    	// TODO sysout
//    	System.out.println( className + "==" + method );    
//    	Method me = Util.UserBiz.getBiz( className , method);
//    	if( me == null ) return "";
//    	Word word = Util.UserBiz.map2Word(request);
//    	// TODO sysout 
//    	System.out.println( "word++"+word );
//    	// TO DO 获得spring 的 bean;
//    	Object obj = Util.SpringBean.getBean( Class.forName(className) );
//    	System.out.println( "obj="+obj );     
//    	String re = me.invoke(obj, word).toString();
//    	System.out.println( "re="+re );    
//    	return re;
//    }              
	//html
//	 @RequestMapping("/user/zzz")
//	    public String index(ModelMap map) {  
//		 //可以封装到Word里面
//	    	Map<String,Object> obj = new HashMap<String,Object>();
//	    	obj.put("host", "http://blog.didispace.com");//ok
//	    	map.addAllAttributes(obj);      
////	        map.addAttribute("host", "http://blog.didispace.com"); //ok 
//	        return "index";
//	    }
	 //Global 	ExceptionHandler
//	 @RequestMapping("/null")
//	    public String index() {      
//		 throw new NullPointerException("My NullPointerException");
//	 }  
	 /*
	  * Swagger2 文档...
	  * 访问..
	  * http://localhost:8080/swagger-ui.html
	  */  
//	 static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());
//	 @ApiOperation(value="删除用户", notes="根据url的id来指定删除对象")
//	    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long", paramType = "path")
//	    @RequestMapping(value="/{id}.del", method=RequestMethod.DELETE)
//	    public String deleteUser(@PathVariable Long id) {      
//	        users.remove(id);  
//	        return "success";     
//	    }
//	 @ApiIgnore//不会显示...
//	 @RequestMapping(value="/{id}.do", method=RequestMethod.PUT)
//	    public String putUser(@PathVariable Long id, @RequestBody User user) {
//	        User u = users.get(id);
//	        u.setName(user.getName());
//	        u.setAge(user.getAge());    
//	        users.put(id, u);  
//	        return "success";
//	   }
	
//  @RequestMapping(value="/mdj/{bean}/{method}.do", method=RequestMethod.GET)
//  public String mdjDo(@PathVariable String bean , @PathVariable String method ,HttpServletRequest request,ModelMap map) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
////  	String className = "com.didispace.biz.mdj."+ bean;
//  	String className = Test.class.getPackage().getName() + "."+ bean;
//  	method = "txn" + method;
//  	// TODO sysout
//  	System.out.println( className + "==" + method );    
//  	Method me = Util.UserBiz.getBiz( className , method);
//  	if( me == null ) return "";
//  	Word word = Util.UserBiz.map2Word(request);
//  	// TODO sysout 
//  	System.out.println( "word++"+word );
//  	// TO DO 获得spring 的 bean;
//  	Object obj = Util.SpringBean.getBean( Class.forName(className) );
//  	System.out.println( "obj="+obj );     
//  	Word re = ( (Word) me.invoke(obj, word) ).getVIEW_MODEL();
//  	System.out.println( "re="+re );    
//  	map.addAllAttributes(re); 
//  	return re.getViewHtml();
//  }        
  @RequestMapping(value="/mdj/{bean}/{method}.ajax", method=RequestMethod.GET)
  public String mdjAjax(@PathVariable String bean , @PathVariable String method ,HttpServletRequest request) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
	  System.out.println( _File.WOKE_PATH );
//  	String className = "com.didispace.biz.mdj."+ bean;
  	String className = Test.class.getPackage().getName() + "."+ bean;
  	method = "txn" + method;
  	// TODO sysout
//  	System.out.println( className + "==" + method );    
  	Method me = Util.UserBiz.getBiz( className , method);
  	if( me == null ) return "";
  	Word word = Util.UserBiz.map2Word(request);
  	// TODO sysout 
//  	System.out.println( "word++"+word );
  	// TO DO 获得spring 的 bean;
  	Object obj = Util.SpringBean.getBean( Class.forName(className) );
//  	System.out.println( "obj="+obj );     
  	String re = me.invoke(obj, word).toString();
//  	System.out.println( "re="+re );    
  	return re;
  }
	 /*
	  * 权限... 
	  *    
	  */
	 @RequestMapping("/hello")
	    public String hello() {
	        return "hello";
	    }
	    @RequestMapping("/login")      
	    public String login() {      
//	    	logger.info("ARGS : =-============ARGS : =-ARGS : =-ARGS : =-ARGS : =-===="); 
	        return "login";
	    }
	    @RequestMapping("/")
	    public String index1() {  
	        return "index";
	    }
}
