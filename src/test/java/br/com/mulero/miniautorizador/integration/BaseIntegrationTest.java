package br.com.mulero.miniautorizador.integration;

import br.com.mulero.miniautorizador.rest.MockMvcClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
public abstract class BaseIntegrationTest extends MockMvcClient {

    public static final String URL_CARDS = "/cartoes";
    public static final String URL_TRANSACTION = "/transacoes";
    public static final String URL_TRANSACTION_DEPOSIT = "/transacoes/deposito";
    public static final String URL_TRANSACTION_TRANSFER = "/transacoes/transferencia";

    public static final BigDecimal DEFAULT_BALANCE = new BigDecimal("500");

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    @Rollback
    public void closeService() throws Exception {
        closeable.close();
    }
}
