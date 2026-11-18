# Notas técnicas

Este proyecto practica cuatro ideas base de archivos en Java.

## 1. Archivos de texto con `java.nio.file`

- `Files.exists(path)` comprueba si existe.
- `Files.createFile(path)` crea solo si no existe.
- `Files.write(path, lines, UTF_8)` escribe una lista de líneas.
- `Files.readAllLines(path, UTF_8)` devuelve una línea por elemento.
- `StandardOpenOption.APPEND` agrega al final; sin esa opción se reemplaza el contenido.

Para modificar una línea: leer todo, modificar la lista en memoria y reescribir.

## 2. Separar campos

En los ejercicios de amigos y música, cada línea tiene campos separados por `-` o `;`.
Siempre hay que validar `vector.length` antes de acceder a `vector[2]` o `vector[3]`.
Una línea mal formada debe omitirse con un mensaje, no romper el programa.

## 3. Serialización de objetos

- La clase debe implementar `Serializable`.
- `serialVersionUID` documenta la versión del formato.
- `ObjectOutputStream.writeObject` guarda el grafo completo.
- `ObjectInputStream.readObject` lo reconstruye.
- La deserialización de archivos que pueden ser modificados por terceros debe usar un
  `ObjectInputFilter` con una lista blanca de clases.

## 4. Errores y escritura segura

- Un `catch` vacío oculta fallos de disco o permisos. Siempre informar al usuario.
- Para reemplazos completos, escribir en un archivo temporal y moverlo con
  `ATOMIC_MOVE` reduce el riesgo de dejar el archivo truncado.
- Los datos generados en tiempo de ejecución van a `data/`, que está ignorado por Git.
