package Model;

import Controlador.HistorialEstado;

import java.sql.*;
import java.util.ArrayList;

import static Model.Config.*;

public class DAOHistorialEstado implements DAO<HistorialEstado>{
    @Override
    public void guardar(HistorialEstado elemento) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO HistorialEstado (id_tarea, estado, fecha, id_responsable) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1,elemento.getIdTarea());
            preparedStatement.setString(2, elemento.getEstado());
            preparedStatement.setDate(3, new java.sql.Date(elemento.getFecha().getTime()));
            preparedStatement.setInt(4 ,elemento.getIdResponsable());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregó " + res + " registro(s) al historial de estados.");
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException{

    }

    @Override
    public HistorialEstado buscar(int id) throws DAOException {
        return null;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        return null;
    }

    public ArrayList<HistorialEstado> obtenerDatos() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<HistorialEstado> historiales = new ArrayList<>();

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT h.id, h.id_tarea, t.titulo AS titulo_tarea, h.estado, h.fecha, " +
                    "h.id_responsable, e.nombre AS nombre_responsable " +
                    "FROM historialestado h " +
                    "INNER JOIN Tarea t on h.id_tarea = t.id " +
                    "INNER JOIN Empleado e ON h.id_responsable = e.id");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int idTarea = resultSet.getInt("id_tarea");
                String tituloTarea = resultSet.getString("titulo_tarea");
                String estado = resultSet.getString("estado");
                Date fecha = resultSet.getDate("fecha");
                int idResponsable = resultSet.getInt("id_responsable");
                String nombreResponsable = resultSet.getString("nombre_responsable");

                HistorialEstado historial = new HistorialEstado();

                historial.setId(id);
                historial.setIdTarea(idTarea);
                historial.setTituloTarea(tituloTarea);
                historial.setEstado(estado);
                historial.setFecha(fecha);
                historial.setIdResponsable(idResponsable);
                historial.setNombreResponsable(nombreResponsable);

                historiales.add(historial);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return historiales;
    }

    @Override
    public void modificar(HistorialEstado elemento) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE historialestado SET estado=?, fecha=?, id_responsable=? WHERE id_tarea=?");
            preparedStatement.setString(1, elemento.getEstado());
            preparedStatement.setDate(2, new java.sql.Date(elemento.getFecha().getTime()));
            preparedStatement.setInt(3, elemento.getIdResponsable());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
