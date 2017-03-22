package com.jyh.utils;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.directory.SearchControls;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.vectorhighlight.BaseFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.search.vectorhighlight.FieldQuery;
import org.apache.lucene.search.vectorhighlight.FragListBuilder;
import org.apache.lucene.search.vectorhighlight.FragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.ScoreOrderFragmentsBuilder;
import org.apache.lucene.search.vectorhighlight.SimpleFragListBuilder;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.jyh.domain.Article;

@SuppressWarnings("all")
public class LuceneUtil {

	private static Directory directory; // 索引库目录

	private static Analyzer analyzer; // 分词器

	static {
		try {
			directory = FSDirectory.open(Paths.get("../indexDir"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		analyzer = new IKAnalyzer();
	}

	public static List<Article> search(String queryCondition) throws Exception {
		// 执行搜索(lucene)
		List<Article> articles = new ArrayList<Article>();

		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		// 把查询字符串转为Query对象(只在title中查询)
		QueryParser titleQueryParser = new QueryParser("title", analyzer);
		Query titleQuery = titleQueryParser.parse(queryCondition);

		QueryParser summaryQueryParser = new QueryParser("summary", analyzer);
		Query summaryQuery = summaryQueryParser.parse(queryCondition);

		QueryParser tagQueryParser = new QueryParser("tag", analyzer);
		Query tagQuery = tagQueryParser.parse(queryCondition);

		builder.add(titleQuery, Occur.SHOULD);
		builder.add(summaryQuery, Occur.SHOULD);
		builder.add(tagQuery, Occur.SHOULD);
		// 执行搜索得到结果
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs topDocs = indexSearcher.search(builder.build(), 100); // 返回查询出来的前n条结果

		Integer count = topDocs.totalHits; // 总结果数量

		ScoreDoc[] scoreDocs = topDocs.scoreDocs; // 返回前N条结果信息
		// 排序
		Sort sort = new Sort(new SortField("date", SortField.Type.STRING));

		// 高亮设置
		Analyzer analyzer = new IKAnalyzer();// 设定分词器
		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter(
				"<font color='red'>", "</font>");// 设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter,
				new QueryScorer(builder.build()));
		highlighter.setTextFragmenter(new SimpleFragmenter(150));// 设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
		for (int i = 0; i < scoreDocs.length; i++) {
			ScoreDoc scoreDoc = scoreDocs[i];
			int docId = scoreDoc.doc;
			// System.out.println("得分是："+scoreDoc.score+"，内部编号是："+docId);
			// 根据内部编号取出真正的Document数据
			Document doc = indexSearcher.doc(docId);
			// 将document转化为Article
			Article article = new Article();
			article.setArticleId(doc.get("id"));
			Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(doc
					.get("date"));
			article.setArticleDate(date);

			TokenStream titleStream = analyzer.tokenStream("title",
					new StringReader(doc.get("title")));
			String titleString = highlighter.getBestFragment(titleStream,
					doc.get("title"));

			TokenStream tagStream = analyzer.tokenStream("tag",
					new StringReader(doc.get("tag")));
			String tagString = highlighter.getBestFragment(tagStream,
					doc.get("tag"));

			TokenStream summaryStream = analyzer.tokenStream("summary",
					new StringReader(doc.get("summary")));
			String summaryString = highlighter.getBestFragment(summaryStream,
					doc.get("summary"));

			article.setArticleTitle(titleString == null ? doc.get("title")
					: titleString);
			article.setTag(tagString == null ? doc.get("tag") : tagString);
			article.setArticleSummary(summaryString == null ? doc
					.get("summary") : summaryString);
			articles.add(article);
		}
		return articles;
	}

	public static void add(Article article) throws Exception {
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		Document doc = new Document();

		doc.add(new TextField("id", article.getArticleId().toString(),
				Store.YES));

		doc.add(new TextField("title", article.getArticleTitle(), Store.YES));

		doc.add(new TextField("tag", article.getTag(), Store.YES));

		doc.add(new TextField("summary", article.getArticleSummary(), Store.YES));

		String d = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(article
				.getArticleDate());
		doc.add(new TextField("date", d, Store.YES));

		// >>3.2 保存到索引库中

		IndexWriter indexWriter = new IndexWriter(directory, iwc);
		indexWriter.addDocument(doc);

		indexWriter.close(); // 释放资源
	}

	public static void update(Article article) throws Exception {
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		Document doc = new Document();

		doc.add(new TextField("id", article.getArticleId().toString(),
				Store.YES));

		doc.add(new TextField("title", article.getArticleTitle(), Store.YES));

		doc.add(new TextField("tag", article.getTag(), Store.YES));

		doc.add(new TextField("summary", article.getArticleSummary(), Store.YES));

		String d = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(article
				.getArticleDate());
		doc.add(new TextField("date", d, Store.YES));

		Term term = new Term("id", article.getArticleId());

		IndexWriter indexWriter = new IndexWriter(directory, iwc);
		indexWriter.updateDocument(term, doc);

		indexWriter.close(); // 释放资源
	}

	public static void delete(String articleId) throws Exception {
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		Term term = new Term("id", articleId);

		IndexWriter indexWriter = new IndexWriter(directory, iwc);
		indexWriter.deleteDocuments(term);

		indexWriter.close(); // 释放资源
	}

	public static void deleteAll() throws Exception {
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

		IndexWriter indexWriter = new IndexWriter(directory, iwc);
		indexWriter.deleteAll();

		indexWriter.close(); // 释放资源
	}
}
