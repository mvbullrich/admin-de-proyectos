package Controlador;

import java.util.ArrayList;

public class Empleado{
    private int id;
    private String nombre;
    private double costoPorHora;
    private boolean disponible;

    public Empleado(int id, String nombre, double costoPorHora) {
        this.id = id;
        this.nombre = nombre;
        this.costoPorHora = costoPorHora;
        this.disponible = true; //asumo que todo nuevo empleado esta disponoble hasta que se le asigne una tarea o proyecto
    }

    public Empleado(){}

    public int getId(){ return id;}
    public String getNombre() {
        return nombre;
    }

    public double getCostoPorHora() {
        return costoPorHora;
    }
    public void setId(int id){ this.id = id;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCostoPorHora(double costoPorHora) {
        this.costoPorHora = costoPorHora;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }



    @Override
    public String toString() {
        return  "ID: " + id + ", Nombre: " + nombre +
                ", CostoPorHora: " + costoPorHora;
    }
}
