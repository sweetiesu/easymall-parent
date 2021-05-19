package cn.lit.order.service;

import cn.lit.order.mapper.OrderMapper;
import com.jt.common.pojo.Order;
import com.jt.common.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderMapper om;
    public void addOrder(Order order) {
        //补齐数据
        order.setOrderId(UUID.randomUUID().toString());
        order.setOrderPaystate(0);//0w未支付
        order.setOrderTime(new Date());

        om.insertOrderAndOrderItem(order);
    }

    public List<Order> queryMyOrders(String userId) {
        System.out.println("进入查询");
        List<Order> oList=om.queryMyorder(userId);
        return oList;
    }

    public void deleteOrder(String orderId) {
        om.deleteOrderById(orderId);
    }
}
