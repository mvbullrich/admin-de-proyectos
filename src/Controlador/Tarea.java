package Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private int estimacion;
    private int horasReales;
    private int empleado_id;
    private ArrayList<HistorialEstado> historialEstados;
    private int id_proyecto;
    private int id_sprint;
    private int id_backlog;

    public Tarea(int id, String titulo, String descripcion, int estimacion, int horasReales) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estimacion = estimacion;
        this.horasReales = horasReales;
        this.historialEstados = new ArrayList<>();
        this.empleado_id = empleado_id;
    }

    public Tarea(){}

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getEstimacion() {
        return estimacion;
    }

    public int getHorasReales() {
        return horasReales;
    }

    public int getEmpleado_id() {
        return empleado_id;
    }

    public ArrayList<HistorialEstado> getHistorialEstados() {
        return historialEstados;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstimacion(int estimacion) {
        this.estimacion = estimacion;
    }

    public void setHorasReales(int horasReales) {
        this.horasReales = horasReales;
    }

    public void setEmpleado_id(int empleado_id) {
        this.empleado_id = empleado_id;
    }

    public void setHistorialEstados(ArrayList<HistorialEstado> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public void asignarEmpleado(int id){
        this.empleado_id = empleado_id;
    }

    public void actualizarEstado(int idTarea, String estado, Date fecha, int idResponsable){
        HistorialEstado historial = new HistorialEstado(idTarea, estado, fecha, idResponsable);
        historialEstados.add(historial);
    }

    public ArrayList<HistorialEstado> consultarHistorialEstados(){
        return historialEstados;
    }

    public double obtenerCosto(){
        Empleado empleado = new Empleado();
        return empleado.getCostoPorHora() * horasReales;
    }

    public int getIdProyecto() {
        return id_proyecto;
    }

    public void setIdProyecto(int id_proyecto) {
        this.id_proyecto = id_proyecto;
    }

    public int getId_sprint() {
        return id_sprint;
    }

    public void setId_sprint(int id_sprint) {
        this.id_sprint = id_sprint;
    }

    public int getId_backlog() {
        return id_backlog;
    }

    public void setId_backlog(int id_backlog) {
        this.id_backlog = id_backlog;
    }

    @Override
    public String toString() {
        return  "ID: " + id +
                ", Titulo: " + titulo +
                ", Descripcion: " + descripcion +
                ", Estimacion: " + estimacion;
    }
}
