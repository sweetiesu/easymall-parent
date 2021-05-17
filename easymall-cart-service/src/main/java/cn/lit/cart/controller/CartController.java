package cn.lit.cart.controller;

import cn.lit.cart.service.CartService;
import com.jt.common.pojo.Cart;
import com.jt.common.vo.SysResult;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cart/manage")
public class CartController {
    @Autowired
    private CartService cs;
        //查询我的购物车所有商品
    @RequestMapping("query")
    public List<Cart> queryMyCarts(String userId){
        return cs.queryMyCarts(userId);
    }
    //新增购物车
    @RequestMapping("save")
    public SysResult addCart(Cart cart){
        try{
            cs.addCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"fail",null);
        }
    }
    //更新一个商品购物车num
    @RequestMapping("update")
    public SysResult addOrUpdateNum(Cart cart){
        //userId productId num
        try{
            cs.addOrUpdateNum(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"",null);
        }
    }
    @RequestMapping("delete")
    public SysResult deleteCart(Cart cart){
        //userId productId
        //delete from t_cart where userId productId
        try{
            cs.deleteCart(cart);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"",null);
        }
    }
}
