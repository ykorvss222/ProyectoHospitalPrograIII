 
package com.mycompany.proyectoprogramacion3;

import Menu.MenuPrincipal;
import Persistencia.*;


 
public class ProyectoProgramacion3 {

    public static void main(String[] args) {
        IPersistenciaFachada persistencia = new PersistenciaFachada();
        MenuPrincipal menu = new MenuPrincipal(persistencia);
       
        menu.mostrarTabla();
     
    }
}
