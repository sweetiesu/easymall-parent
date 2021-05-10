package cn.lit.user.controller;

import cn.lit.user.util.RedisClusterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
public class ClusterController {
    @Autowired
    private JedisCluster cluster;
    @Autowired
    private RedisClusterUtils utils;
    //操作redis-cluster实现数据增删查改
    @RequestMapping("cluster")
    public String setAndGet(String key,String value){
        //cluster set/get
        //name wanglaoshi
       /* cluster.set(key,value);
        return cluster.get(key);*/
       utils.addAndUpdate(key,value);
       return utils.readValue(key);
        //wanglaoshi
    }
}
