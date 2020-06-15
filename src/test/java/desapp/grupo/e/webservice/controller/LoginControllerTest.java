package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.webservice.handler.CustomizeErrorHandler;
import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.webservice.handler.LoginControllerHandler;
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
import desapp.grupo.e.persistence.exception.EmailRegisteredException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LoginControllerTest {

    private LoginService loginService;
    private AuthService authService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        loginService = mock(LoginService.class);
        authService = mock(AuthService.class);
        LoginController loginController = new LoginController(loginService, authService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController)
                    .setControllerAdvice(new CustomizeErrorHandler(), new LoginControllerHandler())
                    .build();
    }

    @Test
    public void whenPostRequestToSignUpAndTheyAreMissingFields_thenReturnBadRequesResponseWithFieldsMissing() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setSurname("Test");
        userDTO.setEmail("test@test.test");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(userDTO))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error[0]", Is.is("Password is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToSignUpAndIsValidUser_thenReturnACustomerWithSameDataAndId() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Test");
        userDTO.setSurname("Test");
        userDTO.setEmail("test@test.test");
        userDTO.setPassword("Secret");

        User user = UserBuilder.aUser().withId(1L).withName("Test").withSurname("Test")
                .withEmail("test@test.test").withPassword("Secret")
                .build();

        when(loginService.signUp(any(User.class))).thenReturn(user);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(userDTO))
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        UserDTO newUser = (UserDTO) JsonUtils.fromJson(UserDTO.class, response.getContentAsString());
        Assertions.assertEquals(1L, newUser.getId());
        Assertions.assertEquals(userDTO.getName(), newUser.getName());
        Assertions.assertEquals(userDTO.getSurname(), newUser.getSurname());
        Assertions.assertEquals(userDTO.getEmail(), newUser.getEmail());
        Assertions.assertNull(newUser.getPassword()); //Password not return
    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/logout")
            .header("Authorization", "token")
            .characterEncoding("utf-8")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void whenPostRequestToSignUpAndIsEmailUserIsAlreadyInUse_thenReturnAResponseWithStatus409AndAnApiError() throws Exception {
        UserDTO userRequestDTO = new UserDTO();
        userRequestDTO.setName("Test");
        userRequestDTO.setSurname("Test");
        userRequestDTO.setEmail("test@test.test");
        userRequestDTO.setPassword("Secret");

        when(loginService.signUp(any(User.class))).thenThrow(new EmailRegisteredException("Email was already registered"));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(JsonUtils.toJson(userRequestDTO))
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
