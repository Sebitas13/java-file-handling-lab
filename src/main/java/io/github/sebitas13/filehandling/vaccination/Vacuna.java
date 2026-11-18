package io.github.sebitas13.filehandling.vaccination;

import java.io.Serializable;

/**
 * <b>EJERCICIO 4 (File Handling - Vacunación):</b> representa UNA DOSIS de vacuna que se
 * aplica a una persona.
 *
 * <p>
 * Esta clase respeta el diagrama del ejercicio: atributos {@code marca: String},
 * {@code vencimiento: String} y
 * {@code fAplicacion: String} (fecha de aplicación). No tiene "número de
 * dosis": ese número se deduce de la posición de la dosis dentro de la lista de
 * la persona (ver {@link Persona#mostrarPersona()}).</p>
 *
 * <p>
 * En el diagrama, la relación entre Persona y Vacuna es de <b>COMPOSICIÓN 1 a
 * muchos</b> (rombo relleno junto a Persona, multiplicidad {@code 1..*} junto a
 * Vacuna): una persona tiene de 1 a muchas dosis, y cada dosis pertenece a UNA
 * sola persona (por eso junto a Persona aparece {@code 1..1}). Esta clase es el
 * "muchos" de esa relación: cada objeto {@code Vacuna} es un elemento de la
 * lista de dosis de {@link Persona}.</p>
 *
 * <p>
 * ¿Por qué {@code implements Serializable}? Porque el ejercicio pide
 * <b>persistir los datos en un archivo</b>. Para guardar OBJETOS en un archivo,
 * Java exige que la clase (y todo lo que contenga) implemente la interfaz
 * {@link Serializable}. Esa interfaz es solo una "marca": no tiene métodos que
 * implementar, solo le dice a Java "esta clase se puede convertir en bytes y
 * volver a convertirse en objeto". Ese proceso se llama <b>serialización</b>
 * (ver {@link OperacionesVacunas#guardarObjetos()}).</p>
 *
 * <p>
 * La constante {@code serialVersionUID} es un número de versión de la clase: si
 * algún día cambias los atributos y quieres leer archivos viejos, Java compara
 * este número para saber si la clase sigue siendo compatible.</p>
 *
 * @author Sebitas13
 */
public class Vacuna implements Serializable {

    /**
     * Número de versión de la clase para la serialización (buena práctica).
     */
    private static final long serialVersionUID = 1L;

    /**
     * Marca de la vacuna aplicada (ej. "Vacuna A", "Vacuna B",
     * "Vacuna C"). Es el atributo {@code marca} del diagrama.
     */
    private String marca;

    /**
     * Fecha de vencimiento de la dosis (se guarda como texto, ej.
     * "30-12-2026"). Es el atributo {@code vencimiento} del diagrama.
     */
    private String vencimiento;

    /**
     * Fecha en que se aplicó la dosis (se guarda como texto, ej. "15-08-2026").
     * Es el atributo {@code fAplicacion} del diagrama.
     */
    private String fAplicacion;

    /**
     * Constructor que inicializa los tres datos de la dosis, en el mismo orden
     * que aparecen en el diagrama.
     *
     * @param marca marca de la vacuna
     * @param vencimiento fecha de vencimiento
     * @param fAplicacion fecha de aplicación
     */
    public Vacuna(String marca, String vencimiento, String fAplicacion) {
        this.marca = marca;
        this.vencimiento = vencimiento;
        this.fAplicacion = fAplicacion;
    }

    /**
     * Muestra en consola los datos de esta dosis. Se imprime con sangría porque
     * normalmente se muestra DENTRO de la ficha de una persona (ver
     * {@link Persona#mostrarPersona()}).
     *
     * <p>
     * El número de la dosis NO es un atributo de esta clase (no está en el
     * diagrama): lo recibe por parámetro desde {@link Persona}, que lo calcula
     * como la posición de la dosis en su lista + 1.</p>
     *
     * @param nroDosis número de la dosis (1 = primera, 2 = segunda, etc.)
     */
    public void mostrarVacuna(int nroDosis) {
        System.out.println("   Dosis Nro " + nroDosis + ": " + marca
                + " | Vencimiento: " + vencimiento
                + " | Fecha de aplicacion: " + fAplicacion);
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getFAplicacion() {
        return fAplicacion;
    }

    public void setFAplicacion(String fAplicacion) {
        this.fAplicacion = fAplicacion;
    }
}
