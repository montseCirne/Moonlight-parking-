package Controlador;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PensionadoTest {
    @Test
    void autentica() {
        Pensionado pensionado = new Pensionado();
        boolean autenticado;
        autenticado = pensionado.autentica("karen", "ericklover");
        assertTrue(autenticado);
        System.out.println("Acceso Correcto");

        autenticado = pensionado.autentica("Montse", "Cirne");
        assertFalse(autenticado);
        System.out.println("Acceso Denegado");

        autenticado = pensionado.autentica("sher17", "contraseña");
        assertFalse(autenticado);
        System.out.println("Contraseña incorrecta");
    }
}