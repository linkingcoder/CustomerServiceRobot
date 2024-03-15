<%--
  Created by IntelliJ IDEA.
  User: Linkingcoder
  Date: 2023/10/31
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>语音客服机器人</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            justify-content: flex-end;
            align-items: center;
            min-height: 100vh;
            margin-right: 200px; /* 添加右侧间距 */
        }

        form {
            max-width: 300px;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        input[type="submit"],
        input[type="reset"] {
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        input[type="submit"]:hover,
        input[type="reset"]:hover {
            background-color: #45a049;
        }

        .register-link {
            text-align: center;
            margin-top: 10px;
        }

        .register-link a {
            color: #007BFF;
            text-decoration: none;
        }
    </style>
</head>
<body background="./img/bg1.png" style="background-repeat: no-repeat; background-attachment: fixed; background-size: 100% 100%;">
<form method="post" action="http://localhost:8080/CustomerServiceRobot_war_exploded/Login">
    用户名:<input type="text" id="username" name="id" placeholder="请输入用户名"><br>
    密码:<input type="password" id="password" name="pwd" placeholder="请输入密码"><br>
    <input type="submit" value="登录"> <input type="reset" value="重新输入">
    <div class="register-link">
       <a href="http://localhost:8080/CustomerServiceRobot_war_exploded/Register.jsp">还没有账号？立即注册</a>
    </div>
</form>
</body>
</html>
