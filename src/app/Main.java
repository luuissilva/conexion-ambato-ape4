package app;

import grafo.RedSocial;

/** Ejecuta la secuencia de comprobación de la Tabla 3 (Conexión Ambato). */
public class Main {

    private static void titulo(String t) {
        System.out.println();
        System.out.println("=== " + t + " ===");
    }

    public static void main(String[] args) {
        RedSocial red = new RedSocial();
        String seccion = args.length > 0 ? args[0] : "todo";
        boolean todo = seccion.equals("todo");

        // Paso 1: registrar A01-A06 y las cinco amistades
        String[][] personas = {
            {"A01", "Sofía"}, {"A02", "Mateo"}, {"A03", "Valeria"},
            {"A04", "Andrés"}, {"A05", "Camila"}, {"A06", "Diego"}
        };
        String[][] amistades = {
            {"A01", "A02"}, {"A02", "A03"}, {"A03", "A04"}, {"A04", "A01"}, {"A04", "A05"}
        };
        boolean ver1 = todo || seccion.equals("1");
        if (ver1) titulo("PASO 1: registrarPersona A01-A06");
        for (String[] p : personas) {
            boolean r = red.registrarPersona(p[0], p[1]);
            if (ver1) System.out.println("registrarPersona(" + p[0] + ", " + p[1] + ") -> " + r);
        }
        if (ver1) titulo("PASO 1: agregarAmistad (cinco amistades)");
        for (String[] a : amistades) {
            boolean r = red.agregarAmistad(a[0], a[1]);
            if (ver1) System.out.println("agregarAmistad(" + a[0] + ", " + a[1] + ") -> " + r);
        }
        if (ver1) {
            titulo("mostrarRed() - estado inicial");
            System.out.print(red.mostrarRed());
            System.out.println("Usuarios: " + red.contarPersonas() + " | Amistades: " + red.contarAmistades());
            System.out.print("Grados A01..A06: ");
            for (String[] p : personas) System.out.print(red.contarAmigos(p[0]) + " ");
            System.out.println();
        }

        // Pasos 2 y 3: seguimientos
        boolean ver2 = todo || seccion.equals("2");
        if (ver2) titulo("PASOS 2 y 3: seguir");
        boolean s1 = red.seguir("A02", "A01");
        boolean s2 = red.seguir("A06", "A01");
        if (ver2) {
            System.out.println("seguir(A02, A01) -> " + s1);
            System.out.println("seguir(A06, A01) -> " + s2);
            System.out.println("seguir(A02, A01) otra vez -> " + red.seguir("A02", "A01") + "  (duplicado)");
            System.out.println("listarSeguidores(A01) -> " + red.listarSeguidores("A01"));
            System.out.println("contarAmigos(A06) -> " + red.contarAmigos("A06") + "  (Diego no tiene amigos)");
            titulo("mostrarRed() - con seguimientos");
            System.out.print(red.mostrarRed());
            System.out.println("Seguimientos: " + red.contarSeguimientos());
        }

        // Paso 4: consultas
        boolean ver4 = todo || seccion.equals("4");
        if (ver4) {
            titulo("PASO 4: consultas");
            System.out.println("listarAmigos(A04)      -> " + red.listarAmigos("A04"));
            System.out.println("listarAmigos(a04)      -> " + red.listarAmigos("a04") + "  (misma cuenta)");
            System.out.println("listarSeguidos(A06)    -> " + red.listarSeguidos("A06"));
            System.out.println("listarSeguidos(A02)    -> " + red.listarSeguidos("A02"));
            System.out.println("listarSeguidores(A01)  -> " + red.listarSeguidores("A01"));
            System.out.println("listarSeguidores(A04)  -> " + red.listarSeguidores("A04"));
            System.out.println("contarAmigos(A04)      -> " + red.contarAmigos("A04"));
            System.out.println("contarAmigos(Z99)      -> " + red.contarAmigos("Z99") + "  (no existe)");
            System.out.println("listarAmigos(Z99)      -> " + red.listarAmigos("Z99") + "  (no existe)");
        }

        // Pasos 5 y 6: eliminaciones
        boolean ver5 = todo || seccion.equals("5");
        boolean e1 = red.eliminarAmistad("A04", "A05");
        if (ver5) {
            titulo("PASO 5: eliminarAmistad(A04, A05)");
            System.out.println("eliminarAmistad(A04, A05) -> " + e1);
            System.out.println("listarAmigos(A04) -> " + red.listarAmigos("A04"));
            System.out.println("listarAmigos(A05) -> " + red.listarAmigos("A05"));
            System.out.println("eliminarAmistad(A04, A05) otra vez -> " + red.eliminarAmistad("A04", "A05"));
        }
        boolean e2 = red.eliminarPersona("A01");
        if (ver5) {
            titulo("PASO 6: eliminarPersona(A01)");
            System.out.println("eliminarPersona(A01) -> " + e2);
            System.out.print(red.mostrarRed());
            System.out.println("listarAmigos(A02)     -> " + red.listarAmigos("A02"));
            System.out.println("listarAmigos(A04)     -> " + red.listarAmigos("A04"));
            System.out.println("listarSeguidos(A06)   -> " + red.listarSeguidos("A06"));
            System.out.println("listarAmigos(A01)     -> " + red.listarAmigos("A01") + "  (ya no existe)");
            System.out.println("Usuarios: " + red.contarPersonas() + " | Amistades: " + red.contarAmistades()
                    + " | Seguimientos: " + red.contarSeguimientos());
        }

        // Pasos 7 y 8: validaciones
        boolean ver7 = todo || seccion.equals("7");
        if (ver7) {
            titulo("PASOS 7 y 8: validaciones");
            System.out.println("registrarPersona(A02, Mateo Pérez) -> " + red.registrarPersona("A02", "Mateo Pérez") + "  (duplicado)");
            System.out.println("agregarAmistad(A03, A03)          -> " + red.agregarAmistad("A03", "A03") + "  (lazo)");
            System.out.println("registrarPersona(\"\", X)           -> " + red.registrarPersona("", "X"));
            System.out.println("registrarPersona(\"A 07\", X)       -> " + red.registrarPersona("A 07", "X"));
            System.out.println("registrarPersona(\"A,07\", X)       -> " + red.registrarPersona("A,07", "X"));
            System.out.println("agregarAmistad(A02, A03) repetida -> " + red.agregarAmistad("A02", "A03"));
            System.out.println("dejarDeSeguir(A06, A04)           -> " + red.dejarDeSeguir("A06", "A04") + "  (no lo seguía)");
        }
    }
}
