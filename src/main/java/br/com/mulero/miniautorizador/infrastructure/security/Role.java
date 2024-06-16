package br.com.mulero.miniautorizador.infrastructure.security;

public class Role {

    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private Role() {
        throw new IllegalStateException("Utility class");
    }
}
