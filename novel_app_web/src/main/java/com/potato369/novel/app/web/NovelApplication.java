package com.potato369.novel.app.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.potato369.novel.basic.service", "com.potato369.novel.app.web", "com.potato369.novel.app.web.controller"})
@EnableCaching
@EnableJpaRepositories(basePackages = "com.potato369.novel.basic.repository")
@EntityScan(basePackages = "com.potato369.novel.basic.dataobject")
@MapperScan(basePackages = "com.potato369.novel.basic.repository.mapper")
@SpringBootApplication
public class NovelApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        System.setProperty("https.protocols", "TLSv1.2");
        SpringApplication.run(NovelApplication.class, args);
    }
    
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
}
