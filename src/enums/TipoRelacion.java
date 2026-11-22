package enums;

/** Tipos de relación entre usuarios de la red social. */
public enum TipoRelacion {
    AMISTAD, // recíproca: se guarda en ambos sentidos
    SEGUIR   // dirigida: solo desde quien sigue hacia el seguido
}
