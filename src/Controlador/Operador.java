package Controlador;

import Modelo.ConexionMySQL;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Operador {
    private static ConexionMySQL conexion;
    
    public Operador(){
        conexion = new ConexionMySQL();
    }
    
public boolean autentica(String usuario, String contra){
    boolean autenticado = false; // Por defecto, no autenticado
    String sql = "SELECT COUNT(*) AS count FROM Sesion WHERE Usuario = ? AND Contraseña = ? AND ID_Sesion = 1";
    try (java.sql.Connection conn = conexion.conectar(); 
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)){
        
        pstmt.setString(1, usuario);
        pstmt.setString(2, contra);
        ResultSet rs = pstmt.executeQuery();
        // Si se encuentra un usuario que coincide con el nombre de usuario, contraseña y el id_usuario es 1, autenticar como verdadero
        if (rs.next()) {
            int count = rs.getInt("count");
            // Si count es mayor que 0, el usuario, contraseña y el id_usuario coinciden en la tabla
            autenticado = count > 0;
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return autenticado;
}

}
