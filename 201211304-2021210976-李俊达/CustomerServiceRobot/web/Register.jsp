<%--
  Created by IntelliJ IDEA.
  User: Dell
  Date: 2023/10/31
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册界面</title>
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
       #band {
           position: absolute;
           top: 0;
           left: 0;
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
    </style>
</head>
<body background="./img/bg2.png" style="background-repeat: no-repeat; background-attachment: fixed; background-size: 100% 100%;">
<div id="band"><img src="./img/item1.png"></div>
<form method="post" action="http://localhost:8080/CustomerServiceRobot_war_exploded/Register">
    用户名:<input type="text" id="username" name="id" placeholder="请填写用户名"><br>
    密码:<input type="password" id="password" name="pwd" placeholder="请填写密码"><br>
    确认密码:<input type="password" id="password2" name="pwd2" placeholder="填写确认密码"><br>
    <input type="submit" value="注册"> <input type="reset" value="重新输入">
</form>
</body>
</html>
