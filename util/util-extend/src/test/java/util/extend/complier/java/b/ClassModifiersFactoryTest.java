package util.extend.complier.java.b;

/**
 * @author 吴灵辉
 * 测试Class的访问权限问题
 */
public class ClassModifiersFactoryTest{
	/*
	 * util.extend.complier.java.a.Test_Test.bbb() 
	 * 这里不能使用...
	 */
	public static ClassModifiersTest newClassModifiersTest() {
		return null;
	}
}

class ClassModifiersTest {
	public  void log() {
		
	}
}
