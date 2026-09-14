package main;
import funcionalidad.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Torneo torneoActual = null;

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            String opcionStr = scanner.nextLine();
            int opcion = convertirAInt(opcionStr);

            switch (opcion) {
                case 1 -> crearTorneo();
                case 2 -> registrarEquipo();
                case 3 -> registrarIntegrante();
                case 4 -> programarPartido();
                case 5 -> registrarResultado();
                case 6 -> mostrarTablaPosiciones();
                case 7 -> buscarJugador();
                case 8 -> {
                    generarReporteYSalir();
                    salir = true;
                }
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- SISTEMA DE GESTIÓN DE TORNEO DEPORTIVO ---");
        System.out.println("1. Crear torneo");
        System.out.println("2. Registrar equipo");
        System.out.println("3. Registrar jugador/entrenador en un equipo");
        System.out.println("4. Programar partido");
        System.out.println("5. Registrar resultado de partido");
        System.out.println("6. Mostrar tabla de posiciones");
        System.out.println("7. Buscar jugador por nombre");
        System.out.println("8. Generar reporte final y salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int convertirAInt(String entrada) {
        try {
            return Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void crearTorneo() {
        System.out.print("Ingrese nombre del torneo: ");
        String nombre = scanner.nextLine();
        try {
            torneoActual = new Torneo(nombre);
            System.out.println("Torneo '" + nombre + "' creado exitosamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarEquipo() {
        if (validarTorneo()) return;
        System.out.print("Nombre del equipo: ");
        String nombre = scanner.nextLine();
        try {
            Equipo equipo = new Equipo(nombre);
            torneoActual.agregarEquipo(equipo);
            System.out.println("Equipo registrado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarIntegrante() {
        if (validarTorneo()) return;
        Equipo equipo = seleccionarEquipo();
        if (equipo == null) return;

        System.out.println("1. Registrar Jugador | 2. Registrar Entrenador");
        int tipo = convertirAInt(scanner.nextLine());

        System.out.print("Nombre: "); String nombre = scanner.nextLine();
        System.out.print("Documento: "); String doc = scanner.nextLine();
        System.out.print("Edad: "); int edad = convertirAInt(scanner.nextLine());

        try {
            if (tipo == 1) {
                System.out.print("Posición (ej. Portero, Delantero): ");
                String posNombre = scanner.nextLine();
                System.out.print("Número de Camiseta: ");
                int num = convertirAInt(scanner.nextLine());

                Jugador j = new Jugador(nombre, doc, edad, new Posicion(posNombre), num);
                equipo.registrar(j); // Sobrecarga[cite: 1]
                System.out.println("Jugador registrado con éxito.");
            } else if (tipo == 2) {
                System.out.print("Años de experiencia: ");
                int exp = convertirAInt(scanner.nextLine());

                Entrenador e = new Entrenador(nombre, doc, edad, exp);
                equipo.registrar(e); // Sobrecarga[cite: 1]
                System.out.println("Entrenador asignado con éxito.");
            } else {
                System.out.println("Tipo inválido.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error en los datos: " + e.getMessage());
        }
    }

    private static void programarPartido() {
        if (validarTorneo()) return;
        System.out.println("--- Seleccionar Equipo Local ---");
        Equipo local = seleccionarEquipo();
        System.out.println("--- Seleccionar Equipo Visitante ---");
        Equipo visitante = seleccionarEquipo();

        if (local == null || visitante == null) return;

        System.out.print("Fecha del partido (DD/MM/AAAA): ");
        String fecha = scanner.nextLine();

        try {
            Partido partido = new Partido(local, visitante, fecha);
            torneoActual.programarPartido(partido);
            System.out.println("Partido programado con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarResultado() {
        if (validarTorneo() || torneoActual.getPartidos().isEmpty()) {
            System.out.println("No hay partidos programados.");
            return;
        }

        List<Partido> partidos = torneoActual.getPartidos();
        for (int i = 0; i < partidos.size(); i++) {
            Partido p = partidos.get(i);
            System.out.printf("%d. %s vs %s [%s] - Jugado: %s\n",
                    (i + 1), p.getEquipoLocal().getNombre(), p.getEquipoVisitante().getNombre(), p.getFecha(), p.isJugado() ? "Sí" : "No");
        }

        System.out.print("Seleccione el número del partido: ");
        int idx = convertirAInt(scanner.nextLine()) - 1;

        if (idx < 0 || idx >= partidos.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Partido partido = partidos.get(idx);
        System.out.print("Goles " + partido.getEquipoLocal().getNombre() + ": ");
        int gLocal = convertirAInt(scanner.nextLine());
        System.out.print("Goles " + partido.getEquipoVisitante().getNombre() + ": ");
        int gVis = convertirAInt(scanner.nextLine());

        try {
            partido.registrarResultado(gLocal, gVis);
            System.out.println("Resultado registrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar resultado: " + e.getMessage());
        }
    }

    private static void mostrarTablaPosiciones() {
        if (validarTorneo()) return;
        List<Torneo.EstadisticaEquipo> tabla = torneoActual.calcularTablaPosiciones();

        System.out.println("\n--- TABLA DE POSICIONES ---");
        System.out.printf("%-15s | %-3s | %-6s\n", "Equipo", "PJ", "Puntos");
        System.out.println("--------------------------------");
        for (Torneo.EstadisticaEquipo e : tabla) {
            System.out.printf("%-15s | %-3d | %-6d\n", e.getNombreEquipo(), e.getPartidosJugados(), e.getPuntos());
        }
    }

    private static void buscarJugador() {
        if (validarTorneo()) return;
        System.out.print("Nombre del jugador a buscar: ");
        String nombre = scanner.nextLine();

        Jugador encontrado = torneoActual.buscarJugadorPorNombre(nombre);
        if (encontrado != null) {
            System.out.println("\nJugador Encontrado:");
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Documento: " + encontrado.getDocumento());
            System.out.println("Edad: " + encontrado.getEdad());
            System.out.println(encontrado.mostrarRol());
        } else {
            System.out.println("No se encontró ningún jugador con ese nombre.");
        }
    }

    private static void generarReporteYSalir() {
        if (torneoActual != null) {
            System.out.println(torneoActual.generarReporteFinal());
        } else {
            System.out.println("Saliendo sin torneo activo.");
        }
    }

    private static boolean validarTorneo() {
        if (torneoActual == null) {
            System.out.println("Primero debe crear un torneo (Opción 1).");
            return true;
        }
        return false;
    }

    private static Equipo seleccionarEquipo() {
        List<Equipo> equipos = torneoActual.getEquipos();
        if (equipos.isEmpty()) {
            System.out.println("No hay equipos registrados.");
            return null;
        }

        for (int i = 0; i < equipos.size(); i++) {
            System.out.println((i + 1) + ". " + equipos.get(i).getNombre());
        }
        System.out.print("Seleccione equipo: ");
        int idx = convertirAInt(scanner.nextLine()) - 1;

        if (idx < 0 || idx >= equipos.size()) {
            System.out.println("Selección inválida.");
            return null;
        }
        return equipos.get(idx);
    }
}