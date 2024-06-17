package br.com.mulero.miniautorizador.integration;

import br.com.mulero.miniautorizador.rest.MockMvcClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public abstract class BaseIntegrationTest extends MockMvcClient {

    public static final String URL_TRANSACTION = "/transacoes";
    public static final String URL_CARDS = "/cartoes";

    private AutoCloseable closeable;

    @BeforeEach
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void closeService() throws Exception {
        closeable.close();
    }
}
