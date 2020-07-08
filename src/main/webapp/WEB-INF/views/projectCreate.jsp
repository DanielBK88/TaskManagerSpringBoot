<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <title>NewProject</title>
</head>
<body>
<img src="images/logo.jpg">
<h1 style="padding-left:5%;">Новый Проект:</h1>
<form style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';" name='f' action="processProjectsCreate" method='POST'>
  <table cellspacing="2" border="1" cellpadding="5">
    <tr>
      <td>Название проекта:</td>
      <td><input type='text' name='projectName' value=''></td>
    </tr>
    <tr>
      <td><input name="submit" type="submit" value="Создать" /></td>
    </tr>
  </table>
</form>
</body>
</html>