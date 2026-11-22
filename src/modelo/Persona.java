package modelo;

/** Datos de un usuario de la red. El usuario se guarda normalizado (minúsculas). */
public class Persona {
    public String usuario;
    public String nombre;

    public Persona(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + " (" + usuario.toUpperCase() + ")";
    }
}
