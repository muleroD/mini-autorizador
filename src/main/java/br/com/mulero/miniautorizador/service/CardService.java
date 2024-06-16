package br.com.mulero.miniautorizador.service;

import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.domain.repository.CardRepository;
import br.com.mulero.miniautorizador.dto.CardDTO;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
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
        Card cardExample = Card.builder().number(cardNumber).build();

        return cardRepository.findOne(cardExample.toExample())
                .map(Card::getBalance)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Transactional(rollbackFor = Exception.class)
    public void withdraw(TransactionDTO transactionDTO) {
        Card card = cardRepository.findOneByCardNumber(transactionDTO.getCardNumber());

        card.setBalance(card.getBalance().subtract(transactionDTO.getAmount()));
        cardRepository.save(card);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deposit(TransactionDTO transactionDTO, String cardNumber) {
        Card card = cardRepository.findOneByCardNumber(cardNumber);

        card.setBalance(card.getBalance().add(transactionDTO.getAmount()));
        cardRepository.save(card);
    }

    @Transactional(rollbackFor = Exception.class)
    public void transfer(TransactionDTO transactionDTO, String cardNumber) {
        Card cardToReceive = cardRepository.findOneByCardNumber(transactionDTO.getCardNumber());
        Card cardToTransfer = cardRepository.findOneByCardNumber(cardNumber);

        cardToReceive.setBalance(cardToReceive.getBalance().add(transactionDTO.getAmount()));
        cardToTransfer.setBalance(cardToTransfer.getBalance().subtract(transactionDTO.getAmount()));

        cardRepository.save(cardToReceive);
        cardRepository.save(cardToTransfer);
    }
}
