package Controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class OperadorTest {
    @Test
    void autentica() {
        Operador operador = new Operador();
        boolean autenticado;
        autenticado = operador.autentica("Administrador", "admin01");
        assertTrue(autenticado);
        System.out.println("Acceso correcto");

        autenticado = operador.autentica("unUsuario", "123");
        assertFalse(autenticado);
        System.out.println("Acceso denegado");

        autenticado = operador.autentica("Administrador", "admin1");
        assertFalse(autenticado);
        System.out.println("Contraseña incorrecta");
    }
}