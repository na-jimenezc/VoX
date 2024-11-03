# 12. Notificar referenciación

## Flujo principal:
    1. Un usuario menciona a otro usuario en una publicación o comentario.
    2. El sistema detecta la mención y envía una notificación al usuario mencionado.
    
## Flujos alternos:
    1. Si el usuario referenciado tiene las notificaciones desactivadas, el sistema no envía la notificación.
    2. Si el usuario intenta referenciar un nombre inexistente, el sistema muestra un mensaje de error en el flujo original (CU008).
