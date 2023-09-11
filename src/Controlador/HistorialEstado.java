package Controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class HistorialEstado {
    private int id;
    private int idTarea;
    private String estado;
    private Date fecha;
    private int idResponsable;

    private String tituloTarea;
    private String nombreResponsable;

    public HistorialEstado(int idTarea, String estado, Date fecha, int idResponsable) {
        this.idTarea = idTarea;
        this.estado = estado;
        this.fecha = fecha;
        this.idResponsable = idResponsable;
    }

    public HistorialEstado() {
    }

    public int getId() {
        return id;
    }

    public int getIdTarea() {
        return idTarea;
    }

    public String getEstado() {
        return estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getIdResponsable() {
        return idResponsable;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIdResponsable(int idResponsable) {
        this.idResponsable = idResponsable;
    }


    public String getTituloTarea() {
        return tituloTarea;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setTituloTarea(String tituloTarea) {
        this.tituloTarea = tituloTarea;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }
}
