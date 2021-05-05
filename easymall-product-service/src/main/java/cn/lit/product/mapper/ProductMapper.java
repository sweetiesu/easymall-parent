package cn.lit.product.mapper;

import com.jt.common.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    Integer selectProductCount();
    //select * from t_product limit #{start},#{rows}
    List<Product> selectProductsByPage(@Param("start") Integer start, @Param("rows") Integer rows);

    Product selectProductById(String productId);

    void insertProduct(Product product);

    void updateProductById(Product product);
}
