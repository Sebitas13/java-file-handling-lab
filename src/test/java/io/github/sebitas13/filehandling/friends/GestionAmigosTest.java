package io.github.sebitas13.filehandling.friends;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class GestionAmigosTest {

    @Test
    void parsesPhoneWithHyphen() {
        String[] fields = GestionAmigos.parseLinea("Persona Ficticia|Calle Falsa 123|555-0100");
        assertNotNull(fields);
        assertEquals(3, fields.length);
        assertEquals("Persona Ficticia", fields[0]);
        assertEquals("Calle Falsa 123", fields[1]);
        assertEquals("555-0100", fields[2]);
    }

    @Test
    void rejectsMalformedLines() {
        assertNull(GestionAmigos.parseLinea("sin separadores"));
        assertNull(GestionAmigos.parseLinea("a|b"));
        assertNull(GestionAmigos.parseLinea("a||c"));
        assertNull(GestionAmigos.parseLinea(""));
    }
}
