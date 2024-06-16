package br.com.mulero.miniautorizador.domain.repository;

import br.com.mulero.miniautorizador.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("select true from Card c where c.number = :cardNumber")
    Optional<Boolean> findFirstByNumber(String cardNumber);

}
