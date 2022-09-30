<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Пользователь</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous">
    </head>
    <body>
        <c:forEach var="entry" items="${user}">
            <div>
                <c:out value="${entry.key}"/>
                :
                <c:out value="${entry.value}"/>
            </div>
        </c:forEach>
        <div>
            <a href='/users/delete?id=${user.get("id")}'>Удалить пользователя</a>
        </div>
    </body>
</html>

<!-- END -->
