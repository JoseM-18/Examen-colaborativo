# Proyecto de Examen Colaborativo con Sockets en Java

Este proyecto implementa un sistema de examen colaborativo en Java utilizando sockets. Permite a un servidor local cargar exámenes y permitir que los clientes se conecten para responder preguntas en tiempo real.

## Características

- Carga de exámenes por parte del servidor.
- Conexión de múltiples clientes al servidor.
- Examen en tiempo real y colaborativo.
- Resultados y respuestas guardados en el servidor.

## Requisitos

- Java Development Kit (JDK) 8 o superior.
- IDE Java (Eclipse, IntelliJ, etc.) o compilador Java para línea de comandos.

## Uso

1. Clona este repositorio en tu máquina local.
2. Abre el proyecto en tu IDE o compila los archivos Java utilizando el compilador de línea de comandos.
3. Ejecuta primero el `Servidor` para cargar los exámenes y esperar conexiones.
4. Ejecuta múltiples instancias del `Cliente` en diferentes terminales o en diferentes máquinas en la misma red.

### Servidor

- Ejecuta la clase `ServidorPrincipal.java` en el paquete `servidor`.
- El servidor cargará los exámenes disponibles desde los archivos de texto proporcionados.
- Esperará conexiones entrantes de los clientes.

### Cliente

- Ejecuta la clase `ClientePrincipal.java` en el paquete `cliente`.
- Sigue las instrucciones en pantalla para conectarte al servidor y seleccionar un examen.
- Responde las preguntas de manera colaborativa con otros clientes conectados.

## Estructura del Proyecto

- `servidor`: Contiene las clases relacionadas con el servidor.
- `cliente`: Contiene las clases relacionadas con el cliente.
- `exámenes`: Carpeta que contiene archivos de texto con los exámenes en un formato específico.

## Contribuciones

Las contribuciones son bienvenidas. Si encuentras algún error, tienes ideas para mejoras o deseas agregar nuevas características, no dudes en crear un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT. Puedes consultar el archivo [LICENSE](LICENSE) para más detalles.

---

