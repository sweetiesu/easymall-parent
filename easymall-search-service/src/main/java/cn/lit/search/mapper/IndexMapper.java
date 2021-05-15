package cn.lit.search.mapper;

import com.jt.common.pojo.Product;

import java.util.List;

public interface IndexMapper {
    List<Product> selectProduct();
}
