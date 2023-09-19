package Service;

import Controlador.Empleado;
import Controlador.Tarea;
import Model.DAO;
import Model.DAOTarea;
import Model.DAOException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TareaService {
    private DAOTarea daoTarea;

    public TareaService() {
        daoTarea = new DAOTarea();
    }

    public void guardarTarea(Tarea tarea) throws ServiceException {
        try {
            daoTarea.guardar(tarea);
            System.out.println("Tarea agregada correctamente");
        } catch (DAOException e) {
            System.out.println("Error al agregar la tarea: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminarTarea(int id) throws ServiceException {
        try {
            daoTarea.eliminar(id);
            System.out.println("Tarea eliminada correctamente");
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(Tarea tarea) throws ServiceException {
        try {
            daoTarea.modificar(tarea);
            System.out.println("Tarea modificada correctamente.");
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Tarea buscarTarea(int id) throws ServiceException {
        Tarea tarea = null;
        try {
            tarea = daoTarea.buscar(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
        return tarea;
    }

    public void asignarEmpleado(int idTarea, int idEmpleado) throws ServiceException {
        try {
            daoTarea.asignarEmpleado(idTarea, idEmpleado);
            System.out.println("Empleado asignado correctamente.");
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public void desasignarEmpleado(Empleado empleado) throws ServiceException{
        try {
            daoTarea.desasignarEmpleado(empleado);
            System.out.println("Empleado desasignado de la tarea");
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasSinEmpleadosAsignados() throws ServiceException {
        try {
            return daoTarea.obtenerTareasSinEmpleadosAsignados();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareas() throws ServiceException {
        try {
            return daoTarea.obtenerTareaSinProyecto();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasAsignadas(int idProyecto) throws ServiceException {
        try {
            return daoTarea.obtenerTareasAsignadas(idProyecto);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTodasLasTareas() throws ServiceException{
        try{
            return daoTarea.obtenerTodasLasTareas();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerProyectosConTareas(int idProyecto) throws ServiceException{
        try{
            return daoTarea.obtenerProyectosConTareas(idProyecto);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasConProyectos() throws ServiceException{
        try {
            return daoTarea.obtenerTareasConProyecto();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasSinEstado() throws ServiceException{
        try {
            return daoTarea.obtenerTareasSinEstado();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
