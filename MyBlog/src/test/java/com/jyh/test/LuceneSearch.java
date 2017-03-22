package com.jyh.test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jyh.domain.Article;

public class LuceneSearch {
	/**
     * 测试搜索
     * @throws Exception
     */
    @Test
    public void testSearch() throws Exception {
        //1 搜索条件
        String queryCondition="标题";
        //2 执行搜索(lucene)
        List<Article> articles=new ArrayList<Article>();
        //--------------------------搜索代码-----------------------------
        Directory directory=FSDirectory.open(Paths.get("./indexDir/"));  //索引库目录
        //Analyzer analyzer=new StandardAnalyzer();        //分词器
        Analyzer analyzer=new IKAnalyzer();
        //2.1 把查询字符串转为Query对象(只在title中查询)
        QueryParser queryParser=new QueryParser("title",analyzer);
        Query query=queryParser.parse(queryCondition);

        //2.2 执行搜索得到结果
        IndexReader indexReader=DirectoryReader.open(directory);
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
        TopDocs topDocs= indexSearcher.search(query, 100); //返回查询出来的前n条结果

        Integer count= topDocs.totalHits; //总结果数量
        ScoreDoc[] scoreDocs=topDocs.scoreDocs;  //返回前N条结果信息

        //2.3 处理结果
        for (int i = 0; i < scoreDocs.length; i++) {
            ScoreDoc scoreDoc=scoreDocs[i];
            int docId=scoreDoc.doc;
            System.out.println("得分是："+scoreDoc.score+"，内部编号是："+docId);
            //根据内部编号取出真正的Document数据
            Document doc=indexSearcher.doc(docId);
            //将document转化为Article
            Article article = new Article();
            article.setArticleId(doc.get("id"));
            article.setArticleTitle(doc.get("title"));
            article.setArticleSummary(doc.get("content"));
            articles.add(article);            
        }
        //------------------------------------------------------------
        //3  控制台显示结果
        System.err.println("总结果数："+count);
        for (Article article : articles) {
            System.out.println("查询结果为："+article.getArticleTitle());
        }
        indexSearcher.getIndexReader().close();
    }
}
