package com.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @Description：MVC配置
 * @author [ Wenfeng.Huang@desay-svautomotive.com ] on [2018年9月5日上午9:54:47]
 * @Modified By： [修改人] on [修改日期] for [修改说明]
 *
 */
@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 页面跳转请求注册
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//登录页面url
		registry.addViewController("/login").setViewName("login");
		//登录成功之后跳转的页面，如果需要传入一些资源参数进页面可以写到controller里
		//registry.addViewController("/main").setViewName("main");
	}
	
	/**
	 * 静态资源配置
	 */
	@Override
	  public void addResourceHandlers(ResourceHandlerRegistry registry) {   
		  registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	      super.addResourceHandlers(registry);
	  }
}
