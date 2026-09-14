package funcionalidad;

public class Posicion {
    private String nombre;

    public Posicion(String nombre) {
        setNombre(nombre);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la posición no puede estar vacío.");
        }
        this.nombre = nombre;
    }
}
