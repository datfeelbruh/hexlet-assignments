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
        Path usersPath = Paths.get("src", "main", "resources", "users.json").toAbsolutePath().normalize();
        String usersContent = Files.readString(usersPath);
        return objectMapper.readValue(usersContent, List.class);
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        StringBuilder body = new StringBuilder();
        body.append("""
                <!DOCTYPE html>
                <html lang="ru">
                    <head>
                        <meta charset="UTF-8">
                        <title>Example application | Users</title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                                    rel="stylesheet" 
                                    integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                                    crossorigin="anonymous">
                    </head>
                    <body>
                        <div class=\"container\">
                            <a href=\"/\">Главная</a>
                            <table>
                """);

        for (Map<String, String> user : users) {
            String id = user.get("id");
            String fullname = user.get("firstName") + " " + user.get("lastName");
            body.append("<tr>");
            body.append("<td>" + id + "</td>");
            body.append("<td><a href=\"/users/" + id + "\">" + fullname + "</a>" + "</td>");
            body.append("</tr>");
        }

        body.append("""
                        </table>
                    </div>
                </body>
            </html>
            """);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(body);
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        Map<String, String> user = users
                .stream()
                .filter(usr -> usr.get("id").equals(id))
                .findAny()
                .orElse(null);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        StringBuilder body = new StringBuilder();
        body.append("""
            <!DOCTYPE html>
            <html lang="ru">
                <head>
                    <meta charset="UTF-8">
                    <title>Example application | Users</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                                rel="stylesheet"
                                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                                crossorigin="anonymous">
                   
                </head>
                <body>
                    <div class=\"container\">
                        <a href=\"/users\">Пользователи</a>
            """);
        for (Map.Entry<String, String> entry : user.entrySet()) {
            body.append("<div>");
            body.append(entry.getKey() + ": " + entry.getValue() + " ");
            body.append("</div");
        }

        body.append("""
                    </div>
                </table>
            </body>
        </html>
        """);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(body);
        // END
    }
}
