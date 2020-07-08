<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
  <head>
      <title>Domains</title>
  </head>
  <body>
        <img src="images/logo.jpg">
        <p style="padding-left:5%;font-size:20px;font-style:bold;color:green;">
        <%
            if (request.getParameter("exportok") != null) {
                out.println("Домен успешно экспортирован!");
            }
        %>
        </p>
        <h1 style="padding-left:5%;">Выберите домен:</h1>
        <table style="padding-left:5%;font-size:20px;font-family:'Comic Sans MS';" cellspacing="2" border="1" cellpadding="5">
        <th>
        Название домена
        </th>
        <th/>
        <th/>
        <c:forEach items="${domains}" var="domain">
            <tr>
              <td>
                  <c:out value="${domain.name}"/>
              </td>
              <td>
                    <form name"selectForm${domain.name}" action="projects?domain=${domain.name}" method='POST'>
                    <input
                        name"selectInput${domain.name}"
                        value="Выбрать"
                        type="submit"
                    />
                    </form>
              </td>
              <td>
                    <form name"exportForm${domain.name}" action="domainsExport?domain=${domain.name}" method='POST'>
                    <input
                        name"exportInput${domain.name}"
                        value="Экспорт в XML"
                        type="submit"
                    />
                    </form>
              </td>
            </tr>
        </c:forEach>
        </table>
        <p>
            <form style="padding-left:5%;" name="logoutForm" action="perform_logout" method='POST'>
            <input
                name="logoutInput"
                value="Выход из системы"
                type="submit"
            />
            </form>
        </p>
  </body>
</html>