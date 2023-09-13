package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Config {
    public static String DB_JDBC_DRIVER = "org.h2.Driver";
    public static String DB_URL = "jdbc:h2:tcp://localhost/~/base";
    public static String DB_USER = "sa";
    public static String DB_PASSWORD = "";
}