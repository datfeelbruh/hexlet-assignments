package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        ObjectMapper objectMapper = new ObjectMapper();
        Path usersPath = Paths.get("/home/sobad/Hexlet/hexlet-assignments/java-web-ru/html/src/main/resources/users.json");
        String usersContent = Files.readString(usersPath);
        return objectMapper.readValue(usersContent, new TypeReference<>(){});
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        PrintWriter out = response.getWriter();
        StringBuilder body = new StringBuilder();
        body.append("""
                <!DOCTYPE html>
                <html lang="ru">
                    <head>
                        <meta charset="UTF-8">
                        <title>Example application | Users</title>
                        <link rel="stylesheet" href="mysite.css">
                    </head>
                    <body>
                        <table>
                """);
        for (Map<String, String> user : users) {
            body.append("<tr>");
            body.append("<td>")
                    .append(user.get("id"))
                    .append("</td>")
                    .append("<td>" + "<a href=/users/")
                    .append(user.get("id"))
                    .append(">")
                    .append(user.get("firstName"))
                    .append(" ")
                    .append(user.get("lastName"))
                    .append("</a>")
                    .append("</td>");
            body.append("</tr>");
        }


        body.append("""
                        </table>
                    </body>
                </html>
                """);
        out.println(body);
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        PrintWriter out = response.getWriter();
        Map<String, String> searchResult = new HashMap<>();
        StringBuilder body = new StringBuilder();
        body.append("""
                <!DOCTYPE html>
                <html lang="ru">
                    <head>
                        <meta charset="UTF-8">
                        <title>Example application | Users</title>
                        <link rel="stylesheet" href="mysite.css">
                    </head>
                    <body>
                        <table>
                """);
        for (Map<String, String> user : users) {
            if (Objects.equals(user.get("id"), id)) {
                searchResult = user;
            }
        }
        if (!searchResult.isEmpty()) {
            body.append("<tr><td>")
                    .append(searchResult.get("id"))
                    .append("</td></tr>")
                    .append("<tr><td>")
                    .append(searchResult.get("firstName"))
                    .append("</td></tr>")
                    .append("<tr><td>")
                    .append(searchResult.get("lastName"))
                    .append("</td></tr>")
                    .append("<tr><td>")
                    .append(searchResult.get("email"))
                    .append("</td></tr>");
        } else {
            response.sendError(404, "Not found");
            return;
        }

        body.append("""
                        </table>
                    </body>
                </html>
                """);
        out.println(body);
        // END
    }
}
