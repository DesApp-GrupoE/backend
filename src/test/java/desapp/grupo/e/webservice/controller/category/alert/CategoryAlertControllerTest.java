package desapp.grupo.e.webservice.controller.category.alert;

import desapp.grupo.e.model.builder.product.CategoryAlertBuilder;
import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.service.category.alert.CategoryAlertService;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.webservice.handler.CustomizeErrorHandler;
import desapp.grupo.e.webservice.handler.category.alert.CategoryAlertControllerHandler;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CategoryAlertControllerTest {

    private static final Long USER_ID = 1L;
    private static final String URL_BASE_CATEGORY_ALERT = "/user/" + USER_ID + "/category-alert";

    private CategoryAlertService categoryAlertService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        categoryAlertService = mock(CategoryAlertService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CategoryAlertController(categoryAlertService))
                .setControllerAdvice(new CustomizeErrorHandler(), new CategoryAlertControllerHandler())
                .build();
    }

    @Test
    public void createCategoryAlert() throws Exception {
        String jsonPost = "{ \"category\" : \""+Category.ALMACEN.name()+"\", \"percentage\" : 10 }";
        CategoryAlert catAlert = CategoryAlertBuilder.aCategoryAlert()
                .withId(1L)
                .withCategory(Category.ALMACEN)
                .withPercentage(10)
                .build();

        when(categoryAlertService.save(eq(USER_ID), any(CategoryAlert.class))).thenReturn(catAlert);

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE_CATEGORY_ALERT)
                .content(jsonPost)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category", Is.is(Category.ALMACEN.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.percentage", Is.is(10)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createCategoryAlertWithInexistentUserShouldReturnAnApiErrorWithNotFoundStatus() throws Exception {
        String jsonPost = "{ \"category\" : \""+Category.ALMACEN.name()+"\", \"percentage\" : 10 }";
        String url = URL_BASE_CATEGORY_ALERT.replace(USER_ID.toString(), "0");

        given(categoryAlertService.save(any(Long.class), any(CategoryAlert.class))).willThrow( new ResourceNotFoundException("User 0 not found"));

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(jsonPost)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("User 0 not found")));
    }

    @Test
    public void createCategoryAlertWithInexistentCategoryShouldReturnAnApiErrorWithNoutFoundStatus() throws Exception {
        String jsonPost = "{ \"category\" : \"INEXISTENT\", \"percentage\" : 10 }";

        mockMvc.perform(MockMvcRequestBuilders.post(URL_BASE_CATEGORY_ALERT)
                .content(jsonPost)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Category 'INEXISTENT' not found")));
    }


    @Test
    public void getAllCategoryAlertsFromUser() throws Exception {
        CategoryAlert catAlert = CategoryAlertBuilder.aCategoryAlert()
                .withId(1L)
                .withCategory(Category.BEBIDAS)
                .withPercentage(15)
                .build();
        ArrayList<CategoryAlert> categories = new ArrayList<>();
        categories.add(catAlert);

        when(categoryAlertService.getCategoryAlertsByUserId(eq(USER_ID))).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE_CATEGORY_ALERT)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].category", Is.is(Category.BEBIDAS.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].percentage", Is.is(15)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getAllCategoryAlertsFromInexistentUserShouldReturnAnApiErrorWithStatusNotFound() throws Exception {
        String url = URL_BASE_CATEGORY_ALERT.replace(USER_ID.toString(), "0");

        given(categoryAlertService.getCategoryAlertsByUserId(any(Long.class))).willThrow( new ResourceNotFoundException("User 0 not found"));

        mockMvc.perform(MockMvcRequestBuilders.get(url)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("User 0 not found")));
    }

    @Test
    public void deleteCategoryAlert() throws Exception {
        Long catAlertId = 1L;
        String urlDelete = URL_BASE_CATEGORY_ALERT + "/" + catAlertId;

        mockMvc.perform(MockMvcRequestBuilders.delete(urlDelete)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteCategoryAlertWithInexistentUserShouldReturnAnApiErrorWithStatusNotFound() throws Exception {
        Long catAlertId = 1L;
        String url = URL_BASE_CATEGORY_ALERT.replace(USER_ID.toString(), "0") + "/" + catAlertId;

        doThrow(new ResourceNotFoundException("User 0 not found"))
                .when(categoryAlertService).removeById(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete(url)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("User 0 not found")));
    }
}
