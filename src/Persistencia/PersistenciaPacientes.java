package Persistencia;

/**
 *
 * @author Enrique Osuna
 */
import Entidades.Paciente;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaPacientes {
    
    private List<Paciente> pacientes;

    public PersistenciaPacientes() {
        this.pacientes = new ArrayList<>();
    }

    public void agregarPaciente(Paciente paciente) throws Exception {
        // Validar que el ID sea único
        for (Paciente p : pacientes) {
            if (p.getId() == paciente.getId()) {
                throw new Exception("Ya existe un paciente con ID " + paciente.getId());
            }
        }
        // Validar que los datos del paciente sean válidos (ya se validan en el constructor)
        this.pacientes.add(paciente);
    }

    public Paciente obtenerPacientePorId(int id) throws Exception {
        for (Paciente p : pacientes) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new Exception("No se encontró un paciente con ID " + id);
    }
    
    public void actualizarPaciente(Paciente paciente) throws Exception {
        for (Paciente p : pacientes) {
            if (p.getId() == paciente.getId()) {
                p.setNombre(paciente.getNombre());
                p.setEdad(paciente.getEdad());                
                p.setDireccion(paciente.getDireccion());
                return;
            }
        }
        throw new Exception("No se encontró un paciente con ID  proporcionada");        
    }

  public List<Paciente> filtrarPacientes(String direccion, Integer edadMin, Integer edadMax) {
    List<Paciente> filtrado = new ArrayList<>();
    for (Paciente paciente: pacientes) {
        boolean fDireccion = (direccion == null || direccion.isEmpty() || paciente.getDireccion().equalsIgnoreCase(direccion));
        boolean fEdad = (edadMin == null || paciente.getEdad() >= edadMin) && (edadMax == null || paciente.getEdad() <= edadMax);

        if (fDireccion && fEdad) {
            filtrado.add(paciente);
        }
    }
    return filtrado;
}
    
  public void eliminarPaciente(int id) throws Exception {
        boolean encontrado = false;
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == id) {
                pacientes.remove(i);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Exception("No se encontró un paciente con ID" + id);
        }
    }
  
    
    public List<Paciente> listarPacientes() {
        return new ArrayList<>(pacientes);
    }
}

