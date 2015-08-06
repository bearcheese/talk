package com.bearmaster.talk.util;

import org.jdesktop.application.SingleFrameApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "com.bearmaster.talk")
@PropertySource("classpath:talk.properties")
public class AppConfig {

    private static SingleFrameApplication application;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void setApplication(SingleFrameApplication app) {
        application = app;
    }
    
    @Bean
    public SingleFrameApplication getApplication() {
        return application;
    }
}
