package org.alting.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public final class PostgresDB {
    Dotenv dotenv = Dotenv.load();
    private static final String host = dotenv.get("DB_HOST");
    private static final String port = dotenv.get("DB_PORT");
    private static final String database = dotenv.get("DB_DATABASE");
    private static final String username = dotenv.get("DB_USERNAME");
    private static final String password = dotenv.get("DB_PASSWORD");

    String url = String.format(
            "jdbc:postgresql://%s:%s/%s",
            host,
            port,
            database
        );

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    // // public static void main(String [] args) {
    // //     Dotenv dotenv = Dotenv.load();
    // //     String host = dotenv.get("DB_HOST");
    // //     String port = dotenv.get("DB_PORT");
    // //     String database = dotenv.get("DB_DATABASE");
    // //     String username = dotenv.get("DB_USERNAME");
    // //     String password = dotenv.get("DB_PASSWORD");

    // //     String url = String.format(
    // //         "jdbc:postgresql://%s:%s/%s",
    // //         host,
    // //         port,
    // //         database
    // //     );

    // //     try (
    // //         Connection connection = DriverManager.getConnection(url, username, password);
    // //         System.out.println("Connected to the database successfully!");
    // //         connection.close();
    // //     ) catch(Exception e) {
    // //         e.printStackTrace();
    // //     }
    // }
}