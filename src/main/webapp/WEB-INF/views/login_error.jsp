<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
  <head>
      <title>Login Failed</title>
  </head>
  <body>
      <img src="images/logo.jpg">
       <p style="padding-left:5%;font-size:20px;font-style:bold;color:red;">Неверное имя пользователя или пароль!</p>
       <p style="padding-left:2%;font-size:20px;font-family:'Comic Sans MS';">
       Пожалуйста, 
       <a href="/login"/>попробуйте снова</a> либо <a href="/signup"/>зарегистрируйтесь.</a>
       </p>
  </body>
</html>