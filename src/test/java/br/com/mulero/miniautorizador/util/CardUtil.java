package br.com.mulero.miniautorizador.util;

import br.com.mulero.miniautorizador.dto.CardDTO;

import java.util.Random;

public class CardUtil {

    private static final Random RANDOM = new Random();

    public static CardDTO createCardDTO() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber(generateCardNumber());
        cardDTO.setPassword(generatePassword());
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
