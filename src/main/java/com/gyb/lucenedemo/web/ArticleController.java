package com.gyb.lucenedemo.web;

import com.gyb.lucenedemo.entity.Article;
import com.gyb.lucenedemo.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author gengyuanbo
 * 2019/03/27
 */
@Controller
public class ArticleController {

    private ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }
    @GetMapping("/addArticleShow")
    public String addArticleView(){
        return "addArticle";
    }

    @PostMapping("/addArticle")
    public ModelAndView addArticle(Article article){

        ModelAndView modelAndView = new ModelAndView("addArticle");
        String res = "";
        if(validArticle(article)) {
            articleService.addArticle(article);
            res = "成功发布《"+article.getTitle()+"》！";
        }else{
            res = "发布错误，参数校验失败！";
        }

        modelAndView.addObject("res",res);
        return modelAndView;
    }

    @GetMapping("/soArticleShow")
    public String soArticleView(){
        return "soArticle";
    }

    @GetMapping("/soArticle")
    public ModelAndView soArticle(String key){

        ModelAndView mv = new ModelAndView("soArticle");
        List<Article> articles = null;
        if(key != null && !"".equals(key)) {
           articles = articleService.queryArticle(key);
        }
        mv.addObject("articles",articles);
        return mv;
    }

    private boolean validArticle(Article article){
        if(article == null){
            return false;
        }else{
            try {
                Long.valueOf(article.getAuthorId());
            }catch (Exception e){
                return false;
            }
            if(article.getTitle() == null || article.getTitle().equals("") ||
                    article.getContent()==null || article.getContent().equals(""))
                return false;
        }
        return true;
    }
}
