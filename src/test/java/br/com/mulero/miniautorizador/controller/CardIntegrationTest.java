package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.dto.CardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static br.com.mulero.miniautorizador.util.CardUtil.createDefaultCardDto;
import static br.com.mulero.miniautorizador.util.CardUtil.generateCardNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class CardIntegrationTest extends BaseIntegrationTest {

    private final BigDecimal balance = new BigDecimal("500.00");

    public static final String URL_CARDS = "/cartoes";

    @Test
    void create() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        MvcResult result = performPost(URL_CARDS, cardBody);

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        CardDTO cardResult = objectMapper.readValue(result.getResponse().getContentAsString(), CardDTO.class);

        assertNotNull(cardResult);

        assertEquals(cardBody.getCardNumber(), cardResult.getCardNumber());
        assertEquals(cardBody.getPassword(), cardResult.getPassword());
    }

    @Test
    void createWithExistingCard() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        MvcResult result = performPost(URL_CARDS, cardBody);

        assertNotNull(result.getResponse().getContentAsString());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
    }

    @Test
    void createWithAuthenticationError() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        MvcResult result = performInvalidPost(URL_CARDS, cardBody);

        assertNotNull(result);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }

    @Test
    void getBalanceByCardNumber() throws Exception {
        String cardNumber = createDefaultCardDto().getCardNumber();

        MvcResult result = performGet(URL_CARDS + "/" + cardNumber);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        BigDecimal currentBalance = new BigDecimal(result.getResponse().getContentAsString());
        assertNotNull(currentBalance);

        assertEquals(currentBalance, balance);
    }

    @Test
    void getBalanceByCardNumberWithNonExistingCard() throws Exception {
        String cardNumber = generateCardNumber();

        MvcResult result = performGet(URL_CARDS + "/" + cardNumber);

        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void getBalanceByCardNumberWithAuthenticationError() throws Exception {
        String cardNumber = generateCardNumber();

        MvcResult result = performInvalidGet(URL_CARDS + "/" + cardNumber);

        assertNotNull(result);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }

}