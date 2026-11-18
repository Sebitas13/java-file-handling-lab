package io.github.sebitas13.filehandling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileStorageTest {

    @TempDir
    Path tempDir;

    @Test
    void writesLinesAtomically() throws IOException {
        Path target = tempDir.resolve("data").resolve("test.txt");
        FileStorage.writeLinesAtomically(target, List.of("linea 1", "linea 2"));
        assertEquals(List.of("linea 1", "linea 2"), Files.readAllLines(target));
    }

    @Test
    void replacesExistingContent() throws IOException {
        Path target = tempDir.resolve("test.txt");
        Files.writeString(target, "viejo");
        FileStorage.writeLinesAtomically(target, List.of("nuevo"));
        assertEquals(List.of("nuevo"), Files.readAllLines(target));
    }
}
