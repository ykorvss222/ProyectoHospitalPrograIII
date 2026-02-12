/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

/**
 *
 * @author Enrique Osuna
 */
import Entidades.Consulta;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class PersistenciaConsultas {
    
    private List<Consulta> consultas;

    public PersistenciaConsultas() {
        this.consultas = new ArrayList<>();
    }

    public void programarConsulta(Consulta consulta) throws Exception {
        // Validar que el ID sea único
        for (Consulta c : consultas) {
            if (c.getId() == consulta.getId()) {
                throw new Exception("Ya existe una consulta con ID " + consulta.getId());
            }
        }
        // Validar fecha (hoy o futuro)
        LocalDate fechaConsulta = LocalDate.parse(consulta.getFecha());
        LocalDate hoy = LocalDate.now();
        if (fechaConsulta.isBefore(hoy)) {
            throw new Exception("La fecha de la consulta no puede ser en el pasado.");
        }
        // Paciente y médico deben existir (la capa de fachada ya se encarga de verificarlos)
        this.consultas.add(consulta);
    }
    
    public void determinarEstado(Consulta consulta){
        LocalDate hoy = LocalDate.now();
        LocalDate fechaConsulta = LocalDate.parse(consulta.getFecha());
        if (fechaConsulta.isBefore(hoy) || fechaConsulta.isEqual(hoy)) {
            consulta.setEstado("Realizada");
        } else {
            consulta.setEstado("Pendiente");
        }
    }
    
    public List<Consulta> listarConsultas() {
        return new ArrayList<>(consultas);
    }
}

