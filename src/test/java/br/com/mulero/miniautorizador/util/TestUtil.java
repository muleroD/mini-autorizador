package br.com.mulero.miniautorizador.util;

import br.com.mulero.miniautorizador.dto.CardDTO;
import br.com.mulero.miniautorizador.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Random;

public class TestUtil {

    private static final Random RANDOM = new Random();

    public static TransactionDTO createDefaultTransactionDto() {
        return new TransactionDTO(createDefaultCardDto());
    }

    public static TransactionDTO createRandomTransactionDto() {
        return new TransactionDTO(createRandomCardDto());
    }

    public static CardDTO createDefaultCardDto() {
        return getCardDTO("1234567890123456", "1234");
    }

    public static CardDTO createRandomCardDto() {
        return getCardDTO(generateRandomCardNumber(), generateRandomPassword());
    }

    public static CardDTO getCardDTO(String cardNumber, String password) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(cardNumber);
        cardDTO.setPassword(password);
        return cardDTO;
    }

    public static TransactionDTO getTransactionDTO(CardDTO cardDTO, BigDecimal amount) {
        return new TransactionDTO(cardDTO, amount);
    }

    public static String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        RANDOM.ints(16, 0, 10)
                .forEach(cardNumber::append);
        return cardNumber.toString();
    }

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();
        RANDOM.ints(4, 0, 10)
                .forEach(password::append);
        return password.toString();
    }

}
