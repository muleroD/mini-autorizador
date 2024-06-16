package br.com.mulero.miniautorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(securedEnabled = true)
public class MiniAutorizadorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniAutorizadorApplication.class, args);
    }

}
