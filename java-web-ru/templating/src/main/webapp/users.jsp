<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- BEGIN -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Пользователи</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            crossorigin="anonymous">
    </head>
    <body>
        <c:forEach var="user" items="${users}">
            <div>
                ${user.get("id")}
                <a href='/users/show?id=${user.get("id")}'>${user.get("firstName")} ${user.get("lastName")}</a>
            </div>
        </c:forEach>
    </body>
</html>
<!-- END -->
