package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter out = response.getWriter();
        List<String> companies= getCompanies();
        String searchValue = request.getParameter("search") == null
                ? ""
                : request.getParameter("search");

        List<String> filtered = companies
                .stream()
                .filter(e -> e.contains(searchValue)).toList();
        if (filtered.isEmpty()) {
            out.println("Companies not found");
            return;
        }
        filtered.forEach(out::println);
        // END
    }
}
