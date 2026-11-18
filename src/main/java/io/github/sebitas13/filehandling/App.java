package io.github.sebitas13.filehandling;

/**
 * Entry point and index for the Java File Handling Lab.
 *
 * <p>Each exercise has its own console entry point. Run one of them with
 * Maven or from an IDE.</p>
 *
 * @author Sebitas13
 */
public final class App {

    private App() {
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  JAVA FILE HANDLING LAB");
        System.out.println("==============================================");
        System.out.println("  Available exercises:");
        System.out.println("  - poetry     -> io.github.sebitas13.filehandling.poetry.PrincipalPoesia");
        System.out.println("  - friends    -> io.github.sebitas13.filehandling.friends.PrincipalAmigos");
        System.out.println("  - music      -> io.github.sebitas13.filehandling.music.PrincipalMusica");
        System.out.println("  - vaccination -> io.github.sebitas13.filehandling.vaccination.PrincipalVacunas");
        System.out.println();
        System.out.println("  Example:");
        System.out.println("  mvn exec:java -Dexec.mainClass=io.github.sebitas13.filehandling.poetry.PrincipalPoesia");
        System.out.println("==============================================");
    }
}
