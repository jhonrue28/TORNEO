package funcionalidad;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private String nombre;
    private List<Jugador> jugadores;
    private Entrenador entrenador;

    public Equipo(String nombre) {
        setNombre(nombre);
        this.jugadores = new ArrayList<>();
    }

    // Método estático de validación requerido[cite: 1]
    public static boolean esNombreValido(String nombre) {
        return nombre != null && !nombre.trim().isEmpty();
    }

    public void agregarJugador(Jugador jugador) {
        if (jugador != null) {
            this.jugadores.add(jugador);
        }
    }

    public void asignarEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
        if (entrenador != null && entrenador.getEquipo() != this) {
            entrenador.setEquipo(this);
        }
    }

    // Extensión opcional: Sobrecarga de métodos registrar()[cite: 1]
    public void registrar(Jugador jugador) {
        agregarJugador(jugador);
    }

    public void registrar(Entrenador entrenador) {
        asignarEntrenador(entrenador);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (!esNombreValido(nombre)) {
            throw new IllegalArgumentException("El nombre del equipo no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }
}
