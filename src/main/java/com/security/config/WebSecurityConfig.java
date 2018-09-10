package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 
 * @Description： 功能描述
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2018年9月5日上午9:58:24]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private MyauthenticationProvider myauthenticationProvider;
	
	private static final String KEY = "Security_Key";
	
	// 完成自定义认证实体注入
	@Bean
	UserDetailsService userService() {
		return new UserDetailsServiceImpl();
	}

	/**
	 * form-login是spring security命名空间配置登录相关信息的标签,它包含如下属性： 
		1. login-page 自定义登录页url,默认为/login 
		2. login-processing-url 登录请求拦截的url,也就是form表单提交时指定的action 
		3. default-target-url 默认登录成功后跳转的url 
		4. always-use-default-target 是否总是使用默认的登录成功后跳转url 
		5. authentication-failure-url 登录失败后跳转的url 
		6. username-parameter 用户名的请求字段 默认为userName 
		7. password-parameter 密码的请求字段 默认为password 
		8. authentication-success-handler-ref 指向一个AuthenticationSuccessHandler用于处理认证成功的请求,
		不能和default-target-url还有always-use-default-target同时使用 
		9. authentication-success-forward-url 用于authentication-failure-handler-ref 
		10. authentication-failure-handler-ref 指向一个AuthenticationFailureHandler用于处理失败的认证请求 
		11. authentication-failure-forward-url 用于authentication-failure-handler-ref 
		12. authentication-details-source-ref 指向一个AuthenticationDetailsSource,在认证过滤器中使用
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				// 都可以访问
				.antMatchers("/static/**","/css/**", "/js/**", "/fonts/**", "/img/**","/OAuth/callback/getOAuth","/OAuth/authLogin").permitAll()
				// 需要相应的角色才能访问
				//.antMatchers("/admin/**").hasRole("ADMIN")
				.and()
				//基于 Form 表单登录验证
				.formLogin().loginProcessingUrl("/login")
				.defaultSuccessUrl("/main")//登陆成功之后跳转的页面
				// 自定义登录界面
				.loginPage("/login").failureUrl("/login?error").permitAll()
				.and().logout().logoutSuccessUrl("/login").permitAll()// 注销请求可直接访问
				// 启用 remember me
				.and().rememberMe().key(KEY)
				// 处理异常，拒绝访问就重定向到 403 页面
				.and().exceptionHandling().accessDeniedPage("/403");
		// 禁用 H2 控制台的 CSRF 防护
		//http.csrf().ignoringAntMatchers("/h2-console/**");
		// 允许来自同一来源的H2 控制台的请求
		http.headers().frameOptions().sameOrigin();
		http.csrf().disable().authorizeRequests().anyRequest().authenticated();// 所有请求必须登陆后访问
	}
	
	/**
     * @Description: 用户登录认证
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // 将验证过程交给自定义验证工具
        auth.authenticationProvider(myauthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
}
