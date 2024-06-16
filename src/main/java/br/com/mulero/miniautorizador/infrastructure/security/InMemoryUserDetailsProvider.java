package br.com.mulero.miniautorizador.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import static br.com.mulero.miniautorizador.infrastructure.security.Role.USER;

@Component
@RequiredArgsConstructor
public class InMemoryUserDetailsProvider {

    private final PasswordEncoder passwordEncoder;

    @Value("${security.basic.username}")
    private String basicUsername;

    @Value("${security.basic.password}")
    private String basicPassword;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails basicUser = User.builder()
                .username(basicUsername)
                .password(basicPassword)
                .passwordEncoder(passwordEncoder::encode)
                .authorities(new SimpleGrantedAuthority(USER))
                .build();

        return new InMemoryUserDetailsManager(basicUser);
    }
}
