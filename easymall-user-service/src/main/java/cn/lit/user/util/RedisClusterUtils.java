package cn.lit.user.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
/*
二次封装的工具类,用到哪些cluster方法
就可以在这个类过滤筛选,二次封装
 */
@Component
public class RedisClusterUtils {
    @Autowired
    private JedisCluster cluster;
    //封装当前工程系统需要的方法
    public void addAndUpdate(String key,String value){
        cluster.set(key,value);
    }
    public String readValue(String key){
        return cluster.get(key);
    }
}
