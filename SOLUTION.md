# 2048

## Anmerkungen zum Projekt

Die Testabdeckung für `Board` und `GameImpl` ist 100%. Die Branch-Abdeckung ist bei `GameImpl` nur bei 85%, da hier ein Case-Statement für die Eingabe der Richtung nicht mit default-Fall ausgeführt werden kann. Der eingabe-Parameter ist vom Typ `direction` und kann somit nur die Werte aus dem Enum annehmen.

```java 
switch (direction) {
    case up: {
        board.moveUp();
        break;
    }
    case down: {
        board.moveDown();
        break;
    }
    case left: {
        board.moveLeft();

        break;
    }
    case right: {
        board.moveRight();
        break;
    }
}
```


## CI/CD Setup

Um den docker container für den Runner zu starten:
```bash
docker network create runner-net

docker run -d \
  --name github-runner \
  --network runner-net \
  -p 8081:8080 \
  tomcat:9-jdk17-openjdk-slim
  ```

