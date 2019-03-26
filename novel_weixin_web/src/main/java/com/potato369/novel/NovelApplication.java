package com.potato369.novel;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.InMemoryTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.MultipartConfigElement;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class NovelApplication extends SpringBootServletInitializer{

    public static void main(String[] args) {
        SpringApplication.run(NovelApplication.class, args);
    }
    
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}

	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        multipartConfigFactory.setFileSizeThreshold(1024*1024);
        multipartConfigFactory.setLocation("/");
        return multipartConfigFactory.createMultipartConfig();
    }

    @Bean
    public InMemoryTraceRepository traceRepository() {
        InMemoryTraceRepository traceIRepo = new InMemoryTraceRepository();
        traceIRepo.setCapacity(10000);
        traceIRepo.setReverse(false);
        return traceIRepo;
    }

    /**
     * tomcatEmbedded这段代码是为了解决，上传文件大于10M出现连接重置的问题。
     * 此异常内容GlobalException也捕获不到。
     * @return
     */
    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
        TomcatEmbeddedServletContainerFactory tomcatFactory = new TomcatEmbeddedServletContainerFactory();
        tomcatFactory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                ((AbstractHttp11Protocol<?>)connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcatFactory;
    };
}

