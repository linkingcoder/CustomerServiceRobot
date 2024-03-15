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

@WebServlet(name = "RegisterServlet", urlPatterns = "/Register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");//设置编码
        String username = request.getParameter("id");
        String password = request.getParameter("pwd");
        String ConfrimPassword = request.getParameter("pwd2");
        PrintWriter out = response.getWriter();
        if(username.length()==0 || password.length()==0 || ConfrimPassword.length()==0){
            out.print("<script>alert('信息不完整'); window.location='Register.jsp' </script>");
            LogWrite.Write(username,"注册失败");
            out.flush();
            out.close();
        }
       else {
            try {
                if(DataService.isExist(username)){
                             out.print("<script>alert('用户名已存在'); window.location='Register.jsp' </script>");
                             LogWrite.Write(username,"注册失败");
                             out.flush();
                             out.close();
                          }
                 else  if(password.equals(ConfrimPassword)){
                         DataService.CreateAccount(username,password);
                         response.sendRedirect("index.jsp");
                         LogWrite.Write(username,"注册成功");
                 } else {
                     out.print("<script>alert('确认密码错误，注册失败'); window.location='Register.jsp' </script>");
                     LogWrite.Write(username,"注册失败");
                     out.flush();
                     out.close();
                 }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}