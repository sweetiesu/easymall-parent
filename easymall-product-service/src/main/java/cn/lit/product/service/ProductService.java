package cn.lit.product.service;

import cn.lit.product.mapper.ProductMapper;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import com.jt.common.vo.EasyUIResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductMapper pm;
    public EasyUIResult queryPageProduct(Integer page, Integer rows) {
        //准备一个返回对象
        EasyUIResult result=new EasyUIResult();
        //封装total 需要从数据库查询 count值
        Integer total= pm.selectProductCount();
        result.setTotal(total);
        //封装返回分页数据rows List<Product> 就是通过分页
        //查询sql查询的结果集
        Integer start=(page-1)*rows;
        List<Product> pList=pm.selectProductsByPage(start,rows);
        result.setRows(pList);
        return result;
    }
    @Autowired
    private JedisCluster cluster;
    public Product queryOneProduct(String productId) {
        //缓存逻辑
        //准备一个操作缓存数据的key值
        String prodKey="product_query_"+productId;
        //判断是否有更新的锁
        String prodLock="product_query_"+productId+".lock";
        if(cluster.exists(prodLock)){
            //说明有人更新,不需要操作缓存redis
            return pm.selectProductById(productId);
        }
        //判断缓存redis存在
        try{
            if(cluster.exists(prodKey)){
                //说明存在缓存数据
                String pJson=cluster.get(prodKey);
                //转化为对象
               Product p= MapperUtil.MP.readValue(pJson,Product.class);
                return p;
            }else{
                //缓存没有数据
                Product p = pm.selectProductById(productId);
                //生成商品对象的缓存value存储缓存
                String pJson=MapperUtil.MP.writeValueAsString(p);
                cluster.set(prodKey,pJson);
                return p;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }


    }

    public void addProduct(Product product) {
        //没有商品id uuid实现
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);
        //商品数据加入缓存
        try{
            String prodKey="product_query_"+productId;
            String pJson=MapperUtil.MP.writeValueAsString(product);
            cluster.set(prodKey,pJson);
        }catch(Exception e){
            e.printStackTrace();
        }
        //添加数据库
        pm.insertProduct(product);//

    }

    public void updateProduct(Product product) {
        //删除原有缓存
        String prodKey="product_query_"+product.getProductId();
        String prodLock="product_query_"+product.getProductId()+".lock";
        cluster.set(prodLock,"");
        cluster.del(prodKey);
        pm.updateProductById(product);
        cluster.del(prodLock);
    }
}
