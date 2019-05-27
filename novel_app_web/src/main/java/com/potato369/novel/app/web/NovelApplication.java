package com.potato369.novel.app.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.potato369.novel.basic.service", "com.potato369.novel.app.web.controller"})
@EnableJpaRepositories(basePackages = "com.potato369.novel.basic.repository")
@EntityScan(basePackages = "com.potato369.novel.basic.dataobject")
@MapperScan(basePackages = "com.potato369.novel.basic.repository.mapper")
public class NovelApplication {

	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1.2");
		SpringApplication.run(NovelApplication.class, args);
	}

}
