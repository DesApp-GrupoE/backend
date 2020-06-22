package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.user.CommerceSector;
import desapp.grupo.e.webservice.handler.CustomizeErrorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.isA;

class CommerceSectorControllerTest {

    private MockMvc mockMvc;
    private static final String URL_BASE = "/commerce-sector";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CommerceSectorController())
                .setControllerAdvice(new CustomizeErrorHandler())
                .build();
    }

    @Test
    public void getAllCommerceSector() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(CommerceSector.values().length)));
    }
}