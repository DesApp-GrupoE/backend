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
    private static final String KEY_CART = "/anyKey";

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
    public void createShoppingCart() throws Exception {
        when(shoppingCartService.createShoppingCart()).thenReturn(KEY_CART);

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.key", Is.isA(String.class)));
    }

    @Test
    public void getNewShoppingCartBySessionShouldReturnAEmptyShoppingCart() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + KEY_CART)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Token1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cartProducts", hasSize(0)));
    }

    @Test
    public void addProductToCart() throws Exception {
        String json = "{ \"id\": 1, \"quantity\": 1 }";
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + KEY_CART + PRODUCT)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addProductInexisteShouldReturnApiErrorAndStatusNotFound() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());
        String json = "{ \"id\": 0, \"quantity\": 1 }";

        doThrow(new ResourceNotFoundException("Product 0 not found"))
            .when(shoppingCartService).addProduct(anyString(), anyLong(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + KEY_CART + PRODUCT)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Product 0 not found")));
    }

    @Test
    public void deleteProductFromShoppingCart() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());
        Long anyProductId = 1L;
        String urlDelete = BASE_URL + KEY_CART + PRODUCT + "/" + anyProductId;

        mockMvc.perform(MockMvcRequestBuilders.delete(urlDelete)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addOfferToCart() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());
        String json = "{ \"id\": 1, \"quantity\": 1 }";
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + KEY_CART + OFFER)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addOfferInexisteShouldReturnApiErrorAndStatusNotFound() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());
        String json = "{ \"id\": 0, \"quantity\": 1 }";

        doThrow(new ResourceNotFoundException("Offer 0 not found"))
                .when(shoppingCartService).addOffer(anyString(), anyLong(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + KEY_CART + OFFER)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Offer 0 not found")));
    }

    @Test
    public void deleteOfferFromShoppingCart() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());
        Long anyOfferId = 1L;
        String urlDelete = BASE_URL + KEY_CART + OFFER + "/" + anyOfferId;

        mockMvc.perform(MockMvcRequestBuilders.delete(urlDelete)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void setQuantityToProduct() throws Exception {
        when(shoppingCartService.getShoppingCartByKey(anyString())).thenReturn(new ShoppingCart());
        String urlPut = BASE_URL + KEY_CART + PRODUCT;
        String json = "{ \"id\": 1, \"quantity\": 1 }";

        mockMvc.perform(MockMvcRequestBuilders.put(urlPut)
                .content(json)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
