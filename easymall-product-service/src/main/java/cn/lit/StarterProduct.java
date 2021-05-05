package cn.lit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.lit.product.mapper")
public class StarterProduct {
    public static void main(String[] args) {
        SpringApplication.run(StarterProduct.class,args);
    }
}
