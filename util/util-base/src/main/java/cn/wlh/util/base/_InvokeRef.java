package cn.wlh.util.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class _InvokeRef {
	 /**  
		 * @param <T>
		 * @param target
		 * @param tar
		 * @param name
		 * @param declared 获得私有的方法,但不获得父类的
		 * @param objects  
		 * @return
		 * @throws NoSuchMethodException
		 * @throws SecurityException
		 * @throws IllegalAccessException
		 * @throws IllegalArgumentException
		 * @throws InvocationTargetException
		 */
		@SuppressWarnings("unchecked")
		private static <T> T invoke(Object target,Class<?> tar,String name,boolean declared,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			//通过getClass获得parameterTypes
			Class<?> [] parameterTypes = _Class.obj2Class(objects);
			//获得公开方法,并反射
			return (T) (declared ? tar.getDeclaredMethod(name, parameterTypes).invoke(target, objects) 
					: tar.getMethod(name, parameterTypes).invoke(target, objects) );
		}
		public static <T> T invoke(Object target,String name,boolean declared,Object...objects ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			if(target !=null ) return null;
			return invoke(target, target.getClass() , name, declared, objects);
		}
		/** 该方法,只能循环后面的参数
		 * @param obj -- 执行方法的对象 , null为静态方法         
		 * @param method	  
		 * @param fix	-- 固定的Para  
		 * @param forPara	-- 循环的 Para  
		 * @param skip	-- forPara 的 步长
		 * @return
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 *///Object [] -- 用户不好转换
		public static /*Object []*/ /*Object*/ <T> T[] forInvoke(Object obj,Method method,Object [] fix,Object [] forPara,int skip) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			if( forPara == null || fix == null ) throw new NullPointerException(); 
			if( skip <= 0) throw new IndexOutOfBoundsException();
			int pLen = forPara.length;
			//校验 forPara.length 与  skip 的合法性
			if ( pLen % skip != 0) throw new IndexOutOfBoundsException();  
			int fLen = fix.length;
			// 组装 方法的args ..由fix + forPara的参数
			Object [] args = new Object [ fLen + skip ];
			for (int i = 0; i < fLen; i++) {//把fix放入,之后就不改变了
				args [i] = fix[i]; 
			}
			//方法的循环...
			Object [] retObjs = new Object[ pLen / skip ];
			int flag = 0;
			for (int i = 0; i < pLen ; i+= skip) {
				//先把forPara放入指定位置
				for (int j = 0; j < skip ; j++) {
					args[ fLen + j ] = forPara[j]; 
				}
				//invoke
				retObjs[flag] = method.invoke(obj, args);
				flag++;//下标++
			}
			return (T[]) retObjs;
		}
	
		/**	@see this.forInvoke(Object obj,Method method,Object [] fix,Object [] forPara,int skip)
		 * @param obj obj -- 执行方法的对象  , 不能为null
		 * @param methodName
		 * @param parsClass
		 * @param fix
		 * @param forPara
		 * @return
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 * @throws SecurityException
		 * @throws NoSuchMethodException
		 */
		public static <T> T[] forInvoke(Object obj,String methodName,Class<?>[] parsClass,Object [] fix,Object [] forPara) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException{
			if(obj == null) throw new NullPointerException();//不支持静态方法    
			return forInvoke(obj, _Method.getAllMethod(obj.getClass(), methodName, parsClass) ,fix ,forPara,  parsClass.length - fix.length);
		}
		/** 为了灵活的循环调用方法
		 * @param obj
		 * @param method
		 * @param fix
		 * @param forPara
		 * @param argsOrderBy -- 为arcs排序.  new int[]{ 1,3,5,//fix的参数
		 * 												 2 , 4}//forPara的参数
		 * 										通过这样来为临时的args的参数
		 * @return
		 * @throws IllegalArgumentException
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 */
		public static  <T> T[] forInvoke(Object obj,Method method,Object [] fix,Object [] forPara,int [] argsOrderBy) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
			//校验..
			if( forPara == null || fix == null || argsOrderBy == null) throw new NullPointerException(); 
			int f = fix.length;
			int p = forPara.length;
			int a = argsOrderBy.length;
			int skip = a - p;
			if( skip <= 0)  throw new IndexOutOfBoundsException();
			if ( p % skip != 0) throw new IndexOutOfBoundsException();
			// 组装 方法的args ..由fix + forPara的参数
			Object [] args = new Object [ f + skip ];
			for (int i = 0; i < f; i++) {//把fix放入指定的位置,之后就不改变了
				args [ argsOrderBy[i] ] = fix[i];
			}
			//方法的循环...
			Object [] retObjs = new Object[ p / skip ];
			int flag = 0;
			for (int i = 0; i < p ; i+= skip) {
				//先把forPara放入指定位置
				for (int j = 0; j < skip ; j++) {
					args[  argsOrderBy[f + j]  ] = forPara[j]; 
				}
				//invoke
				retObjs[flag] = method.invoke(obj, args);
				flag++;//下标++
			}
			return (T[]) retObjs;
		}
	
}
