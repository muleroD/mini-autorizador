package br.com.mulero.miniautorizador.domain.entity;

import br.com.mulero.miniautorizador.enumerator.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Card card;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private TransactionType type;
}
