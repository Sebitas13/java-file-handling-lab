package io.github.sebitas13.filehandling.poetry;

import java.util.Scanner;

/**
 * <b>EJERCICIO 1 (File Handling):</b> menú principal en consola para el archivo
 * {@code poesia.txt}.
 *
 * <p>
 * Esta clase solo se encarga de MOSTRAR el menú y llamar al método correcto de
 * {@link GestionPoesia} según la opción elegida. El patrón es el mismo en todos
 * los ejercicios:</p>
 * <ul>
 * <li>un bucle {@code do-while} para que el menú se repita hasta salir;</li>
 * <li>un {@code switch} que llama al método según el número elegido;</li>
 * <li>una variable {@code continuar} que se pone en {@code false} al
 * salir.</li>
 * </ul>
 *
 * <p>
 * TRUCO IMPORTANTE: después de {@code leer.nextInt()} siempre se ejecuta
 * {@code leer.nextLine()}. ¿Por qué? {@code nextInt()} solo lee el número y
 * DEJA el salto de línea pendiente en el teclado; si no lo "consumimos", el
 * siguiente {@code nextLine()} se comería un Enter vacío. Es el error clásico
 * de los menús en consola y suele caer en exámenes.</p>
 *
 * @author Sebitas13
 */
public class PrincipalPoesia {

    /**
     * Punto de entrada del programa: muestra el menú en un bucle hasta que el
     * usuario elija salir
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        GestionPoesia obj = new GestionPoesia();
        Scanner leer = new Scanner(System.in);
        int opc;
        boolean continuar = true;

        do {
            System.out.println("\n===== EJERCICIO 1: POESIA.TXT =====");
            System.out.println("1. Crear archivo poesia.txt");
            System.out.println("2. Mostrar el contenido del archivo");
            System.out.println("3. Mostrar la linea con mas palabras");
            System.out.println("4. Buscar una palabra en el archivo");
            System.out.println("5. Salir");
            System.out.print("Digite una opcion: ");
            try {
                opc = leer.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Debes ingresar un numero valido.");
                leer.nextLine();
                continue;
            }
            leer.nextLine(); // consumir el Enter que deja nextInt()

            switch (opc) {
                case 1 ->
                    obj.crearArchivoPoesia();
                case 2 ->
                    obj.mostrarContenido();
                case 3 ->
                    obj.mostrarLineaConMasPalabras();
                case 4 ->
                    obj.buscarPalabra(leer);
                case 5 ->
                    continuar = false;
                default ->
                    System.out.println("Opcion no valida");
            }
        } while (continuar);
    }
}
