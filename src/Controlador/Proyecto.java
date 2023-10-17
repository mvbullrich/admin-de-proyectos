package Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Proyecto {
    private int id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<Tarea> tareas;
    private ArrayList<Empleado> empleados;

    public Proyecto(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tareas = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }

    public Proyecto(){}

    public int getId(){
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }

    public void agregarTarea(Tarea tarea){
        tareas.add(tarea);
    }

    public void eliminarTarea(Tarea tarea){
        tareas.remove(tarea);
    }

    public void agregarEmpleado(Empleado empleado){
        empleados.add(empleado);
    }

    public void eliminarEmpleado(Empleado empleado){
        empleados.remove(empleado);
    }

    public double obtenerCostoDinero(){
        double total = 0;
        for(Tarea tarea:tareas){
            total += tarea.obtenerCosto();
        }
        return total;
    }

    public double obtenerCostoHoras(){
        double costoHoras = 0;
        for(Tarea tarea:tareas){
            costoHoras += tarea.getHorasReales();
        }
        return costoHoras;
    }


    @Override
    public String toString() {
        return titulo;
    }
}
