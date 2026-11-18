package io.github.sebitas13.filehandling.friends;

import io.github.sebitas13.filehandling.FileStorage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <b>EJERCICIO 2 (File Handling - MANEJO DE ARCHIVOS):</b> operaciones sobre el
 * archivo de texto {@code amigos.txt}.
 *
 * <p>
 * El archivo guarda un amigo por línea, con sus datos separados por el carácter
 * {@code |} de esta forma: {@code nombre|direccion|telefono}. Por ejemplo:
 * {@code Persona Ficticia|Calle Falsa 123|555-0100} (datos ficticios).</p>
 *
 * <p>
 * ¿Por qué se separan los campos? Porque el archivo es TEXTO plano: no tiene
 * columnas. La única forma de saber "dónde termina el nombre y empieza la
 * dirección" es usando un separador. Para recuperar los campos se usa
 * {@code parseLinea()}, que parte la línea en tres campos y conserva los guiones
 * internos del teléfono:
 * {@code "Persona Ficticia|Calle Falsa|555-0100"} devuelve
 * {@code ["Persona Ficticia", "Calle Falsa", "555-0100"]}.</p>
 *
 * <p>
 * Incisos del ejercicio:</p>
 * <ul>
 * <li>Inciso a) {@link #adicionarAmigo(Scanner)}</li>
 * <li>Inciso b) {@link #buscarAmigo(Scanner)}</li>
 * <li>Inciso c) {@link #modificarCelular(Scanner)}</li>
 * </ul>
 *
 * <p>
 * NOTA SOBRE ESCRIBIR VS. AGREGAR:</p>
 * <ul>
 * <li>{@code StandardOpenOption.APPEND} → AGREGA al final del archivo, sin
 * borrar lo que ya había (se usa para adicionar amigos).</li>
 * <li>escribir sin esa opción (como en {@link #modificarCelular(Scanner)}) →
 * SOBREESCRIBE el archivo completo con el contenido que se le pase.</li>
 * </ul>
 * <p>
 * Para modificar una línea, el truco es: leer TODO el archivo, cambiar la línea
 * en la lista (en memoria) y volver a escribir la lista completa al
 * archivo.</p>
 *
 * @author Sebitas13
 */
public class GestionAmigos {

    /**
     * Nombre del archivo (ruta relativa = carpeta del proyecto).
     */
    private static final String NOMBRE_ARCHIVO = "amigos.txt";

    /**
     * Carácter que separa los campos (nombre | direccion | telefono).
     */
    private static final String SEPARADOR = "|";

    /**
     * Ruta del archivo ya construida.
     */
    private final Path ruta;

    /**
     * Constructor: prepara la ruta del archivo. (El Scanner se pasa por
     * parámetro desde Principal, ver clase {@code GestionPoesia} para la
     * explicación de por qué.)
     */
    public GestionAmigos() {
        this.ruta = Paths.get("data", NOMBRE_ARCHIVO);
        try {
            Files.createDirectories(this.ruta.getParent());
        } catch (IOException e) {
            System.err.println("No se pudo crear la carpeta data: " + e.getMessage());
        }
    }

    /**
     * Parses one line into exactly three fields: name, address and phone.
     * The third field keeps any hyphens, so a phone like {@code 555-0100}
     * survives a round trip.
     *
     * @param linea raw line
     * @return three fields, or {@code null} if the line is malformed
     */
    static String[] parseLinea(String linea) {
        if (linea == null || linea.isBlank()) {
            return null;
        }
        String[] partes = linea.split(java.util.regex.Pattern.quote(SEPARADOR), 3);
        if (partes.length < 3) {
            return null;
        }
        for (String parte : partes) {
            if (parte.isBlank()) {
                return null;
            }
        }
        return partes;
    }

    /**
     * Crea el archivo {@code amigos.txt} vacío si todavía no existe (mismo
     * patrón que el {@code crearArchivo()} del ejercicio).
     *
     * <p>
     * {@code Files.createFile(ruta)} crea el archivo SOLO si no existe; por eso
     * antes se verifica con {@code Files.exists()} para avisar en vez de lanzar
     * error.</p>
     */
    public void crearArchivo() {
        try {
            if (!Files.exists(ruta)) {
                Files.createFile(ruta);
                System.out.println("Archivo amigos.txt creado");
            } else {
                System.out.println("El archivo amigos.txt ya existe");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso a) - Adicionar amigos:</b> pide nombre, dirección y teléfono,
     * arma la línea {@code nombre|direccion|telefono} y la AGREGA al final del
     * archivo. Pregunta si se quiere seguir agregando (S/N), igual que el
     * método {@code adicionarContacto()} del ejercicio.
     *
     * <p>
     * El {@code do-while} con {@code res.equalsIgnoreCase("S")} repite el
     * registro mientras el usuario digite S.</p>
     *
     * @param leer objeto {@link Scanner} para leer los datos del amigo
     */
    public void adicionarAmigo(Scanner leer) {
        String nombre, direccion, telefono, res;
        try {
            if (!Files.exists(ruta)) {
                System.out.println("El archivo no existe. Primero usa la opcion 1");
                return;
            }
            do {
                System.out.print("Digite el nombre del amigo: ");
                nombre = leer.nextLine();
                System.out.print("Digite la direccion: ");
                direccion = leer.nextLine();
                System.out.print("Digite el numero de telefono: ");
                telefono = leer.nextLine();

                if (nombre.contains(SEPARADOR) || direccion.contains(SEPARADOR) || telefono.contains(SEPARADOR)) {
                    System.out.println("No uses el separador | en los campos.");
                    return;
                }

                // Se arma la linea con el formato "nombre|direccion|telefono".
                String linea = nombre + SEPARADOR + direccion + SEPARADOR + telefono + "\n";

                // APPEND = agregar al final SIN borrar lo que ya existia.
                Files.write(ruta, linea.getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.APPEND);
                System.out.println("Amigo registrado correctamente");

                System.out.print("Desea agregar otro amigo (S/N)? ");
                res = leer.nextLine();
            } while (res.equalsIgnoreCase("S"));
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso b) - Buscar un amigo por nombre:</b> lee todo el archivo y
     * compara el PRIMER campo (la posición 0 del arreglo de {@code split}) con
     * el nombre buscado, sin distinguir mayúsculas.
     *
     * <p>
     * La variable {@code encontrado} (bandera/interruptor) Empieza en false y
     * cambia a true cuando encuentra al amigo. Al final, si sigue en false es
     * que no se encontró nada.</p>
     *
     * @param leer objeto {@link Scanner} para leer el nombre a buscar
     */
    public void buscarAmigo(Scanner leer) {
        String nomBuscado;
        boolean encontrado = false;
        try {
            if (!Files.exists(ruta)) {
                System.out.println("El archivo no existe. Primero usa la opcion 1");
                return;
            }
            List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            if (lineas.isEmpty()) {
                System.out.println("El archivo esta vacio");
                return;
            }

            System.out.print("Digite el nombre del amigo a buscar: ");
            nomBuscado = leer.nextLine();

            for (String linea : lineas) {
                String[] vector = parseLinea(linea); // [nombre, direccion, telefono]
                if (vector == null) {
                    System.out.println("Linea mal formada, se omite: " + linea);
                    continue;
                }
                if (nomBuscado.equalsIgnoreCase(vector[0])) {
                    encontrado = true;
                    System.out.println("---- AMIGO ENCONTRADO ----");
                    System.out.println("Nombre:    " + vector[0]);
                    System.out.println("Direccion: " + vector[1]);
                    System.out.println("Telefono:  " + vector[2]);
                    System.out.println("--------------------------");
                }
            }
            if (!encontrado) {
                System.out.println("El amigo \"" + nomBuscado + "\" no esta registrado");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso c) - Modificar el celular de un amigo:</b> busca la línea del
     * amigo por nombre, arma la línea nueva con el nuevo teléfono y vuelve a
     * escribir TODAS las líneas al archivo (sobreescribiéndolo).
     *
     * <p>
     * Este es el patrón clave del inciso c, que sirve también para borrar o
     * modificar cualquier dato en un archivo de texto:</p>
     * <ol>
     * <li>LEER todas las líneas con {@code readAllLines}.</li>
     * <li>MODIFICAR la lista en memoria (aquí: reconstruir la línea con el
     * nuevo teléfono).</li>
     * <li>REESCRIBIR la lista completa con {@code Files.write} SIN la opción
     * APPEND (así el archivo queda reemplazado por la lista nueva).</li>
     * </ol>
     *
     * @param leer objeto {@link Scanner} para leer el nombre y el nuevo
     * teléfono
     */
    public void modificarCelular(Scanner leer) {
        String nomBuscado, nuevoTelefono;
        int sw = 0;
        try {
            if (!Files.exists(ruta)) {
                System.out.println("El archivo no existe. Primero usa la opcion 1");
                return;
            }
            List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            if (lineas.isEmpty()) {
                System.out.println("El archivo esta vacio.");
                return;
            }

            System.out.print("Digite el nombre del amigo a modificar: ");
            nomBuscado = leer.nextLine();
            System.out.print("Digite el NUEVO numero de celular: ");
            nuevoTelefono = leer.nextLine();
            if (nuevoTelefono.isBlank() || nuevoTelefono.contains(SEPARADOR)) {
                System.out.println("Telefono invalido: no puede estar vacio ni contener |.");
                return;
            }

            // Lista nueva donde se guardan las lineas (con la modificada)
            List<String> lineasNuevas = new ArrayList<>();
            for (String linea : lineas) {
                String[] vector = parseLinea(linea);
                if (vector == null) {
                    System.out.println("Linea mal formada, se conserva sin cambios: " + linea);
                    lineasNuevas.add(linea);
                    continue;
                }
                if (nomBuscado.equalsIgnoreCase(vector[0])) {
                    // Se reconstruye la linea con el nuevo telefono.
                    lineasNuevas.add(vector[0] + SEPARADOR + vector[1] + SEPARADOR + nuevoTelefono);
                    sw = 1;
                } else {
                    // Las demas lineas se copian tal cual.
                    lineasNuevas.add(linea);
                }
            }

            if (sw == 1) {
                // Sin APPEND: se SOBREESCRIBE el archivo con la lista modificada.
                FileStorage.writeLinesAtomically(ruta, lineasNuevas);
                System.out.println("Numero de celular actualizado correctamente");
            } else {
                System.out.println("El amigo \"" + nomBuscado + "\" no esta registrado");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }
}
