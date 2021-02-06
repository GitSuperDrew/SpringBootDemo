package edu.study.module.springbootwebsocket.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author drew
 * @date 2021/2/6 10:56
 **/
@Component
@ServerEndpoint(value = "/webSocket/{sid}")
public class WebSocketServer {

    private static AtomicInteger onlineNum = new AtomicInteger();

    private static ConcurrentMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息主题
     *
     * @param session 会话对象
     * @param message 消息内容
     * @throws IOException
     */
    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * 给指定用户发送信息
     *
     * @param userName 用户名
     * @param message  信息内容
     */
    public void sendInfo(String userName, String message) {
        Session session = sessionPools.get(userName);
        try {
            sendMessage(session, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接成功调用
     *
     * @param session  会话对象
     * @param userName 消息内容
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName) {
        sessionPools.put(userName, session);
        addOnlineCount();
        System.out.println(userName + "加入webSocket！当前人数为" + onlineNum);
        try {
            sendMessage(session, "欢迎" + userName + "加入连接！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接时调用
     *
     * @param userName 用户名
     */
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName) {
        sessionPools.remove(userName);
        subOnlineCount();
        System.out.println(userName + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 收到客户端信息
     *
     * @param message 消息内容
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message) throws IOException {
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Session session : sessionPools.values()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 错误时调用
     *
     * @param session   会话对象
     * @param throwable 异常信息
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生错误");
        throwable.printStackTrace();
    }

    public static void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    public static void subOnlineCount() {
        onlineNum.decrementAndGet();
    }
}
