package br.com.mulero.miniautorizador.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(unique = true)
    private String numero;

    @NotNull
    private String senha;

    @NotNull
    private BigDecimal saldo;

    public void setSenha(@NotNull String senha) {
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    public Example<Cartao> toExample() {
        return Example.of(this);
    }
}
