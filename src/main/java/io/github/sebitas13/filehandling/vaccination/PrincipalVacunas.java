package io.github.sebitas13.filehandling.vaccination;

import java.util.Scanner;

/**
 * <b>EJERCICIO 4 (File Handling - Vacunación):</b> menú principal en consola para el
 * control de dosis de vacunación.
 *
 * <p>
 * Este menú sigue el patrón de los demás ejercicios, con un detalle extra:
 * después de REGISTRAR o MODIFICAR datos, se llama automáticamente a
 * {@code guardarObjetos()} para que los cambios queden guardados en el archivo
 * (el registro guarda automáticamente después de cada cambio). La opción 5
 * sirve para volver a cargar los datos que ya estaban en el archivo.</p>
 *
 * @author Sebitas13
 */
public class PrincipalVacunas {

    /**
     * Punto de entrada del programa: muestra el menú hasta que el usuario
     * salga.
     *
     * @param args argumentos de línea de comandos (no se usan)
     */
    public static void main(String[] args) {
        OperacionesVacunas obj = new OperacionesVacunas();
        Scanner leer = new Scanner(System.in);
        int opc;
        boolean continuar = true;

        do {
            System.out.println("\n===== EJERCICIO 4: REGISTRO DE VACUNACIÓN =====");
            System.out.println("1. Registrar persona con primera dosis");
            System.out.println("2. Buscar persona y adicionar la siguiente dosis");
            System.out.println("3. Mostrar personas con sus dosis");
            System.out.println("4. Guardar datos en el archivo");
            System.out.println("5. Cargar datos desde el archivo");
            System.out.println("6. Salir");
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
                case 1 -> {
                    obj.registrarPersonaPrimeraDosis(leer);
                    obj.guardarObjetos(); // se guarda automaticamente
                }
                case 2 -> {
                    obj.buscarAdicionarSiguienteDosis(leer);
                    obj.guardarObjetos(); // se guarda automaticamente
                }
                case 3 ->
                    obj.mostrarPersonasConDosis();
                case 4 ->
                    obj.guardarObjetos();
                case 5 ->
                    obj.leerObjetos();
                case 6 ->
                    continuar = false;
                default ->
                    System.out.println("Opcion no valida");
            }
        } while (continuar);
    }
}
