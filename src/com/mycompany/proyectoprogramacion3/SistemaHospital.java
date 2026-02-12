 package com.mycompany.proyectoprogramacion3;

import Menu.MenuPrincipal;
import Persistencia.*;

/*
 INTEGRANTES:
 JORGE MAYORQUIN URBINA URIAS
 JAVIER ENRIQUE VALENZUELA TRUJILLO
 YAZID ENRIQUE REVILLA BERNAL 2726262
 REPOSITORIO: https://github.com/ykorvss222/ProyectoHospitalPrograIII
 */
public class SistemaHospital {

    public static void main(String[] args) {
        IPersistenciaFachada persistencia = new PersistenciaFachada();
        MenuPrincipal menu = new MenuPrincipal(persistencia);
       
        menu.mostrarTabla();
     
    }
}
