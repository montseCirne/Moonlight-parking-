package Modelo;

import com.mysql.jdbc.Connection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConexionMySQLTest {
    public ConexionMySQLTest() {}
    
    @Test
    public void testConectar() {
        ConexionMySQL conexion = new ConexionMySQL();
        Connection cn = conexion.conectar();
        assertNotNull(cn);
        System.out.println("Prueba de conexión exitosa.");
        /*
        *ConexionMySQL conexion = new ConexionMySQL();
        *Connection cn = conexion.conectar();
        *assertNull(cn);
        *System.out.println("Prueba de conexión fallida.");
        */
    }
}
