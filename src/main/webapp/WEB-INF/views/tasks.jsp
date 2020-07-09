<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>
      <title>Tasks</title>
  </head>
  <body>
        <img src="images/logo.jpg">
        <h1 style="padding-left:5%;">Задачи в проекте <c:out value="${selectedProject}"/></h1>
        <table style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';" cellspacing="2" border="1" cellpadding="5">
        <th>
        Название задачи
        </th>
        <th/>
        <c:forEach items="${tasks}" var="task">
            <tr>
              <td>
                  <c:out value="${task.name}"/>
              </td>
              <td>
                    <form name="deleteForm${task.name}" action="processTasksDelete?task=${task.name}" method='POST'>
                    <input
                        name="deleteInput${task.name}"
                        value="Удалить"
                        type="submit"
                    />
                    </form>
              </td>
            </tr>
        </c:forEach>
        </table>
        <p style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';">
            <a href="/taskCreate"/>Новая задача</a>
        </p>
        <p>
            <form style="padding-left:5%;" name="backForm" action="projects?domain=${selectedDomain}" method='POST'>
            <input
                name="backInput"
                value="Назад"
                type="submit"
            />
            </form>
        </p>
  </body>
</html>