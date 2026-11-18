package io.github.sebitas13.filehandling.music;

import io.github.sebitas13.filehandling.FileStorage;
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
 * <b>EJERCICIO 3 (File Handling - MANEJO DE ARCHIVOS):</b> operaciones sobre el
 * archivo {@code musica.csv}.
 *
 * <p>
 * El archivo guarda un tema musical por línea con TRES campos separados por
 * punto y coma {@code ;}: {@code nombre del tema;artista;genero}. Ejemplo:
 * {@code Tema Uno;Artista A;Pop} (datos ficticios).</p>
 *
 * <p>
 * El ejercicio usa la extensión {@code .csv} (Comma Separated Values = valores
 * separados por comas). Normalmente el separador es la coma, pero aquí el
 * enunciado dice que los campos van separados por {@code " ; "}. Da igual el
 * separador: el mecanismo es el mismo, solo cambia el carácter de
 * {@code split()}.</p>
 *
 * <p>
 * Incisos del ejercicio:</p>
 * <ul>
 * <li>Inciso a) {@link #mostrarTodos()}</li>
 * <li>Inciso b) {@link #agregarDuracionTema(Scanner)} — agrega el campo 4
 * (duración) a un tema buscado por nombre.</li>
 * <li>Inciso c) {@link #mostrarPorGenero(Scanner)}</li>
 * <li>Inciso d) {@link #mostrarPorArtista(Scanner)}</li>
 * </ul>
 *
 * <p>
 * Detalle del inciso b): después de ejecutarlo, una línea puede tener 3 o 4
 * campos. Por eso los métodos de "mostrar" revisan cuántos campos tiene la
 * línea con {@code vector.length} antes de imprimir la duración.</p>
 *
 * @author Sebitas13
 */
public class GestionMusica {

    /**
     * Nombre del archivo (ruta relativa = carpeta del proyecto).
     */
    private static final String NOMBRE_ARCHIVO = "musica.csv";

    /**
     * Separador de campos que indica el enunciado: " ; " (se usa el ";"). En
     * {@code split} el ";" no es un carácter especial, se puede usar directo.
     */
    private static final String SEPARADOR = ";";

    /**
     * Ruta del archivo ya construida.
     */
    private final Path ruta;

    /**
     * Constructor: prepara la ruta del archivo.
     */
    public GestionMusica() {
        this.ruta = Paths.get("data", NOMBRE_ARCHIVO);
        try {
            Files.createDirectories(this.ruta.getParent());
        } catch (IOException e) {
            System.err.println("No se pudo crear la carpeta data: " + e.getMessage());
        }
    }

    /**
     * Devuelve una lista de temas iniciales para crear el archivo (así el CSV
     * nace con datos y se pueden probar todos los incisos de inmediato).
     *
     * @return lista de líneas con formato {@code tema;artista;genero}
     */
    private List<String> obtenerTemasIniciales() {
        List<String> temas = new ArrayList<>();
        // Datos ficticios: no representan canciones ni artistas reales.
        temas.add("Tema Uno;Artista A;Pop");
        temas.add("Tema Dos;Artista B;Balada");
        temas.add("Tema Tres;Artista C;Rock");
        temas.add("Tema Cuatro;Artista D;Balada");
        temas.add("Tema Cinco;Artista E;Pop");
        temas.add("Tema Seis;Artista F;Cumbia");
        temas.add("Tema Siete;Artista G;Pop");
        temas.add("Tema Ocho;Artista H;Rock");
        return temas;
    }

