package database;

import java.sql.*;

public class SQLConnection {

    private final Statement statement;
    private Connection connection;
    private String JDBC_URL;

    public SQLConnection(String URL) throws SQLException {
        this.JDBC_URL = URL;
        this.connection = DriverManager.getConnection(JDBC_URL);
        this.statement = connection.createStatement();
    }

    public String selectFunction(String command, int columnsLength) throws SQLException {

        ResultSet resultSet = this.statement.executeQuery(command);

        StringBuilder result = new StringBuilder();

        while (resultSet.next()) {
            for (int i = 1; i <= columnsLength; i++) {
                result.append(resultSet.getString(i)).append(",");
            }
            result.append(";");
        }
        return result.toString();
    }

    public void insertFunction(String command) throws SQLException {
        this.statement.executeQuery(command);
    }

}
