/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Erick
 */
public class ConexionMySQL{
    Connection cn;
    
    public Connection conectar(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            cn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/parking","root",""); //Cambiar al nombre de su base de datos
            System.out.println("Conexion establecida de manera correcta.");
        } catch(Exception e){
            System.out.println("Error en la conexion " + e);
        }
        return cn;
    }
}
