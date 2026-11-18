package io.github.sebitas13.filehandling.music;

import java.util.Scanner;

/**
 * <b>EJERCICIO 3 (File Handling):</b> menú principal en consola para el archivo
 * {@code musica.csv}. Mismo patrón de menú que los demás ejercicios.
 *
 * @author Sebitas13
 */
public class PrincipalMusica {

    /**
     * Punto de entrada del programa: muestra el menú hasta que el usuario
     * salga.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        GestionMusica obj = new GestionMusica();
        Scanner leer = new Scanner(System.in);
        int opc;
        boolean continuar = true;

        do {
            System.out.println("\n===== EJERCICIO 3: MUSICA.CSV =====");
            System.out.println("1. Crear archivo musica.csv (temas iniciales)");
            System.out.println("2. Adicionar un tema musical");
            System.out.println("3. Visualizar todos los datos");
            System.out.println("4. Agregar duracion a un tema (buscar por nombre)");
            System.out.println("5. Mostrar temas de un genero musical");
            System.out.println("6. Mostrar temas de un artista");
            System.out.println("7. Salir");
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
                    obj.crearArchivo();
                case 2 ->
                    obj.adicionarTema(leer);
                case 3 ->
                    obj.mostrarTodos();
                case 4 ->
                    obj.agregarDuracionTema(leer);
                case 5 ->
                    obj.mostrarPorGenero(leer);
                case 6 ->
                    obj.mostrarPorArtista(leer);
                case 7 ->
                    continuar = false;
                default ->
                    System.out.println("Opcion no valida.");
            }
        } while (continuar);
    }
}
