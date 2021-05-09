package cn.lit;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import redis.clients.jedis.Jedis;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.lit.user.mapper")
public class StarterUser {
    public static void main(String[] args) {
        SpringApplication.run(StarterUser.class,args);
    }
    /*@Bean
    public Jedis initJedis(){
        return new Jedis("10.9.39.13",6379);
    }*/
    //声明当前工程使用的队列交换机和绑定关系
    public static final String ex="seckillEx";//秒杀使用的交换机
    public static final String queue="seckillQ";//秒杀使用的队列
    public static final String routingKey="seckill";
    //声明队列
    @Bean//创建一个queue对象,在工程第一次使用rabbitmq时
    //spring容器提供这个对象创建声明队列
    //org.springframework.amqp.core
    public Queue queue01(){
        return new Queue(queue,false,false,false,null);//底层使用对象属性,queueDeclare
    }
    //声明交换机
    //springboot的整合代码,封装了创建交换机的对象
    //DirectEx FanoutEx TopicEX
    @Bean
    public DirectExchange ex(){
        return new DirectExchange(ex);//底层调用exDeclare
    }
    //绑定关系
    @Bean
    public Binding bind(){
        //绑定 seckillQ 域seckillEx的路由key seckill
        return
            BindingBuilder.bind(queue01()).to(ex()).with(routingKey);
        //channel.queueBind(queue,ex,routingKey)
    }

}
