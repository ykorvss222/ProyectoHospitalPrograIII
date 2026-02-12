/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Enrique Osuna
 */
public class EquipoMedico {
    
    private int id;
    private String nombre;
    private int cantidad;

    public EquipoMedico(int id, String nombre, int cantidad) throws Exception {
        if (id <= 0) {
            throw new Exception("El ID del equipo médico debe ser positivo.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre del equipo médico no puede estar vacío.");
        }
        if (cantidad < 0) {
            throw new Exception("La cantidad no puede ser negativa.");
        }
        
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
