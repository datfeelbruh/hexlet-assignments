package exercise.servlet;

import exercise.Data;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;
import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        // BEGIN
        PrintWriter out = response.getWriter();
        if (request.getQueryString() == null || request.getQueryString().endsWith("=")) {
            out.println(getCompanies());
        } else {
            List<String> companies = getCompanies().stream()
                    .filter(e -> e.contains(request.getParameter("search")))
                    .collect(Collectors.toList());
            if (companies.size() == 0) {
                out.println("Companies not found");
            } else {
                out.println(companies);
            }
        }
        // END
    }
}
