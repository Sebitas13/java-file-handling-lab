package io.github.sebitas13.filehandling.vaccination;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Vaccination records: composition ({@link Persona} -> {@link Vacuna}) plus
 * object serialization.
 *
 * <p>Data is stored in {@code data/vacunas.txt}. Deserialization is limited by
 * an {@link ObjectInputFilter} so only the expected classes can be read.</p>
 *
 * @author Sebitas13
 */
public class OperacionesVacunas {

    private static final Path DATA_FILE = Paths.get("data", "vacunas.txt");

    private List<Persona> listaPersonas;

    public OperacionesVacunas() {
        this.listaPersonas = new ArrayList<>();
        ensureDataDirectory();
    }

    private static void ensureDataDirectory() {
        try {
            Files.createDirectories(DATA_FILE.getParent());
        } catch (IOException e) {
            System.err.println("No se pudo crear la carpeta data: " + e.getMessage());
        }
    }

    private int leerEntero(Scanner leer, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (leer.hasNextInt()) {
                int valor = leer.nextInt();
                leer.nextLine();
                return valor;
            }
            System.out.println("Debes ingresar un numero entero valido.");
            leer.nextLine();
        }
    }

    /** Registers a person with the first dose. */
    public void registrarPersonaPrimeraDosis(Scanner leer) {
        int cedula = leerEntero(leer, "Digite la cedula de la persona: ");
        System.out.print("Digite el nombre completo: ");
        String nombre = leer.nextLine();
        System.out.print("Digite la fecha de nacimiento (ej. 12-05-1990): ");
        String fechaNacimiento = leer.nextLine();
        System.out.print("Digite la marca de la vacuna (ej. Vacuna A): ");
        String marca = leer.nextLine();
        System.out.print("Digite el vencimiento (ej. 30-12-2026): ");
        String vencimiento = leer.nextLine();
        System.out.print("Digite la fecha de aplicacion (ej. 15-08-2026): ");
        String fechaAplicacion = leer.nextLine();

        Persona persona = new Persona(cedula, nombre, fechaNacimiento);
        persona.adicionarDosis(new Vacuna(marca, vencimiento, fechaAplicacion));
        listaPersonas.add(persona);
        System.out.println("Persona registrada con la primera dosis.");
    }

    /** Finds a person by id and adds the next dose. */
    public void buscarAdicionarSiguienteDosis(Scanner leer) {
        if (listaPersonas.isEmpty()) {
            System.out.println("No hay personas registradas.");
            return;
        }
        int cedula = leerEntero(leer, "Digite la cedula de la persona: ");
        for (Persona persona : listaPersonas) {
            if (persona.getCedula() == cedula) {
                System.out.print("Digite la marca de la vacuna: ");
                String marca = leer.nextLine();
                System.out.print("Digite el vencimiento (ej. 30-01-2027): ");
                String vencimiento = leer.nextLine();
                System.out.print("Digite la fecha de aplicacion (ej. 15-09-2026): ");
                String fechaAplicacion = leer.nextLine();
                persona.adicionarDosis(new Vacuna(marca, vencimiento, fechaAplicacion));
                System.out.println("Dosis Nro " + persona.getDosis().size()
                        + " agregada a " + persona.getNombreCompleto());
                return;
            }
        }
        System.out.println("No se encontro ninguna persona con cedula " + cedula);
    }

    /** Shows every person and their doses. */
    public void mostrarPersonasConDosis() {
        if (listaPersonas.isEmpty()) {
            System.out.println("No hay personas registradas.");
            return;
        }
        System.out.println("===== PERSONAS Y SUS DOSIS =====");
        for (Persona persona : listaPersonas) {
            persona.mostrarPersona();
            System.out.println("----------------------------------------");
        }
    }

    /** Saves the full list using object serialization and an atomic move. */
    public void guardarObjetos() {
        ensureDataDirectory();
        Path temp = null;
        try {
            temp = Files.createTempFile(DATA_FILE.getParent(), "vacunas", ".tmp");
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(temp.toFile()))) {
                out.writeObject(listaPersonas);
            }
            try {
                Files.move(temp, DATA_FILE, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, DATA_FILE, StandardCopyOption.REPLACE_EXISTING);
            }
            temp = null;
            System.out.println("Datos guardados en " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("No se pudo guardar " + DATA_FILE + ": " + e.getMessage());
        } finally {
            if (temp != null) {
                try {
                    Files.deleteIfExists(temp);
                } catch (IOException ignored) {
                    // best effort cleanup
                }
            }
        }
    }

    /** Loads the list with a whitelist filter for deserialization. */
    @SuppressWarnings("unchecked")
    public void leerObjetos() {
        ensureDataDirectory();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE.toFile()))) {
            in.setObjectInputFilter(info -> {
                Class<?> type = info.serialClass();
                if (type == null) {
                    return ObjectInputFilter.Status.UNDECIDED;
                }
                if (info.depth() > 20 || info.references() > 10000 || info.arrayLength() > 10000) {
                    return ObjectInputFilter.Status.REJECTED;
                }
                if (type.isArray() && type.getComponentType() == Object.class) {
                    return ObjectInputFilter.Status.ALLOWED;
                }
                if (type == Persona.class
                        || type == Vacuna.class
                        || type == String.class
                        || type == Integer.class
                        || type == ArrayList.class) {
                    return ObjectInputFilter.Status.ALLOWED;
                }
                return ObjectInputFilter.Status.REJECTED;
            });
            List<Persona> cargadas = (List<Persona>) in.readObject();
            listaPersonas = new ArrayList<>(cargadas);
            System.out.println("Datos cargados desde " + DATA_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Aun no existe " + DATA_FILE + ". Registra primero una persona.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("No se pudo leer " + DATA_FILE + ": " + e.getMessage());
        }
    }
}
