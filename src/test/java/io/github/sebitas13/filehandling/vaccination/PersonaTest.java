package io.github.sebitas13.filehandling.vaccination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

class PersonaTest {

    private Persona persona() {
        Persona persona = new Persona(1001, "Persona Ficticia", "01-01-2000");
        persona.adicionarDosis(new Vacuna("Vacuna A", "31-12-2026", "01-01-2026"));
        return persona;
    }

    @Test
    void addsDoses() {
        Persona persona = persona();
        assertEquals(1, persona.getDosis().size());
        persona.adicionarDosis(new Vacuna("Vacuna B", "31-01-2027", "01-02-2026"));
        assertEquals(2, persona.getDosis().size());
    }

    @Test
    void dosisListIsUnmodifiable() {
        Persona persona = persona();
        assertThrows(UnsupportedOperationException.class,
                () -> persona.getDosis().add(new Vacuna("X", "X", "X")));
    }

    @Test
    void serializationRoundTrip() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(persona());
        }

        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()))) {
            Persona restored = (Persona) in.readObject();
            assertEquals(1001, restored.getCedula());
            assertEquals("Persona Ficticia", restored.getNombreCompleto());
            assertEquals(1, restored.getDosis().size());
            assertEquals("Vacuna A", restored.getDosis().get(0).getMarca());
        }
    }

    @Test
    void rejectsNullDose() {
        Persona persona = new Persona(1002, "Otra Persona", "02-02-2000");
        assertThrows(IllegalArgumentException.class, () -> persona.adicionarDosis(null));
    }
}
