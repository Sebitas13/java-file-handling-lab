# Java File Handling Lab

[![Java CI](https://github.com/Sebitas13/java-file-handling-lab/actions/workflows/ci.yml/badge.svg)](https://github.com/Sebitas13/java-file-handling-lab/actions/workflows/ci.yml)

Small console project to practice **text files, CSV parsing, object serialization, composition and defensive file I/O** in Java.

> Educational project. The sample data is fictional and the repository does not contain real personal data or credentials. The app is not intended to store real personal data.

## What it does

| Module | What you can practice |
|---|---|
| **Poetry** | Create, read, count words and search inside a UTF-8 text file. |
| **Friends** | Append records, search by name and update a field using the read → modify → rewrite pattern. |
| **Music** | Work with a CSV file, add a duration field and filter by genre or artist. |
| **Vaccination** | Model a composition relationship and persist `Persona` / `Vacuna` objects with Java serialization. |

## Stack

- Java 21 (LTS)
- Maven
- JUnit 5
- No external runtime dependencies

## Structure

```text
src/main/java/io/github/sebitas13/filehandling/
├── App.java
├── FileStorage.java
├── poetry/        # text file
├── friends/       # text file with CRUD
├── music/         # CSV
└── vaccination/   # composition + object serialization
```

## Run

```bash
# compile and run tests
mvn test

# show the exercise index
mvn -q exec:java

# run one exercise
mvn -q exec:java -Dexec.mainClass=io.github.sebitas13.filehandling.poetry.PrincipalPoesia
mvn -q exec:java -Dexec.mainClass=io.github.sebitas13.filehandling.friends.PrincipalAmigos
mvn -q exec:java -Dexec.mainClass=io.github.sebitas13.filehandling.music.PrincipalMusica
mvn -q exec:java -Dexec.mainClass=io.github.sebitas13.filehandling.vaccination.PrincipalVacunas
```

You can also open the project in any IDE and run the `Principal...` classes directly.

Data files are created at runtime inside `data/` (ignored by Git):

```text
data/poesia.txt
data/amigos.txt
data/musica.csv
data/vacunas.txt
```

## Example

```text
===== EJERCICIO 1: POESIA.TXT =====
1. Crear archivo poesia.txt
2. Mostrar el contenido del archivo
3. Mostrar la linea con mas palabras
4. Buscar una palabra en el archivo
5. Salir
```

The console menus and Javadoc are in Spanish; this README is in English so the project can be reviewed by an international audience.

## Design notes

- **One `Scanner`** is passed to the managers instead of creating several over `System.in`.
- **`nextInt()` + `nextLine()`** is handled explicitly to avoid the classic leftover-newline bug.
- **Append vs. overwrite** is intentional: friends and songs are appended; updates rewrite a sanitized list.
- **Atomic writes:** text updates use a temporary file + `ATOMIC_MOVE` when supported; object saves also write a temporary file and move it into place.
- **Validation:** malformed CSV/text lines are skipped or preserved unchanged with a message instead of crashing.
- **Deserialization filter:** `ObjectInputFilter` whitelists the expected classes and bounds depth, references and array length when reading `vacunas.txt`.

## Security and privacy

- No API keys, tokens, passwords or `.env` files.
- `data/` is ignored; the repository only ships code and fictional examples.
- The app is not intended for real personal data.
- To report a vulnerability, read [SECURITY.md](SECURITY.md). Do not open a public issue for security reports.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) and [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md).

## License

MIT. See [LICENSE](LICENSE).

## Author

Sebastian Salazar — [@Sebitas13](https://github.com/Sebitas13)
