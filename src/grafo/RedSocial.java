package grafo;

import enums.TipoRelacion;
import modelo.Arista;
import modelo.Persona;
import modelo.Vertice;

/**
 * Red social "Conexión Ambato" representada con una multilista:
 * lista enlazada de vértices y, en cada vértice, lista enlazada de aristas.
 * No usa HashMap, HashSet, ArrayList ni LinkedList.
 */
public class RedSocial {

    private Vertice cabeza;

    // ===================== Auxiliares =====================

    /** Busca un vértice por su identificador normalizado. O(V) */
    private Vertice buscar(String usuario) {
        if (usuario == null) return null;
        String clave = usuario.trim().toLowerCase();
        for (Vertice v = cabeza; v != null; v = v.sig)
            if (v.persona.usuario.equals(clave)) return v;
        return null;
    }

    /** Inserta una arista al comienzo de la lista del origen. O(1) */
    private void insertar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista a = new Arista();
        a.destino = destino;
        a.tipo = tipo;
        a.sig = origen.aristas;
        origen.aristas = a;
    }

    /** ¿Existe una arista origen→destino del tipo indicado? O(grado) */
    private boolean existe(Vertice origen, Vertice destino, TipoRelacion tipo) {
        for (Arista a = origen.aristas; a != null; a = a.sig)
            if (a.destino == destino && a.tipo == tipo) return true;
        return false;
    }

    /** Quita la arista origen→destino del tipo indicado. O(grado) */
    private boolean quitar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista anterior = null, actual = origen.aristas;
        while (actual != null) {
            if (actual.destino == destino && actual.tipo == tipo) {
                if (anterior == null) origen.aristas = actual.sig;
                else anterior.sig = actual.sig;
                return true;
            }
            anterior = actual;
            actual = actual.sig;
        }
        return false;
    }

    /** Retira TODA arista (de cualquier tipo) que vaya de origen hacia destino. O(grado) */
    private void quitarHacia(Vertice origen, Vertice destino) {
        // Saltar las aristas iniciales que apuntan al destino
        while (origen.aristas != null && origen.aristas.destino == destino)
            origen.aristas = origen.aristas.sig;
        // Revisar el resto de la lista
        for (Arista a = origen.aristas; a != null && a.sig != null; ) {
            if (a.sig.destino == destino) a.sig = a.sig.sig;
            else a = a.sig;
        }
    }

    /** Valida el formato del identificador: no vacío, sin espacios ni comas. */
    private boolean usuarioValido(String usuario) {
        if (usuario == null) return false;
        String u = usuario.trim();
        return !u.isEmpty() && u.indexOf(' ') < 0 && u.indexOf(',') < 0;
    }

    // ===================== Personas =====================

    /** Registra una persona insertándola al inicio de la lista de vértices. */
    public boolean registrarPersona(String usuario, String nombre) {
        if (!usuarioValido(usuario) || nombre == null || nombre.trim().isEmpty()) return false;
        if (buscar(usuario) != null) return false; // duplicado
        Vertice nuevo = new Vertice(new Persona(usuario.trim().toLowerCase(), nombre.trim()));
        nuevo.sig = cabeza;
        cabeza = nuevo;
        return true;
    }

    /** Elimina la persona y, antes, todas las aristas que llegan a ella. */
    public boolean eliminarPersona(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return false;
        for (Vertice w = cabeza; w != null; w = w.sig)
            quitarHacia(w, objetivo);
        Vertice anterior = null, actual = cabeza;
        while (actual != objetivo) {
            anterior = actual;
            actual = actual.sig;
        }
        if (anterior == null) cabeza = objetivo.sig;
        else anterior.sig = objetivo.sig;
        return true;
    }

    // ===================== Relaciones =====================

    /** La amistad es recíproca: se crean dos aristas AMISTAD. */
    public boolean agregarAmistad(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb ||
            existe(va, vb, TipoRelacion.AMISTAD)) return false;
        insertar(va, vb, TipoRelacion.AMISTAD);
        insertar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    /** Seguir es dirigido: se crea una sola arista SEGUIR (a → b). */
    public boolean seguir(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb ||
            existe(va, vb, TipoRelacion.SEGUIR)) return false;
        insertar(va, vb, TipoRelacion.SEGUIR);
        return true;
    }

    /** Quita las dos aristas AMISTAD. */
    public boolean eliminarAmistad(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb) return false;
        if (!quitar(va, vb, TipoRelacion.AMISTAD)) return false;
        quitar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    /** Quita solo la arista SEGUIR a → b. */
    public boolean dejarDeSeguir(String a, String b) {
        Vertice va = buscar(a), vb = buscar(b);
        if (va == null || vb == null || va == vb) return false;
        return quitar(va, vb, TipoRelacion.SEGUIR);
    }

    // ===================== Consultas =====================

    /** Nombres de los destinos de las aristas del tipo dado, o null si no existe. */
    private String listarPorTipo(String usuario, TipoRelacion tipo) {
        Vertice v = buscar(usuario);
        if (v == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Arista a = v.aristas; a != null; a = a.sig)
            if (a.tipo == tipo) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(a.destino.persona.nombre);
            }
        return sb.length() == 0 ? "(ninguno)" : sb.toString();
    }

    public String listarAmigos(String usuario) {
        return listarPorTipo(usuario, TipoRelacion.AMISTAD);
    }

    public String listarSeguidos(String usuario) {
        return listarPorTipo(usuario, TipoRelacion.SEGUIR);
    }

    /** Seguidores: hay que revisar las aristas de TODA la red. O(V + A) */
    public String listarSeguidores(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Vertice w = cabeza; w != null; w = w.sig)
            for (Arista a = w.aristas; a != null; a = a.sig)
                if (a.destino == objetivo && a.tipo == TipoRelacion.SEGUIR) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(w.persona.nombre);
                }
        return sb.length() == 0 ? "(ninguno)" : sb.toString();
    }

    /** Número de amigos, o -1 si el usuario no existe. */
    public int contarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return -1;
        int n = 0;
        for (Arista a = v.aristas; a != null; a = a.sig)
            if (a.tipo == TipoRelacion.AMISTAD) n++;
        return n;
    }

    public int contarPersonas() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.sig) n++;
        return n;
    }

    /** Número de amistades (cada una se guarda como dos aristas). */
    public int contarAmistades() {
        int aristas = 0;
        for (Vertice v = cabeza; v != null; v = v.sig)
            for (Arista a = v.aristas; a != null; a = a.sig)
                if (a.tipo == TipoRelacion.AMISTAD) aristas++;
        return aristas / 2;
    }

    public int contarSeguimientos() {
        int n = 0;
        for (Vertice v = cabeza; v != null; v = v.sig)
            for (Arista a = v.aristas; a != null; a = a.sig)
                if (a.tipo == TipoRelacion.SEGUIR) n++;
        return n;
    }

    /** Muestra toda la multilista: vértices y sus aristas. O(V + A) */
    public String mostrarRed() {
        StringBuilder sb = new StringBuilder();
        if (cabeza == null) return "(red vacía)\n";
        for (Vertice v = cabeza; v != null; v = v.sig) {
            sb.append(String.format("%-16s", v.persona.toString())).append(" ->");
            if (v.aristas == null) sb.append(" null");
            for (Arista a = v.aristas; a != null; a = a.sig)
                sb.append(" [").append(a.tipo).append(":").append(a.destino.persona.nombre).append("]");
            sb.append('\n');
        }
        return sb.toString();
    }
}
