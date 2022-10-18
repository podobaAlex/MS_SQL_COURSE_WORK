package database;

import java.sql.*;
import java.util.Arrays;

public class Main {

    public static Connection connObj;
    public static String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=tutorialDb;integratedSecurity=true";

    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://DESKTOP-GNG9DNC\\SQLEXPRESS01;"
                        + "database=T&P_Firm;"
                        + "integratedSecurity=true;"
                        + "encrypt=false;";

        ResultSet resultSet = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl);
             Statement statement = connection.createStatement()) {

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT TOP 10 * from dbo.Agents";
            resultSet = statement.executeQuery(selectSql);

            StringBuilder result = new StringBuilder();

            // Print results from select statement
            while (resultSet.next()) {
                result.append(resultSet.getString(2)).append(",").append(resultSet.getString(3)).append(";");
            }

            Object[][] res = Arrays.stream(
                    result.toString().split(";")
            ).map(
                    i -> i.split(",")
            ).toArray(Object[][]::new);

            Arrays.stream(res).forEach(x -> Arrays.stream(x).forEach(System.out::println));



        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
