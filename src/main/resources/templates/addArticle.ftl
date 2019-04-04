<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加文章</title>
</head>
<body>
    <form action="/addArticle" method="post">
        作者：<input type="text" name="authorId" placeholder="作者ID，非负整数" required><br>
        标题：<input type="text" name="title" required><br>
        内容：<textarea name="content" required placeholder="输入内容...." cols="60" rows="20"></textarea>  <br>
        <input type="submit" value="发布">
    </form>
    <p>
        ${res!}
    </p>
</body>
</html>