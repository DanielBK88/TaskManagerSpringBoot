<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
  <title>NewTask</title>
</head>
<body>
<img src="images/logo.jpg">
<h1 style="padding-left:5%;">Новая задача:</h1>
<form name='f' action="processTaskCreate" method='POST'>
  <table style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';" cellspacing="2" border="1" cellpadding="5">
    <tr>
      <td>Название задачи:</td>
      <td><input type='text' name='taskName' value=''></td>
    </tr>
    <tr>
      <td><input name="submit" type="submit" value="Создать" /></td>
    </tr>
  </table>
</form>
</body>
</html>