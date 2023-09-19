package Model;

import java.sql.*;

public class Config {
    public static String DB_JDBC_DRIVER = "org.h2.Driver";
    public static String DB_URL = "jdbc:h2:tcp://localhost/~/base";
    public static String DB_USER = "sa";
    public static String DB_PASSWORD = "";

    public static void CreateTable() {
        try {
            Class.forName(DB_JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if (!tableExists(connection, "EMPLEADO")) {
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE Empleado (id INT PRIMARY KEY, nombre VARCHAR(255) NOT NULL, costoPorHora DECIMAL(10, 2) NOT NULL, disponible BOOLEAN NOT NULL)");
                preparedStatement.executeUpdate();
                System.out.println("Se creó la tabla de EMPLEADO");
            }
            if (!tableExists(connection, "TAREA")) {
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE Tarea (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255) NOT NULL, descripcion VARCHAR(255), empleado_id INT, id_proyecto INT)");
                preparedStatement.executeUpdate();
                System.out.println("Se creó la tabla de TAREA");
            }
            if (!tableExists(connection, "PROYECTO")) {
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE Proyecto (id INT PRIMARY KEY AUTO_INCREMENT, titulo VARCHAR(255) NOT NULL, descripcion VARCHAR(255), fechaInicio DATE, fechaFin DATE)");
                preparedStatement.executeUpdate();
                System.out.println("Se creó la tabla de PROYECTO");
            }
            if (!tableExists(connection, "HISTORIALESTADO")) {
                PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE HistorialEstado (id INT PRIMARY KEY AUTO_INCREMENT, id_tarea INT NOT NULL, estado VARCHAR(255) NOT NULL, fecha DATE NOT NULL, id_responsable INT NOT NULL, FOREIGN KEY (id_tarea) REFERENCES Tarea(id), FOREIGN KEY (id_responsable) REFERENCES Empleado(id))");
                preparedStatement.executeUpdate();
                System.out.println("Se creó la tabla de HISTORIALESTADO");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }

    public static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

    public static boolean tableExistsSQL(Connection connection, String tableName) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
                + "FROM information_schema.tables "
                + "WHERE table_name = ?"
                + "LIMIT 1;");
        preparedStatement.setString(1, tableName);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1) != 0;
    }
}