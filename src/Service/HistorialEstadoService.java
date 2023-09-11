package Service;

import Controlador.HistorialEstado;
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
}
