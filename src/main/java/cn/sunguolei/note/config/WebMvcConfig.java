package cn.sunguolei.note.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 首页
        registry.addViewController("/").setViewName("index");
        // 登录页
        registry.addViewController("/toLogin").setViewName("login");
    }
}
