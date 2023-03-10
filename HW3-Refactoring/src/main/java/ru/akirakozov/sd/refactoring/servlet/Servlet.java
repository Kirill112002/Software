package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public abstract class Servlet extends HttpServlet {

    protected final String connectionDatabase;

    public Servlet(String connectionDatabase) {
        this.connectionDatabase = connectionDatabase;
    }

    protected Parameters parse(HttpServletRequest request) {
        return new Parameters(request);
    }

    protected abstract void statementGet(Parameters parameters, Statement statement, HttpServletResponse response) throws SQLException, IOException;

    public void sendBeginHTML(HttpServletResponse response) throws IOException {
        sendLineHtml(response, "<html><body>");
    }

    public void sendEndHTML(HttpServletResponse response) throws IOException {
        sendLineHtml(response, "</body></html>");
    }

    public void sendLineHtml(HttpServletResponse response, String s) throws IOException {
        response.getWriter().println(s);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection connection = DriverManager.getConnection(connectionDatabase)) {
            Statement statement = connection.createStatement();
            Parameters parameters = parse(request);
            statementGet(parameters, statement, response);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    static class Parameters {

        public final Map<String, String[]> parameters;

        Parameters(HttpServletRequest request) {
            parameters = request.getParameterMap();
        }
    }
}