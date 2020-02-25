package com.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/MyServlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=utf-8");
            String username = request.getParameter("username");
            System.out.println(username);

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "chb990818");
            PreparedStatement pre = conn.prepareStatement("select password from account where username=?");
            pre.setString(1,username);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("password"));
                request.setAttribute("password",rs.getString("password"));
                request.getRequestDispatcher("doit.jsp").forward(request,response);
            }
            rs.close();
            pre.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
