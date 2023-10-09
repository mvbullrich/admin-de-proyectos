package Service;

import Controlador.Proyecto;
import Controlador.Tarea;
import Model.DAOProyecto;
import  Model.DAOException;

import java.util.ArrayList;

public class ProyectoService {

    private DAOProyecto daoProyecto;

    public ProyectoService() {
        daoProyecto = new DAOProyecto();
    }

    public void guardarProyecto(Proyecto proyecto) throws ServiceException {
        try {
            daoProyecto.guardar(proyecto);
            System.out.println("Proyecto agregado correctamente");
        } catch (DAOException e) {
            System.out.println("Error al agregar el proyecto: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminarProyecto(int id) throws ServiceException {
        try {
            daoProyecto.eliminar(id);
            System.out.println("Proyecto eliminado correctamente");
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(Proyecto proyecto) throws ServiceException {
        try {
            daoProyecto.modificar(proyecto);
            System.out.println("Se modificó el proyecto correctamente.");
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Proyecto buscarProyecto(int id) throws ServiceException {
        Proyecto proyecto = null;
        try {
            proyecto = daoProyecto.buscar(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return proyecto;
    }

    public ArrayList<Proyecto> obtenerProyectos() throws ServiceException {
        try {
            return daoProyecto.obtenerProyectos();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void asignarTareaProyecto(Tarea tarea, Proyecto proyecto) throws ServiceException {
        try {
            daoProyecto.asignarTareaProyecto(tarea, proyecto);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void desasignarTarea(Tarea tarea) throws ServiceException{
        try {
            daoProyecto.desasignarTarea(tarea);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public double calcularCostoHoras(int idProyecto) throws ServiceException{
        try{
            return daoProyecto.calcularCostoHoras(idProyecto);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public double calcularCostoDinero(int idProyecto) throws ServiceException{
        try{
            return daoProyecto.calcularCostoDinero(idProyecto);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
