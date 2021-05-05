package cn.lit.product.controller;

import cn.lit.product.service.ProductService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product/manage")
public class ProductController {
    @Autowired
    private ProductService ps;
    //查询商品分页 /product/manage/pageManage
    @RequestMapping("pageManage")
    public EasyUIResult queryPageProduct(
            Integer page,Integer rows){
        //直接控制层调用业务层 获取数据
        return ps.queryPageProduct(page,rows);
    }
    //单个商品查询
    @RequestMapping("item/{productId}")
    public Product queryOneProduct(@PathVariable
                                   String productId){
        return ps.queryOneProduct(productId);
    }
    //新增商品
    @RequestMapping("save")
    //productName=wawa&productPrice=333.00&productCategory=手机
    //hahah=wang
    public SysResult addProduct(Product product){
        //判断新增成功和失败 result int
        //异常捕获 结构模拟和ajax对话
        try {
            //成功返回
            ps.addProduct(product);
            return SysResult.ok();// status=200 msg="ok" data=null；
        }catch (Exception e){
            //新增失败返回
            e.printStackTrace();//给我们控制台看的
            return SysResult.build(201,"新增失败",null);
        }
        //你在工作，编写代码时碰到的异常有哪些？
    }
    //商品数据更新
    @RequestMapping("update")
    public SysResult updateProduct(Product product){
        //参数中具备productId
        try{
            ps.updateProduct(product);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"更新失败",null);
        }
    }


}
