package Model;

import Controlador.Backlog;
import Controlador.Tarea;

import java.sql.*;
import java.util.ArrayList;

import static Model.Config.*;

public class DAOBacklog implements DAO<Backlog>{

    @Override
    public void guardar(Backlog elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Backlog (nombre, descripcion) VALUES (?, ?)");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getDescripcion());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregó " + res + " registro(s) a la tabla Backlog");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Tarea SET id_backlog = NULL WHERE id_backlog = ?");
            updateStatement.setInt(1, id);
            updateStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM Backlog WHERE id=?");
            preparedStatement.setInt(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se eliminó " + res + " registro(s) de la tabla Backlog");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Backlog elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE BACKLOG SET nombre=?, descripcion=? WHERE id=?");
            preparedStatement.setString(1, elemento.getNombre());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setInt(3, elemento.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res + " registro(s) de la tabla Backlog");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Backlog buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Backlog backlog = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Backlog WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                backlog = new Backlog();
                backlog.setId(resultSet.getInt("id"));
                backlog.setNombre(resultSet.getString("nombre"));
                backlog.setDescripcion(resultSet.getString("descripcion"));
            }
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
        return backlog;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        return null;
    }

    public void agregarTarea(int idBacklog, int idTarea) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET id_backlog=? WHERE id=?");
            preparedStatement.setInt(1, idBacklog);
            preparedStatement.setInt(2, idTarea);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res + " registros");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public void eliminarTarea(Tarea tarea) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET id_backlog=NULL WHERE id=?");
            preparedStatement.setInt(1, tarea.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res + " registros");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Backlog> obtenerBacklogs() throws DAOException{
        ArrayList<Backlog> backlogs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Backlog");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Backlog backlog = new Backlog();
                backlog.setId(resultSet.getInt("id"));
                backlog.setNombre(resultSet.getString("nombre"));
                backlog.setDescripcion(resultSet.getString("descripcion"));

                backlogs.add(backlog);
            }
            return backlogs;
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasSinBacklog() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE id_backlog IS NULL AND id_sprint IS NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareas = new ArrayList<>();

            while (resultSet.next()){
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                tarea.setHorasReales(resultSet.getInt("horasreales"));
                tarea.setEmpleado_id(resultSet.getInt("empleado_id"));
                tarea.setIdProyecto(resultSet.getInt("id_proyecto"));
                tarea.setId_sprint(resultSet.getInt("id_sprint"));
                tarea.setId_backlog(resultSet.getInt("id_backlog"));

                tareas.add(tarea);
            }
            return tareas;
        }catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasPorBacklog(int idBacklog)throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE id_backlog IS NOT NULL AND id_backlog=?");
            preparedStatement.setInt(1, idBacklog);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareaArrayList = new ArrayList<>();

            while (resultSet.next()){
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                tarea.setHorasReales(resultSet.getInt("horasreales"));
                tarea.setEmpleado_id(resultSet.getInt("empleado_id"));
                tarea.setIdProyecto(resultSet.getInt("id_proyecto"));
                tarea.setId_sprint(resultSet.getInt("id_sprint"));
                tarea.setId_backlog(resultSet.getInt("id_backlog"));

                tareaArrayList.add(tarea);
            }
            return tareaArrayList;
        } catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasConBacklog()throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE id_backlog IS NOT NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareaArrayList = new ArrayList<>();

            while (resultSet.next()){
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                tarea.setHorasReales(resultSet.getInt("horasreales"));
                tarea.setEmpleado_id(resultSet.getInt("empleado_id"));
                tarea.setIdProyecto(resultSet.getInt("id_proyecto"));
                tarea.setId_sprint(resultSet.getInt("id_sprint"));
                tarea.setId_backlog(resultSet.getInt("id_backlog"));

                tareaArrayList.add(tarea);
            }
            return tareaArrayList;
        } catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }
}