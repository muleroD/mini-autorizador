package br.com.mulero.miniautorizador.infrastructure.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {

    @Value("${security.basic.username}")
    private String basicUsername;

    @Value("${security.basic.password}")
    private String basicPassword;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        if (!basicUsername.equals(username) || !basicPassword.equals(password)) {
            return null;
        }

        final List<GrantedAuthority> authorityList = Collections.singletonList(new SimpleGrantedAuthority(Role.USER));
        final UserDetails principal = new User(username, password, authorityList);

        return new UsernamePasswordAuthenticationToken(principal, password, authorityList);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
