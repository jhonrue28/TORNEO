package funcionalidad;

public class Jugador extends Persona{
    private Posicion posicion;
    private int numeroCamiseta;

    public Jugador(String nombre, String documento, int edad, Posicion posicion, int numeroCamiseta) {
        super(nombre, documento, edad);
        setPosicion(posicion);
        setNumeroCamiseta(numeroCamiseta);
    }

    @Override
    public String mostrarRol() {
        return "Rol: Jugador | Posición: " + (posicion != null ? posicion.getNombre() : "Sin posición") +
                " | Camiseta: #" + numeroCamiseta;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        if (posicion == null) {
            throw new IllegalArgumentException("La posición no puede ser nula.");
        }
        this.posicion = posicion;
    }

    public int getNumeroCamiseta() {
        return numeroCamiseta;
    }

    public void setNumeroCamiseta(int numeroCamiseta) {
        if (numeroCamiseta <= 0) {
            throw new IllegalArgumentException("El número de camiseta debe ser mayor a 0.");
        }
        this.numeroCamiseta = numeroCamiseta;
    }
}
