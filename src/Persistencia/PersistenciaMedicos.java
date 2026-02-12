/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

/**
 *
 * @author Enrique Osuna
 */
import Entidades.Medico;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersistenciaMedicos {
    
    private List<Medico> medicos;

    public PersistenciaMedicos() {
        this.medicos = new ArrayList<>();
    }

    public void agregarMedico(Medico medico) throws Exception {
        // Validar que el ID sea único
        for (Medico m : medicos) {
            if (m.getId() == medico.getId()) {
                throw new Exception("Ya existe un médico con ID " + medico.getId());
            }
        }
        this.medicos.add(medico);
    }

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

