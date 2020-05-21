package desapp.grupo.e.webservice.controller;

import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.jackson.JsonUtils;
import desapp.grupo.e.service.user.UserService;
import desapp.grupo.e.webservice.handler.UserControllerHandler;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new UserControllerHandler())
                .build();
    }

    @Test
    public void getUserById() throws Exception {
        Long idUser = 1L;
        User user = UserBuilder.aUser().withId(idUser).withName("Test").withSurname("Test")
                .withEmail("test@test.test").withPassword("Secret")
                .build();
        when(userService.getUserById(any(Long.class))).thenReturn(user);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/user/"+idUser)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        UserDTO newUser = (UserDTO) JsonUtils.fromJson(UserDTO.class, response.getContentAsString());
        Assertions.assertEquals(1L, newUser.getId());
        Assertions.assertEquals("Test", newUser.getName());
        Assertions.assertEquals("Test", newUser.getSurname());
        Assertions.assertEquals("test@test.test", newUser.getEmail());
    }


    @Test
    public void getUserInexistentByIdShouldReturnAResponse404NotFound() throws Exception {
        when(userService.getUserById(any(Long.class))).thenThrow(new ResourceNotFoundException(any(String.class)));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("User not found")));
    }


}