    /**
     * Crea el archivo {@code musica.csv} con los temas iniciales, solo si no
     * existe. Igual que en el ejercicio 1, se escribe la lista completa con
     * {@code Files.write} y la opción {@code CREATE_NEW}.
     */
    public void crearArchivo() {
        try {
            if (!Files.exists(ruta)) {
                Files.write(ruta, obtenerTemasIniciales(), StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE_NEW);
                System.out.println("Archivo musica.csv creado con temas iniciales");
            } else {
                System.out.println("El archivo musica.csv ya existe");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * (Ayuda extra) Adiciona un tema nuevo al archivo, con el mismo patrón de
     * "adicionar" del ejercicio 2: se arma la línea {@code tema;artista;genero}
     * y se agrega con {@code APPEND}. El tema nuevo nace con 3 campos; su
     * duración se le agrega después con {@link #agregarDuracionTema(Scanner)}.
     *
     * @param leer objeto {@link Scanner} para leer los datos del tema
     */
    public void adicionarTema(Scanner leer) {
        String nombre, artista, genero;
        try {
            if (!Files.exists(ruta)) {
                System.out.println("El archivo no existe. Primero usa la opcion 1");
                return;
            }
            System.out.print("Digite el nombre del tema: ");
            nombre = leer.nextLine();
            System.out.print("Digite el artista: ");
            artista = leer.nextLine();
            System.out.print("Digite el genero musical: ");
            genero = leer.nextLine();

            String linea = nombre + SEPARADOR + artista + SEPARADOR + genero + "\n";
            Files.write(ruta, linea.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND);
            System.out.println("Tema registrado correctamente");
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso a) - Visualizar todos los datos:</b> lee todas las líneas del
     * archivo y las muestra separando los campos para que se vea ordenado. Si
     * la línea ya tiene duración (4 campos), la muestra también.
     *
     * <p>
     * Se usa {@code vector.length} para saber cuántos campos tiene la línea: 3
     * = aún sin duración, 4 = con duración (inciso b ya aplicado).</p>
     */
    public void mostrarTodos() {
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
            System.out.println("---- TEMAS MUSICALES REGISTRADOS ----");
            for (String linea : lineas) {
                String[] v = linea.split(SEPARADOR);
                if (v.length < 3) {
                    System.out.println("Linea mal formada, se omite: " + linea);
                    continue;
                }
                System.out.print("Tema: " + v[0] + " | Artista: " + v[1]
                        + " | Genero: " + v[2]);
                if (v.length == 4) { // ya tiene duracion (inciso b)
                    System.out.print(" | Duracion: " + v[3]);
                }
                System.out.println();
            }
            System.out.println("---------------------------------------");
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso b) - Agregar duración a un tema:</b> busca la línea del tema
     * por nombre (primer campo), le agrega el campo nuevo con la duración y
     * reescribe el archivo completo con el patrón "leer → modificar →
     * reescribir" (igual que el inciso c del ejercicio 2).
     *
     * <p>
     * Si el tema ya tiene duración (4 campos), avisa y no duplica el campo. Si
     * el tema no existe, también avisa.</p>
     *
     * @param leer objeto {@link Scanner} para leer el nombre del tema y la
     * duración
     */
    public void agregarDuracionTema(Scanner leer) {
        String nombreTema, duracion;
        int modificados = 0;  // cuantos temas recibieron la duracion
        boolean encontrado = false; // el tema existe (aunque ya tenga duracion)
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

            System.out.print("Digite el nombre del tema a modificar: ");
            nombreTema = leer.nextLine();

            List<String> lineasNuevas = new ArrayList<>();
            for (String linea : lineas) {
                String[] v = linea.split(SEPARADOR);
                if (v.length < 3) {
                    System.out.println("Linea mal formada, se omite: " + linea);
                    lineasNuevas.add(linea);
                    continue;
                }
                if (v[0].equalsIgnoreCase(nombreTema)) {
                    encontrado = true;
                    if (v.length == 4) {
                        // El tema ya tiene duracion: se copia tal cual y se avisa.
                        lineasNuevas.add(linea);
                        System.out.println("El tema \"" + nombreTema + "\" ya tiene duracion registrada");
                    } else {
                        System.out.print("Digite la duracion del tema (ej. 3:45): ");
                        duracion = leer.nextLine();
                        // Se agrega el cuarto campo al final de la linea.
                        lineasNuevas.add(linea + SEPARADOR + duracion);
                        modificados++;
                    }
                } else {
                    lineasNuevas.add(linea);
                }
            }

            if (modificados > 0) {
                // Reescribir todo el archivo con la linea ya modificada.
                FileStorage.writeLinesAtomically(ruta, lineasNuevas);
                System.out.println("Duracion agregada al tema \"" + nombreTema + "\".");
            } else if (!encontrado) {
                System.out.println("El tema \"" + nombreTema + "\" no esta registrado");
            }
            // Si encontrado == true pero modificados == 0, ya se imprimio el aviso
            // de "ya tiene duracion" dentro del bucle.
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso c) - Mostrar temas de un género:</b> pide un género y muestra
     * todos los temas cuyo TERCER campo ({@code v[2]}) coincida, sin distinguir
     * mayúsculas.
     *
     * @param leer objeto {@link Scanner} para leer el género a filtrar
     */
    public void mostrarPorGenero(Scanner leer) {
        String generoBuscado;
        int sw = 0;
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

            System.out.print("Digite el genero musical: ");
            generoBuscado = leer.nextLine();

            System.out.println("---- TEMAS DEL GENERO: " + generoBuscado.toUpperCase(Locale.ROOT) + " ----");
            for (String linea : lineas) {
                String[] v = linea.split(SEPARADOR);
                if (v.length < 3) {
                    System.out.println("Linea mal formada, se omite: " + linea);
                    continue;
                }
                if (v[2].equalsIgnoreCase(generoBuscado)) {
                    sw = 1;
                    System.out.print("Tema: " + v[0] + " | Artista: " + v[1]);
                    if (v.length == 4) {
                        System.out.print(" | Duracion: " + v[3]);
                    }
                    System.out.println();
                }
            }
            if (sw == 0) {
                System.out.println("No hay temas del genero \"" + generoBuscado + "\".");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }

    /**
     * <b>Inciso d) - Mostrar temas de un artista:</b> igual que el inciso c,
     * pero comparando el SEGUNDO campo ({@code v[1]}), que es el artista.
     *
     * @param leer objeto {@link Scanner} para leer el artista a filtrar
     */
    public void mostrarPorArtista(Scanner leer) {
        String artistaBuscado;
        int sw = 0;
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

            System.out.print("Digite el artista: ");
            artistaBuscado = leer.nextLine();

            System.out.println("---- TEMAS DEL ARTISTA: " + artistaBuscado.toUpperCase(Locale.ROOT) + " ----");
            for (String linea : lineas) {
                String[] v = linea.split(SEPARADOR);
                if (v.length < 3) {
                    System.out.println("Linea mal formada, se omite: " + linea);
                    continue;
                }
                if (v[1].equalsIgnoreCase(artistaBuscado)) {
                    sw = 1;
                    System.out.print("Tema: " + v[0] + " | Genero: " + v[2]);
                    if (v.length == 4) {
                        System.out.print(" | Duracion: " + v[3]);
                    }
                    System.out.println();
                }
            }
            if (sw == 0) {
                System.out.println("No hay temas del artista \"" + artistaBuscado + "\".");
            }
        } catch (IOException e) { System.err.println("Error de E/S: " + e.getMessage()); }
    }
}
