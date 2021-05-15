package cn.lit.search.controller;

import cn.lit.search.service.IndexService;
import com.jt.common.pojo.Product;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search/manage")
public class IndexControlelr {
    @Autowired
    private IndexService is;
    //访问该接口,创建一次索引文件
    //如果 想要索引中document数据具备ik分词器使用能力
    //先创建索引 使用mapping 设置ik
    @RequestMapping("create/{indexName}")
    public SysResult creatIndex(@PathVariable String
                                indexName){
        try{
            is.createIndex(indexName);
            return SysResult.ok();
        }catch(Exception e){
            e.printStackTrace();
            return SysResult.build(201,"",null);
        }
    }
    //实现match_query搜索功能
    @RequestMapping("query")
    public List<Product> query(String query,
                               Integer page, Integer rows){
        return is.query(query,page,rows);
    }
}
