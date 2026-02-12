package Persistencia;

import Entidades.Medico;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersistenciaMedicos {
    
    private List<Medico> medicos;

    public PersistenciaMedicos() {
        this.medicos = new ArrayList<>();
    }

    /**
     * método para agregar médico
     * @param medico
     * @throws Exception si ya existe un médico
     */
    public void agregarMedico(Medico medico) throws Exception {
        // Validar que el ID sea único
        for (Medico m : medicos) {
            if (m.getId() == medico.getId()) {
                throw new Exception("Ya existe un médico con ID " + medico.getId());
            }
        }
        this.medicos.add(medico);
    }

    /**
     * médico a buscar
     * @param id del médico
     * @return médico
     * @throws Exception si no se encuentra
     */
    public Medico obtenerMedicoPorId(int id) throws Exception {
        for (Medico m : medicos) {
            if (m.getId() == id) {
                return m;
            }
        }
        throw new Exception("No se encontró un médico con ID " + id);
    }

    public List<Medico> listarMedicos() {
        return new ArrayList<>(medicos);
    }
    
    /**
     * método para actualizar un médico
     * @param medicoActualizado
     * @throws Exception si no se encontró
     */
    public void actualizarMedico(Medico medicoActualizado) throws Exception {
        boolean encontrado = false;
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medicoActualizado.getId()) {
                medicos.set(i, medicoActualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Exception("No se encontró un médico con ID " + medicoActualizado.getId() + " para actualizar.");
        }
    }

    /**
     * método para eliminar un médico
     * @param idMedico a buscar
     * @throws Exception si no se encuentra
     */
    public void eliminarMedico(int idMedico) throws Exception {
        Iterator<Medico> it = medicos.iterator();
        boolean encontrado = false;
        while (it.hasNext()) {
            Medico medico = it.next();
            if (medico.getId() == idMedico) {
                it.remove();
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Exception("No se encontró un médico con ID " + idMedico + " para eliminar.");
        }
    }
}

