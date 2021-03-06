package cn.shirtiny.community.SHcommunity.Config;

import cn.shirtiny.community.SHcommunity.DTO.UserDTO;
import cn.shirtiny.community.SHcommunity.Service.IchatMessageService;
import cn.shirtiny.community.SHcommunity.Service.IcookieService;
import cn.shirtiny.community.SHcommunity.Service.IjwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.Cookie;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private IjwtService jwtService;
    @Autowired
    private IchatMessageService chatMessageService;
    @Autowired
    private IcookieService cookieService;

    //注册STOMP协议节点并映射url
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //添加服务端接口 终端点 js连接: let socket=new SockJS('/websocket');
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                //握手拦截器
                .addInterceptors(myHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //允许客户端订阅主题/room
        registry.enableSimpleBroker("/room", "/user", "/uid");
        //注册 app的前缀/app
        registry.setApplicationDestinationPrefixes("/app");
        //推送用户前缀 不过默认就是/user
        registry.setUserDestinationPrefix("/user");
    }


    //handler的工厂
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(webSocketHandler -> new WebSocketHandlerDecorator(webSocketHandler) {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                super.afterConnectionEstablished(session);
            }

            private Pattern chatMessageIdReg = Pattern.compile("chatMessageId:([^\n]*)");

            @Override
            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                UserDTO user = (UserDTO) session.getAttributes().get("user");
                System.out.println(user + "\n正在发送一条消息：" + message.getPayload());
                String payload = (String) message.getPayload();
                //如果stomp帧是ACK,载荷字符串以ACK开头
                boolean isACK = payload.startsWith("ACK");
                if (isACK) {
                    System.out.println("这是ack");
                    Matcher matcher = chatMessageIdReg.matcher(payload);
                    while (matcher.find()) {
                        String chatMessageId = matcher.group(1);
                        System.out.println("消息id匹配结果：" + chatMessageId);
                        //更新消息为已读状态
                        chatMessageService.updateMessageRead(chatMessageId, true);
                    }
                }
                //连接帧
                boolean isCONNECT = payload.startsWith("CONNECT");
                if (user == null && isCONNECT) {
                    //检查自定head头，查看是否是聊天频道
                    boolean matchChatRoomChannel = payload.matches("[\\s\\S]+\nchannel:/room/chat\n[\\s\\S]+");
                    //查看是否含有游客id
                    boolean matchTouristId = payload.matches("[\\s\\S]+\ntouristId:\\d+\n[\\s\\S]+");
                    System.out.println("是否是聊天室频道："+matchChatRoomChannel+"；是否含有游客id："+matchTouristId);
                    if (matchChatRoomChannel && matchTouristId){
                        super.handleMessage(session, message);
                    }else {
                        //不是有效的用户登录，要使用的频道不是聊天室，也没有游客id，就关闭session
                        System.out.println("用户无效 ，关闭session");
                        session.close();
                    }
                } else {
//                    session.sendMessage(new TextMessage("用户有效"));
                    super.handleMessage(session, message);
                }
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                session.sendMessage(new TextMessage("出错了"));
                session.sendMessage(new TextMessage(exception.getMessage()));
                super.handleTransportError(session, exception);
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                super.afterConnectionClosed(session, closeStatus);
            }
        });
    }

    /**
     * @Bean WebSocket 握手拦截器 从cookie中解析jwt
     */
    private HandshakeInterceptor myHandshakeInterceptor() {
        return new HandshakeInterceptor() {

            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
                Cookie[] cookies = servletServerHttpRequest.getServletRequest().getCookies();
                if (cookies == null || cookies.length == 0) {
                    return false;
                }
                UserDTO userDTO = null;
                //从cookie中解析jwt
                String jwt = cookieService.getShJwtFromCookie(cookies);
                userDTO = jwtService.parseJwtToUser(jwt);
                if (userDTO != null) {
                    //这里的map里的值会交给websocketSession Message对象里header里会有session的值
                    map.put("user", userDTO);
                }
//                return userDTO!=null;
                //不管怎么样都握手完成 交给handler处理无效用户 为了处理不停握手的问题
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
                System.out.println("握手成功");
            }
        };
    }

}
