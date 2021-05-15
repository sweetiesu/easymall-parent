package cn.lit.search.service;

import cn.lit.search.mapper.IndexMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jt.common.pojo.Product;
import com.jt.common.utils.MapperUtil;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexService {
    @Autowired
    private IndexMapper im;
    @Autowired
    private TransportClient client;
    public void createIndex(String indexName) throws JsonProcessingException {
        /*
            1判断索引是否存在
            2不存在则创建索引
            3读取数据源得到商品数据
            4 解析成source 设置在request对象中
            5 es接收请求,创建document文档数据
         */
        IndicesExistsResponse response
        = client.admin().indices().
        prepareExists(indexName).get();
        //判断存在
        if(!response.isExists()){
            //说明不存在 创建
            client.admin().indices().
            prepareCreate(indexName).get();
        }
        //整理数据源数据，写入到索引中创建文档
       List<Product> pList= im.selectProduct();
        //循环plist 解析json 写入索引文件
        for(Product p:pList){
            String json = MapperUtil.MP.writeValueAsString(p);
            //document的id只要保证全局唯一即可
            IndexRequestBuilder request1 =
            client.prepareIndex(indexName,
            "product", p.getProductId());
            //封装source 发送请求，写入索引
            request1.setSource(json).get();
        }
    }

    public List<Product> query(String text, Integer page, Integer rows) {
        try{
        //构造查询query对象
        MatchQueryBuilder query = QueryBuilders.matchQuery("productName", text);
        //搜索
        SearchRequestBuilder request = client.prepareSearch("emindex");
        //设置搜索参数
        request.setQuery(query);
        request.setFrom((page-1)*rows);
        request.setSize(rows);
        //发送请求从索引获取数据解析
        SearchResponse response = request.get();
        System.out.println("查询总条数:"+response.getHits().totalHits);
        //获取source元素
        SearchHit[] hits = response.getHits().getHits();
        List<Product> pList=new ArrayList<Product>();
        for(SearchHit hit:hits){
            //获取source as String

            String JSON = hit.getSourceAsString();
            Product p=MapperUtil.MP.readValue(JSON,Product.class);
            pList.add(p);
        }
        return pList;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
