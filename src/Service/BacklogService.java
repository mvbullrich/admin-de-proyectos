package Service;

import Controlador.Backlog;
import Controlador.Tarea;
import Model.DAOBacklog;
import Model.DAOException;

import java.util.ArrayList;

public class BacklogService {
    private DAOBacklog daoBacklog;

    public BacklogService(){ daoBacklog = new DAOBacklog(); }

    public void guardarBacklog(Backlog backlog) throws ServiceException {
        try{
            daoBacklog.guardar(backlog);
            System.out.println("Se guardó el backlog correctamente");
        } catch (DAOException e){
            System.out.println("Error al guardar el backlog");
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminarBacklog(int id) throws ServiceException{
        try{
            daoBacklog.eliminar(id);
            System.out.println("Se eliminó el backlog correctamente");
        }catch (DAOException e){
            System.out.println("Error al guardar el backlog");
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(Backlog backlog) throws ServiceException{
        try{
            daoBacklog.modificar(backlog);
            System.out.println("Se modificó el backlog correctamente");
        } catch (DAOException e){
            System.out.println("Error al modificar el backlog");
            throw new ServiceException(e.getMessage());
        }
    }

    public Backlog buscarBacklog(int id) throws ServiceException{
        Backlog backlog = null;
        try{
            backlog = daoBacklog.buscar(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return backlog;
    }

    public void agregarTarea(int idBacklog, int idTarea) throws ServiceException{
        try {
            daoBacklog.agregarTarea(idBacklog, idTarea);
            System.out.println("Se agregó la tarea al backlog correctamente");
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void eliminarTarea(Tarea tarea) throws ServiceException{
        try{
            daoBacklog.eliminarTarea(tarea);
            System.out.println("Tarea eliminada del backlog correctamente");
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Backlog> obtenerBacklogs() throws ServiceException{
        try {
            return daoBacklog.obtenerBacklogs();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasSinBacklog() throws ServiceException{
        try{
            return daoBacklog.obtenerTareasSinBacklog();
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasPorBacklog(int idBacklog) throws ServiceException{
        try{
            return daoBacklog.obtenerTareasPorBacklog(idBacklog);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasConBacklog() throws ServiceException{
        try{
            return daoBacklog.obtenerTareasConBacklog();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
