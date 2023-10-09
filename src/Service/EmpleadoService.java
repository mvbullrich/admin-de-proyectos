package Service;

import Controlador.Empleado;
import Model.DAOEmpleado;
import Model.DAOException;

import java.util.ArrayList;

public class EmpleadoService {
    private DAOEmpleado daoEmpleado;

    public EmpleadoService(){
        daoEmpleado = new DAOEmpleado();
    }

    public void guardarEmpleado(Empleado empleado) throws ServiceException {
        // Asegúrate de que la disponibilidad siempre sea true antes de guardar
        empleado.setDisponible(true);

        try {
            daoEmpleado.guardar(empleado);
            System.out.println("Empleado agregado correctamente");
        } catch (DAOException e) {
            System.out.println("Error al agregar el empleado: " + e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }


    public void eliminarEmpleado(int id) throws ServiceException{
        try{
            daoEmpleado.eliminar(id);
            System.out.println("Empleado eliminado correctamente");
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public void modificar(Empleado empleado) throws ServiceException{
        try{
            daoEmpleado.modificar(empleado);
            System.out.println("Empleado modificado correctamente.");;
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public Empleado buscarEmpleado(int id) throws ServiceException{
        Empleado empleado = null;
        try {
            empleado = daoEmpleado.buscar(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
        return empleado;
    }

    public ArrayList<Empleado> obtenerEmpleadosDisponibles() throws ServiceException {
        try {
            return daoEmpleado.obtenerEmpleadosDisponibles();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Empleado> obtenerTodosLosEmpleados() throws ServiceException{
        try{
            return daoEmpleado.obtenerTodosLosEmpleados();
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ArrayList<Empleado> obtenerEmpleadosAsignados() throws ServiceException{
        try {
            return daoEmpleado.obtenerEmpleadosAsignados();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
