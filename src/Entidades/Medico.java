package Entidades;

/**
 *
 * @author Enrique Osuna
 */
public class Medico {
    
    private int id;
    private String nombre;
    private Especialidad especialidad;

    public Medico(int id, String nombre, Especialidad especialidad) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del médico debe ser positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del médico no puede estar vacío.");
        }
        if (especialidad == null) {
            throw new Exception("La especialidad no puede ser nula.");
        }
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
}
