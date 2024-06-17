package br.com.mulero.miniautorizador.util;

import br.com.mulero.miniautorizador.dto.CardDTO;

import java.util.Random;

public class CardUtil {

    private static final Random RANDOM = new Random();

    public static CardDTO createDefaultCardDto() {
        return getCardDTO("1234567890123456", "1234");
    }

    public static CardDTO createRandomCardDto() {
        return getCardDTO(generateCardNumber(), generatePassword());
    }

    private static CardDTO getCardDTO(String cardNumber, String password) {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(cardNumber);
        cardDTO.setPassword(password);
        return cardDTO;
    }

    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        RANDOM.ints(16, 0, 10)
                .forEach(cardNumber::append);
        return cardNumber.toString();
    }

    public static String generatePassword() {
        StringBuilder password = new StringBuilder();
        RANDOM.ints(4, 0, 10)
                .forEach(password::append);
        return password.toString();
    }

}
