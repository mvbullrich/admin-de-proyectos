package Controlador;

public class EstadoTarea {
    private String nombre;

    public EstadoTarea(String nombre) {
        this.nombre = nombre;
    }

    public EstadoTarea (){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "EstadoTarea{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
