package funcionalidad;
import java.util.*;

public class Torneo {
    private String nombre;
    private List<Equipo> equipos;
    private List<Partido> partidos;

    public Torneo(String nombre) {
        setNombre(nombre);
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
    }

    public void agregarEquipo(Equipo equipo) {
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(equipo.getNombre())) {
                throw new IllegalArgumentException("El equipo ya existe en el torneo.");
            }
        }
        equipos.add(equipo);
    }

    public void programarPartido(Partido partido) {
        partidos.add(partido);
    }

    // Clase auxiliar interna para la tabla de posiciones
    public static class EstadisticaEquipo {
        private String nombreEquipo;
        private int partidosJugados;
        private int puntos;

        public EstadisticaEquipo(String nombreEquipo) {
            this.nombreEquipo = nombreEquipo;
            this.partidosJugados = 0;
            this.puntos = 0;
        }

        public String getNombreEquipo() { return nombreEquipo; }
        public int getPartidosJugados() { return partidosJugados; }
        public int getPuntos() { return puntos; }
    }

    public List<EstadisticaEquipo> calcularTablaPosiciones() {
        Map<String, EstadisticaEquipo> stats = new HashMap<>();

        for (Equipo e : equipos) {
            stats.put(e.getNombre(), new EstadisticaEquipo(e.getNombre()));
        }

        for (Partido p : partidos) {
            if (p.isJugado()) {
                EstadisticaEquipo local = stats.get(p.getEquipoLocal().getNombre());
                EstadisticaEquipo visitante = stats.get(p.getEquipoVisitante().getNombre());

                if (local != null && visitante != null) {
                    local.partidosJugados++;
                    visitante.partidosJugados++;

                    if (p.getGolesLocal() > p.getGolesVisitante()) {
                        local.puntos += 3;
                    } else if (p.getGolesLocal() < p.getGolesVisitante()) {
                        visitante.puntos += 3;
                    } else {
                        local.puntos += 1;
                        visitante.puntos += 1;
                    }
                }
            }
        }

        List<EstadisticaEquipo> tabla = new ArrayList<>(stats.values());
        tabla.sort((a, b) -> Integer.compare(b.getPuntos(), a.getPuntos()));
        return tabla;
    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        for (Equipo equipo : equipos) {
            for (Jugador jugador : equipo.getJugadores()) {
                if (jugador.getNombre().equalsIgnoreCase(nombre)) {
                    return jugador;
                }
            }
        }
        return null;
    }

    public String generarReporteFinal() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("         REPORTE FINAL DEL TORNEO        \n");
        sb.append("=========================================\n");
        sb.append("Torneo: ").append(nombre).append("\n");
        sb.append("Total Equipos: ").append(equipos.size()).append("\n");
        sb.append("Total Partidos Programados: ").append(partidos.size()).append("\n\n");

        sb.append("--- TABLA DE POSICIONES FINAL ---\n");
        List<EstadisticaEquipo> tabla = calcularTablaPosiciones();
        for (EstadisticaEquipo est : tabla) {
            sb.append(String.format("Equipo: %-15s | PJ: %-2d | Pts: %-2d\n",
                    est.getNombreEquipo(), est.getPartidosJugados(), est.getPuntos()));
        }
        sb.append("=========================================\n");
        return sb.toString();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del torneo no puede estar vacío.");
        }
        this.nombre = nombre;
    }
    public List<Equipo> getEquipos() { return equipos; }
    public List<Partido> getPartidos() { return partidos; }
}
