package util.extend.complier.java.a;

import java.util.Date;

import org.junit.Test;

import util.extend.complier.java.b.ClassModifiersFactoryTest;

public class Test_Test {
	@Test
	public void aaa() throws ClassNotFoundException {
//		Class.forName("util.extend.complier.java.a.Test0");
//		Class.forName("util.extend.complier.java.a.Test000");
		Date date = new Date();
		date.setYear(2017-1900);
		date.setMonth(3);  
		date.setDate(1);
		System.out.println(date);
		System.out.println(date.getTime());
	}
	public void bbb() {
//		ClassModifiersFactoryTest.newClassModifiersTest().log();  //  The type ClassModifiersTest is not visible
	}
}
