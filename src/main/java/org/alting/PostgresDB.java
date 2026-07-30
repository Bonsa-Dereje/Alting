package org.alting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public final class PostgresDB {
    private static final Dotenv staticDotenv = Dotenv.load();
    private static final String url = staticDotenv.get("DB_URL");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}