package br.com.mulero.miniautorizador.controller;

import br.com.mulero.miniautorizador.dto.CardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static br.com.mulero.miniautorizador.util.CardUtil.createDefaultCardDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CardIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create() throws Exception {
        CardDTO cardBody = createDefaultCardDto();

        MvcResult result = mockMvc.perform(post("/cartoes")
                        .header(HttpHeaders.AUTHORIZATION, getInvalidBasicAuth())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardBody)))
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());

        CardDTO cardResult = objectMapper.readValue(result.getResponse().getContentAsString(), CardDTO.class);

        assertNotNull(cardResult);

        assertEquals(cardBody.getCardNumber(), cardResult.getCardNumber());
        assertEquals(cardBody.getPassword(), cardResult.getPassword());
    }
}