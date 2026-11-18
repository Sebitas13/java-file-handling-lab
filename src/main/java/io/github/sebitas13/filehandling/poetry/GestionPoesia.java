package io.github.sebitas13.filehandling.poetry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * <b>EJERCICIO 1 (File Handling - MANEJO DE ARCHIVOS):</b> operaciones sobre el
 * archivo de texto {@code poesia.txt}.
 *
 * <p>
 * Esta clase agrupa TODAS las operaciones que se pueden hacer con el archivo.
 * Sigue el esquema de una clase de gestión y una clase principal:
 * {@code Gestion/Operaciones} con los métodos del problema y una clase
 * {@code Principal} con el menú en consola.</p>
 *
 * <p>
 * Para leer/escribir el archivo se usa la API moderna
 * <b>{@code java.nio.file}</b>:</p>
 * <ul>
 * <li>{@code Files.write(listaDeLineas)} → CREA el archivo y escribe TODAS las
 * líneas de una sola vez.</li>
 * <li>{@code Files.readAllLines(ruta)} → LEE todas las líneas y las devuelve en
 * una {@link List} de {@link String} (una posición por línea).</li>
 * <li>{@code Path} + {@code Paths} → representan la ubicación del archivo.</li>
 * <li>{@code StandardOpenOption} → opciones al abrir el archivo (CREATE_NEW,
 * APPEND, etc.).</li>
 * </ul>
 *
 * <p>
 * NOTA SOBRE LA RUTA: se usa una ruta <b>relativa</b> ({@code "poesia.txt"}),
 * por lo que el archivo se crea dentro de la carpeta del proyecto (donde está
 * el {@code pom.xml}). Si quieres una ruta fija como en otros ejemplos (ej.
 * {@code D:\\programacion\\poesia.txt}), solo cambia la constante
 * {@link #NOMBRE_ARCHIVO}.</p>
 *
 * <p>
 * Relación con los incisos del ejercicio:</p>
 * <ul>
 * <li>Inciso a) {@link #crearArchivoPoesia()} y
 * {@link #mostrarContenido()}</li>
 * <li>Inciso b) {@link #mostrarLineaConMasPalabras()}</li>
 * <li>Inciso c) {@link #buscarPalabra(Scanner)}</li>
 * </ul>
 *
 * @author Sebitas13
 */
public class GestionPoesia {

    /**
     * Nombre del archivo. Al ser relativo, se crea en la carpeta del proyecto.
     * Para usar una ruta absoluta: {@code "D:\\programacion\\poesia.txt"}.
     */
    private static final String NOMBRE_ARCHIVO = "poesia.txt";

    /**
     * Ruta del archivo ya construida, para no repetir {@code Paths.get()} en
     * cada método.
     */
    private final Path ruta;

    /**
     * Constructor de la clase. Solo prepara la ruta del archivo.
     *
     * <p>
     * NOTA: el {@link Scanner} NO se crea aquí. Se pasa como parámetro desde la
     * clase {@code Principal} para evitar tener DOS {@code Scanner} sobre
     * {@code System.in} a la vez, que es un error clásico (el segundo Scanner
     * "se come" datos del primero). En algunos ejemplos se ve un Scanner en cada
     * clase; pasarlo por parámetro es la forma correcta de hacer lo mismo.</p>
     */
    public GestionPoesia() {
        this.ruta = Paths.get("data", NOMBRE_ARCHIVO);
        try {
            Files.createDirectories(this.ruta.getParent());
        } catch (IOException e) {
            System.err.println("No se pudo crear la carpeta data: " + e.getMessage());
        }
    }

    /**
     * Devuelve las líneas del poema que debe contener el archivo
     * {@code poesia.txt} (texto exacto del ejercicio).
     *
     * @return lista con cada verso del poema (una línea por elemento)
     */
    private List<String> obtenerPoesia() {
        List<String> poesia = new ArrayList<>();
        // Texto de ejemplo original para practicar conteo y busqueda de palabras.
        poesia.add("Sobre la arena");
        poesia.add("camina la ballena,");
        poesia.add("busca una ola");
        poesia.add("que la serene.");
        poesia.add("Mira el horizonte");
        poesia.add("y sigue su viaje,");
        poesia.add("con la espuma");
        poesia.add("como equipaje.");
        return poesia;
    }

    /**
     * <b>Inciso a) - Crear el archivo:</b> si {@code poesia.txt} no existe, lo
     * crea y escribe el poema completo de una sola vez; si ya existe, avisa.
     *
     * <p>
     * Explicación de lo que pasa "por dentro":</p>
     * <ul>
     * <li>{@code Files.exists(ruta)} → pregunta si el archivo ya está en
     * disco.</li>
     * <li>{@code Files.write(ruta, lista, StandardCharsets.UTF_8, CREATE_NEW)}
     * → escribe la lista de líneas. La opción {@code CREATE_NEW} significa
     * "crear SOLO si no existe" (si existe, lanza excepción).</li>
     * <li>{@code StandardCharsets.UTF_8} → codificación de caracteres, para que
     * los acentos y la ñ se guarden bien.</li>
     * </ul>
     */
    public void crearArchivoPoesia() {
        try {
            if (!Files.exists(ruta)) {
                Files.write(ruta, obtenerPoesia(), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE_NEW);
                System.out.println("Archivo poesia.txt creado con el poema");
            } else {
                System.out.println("El archivo poesia.txt ya existe");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso a) - Mostrar el contenido:</b> lee todas las líneas del archivo
     * y las imprime en pantalla, una por una.
     *
     * <p>
     * {@code Files.readAllLines(ruta)} devuelve una {@link List} donde cada
     * elemento es UNA línea del archivo. Luego un bucle {@code for-each}
     * recorre la lista y la imprime. Es el patrón base de TODA lectura de
     * archivo.</p>
     */
    public void mostrarContenido() {
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
            System.out.println("--- CONTENIDO DE poesia.txt ---");
            for (String linea : lineas) {
                System.out.println(linea);
            }
            System.out.println("---------------------------------------------------");
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso b) - Línea con más palabras:</b> recorre todas las líneas,
     * cuenta cuántas palabras tiene cada una y muestra la(s) línea(s) con el
     * mayor número de palabras.
     *
     * <p>
     * El truco del método es {@code linea.trim().split("\\s+")}:</p>
     * <ul>
     * <li>{@code trim()} → quita los espacios del inicio y del final.</li>
     * <li>{@code split("\\s+")} → parte la línea por uno o más espacios en
     * blanco y devuelve un arreglo {@code String[]} con cada palabra. La
     * expresión {@code \\s+} significa "espacio, tabulador o salto".</li>
     * <li>{@code .length} sobre ese arreglo → cantidad de palabras.</li>
     * </ul>
     * <p>
     * Primero se calcula el máximo en un bucle y luego se imprime toda línea
     * que tenga esa cantidad (por si hay empate).</p>
     */
    public void mostrarLineaConMasPalabras() {
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

            // 1) Buscar la mayor cantidad de palabras entre todas las líneas
            int maxPalabras = 0;
            for (String linea : lineas) {
                int cantidad = linea.trim().split("\\s+").length;
                if (cantidad > maxPalabras) {
                    maxPalabras = cantidad;
                }
            }

            // 2) Mostrar TODAS las líneas que tengan esa cantidad (puede haber empate)
            System.out.println("La(s) linea(s) con mas palabras (" + maxPalabras + " palabras):");
            for (int i = 0; i < lineas.size(); i++) {
                if (lineas.get(i).trim().split("\\s+").length == maxPalabras) {
                    System.out.println("Linea " + (i + 1) + ": " + lineas.get(i));
                }
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso c) - Buscar una palabra:</b> pide una palabra, recorre todas
     * las líneas y muestra aquellas que contienen la palabra (sin distinguir
     * mayúsculas/minúsculas, por eso se usa {@code toLowerCase()}).
     *
     * @param leer objeto {@link Scanner} para leer la palabra desde el teclado.
     * Se pasa como parámetro para usar UN SOLO Scanner en todo el programa (ver
     * constructor).
     */
    public void buscarPalabra(Scanner leer) {
        System.out.print("Digite la palabra a buscar: ");
        String palabra = leer.nextLine().trim();

        try {
            if (!Files.exists(ruta)) {
                System.out.println("El archivo no existe. Primero usa la opcion 1");
                return;
            }
            List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
            boolean encontrada = false;

            for (int i = 0; i < lineas.size(); i++) {
                // contains() = la linea "contiene" ese texto
                // toLowerCase() en ambos lados para que "BALLENA" == "ballena"
                if (lineas.get(i).toLowerCase(Locale.ROOT).contains(palabra.toLowerCase(Locale.ROOT))) {
                    encontrada = true;
                    System.out.println("Linea " + (i + 1) + ": " + lineas.get(i));
                }
            }
            if (!encontrada) {
                System.out.println("La palabra \"" + palabra + "\" no aparece en el archivo");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }
}
