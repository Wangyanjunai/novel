package com.potato369.novel.basic;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * @PackageName com.potato369.novel.basic
 * @ClassName TestProtocols
 * @Desc 类实现的功能点描述
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019-04-29 22:36
 * @CreateBy IntelliJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Slf4j
public class TestProtocols {
    public static void main(String[] args) {
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, null, null);
            SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket();
            String[] protocols = socket.getSupportedProtocols();
            System.out.println("Supported Protocols: " + protocols.length);
            for (int i = 0; i < protocols.length; i++) {
                System.out.println(" " + protocols[i]);
            }
            protocols = socket.getEnabledProtocols();
            System.out.println("Enabled Protocols: " + protocols.length);
            for (int i = 0; i < protocols.length; i++) {
                System.out.println(" " + protocols[i]);
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
