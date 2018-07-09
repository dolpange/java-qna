package codesquad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

        registry.addViewController("/").setViewName("/qnas");
        registry.addViewController("/qna/form").setViewName("qna/form");
        registry.addViewController("/user/form").setViewName("user/form");
        registry.addViewController("/user/login").setViewName("user/login");
        registry.addViewController("/user/login_failed").setViewName("user/login_failed");
    }
}