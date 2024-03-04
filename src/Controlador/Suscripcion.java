package Controlador;

import java.sql.SQLException;
import com.mysql.jdbc.PreparedStatement;
import java.util.Random;
import Modelo.ConexionMySQL;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Suscripcion {
    
    private static ConexionMySQL conexion;
    private int folio;
    
    public Suscripcion(){
        conexion = new ConexionMySQL();
    }
    
    public int getFolio(){
        return this.folio;
    }
        
    public int generarFolioUnico() {
        Random random = new Random();
        
        // Generar folio único de 6 dígitos
        int folioRamdom = random.nextInt(900000) + 100000;
        
        // Verificar si el folio generado ya existe en la base de datos
        // Supongamos que tienes una conexión a la base de datos llamada 'conn'
        String sql = "SELECT COUNT(*) AS count FROM Pensionado WHERE Folio_Pen = ?";
        
        try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            pstmt.setInt(1, folio);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                // Si el folio ya existe en la base de datos, genera otro folio recursivamente
                if (count > 0) {
                    folioRamdom = generarFolioUnico();
                }
            }
                  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return folioRamdom;
    }
    
    
    public void creaUsuario(String usuario, String contra){
        String sql = "INSERT INTO Sesion(Usuario, Contraseña) VALUES (?, ?)";
        try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
            pstmt.setString(2, contra);
            
            int result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println("Usuario creado");
            } 
            else {
                System.out.println("Usuario no creado");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace(); }
    }
    
    
    public int recuperarIdSesion(String usuario){
        String sql = "SELECT ID_Sesion FROM Sesion WHERE Usuario = ?";
        int idSesion = 0;
        try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            
            pstmt.setString(1, usuario);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    idSesion = rs.getInt("ID_Sesion");
                }
        }catch (SQLException e) {
            e.printStackTrace(); 
        }
        return idSesion;
        
    }
    
    public void registroPersona(String nombre, String apellidoP, String apellidoM, long tel, String email, String usuario, String contra){
        
        //Genera el folio
        folio = generarFolioUnico();
        
        //Crea una cuenta 
        creaUsuario(usuario, contra);
        
        //Recupera el id relacionado a la cuenta 
        int id = recuperarIdSesion(usuario);
       
        //Inserta los datos a la tabla pensionados 
        String sql = "INSERT INTO Pensionado(Folio_Pen, Nombre, ApellidoP, ApellidoM, Telefono, ID_Sesion, Email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            
      
            pstmt.setInt(1, folio);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apellidoP);
            pstmt.setString(4, apellidoM);
            pstmt.setLong(5, tel);
            pstmt.setInt(6, id);
            pstmt.setString(7, email);
            
            int result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println("Usuario creado");
            } 
            
            else {
                System.out.println("Usuario no creado");
            }
        } 
    
        catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public void registroTarjeta(int miFolio, int numTarjeta, String tipo, int cvv, String vigencia){
        
        String sql = "INSERT INTO Tarjeta(Num_Tar, Tipo, CVV, Vigencia, Folio_Pen) VALUES (?, ?, ?, ?, ?)";
        try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)) {
            
      
            pstmt.setInt(1, numTarjeta);
            pstmt.setString(2, tipo);
            pstmt.setInt(3, cvv);
            pstmt.setString(4, vigencia);
            pstmt.setInt(5, miFolio);
            
            
            int result = pstmt.executeUpdate();

            if (result == 1) {
                System.out.println("Usuario creado");
            } 
            
            else {
                System.out.println("Usuario no creado");
            }
        } 
    
        catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    
    public void activaMembresia(int miFolio){
        
         String sql = "INSERT INTO Membresia (Fecha_Inicio, Fecha_Fin, Estado, Folio_Pen) VALUES (?, ?, ?, ?)";
         
         LocalDate fechaInicio = LocalDate.now();
         LocalDate fechaFin = fechaInicio.plusMonths(1);
         
         try (java.sql.Connection conn = conexion.conectar(); 
             PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql)){
                    
            pstmt.setDate(1, java.sql.Date.valueOf(fechaInicio));
            pstmt.setDate(2, java.sql.Date.valueOf(fechaFin));
            pstmt.setString(3, "ACTIVO");
            pstmt.setInt(4, miFolio);
            
            int resultado = pstmt.executeUpdate();
            if (resultado == 1) {
                System.out.println("Membresía insertada correctamente.");
            } else {
                System.out.println("Error al insertar la membresía.");
            }
             
             
         }catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
}
