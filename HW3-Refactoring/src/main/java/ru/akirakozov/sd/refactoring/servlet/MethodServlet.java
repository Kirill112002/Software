package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public abstract class MethodServlet extends Servlet {

    public MethodServlet(String connectionDatabase) {
        super(connectionDatabase);
    }

    protected abstract String getQuery(Parameters parameters);

    protected String getInvalidAnswer() {
        return null;
    }

    protected abstract void updateResponse(Parameters parameters, ResultSet resultSet, HttpServletResponse response) throws IOException, SQLException;

    @Override
    protected void statementGet(Parameters parameters, Statement statement, HttpServletResponse response) throws SQLException, IOException {
        String query = getQuery(parameters);
        if (query == null) {
            sendBeginHTML(response);
            String invalidAnswer = getInvalidAnswer();
            if (invalidAnswer != null) {
                sendLineHtml(response, invalidAnswer);
            }
            sendEndHTML(response);
        } else {
            ResultSet resultSet = statement.executeQuery(getQuery(parameters));
            updateResponse(parameters, resultSet, response);
            resultSet.close();
        }
    }
}