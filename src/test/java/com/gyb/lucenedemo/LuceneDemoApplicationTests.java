package com.gyb.lucenedemo;

import com.gyb.lucenedemo.entity.Article;
import com.gyb.lucenedemo.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LuceneDemoApplicationTests {

    @Autowired
    private ArticleService service;
    @Test
    public void contextLoads() {
        Article article = new Article();
        article.setAuthorId(5L);
        article.setArticleId(1L);
        article.setTitle("java");
        article.setContent("java 中 String 类使用了final关键字修饰，说明String不可被继承，String的实例是不可变对象！");

        service.addArticle(article);
    }

    @Test
    public void queryTest(){
        List<Article> 哈哈 = service.queryArticle("java");
    }

}
