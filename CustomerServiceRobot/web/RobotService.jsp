<%--
  Created by IntelliJ IDEA.
  User: Linkingcoder
  Date: 2023/11/5
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ScriptParser.Parser" %>
<%@ page import="ScriptInterpreter.Interpreter"%>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>服务界面</title>
    <style>
        body {
            background-image: url("./img/bg3.png");
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: 100% 100%;
            text-align: center;
            font-family: Arial, sans-serif;
        }

        #container {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        #images {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        #images img {
            max-width: 100%;
            max-height: 100%;
            margin: 10px;
        }

        #robot-container {
            position: absolute;
            top:  30px; /* 调整垂直位置 */
            right: 20px; /* 调整水平位置 */
        }

        #robot-container img {
            width: 500px; /* 改变宽度 */
            height: 500px; /* 改变高度 */
        }

        #button-container {
            margin-top: 20px;
        }

        #execute-button {
            background-color: #007BFF;
            color: #fff;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="images">
        <img src="./img/listening.png" width="221" height="219" alt="Listening">
        <div id="robot-container">
            <img src="./img/robot.jpg" width="200" height="200" alt="Robot">
        </div>
    </div>

    <div id="button-container">
        <form method="post">
             <div>模式选择<input type="text" name="mode-num" placeholder="填写数字1~5"> </div><br>
            <input type="submit" id="execute-button" name="executeRobotService" value="启动服务">
        </form>
    </div>
</div>

<%
    if ("POST".equals(request.getMethod()) && request.getParameter("executeRobotService") != null) {
        // 调用 interpreter 方法
        Interpreter interpreter = new Interpreter();
        Parser parser = new Parser();
        String scriptnum = request.getParameter("mode-num");
        try {
            parser.ParserFile("s"+scriptnum+".txt");//选择脚本
            try {
                interpreter.InterpreterScript(parser.ReturnSteps());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            out.println("Java代码执行失败：" + e.getMessage());
        }
    }
%>
</body>
</html>
