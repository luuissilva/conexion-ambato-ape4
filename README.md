# Conexión Ambato – APE 4 Fase 2

Red social dinámica con multilista (lista de vértices + lista de aristas por vértice), sin HashMap, HashSet, ArrayList ni LinkedList.

## Estructura
- `src/enums/TipoRelacion.java` – AMISTAD, SEGUIR
- `src/modelo/` – Persona, Vertice, Arista
- `src/grafo/RedSocial.java` – operaciones y validaciones
- `src/app/Main.java` – secuencia de comprobación (Tabla 3)
- `src/app/Menu.java` – menú interactivo
- `src/pruebas/PruebasRedSocial.java` – pruebas automatizadas

## Compilar y ejecutar
```
javac -encoding UTF-8 -d out src/enums/*.java src/modelo/*.java src/grafo/*.java src/app/*.java src/pruebas/*.java
java -cp out app.Main
java -cp out pruebas.PruebasRedSocial
java -cp out app.Menu
```
