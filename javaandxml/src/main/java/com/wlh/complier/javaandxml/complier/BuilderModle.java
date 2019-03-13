package com.wlh.complier.javaandxml.complier;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.cn.wlh.boot.complier.util.Config;

import cn.wlh.util.base._File;
import cn.wlh.util.base._File.HandlerFile;

/**
 * @author wlh
 * 递归获得父级,生成标签模板.(Freemark框架)
 * 这个属于Maven的模板。
 */
public class BuilderModle {
	/**模板来源方式:和class同级,类似mybatis*/
	public static final byte CLASS_TONGJI = 0;
	
	
	public void complier() throws Throwable {
		String root = String.class.getResource("/").getFile();
		_File.forFile(new File(root), new cn.wlh.util.base._File.Filter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(Config.single.getSuffix());
			}
			
		}, new HandlerFile() {
			
			@Override
			public void handler(File file) throws Throwable {
				SAXReader read = new SAXReader();
				Document document = read.read(file);
				Node selectSingleNode = document.selectSingleNode("");
//				object.getText();
			}
			
		});
	}
}
