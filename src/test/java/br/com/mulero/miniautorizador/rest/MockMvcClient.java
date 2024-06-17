package br.com.mulero.miniautorizador.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcClient {

    @Autowired
    private MockMvc mockMvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    public MvcResult performGet(String url) throws Exception {
        return doGet(url, getBasicAuth());
    }

    public MvcResult performInvalidGet(String url) throws Exception {
        return doGet(url, getInvalidBasicAuth());
    }

    public MvcResult performPost(String url, Object body) throws Exception {
        return doPost(url, body, getBasicAuth());
    }

    public MvcResult performInvalidPost(String url, Object body) throws Exception {
        return doPost(url, body, getInvalidBasicAuth());
    }

    private MvcResult doGet(String url, String auth) throws Exception {
        return mockMvc.perform(get(url)
                        .header(HttpHeaders.AUTHORIZATION, auth)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private MvcResult doPost(String url, Object body, String auth) throws Exception {
        return mockMvc.perform(post(url)
                        .header(HttpHeaders.AUTHORIZATION, auth)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andReturn();
    }

    private String getBasicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes());
    }

    private String getInvalidBasicAuth() {
        return "Basic " + Base64.getEncoder().encodeToString("invalid:invalid".getBytes());
    }

}
