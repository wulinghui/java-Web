package com.wlh.complier.javaandxml.complier;

import java.io.File;

import org.junit.Test;

import cn.wlh.util.base._File;
import cn.wlh.util.base._File.HandlerFile;

public class BuilderModleTest {
	
	/**
	 * 获得根路径 
	 * @throws Throwable 
	 */      
	@Test
	public void rootClass() throws Throwable {
		System.out.println(String.class.getResource("/"));
		String file = String.class.getResource("/").getFile();
		System.out.println(String.class.getResource("/").getFile());
		_File.forFile(new File(file), new HandlerFile() {
			@Override
			public void handler(File file) throws Throwable {
				System.out.println(file);
			}
		});
	}
}
