package desapp.grupo.e.webservice.controller.commerce;

import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.persistence.exception.CommerceDuplicatedException;
import desapp.grupo.e.service.commerce.CommerceService;
import desapp.grupo.e.webservice.handler.CustomizeErrorHandler;
import desapp.grupo.e.webservice.handler.commerce.CommerceControllerHandler;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommerceControllerTest {

    private CommerceService commerceService;
    private MockMvc mockMvc;
    private static final Long USER_ID = 1L;
    private static final String URL_BASE = "/user/" + USER_ID + "/commerce";

    @BeforeEach
    public void setUp() {
        commerceService = mock(CommerceService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CommerceController(commerceService))
                .setControllerAdvice(new CustomizeErrorHandler(), new CommerceControllerHandler())
                .build();
    }

    @Test
    public void createCommerce() throws Exception {
        String jsonPost = "{\"name\": \"Test\", \"addressNumber\": 1010, " +
                "\"latitude\": 21.1, \"longitude\": -12.1, \"phone\": \"0303456\"}";
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().withId(1L).build();

        when(commerceService.save(eq(USER_ID), any(Commerce.class))).thenReturn(commerce);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(jsonPost)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    public void createCommerceWithNameExistentShouldReturnA409() throws Exception {
        String jsonPost = "{\"name\": \"NameDuplicated\", \"addressNumber\": 1010, " +
                "\"latitude\": 21.1, \"longitude\": -12.1, \"phone\": \"0303456\"}";

        when(commerceService.save(eq(USER_ID), any(Commerce.class)))
                .thenThrow(CommerceDuplicatedException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(jsonPost)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void getCommerce() throws Exception {
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().withId(1L).build();

        when(commerceService.getCommerceByUser(eq(USER_ID))).thenReturn(commerce);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)));
    }

    @Test
    public void getCommerceByUserInexistent() throws Exception {
        when(commerceService.getCommerceByUser(eq(USER_ID))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteCommerce() throws Exception {
        Long commerceId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete(URL_BASE + "/" + commerceId)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}