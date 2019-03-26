package com.potato369.novel.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <pre>
 * @PackageName com.potato369.novel.service.impl
 * @ClassName WebSocket
 * @Desc WebSocket服务
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/01/16 15:19
 * @CreateBy IntellJ IDEA 2018.3.3
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
@Component
@Slf4j
@ServerEndpoint(value = "/webSocket")
public class WebSocket {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据。
    private Session session;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    /**
     * @Title: onOpen
     * @Description 连接建立成功调用的方法
     * @param session
     * @return void 返回值类型
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            this.session = session;
            webSocketSet.add(this);
            if (log.isDebugEnabled()) {
                log.debug("【WebSocket消息】有新的连接，连接总数：{}。", webSocketSet.size());
            }
        } catch (Exception e) {
            log.error("【WebSocket消息】有新的连接，将其添加到WebSocket出现错误。", e);
        }
    }

    /**
     * @Title onClose
     * @Description 连接关闭调用方法
     * @return void返回值类型
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

    /**
     * @Title onMessage
     * @Description 收到客户端消息后调用的方法
     * @param message 客户端消息
     * @return void返回值类型
     */
    @OnMessage
    public void onMessage(String message) {
        if (log.isDebugEnabled()) {
            log.debug("【WebSocket消息】收到客户端发来的消息：{}。", message);
        }
    }

    /**
     * @Title onError
     * @Description WebSocket发生错误时调用
     * @param session
     * @param error
     * @return void 返回值类型
     */
    @OnError
    public void onError(Session session, Throwable error) {
        try {
            this.session = session;
            this.session.close();
        } catch (Exception e) {
            log.error("【WebSocket消息】WebSocket关闭session出现错误。", error);
        }
    }

    /**
     * @Title: sendMessage
     * @Description 群发 WebSocket自定义消息
     * @param message 传入参数
     * @return void 返回值类型
     */
    public void sendMessage(String message) {
        for (WebSocket webSocket : webSocketSet) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("【WebSocket消息】广播消息：message={}。", message);
                }
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("【WebSocket消息】广播消息出现错误。", e);
            }
        }
    }
}
