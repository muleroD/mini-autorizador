package br.com.mulero.miniautorizador.unit;

import br.com.mulero.miniautorizador.domain.entity.Card;
import br.com.mulero.miniautorizador.domain.repository.CardRepository;
import br.com.mulero.miniautorizador.dto.CardDTO;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import br.com.mulero.miniautorizador.infrastructure.exception.CardAlreadyExistsException;
import br.com.mulero.miniautorizador.infrastructure.exception.CardNotFoundException;
import br.com.mulero.miniautorizador.service.CardService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static br.com.mulero.miniautorizador.util.TestUtil.createDefaultCardDto;
import static br.com.mulero.miniautorizador.util.TestUtil.createDefaultTransactionDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CardServiceTest extends BaseUnitTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @Test
    void createCardWithInitialBalance() {
        Card card = new Card("1234567890123456", "1234", BigDecimal.valueOf(500));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardDTO cardRequest = createDefaultCardDto();
        ResponseEntity<CardDTO> createdCard = cardService.create(cardRequest);

        assertEquals(HttpStatus.CREATED, createdCard.getStatusCode());
        assertNotNull(createdCard.getBody());
        assertEquals(cardRequest.getCardNumber(), createdCard.getBody().getCardNumber());
        assertEquals(cardRequest.getPassword(), createdCard.getBody().getPassword());
    }

    @Test
    void createCardAlreadyExists() {
        Card card = new Card("1234567890123456", "1234", BigDecimal.valueOf(500));
        when(cardRepository.findOne(any())).thenReturn(Optional.of(card));

        CardDTO cardRequest = createDefaultCardDto();
        assertThrows(CardAlreadyExistsException.class, () -> cardService.create(cardRequest));
    }

    @Test
    void getBalanceByCardNumber() {
        Card card = new Card("1234567890123456", "1234", BigDecimal.valueOf(500));
        when(cardRepository.findOne(any())).thenReturn(Optional.of(card));

        ResponseEntity<BigDecimal> balance = cardService.getBalanceByCardNumber("1234567890123456");

        assertEquals(HttpStatus.OK, balance.getStatusCode());
        assertNotNull(balance.getBody());
        assertEquals(card.getBalance(), balance.getBody());
    }

    @Test
    void getBalanceByCardNumberNotFound() {
        when(cardRepository.findOne(any())).thenReturn(Optional.empty());

        ResponseEntity<BigDecimal> balance = cardService.getBalanceByCardNumber("1234567890123456");

        assertEquals(HttpStatus.NOT_FOUND, balance.getStatusCode());
        assertNull(balance.getBody());
    }

    @Test
    void withdraw100FromCard() {
        Card card = new Card("1234567890123456", "1234", BigDecimal.valueOf(500));
        when(cardRepository.findOneByCardNumber(any())).thenReturn(card);

        TransactionDTO transactionDTO = createDefaultTransactionDto();
        transactionDTO.setAmount(BigDecimal.valueOf(100));
        Card updatedCard = cardService.withdraw(transactionDTO);

        assertEquals(card.getBalance(), updatedCard.getBalance());
    }

    @Test
    void withdrawFromCardNotFound() {
        when(cardRepository.findOneByCardNumber(any())).thenThrow(CardNotFoundException.class);

        TransactionDTO transactionDTO = createDefaultTransactionDto();
        transactionDTO.setAmount(BigDecimal.valueOf(100));

        assertThrows(CardNotFoundException.class, () -> cardService.withdraw(transactionDTO));
    }
}
