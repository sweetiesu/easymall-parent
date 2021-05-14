package cn.lit;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.lit.search.mapper")
public class StarterSearch {
    public static void main(String[] args) {
        SpringApplication.run(StarterSearch.class,args);
    }
}
