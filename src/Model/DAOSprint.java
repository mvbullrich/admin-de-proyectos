package Model;

import Controlador.Proyecto;
import Controlador.Sprint;
import Controlador.Tarea;

import java.sql.*;
import java.util.ArrayList;

import static Model.Config.*;

public class DAOSprint implements DAO<Sprint>{
    @Override
    public void guardar(Sprint elemento) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Sprint (fecha_inicio, fecha_fin) VALUES (?, ?)");
            preparedStatement.setDate(1, java.sql.Date.valueOf(elemento.getFechaInicio()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(elemento.getFechaFin()));
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregó " + res + " registro(s) al sprint");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

   @Override
    public void eliminar(int id) throws DAOException{
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Tarea SET id_sprint = NULL WHERE id_sprint = ?");
            updateStatement.setInt(1, id);
            updateStatement.executeUpdate();

            preparedStatement=connection.prepareStatement("DELETE FROM Sprint  WHERE id=?");
            preparedStatement.setInt(1, id);
            int res=preparedStatement.executeUpdate();
            System.out.println("Se elimino " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Sprint elemento) throws DAOException{
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Sprint SET fecha_inicio=?, fecha_fin=? WHERE id=?");
            preparedStatement.setDate(1, java.sql.Date.valueOf(elemento.getFechaInicio()));
            preparedStatement.setDate(2, java.sql.Date.valueOf(elemento.getFechaFin()));
            preparedStatement.setInt(3, elemento.getId());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public Sprint buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Sprint sprint = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Sprint WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                sprint = new Sprint();
                sprint.setId(resultSet.getInt("id"));
                sprint.setFechaInicio(resultSet.getDate("fecha_inicio").toLocalDate());
                sprint.setFechaFin(resultSet.getDate("fecha_fin").toLocalDate());
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return sprint;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException{
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Sprint> datos = new ArrayList<>();
        Sprint sprint;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Sprint");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                sprint = new Sprint();
                sprint.setId(resultSet.getInt("id"));
                sprint.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                sprint.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());
                datos.add(sprint);
            }
        }
        catch(ClassNotFoundException | SQLException e){
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    public void agregarTarea(int idSprint, int idTarea) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET id_sprint=? WHERE id=?");
            preparedStatement.setInt(1, idSprint);
            preparedStatement.setInt(2, idTarea);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificarion " + res + " registros");
        } catch (ClassNotFoundException | SQLException e){
            throw new DAOException(e.getMessage());
        }
    }

    public void quitarTarea(Tarea tarea) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET id_sprint=NULL WHERE id=?");
            preparedStatement.setInt(1, tarea.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasSinSprint() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE id_sprint IS NULL AND id_backlog IS NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareasSinSprint = new ArrayList<>();

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

                tareasSinSprint.add(tarea);
            }
            return tareasSinSprint;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Sprint> obtenerSprints() throws DAOException{
        ArrayList<Sprint> sprints = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Sprint");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Sprint sprint = new Sprint();
                sprint.setId(resultSet.getInt("id"));
                sprint.setFechaInicio(resultSet.getDate("fecha_inicio").toLocalDate());
                sprint.setFechaFin(resultSet.getDate("fecha_fin").toLocalDate()); // Corregir aquí

                sprints.add(sprint);
            }
            return sprints;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasConSprint() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE id_sprint IS NOT NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareasSinSprint = new ArrayList<>();

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

                tareasSinSprint.add(tarea);
            }
            return tareasSinSprint;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasPorSprint(int idSprint)throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE id_sprint IS NOT NULL AND id_sprint=?");
            preparedStatement.setInt(1, idSprint);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareasSinSprint = new ArrayList<>();

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

                tareasSinSprint.add(tarea);
            }
            return tareasSinSprint;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }
}