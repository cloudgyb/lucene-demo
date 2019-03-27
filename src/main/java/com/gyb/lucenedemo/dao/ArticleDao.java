package com.gyb.lucenedemo.dao;

import com.gyb.lucenedemo.entity.Article;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author gengyuanbo
 * 2019/03/26
 */
@Repository
public class ArticleDao {
    private JdbcTemplate jdbcTemplate;

    public ArticleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Article getArticle(long articleId){
        String sql = "select * from article where article_id=?";

        List<Article> articles = jdbcTemplate.query(sql, new Object[]{articleId}, new RowMapper<Article>() {
            @Override
            public Article mapRow(ResultSet resultSet, int i) throws SQLException {
                Article article = new Article();
                article.setArticleId(resultSet.getLong("article_id"));
                article.setAuthorId(resultSet.getLong("author_id"));
                article.setTitle(resultSet.getString("title"));
                article.setContent(resultSet.getString("content"));
                article.setCreateTime(resultSet.getLong("create_time"));
                article.setUpdateTime(resultSet.getLong("update_time"));
                return article;
            }
        });
        return articles!=null && articles.size()>0?articles.get(0):null;
    }

    public int addArticle(Article article){
        String sql = "insert into article(title,content,author_id,create_time,update_time) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int update = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1,article.getTitle());
                ps.setString(2,article.getContent());
                long time = new Date().getTime();
                ps.setLong(3,article.getAuthorId());
                ps.setLong(4,time);
                ps.setLong(5,time);
                return ps;
            }
        }, keyHolder);
        article.setArticleId(keyHolder.getKey().longValue());
        return update;
    }
}
