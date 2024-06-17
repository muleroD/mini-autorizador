package br.com.mulero.miniautorizador.integration;

import br.com.mulero.miniautorizador.dto.CardDTO;
import br.com.mulero.miniautorizador.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static br.com.mulero.miniautorizador.infrastructure.config.I18nConfig.RESOURCE_BUNDLE;
import static br.com.mulero.miniautorizador.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class TransactionControllerIntegrationTest extends BaseIntegrationTest {

    public static final String OPERATION_TRANSACTION_SUCCESS = RESOURCE_BUNDLE.getString("operation.transaction.success");

    public static final String OPERATION_TRANSACTION_CARD_NOT_FOUND = RESOURCE_BUNDLE.getString("error.card.not.found");
    public static final String OPERATION_TRANSACTION_INSUFFICIENT_BALANCE = RESOURCE_BUNDLE.getString("error.insufficient.balance");
    public static final String OPERATION_TRANSACTION_INVALID_PASSWORD = RESOURCE_BUNDLE.getString("error.invalid.card.password");

    @Test
    void withdraw() throws Exception {
        CardDTO cardBody = createDefaultCardDto();
        performPost(URL_CARDS, cardBody);

        TransactionDTO body = createDefaultTransactionDto();
        body.setAmount(BigDecimal.valueOf(100));

        MvcResult result = performPost(URL_TRANSACTION, body);
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        String transactionResult = result.getResponse().getContentAsString();

        assertEquals(OPERATION_TRANSACTION_SUCCESS, transactionResult);
    }

    @Test
    void withdrawWithCardNotFound() throws Exception {
        CardDTO cardBody = createDefaultCardDto();
        performPost(URL_CARDS, cardBody);

        TransactionDTO body = createRandomTransactionDto();
        body.setAmount(BigDecimal.valueOf(100));

        MvcResult result = performPost(URL_TRANSACTION, body);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());

        String transactionResult = result.getResponse().getContentAsString();

        assertEquals(OPERATION_TRANSACTION_CARD_NOT_FOUND, transactionResult);
    }

    @Test
    void withdrawWithInsufficientBalance() throws Exception {
        CardDTO cardBody = createDefaultCardDto();
        performPost(URL_CARDS, cardBody);

        TransactionDTO body = createDefaultTransactionDto();
        body.setAmount(BigDecimal.valueOf(1000));

        MvcResult result = performPost(URL_TRANSACTION, body);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());

        String transactionResult = result.getResponse().getContentAsString();

        assertEquals(OPERATION_TRANSACTION_INSUFFICIENT_BALANCE, transactionResult);
    }

    @Test
    void withdrawWithInvalidPassword() throws Exception {
        CardDTO cardBody = createDefaultCardDto();
        performPost(URL_CARDS, cardBody);

        TransactionDTO body = createDefaultTransactionDto();
        body.setAmount(BigDecimal.valueOf(100));
        body.setPassword(generateRandomPassword());

        MvcResult result = performPost(URL_TRANSACTION, body);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());

        String transactionResult = result.getResponse().getContentAsString();

        assertEquals(OPERATION_TRANSACTION_INVALID_PASSWORD, transactionResult);
    }

    @Test
    void deposit() throws Exception {
        MvcResult resultBalance;

        CardDTO cardBody = createDefaultCardDto();
        performPost(URL_CARDS, cardBody);

        String cardNumber = cardBody.getCardNumber();
        resultBalance = performGet(URL_CARDS + "/" + cardNumber);
        BigDecimal currentBalance = new BigDecimal(resultBalance.getResponse().getContentAsString());
        assertEquals(DEFAULT_BALANCE, currentBalance);

        TransactionDTO body = createDefaultTransactionDto();
        body.setAmount(BigDecimal.valueOf(100));

        MvcResult result = performPost(URL_TRANSACTION_DEPOSIT, body);
        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        String transactionResult = result.getResponse().getContentAsString();

        assertEquals(OPERATION_TRANSACTION_SUCCESS, transactionResult);

        resultBalance = performGet(URL_CARDS + "/" + cardNumber);
        BigDecimal newBalance = new BigDecimal(resultBalance.getResponse().getContentAsString());
        assertEquals(DEFAULT_BALANCE.add(BigDecimal.valueOf(100)), newBalance);
    }
}