## 使用Lucene做的一个全文检索Demo
> 背景：用户可以发表文章，然后可以通过关键字检索匹配到的文章，并展示到页面上，并且将关键字标红。


1. Clone项目到本地 git clone https://github.com/cloudgyb/lucene-demo
2. cd lucene-demo
3. 创建数据库lucene，并使用mysql.sql创建表 
3. mvn spring-boot:run
4. 在浏览器中打开：localhost:8080/addArticleShow,发布一篇测试文章
5. 访问localhost：8080/soArticleShow,搜索文章


