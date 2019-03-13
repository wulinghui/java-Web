package cn.wlh.util.extend.freemaker.fm;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Map;

import cn.wlh.util.base._File;
import cn.wlh.util.extend.map.FileMap;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerUtil {
	final String basePackagePath;
	final String outFilePath;
    public FreemarkerUtil(String basePackagePath, String outFile) {
		this.basePackagePath = basePackagePath;
		this.outFilePath = outFile;
	}
	public FreemarkerUtil( String basePackagePath ,String... strings) {
		this( basePackagePath , _File.getAbsoluteFromWork( strings ) );
	}
	public FreemarkerUtil(  ) {
		this( "/ftl" , _File.getAbsoluteFromWork("src","page" ) );
	}
	public Template getTemplate(String name) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
            // 通过Freemaker的Configuration读取相应的ftl
            Configuration cfg = new Configuration();
            // 设定去哪里读取相应的ftl模板文件
            cfg.setClassForTemplateLoading(this.getClass(), this.basePackagePath );
            // 在模板文件目录中找到名称为name的文件
            Template temp = cfg.getTemplate(name);
            return temp;
    }
	
    /**
     * 控制台输出
     * @param name
     * @param root
     * @throws IOException 
     * @throws TemplateException 
     * @throws ParseException 
     */
    public void fprint(String name, Map<String, ?> root) throws TemplateException, IOException, ParseException {
            // 通过Template可以将模板文件输出到相应的流
            Template temp = this.getTemplate(name);
            temp.process(root, new PrintWriter(System.out));
    }
    
    /**
     * 输出HTML文件
     * @param name
     * @param root
     * @param outFile
     * @throws IOException 
     * @throws TemplateException 
     * @throws ParseException 
     */
    public void fprint(String name, Map<String,?> root, String outFile) throws IOException, TemplateException, ParseException {
        FileWriter out = null;
        try {
            // 通过一个文件输出流，就可以写到相应的文件中，此处用的是绝对路径
            out = new FileWriter(new File( _File.joinPath( this.outFilePath , outFile )   ));
            Template temp = this.getTemplate(name);
            temp.process(root, out);
        } finally {
                if (out != null) out.close();
        }
    }
    /** 通过文件获得Map.再执行上面的fpring
     * @param name
     * @param outFile
     * @param strings 
     * @throws IOException
     * @throws TemplateException 
     * @throws ParseException 
     */
    public void fprint(String name,  String outFile,String...strings) throws IOException, TemplateException, ParseException {
    	FileMap map = new FileMap();
    	Map<String, String> root = map.initMap(strings);
    	fprint(name, root,outFile);
    }
}