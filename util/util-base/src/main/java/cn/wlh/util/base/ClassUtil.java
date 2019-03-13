package cn.wlh.util.base;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import cn.wlh.util.base.exception.ThrowableFacade;

/**
*@author 吴灵辉
*/
public class ClassUtil extends _Class {
	static java.lang.ClassLoader CONTEXT_CLASS_LOADER;
	
	/**这个不会触发static方法
	 * 这里用Class.forName有一些不好，会触发static方法，没有使用classLoader的load干净
	 * @param className
	 * @return
	 */
	public static Class<?> forName(String className) {
		try {
			Class<?> loadClass = getClassMap().get(className);
			if( loadClass == null) {
				loadClass = getClassLoader().loadClass(className);
				getClassMap().put(className, loadClass);
			}
			return loadClass;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
    /**
     * 获取类加载器
     */
	public static java.lang.ClassLoader getClassLoader() {
		if(CONTEXT_CLASS_LOADER == null)
			CONTEXT_CLASS_LOADER = Thread.currentThread().getContextClassLoader();
        return CONTEXT_CLASS_LOADER;
    }
    
    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls = null;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
            //这里登记到系统Map中。 
            getClassMap().put(className, cls);
        } catch (ClassNotFoundException e) {
        	ThrowableFacade.THROW_RUN.doThrowOfRun(e);
        }    
        return cls;  
    }

    /**
     * 加载类（默认将初始化类）
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, true);
    }
    public static void main(String[] args) {
		Set<Class<?>> classSet = getClassSet("java.lang");
		for (Class<?> class1 : classSet) {
			System.out.println(class1);
		}
		System.out.println(1111);
		
	}
    /**
     * 获取指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName) {
    	return getClassSet(getClassLoader(), packageName);
    }
    public static Set<Class<?>> getClassSet(java.lang.ClassLoader classLoader ,String packageName) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls = classLoader.getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
            	URL url = urls.nextElement();
                addClassFromUrlToSet(packageName, classSet, url);
            }
        } catch (Throwable e) {
        	ThrowableFacade.THROW_RUN.doThrowOfRun(e);
        }
        return classSet;
    }

	public static void addClassFromUrlToSet(String packageName, Set<Class<?>> classSet, URL url)
			throws IOException {
		if (url != null) {
		    String protocol = url.getProtocol();
		    if (protocol.equals("file")) {
		        String packagePath = url.getPath().replaceAll("%20", " ");
		        addClass(classSet, packagePath, packageName);
		    } else if (protocol.equals("jar")) {
		        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		        if (jarURLConnection != null) {
		            JarFile jarFile = jarURLConnection.getJarFile();
		            if (jarFile != null) {
		                Enumeration<JarEntry> jarEntries = jarFile.entries();
		                while (jarEntries.hasMoreElements()) {
		                    JarEntry jarEntry = jarEntries.nextElement();
		                    String jarEntryName = jarEntry.getName();
		                    if (jarEntryName.endsWith(".class")) {
		                        String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
		                        doAddClass(classSet, className);
		                    }
		                }
		            }
		        }
		    }
		}
	}
    /**这里对java没有反应。
     * @throws IOException */
    public static void main0(String[] args) throws IOException {
//    	System.out.println(getClassSet(Map.class.getPackage().getName()));
    	
    	Enumeration<URL> resources = getClassLoader().getResources("java.util".replace(".", "/"));//没有值
    	System.out.println(resources.hasMoreElements());
    	resources = getClassLoader().getParent().getResources("org.apache.commons.lang3".replace(".", "/"));
    	System.out.println(resources.hasMoreElements());       
	}
    /**从CLASS_SET下某父类（或接口）的所有子类（或实现类）
     * @param superClass
     * @param CLASS_SET
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass , Set<Class<?>> CLASS_SET) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
            }
        });
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtil.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                doAddClass(classSet, className);
            } else {
                String subPackagePath = fileName;
                if (StringUtil.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                String subPackageName = fileName;
                if (StringUtil.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls = loadClass(className, false);
        classSet.add(cls);
    }
    
	/*public static void main(String[] args) {
	System.out.println(  "txn99".substring(3, 5));
	System.out.println(System.getProperty("user.dir"));
	String[] split = System.getProperty("java.class.path").split(";");
	HashSet<Class<?>> hashSet = new HashSet<Class<?>>();
	for (String string : split) {
		try {
			String per = "cn/wlh";
			//  file:/C:/Users/wlh/eclipse-workspace/web/target/classes/cn/wlh
			System.out.println(new File(string+"/"+per).toURL());
			ClassUtil.addClassFromUrlToSet( per, hashSet, new File(string).toURL() );
			System.out.println( new URL("file:/C:/Users/wlh/eclipse-workspace/web/target/classes/cn/wlh")); 
		} catch (Exception e) {
			System.out.println( e.getMessage());
		}
	}
	ClassUtil.getClassSet("cn.wlh");
	Class<?> class1 = LinkedHashSet.class;
	String name = class1.getPackage().getName();
	System.out.println(name);
	ClassLoader classLoader = class1.getClassLoader();
	System.out.println( classLoader );
	ClassLoader classLoader2 = ViewImp.class.getClassLoader();
	System.out.println( classLoader2 );
//	ClassUtil.getClassSet(classLoader,name);
	System.out.println( hashSet.size() );  
	System.out.println(System.getProperty("java.class.path"));  
}*/
}
