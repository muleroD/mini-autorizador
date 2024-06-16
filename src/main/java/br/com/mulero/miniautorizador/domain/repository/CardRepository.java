package br.com.mulero.miniautorizador.domain.repository;

import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.infrastructure.exception.CardNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    default Card findOneByCardNumber(String cardNumber) {
        return findOne(Card.builder().number(cardNumber).build().toExample())
                .orElseThrow(CardNotFoundException::new);
    }
}
