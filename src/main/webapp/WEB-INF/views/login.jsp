<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <title>Login</title>
</head>
<body>
<img src="images/logo.jpg">
<h1 style="padding-left:5%;">Авторизация</h1>
<form style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';" name='f' action="perform_login" method='POST'>
  <table cellspacing="2" border="1" cellpadding="5">
    <tr>
      <td>Имя пользователя:</td>
      <td><input type='text' name='username' value=''></td>
    </tr>
    <tr>
      <td>Пароль:</td>
      <td><input type='password' name='password' /></td>
    </tr>
    <tr>
      <td><input name="submit" type="submit" value="Авторизоваться" /></td>
    </tr>
  </table>
</form>
</body>
</html>