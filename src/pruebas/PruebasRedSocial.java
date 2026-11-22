package pruebas;

import grafo.RedSocial;

/** Pruebas automatizadas sin librerías externas: cada caso compara esperado vs. obtenido. */
public class PruebasRedSocial {

    private static int ok = 0, fallos = 0;

    private static void verificar(String nombre, Object esperado, Object obtenido) {
        boolean pasa = (esperado == null) ? obtenido == null : esperado.equals(obtenido);
        if (pasa) ok++; else fallos++;
        System.out.printf("[%s] %-48s esperado=%s obtenido=%s%n",
                pasa ? "OK  " : "FALLA", nombre, esperado, obtenido);
    }

    private static RedSocial redInicial() {
        RedSocial r = new RedSocial();
        r.registrarPersona("A01", "Sofía");  r.registrarPersona("A02", "Mateo");
        r.registrarPersona("A03", "Valeria"); r.registrarPersona("A04", "Andrés");
        r.registrarPersona("A05", "Camila"); r.registrarPersona("A06", "Diego");
        r.agregarAmistad("A01", "A02"); r.agregarAmistad("A02", "A03");
        r.agregarAmistad("A03", "A04"); r.agregarAmistad("A04", "A01");
        r.agregarAmistad("A04", "A05");
        return r;
    }

    public static void main(String[] args) {
        RedSocial r = redInicial();
        verificar("P1 usuarios registrados", 6, r.contarPersonas());
        verificar("P1 amistades", 5, r.contarAmistades());
        String grados = "";
        for (String u : new String[]{"A01","A02","A03","A04","A05","A06"}) grados += r.contarAmigos(u);
        verificar("P1 grados A01..A06", "222310", grados);

        verificar("P2 seguir(A02,A01)", true, r.seguir("A02", "A01"));
        verificar("P3 seguir(A06,A01)", true, r.seguir("A06", "A01"));
        verificar("P2-3 listarSeguidores(A01)", "Diego, Mateo", r.listarSeguidores("A01"));
        verificar("seguir duplicado", false, r.seguir("A02", "A01"));
        verificar("seguir no crea amistad", 0, r.contarAmigos("A06"));
        verificar("listarSeguidos(A06)", "Sofía", r.listarSeguidos("A06"));

        verificar("P4 contarAmigos(A04)", 3, r.contarAmigos("A04"));
        verificar("P4 listarAmigos(A04)", "Camila, Sofía, Valeria", r.listarAmigos("A04"));
        verificar("búsqueda sin distinguir mayúsculas", 3, r.contarAmigos("a04"));

        verificar("P5 eliminarAmistad(A04,A05)", true, r.eliminarAmistad("A04", "A05"));
        verificar("P5 contarAmigos(A04)", 2, r.contarAmigos("A04"));
        verificar("P5 contarAmigos(A05)", 0, r.contarAmigos("A05"));
        verificar("eliminarAmistad inexistente", false, r.eliminarAmistad("A04", "A05"));

        verificar("dejarDeSeguir(A02,A01)", true, r.dejarDeSeguir("A02", "A01"));
        verificar("dejarDeSeguir no toca amistad", 2, r.contarAmigos("A02"));
        r.seguir("A02", "A01");

        verificar("P6 eliminarPersona(A01)", true, r.eliminarPersona("A01"));
        verificar("P6 usuarios restantes", 5, r.contarPersonas());
        verificar("P6 A02 sin Sofía", "Valeria", r.listarAmigos("A02"));
        verificar("P6 A04 sin Sofía", "Valeria", r.listarAmigos("A04"));
        verificar("P6 Diego ya no sigue a nadie", "(ninguno)", r.listarSeguidos("A06"));
        verificar("P6 consulta a eliminado", null, r.listarAmigos("A01"));
        verificar("P6 contarAmigos eliminado", -1, r.contarAmigos("A01"));
        verificar("P6 seguimientos restantes", 0, r.contarSeguimientos());
        verificar("eliminarPersona inexistente", false, r.eliminarPersona("A01"));

        verificar("P7 registrar duplicado A02", false, r.registrarPersona("A02", "Otro"));
        verificar("P7 duplicado en minúsculas a02", false, r.registrarPersona("a02", "Otro"));
        verificar("P8 agregarAmistad(A03,A03) lazo", false, r.agregarAmistad("A03", "A03"));
        verificar("seguir(A03,A03) lazo", false, r.seguir("A03", "A03"));
        verificar("registrar usuario vacío", false, r.registrarPersona("  ", "X"));
        verificar("registrar con espacios", false, r.registrarPersona("A 07", "X"));
        verificar("registrar con comas", false, r.registrarPersona("A,07", "X"));
        verificar("amistad duplicada", false, r.agregarAmistad("A02", "A03"));
        verificar("amistad con inexistente", false, r.agregarAmistad("A02", "Z99"));

        System.out.println();
        System.out.println("Resultado: " + ok + " pruebas OK, " + fallos + " fallos.");
        if (fallos > 0) System.exit(1);
    }
}
