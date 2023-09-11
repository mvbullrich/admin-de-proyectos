package Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class AdministradorProyectos {
    private ArrayList<Proyecto> proyectos;
    private ArrayList<Empleado> empleados;
    private ArrayList<EstadoTarea> estadosTarea;


    public AdministradorProyectos() {
        this.proyectos = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.estadosTarea = new ArrayList<>();
    }

    public void agregarProyecto(Proyecto proyecto){
        proyectos.add(proyecto);
    }

    public void eliminarProyecto(Proyecto proyecto){
        proyectos.remove(proyecto);
    }

    public void agregarEmpleado(Empleado empleado){
        empleados.add(empleado);
    }

    public void eliminarEmpleado(Empleado empleado){
        empleados.remove(empleado);
    }

    public void agregarEstadoTarea(EstadoTarea estado){
        estadosTarea.add(estado);
    }

    public void eliminarEstadoTarea(EstadoTarea estado){
        estadosTarea.remove(estado);
    }

    public ArrayList<Empleado> obtenerEmpleadosDisponibles(){
        ArrayList<Empleado> empleadosDisponibles = new ArrayList<>();
        for (Empleado empleado : empleados) {
            if (empleado.isDisponible()) {
                empleadosDisponibles.add(empleado);
            }
        }
        return empleadosDisponibles;
    }

    public void asignanarEmpleadoProyecto(Empleado empleado, Proyecto proyecto){
        proyecto.agregarEmpleado(empleado);

        empleado.setDisponible(false);
    }

    public void actualizarEstadoTarea(Tarea tarea, int idTarea, String estado, Date fecha, int idResponsable){
        tarea.actualizarEstado(idTarea, estado, fecha, idResponsable);
    }

    public double obtenerCostoProyecto(Proyecto proyecto){
        return proyecto.obtenerCostoDinero();
    }

    public double obtenerCostoTarea(Tarea tarea){
        return tarea.obtenerCosto();
    }

    public double obtenerCostoHoras(Proyecto proyecto){
        return proyecto.obtenerCostoHoras();
    }


    @Override
    public String toString() {
        return "AdministradorProyectos \n" +
                "Proyectos = " + proyectos +
                "\n Empleados = " + empleados +
                "\n EstadosTarea = " + estadosTarea;
    }
}
