<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
  <head>
      <title>Welcome</title>
  </head>
  <body>
        <img src="images/logo.jpg">
        <p style="padding-left:5%;font-size:20px;font-style:bold;color:green;">
        <%
            if (request.getParameter("signupok") != null) {
                out.println("Авторизация успешна!");
            }
        %>
        </p>
       <h1 style="padding-left:5%;" >Добро пожаловать!</h1>
       <p style="padding-left:2%;font-size:20px;font-family:'Comic Sans MS';" >
       Пожалуйста, 
       <a href="/login"/>авторизуйтесь</a> либо <a href="/signup"/>зарегистрируйтесь.</a>
       </p>
  </body>
</html>