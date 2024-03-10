package Controlador;
import Modelo.*;
import com.mysql.jdbc.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class Pensionado {
    private static ConexionMySQL conexion;
    
    public Pensionado(){
        conexion = new ConexionMySQL();
    }
    
    public boolean autentica(String usuario, String contra){
        boolean autenticado = false;
        String sql = "SELECT COUNT(*) AS count FROM Sesion WHERE Usuario = ? AND Contraseña = ?";
        try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)){
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, contra);
            ResultSet rs = pstmt.executeQuery();
            // Si se encuentra un usuario que coincide con el nombre de usuario y contraseña proporcionados, autenticar como verdadero
            if (rs.next()) {
                int count = rs.getInt("count");
                // Si count es mayor que 0, el usuario y contraseña coinciden en la tabla
                autenticado = count > 0;
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return autenticado;
    }
    
    
}
