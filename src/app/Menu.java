package app;

import grafo.RedSocial;
import java.util.Scanner;

/** Menú interactivo por consola para operar la red social. */
public class Menu {

    public static void main(String[] args) {
        RedSocial red = new RedSocial();
        Scanner sc = new Scanner(System.in);
        int op;
        do {
            System.out.println("\n===== CONEXIÓN AMBATO =====");
            System.out.println("1. Registrar persona        2. Eliminar persona");
            System.out.println("3. Agregar amistad          4. Eliminar amistad");
            System.out.println("5. Seguir                   6. Dejar de seguir");
            System.out.println("7. Listar amigos            8. Listar seguidos");
            System.out.println("9. Listar seguidores       10. Contar amigos");
            System.out.println("11. Mostrar red            12. Cargar datos de la Fase 1");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            String linea = sc.nextLine().trim();
            try { op = Integer.parseInt(linea); } catch (NumberFormatException e) { op = -1; }
            String a, b;
            switch (op) {
                case 1:
                    a = leer(sc, "Usuario: "); b = leer(sc, "Nombre: ");
                    System.out.println(red.registrarPersona(a, b) ? "Registrado." : "No se pudo registrar (vacío, repetido, espacios o comas).");
                    break;
                case 2:
                    a = leer(sc, "Usuario: ");
                    System.out.println(red.eliminarPersona(a) ? "Eliminado." : "El usuario no existe.");
                    break;
                case 3:
                    a = leer(sc, "Usuario A: "); b = leer(sc, "Usuario B: ");
                    System.out.println(red.agregarAmistad(a, b) ? "Amistad creada." : "No se pudo (no existe, lazo o duplicada).");
                    break;
                case 4:
                    a = leer(sc, "Usuario A: "); b = leer(sc, "Usuario B: ");
                    System.out.println(red.eliminarAmistad(a, b) ? "Amistad eliminada." : "No existía esa amistad.");
                    break;
                case 5:
                    a = leer(sc, "Quién sigue: "); b = leer(sc, "A quién: ");
                    System.out.println(red.seguir(a, b) ? "Ahora lo sigue." : "No se pudo (no existe, lazo o duplicado).");
                    break;
                case 6:
                    a = leer(sc, "Quién deja de seguir: "); b = leer(sc, "A quién: ");
                    System.out.println(red.dejarDeSeguir(a, b) ? "Dejó de seguirlo." : "No lo seguía.");
                    break;
                case 7: imprimir(red.listarAmigos(leer(sc, "Usuario: "))); break;
                case 8: imprimir(red.listarSeguidos(leer(sc, "Usuario: "))); break;
                case 9: imprimir(red.listarSeguidores(leer(sc, "Usuario: "))); break;
                case 10:
                    int n = red.contarAmigos(leer(sc, "Usuario: "));
                    System.out.println(n < 0 ? "El usuario no existe." : "Amigos: " + n);
                    break;
                case 11: System.out.print(red.mostrarRed()); break;
                case 12:
                    String[][] p = {{"A01","Sofía"},{"A02","Mateo"},{"A03","Valeria"},{"A04","Andrés"},{"A05","Camila"},{"A06","Diego"}};
                    for (String[] x : p) red.registrarPersona(x[0], x[1]);
                    String[][] am = {{"A01","A02"},{"A02","A03"},{"A03","A04"},{"A04","A01"},{"A04","A05"}};
                    for (String[] x : am) red.agregarAmistad(x[0], x[1]);
                    System.out.println("Datos de la Fase 1 cargados.");
                    break;
                case 0: System.out.println("Hasta luego."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private static String leer(Scanner sc, String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    private static void imprimir(String s) {
        System.out.println(s == null ? "El usuario no existe." : s);
    }
}
