package cn.lit.cart.mapper;

import com.jt.common.pojo.Cart;

import java.util.List;

public interface CartMapper {
    List<Cart> selectCartsByUserId(String userId);

    Cart selectExistCart(Cart cart);

    void insertCart(Cart cart);

    void updateNumByCart(Cart cart);

    void deleteCart(Cart cart);
}
