package cn.lit.cart.service;

import cn.lit.cart.mapper.CartMapper;
import com.jt.common.pojo.Cart;
import com.jt.common.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class CartService {
    @Autowired
    private CartMapper cm;
    public List<Cart> queryMyCarts(String userId) {
        return cm.selectCartsByUserId(userId);
    }
    @Autowired
    private RestTemplate client;
    public void addCart(Cart cart) {
        /*1 判断当前用户是否添加过这个商品到购物车
            查询数据库 userId productId
          2 exist==null:说明新增 insert
          3 exist!=null: 将久num+新num更新到数据库
         */
        Cart exist=cm.selectExistCart(cart);
        //if判断
        if(exist==null){
            //TODO 调用商品微服务完成数据封装
            //缺少3个字段 image name price 商品系统访问单个商品查询
            Product product = client.getForObject("http://productservice/product/manage/item/" + cart.getProductId(),
                    Product.class);
            cart.setProductImage(product.getProductImgurl());
            cart.setProductName(product.getProductName());
            cart.setProductPrice(product.getProductPrice());
            cm.insertCart(cart);
        }else{
            //非空说明更新num
            //更新内存对象num
            cart.setNum(exist.getNum()+cart.getNum());
            //持久层更新 update t-cart set num=#{num}
            //where userId productI
            cm.updateNumByCart(cart);
        }
    }

    public void addOrUpdateNum(Cart cart) {
        cm.updateNumByCart(cart);
    }

    public void deleteCart(Cart cart) {
        cm.deleteCart(cart);
    }
}
