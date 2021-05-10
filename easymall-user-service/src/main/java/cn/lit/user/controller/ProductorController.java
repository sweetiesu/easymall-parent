package cn.lit.user.controller;

import cn.lit.StarterUser;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
    通过访问该controller url接口地址
    进行一次将参数发送到rabbitmq的操作
 */
@RestController
public class ProductorController {
    //localhost:10003/send?msg=消息
    @Autowired
    private RabbitTemplate client;
    @RequestMapping("send")
    public String send(String msg){
        //调用
        //Message 对象 byte[] properties(content_type priority)
        //消费端 关注的不仅是消息体,接收properties
       /* MessageProperties props=new MessageProperties();
        props.setPriority(100);
        Message message=new Message(msg.getBytes(),props);
        client.send(StarterUser.ex,StarterUser.routingKey,message);
        *///不关心properties 可以将对象product
        client.convertAndSend(StarterUser.ex,StarterUser.routingKey,msg);

        return "成功";
    }
}
