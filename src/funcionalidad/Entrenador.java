package funcionalidad;

public class Entrenador extends Persona{
    private int anosExperiencia;
    private Equipo equipo;

    public Entrenador(String nombre, String documento, int edad, int anosExperiencia) {
        super(nombre, documento, edad);
        setAnosExperiencia(anosExperiencia);
    }

    @Override
    public String mostrarRol() {
        return "Rol: Entrenador | Años de Experiencia: " + anosExperiencia;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        if (anosExperiencia < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos.");
        }
        this.anosExperiencia = anosExperiencia;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }
}
