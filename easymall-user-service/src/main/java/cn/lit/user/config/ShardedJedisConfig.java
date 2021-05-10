package cn.lit.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "redis")
public class ShardedJedisConfig {
    //准备多个为构造bean对象使用的属性们
    private List<String> nodes;//{"10.9.104.184:6379","",""}
    //连接池配置
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;

    //初始化方法构造一个连接池对象返回
    @Bean
    public ShardedJedisPool initPool(){
        //收集节点信息
        List<JedisShardInfo> infoList
                =new ArrayList<JedisShardInfo>();
        //解析ip port
        for(String node:nodes){
            //10.9.104.184:6379
            String host=node.split(":")[0];
            int port=Integer.parseInt(node.split(":")[1]);
            infoList.add(new JedisShardInfo(host,port));
        }
        //连接池配置对象
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setMaxIdle(maxIdle);
        return new ShardedJedisPool(config,infoList);
    }
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }
}
