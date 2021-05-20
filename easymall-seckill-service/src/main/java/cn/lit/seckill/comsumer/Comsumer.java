package cn.lit.seckill.comsumer;

import cn.lit.seckill.mapper.SecMapper;
import com.jt.common.pojo.Success;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Component
public class Comsumer {
    @Autowired
    private SecMapper secM;
    //当前类中,编写消费逻辑
    //可以在方法的参数中,接收消息
    @Autowired
    private JedisCluster cluster;
    @RabbitListener(queues = {"seckillQ"})
    public void comsum(String msg){
        //msg=13388992728/1
        //解析出来属性userPhone seckillId
        Long userPhone=Long.parseLong(msg.split("/")[0]);
        Long seckillId=Long.parseLong(msg.split("/")[1]);
        //减库存 seckill 对应的商品num update语句条件判断减库存
        //利用两个参数 now() seckillId
        //添加redis的逻辑
        if(cluster.exists("oppo_seckill.lock")){
            System.out.println("还没开始卖秒杀商品");
            return;
        }
        //从list获取rpop
        String comp = cluster.rpop("oppo_list_sell");
        //判断具备不具备减库存资格
        if(comp==null){
            System.out.println("该用户:"+userPhone+";秒杀失败,因为卖完了");
            return;
        }
        //没卖完
        int result=secM.updateNumber(seckillId);
            //减库存成功result==1
        if(result!=1){
            //减库存不成功,秒杀失败
            System.out.println("该用户:"+userPhone+";秒杀失败");
            return;
        }
        //result==1 秒杀成功 在success 记录成功
        // insert into sucess 记录userPhone seckillId
        Success suc=new Success();//SUCCESS添加一个字段
            suc.setUserPhone(userPhone);
            suc.setSeckillId(seckillId);
            suc.setCreateTime(new Date());
            suc.setState(0);//表示复杂的成功状态
        secM.insertSuc(suc);


    }
}
