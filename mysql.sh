    #!/bin/sh

    # ejecute `mysql -p` dentro del contenedor `tareas-db`
    docker exec -it proyecto-db \
      mysql -p