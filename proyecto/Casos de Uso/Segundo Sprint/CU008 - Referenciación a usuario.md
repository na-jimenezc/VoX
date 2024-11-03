# 8. Referenciación de Usuario

## Flujo principal:
    1. El usuario selecciona la opción para escribir una publicación o comentario dentro de una publicación.
    2. Escribe el texto e incluye el nombre de otro usuario en el formato @usuario.
    3. Elige "Publicar en modo normal".
    4. El sistema muestra la publicación en el feed con la identidad del usuario y envía una notificación al usuario referenciado.

## Flujos alternos:
    1. Si el usuario elige "Publicar en modo anónimo", el sistema oculta su identidad en el feed, pero aún envía una notificación al usuario referenciado.
    2. Si el usuario intenta referenciar a un nombre de usuario inexistente, el sistema muestra un mensaje de error.