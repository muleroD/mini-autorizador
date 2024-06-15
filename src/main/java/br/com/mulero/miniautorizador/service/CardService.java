package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.domain.repository.CardRepository;
import br.com.mulero.miniautorizador.dto.CardDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.CardAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.CREATED;

@Service
@RequiredArgsConstructor
public class CardService {

    public static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(500);

    private final CardRepository cardRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<CardDTO> create(CardDTO cardDTO) {
        Card card = Card.builder().number(cardDTO.getCardNumber()).build();

        cardRepository.findOne(card.toExample()).ifPresent(c -> {
            throw new CardAlreadyExistsException(cardDTO);
        });

        card.setPassword(cardDTO.getPassword());
        card.setBalance(INITIAL_BALANCE);
        cardRepository.save(card);

        return ResponseEntity.status(CREATED).body(cardDTO);
    }

    public ResponseEntity<BigDecimal> getBalanceByCardNumber(String cardNumber) {
        return cardRepository.findOne(Card.builder().number(cardNumber).build().toExample())
                .map(Card::getBalance)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
