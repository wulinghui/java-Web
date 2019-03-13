package springboot.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.novalue.Util;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext ac	= SpringApplication.run(Application.class, args);
		Util.SpringBean.setAc(ac);//放入Bean..
//		DaoProxy a = ac.getBean(DaoProxy.class);
//		System.out.println("ac===="+ac + "----a:"+a);
	}

}
