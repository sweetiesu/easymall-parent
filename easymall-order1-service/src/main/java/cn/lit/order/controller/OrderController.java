package cn.lit.order.controller;

import cn.lit.order.service.OrderService;
import com.jt.common.pojo.Order;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("order/manage")
public class OrderController {
        /*
        新增订单数据
         */
        @Autowired
        private OrderService os;
        @RequestMapping("save")
        public SysResult addOrder(Order order){
            try{
                os.addOrder(order);
                return SysResult.ok();
            }catch(Exception e){
                e.printStackTrace();
                return SysResult.build(201,"新增订单失败",null);
            }
        }
        //查询我的订单
    @RequestMapping("query/{userId}")
    public List<Order> queryMyOrders(@PathVariable String userId){
            return os.queryMyOrders(userId);
    }
    //订单删除
    @RequestMapping("delete/{orderId}")
    public SysResult deleteOrder(@PathVariable String orderId){
            os.deleteOrder(orderId);
            return SysResult.ok();
    }
}
