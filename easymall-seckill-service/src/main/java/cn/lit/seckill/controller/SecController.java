package cn.lit.seckill.controller;

import cn.lit.StarterSeckill;
import cn.lit.seckill.mapper.SecMapper;
import com.jt.common.pojo.Seckill;
import com.jt.common.vo.SysResult;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("seckill/manage")
public class SecController {
    @Autowired
    private SecMapper secM;
    //查询秒杀商品list
    @RequestMapping("list")
    public List<Seckill> list(){
        return secM.selectAll();
    }
    //查询某个具体秒杀的商品
    @RequestMapping("detail")
    public Seckill queryOne(Long seckillId){
       return secM.selectOne(seckillId);
    }
    @Autowired
    private RabbitTemplate client;
    @RequestMapping("{seckillId}")
    public SysResult startSeckill(@PathVariable Long seckillId){
        //解析参数，封装msg消息。发送消息
        String userPhone="1338899"+ RandomUtils.nextInt(1000,9999);
        String msg=userPhone+"/"+seckillId;//"1333**/2"
        try{
            client.convertAndSend(StarterSeckill.ex,StarterSeckill.routingKey,msg);

        }catch(Exception e){
            e.printStackTrace();
        }
        return SysResult.ok();
    }
}
