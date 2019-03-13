package cn.wlh.util.base;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Test_Array {
	@Test
	public void getArrays() {
		String [] s = _Array.getArrays(String.class, 10);
		assertEquals(10, s.length); //assertion ╤оят
	}
}
