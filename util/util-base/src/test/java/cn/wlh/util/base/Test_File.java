package cn.wlh.util.base;

import org.junit.Test;

import static  junit.framework.Assert.*;

public class Test_File {
	@Test
	public void Work() {
		assertEquals("", "");
		// C:\Users\wlh\eclipse-workspace\ util\ util-base
		System.out.println( _File.WOKE_PATH );
	}  
	@Test
	public void getSourcePath() {
		System.out.println( _File.getSourcePath() );
	}
	@Test
	public void work() {
		System.out.println(_File._Path.getClassRoot(String.class));
	}
	public static void main(String[] args) {
		/* C:\Users\wlh\eclipse-workspace \ util \ util-base */
//		System.out.println( _File.WOKE_PATH );	
	}
}
