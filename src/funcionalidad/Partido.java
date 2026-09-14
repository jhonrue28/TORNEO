package funcionalidad;

public class Partido {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private String fecha;
    private boolean jugado;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, String fecha) {
        if (equipoLocal == null || equipoVisitante == null) {
            throw new IllegalArgumentException("Los equipos no pueden ser nulos.");
        }
        if (equipoLocal.equals(equipoVisitante)) {
            throw new IllegalArgumentException("Un equipo no puede jugar contra sí mismo.");
        }
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fecha = fecha;
        this.jugado = false;
    }

    public void registrarResultado(int golesLocal, int golesVisitante) {
        if (golesLocal < 0 || golesVisitante < 0) {
            throw new IllegalArgumentException("Los goles no pueden ser negativos.");
        }
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jugado = true;
    }

    public Equipo getEquipoLocal() { return equipoLocal; }
    public Equipo getEquipoVisitante() { return equipoVisitante; }
    public int getGolesLocal() { return golesLocal; }
    public int getGolesVisitante() { return golesVisitante; }
    public String getFecha() { return fecha; }
    public boolean isJugado() { return jugado; }
}
