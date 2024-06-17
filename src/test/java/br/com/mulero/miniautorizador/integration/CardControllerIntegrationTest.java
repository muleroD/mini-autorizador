package br.com.mulero.miniautorizador.integration;

import br.com.mulero.miniautorizador.dto.CardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static br.com.mulero.miniautorizador.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class CardControllerIntegrationTest extends BaseIntegrationTest {

    private final BigDecimal balance = new BigDecimal("500.00");

    @Test
    void createDefaultCard() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        MvcResult result = performPost(URL_CARDS, cardBody);

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        CardDTO cardResult = objectMapper.readValue(result.getResponse().getContentAsString(), CardDTO.class);

        assertNotNull(cardResult);

        assertEquals(cardBody.getCardNumber(), cardResult.getCardNumber());
        assertEquals(cardBody.getPassword(), cardResult.getPassword());
    }

    @Test
    void createRandomCard() throws Exception {
        CardDTO cardBody = createRandomCardDto();

        MvcResult result = performPost(URL_CARDS, cardBody);

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        CardDTO cardResult = objectMapper.readValue(result.getResponse().getContentAsString(), CardDTO.class);

        assertNotNull(cardResult);

        assertEquals(cardBody.getCardNumber(), cardResult.getCardNumber());
        assertEquals(cardBody.getPassword(), cardResult.getPassword());
    }

    @Test
    void createCardWithExistingCard() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        performPost(URL_CARDS, cardBody);
        MvcResult invalidResult = performPost(URL_CARDS, cardBody);
        assertNotNull(invalidResult.getResponse().getContentAsString());

        CardDTO cardResult = objectMapper.readValue(invalidResult.getResponse().getContentAsString(), CardDTO.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), invalidResult.getResponse().getStatus());
        assertEquals(cardBody.getCardNumber(), cardResult.getCardNumber());
        assertEquals(cardBody.getPassword(), cardResult.getPassword());
    }

    @Test
    void createCardWithAuthenticationError() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        MvcResult result = performInvalidPost(URL_CARDS, cardBody);

        assertEquals(0, result.getResponse().getContentLength());
        assertEquals(HttpStatus.UNAUTHORIZED.value(), result.getResponse().getStatus());
    }

    @Test
    void getBalanceByCardNumber() throws Exception {
        createDefaultCard();

        String cardNumber = createDefaultCardDto().getCardNumber();

        MvcResult result = performGet(URL_CARDS + "/" + cardNumber);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        BigDecimal currentBalance = new BigDecimal(result.getResponse().getContentAsString());
        assertNotNull(currentBalance);

        assertEquals(currentBalance, balance);
    }

    @Test
    void getBalanceByCardNumberWithNonExistingCard() throws Exception {
        createRandomCard();

        String cardNumber = generateCardNumber();

        MvcResult result = performGet(URL_CARDS + "/" + cardNumber);

        assertEquals(0, result.getResponse().getContentLength());
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