package Entidades;

/**
 *
 * @author Enrique Osuna
 */
public class Especialidad {
    
    private int id;
    private String nombre;

    public Especialidad(int id, String nombre) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID de la especialidad debe ser positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre de la especialidad no puede estar vacío.");
        }
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
