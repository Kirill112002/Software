package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends MethodServlet {

    public GetProductsServlet(String connectionDatabase) {
        super(connectionDatabase);
    }

    @Override
    protected String getQuery(Parameters parameters) {
        return "SELECT * FROM PRODUCT";
    }

    @Override
    protected void updateResponse(Parameters parameters, ResultSet resultSet, HttpServletResponse response) throws IOException, SQLException {
        sendBeginHTML(response);
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            sendLineHtml(response, name + "\t" + price + "</br>");
        }
        sendEndHTML(response);
    }
}
