package io.github.sebitas13.filehandling.friends;

import java.util.Scanner;

/**
 * <b>EJERCICIO 2 (File Handling):</b> menú principal en consola para el archivo
 * {@code amigos.txt}.
 *
 * <p>
 * Mismo patrón de menú que en todos los ejercicios: {@code do-while} +
 * {@code switch} + variable {@code continuar}. Solo llama a los métodos de
 * {@link GestionAmigos}.</p>
 *
 * @author Sebitas13
 */
public class PrincipalAmigos {

    /**
     * Punto de entrada del programa: muestra el menú hasta que el usuario salga
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        GestionAmigos obj = new GestionAmigos();
        Scanner leer = new Scanner(System.in);
        int opc;
        boolean continuar = true;

        do {
            System.out.println("\n===== EJERCICIO 2: AMIGOS.TXT =====");
            System.out.println("1. Crear archivo amigos.txt");
            System.out.println("2. Adicionar amigos");
            System.out.println("3. Buscar un amigo por nombre");
            System.out.println("4. Modificar el numero de celular de un amigo");
            System.out.println("5. Salir");
            System.out.print("Digite una opcion: ");
            try {
                opc = leer.nextInt();
            } catch (java.util.InputMismatchException e) {
                System.out.println("Debes ingresar un numero valido.");
                leer.nextLine();
                continue;
            }
            leer.nextLine(); // consumir el Enter que deja nextInt() (ver PrincipalPoesia)

            switch (opc) {
                case 1 ->
                    obj.crearArchivo();
                case 2 ->
                    obj.adicionarAmigo(leer);
                case 3 ->
                    obj.buscarAmigo(leer);
                case 4 ->
                    obj.modificarCelular(leer);
                case 5 ->
                    continuar = false;
                default ->
                    System.out.println("Opcion no valida");
            }
        } while (continuar);
    }
}
