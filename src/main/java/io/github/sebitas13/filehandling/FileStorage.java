package io.github.sebitas13.filehandling;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * Small file helpers shared by the exercises.
 *
 * @author Sebitas13
 */
public final class FileStorage {

    private FileStorage() {
    }

    /**
     * Writes all lines to a temporary file and replaces the target atomically
     * when the filesystem supports it.
     *
     * @param target destination file
     * @param lines lines to write
     * @throws IOException if the write or move fails
     */
    public static void writeLinesAtomically(Path target, List<String> lines) throws IOException {
        Path absolute = target.toAbsolutePath();
        Path parent = absolute.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = Files.createTempFile(parent, target.getFileName().toString(), ".tmp");
        try {
            Files.write(temp, lines, StandardCharsets.UTF_8);
            try {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp);
        }
    }
}
