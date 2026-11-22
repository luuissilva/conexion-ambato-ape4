package modelo;

/** Nodo de la lista principal: una persona con su propia lista de aristas. */
public class Vertice {
    public Persona persona;
    public Arista aristas; // cabeza de la lista de adyacencia
    public Vertice sig;    // siguiente vértice de la red

    public Vertice(Persona persona) {
        this.persona = persona;
    }
}
