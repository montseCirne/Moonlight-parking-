package Controlador;

import com.mysql.jdbc.PreparedStatement;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static Controlador.Suscripcion.conexion;
import static org.junit.jupiter.api.Assertions.*;
class SuscripcionTest {
    @Test
    void getFolio() {}
    @Test
    int generarFolioUnico() {
        Suscripcion suscripcion = new Suscripcion();
        int folio1 = suscripcion.generarFolioUnico();
        int folio2 = suscripcion.generarFolioUnico();
        assertNotEquals(folio1, folio2);
        System.out.println("Folios no iguales");
        return folio1;
    }

    @Test
    void creaUsuario(String usuario, String contra) throws SQLException {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.creaUsuario("Persona1", "Contraseña1");
        String sql = "SELECT COUNT(*) FROM Sesion WHERE Usuario = ?";
        java.sql.Connection conn = conexion.conectar();
        PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
        pstmt.setString(1, "NO");
        ResultSet rs = pstmt.executeQuery();
        assertFalse(rs.next() && rs.getInt("COUNT(*)") == 1);
    }
/*
    @Test
    void recuperarIdSesion() {
        Suscripcion suscripcion = new Suscripcion();
        suscripcion.creaUsuario("Mont", "prueba");
        int idSesion = suscripcion.recuperarIdSesion("Mont");
        assertEquals(12, idSesion);
        System.out.println("Recuperacion correcta");
    }
*/

    @Test
    public void registroPersona() {
        Suscripcion suscripcion = new Suscripcion();
        String nombre="MON";
        String apellidoP="CIRNE";
        String apellidoM="CASTRO";
        long tel=2229226123L;
        String email="mon@movis";
        String usuario="mon";
        String contra= "cir";
        int folio = generarFolioUnico();

        try {
            creaUsuario(usuario, contra);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Get the ID associated with the user
        int id = suscripcion.recuperarIdSesion("mon");
        // Insert the data into the pensionados table
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
            assertEquals(1, result);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    void registroTarjeta() {
    }

    @Test
    void activaMembresia() {
    }
}