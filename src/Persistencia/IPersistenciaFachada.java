/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia;

import Entidades.*;

/**
 *
 * @author Enrique Osuna
 */
import java.util.List;

public interface IPersistenciaFachada {
    
    // Pacientes
    void agregarPaciente(Paciente paciente) throws Exception;
    Paciente obtenerPacientePorId(int id) throws Exception; 
    List<Paciente> listarPacientes() throws Exception;
    void actualizarPaciente(Paciente paciente) throws Exception;
    void eliminarPaciente(int id);
    List<Paciente> filtrarPacientes(String direccion, Integer edadMin, Integer edadMax);
    
    // Médicos
    void agregarMedico(Medico medico) throws Exception;
    Medico obtenerMedicoPorId(int id) throws Exception; 
    List<Medico> listarMedicos() throws Exception;
    public void actualizarMedico(Medico actualizarMedico) throws Exception; 
    public void eliminarMedico(Medico medicos) throws Exception;
    

    // Especialidades
    void agregarEspecialidad(Especialidad especialidad) throws Exception;
    Especialidad obtenerEspecialidadPorId(int id) throws Exception;
    List<Especialidad> listarEspecialidades() throws Exception;

    // Equipos Médicos
    void agregarEquipoMedico(EquipoMedico equipo) throws Exception;
    void actualizarCantidadEquipo(int id, int cantidad) throws Exception;
    List<EquipoMedico> listarEquiposMedicos() throws Exception;
    public EquipoMedico obtenerEquipoPorId(int id) throws Exception;
    public List<EquipoMedico> filtrarEquipos(String nombreFiltro, int cantidadMinima, int cantidadMaxima) throws Exception;
    public void eliminarEquipoMedico(EquipoMedico equipo) throws Exception;
    public void actualizarEquipoMedico(EquipoMedico equipo) throws Exception;
    public void inventariarEquipoMedico(int id, int cantidadAñadir) throws Exception;
    public void desinventariarEquipoMedico(int id, int cantidadQuitar) throws Exception;
    
    // Consultas
    void programarConsulta(Consulta consulta) throws Exception;
    List<Consulta> listarConsultas() throws Exception;

}


