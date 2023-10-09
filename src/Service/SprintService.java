package Service;

import Controlador.Sprint;
import Controlador.Tarea;
import Model.DAOException;
import Model.DAOSprint;

import java.util.ArrayList;

public class SprintService {
    private DAOSprint daoSprint;

    public SprintService(){
        daoSprint = new DAOSprint();
    }

    public void guardarSprint(Sprint sprint) throws ServiceException{
        try{
            daoSprint.guardar(sprint);
            System.out.println("Se guardó el Sprint correctamente.");
        } catch (DAOException e){
            System.out.println("Error al guardar el Sprint: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminarSprint(int id) throws ServiceException{
        try{
            daoSprint.eliminar(id);
            System.out.println("Se eliminó el Sprint correctamente");
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificarSprint(Sprint sprint) throws ServiceException{
        try {
            daoSprint.modificar(sprint);
            System.out.println("Se modificó el Sprint correctamente.");
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public Sprint buscarSpint(int id) throws ServiceException{
        Sprint sprint = null;
        try {
            sprint = daoSprint.buscar(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return sprint;
    }

    public void agregarTarea(int idSprint, int idTarea) throws ServiceException{
        try{
            daoSprint.agregarTarea(idSprint, idTarea);
            System.out.println("Tarea agregada al sprint correctamente");
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void quitarTarea(Tarea tarea) throws ServiceException{
        try{
            daoSprint.quitarTarea(tarea);
            System.out.println("Tarea eliminada del sprint");
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareaSinSprint() throws ServiceException{
        try{
            return daoSprint.obtenerTareasSinSprint();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Sprint> obtenerSprints() throws ServiceException{
        try{
            return daoSprint.obtenerSprints();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasConSprint() throws ServiceException{
        try{
            return daoSprint.obtenerTareasConSprint();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasPorSprint(int idSprint) throws ServiceException{
        try{
            return daoSprint.obtenerTareasPorSprint(idSprint);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
