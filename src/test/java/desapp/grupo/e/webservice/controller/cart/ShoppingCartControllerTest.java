package desapp.grupo.e.webservice.controller.cart;

import desapp.grupo.e.model.cart.ShoppingCart;
import desapp.grupo.e.service.cart.ShoppingCartService;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.webservice.handler.CustomizeErrorHandler;
import desapp.grupo.e.webservice.handler.cart.ShoppingCartHandler;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShoppingCartControllerTest {

    private static final String BASE_URL = "/cart";
    private static final String PRODUCT = "/product";
    private static final String OFFER = "/offer";

    private ShoppingCartService shoppingCartService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        shoppingCartService = mock(ShoppingCartService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ShoppingCartController(shoppingCartService))
                .setControllerAdvice(new CustomizeErrorHandler(), new ShoppingCartHandler())
                .build();
    }

    @Test
    public void getNewShoppingCartBySessionShouldReturnAEmptyShoppingCart() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartProducts", hasSize(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartOfferProducts", hasSize(0)));
    }

    @Test
    public void addProductToCart() throws Exception {
        String json = "{ \"id\": 1, \"quantity\": 1 }";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + PRODUCT)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addProductInexisteShouldReturnApiErrorAndStatusNotFound() throws Exception {
        String json = "{ \"id\": 0, \"quantity\": 1 }";

        doThrow(new ResourceNotFoundException("Product 0 not found"))
            .when(shoppingCartService).addProduct(anyString(), anyLong(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + PRODUCT)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product 0 not found")));
    }

    @Test
    public void deleteProductFromShoppingCart() throws Exception {
        Long anyProductId = 1L;
        String urlDelete = BASE_URL + PRODUCT + "/" + anyProductId;

        mockMvc.perform(MockMvcRequestBuilders.delete(urlDelete)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addOfferToCart() throws Exception {
        String json = "{ \"id\": 1, \"quantity\": 1 }";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + OFFER)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addOfferInexisteShouldReturnApiErrorAndStatusNotFound() throws Exception {
        String json = "{ \"id\": 0, \"quantity\": 1 }";

        doThrow(new ResourceNotFoundException("Offer 0 not found"))
                .when(shoppingCartService).addOffer(anyString(), anyLong(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + OFFER)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Offer 0 not found")));
    }

    @Test
    public void deleteOfferFromShoppingCart() throws Exception {
        Long anyOfferId = 1L;
        String urlDelete = BASE_URL + OFFER + "/" + anyOfferId;

        mockMvc.perform(MockMvcRequestBuilders.delete(urlDelete)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
