# Sistema de Gestión de Torneo Deportivo

## Información de la Entrega
- **Integrantes**:
    - Jhonatan David Rueda Galeano

---

## Descripción del Proyecto
Este proyecto es una aplicación de consola desarrollada en **Java** para administrar torneos deportivos integrando conceptos avanzados de Programación Orientada a Objetos (POO).

El sistema permite:
1. Crear un torneo.
2. Registrar equipos (validando nombres únicos e independientes).
3. Registrar integrantes (*Jugadores* con posiciones asignadas y *Entrenadores* con años de experiencia).
4. Programar partidos entre dos equipos.
5. Registrar los resultados de los partidos (validando goles no negativos).
6. Calcular y mostrar la tabla de posiciones ordenada de mayor a menor puntaje[cite: 1].
7. Buscar jugadores por su nombre (búsqueda insensible a mayúsculas/minúsculas)[cite: 1].
8. Generar un reporte final formateado utilizando `StringBuilder`[cite: 1].

---

## Estructura del Proyecto

```text
src/
├── funcionalidad/
│   ├── Persona.java       (Clase abstracta base)
│   ├── Jugador.java       (Hereda de Persona)
│   ├── Entrenador.java    (Hereda de Persona)
│   ├── Posicion.java      (Modelo simple de posición)
│   ├── Equipo.java        (Gestión de integrantes y agregación)
│   ├── Partido.java       (Registro de encuentros y goles)
│   └── Torneo.java        (Composición y cálculo de posiciones)
└── main/
    └── Main.java          (Menú interactivo por consola)