package cn.lit.order.mapper;

import com.jt.common.pojo.Order;

import java.util.List;

public interface OrderMapper {
    void insertOrderAndOrderItem(Order order);

    List<Order> selectOrdersByUserId(String userId);

    void deleteOrderById(String orderId);
}
