package app.controller;

import app.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;


@ExtendWith(SpringExtension.class)
@WebMvcTest
@ComponentScan({"app.controller", "service.login", "service.persistence"})
@ContextConfiguration(classes = { Application.class })
public class LoginControllerTest {

    /*

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginController loginController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        loginService = mock(LoginService.class);
        loginController = new LoginController(loginService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void whenPostRequestToSignUpAndIsValidUser_thenReturnCorrectResponse() throws Exception {
        String user = "{\"name\": \"test\", \"surname\" : \"test\", \"password\" : \"secret\",  \"email\" : \"test@test.com\" }";
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(user)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToSignUpAndTheyAreMissingFields_thenReturnBadRequesResponseWithFieldsMissing() throws Exception {
        String user = "{\"name\": \"\", \"email\" : \"bob@domain.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(user)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Is.is("Name is mandatory")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("Password is mandatory")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenPostRequestToAnyEndpointAndJsonIsInvalid_thenReturnBadRequest() throws Exception {
        String user = "{\"name\": \"\" \"email\" : \"bob@domain.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .content(user)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("Json Inconsistent")))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

*/
}
