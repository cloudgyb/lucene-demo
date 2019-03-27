<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>搜索文章</title>
</head>
<body>
    <form action="/soArticle" method="get">
        输入关键字：<input name="key" type="text" required>
        <input type="submit" value="搜一下">
    </form>
    <div>
        <#if articles??>
            共找到${articles?size}条匹配结果！<br>
            <#list articles as item>
                标题：${item.title!}<br>
                内容：${item.content!}<br>
                作者：${item.authorId!}<br>
            </#list>
        </#if>
    </div>
</body>


</html>