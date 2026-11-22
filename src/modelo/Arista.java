package modelo;

import enums.TipoRelacion;

/** Nodo de la lista de adyacencia: una relación hacia un vértice destino. */
public class Arista {
    public Vertice destino;
    public TipoRelacion tipo;
    public Arista sig;
}
