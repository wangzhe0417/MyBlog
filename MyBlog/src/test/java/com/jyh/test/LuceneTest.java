package com.jyh.test;

import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jyh.domain.Article;
import com.jyh.utils.LuceneUtil;

public class LuceneTest {
	/**
	
	     * 为文章建立索引
	
	     * @throws IOException 
	
	     */
	
	    @Test
	
	    public void testCreateIndex() throws Exception {
	
	        //1  将需要添加的实体构造成实体对象
	
	        Article article=new Article();
	        article.setArticleId("asdfsf");
	        article.setArticleSummary("afsd");
	        article.setArticleTitle("标题");
	        article.setArticleDate(Calendar.getInstance().getTime());
	
	        //2 保存到数据库(此步骤暂时省略)
	
	        //3 建立索引(lucene)
	
	        Directory directory=FSDirectory.open(Paths.get("./indexDir/"));  //索引库目录
	
	        Analyzer analyzer=new IKAnalyzer();        //分词器
	
	        IndexWriterConfig iwc= new IndexWriterConfig(analyzer);
	
	        // >>3.1   将Article转为Document
	
	        /** Store参数说明
	
	            No 本字段的原始值不存储
	
	            YES 本字段的原始值会存在出在数据库区中
	
	        如果不存在，则搜索出来的结果中这个字段的值为null */
	
	        /** 
	
	         * 自Lucene4开始 创建field对象使用不同的类型 只需要指定是否需要保存源数据 不需指定分词类别  
	
	         * 之前版本的写法如下  
	
	         * doc.Add(new Field("id", article.id.ToString(), Field.Store.YES, Field.Index.ANALYZED)); 
	
	         */
	
	        Document doc=new Document();
	
	        doc.add(new TextField("id", article.getArticleId().toString(), Store.YES));
	
	        doc.add(new TextField("title", article.getArticleTitle(), Store.YES));
	
	        doc.add(new TextField("content", article.getArticleSummary(), Store.YES));
	
	        // >>3.2 保存到索引库中
	
	        IndexWriter indexWriter=new IndexWriter(directory,iwc);
	        indexWriter.addDocument(doc);
	
	        indexWriter.close();  //释放资源
	
	    }
	    
	    @Test
	    public void testAdd(){
	    	Article article = new Article();
	    	article.setArticleId("nnnn");
	    	article.setArticleTitle("title");
	    	article.setArticleSummary("title");
	    	article.setTag("tag");
	    	article.setArticleDate(Calendar.getInstance().getTime());
	    	//article.setArticleDate(Calendar.getInstance().getTime());
	    	
	    	try {
				LuceneUtil.add(article);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    @Test
	    public void testSearch(){
	    	List<Article> articles = new ArrayList<Article>();
	    	try {
				articles = LuceneUtil.search("title");
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	for (Article article : articles) {
				System.out.println(article.getArticleTitle() + "|" + article.getTag());
			}
	    }
	    
	    @Test
	    public void testUpdate(){
	    	Article article = new Article();
	    	article.setArticleId("asdfsf");
	    	article.setArticleTitle("title");
	    	article.setArticleSummary("summary");
	    	try {
				LuceneUtil.update(article);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    @Test
	    public void testDelete(){
	    	try {
				LuceneUtil.delete("402881f25ae2291c015ae22953430000");
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    @Test
	    public void testfo(){
	    	try {
	    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse("2017-03-18");
		    	System.out.println(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    @Test
	    public void deleteAll(){
	    	try {
				LuceneUtil.deleteAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}

