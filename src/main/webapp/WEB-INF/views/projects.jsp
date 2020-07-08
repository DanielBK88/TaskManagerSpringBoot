<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
  <head>
      <title>Projects</title>
  </head>
  <body>
        <img src="images/logo.jpg">
        <h1 style="padding-left:5%;">Проекты в домене <c:out value="${selectedDomain}"/></h1>
        <table style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';" cellspacing="2" border="1" cellpadding="5">
        <th>
        Название проекта
        </th>
        <th>
        Дата создания
        </th>
        <th/>
        <th/>
        <c:forEach items="${projects}" var="project">
            <tr>
              <td>
                  <c:out value="${project.name}"/>
              </td>
              <td>
                  <tags:localDate date="${project.created}"/>
              </td>
              <td>
                    <form name="selectForm${project.name}" action="tasks?project=${project.name}" method='POST'>
                    <input
                        name="selectInput${project.name}"
                        value="Выбрать"
                        type="submit"
                    />
                    </form>
              </td>
              <td>
                    <form name="deleteForm${project.name}" action="processProjectsDelete?project=${project.name}" method='POST'>
                    <input
                        name="deleteInput${project.name}"
                        value="Удалить"
                        type="submit"
                    />
                    </form>
              </td>
            </tr>
        </c:forEach>
        </table>
        <p style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';">
            <a href="/projectCreate"/>Новый проект</a>
        </p>
        <p>
            <form style="padding-left:5%;" name="backForm" action="domains" method='POST'>
            <input
                name="backInput"
                value="Назад"
                type="submit"
            />
            </form>
        </p>
  </body>
</html>