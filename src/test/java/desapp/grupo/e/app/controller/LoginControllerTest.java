package desapp.grupo.e.app.controller;

import desapp.grupo.e.app.config.CustomizeErrorHandler;
import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.model.dto.user.CustomerDTO;
import desapp.grupo.e.model.user.Customer;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import desapp.grupo.e.service.jackson.JsonUtils;
import desapp.grupo.e.service.login.LoginService;
import desapp.grupo.e.service.persistence.exception.DataErrorException;
import desapp.grupo.e.service.persistence.exception.UniqueClassException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LoginControllerTest {

    private LoginService loginService;
    private LoginController loginController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        loginService = mock(LoginService.class);
        loginController = new LoginController(loginService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                    .setControllerAdvice(new CustomizeErrorHandler())
                    .build();
    }

    @Test
    public void whenPostRequestToSignUpAndTheyAreMissingFields_thenReturnBadRequesResponseWithFieldsMissing() throws Exception {
        CustomerDTO customerRequestDTO = new CustomerDTO();
        customerRequestDTO.setName("Test");
        customerRequestDTO.setSurname("Test");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(customerRequestDTO))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("Email is mandatory")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("Password is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToSignUpAndIsValidUser_thenReturnACustomerWithSameDataAndId() throws Exception {
        CustomerDTO customerRequestDTO = new CustomerDTO();
        customerRequestDTO.setName("Test");
        customerRequestDTO.setSurname("Test");
        customerRequestDTO.setEmail("test@test.test");
        customerRequestDTO.setPassword("Secret");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Test");
        customer.setSurname("Test");
        customer.setEmail("test@test.test");
        customer.setPassword("Secret");

        when(loginService.signUp(any(Customer.class))).thenReturn(customer);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(customerRequestDTO))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        CustomerDTO newCustomer = (CustomerDTO) JsonUtils.fromJson(CustomerDTO.class, response.getContentAsString());
        Assertions.assertEquals(newCustomer.getId(), 1L);
        Assertions.assertEquals(newCustomer.getName(), customerRequestDTO.getName());
        Assertions.assertEquals(newCustomer.getSurname(), customerRequestDTO.getSurname());
        Assertions.assertEquals(newCustomer.getEmail(), customerRequestDTO.getEmail());
        Assertions.assertNull(newCustomer.getPassword()); //Password not return
    }

    @Test
    public void whenPostRequestToSignUpAndIsEmailUserIsAlreadyInUse_thenReturnAResponseWithStatus409AndAnApiError() throws Exception {
        CustomerDTO customerRequestDTO = new CustomerDTO();
        customerRequestDTO.setName("Test");
        customerRequestDTO.setSurname("Test");
        customerRequestDTO.setEmail("test@test.test");
        customerRequestDTO.setPassword("Secret");

        when(loginService.signUp(any(Customer.class))).thenThrow(new UniqueClassException("Email was already registered"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(customerRequestDTO))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        ApiError apiError = (ApiError) JsonUtils.fromJson(ApiError.class, response.getContentAsString());
        Assertions.assertEquals(apiError.getError(), "Email was already registered");
    }

    @Test
    public void whenPostRequestToSignWithCorrectRequestButOccursAnExceptionFromDatabase_thenReturnAResponseWithStatus500AndAnApiError() throws Exception {
        CustomerDTO customerRequestDTO = new CustomerDTO();
        customerRequestDTO.setName("Test");
        customerRequestDTO.setSurname("Test");
        customerRequestDTO.setEmail("test@test.test");
        customerRequestDTO.setPassword("Secret");

        when(loginService.signUp(any(Customer.class))).thenThrow(new DataErrorException("Custom message: Error in database"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(customerRequestDTO))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        ApiError apiError = (ApiError) JsonUtils.fromJson(ApiError.class, response.getContentAsString());
        Assertions.assertEquals(apiError.getError(), "Unexpected error. Please, try again.");
    }

    @Test
    public void whenPostRequestToAnyEndpointAndJsonIsInvalid_thenReturnBadRequest() throws Exception {
        String user = "{\"name\": \"\" \"email\" : \"bob@domain.com\"}";
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(user)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        ApiError error = (ApiError) JsonUtils.fromJson(ApiError.class, response.getContentAsString());
        Assertions.assertEquals(response.getStatus(), HttpStatus.BAD_REQUEST.value());
        Assertions.assertEquals(error.getError(), "Json Inconsistent");
    }

}
