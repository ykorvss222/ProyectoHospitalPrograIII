
package Menu;

import Entidades.*;
import Persistencia.IPersistenciaFachada;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MenuPrincipal extends JFrame implements IAgregarPaciente {
    
    private IPersistenciaFachada persistencia;
    private Scanner scanner;
    private JTable tabla; 
    private DefaultTableModel modeloTabla;
    private JPanel consultarPanel;
    List <Consulta> consultas;
    private JPanel panelDerecho; //Usen este para modificar lo del primer panel al derecho y se mueste la informacion que se va a consultar
    public MenuPrincipal(IPersistenciaFachada persistencia) {
        this.persistencia = persistencia;
        this.scanner = new Scanner(System.in);
    }
            
    private void actualizarTabla(String[] columnas, List<?> datos) {
            Object[][] data = new Object[datos.size()][columnas.length];
            for (int i = 0; i < datos.size(); i++) {
                Object obj = datos.get(i);

                if (obj instanceof Consulta c) {
                    data[i][0] = c.getId();
                    data[i][1] = c.getPaciente().getNombre();
                    data[i][2] = c.getMedico().getNombre();
                    data[i][3] = c.getFecha();
                    data[i][4] = c.getEstado();
                } else if (obj instanceof Paciente p) {
                    data[i][0] = p.getId();
                    data[i][1] = p.getNombre();
                    data[i][2] = p.getEdad();
                    data[i][3] = p.getDireccion();
                } else if (obj instanceof Medico m) {
                    data[i][0] = m.getId();
                    data[i][1] = m.getNombre();
                    data[i][2] = m.getEspecialidad().getNombre();
                    data[i][3] = m.getEspecialidad().getId();
                } else if (obj instanceof Especialidad e) {
                    data[i][0] = e.getId();
                    data[i][1] = e.getNombre();
                } else {
                    data[i][0] = "Tipo no soportado";
                }
            }
            modeloTabla.setDataVector(data, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();                  
        }
    
    public void mostrarTabla(){
        
          setTitle("Sistema hospital");
          setSize(800,500);
          setDefaultCloseOperation(EXIT_ON_CLOSE);
          setLocationRelativeTo(null); // esta linea de aqui sirve para centrar la ventana en el monitor en que se encuentre la ventana

          //Inicia panel derecho
          
          panelDerecho = new JPanel();
          panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
          this.add(panelDerecho, BorderLayout.EAST);
          
          // panel principal de botones de consulta
          
          consultarPanel = new JPanel();          
          consultarPanel.setLayout(new BoxLayout(consultarPanel, BoxLayout.Y_AXIS));
          consultarPanel.setBackground(Color.LIGHT_GRAY);
          
          JLabel titulo = new JLabel("== CONSULTAS ==");
          titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
          
          JButton btnPacientes = new JButton("== PACIENTES ==");
          btnPacientes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
          btnPacientes.setAlignmentX(Component.CENTER_ALIGNMENT);

//          JButton btnEspecialidad = new JButton("== CONSULTAR ESPECIALIDA ==");
//          btnEspecialidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//          btnEspecialidad.setAlignmentX(Component.CENTER_ALIGNMENT);
          
          JButton btnConsultas = new JButton("== CONSULTAR CONSULTAS ==");
          btnConsultas.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
          btnConsultas.setAlignmentX(Component.CENTER_ALIGNMENT);        
          btnConsultas.addActionListener(e -> consultarConsultas());                    
          
          JButton btnMedicosId = new JButton("== CONSULTAR MÉDICOS ==");
          btnMedicosId.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
          btnMedicosId.setAlignmentX(Component.CENTER_ALIGNMENT);
          
           JButton btnEquiposMedicos = new JButton("== EQUIPOS MEDICOS ==");
            btnEquiposMedicos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
           btnEquiposMedicos.setAlignmentX(Component.CENTER_ALIGNMENT);
          
          // Agrega esta línea:
          btnMedicosId.addActionListener(e -> medicosId());
          btnEquiposMedicos.addActionListener(e -> equiposMedicos());
          consultarPanel.add(titulo);
          consultarPanel.add(Box.createVerticalStrut(20));
          consultarPanel.add(btnPacientes);
          consultarPanel.add(Box.createVerticalStrut(20));
          consultarPanel.add(btnMedicosId);
          consultarPanel.add(Box.createVerticalStrut(20));
//       consultarPanel.add(btnEspecialidad);
//       consultarPanel.add(Box.createVerticalStrut(20));
        consultarPanel.add(btnConsultas);          
        consultarPanel.add(Box.createVerticalStrut(20));
        consultarPanel.add(btnEquiposMedicos);
          
        add(consultarPanel, BorderLayout.WEST);
          
        // aqui creamos la tabla
          
        modeloTabla = new DefaultTableModel();
        tabla = new JTable(modeloTabla);
          
        //  aqui creamos el menu de la tabla
          
        JMenuBar barraMenu = new JMenuBar();
        JMenu menu = new JMenu("Opciones");
                    
        JMenuItem agregarPaciente = new JMenuItem("Agregar paciente");
        JMenuItem agregarMedico = new JMenuItem("Agregar medico");
        JMenuItem agregarEspecialidad = new JMenuItem("Agregar especialidad");
        JMenuItem agregarEquipo = new JMenuItem("Agregar equipo medico");
        JMenuItem programarConsulta = new JMenuItem("Programar consulta");
        //JMenuItem listarDatos = new JMenuItem("Listar Datos");
        JMenuItem salir = new JMenuItem("Salir");
                         
        //añadimos el menu 
          
        menu.add(agregarPaciente);
        menu.add(agregarMedico);
        menu.add(agregarEspecialidad);
        menu.add(agregarEquipo);
        menu.add(programarConsulta);
      //  menu.add(listarDatos);
        menu.add(salir);
               
        barraMenu.add(menu); 
        setJMenuBar(barraMenu);
          
        agregarPaciente.addActionListener(e -> opcionAgregarPaciente());
        agregarMedico.addActionListener(e -> opcionAgregarMedico());
        agregarEspecialidad.addActionListener(e -> opcionAgregarEspecialidad());
        agregarEquipo.addActionListener(e -> opcionAgregarEquipoMedico());
        programarConsulta.addActionListener(e ->  opcionProgramarConsulta());
        salir.addActionListener(e -> salir());
        
        //Panel Izquierdo ActionListeners
        btnPacientes.addActionListener(e -> pacientes());
          
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        setVisible(true);          
    }     

    /**
     * Llama al método de IPersistenciaFachada para agregar un nuevo paciente,
     * validando los datos antes de la operación.
     */
    @Override
    public void opcionAgregarPaciente() {
        
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        
        JTextField idPaciente = new JTextField();
        JTextField nombrePaciente = new JTextField();
        JTextField edadPaciente = new JTextField();
        JTextField direccionPaciente = new JTextField();
        
        panelFormulario.add(new JLabel("ID PACIENTE:"));
        panelFormulario.add(idPaciente);
        panelFormulario.add(new JLabel("NOMBRE PACIENTE:"));
        panelFormulario.add(nombrePaciente);
        panelFormulario.add(new JLabel("EDAD PACIENTE:"));
        panelFormulario.add(edadPaciente);
        panelFormulario.add(new JLabel("DIRECCION PACIENTE:"));
        panelFormulario.add(direccionPaciente);
        
        int resultado = JOptionPane.showConfirmDialog(this, panelFormulario, "== Agregar Paciente ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idPaciente.getText().trim());
                String nombre = nombrePaciente.getText();
                int edad = Integer.parseInt(edadPaciente.getText());
                String direccion = direccionPaciente.getText();

                if (nombre.isEmpty() || direccion.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ERROR: 'NOMBRE' y 'DIRECCION' campos vacios.");
                    return;
                }
                
                Paciente paciente = new Paciente(id, nombre, edad, direccion);
                persistencia.agregarPaciente(paciente);
                JOptionPane.showMessageDialog(this, "Paciente Agregado Correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Formato de número inválido. Asegúrese de ingresar números para ID y edad.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar paciente" + ex.getMessage());
            }
        }        
    }
    
    /**
     * Llama al método de IPersistenciaFachada para agregar un nuevo médico,
     * validando los datos antes de la operación.
     */
    @Override
    public void opcionAgregarMedico() {
        
        JPanel panelMedico = new JPanel();        
        panelMedico.setLayout(new BoxLayout(panelMedico, BoxLayout.Y_AXIS));
                        
        JTextField idMedico = new JTextField();
        JTextField nombreMedico = new JTextField();
        JTextField idMedicoEspecialidad = new JTextField();
        
        panelMedico.add(new JLabel("ID MEDICO: "));
        panelMedico.add(idMedico);
        panelMedico.add(new JLabel("NOMBRE: "));
        panelMedico.add(nombreMedico);
        panelMedico.add(new JLabel("ID ESPECIALIDAD MEDICO: "));
        panelMedico.add(idMedicoEspecialidad);
       
        int resultado = JOptionPane.showConfirmDialog(this, panelMedico, "== Agregar Médico ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idMedico.getText().trim());
                String nombre = nombreMedico.getText();
                int idEspecialidad = Integer.parseInt(idMedicoEspecialidad.getText().trim());
                
                //Se asume que la especialidad ya debe haber sido agregado antes
                Especialidad especialidad = persistencia.obtenerEspecialidadPorId(idEspecialidad);
                if (especialidad == null) {
                    JOptionPane.showMessageDialog(this, "Error: No existe especialidad");
                    return;
                }
                Medico medico = new Medico(id, nombre, especialidad);
                persistencia.agregarMedico(medico);
                JOptionPane.showMessageDialog(this, "Médico agregado correctamente");                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Formato de número inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar medico: " + ex.getMessage());
            }            
        }
    }

    /**
     * Llama al método de IPersistenciaFachada para agregar una nueva especialidad,
     * validando los datos antes de la operación.
     */
    @Override
    public void opcionAgregarEspecialidad() {
        
        JPanel panelEspecialidad = new JPanel();
        panelEspecialidad.setLayout(new BoxLayout(panelEspecialidad, BoxLayout.Y_AXIS));
        
        JTextField idEspecialidad = new JTextField();
        JTextField nombreEspecialidad = new JTextField();
        
        panelEspecialidad.add(new JLabel("ID ESPECIALIDAD: "));
        panelEspecialidad.add(idEspecialidad);
        panelEspecialidad.add(new JLabel("NOMBRE ESPECIALIDAD: "));
        panelEspecialidad.add(nombreEspecialidad);
        
        int resultado = JOptionPane.showConfirmDialog(this, panelEspecialidad, "== Agregar Especialidad ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idEspecialidad.getText());
                String nombre = nombreEspecialidad.getText();
                
                Especialidad especialidad = new Especialidad(id, nombre);
                persistencia.agregarEspecialidad(especialidad);
                JOptionPane.showMessageDialog(this, "Especialidad agregada correctamente.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Formato de número inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al agregar especialidad" + e.getMessage());
            }
        }
    }

    /**
     * Llama al método de IPersistenciaFachada para agregar un equipo médico,
     * validando los datos antes de la operación.
     */
    @Override
    public void opcionAgregarEquipoMedico() {
        
        JPanel panelEquipoMedico = new JPanel();
        panelEquipoMedico.setLayout(new BoxLayout(panelEquipoMedico, BoxLayout.Y_AXIS));

        
        JTextField idEquipoMedico = new JTextField();
        JTextField nombreEquipoMedico = new JTextField();
        JTextField cantidadEquipoMedico = new JTextField();
        
        panelEquipoMedico.add(new JLabel("ID EQUIPO MÉDICO:"));
        panelEquipoMedico.add(idEquipoMedico);
        panelEquipoMedico.add(new JLabel("NOMBRE EQUIPO MÉDICO:"));
        panelEquipoMedico.add(nombreEquipoMedico);
        panelEquipoMedico.add(new JLabel("CANTIDAD EQUIPO MÉDICO:"));
        panelEquipoMedico.add(cantidadEquipoMedico);
        
        int resultado = JOptionPane.showConfirmDialog(this,panelEquipoMedico, "== Agregar Equipo Médico ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idEquipoMedico.getText().trim());
                String nombre =  nombreEquipoMedico.getText();
                int cantidad = Integer.parseInt(cantidadEquipoMedico.getText().trim());
                
                EquipoMedico equipo = new EquipoMedico(id, nombre, cantidad);
                persistencia.agregarEquipoMedico(equipo);
                JOptionPane.showMessageDialog(this, "Equipo médico agregado correctamente");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ERROR: Formato de número inválido.,","ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar equipo médico" + ex.getMessage());
            }      
        }
    }

    /**
     * Llama al método de IPersistenciaFachada para programar una consulta,
     * validando que el paciente y el médico existan, y que la fecha sea correcta.
     */
    @Override
    public void opcionProgramarConsulta() {
        
        JPanel panelProgramarConsulta = new JPanel();        
        panelProgramarConsulta.setLayout(new BoxLayout(panelProgramarConsulta, BoxLayout.Y_AXIS));
        
        JTextField idConsulta = new JTextField();
        JTextField idPaciente = new JTextField();
        JTextField idMedico = new JTextField();
        JTextField fechaConsulta = new JTextField();
        //SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd");
        
        panelProgramarConsulta.add(new JLabel("ID CONSULTA:"));
        panelProgramarConsulta.add(idConsulta);
        panelProgramarConsulta.add(new JLabel("ID PACIENTE:"));
        panelProgramarConsulta.add(idPaciente);
        panelProgramarConsulta.add(new JLabel("ID MÉDICO:"));
        panelProgramarConsulta.add(idMedico);
        panelProgramarConsulta.add(new JLabel("FECHA: (FORMATO: yyyy-MM-dd)"));
        panelProgramarConsulta.add(fechaConsulta);
        
        int resultado = JOptionPane.showConfirmDialog(this, panelProgramarConsulta, "== Programar Consulta ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idConsulta.getText().trim());
                int pacienteID = Integer.parseInt(idPaciente.getText().trim());
                int medicoID = Integer.parseInt(idMedico.getText().trim());
                String fecha = fechaConsulta.getText();
                
                // Se obtienen las entidades desde la persistencia para validarlas
                Paciente paciente = persistencia.obtenerPacientePorId(pacienteID);
                Medico medico = persistencia.obtenerMedicoPorId(medicoID);
                if (paciente == null) {
                    JOptionPane.showConfirmDialog(this, "Error: No existe paciente con ID: " + paciente.getId());
                    return;
                }
                if (medico == null) {
                    JOptionPane.showMessageDialog(this, "ERROR: No existe medico con ID: " + medico.getId());
                    return;
                }
                
                Consulta consulta = new Consulta(id, paciente, medico, fecha);
                persistencia.programarConsulta(consulta);
                JOptionPane.showMessageDialog(this, "Consulta programada correctamente.");   
                JOptionPane.showMessageDialog(
                    this,
                    "ID: " + consulta.getId() + "\n" +
                    "PACIENTE: " + consulta.getPaciente().getNombre() + "\n" +
                    "MEDICO: " + consulta.getMedico().getNombre() + "\n" +
                    "FECHA: " + consulta.getFecha(),
                    "Detalle de Consulta",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ERROR: Formato de número inválido", "ERROR", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al programar consulta: " + ex.getMessage());
            }            
        }      
    } //
    
    /**
     * Metodo para el manejo de pacientes.
     * Consultar, actualizar, eliminar, filtrar
     */
    @Override
    public void pacientes() { 
                
        Object[] opciones = {"1. Consultar paciente", "2. Actualizar paciente", "3. Eliminar paciente", "4. Listado de pacientes"};
        
        int opcionSeleciconada = JOptionPane.showOptionDialog(this, "SELECCIONE OPCION", "== PACIENTES ==", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,  null, opciones, opciones[0]);
        
        switch (opcionSeleciconada) {
            case 0 -> consultarPacienteID();
            case 1 -> actualizarPacientes();
            case 2 -> eliminarPaciente();
            case 3 -> filtrarPacientes();
            default -> JOptionPane.showMessageDialog(this, "Opcion Invalida.");
        }
    }
        
    /**
     * Metodo para mostrar la informacion de un pacient por ID.
     */
    public void consultarPacienteID() {
               
        try {
            String id = JOptionPane.showInputDialog(this, "Ingrese ID del paciente:");
            int idPaciente = Integer.parseInt(id);
            Paciente paciente = persistencia.obtenerPacientePorId(idPaciente); 
            
            if (paciente == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe paciente con ID proporcionada.");
                return;
            }

            String[] columnas = {"ID", "NOMBRE", "EDAD", "DIRECCION"};
            Object[][] filas = {{paciente.getId(), paciente.getNombre(), paciente.getEdad(), paciente.getDireccion()}};
            modeloTabla.setDataVector(filas, columnas);            
            
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: No existe paciente con ID proporcionada ", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al consultar paciente.");
        }       
    }
    
    /**
     * Metodo para actualizar la informacion de un paciente.
     * Este metodo muestra la informacion de un paciente y al seleccionarlo podra actualizar su informacion
     */
    public void actualizarPacientes() {
        
        JPanel panelActualizacion = new JPanel();
        panelActualizacion.setLayout(new BoxLayout(panelActualizacion, BoxLayout.Y_AXIS));
        
        try {                    
            var pacientes = persistencia.listarPacientes();
            if (pacientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No existen pacientes");
                return;
            }            
            
            String[] columnas = {"ID", "NOMBRE", "EDAD", "DIRECCION"};
            
            Object[][] filas = new Object[pacientes.size()][4];
            for (int i = 0; i < pacientes.size(); i++) {
                Paciente paciente = pacientes.get(i);
                filas[i][0] = paciente.getId();
                filas[i][1] = paciente.getNombre();
                filas[i][2] = paciente.getEdad();
                filas[i][3] = paciente.getDireccion();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();
                
            String id = JOptionPane.showInputDialog(this, "Ingrese ID del paciente a actualizar:");
            int idPacienteAct = Integer.parseInt(id);
            
            Paciente pacienteA = persistencia.obtenerPacientePorId(idPacienteAct);                    
            if (pacienteA == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe paciente con ID proporcionada.");                   
                return;
            }
                                
            JTextField nombrePaciente = new JTextField();
            JTextField edadPaciente = new JTextField();
            JTextField direccionPaciente = new JTextField();
        
            panelActualizacion.add(new JLabel("NOMBRE PACIENTE:"));
            panelActualizacion.add(nombrePaciente);
            panelActualizacion.add(new JLabel("EDAD PACIENTE:"));
            panelActualizacion.add(edadPaciente);
            panelActualizacion.add(new JLabel("DIRECCION PACIENTE:"));
            panelActualizacion.add(direccionPaciente);
            
            int resultado = JOptionPane.showConfirmDialog(this, panelActualizacion, "== Actualizar Paciente ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    String nombre = nombrePaciente.getText();
                    int edad = Integer.parseInt(edadPaciente.getText().trim());
                    String direccion = direccionPaciente.getText();
                    
                    Paciente pacienteAct = new Paciente(pacienteA.getId(), nombre, edad, direccion);
                    persistencia.actualizarPaciente(pacienteAct);
                    JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente");  
                        
                    pacientes = persistencia.listarPacientes();
                    filas = new Object[pacientes.size()][4];                        
                    for (int i = 0; i < pacientes.size(); i++) {
                        Paciente p = pacientes.get(i);                          
                        filas[i][0] = p.getId();
                        filas[i][1] = p.getNombre();
                        filas[i][2] = p.getEdad();
                        filas[i][3] = p.getDireccion();
                    }  
            
                    modeloTabla.setDataVector(filas, columnas);                
                    panelDerecho.removeAll();
                    panelDerecho.revalidate();
                    panelDerecho.repaint();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error: formato de número invalido.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar" + ex.getMessage());
                }
            }                          
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ERROR: ID invalido");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage());
        }
    }
    
   /**
     * Metodo para eliminar un paciente. 
     */
    public void eliminarPaciente() {        
        JButton btnEliminar = new JButton("Eliminar");        
        try {
            var pacientes = persistencia.listarPacientes();
            if (pacientes.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Error: No existen pacientes.");
                return;
            }                        
            
            String[] columnas = {"ID", "NOMBRE", "EDAD", "DIRECCION"};
            
            Object[][] filas = new Object[pacientes.size()][4];
            for (int i = 0; i < pacientes.size(); i++) {
                Paciente paciente = pacientes.get(i);
                filas[i][0] = paciente.getId();
                filas[i][1] = paciente.getNombre();
                filas[i][2] = paciente.getEdad();
                filas[i][3] = paciente.getDireccion();
            }
            modeloTabla.setDataVector(filas, columnas);
            tabla.setDefaultEditor(Object.class, null);
            panelDerecho.removeAll();            
            panelDerecho.add(btnEliminar);
            panelDerecho.revalidate();
            panelDerecho.repaint();            
            
            btnEliminar.addActionListener((ActionEvent e) -> {
                int fila = tabla.getSelectedRow();
                
                if (fila  == -1) {
                    JOptionPane.showMessageDialog(this, "Selecciones paciente a eliminar.");
                    return;
                }
                
                int id = (int) modeloTabla.getValueAt(fila, 0);
                
                int resultado = JOptionPane.showConfirmDialog(this, "Â¿Seguro desea eliminar el paciente?","== Eliminar Paciente ==", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if (resultado == JOptionPane.OK_OPTION) {
                    try {
                        persistencia.eliminarPaciente(id);
                        modeloTabla.removeRow(fila);
                        JOptionPane.showMessageDialog(this, "Paciente eliminado correctamente.");                        
                    } catch (Exception et) {
                        JOptionPane.showMessageDialog(this,"Error al eliminar paciente.");
                    }
                }
            });
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar pacientes.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage());
        }
    }
        
    /**
     * Metodo para filtrar pacientes direccion, edad, ambas o todos los pacientes.
    */
    public void filtrarPacientes() {
        try {
            String direccion = JOptionPane.showInputDialog(this, "Ingrese direccion (o vacio para no especificar)");
        
            String edadMinima = JOptionPane.showInputDialog(this, "Ingrese edad minima del paciente (o vacio para no especificar)");
            Integer edadMin = (edadMinima == null || edadMinima.isEmpty()? null: Integer.parseInt(edadMinima));
        
            String edadMaxima = JOptionPane.showInputDialog(this, "Ingrese edad maxima del paciente (o vacio para no especificar)");
            Integer edadMax = (edadMaxima == null || edadMaxima.isEmpty()? null: Integer.parseInt(edadMaxima));
        
            List<Paciente> filtros = persistencia.filtrarPacientes(direccion, edadMin, edadMax);
            
            if (filtros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No se encontraron pacientes con filtros dados.");
            }
            
            String[] columnas = {"ID", "NOMBRE", "EDAD", "DIRECCION"};
            
            Object[][] filas = new Object[filtros.size()][4];
            for (int i = 0; i < filtros.size(); i++) {
                Paciente paciente = filtros.get(i);
                filas[i][0] = paciente.getId();
                filas[i][1] = paciente.getNombre();
                filas[i][2] = paciente.getEdad();
                filas[i][3] = paciente.getDireccion();
            }
            modeloTabla.setDataVector(filas, columnas);
            tabla.setDefaultEditor(Object.class, null);

            panelDerecho.removeAll();            
            panelDerecho.revalidate();
            panelDerecho.repaint();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error de formato numerico invalido.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Error al filtrar pacientes.");
        }
    }
    
    /**
     * Menú para listar pacientes, médicos, especialidades, equipos y consultas.
     */
    public void opcionListarDatos() {
        boolean salir = false;
        while(!salir) {
            System.out.println("\n=== Menú de Listado ===");
            System.out.println("1. Listar Pacientes");
            System.out.println("2. Listar Médicos");
            System.out.println("3. Listar Especialidades");
            System.out.println("4. Listar Equipos Médicos");
            System.out.println("5. Listar Consultas");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine();
            switch(opcion) {
                case "1":
                    listarPacientes();
                    break;
                case "2":
                    listarMedicos();
                    break;
                case "3":
                    listarEspecialidades();
                    break;
                case "4":
                    listarEquiposMedicos();
                    break;
                case "5":
                    listarConsultas();
                    break;
                case "6":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }
        }
    }
    
    private void listarPacientes() {
        try {
            List<Paciente> pacientes = persistencia.listarPacientes();
            if (pacientes.isEmpty()) {
                System.out.println("No hay pacientes registrados.");
            } else {
                System.out.println("\n== Lista de Pacientes ==");
                for (Paciente p : pacientes) {
                    System.out.println("ID: " + p.getId() + ", Nombre: " + p.getNombre() +
                            ", Edad: " + p.getEdad() + ", Dirección: " + p.getDireccion());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar pacientes: " + e.getMessage());
        }
    }
    
    private void listarEspecialidades() {
        try {
            List<Especialidad> especialidades = persistencia.listarEspecialidades();
            if (especialidades.isEmpty()) {
                System.out.println("No hay especialidades registradas.");
            } else {
                System.out.println("\n== Lista de Especialidades ==");
                for (Especialidad e : especialidades) {
                    System.out.println("ID: " + e.getId() + ", Nombre: " + e.getNombre());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar especialidades: " + e.getMessage());
        }
    }   
    
    private void listarConsultas() {
        try {
            List<Consulta> consultas = persistencia.listarConsultas();
            if (consultas.isEmpty()) {
                System.out.println("No hay consultas registradas.");
            } else {
                System.out.println("\n== Lista de Consultas ==");
                for (Consulta c : consultas) {
                    System.out.println("ID: " + c.getId() + 
                            ", Paciente: " + c.getPaciente().getNombre() +
                            ", Médico: " + c.getMedico().getNombre() +
                            ", Fecha: " + c.getFecha());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar consultas: " + e.getMessage());
        }
    }
    
    public void medicosId() {
        Object[] opciones = {"1. Consultar médico", "2. Actualizar médico", "3. Eliminar médico", "4. Listado de médicos"};
        
        int opcionSeleccionada = JOptionPane.showOptionDialog(this, "SELECCIONE OPCION", "== MEDICOS ==", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        switch (opcionSeleccionada) {
            case 0 -> consultarMedicoID();
            case 1 -> actualizarMedico();
            case 2 -> eliminarMedico();
            case 3 -> listarMedicos();
            default -> JOptionPane.showMessageDialog(this, "Opcion Invalida.");
        }
    }

    public void consultarMedicoID() {
        try {
            String id = JOptionPane.showInputDialog(this, "Ingrese el ID del médico:");
            if (id == null) {
                return; // El usuario canceló la operación
            }
            int idMedico = Integer.parseInt(id);

            Medico medico = persistencia.obtenerMedicoPorId(idMedico);

            if (medico == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe un médico con el ID proporcionado.");
                // Opcional: limpiar la tabla si no se encuentra el médico
                modeloTabla.setRowCount(0); 
                return;
            }

            // Si el médico existe, se actualiza la tabla
            String[] columnas = {"ID", "NOMBRE", "ESPECIALIDAD", "ID ESPECIALIDAD"};
            Object[][] filas = {{medico.getId(), medico.getNombre(), medico.getEspecialidad().getNombre(), medico.getEspecialidad().getId()}};

            modeloTabla.setDataVector(filas, columnas);

            // Opcional: revalidar y repintar la tabla
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: Formato de ID inválido. Por favor, ingrese un número.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al consultar médico: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actualizarMedico() {
        try {
            List<Medico> medicos = persistencia.listarMedicos();
            if (medicos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No hay médicos registrados.");
                return;
            }

            // Muestra la lista de médicos en la tabla para que el usuario pueda ver los IDs
            String[] columnas = {"ID", "NOMBRE", "ESPECIALIDAD", "ID ESPECIALIDAD"};
            Object[][] filas = new Object[medicos.size()][4];
            for (int i = 0; i < medicos.size(); i++) {
                Medico medico = medicos.get(i);
                filas[i][0] = medico.getId();
                filas[i][1] = medico.getNombre();
                filas[i][2] = medico.getEspecialidad().getNombre();
                filas[i][3] = medico.getEspecialidad().getId();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del médico a actualizar:");
            if (idStr == null || idStr.trim().isEmpty()) {
                return;
            }
            int idMedicoAct = Integer.parseInt(idStr);

            Medico medicoA = persistencia.obtenerMedicoPorId(idMedicoAct);
            if (medicoA == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe médico con el ID proporcionado.");
                return;
            }

            JPanel panelActualizacion = new JPanel();
            panelActualizacion.setLayout(new BoxLayout(panelActualizacion, BoxLayout.Y_AXIS));

            JTextField nombreMedico = new JTextField(medicoA.getNombre());
            JTextField idEspecialidad = new JTextField(String.valueOf(medicoA.getEspecialidad().getId()));

            panelActualizacion.add(new JLabel("Nuevo nombre del médico:"));
            panelActualizacion.add(nombreMedico);
            panelActualizacion.add(new JLabel("Nuevo ID de especialidad:"));
            panelActualizacion.add(idEspecialidad);

            int resultado = JOptionPane.showConfirmDialog(this, panelActualizacion, "Actualizar Médico", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String nuevoNombre = nombreMedico.getText();
                int nuevoIdEspecialidad = Integer.parseInt(idEspecialidad.getText().trim());

                Especialidad especialidad = persistencia.obtenerEspecialidadPorId(nuevoIdEspecialidad);
                if (especialidad == null) {
                    JOptionPane.showMessageDialog(this, "Error: No existe especialidad con el ID proporcionado.");
                    return;
                }

                // Línea corregida aquí
                  Medico medicoActualizado = new Medico(medicoA.getId(), nuevoNombre, especialidad);

                persistencia.actualizarMedico(medicoActualizado);
                JOptionPane.showMessageDialog(this, "Médico actualizado correctamente.");

                // Vuelve a listar los médicos para reflejar el cambio en la tabla
                medicos = persistencia.listarMedicos();
                filas = new Object[medicos.size()][4];
                for (int i = 0; i < medicos.size(); i++) {
                    Medico m = medicos.get(i);
                    filas[i][0] = m.getId();
                    filas[i][1] = m.getNombre();
                    filas[i][2] = m.getEspecialidad().getNombre();
                    filas[i][3] = m.getEspecialidad().getId();
                }
                modeloTabla.setDataVector(filas, columnas);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: ID o formato de número inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el médico: " + ex.getMessage());
        }
    }
    
    public void eliminarMedico() {
        try {
            var medicos = persistencia.listarMedicos();
            if (medicos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No existen médicos registrados.");
                return;
            }

            String[] columnas = {"ID", "NOMBRE", "ESPECIALIDAD", "ID ESPECIALIDAD"};
            Object[][] filas = new Object[medicos.size()][4];
            for (int i = 0; i < medicos.size(); i++) {
                Medico medico = medicos.get(i);
                filas[i][0] = medico.getId();
                filas[i][1] = medico.getNombre();
                filas[i][2] = medico.getEspecialidad().getNombre();
                filas[i][3] = medico.getEspecialidad().getId();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del médico a eliminar:");
            if (idStr == null || idStr.trim().isEmpty()) {
                return; // El usuario canceló o no ingresó nada
            }

            int id = Integer.parseInt(idStr);
            Medico medicoAEliminar = persistencia.obtenerMedicoPorId(id);

            if (medicoAEliminar == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe un médico con el ID proporcionado.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro de que desea eliminar al médico " + medicoAEliminar.getNombre() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                persistencia.eliminarMedico(medicoAEliminar);
                JOptionPane.showMessageDialog(this, "Médico eliminado correctamente.");

                // Volver a listar los médicos para reflejar los cambios
                medicos = persistencia.listarMedicos();
                filas = new Object[medicos.size()][4];
                for (int i = 0; i < medicos.size(); i++) {
                    Medico m = medicos.get(i);
                    filas[i][0] = m.getId();
                    filas[i][1] = m.getNombre();
                    filas[i][2] = m.getEspecialidad().getNombre();
                    filas[i][3] = m.getEspecialidad().getId();
                }
                modeloTabla.setDataVector(filas, columnas);
                panelDerecho.removeAll();
                panelDerecho.revalidate();
                panelDerecho.repaint();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: ID inválido. Asegúrese de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al eliminar el médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarMedicos() {
        try {
            // Obtiene la lista de médicos usando la fachada de persistencia
            List<Medico> medicos = persistencia.listarMedicos();

            // Verifica si la lista está vacía y muestra un mensaje si es así
            if (medicos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay médicos registrados.");
                // Opcional: limpiar la tabla si no hay datos
                modeloTabla.setRowCount(0);
                return;
            }

            // Define las columnas para la tabla
            String[] columnas = {"ID", "NOMBRE", "ESPECIALIDAD", "ID ESPECIALIDAD"};

            // Crea una matriz de objetos para las filas de la tabla
            Object[][] filas = new Object[medicos.size()][4];

            // Llena la matriz con los datos de cada médico
            for (int i = 0; i < medicos.size(); i++) {
                Medico m = medicos.get(i);
                filas[i][0] = m.getId();
                filas[i][1] = m.getNombre();
                // Asume que la especialidad no es nula para obtener el nombre
                filas[i][2] = m.getEspecialidad().getNombre(); 
                filas[i][3] = m.getEspecialidad().getId();                 
            }

            // Actualiza el modelo de la tabla con los nuevos datos
            modeloTabla.setDataVector(filas, columnas);

            // Remueve y revalida el panel derecho para asegurar que se limpia
            // (esto puede ser opcional dependiendo de tu diseño)
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } catch (Exception e) {
            // Muestra un mensaje de error en un cuadro de diálogo si algo sale mal
            JOptionPane.showMessageDialog(this, "Error al listar médicos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
     }
     
    public void consultarConsultas() {
        String[] columnas = {"ID","PACIENTE","MÉDICO","FECHA","ESTADO"};
         try {
             consultas = persistencia.listarConsultas();
         } catch (Exception ex) {
             Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
         }
         actualizarTabla(columnas, consultas);
         
         Object[] opciones = {"1. Filtrar por ID Paciente", "2. Filtrar por ID Médico", "3. Filtrar por Periodo", "4. Cancelar Consulta"};
         int opcionSeleccionada = JOptionPane.showOptionDialog(this, "SELECCIONE OPCION", "== PACIENTES ==", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,  null, opciones, opciones[0]);

         switch (opcionSeleccionada) {
             case 0 -> filtrarPacienteID();
             case 1 -> filtrarMedicoID();
             case 2 -> filtrarFechaPeriodo();
             case 3 -> cancelarConsulta(); 
             default -> JOptionPane.showMessageDialog(this, "Opcion Invalida.");
         }
     }
    
    public void filtrarPacienteID(){
        String[] columnas = {"ID","PACIENTE","MÉDICO","FECHA","ESTADO"};
        String idPaciente = JOptionPane.showInputDialog("Filtro de busqueda","Ingrese el ID del paciente:" );              
        for(int i=0;i<consultas.size();i++){
            String idPacienteConsulta = String.valueOf(consultas.get(i).getPaciente().getId());
            if(idPacienteConsulta.equals(idPaciente)){
                List<Consulta> consultasFiltroPaciente = consultas;
                actualizarTabla(columnas, consultasFiltroPaciente);
            }
        }
     }

    public void filtrarMedicoID(){
        String[] columnas = {"ID","PACIENTE","MÉDICO","FECHA","ESTADO"};
        String idMedico = JOptionPane.showInputDialog("Filtro de busqueda","Ingrese el ID del paciente:" );              
        for(int i=0;i<consultas.size();i++){
            String idMedicoConsulta = String.valueOf(consultas.get(i).getMedico().getId());
            if(idMedicoConsulta.equals(idMedico)){
                List<Consulta> consultasFiltroPaciente = consultas;
                actualizarTabla(columnas, consultasFiltroPaciente);
            }
        }
     }

    public static int convertirFecha(JTextField campoFecha) throws IllegalArgumentException{
        String fechaStr = campoFecha.getText().trim();
        
        try {
            if (fechaStr.isEmpty()) {
                JOptionPane.showMessageDialog(campoFecha, "El campo de fecha está vacio.");
                return -1;
            }                        
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(campoFecha, e.getMessage());
        }
        
        String fechaNumerica = fechaStr.replace("-", "");
        try {
            return Integer.parseInt(fechaNumerica);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(campoFecha, "El formato de fecha es inválido." + fechaStr);
            return -1;
        }
    }

    public int convertirFecha(String campoFecha) throws IllegalArgumentException{
        
        try {
            if (campoFecha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El campo de está vacío.");
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());            
        }

        String fechaNumerica = campoFecha.replace("-", "");        
        try {
            return Integer.parseInt(fechaNumerica);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El formato de fecha es inválido." + campoFecha);
        }
    }
    
    public void filtrarFechaPeriodo() {
        String[] columnas = {"ID","PACIENTE","MÉDICO","FECHA","ESTADO"};
        JPanel panelFiltrado = new JPanel();
        panelFiltrado.setLayout(new BoxLayout(panelFiltrado, BoxLayout.Y_AXIS));

        JTextField fechaInicio = new JTextField();
        JTextField fechaFin = new JTextField();

        panelFiltrado.add(new JLabel("Fecha Inicio (yyyy-MM-dd)"));
        panelFiltrado.add(fechaInicio);
        panelFiltrado.add(new JLabel("Fecha Final (yyyy-MM-dd)"));
        panelFiltrado.add(fechaFin);

        int result = JOptionPane.showConfirmDialog(null, panelFiltrado, "Filtrar por Periodo", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int fechaIni = convertirFecha(fechaInicio);
            int fechaFinal = convertirFecha(fechaFin);
            
            if (fechaIni == -1 || fechaFinal == -1) {
                return;
            }

            if (fechaIni > fechaFinal) {
                JOptionPane.showMessageDialog(this, "Orden de fechas incorrecto");
                return;
            }

            List<Consulta> consultasFiltroPeriodo = new ArrayList<>();

            for (Consulta c : consultas) {
                int fechaConsulta = convertirFecha(c.getFecha());
                if (fechaConsulta >= fechaIni && fechaConsulta <= fechaFinal) {
                    consultasFiltroPeriodo.add(c);
                }
            }
            actualizarTabla(columnas, consultasFiltroPeriodo);
        }
    }
    
    public void cancelarConsulta(){
        String[] columnas = {"ID","PACIENTE","MÉDICO","FECHA","ESTADO"};
        String idConsulta = JOptionPane.showInputDialog("Ingrese ID de la consulta a cancelar","Ingrese el ID de la consulta" );
        for(int i=0;i<consultas.size();i++){
            String idConsultaCancelada = String.valueOf(consultas.get(i).getId());
            if(idConsultaCancelada.equals(idConsulta)){
                int confirmacionCancelar = JOptionPane.showConfirmDialog(this, "¿Cancelar Consulta?"+
                    "\nID: " + consultas.get(i).getId() +
                    "\nPaciente: " + consultas.get(i).getPaciente()+
                    "\nMedico: " +consultas.get(i).getMedico()+
                    "\nFecha de la ctia: " +consultas.get(i).getFecha()
                    ,"Confirmar cancelación"
                    ,JOptionPane.YES_NO_OPTION
                );
                if(confirmacionCancelar == 0){
                    consultas.get(i).setEstado("Cancelada");
                }
            }
        }
        actualizarTabla(columnas, consultas);
    }

    public void equiposMedicos() {
        Object[] opciones = {
            "1. Consultar equipo por ID",
            "2. Actualizar equipo",
            "3. Eliminar equipo",
            "4. Listar todos los equipos",
            "5. Filtrar equipos",
            "6. Inventariar equipo"
        }; 
        int opcionSeleccionada = JOptionPane.showOptionDialog(
            this, 
            "SELECCIONE UNA OPCIÓN:", 
            "== GESTIÓN DE EQUIPOS MÉDICOS ==", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            opciones, 
            opciones[0]
        );

        switch (opcionSeleccionada) {
            case 0 -> consultarEquipoID();
            case 1 -> actualizarEquipo();
            case 2 -> eliminarEquipo();
            case 3 -> listarEquiposMedicos();
            case 4 -> filtrarEquipos();
            case 5 -> inventariarEquipo();
            default -> JOptionPane.showMessageDialog(this, "Opción inválida.");
        }
    }

    public void consultarEquipoID() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del equipo médico:");
            if (idStr == null || idStr.trim().isEmpty()) {
                return; // El usuario canceló o no ingresó nada
            }

            int id = Integer.parseInt(idStr);
            EquipoMedico equipo = persistencia.obtenerEquipoPorId(id);

            if (equipo == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe un equipo con el ID proporcionado.");
                return;
            }

            String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};
            Object[][] filas = {{equipo.getId(), equipo.getNombre(), equipo.getCantidad()}};

            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: ID inválido. Asegúrese de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al consultar el equipo médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void filtrarEquipos() {
        try {
            JPanel panelFiltro = new JPanel();
            panelFiltro.setLayout(new BoxLayout(panelFiltro, BoxLayout.Y_AXIS));

            JTextField nombreFiltro = new JTextField();
            JTextField cantidadMinima = new JTextField();
            JTextField cantidadMaxima = new JTextField();

            panelFiltro.add(new JLabel("Filtrar por nombre:"));
            panelFiltro.add(nombreFiltro);
            panelFiltro.add(new JLabel("Cantidad mínima:"));
            panelFiltro.add(cantidadMinima);
            panelFiltro.add(new JLabel("Cantidad máxima:"));
            panelFiltro.add(cantidadMaxima);

            int resultado = JOptionPane.showConfirmDialog(this, panelFiltro, "Filtrar Equipos Médicos", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String nombre = nombreFiltro.getText().trim();
                int min = -1, max = -1;

                try {
                    if (!cantidadMinima.getText().trim().isEmpty()) {
                        min = Integer.parseInt(cantidadMinima.getText().trim());
                    }
                    if (!cantidadMaxima.getText().trim().isEmpty()) {
                        max = Integer.parseInt(cantidadMaxima.getText().trim());
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Error: Ingrese un número válido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Llama al método de la fachada para obtener la lista filtrada
                List<EquipoMedico> equipos = persistencia.filtrarEquipos(nombre, min, max);

                if (equipos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron equipos con los filtros seleccionados.");
                } else {
                    // Actualiza la tabla con los resultados
                    String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};
                    Object[][] filas = new Object[equipos.size()][3];

                    for (int i = 0; i < equipos.size(); i++) {
                        EquipoMedico e = equipos.get(i);
                        filas[i][0] = e.getId();
                        filas[i][1] = e.getNombre();
                        filas[i][2] = e.getCantidad();
                    }

                    modeloTabla.setDataVector(filas, columnas);
                    panelDerecho.removeAll();
                    panelDerecho.revalidate();
                    panelDerecho.repaint();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al filtrar equipos médicos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void eliminarEquipo() {
        try {
            var equipos = persistencia.listarEquiposMedicos();
            if (equipos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No existen equipos médicos.");
                return;
            }

            // Muestra la lista de equipos en la tabla para que el usuario pueda ver los IDs
            String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};
            Object[][] filas = new Object[equipos.size()][3];
            for (int i = 0; i < equipos.size(); i++) {
                EquipoMedico equipo = equipos.get(i);
                filas[i][0] = equipo.getId();
                filas[i][1] = equipo.getNombre();
                filas[i][2] = equipo.getCantidad();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del equipo a eliminar:");
            if (idStr == null || idStr.trim().isEmpty()) {
                return;
            }

            int id = Integer.parseInt(idStr);

            // Obtiene el objeto para mostrar su nombre en el mensaje de confirmación
            EquipoMedico equipoAEliminar = persistencia.obtenerEquipoPorId(id);
            if (equipoAEliminar == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe un equipo con el ID proporcionado.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                this, 
                "¿Está seguro de que desea eliminar el equipo " + equipoAEliminar.getNombre() + "?", 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                // Llama a la fachada para eliminar el equipo
                persistencia.eliminarEquipoMedico(equipoAEliminar);
                JOptionPane.showMessageDialog(this, "Equipo médico eliminado correctamente.");

                // Actualiza la tabla para reflejar los cambios
                actualizarTablaEquipos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: ID inválido. Asegúrese de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al eliminar el equipo médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método auxiliar para actualizar la tabla de equipos
    private void actualizarTablaEquipos() {
        try {
            var equipos = persistencia.listarEquiposMedicos();
            String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};
            Object[][] filas = new Object[equipos.size()][3];
            for (int i = 0; i < equipos.size(); i++) {
                EquipoMedico e = equipos.get(i);
                filas[i][0] = e.getId();
                filas[i][1] = e.getNombre();
                filas[i][2] = e.getCantidad();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla de equipos: " + e.getMessage());
        }
    }

    public void actualizarEquipo() {
        try {
            var equipos = persistencia.listarEquiposMedicos();
            if (equipos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ERROR: No existen equipos médicos.");
                return;
            }

            // Muestra la lista de equipos en la tabla
            String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};
            Object[][] filas = new Object[equipos.size()][3];
            for (int i = 0; i < equipos.size(); i++) {
                EquipoMedico equipo = equipos.get(i);
                filas[i][0] = equipo.getId();
                filas[i][1] = equipo.getNombre();
                filas[i][2] = equipo.getCantidad();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del equipo a actualizar:");
            if (idStr == null || idStr.trim().isEmpty()) {
                return;
            }
            int idEquipoAct = Integer.parseInt(idStr);
            EquipoMedico equipoA = persistencia.obtenerEquipoPorId(idEquipoAct);

            if (equipoA == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe un equipo con el ID proporcionado.");
                return;
            }

            JTextField nombreEquipo = new JTextField(equipoA.getNombre());
            JTextField cantidadEquipo = new JTextField(String.valueOf(equipoA.getCantidad()));

            JPanel panelActualizacion = new JPanel();
            panelActualizacion.setLayout(new BoxLayout(panelActualizacion, BoxLayout.Y_AXIS));
            panelActualizacion.add(new JLabel("NOMBRE EQUIPO:"));
            panelActualizacion.add(nombreEquipo);
            panelActualizacion.add(new JLabel("CANTIDAD:"));
            panelActualizacion.add(cantidadEquipo);

            int resultado = JOptionPane.showConfirmDialog(
                this, 
                panelActualizacion, 
                "== Actualizar Equipo Médico ==", 
                JOptionPane.OK_CANCEL_OPTION, 
                JOptionPane.PLAIN_MESSAGE
            );

            if (resultado == JOptionPane.OK_OPTION) {
                try {
                    String nombre = nombreEquipo.getText();
                    int cantidad = Integer.parseInt(cantidadEquipo.getText().trim());

                    if (nombre.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "ERROR: El campo 'NOMBRE' no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    EquipoMedico equipoAct = new EquipoMedico(equipoA.getId(), nombre, cantidad);
                    persistencia.actualizarEquipoMedico(equipoAct);
                    JOptionPane.showMessageDialog(this, "Equipo médico actualizado correctamente.");

                    // Actualiza la tabla para reflejar los cambios
                    actualizarTablaEquipos();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "ERROR: Formato de número inválido para la cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ERROR: ID de equipo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al actualizar el equipo médico: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarEquiposMedicos() {
        try {
            List<EquipoMedico> equipos = persistencia.listarEquiposMedicos();
            if (equipos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay equipos médicos registrados.");
                // Limpia la tabla si estaba mostrando otros datos
                modeloTabla.setRowCount(0); 
                return;
            }

            // Define las columnas para la tabla
            String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};

            // Crea una matriz de objetos para las filas de la tabla
            Object[][] filas = new Object[equipos.size()][3];

            // Llena la matriz con los datos de cada equipo
            for (int i = 0; i < equipos.size(); i++) {
                EquipoMedico eq = equipos.get(i);
                filas[i][0] = eq.getId();
                filas[i][1] = eq.getNombre();
                filas[i][2] = eq.getCantidad();
            }

            // Actualiza el modelo de la tabla con los nuevos datos
            modeloTabla.setDataVector(filas, columnas);

            // Asegura que el panel derecho se refresque para mostrar la tabla
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al listar equipos médicos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void inventariarEquipo() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Ingrese el ID del equipo para inventariar:");
            if (idStr == null || idStr.trim().isEmpty()) {
                return;
            }

            int id = Integer.parseInt(idStr);
            EquipoMedico equipo = persistencia.obtenerEquipoPorId(id);

            if (equipo == null) {
                JOptionPane.showMessageDialog(this, "ERROR: No existe un equipo con el ID proporcionado.");
                return;
            }
            
            // Presentar la información actual del equipo
            JOptionPane.showMessageDialog(
                this,
                "Información actual del equipo:\n" +
                "ID: " + equipo.getId() + "\n" +
                "Nombre: " + equipo.getNombre() + "\n" +
                "Cantidad actual: " + equipo.getCantidad(),
                "Información del Equipo",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            String cantidadStr = JOptionPane.showInputDialog(this, "¿Cuántas unidades desea añadir o quitar? (Use un número negativo para quitar):");
            if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
                return;
            }

            int cantidadAñadir = Integer.parseInt(cantidadStr);

            int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Confirma el ajuste de stock de " + cantidadAñadir + " unidades?",
                "Confirmar Ajuste",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                persistencia.inventariarEquipoMedico(id, cantidadAñadir);
                JOptionPane.showMessageDialog(this, "Inventario de equipo actualizado correctamente.");
                actualizarTablaEquipos();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ERROR: ID o cantidad inválida. Asegúrese de ingresar números.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR al ajustar el inventario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Nota: Asegúrate de tener un método `actualizarTablaEquipos()` que refresque el JTable
    // para que los cambios se visualicen. Si ya lo tienes, úsalo. Si no, créalo.
    // Por ejemplo:
    private void actualizarTablaEquipos1() {
        try {
            var equipos = persistencia.listarEquiposMedicos();
            String[] columnas = {"ID", "NOMBRE", "CANTIDAD"};
            Object[][] filas = new Object[equipos.size()][3];
            for (int i = 0; i < equipos.size(); i++) {
                EquipoMedico e = equipos.get(i);
                filas[i][0] = e.getId();
                filas[i][1] = e.getNombre();
                filas[i][2] = e.getCantidad();
            }
            modeloTabla.setDataVector(filas, columnas);
            panelDerecho.removeAll();
            panelDerecho.revalidate();
            panelDerecho.repaint();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la tabla: " + e.getMessage());
        }
    }
    
    public void salir() {
        System.exit(0);
    }
}