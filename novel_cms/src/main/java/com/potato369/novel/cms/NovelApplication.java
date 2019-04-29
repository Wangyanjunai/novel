package com.potato369.novel.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NovelApplication {

	public static void main(String[] args) {
		System.setProperty("https.protocols", "TLSv1.2");
		SpringApplication.run(NovelApplication.class, args);
	}
}
