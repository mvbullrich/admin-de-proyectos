package Model;

import Controlador.Empleado;
import Controlador.Proyecto;
import Controlador.Tarea;

import java.sql.*;
import java.util.ArrayList;

public class DAOTarea implements DAO<Tarea> {
    private String DB_JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL = "jdbc:h2:tcp://localhost/~/base";
    private String DB_USER = "sa";
    private String DB_PASSWORD = "";


    @Override
    public void guardar(Tarea elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("INSERT INTO Tarea (titulo, descripcion, estimacion, horasreales) VALUES (?, ?, ? ,?)");
            //preparedStatement.setInt(1, elemento.getId());
            preparedStatement.setString(1, elemento.getTitulo());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setInt(3, elemento.getEstimacion());
            preparedStatement.setInt(4, elemento.getHorasReales());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se agregaron " + res + " tarea(s).");
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            int empleadoId = 0;
            preparedStatement = connection.prepareStatement("SELECT empleado_id FROM Tarea WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                empleadoId = resultSet.getInt("empleado_id");
            }

            preparedStatement = connection.prepareStatement("DELETE FROM Tarea  WHERE id=?");
            preparedStatement.setInt(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se elimino " + res);

            preparedStatement = connection.prepareStatement("UPDATE Empleado SET disponible=true WHERE id=?");
            preparedStatement.setInt(1, empleadoId);
            int res2 = preparedStatement.executeUpdate();
            System.out.println("Se actualizó la disponibilidad del empleado" + res2);

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void modificar(Tarea elemento) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET titulo=?, descripcion=?, estimacion=?, horasreales=? WHERE id=?");
            preparedStatement.setString(1, elemento.getTitulo());
            preparedStatement.setString(2, elemento.getDescripcion());
            preparedStatement.setInt(3, elemento.getEstimacion());
            preparedStatement.setInt(4, elemento.getHorasReales());
            preparedStatement.setInt(5, elemento.getId());
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }

    }

    @Override
    public Tarea buscar(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Tarea tarea = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea  WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tarea = new Tarea();
                //tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                //tarea.setHorasReales(resultSet.getInt("horasReales"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return tarea;
    }

    @Override
    public ArrayList buscarTodos() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Tarea> datos = new ArrayList<>();
        Tarea tarea;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tarea = new Tarea();
                //tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                tarea.setHorasReales(resultSet.getInt("horasReales"));
                datos.add(tarea);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return datos;
    }

    public void asignarEmpleado(int idTarea, int idEmpleado) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("UPDATE Tarea SET empleado_id=? WHERE id=?");
            preparedStatement.setInt(1, idEmpleado);
            preparedStatement.setInt(2, idTarea);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res);

            preparedStatement = connection.prepareStatement("UPDATE Empleado SET disponible=false where id=?");
            preparedStatement.setInt(1, idEmpleado);
            int res2 = preparedStatement.executeUpdate();
            System.out.println("Se modificaron " + res2 + " empleados.");

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasSinEmpleadosAsignados() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea WHERE empleado_id IS NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareasSinEmpleadosAsignados = new ArrayList<>();

            while (resultSet.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));

                tareasSinEmpleadosAsignados.add(tarea);
            }
            return tareasSinEmpleadosAsignados;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public void eliminarEmpleado(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            preparedStatement = connection.prepareStatement("SELECT empleado_id FROM Tarea where id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int empleadoId = 0;
            if (resultSet.next()) {
                empleadoId = resultSet.getInt("empleado_id");
            }

            preparedStatement = connection.prepareStatement("UPDATE Tarea SET empleado_id=NULL where id=?");
            preparedStatement.setInt(1, id);
            int res = preparedStatement.executeUpdate();
            System.out.println("Se elimino el empleado" + res);

            preparedStatement = connection.prepareStatement("UPDATE Empleado SET disponible=true WHERE id=?");
            preparedStatement.setInt(1, empleadoId);
            int res2 = preparedStatement.executeUpdate();
            System.out.println("Se actualizó la disponibilidad del empleado" + res2);

        } catch (ClassNotFoundException | SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareaSinProyecto() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT id, titulo FROM Tarea WHERE id_proyecto IS NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareas = new ArrayList<>();

            while (resultSet.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));

                tareas.add(tarea);
            }
            return tareas;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasAsignadas(int idProyecto) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT id, titulo, descripcion, estimacion, horasReales, id_proyecto FROM Tarea WHERE id_proyecto IS NOT NULL AND id_proyecto = ?");
            preparedStatement.setInt(1, idProyecto);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareasAsignadas = new ArrayList<>();

            while (resultSet.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                tarea.setHorasReales(resultSet.getInt("horasReales"));
                tarea.setIdProyecto(resultSet.getInt("id_proyecto"));

                tareasAsignadas.add(tarea);
            }
            return tareasAsignadas;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTodasLasTareas() throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM Tarea");
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
                tareas.add(tarea);
            }
            return tareas;
        } catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerProyectosConTareas(int idProyecto) throws DAOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT horasreales, empleado_id FROM Tarea WHERE id_proyecto=?");
            preparedStatement.setInt(1, idProyecto);
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareas = new ArrayList<>();

            while (resultSet.next()){
                Tarea tarea = new Tarea();
                tarea.setHorasReales(resultSet.getInt("horasreales"));
                tarea.setEmpleado_id(resultSet.getInt("empleado_id"));
                tareas.add(tarea);
            }
            return tareas;
        } catch (SQLException | ClassNotFoundException e){
            throw new DAOException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasConProyecto() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT id, titulo, descripcion, estimacion, horasReales, id_proyecto FROM Tarea WHERE id_proyecto IS NOT NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Tarea> tareasAsignadas = new ArrayList<>();

            while (resultSet.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(resultSet.getInt("id"));
                tarea.setTitulo(resultSet.getString("titulo"));
                tarea.setDescripcion(resultSet.getString("descripcion"));
                tarea.setEstimacion(resultSet.getInt("estimacion"));
                tarea.setHorasReales(resultSet.getInt("horasReales"));
                tarea.setIdProyecto(resultSet.getInt("id_proyecto"));

                tareasAsignadas.add(tarea);
            }
            return tareasAsignadas;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
