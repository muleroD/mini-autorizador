package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.rest.MockMvcClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class BaseIntegrationTest extends MockMvcClient {

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
