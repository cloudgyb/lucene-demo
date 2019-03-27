package com.gyb.lucenedemo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author gengyuanbo
 * 2019/03/26
 */
@Data
public class Article implements Serializable {
    private Long authorId;
    private Long articleId;
    private String title;
    private String content;
    private Long createTime;
    private Long updateTime;
}
