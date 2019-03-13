package springboot.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration//最好与Application同级..
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()  
                .antMatchers("/", "/home").permitAll()//不保护的跟路径
                .anyRequest().authenticated()    
                .and()    
            .formLogin()  
                .loginPage("/login")//登陆页面
                .permitAll()    
                .and()        
            .logout()      
                .permitAll();  
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()    
                .withUser("user").password("password").roles("USER_wulinghui");
    }

}