package pl.edu.wat.aplikacjatreningowa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mock;

    @Test
    void doesLoginWork() throws Exception {
        MvcResult mvcResult = mock.perform(
                formLogin("/logowanie")
                .user("login", "Marcus")
                .password("password", "useruseruser")
        ).andExpect(authenticated()).andReturn();
    }

    @Test
    @WithMockUser
    void doesLogoutWork() throws Exception {
        mock.perform(get("/training"))
                .andExpect(authenticated()).andReturn();

        mock.perform(logout("/logout"))
                .andExpect(unauthenticated()).andReturn();
    }

}
