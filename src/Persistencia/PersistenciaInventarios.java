 package Persistencia;


import Entidades.EquipoMedico;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaInventarios {
    
    private List<EquipoMedico> inventarios;

    public PersistenciaInventarios() {
        this.inventarios = new ArrayList<>();
    }

    /**
     * método para agregar un equipo médico
     * @param equipo
     * @throws Exception si ya existe
     */
    public void agregarEquipoMedico(EquipoMedico equipo) throws Exception {
        // Validar que el ID sea único
        for (EquipoMedico e : inventarios) {
            if (e.getId() == equipo.getId()) {
                throw new Exception("Ya existe un equipo médico con ID " + equipo.getId());
            }
        }
        if (equipo.getCantidad() < 0) {
            throw new Exception("La cantidad no puede ser negativa.");
        }
        this.inventarios.add(equipo);
    }

    /**
     * Método para buscar un equipo por su ID
     * @param id a buscar
     * @return equipo
     * @throws Exception si no se encuentra
     */
    public EquipoMedico obtenerEquipoPorId(int id) throws Exception {
        for (EquipoMedico equipo : inventarios) {
            if (equipo.getId() == id) {
                return equipo;  
            }
        }
         
        return null; 
    }
    
    /**
     * método para actualizar la cantidad de inventario de un equipo
     * @param id del equipo
     * @param cantidad
     * @throws Exception si es invalido
     */
    public void actualizarCantidadEquipo(int id, int cantidad) throws Exception {
        // Validar que el equipo exista y la nueva cantidad sea válida
        for (EquipoMedico e : inventarios) {
            if (e.getId() == id) {
                if (cantidad < 0) {
                    throw new Exception("La cantidad no puede ser negativa.");
                }
                e.setCantidad(cantidad);
                return;
            }
        }
        throw new Exception("No se encontró equipo médico con ID " + id);
    }

    public List<EquipoMedico> listarEquiposMedicos() {
        return new ArrayList<>(inventarios);
    }
    
    /**
     * método para filtrar la búsqueda de un equipo médico
     * @param nombreFiltro
     * @param cantidadMinima
     * @param cantidadMaxima
     * @return equipo
     */
    public List<EquipoMedico> filtrarEquipos(String nombreFiltro, int cantidadMinima, int cantidadMaxima) {
        List<EquipoMedico> equiposFiltrados = new ArrayList<>();
  
        if (cantidadMinima == -1) {
            cantidadMinima = 0;
        }
        if (cantidadMaxima == -1) {
            cantidadMaxima = Integer.MAX_VALUE;
        }
    
        for (EquipoMedico equipo : inventarios) {
            boolean coincideNombre = true;
            boolean coincideCantidad = true;

            if (nombreFiltro != null && !nombreFiltro.trim().isEmpty()) {
                if (!equipo.getNombre().toLowerCase().contains(nombreFiltro.toLowerCase())) {
                    coincideNombre = false;
                }
            }
        
            if (equipo.getCantidad() < cantidadMinima || equipo.getCantidad() > cantidadMaxima) {
                coincideCantidad = false;
            }

            if (coincideNombre && coincideCantidad) {
                equiposFiltrados.add(equipo);
            }
        }
    
        return equiposFiltrados;
    }
    
    /**
     * método para eliminar un equipo
     * @param idEquipo
     * @throws Exception si no se encuentra
     */
    public void eliminarEquipoMedico(int idEquipo) throws Exception {
        EquipoMedico equipoAEliminar = null;
        for (EquipoMedico equipo : inventarios) {
            if (equipo.getId() == idEquipo) {
                equipoAEliminar = equipo;
                break;
            }
        }
    
        if (equipoAEliminar != null) {
            inventarios.remove(equipoAEliminar);
        } else {
            throw new Exception("No se encontró el equipo médico con ID " + idEquipo);
        }
    }
    
    /**
     * método para actualizar un equipo médico
     * @param equipoActualizado
     * @throws Exception si no se encuentra
     */
    public void actualizarEquipoMedico(EquipoMedico equipoActualizado) throws Exception {
        boolean encontrado = false;
        for (int i = 0; i < inventarios.size(); i++) {
            EquipoMedico equipo = inventarios.get(i);
            if (equipo.getId() == equipoActualizado.getId()) {
                inventarios.set(i, equipoActualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new Exception("No se encontró el equipo médico con ID " + equipoActualizado.getId() + " para actualizar.");
        }
    }
    
    /**
     * método para inventariar un equipo
     * @param id del equipo
     * @param cantidadAñadir
     * @throws Exception si es invalido
     */
    public void inventariarEquipoMedico(int id, int cantidadAñadir) throws Exception {
        EquipoMedico equipoExistente = null;
        for (EquipoMedico equipo : inventarios) {
            if (equipo.getId() == id) {
                equipoExistente = equipo;
                break;
            }
        }

        if (equipoExistente == null) {
            throw new Exception("No se encontró equipo médico con ID " + id);
        }
    
        int nuevaCantidad = equipoExistente.getCantidad() + cantidadAñadir;
        if (nuevaCantidad < 0) {
            throw new Exception("La cantidad en inventario no puede ser negativa.");
        }
    
        equipoExistente.setCantidad(nuevaCantidad);
    }
    
    /**
     * método para desinventariar un equipo
     * @param id
     * @param cantidadQuitar
     * @throws Exception si es invalido
     */
    public void desinventariarEquipoMedico(int id, int cantidadQuitar) throws Exception {

        EquipoMedico equipoExistente = null;

        for (EquipoMedico equipo : inventarios) {
            if (equipo.getId() == id) {
                equipoExistente = equipo;
                break;
            }
        }

        if (equipoExistente == null) {
            throw new Exception("No se encontró equipo médico con ID " + id);
        }

        int nuevaCantidad = equipoExistente.getCantidad() - cantidadQuitar;

        if (nuevaCantidad < 0) {
            throw new Exception("No hay suficiente inventario para desinventariar.");
        }

        equipoExistente.setCantidad(nuevaCantidad);
    }
    
}

