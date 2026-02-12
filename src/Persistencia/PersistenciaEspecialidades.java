package Persistencia;

import Entidades.Especialidad;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaEspecialidades {
    
    private List<Especialidad> especialidades;

    public PersistenciaEspecialidades() {
        this.especialidades = new ArrayList<>();
    }

    /**
     * método para agregar una especialidad médica
     * @param especialidad
     * @throws Exception si ya existe
     */
    public void agregarEspecialidad(Especialidad especialidad) throws Exception {
        // Validar que el ID sea único
        for (Especialidad e : especialidades) {
            if (e.getId() == especialidad.getId()) {
                throw new Exception("Ya existe una especialidad con ID " + especialidad.getId());
            }
        }
        this.especialidades.add(especialidad);
    }

    /**
     * método para obtener información de una especialidad por id
     * @param id a buscar
     * @return especialidad
     * @throws Exception si no se encuentra
     */
    public Especialidad obtenerEspecialidadPorId(int id) throws Exception {
        for (Especialidad e : especialidades) {
            if (e.getId() == id) {
                return e;
            }
        }
        throw new Exception("No se encontró una especialidad con ID " + id);
    }

    public List<Especialidad> listarEspecialidades() {
        return new ArrayList<>(especialidades);
    }
}

