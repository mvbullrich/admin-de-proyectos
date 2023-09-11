package Model;

import Controlador.Empleado;
import Controlador.Proyecto;
import Controlador.Tarea;
import Service.ServiceException;

import java.sql.*;
import java.util.ArrayList;

public class DAOProyecto implements DAO<Proyecto> {
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:tcp://localhost/~/base";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";

    @Override
    public void guardar(Proyecto elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Proyecto (titulo, descripcion, fechaInicio, fechaFin) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, elemento.getTitulo());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setDate(3, java.sql.Date.valueOf(elemento.getFechaInicio()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(elemento.getFechaFin()));
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res + " proyectos.");
        } catch (ClassNotFoundException | SQLException e) {
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
            preparedStatement=connection.prepareStatement("DELETE FROM Proyecto  WHERE id=?");
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
    public void modificar(Proyecto elemento) throws DAOException{
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("UPDATE Proyecto SET titulo=?, descripcion=?, fechaInicio=?, fechaFin=? WHERE id=?");
            preparedStatement.setString(1, elemento.getTitulo());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setDate(3, java.sql.Date.valueOf(elemento.getFechaInicio()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(elemento.getFechaFin()));
            preparedStatement.setInt(5, elemento.getId());
            int res=preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public Proyecto buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Proyecto proyecto = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Proyecto  WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                proyecto = new Proyecto();
                proyecto.setId(resultSet.getInt("id"));
                proyecto.setTitulo(resultSet.getString("titulo"));
                proyecto.setDescripcion(resultSet.getString("descripcion"));
                proyecto.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                proyecto.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());
            }
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return proyecto;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException{
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ArrayList<Proyecto> datos = new ArrayList<>();
        Proyecto proyecto;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection= DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
            preparedStatement=connection.prepareStatement("SELECT * FROM Proyecto");
            ResultSet resultSet =preparedStatement.executeQuery();
            while (resultSet.next()) {
                proyecto = new Proyecto();
                proyecto.setId(resultSet.getInt("id"));
                proyecto.setTitulo(resultSet.getString("titulo"));
                proyecto.setDescripcion(resultSet.getString("descripcion"));
                proyecto.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                proyecto.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());
                datos.add(proyecto);
            }
        }
        catch(ClassNotFoundException | SQLException e){
            throw  new DAOException(e.getMessage());
        }
        return datos;
    }

    public ArrayList<Proyecto> obtenerProyectos() throws DAOException{
        ArrayList<Proyecto> proyectos = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Proyecto");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Proyecto proyecto = new Proyecto();
                proyecto.setId(resultSet.getInt("id"));
                proyecto.setTitulo(resultSet.getString("titulo"));
                proyecto.setDescripcion(resultSet.getString("descripcion"));
                proyecto.setFechaInicio(resultSet.getDate("fechaInicio").toLocalDate());
                proyecto.setFechaFin(resultSet.getDate("fechaFin").toLocalDate());

                proyectos.add(proyecto);
            }
            return proyectos;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void asignarTareaProyecto(Tarea tarea, Proyecto proyecto) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET id_proyecto = ? WHERE id = ?");
            preparedStatement.setInt(1, proyecto.getId());
            preparedStatement.setInt(2, tarea.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void desasignarTarea(Tarea tarea, Proyecto proyecto) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET id_proyecto =null WHERE id=?");
            preparedStatement.setInt(1, tarea.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }

    public double calcularCostoHoras(int idProyecto) throws DAOException{
        DAOTarea daoTarea = new DAOTarea();
        double costoHoras = 0.0;
        try{
            ArrayList<Tarea> tareasProyectos = daoTarea.obtenerProyectosConTareas(idProyecto);
            for(Tarea tarea:tareasProyectos){
                costoHoras += tarea.getHorasReales();
            }
        } catch (DAOException e) {
            throw new DAOException(e.getMessage());
        }
        return costoHoras;
    }

    public double calcularCostoDinero(int idProyecto) throws DAOException{
        DAOTarea daoTarea = new DAOTarea();
        DAOEmpleado daoEmpleado = new DAOEmpleado();
        double costoDinero = 0.0;
        try {
            ArrayList<Tarea> tareas = daoTarea.obtenerProyectosConTareas(idProyecto);
            for (Tarea tarea : tareas){
                int idEmpleado = tarea.getEmpleado_id();
                Empleado empleado = daoEmpleado.buscar(idEmpleado);
                costoDinero += empleado.getCostoPorHora() * tarea.getHorasReales();
            }
        } catch (DAOException e){
            throw new DAOException(e.getMessage());
        }
        return costoDinero;
    }
}
