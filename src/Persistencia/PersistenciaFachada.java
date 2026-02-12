/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

/**
 *
 * @author Enrique Osuna
 */
import Entidades.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenciaFachada implements IPersistenciaFachada {

    private PersistenciaPacientes persistenciaPacientes;
    private PersistenciaMedicos persistenciaMedicos;
    private PersistenciaEspecialidades persistenciaEspecialidades;
    private PersistenciaInventarios persistenciaInventarios;
    private PersistenciaConsultas persistenciaConsultas;
    
    public PersistenciaFachada() {
        this.persistenciaPacientes = new PersistenciaPacientes();
        this.persistenciaMedicos = new PersistenciaMedicos();
        this.persistenciaEspecialidades = new PersistenciaEspecialidades();
        this.persistenciaInventarios = new PersistenciaInventarios();
        this.persistenciaConsultas = new PersistenciaConsultas();
    }

    // =========================
    //       PACIENTES
    // =========================
    @Override
    public void agregarPaciente(Paciente paciente) throws Exception {
        persistenciaPacientes.agregarPaciente(paciente);
    }

    @Override
    public Paciente obtenerPacientePorId(int id) throws Exception {
        return persistenciaPacientes.obtenerPacientePorId(id);
    }

    @Override
    public List<Paciente> listarPacientes() throws Exception {
        return persistenciaPacientes.listarPacientes();
    }
    
    @Override
    public void actualizarPaciente(Paciente paciente) throws Exception {
        persistenciaPacientes.actualizarPaciente(paciente);
    }
  @Override
                public void eliminarPaciente(int id) {
                    try {
                        persistenciaPacientes.eliminarPaciente(id);
                    } catch (Exception ex) {
                        Logger.getLogger(PersistenciaFachada.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   

    public List<Paciente> filtrarPacientes(String direccion, Integer edadMin, Integer edadMax) {
        return persistenciaPacientes.filtrarPacientes(direccion, edadMin, edadMax);
    }
    

    // =========================
    //       MÉDICOS
    // =========================
    @Override
    public void agregarMedico(Medico medico) throws Exception {
        // Validar que la especialidad exista (se asume que, si existe, no es null)
        if (medico.getEspecialidad() == null) {
            throw new Exception("La especialidad asociada al médico es inválida o no existe.");
        }
        persistenciaMedicos.agregarMedico(medico);
    }

    @Override
    public Medico obtenerMedicoPorId(int id) throws Exception {
        return persistenciaMedicos.obtenerMedicoPorId(id);
    }
    
     
    public void actualizarMedico(Medico actualizarMedico) throws Exception {
    persistenciaMedicos.actualizarMedico(actualizarMedico);

    }
    
    
     @Override
    
    public void eliminarMedico(Medico medico) throws Exception {
    persistenciaMedicos.eliminarMedico(medico.getId());
}


    @Override
    public List<Medico> listarMedicos() throws Exception {
        return persistenciaMedicos.listarMedicos();
    }

    // =========================
    //   ESPECIALIDADES
    // =========================
    @Override
    public void agregarEspecialidad(Especialidad especialidad) throws Exception {
        persistenciaEspecialidades.agregarEspecialidad(especialidad);
    }

    @Override
    public Especialidad obtenerEspecialidadPorId(int id) throws Exception {
        return persistenciaEspecialidades.obtenerEspecialidadPorId(id);
    }

    @Override
    public List<Especialidad> listarEspecialidades() throws Exception {
        return persistenciaEspecialidades.listarEspecialidades();
    }

    // =========================
    //  EQUIPOS MÉDICOS
    // =========================
    @Override
    public void agregarEquipoMedico(EquipoMedico equipo) throws Exception {
        persistenciaInventarios.agregarEquipoMedico(equipo);
    }
    
    
    public EquipoMedico obtenerEquipoPorId(int id) throws Exception {
        return persistenciaInventarios.obtenerEquipoPorId(id);
    }

    @Override
    public void actualizarCantidadEquipo(int id, int cantidad) throws Exception {
        persistenciaInventarios.actualizarCantidadEquipo(id, cantidad);
    }

    @Override
    public List<EquipoMedico> listarEquiposMedicos() throws Exception {
        return persistenciaInventarios.listarEquiposMedicos();
    }
    
    
public List<EquipoMedico> filtrarEquipos(String nombreFiltro, int cantidadMinima, int cantidadMaxima) throws Exception {
    return persistenciaInventarios.filtrarEquipos(nombreFiltro, cantidadMinima, cantidadMaxima);
}

 
public void eliminarEquipoMedico(EquipoMedico equipo) throws Exception {
    persistenciaInventarios.eliminarEquipoMedico(equipo.getId());
}

 
public void actualizarEquipoMedico(EquipoMedico equipo) throws Exception {
    persistenciaInventarios.actualizarEquipoMedico(equipo);
}


public void inventariarEquipoMedico(int id, int cantidadAñadir) throws Exception {
    persistenciaInventarios.inventariarEquipoMedico(id, cantidadAñadir);
}

public void desinventariarEquipoMedico(int id, int cantidadQuitar) throws Exception {
    persistenciaInventarios.desinventariarEquipoMedico(id, cantidadQuitar);
}

    // =========================
    //      CONSULTAS
    // =========================
    @Override
    public void programarConsulta(Consulta consulta) throws Exception {
        persistenciaConsultas.programarConsulta(consulta);
    }

    @Override
    public List<Consulta> listarConsultas() throws Exception {
        return persistenciaConsultas.listarConsultas();
    }
}


