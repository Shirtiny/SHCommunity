package cn.shirtiny.community.SHcommunity.Controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    //接收消息的接口路径
    @MessageMapping("/sendToRoom")
    //发送到 /room/chat 频道
    @SendTo("/room/chat")
    public String retString(String message){

        return message;
    }

}
