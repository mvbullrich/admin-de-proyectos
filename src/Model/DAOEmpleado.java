package Model;

import Controlador.Empleado;
import Controlador.Proyecto;
import Service.ServiceException;

import java.sql.*;
import java.util.ArrayList;

import static Model.Config.*;

public class DAOEmpleado implements DAO<Empleado>{
    @Override
    public void guardar(Empleado elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Empleado (id, nombre, costoPorHora, disponible) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, elemento.getId());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setDouble(3, elemento.getCostoPorHora());
            preparedStatement.setBoolean(4, elemento.isDisponible());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res + " empleado(s).");
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Tarea SET empleado_id = NULL WHERE empleado_id = ?");
            updateStatement.setInt(1, id);
            updateStatement.executeUpdate();

            preparedStatement=connection.prepareStatement("DELETE FROM Empleado  WHERE id=?");
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
    public void modificar(Empleado elemento) throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Empleado SET id=?, nombre=?, costoPorHora=? WHERE id=?");
            preparedStatement.setInt(1, elemento.getId());
            preparedStatement.setString(2, elemento.getNombre());
            preparedStatement.setDouble(3, elemento.getCostoPorHora());
            preparedStatement.setInt(4, elemento.getId());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public Empleado buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Empleado empleado = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado  WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setCostoPorHora(resultSet.getDouble("costoPorHora"));
                empleado.setDisponible(resultSet.getBoolean("disponible"));
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return empleado;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Empleado> datos = new ArrayList<>();
        Empleado empleado;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Empleado");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setCostoPorHora(resultSet.getDouble("costoPorHora"));
                empleado.setDisponible(resultSet.getBoolean("disponible"));
                datos.add(empleado);
            }
        }
        catch(ClassNotFoundException | SQLException e){
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<Empleado> obtenerEmpleadosDisponibles() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado WHERE disponible IS TRUE");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Empleado> empleadosDisponibles = new ArrayList<>();

            while (resultSet.next()){
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setCostoPorHora(resultSet.getDouble("costoPorHora"));
                empleado.setDisponible(resultSet.getBoolean("disponible"));

                empleadosDisponibles.add(empleado);
            }
            return empleadosDisponibles;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Empleado> obtenerTodosLosEmpleados() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Empleado");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Empleado> empleados = new ArrayList<>();

            while (resultSet.next()){
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setCostoPorHora(resultSet.getDouble("costoPorHora"));
                empleado.setDisponible(resultSet.getBoolean("disponible"));

                empleados.add(empleado);
            }
            return empleados;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Empleado> obtenerEmpleadosAsignados() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT e.* FROM empleado e INNER JOIN Tarea t ON e.id = t.empleado_id WHERE t.empleado_id IS NOT NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Empleado> empleadosAsignados = new ArrayList<>();

            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setNombre(resultSet.getString("nombre"));
                empleado.setCostoPorHora(resultSet.getInt("costoporhora"));
                empleado.setDisponible(resultSet.getBoolean("disponible"));

                empleadosAsignados.add(empleado);
            }
            return empleadosAsignados;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
