package cn.wlh.util.extend.complier.java;

import java.io.File;

import cn.wlh.util.extend.complier.ComplierClient.ComplierFile;
import cn.wlh.util.extend.complier.ComplierClient.ComplierFileOfPart;

public class PartOfNewJava extends ComplierFileOfPart<JavaFile> {

	public PartOfNewJava(ComplierFile<JavaFile> part) {
		super(part);
	}

	@Override
	public JavaFile handle(File file, StringBuffer sb) {
		JavaFile java = this.getPart().handle(file, sb);
		//TODO 通过序列化的实体类,进行多次操作.
		return java;
	}

}
