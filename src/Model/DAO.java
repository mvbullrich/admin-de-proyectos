package Model;

import Controlador.Proyecto;
import Controlador.Tarea;

import java.util.ArrayList;


public interface DAO<T>{
    public void guardar(T elemento) throws DAOException;
    public void eliminar(int id) throws DAOException;
    public void modificar(T elemento) throws DAOException;
    public T buscar(int id) throws DAOException;
    //public ArrayList buscarTodos() throws DAOException;
}
