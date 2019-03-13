package cn.wlh.util.extend.wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StringWrap{
	@SuppressWarnings("serial")
	public static class Set extends HashSet<String>{} 
	@SuppressWarnings("serial")
	public static class List extends ArrayList<String>{}
	@SuppressWarnings("serial")
	public static class Map extends HashMap<String,String>{}
}
