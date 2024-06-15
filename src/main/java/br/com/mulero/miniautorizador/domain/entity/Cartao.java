package br.com.mulero.miniautorizador.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(unique = true)
    private String numero;

    @NotNull
    @Size(min = 4, max = 4)
    private String senha;

}
