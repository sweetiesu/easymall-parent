package cn.lit.user.comsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Comsumer {

    //当前类中,编写消费逻辑
    //可以在方法的参数中,接收消息
    @RabbitListener(queues = {"seckillQ"})
    public void comsum(String msg){
        //msg==生产端发送的消息
        System.out.println("消费者接收:"+msg);
    }
}
