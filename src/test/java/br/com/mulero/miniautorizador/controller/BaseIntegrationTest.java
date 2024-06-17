package br.com.mulero.miniautorizador.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

import java.util.Base64;

public abstract class BaseIntegrationTest {

    protected ObjectMapper objectMapper;

    private AutoCloseable closeable;

    @BeforeEach
    public void openService() {
        closeable = MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void closeService() throws Exception {
        closeable.close();
    }

    public String getBasicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes());
    }

    public String getInvalidBasicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString("invalid:invalid".getBytes());
    }
}
