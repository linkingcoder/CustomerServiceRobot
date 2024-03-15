package Servlet;

import CustomerData.DataService;
import DiaryKeeper.LogWrite;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = "/Login")
public class LoginServlet extends HttpServlet {
    public String username;
    public String password;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");//设置编码，防止乱码出现
        username = request.getParameter("id");
        password = request.getParameter("pwd");
        PrintWriter out  = response.getWriter();
        try {
            if (DataService.login(username, password)) {
                out.print("<script>alert('登陆成功'); window.location='RobotService.jsp' </script>");//调用JavaScript的弹窗功能
                LogWrite.Write(username,"登陆成功");

                out.flush();
                out.close();
            }
            else {
                out.print("<script>alert('Error:登陆失败'); window.location='index.jsp' </script>");
                LogWrite.Write(username,"登陆失败");
                out.flush();
                out.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
}