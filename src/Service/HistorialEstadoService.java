package Service;

import Controlador.HistorialEstado;
import Controlador.Tarea;
import Model.DAOException;
import Model.DAOHistorialEstado;

import java.util.ArrayList;

public class HistorialEstadoService {
    private DAOHistorialEstado daoHistorialEstado;

    public HistorialEstadoService(){
        daoHistorialEstado = new DAOHistorialEstado();
    }

    public void guardarHistorialEstado(HistorialEstado historialEstado) throws ServiceException{
        try {
            daoHistorialEstado.guardar(historialEstado);
            System.out.println("Se registró el estado correctamente");
        } catch (DAOException e){
            System.out.println("Error al registrar el estado: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<HistorialEstado> obtenerDatos() throws ServiceException{
        try{
            return daoHistorialEstado.obtenerDatos();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(HistorialEstado historial) throws ServiceException{
        try{
            daoHistorialEstado.modificar(historial);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<HistorialEstado> obtenerHistorialPorTarea(int idTarea) throws ServiceException{
        try{
            return daoHistorialEstado.obtenerHistorialPorTarea(idTarea);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Tarea> obtenerTareasPorEstado(String estado) throws ServiceException{
        try {
            return daoHistorialEstado.obtenerTareasPorEstado(estado);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
