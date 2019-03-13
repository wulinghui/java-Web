package com.novalue;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import dao.sql.Word;
import util.base._Exception;
import util.base._File;
import util.base._Exception.ToNullPointerException;
import util.base._Method.MethodFilter;
import util.extend.Confi;
import util.extend.MethodOfPackage;


public class Util {
	/**controller使用Biz*/
	public static class UserBiz{
		public static final MethodOfPackage methodsOfBiz = _Exception
				.toRuntime(new ToNullPointerException<MethodOfPackage>() {
					@Override
					public MethodOfPackage handle() throws Throwable {
						return new MethodOfPackage.FullName(
								Confi.config.getBizAllMethodByMethodOfPackage(), false
								,MethodFilter.BIZ );
					}    
		});
		public static Method getBiz(HttpServletRequest request ) {
	    	String uri = request.getRequestURI();
	    	String url = request.getRequestURL().toString();
//	    	System.out.println("uri="+uri + "|| url=" + url );
	    	String [] strs = uri.split( _File.separator );
	    	return methodsOfBiz.getMethod(strs[0], strs[1]);
	    }
	    public static Method getBiz(String className,String method ) {
	    	//TODO sysout
//	    	System.out.println( "methodsOfBiz+"+methodsOfBiz );
	    	return methodsOfBiz.getMethod(className, method );
	    }
	    public static Word map2Word(HttpServletRequest request ) {
	    	Map<String,String[]> map = request.getParameterMap();
	    	Word word = new Word();
	    	for (Entry<String, String[]> element : map.entrySet() ) {
				word.put( element.getKey() , element.getValue()[0] );
			}
	    	return word;
	    }
	}
	/** getBean*/
	public static class SpringBean{
		private static ApplicationContext ac = null;
		public static void setAc(ApplicationContext ac) {//main中使用
			SpringBean.ac = ac;
		}
		public static ApplicationContext getAc() {
			return ac;
		}
		public static Object getBean(String id) {
			return ac.getBean(id);
		}
		public static <T> T getBean(Class<T> c) {
			return ac.getBean(c);
		}
		public static <T> T getBean(Class<T>c , String name) {
			return ac.getBean(c,name);
		}
		
		public void publishEvent(ApplicationEvent event) {
			ac.publishEvent(event);
		}
		public BeanFactory getParentBeanFactory() {
			return ac.getParentBeanFactory();
		}
		public boolean containsLocalBean(String name) {
			return ac.containsLocalBean(name);
		}
		public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
			return ac.getMessage(code, args, defaultMessage, locale);
		}
		public void publishEvent(Object event) {
			ac.publishEvent(event);
		}
		public Resource getResource(String location) {
			return ac.getResource(location);
		}
		public Environment getEnvironment() {
			return ac.getEnvironment();
		}
		public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
			return ac.getMessage(code, args, locale);
		}
		public boolean containsBeanDefinition(String beanName) {
			return ac.containsBeanDefinition(beanName);
		}
		public ClassLoader getClassLoader() {
			return ac.getClassLoader();
		}
		public String getId() {
			return ac.getId();
		}
		public Resource[] getResources(String locationPattern) throws IOException {
			return ac.getResources(locationPattern);
		}
		public String getApplicationName() {
			return ac.getApplicationName();
		}
		public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
			return ac.getMessage(resolvable, locale);
		}
		public int getBeanDefinitionCount() {
			return ac.getBeanDefinitionCount();
		}
		public String getDisplayName() {
			return ac.getDisplayName();
		}
		public long getStartupDate() {
			return ac.getStartupDate();
		}
		public String[] getBeanDefinitionNames() {
			return ac.getBeanDefinitionNames();
		}
		public ApplicationContext getParent() {
			return ac.getParent();
		}
		public AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
			return ac.getAutowireCapableBeanFactory();
		}
		public String[] getBeanNamesForType(ResolvableType type) {
			return ac.getBeanNamesForType(type);
		}
		public String[] getBeanNamesForType(Class<?> type) {
			return ac.getBeanNamesForType(type);
		}
		public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
			return ac.getBean(name, requiredType);
		}
		public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
			return ac.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
		}
		public Object getBean(String name, Object... args) throws BeansException {
			return ac.getBean(name, args);
		}
		public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
			return ac.getBeansOfType(type);
		}
		public <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
			return ac.getBean(requiredType, args);
		}
		public <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
				throws BeansException {
			return ac.getBeansOfType(type, includeNonSingletons, allowEagerInit);
		}
		public boolean containsBean(String name) {
			return ac.containsBean(name);
		}
		public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
			return ac.isSingleton(name);
		}
		public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
			return ac.isPrototype(name);
		}
		public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
			return ac.getBeanNamesForAnnotation(annotationType);
		}
		public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType)
				throws BeansException {
			return ac.getBeansWithAnnotation(annotationType);
		}
		public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
			return ac.isTypeMatch(name, typeToMatch);
		}
		public <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
				throws NoSuchBeanDefinitionException {
			return ac.findAnnotationOnBean(beanName, annotationType);
		}
		public boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
			return ac.isTypeMatch(name, typeToMatch);
		}
		public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
			return ac.getType(name);
		}
		public String[] getAliases(String name) {
			return ac.getAliases(name);
		}
	}
}
