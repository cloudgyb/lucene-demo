package com.gyb.lucenedemo.service;

import com.gyb.lucenedemo.dao.ArticleDao;
import com.gyb.lucenedemo.entity.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gengyuanbo
 * 2019/03/26
 */
@Service
public class ArticleService {
    private ArticleDao articleDao;
    @Value("${lucene.path}")
    private String lucenePath;

    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void addArticle(Article article){
        try {
            articleDao.addArticle(article);
            Analyzer analyzer = new CJKAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
            Path indexPath = Paths.get(lucenePath);
            FSDirectory fsDirectory = FSDirectory.open(indexPath);
            IndexWriter indexWriter = new IndexWriter(fsDirectory, indexWriterConfig);

            Document doc = new Document();
            doc.add(new Field("article_id", article.getArticleId()+"", TextField.TYPE_STORED));
            doc.add(new Field("author_id", article.getAuthorId()+"", TextField.TYPE_STORED));
            doc.add(new Field("article_title",article.getTitle(), TextField.TYPE_STORED));
            doc.add(new Field("article_content",article.getContent(), TextField.TYPE_STORED));
            indexWriter.addDocument(doc);
            indexWriter.close();
            fsDirectory.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<Article> queryArticle(String keyWord) {
        List<Article> list = null;
        try{
            Path path = Paths.get(lucenePath);
            DirectoryReader directoryReader = DirectoryReader.open(FSDirectory.open(path));
            IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
            Analyzer standardAnalyzer = new CJKAnalyzer();

            MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(new String[]{"article_title", "article_content"}, standardAnalyzer);
            Query query = multiFieldQueryParser.parse(keyWord);
            ScoreDoc[] scoreDocs = indexSearcher.search(query, 100).scoreDocs;

            QueryScorer queryScorer = new QueryScorer(query);
            SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<span style='color:red'>", "</span>");
            Highlighter highlighter = new Highlighter(simpleHTMLFormatter, queryScorer);
            list = dealDocs(standardAnalyzer,indexSearcher,highlighter,scoreDocs);

            directoryReader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private List<Article> dealDocs(Analyzer analyzer, IndexSearcher indexSearcher, Highlighter highlighter, ScoreDoc[] scoreDocs) throws Exception{
        ArrayList<Article> articles = new ArrayList<>();
        if(scoreDocs!=null) {
            System.out.printf("共找到条%d数据!", scoreDocs.length);
            for (ScoreDoc scoreDoc : scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                String article_id = doc.get("article_id");
                String author_id = doc.get("author_id");
                String article_title = doc.get("article_title");
                String article_title1 = highlighter.getBestFragment(analyzer, "article_title", article_title);
                String article_contents = doc.get("article_content");
                String article_content = highlighter.getBestFragment(analyzer, "article_content", article_contents);
                Article article = new Article();
                article.setArticleId(Long.valueOf(article_id));
                article.setAuthorId(Long.valueOf(author_id));
                if (article_title1 == null) {
                    article.setTitle(article_title);
                } else {
                    article.setTitle(article_title1);
                }
                if (article_content != null) {
                    article.setContent(article_content);
                } else {
                    article.setContent(article_contents);
                }

                articles.add(article);
            }
        }
        return articles;
    }

}
