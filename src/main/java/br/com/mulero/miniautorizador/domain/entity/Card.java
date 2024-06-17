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
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(unique = true)
    private String number;

    @NotNull
    private String password;

    @NotNull
    private BigDecimal balance;

    public Card(String number, String password, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
        setPassword(password);
    }

    public void setPassword(@NotNull String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public Example<Card> toExample() {
        return Example.of(this);
    }
}
