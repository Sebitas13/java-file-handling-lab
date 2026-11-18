package io.github.sebitas13.filehandling.vaccination;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <b>EJERCICIO 4 (File Handling - Vacunación):</b> representa a una persona
 * vacunada y la lista de dosis que se le aplicaron.
 *
 * <p>
 * Esta clase respeta el diagrama del ejercicio: atributos {@code cedula: int},
 * {@code nombreCompleto: String} y {@code fechaNacimiento: String}.</p>
 *
 * <p>
 * La relación con {@link Vacuna} es de <b>COMPOSICIÓN 1 a muchos</b>: en el
 * diagrama se ve un rombo RELLENO junto a Persona (el "todo") y la
 * multiplicidad {@code 1..*} junto a Vacuna (las "partes"). Eso significa que
 * cada persona tiene de 1 a muchas dosis, y que las dosis SOLO existen dentro
 * de la persona (si la persona desaparece, sus dosis desaparecen con ella).
 * Por eso {@link Persona} guarda una {@link List} de {@link Vacuna} y la
 * clase Vacuna NO necesita ninguna referencia de vuelta a Persona.</p>
 *
 * <p>
 * Diferencia con la agregación (ej. Docente→Materia): en la agregación las
 * partes pueden existir solas; en la composición no. Aquí una dosis sin
 * persona no tiene sentido, por eso es composición.</p>
 *
 * <p>
 * Como la clase se va a guardar en un archivo de objetos, debe implementar
 * {@link Serializable}. OJO: {@link ArrayList} y {@link String} YA son
 * serializables en Java, por eso la lista de dosis se puede guardar sin
 * problemas junto con la persona.</p>
 *
 * @author Sebitas13
 */
public class Persona implements Serializable {

    /**
     * Número de versión de la clase para la serialización (buena práctica).
     */
    private static final long serialVersionUID = 1L;

    /**
     * Cédula de identidad de la persona (en el diagrama es de tipo
     * {@code int}). Se usa para buscar a la persona en el inciso b).
     */
    private int cedula;

    /**
     * Nombre completo de la persona.
     */
    private String nombreCompleto;

    /**
     * Fecha de nacimiento de la persona (se guarda como texto, ej.
     * "12-05-1990").
     */
    private String fechaNacimiento;

    /**
     * Lista de dosis aplicadas (relación de COMPOSICIÓN 1 a muchos con
     * {@link Vacuna}, la multiplicidad {@code 1..*} del diagrama). Se
     * inicializa vacía en el constructor y se va llenando con
     * {@link #adicionarDosis(Vacuna)}.
     */
    private final List<Vacuna> dosis;

    /**
     * Constructor: crea la persona y su lista de dosis vacía.
     *
     * @param cedula          cédula de identidad (int, como en el diagrama)
     * @param nombreCompleto  nombre completo
     * @param fechaNacimiento fecha de nacimiento
     */
    public Persona(int cedula, String nombreCompleto, String fechaNacimiento) {
        this.cedula = cedula;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.dosis = new ArrayList<>();
    }

    /**
     * Agrega una dosis a la lista de la persona (método de la relación de
     * composición: la persona "compone" sus dosis).
     *
     * @param vacuna objeto {@link Vacuna} con la nueva dosis aplicada
     */
    public void adicionarDosis(Vacuna vacuna) {
        if (vacuna == null) {
            throw new IllegalArgumentException("La dosis no puede ser null");
        }
        this.dosis.add(vacuna);
    }

    /**
     * Muestra en consola los datos de la persona y, debajo, todas sus dosis.
     * Si no tiene dosis, avisa (para que no se vea una lista vacía).
     *
     * <p>
     * OJO con el número de dosis: el diagrama NO tiene el atributo
     * "número de dosis" en Vacuna. El número se DEDUCE de la posición en la
     * lista ({@code i + 1}): la primera que se agregó es la dosis 1, la
     * siguiente la 2, etc. Eso es una decisión de diseño típica cuando el
     * diagrama no trae ese dato.</p>
     */
    public void mostrarPersona() {
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombreCompleto);
        System.out.println("Fecha de Nacimiento: " + fechaNacimiento);
        System.out.println("Dosis aplicadas: " + dosis.size());
        if (dosis.isEmpty()) {
            System.out.println("   (sin dosis registradas)");
        } else {
            // Recorre la lista de dosis usando el metodo de la clase Vacuna.
            // El numero de la dosis es la posicion en la lista + 1.
            for (int i = 0; i < dosis.size(); i++) {
                dosis.get(i).mostrarVacuna(i + 1);
            }
        }
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Vacuna> getDosis() {
        return java.util.Collections.unmodifiableList(dosis);
    }

}
