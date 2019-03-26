package com.potato369.novel.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * <pre>
 * @PackageName com.potato369.novel.conf
 * @ClassName WebSocketConfig
 * @Desc WebSocket配置
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/16 15:16
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Component
public class WebSocketConfiguration {
    /**
     * 使用@ServerEndpoint创立websocket endpoint
     * 首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
     * 要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，就不要注入ServerEndpointExporter，
     * 因为它将由容器自己提供和管理。需要注释掉以下Bean代码
     */
    /**/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
        return serverEndpointExporter;
    }
}
