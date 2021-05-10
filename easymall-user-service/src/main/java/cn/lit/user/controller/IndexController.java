package cn.lit.user.controller;

import com.jt.common.vo.SysResult;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class IndexController {
    //localhost:10003/create/index01
    @RequestMapping("create/{indexName}")
    public SysResult createIndex(@PathVariable String indexName) throws Exception {
        //org.apache.lucene
        //索引创建的目录
        Path path = Paths.get("d://"+indexName);
        FSDirectory dir = FSDirectory.open(path);
        //创建writer输出流 输出数据时,需要计算分词
        //配置对象,绑定一个分词器,计算分词指定分词器
        IndexWriterConfig config=
                new IndexWriterConfig(new StandardAnalyzer());
        //指定创建索引的模式: 覆盖创建,追加,覆盖或追加
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        //创建输出对象
        IndexWriter writer=new IndexWriter(dir,config);
        //封装document 构造几个有数据的document对象
        Document doc1=new Document();
        //封装一些属性 新闻网页,商品数据,用户数据
        //title string fieldName:域属性名称
        //value:域属性值
        //store:定义该数据是否存储在索引文件数据中
        doc1.add(new TextField("title",
                "国足大战印度", Field.Store.YES));
        //publisher string
        doc1.add(new TextField("publisher",
                "八卦新闻", Field.Store.YES));
        //content string
        doc1.add(new TextField("content",
                "文档数据3", Field.Store.YES));
        //rate Integer 域属性名,和int类型数据值
        doc1.add(new IntPoint("price",3500));
        //显示3500元
        //doc1.add(new StringField("price","3500元", Field.Store.YES));
        //doc1.add(new StringField("priceView","3500元", Field.Store.YES));
        //address string
        doc1.add(new StringField("address","we are family,we are world", Field.Store.YES));
        //域属性类型有哪些,区别是什么?
        //store.yes/no到底有什么不同
        Document doc2=new Document();
        doc2.add(new TextField("title","中国国足大战梵蒂冈", Field.Store.YES));
        doc2.add(new TextField("publisher","体育娱乐", Field.Store.YES));
        doc2.add(new TextField("content","国足惨败梵蒂冈", Field.Store.YES));
        doc2.add(new TextField("address","http://www.image.com/", Field.Store.YES));
        doc2.add(new IntPoint("price",2500));
        //创建索引文件
        //将document添加到writer中输出
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.commit();
        return SysResult.ok();
    }
    @RequestMapping("search/{indexName}/{field}/{text}")
    public String searchIndex(@PathVariable String indexName,
                              @PathVariable String field,@PathVariable
                              String text) throws Exception {
        Path path = Paths.get("d://"+indexName);
        FSDirectory dir = FSDirectory.open(path);
        //获取输入流reader
        DirectoryReader reader = DirectoryReader.open(dir);
        //创建搜索对象
        IndexSearcher search=new IndexSearcher(reader);
        //封装一个查询条件TermQuery
        Term term=new Term(field,text);
        Query query=new TermQuery(term);
        //搜索方法调用,遍历结果集
        TopDocs topDoc = search.search(query, 10);//浅查询搜索前50条数据
        //topDoc没有document对象数据 当前查询条件
        //对应计算结果的 总数
        System.out.println("查询结果总数:"+topDoc.totalHits);
        //最大评分数,根据不同查询条件,匹配度不同,评分结果不同 匹配度
        //越高评分越高
        //获取topDoc根据查询范围5获取的docId
        ScoreDoc[] scores=topDoc.scoreDocs;//最多是5个
        String docs="";
        for(ScoreDoc scoreDoc:scores){
            docs=docs+search.doc(scoreDoc.doc).get("title");
            docs=docs+search.doc(scoreDoc.doc).get("content");

        }
        return docs;
    }
}
