package cn.wlh.util.base.adapter.spring.core.bean;
  
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Map;

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




public abstract class IOCOfSpringBean {
 	
	
	IOCOfSpringBean(){}
	private static ApplicationContext ac = null;
	
	
	{
		BeanFactory parentBeanFactory = ac.getParentBeanFactory();
		AutowireCapableBeanFactory autowireCapableBeanFactory = ac.getAutowireCapableBeanFactory();
		Object existingBean = null; 
		autowireCapableBeanFactory.destroyBean(existingBean);
	}
	
	
	public  static void setAc(ApplicationContext ac) {//main÷– π”√
		IOCOfSpringBean.ac = ac;
	}
	public  static ApplicationContext getAc() {
		return ac;
	}
	public  static Object getBean(String id) {
		return ac.getBean(id);
	}
	public  static <T> T getBean(Class<T> c) {
		return ac.getBean(c);
	}
	public  static <T> T getBean(Class<T>c , String name) {
		return ac.getBean(c,name);
	}
	
	public static void publishEvent(ApplicationEvent event) {
		ac.publishEvent(event);
	}
	public static BeanFactory getParentBeanFactory() {
		return ac.getParentBeanFactory();
	}
	public static boolean containsLocalBean(String name) {
		return ac.containsLocalBean(name);
	}
	public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return ac.getMessage(code, args, defaultMessage, locale);
	}
	public static void publishEvent(Object event) {
		ac.publishEvent(event);  
	}
	public static Resource getResource(String location) {
		return ac.getResource(location);
	}
	public static Environment getEnvironment() {
		return ac.getEnvironment();
	}
	public static String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return ac.getMessage(code, args, locale);
	}
	public static boolean containsBeanDefinition(String beanName) {
		return ac.containsBeanDefinition(beanName);
	}
	public static ClassLoader getClassLoader() {
		return ac.getClassLoader();
	}
	public static String getId() {
		return ac.getId();
	}
	public static Resource[] getResources(String locationPattern) throws IOException {
		return ac.getResources(locationPattern);
	}
	public static String getApplicationName() {
		return ac.getApplicationName();
	}
	public static String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return ac.getMessage(resolvable, locale);
	}
	public static int getBeanDefinitionCount() {
		return ac.getBeanDefinitionCount();
	}
	public static String getDisplayName() {
		return ac.getDisplayName();
	}
	public static long getStartupDate() {
		return ac.getStartupDate();
	}
	public static String[] getBeanDefinitionNames() {
		return ac.getBeanDefinitionNames();
	}
	public static ApplicationContext getParent() {
		return ac.getParent();
	}
	public static AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
		return ac.getAutowireCapableBeanFactory();
	}
	public static String[] getBeanNamesForType(ResolvableType type) {
		return ac.getBeanNamesForType(type);
	}
	public static String[] getBeanNamesForType(Class<?> type) {
		return ac.getBeanNamesForType(type);
	}
	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return ac.getBean(name, requiredType);
	}
	public static String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
		return ac.getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
	}
	public static Object getBean(String name, Object... args) throws BeansException {
		return ac.getBean(name, args);
	}
	public static <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
		return ac.getBeansOfType(type);
	}
	public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
		return ac.getBean(requiredType, args);
	}
	public static <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit)
			throws BeansException {
		return ac.getBeansOfType(type, includeNonSingletons, allowEagerInit);
	}
	public static boolean containsBean(String name) {
		return ac.containsBean(name);
	}
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return ac.isSingleton(name);
	}
	public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		return ac.isPrototype(name);
	}
	public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
		return ac.getBeanNamesForAnnotation(annotationType);
	}
	public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType)
			throws BeansException {
		return ac.getBeansWithAnnotation(annotationType);
	}
	public static boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
		return ac.isTypeMatch(name, typeToMatch);
	}
	public static <A extends Annotation> A findAnnotationOnBean(String beanName, Class<A> annotationType)
			throws NoSuchBeanDefinitionException {
		return ac.findAnnotationOnBean(beanName, annotationType);
	}
	public static boolean isTypeMatch(String name, Class<?> typeToMatch) throws NoSuchBeanDefinitionException {
		return ac.isTypeMatch(name, typeToMatch);
	}
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return ac.getType(name);
	}
	public static String[] getAliases(String name) {
		return ac.getAliases(name);
	}
}
