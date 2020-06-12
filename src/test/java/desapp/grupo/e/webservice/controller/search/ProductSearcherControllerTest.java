package desapp.grupo.e.webservice.controller.search;

import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.search.ProductSearcherService;
import desapp.grupo.e.webservice.controller.search.ProductSearcherController;
import desapp.grupo.e.webservice.handler.CustomizeErrorHandler;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductSearcherControllerTest {

    private static final String URL_BASE = "/products";

    private ProductSearcherService productSearchService;
    private List<Product> productsMock;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        productSearchService = mock(ProductSearcherService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductSearcherController(productSearchService))
                .setControllerAdvice(new CustomizeErrorHandler())
                .build();

        Product product1 = ProductBuilder.aProduct()
                .withCommerceId(1L).withId(1L)
                .withName("Fideos").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .build();
        Product product2 = ProductBuilder.aProduct()
                .withCommerceId(1L).withId(2L)
                .withName("Coca Cola 2.25L").withBrand("Coca Cola")
                .withStock(100).withPrice(120.0)
                .build();
        Product product3 = ProductBuilder.aProduct()
                .withCommerceId(1L).withId(2L)
                .withName("Coca Cola Light 1.5L").withBrand("Coca Cola")
                .withStock(100).withPrice(120.0)
                .build();
        this.productsMock = List.of(product1, product2, product3);
    }

    @Test
    public void searchProductsWithAddressAndProductSearchWithNameShouldReturnAListOfProductWithSameName() throws Exception {
        String search = "{" +
                            "\"address\": " +
                                "{\"county\": \"Quilmes\", \"region\": \"Buenos Aires\", \"kilometers\": 10, \"latitude\": 10.0, \"longitude\": 10.0}," +
                            "\"product\": " +
                                "{\"name\": \"Coca Cola\"}" +
                        " }";
        List<Product> products = this.productsMock.stream().filter(p -> p.getName().contains("Coca Cola")).collect(Collectors.toList());
        when(productSearchService.findProducts(any())).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(search)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", Is.is("Coca Cola 2.25L")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", Is.is("Coca Cola Light 1.5L")));
    }

    @Test
    public void searchProductsWithAddressOnlyShouldReturnAllProducts() throws Exception {
        String search = "{" +
                "\"address\": " +
                    "{\"county\": \"Quilmes\", \"region\": \"Buenos Aires\", \"kilometers\": 10, \"latitude\": 10.0, \"longitude\": 10.0 }" +
                " }";
        when(productSearchService.findProducts(any())).thenReturn(this.productsMock);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE)
                .content(search)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", isA(ArrayList.class)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(3)));
    }
}
