package pl.edu.wat.aplikacjatreningowa.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.edu.wat.aplikacjatreningowa.models.UserAccount;
import pl.edu.wat.aplikacjatreningowa.models.front.RaportInfo;
import pl.edu.wat.aplikacjatreningowa.service.RaportService;
import pl.edu.wat.aplikacjatreningowa.service.UserService;

import java.io.PrintWriter;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@WebMvcTest(RaportController.class)
@SpringBootTest
@AutoConfigureMockMvc
class RaportControllerTest {

    @MockBean
    private RaportService raportService;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void schoudlReturnTrainingRaport(Principal principal) throws Exception {


        when(raportService.getTrainingRaportInfo(1L, userService.getUserAccount(principal.getName())))
                .thenReturn(new RaportInfo("training1",
                        LocalDateTime.now(),
                        60L,
                        6,
                        400,
                        500,
                        null
                ));

        this.mockMvc
                .perform(get("/raport/trainingId=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/raport/raport"))
                .andExpect(model().attributeExists("raportInfo"));



    }
    @Test
    void should() throws Exception {
        when(raportService.getTrainingRaportInfo(1L, userService.getUserAccount("Marcus")))
                .thenReturn(new RaportInfo("training1",
                        LocalDateTime.now(),
                        60L,
                        6,
                        400,
                        500,
                        null
                ));
        mockMvc.perform(get("/raport/trainingId=3").with(user("Marcus")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    void shouldd() throws Exception {

        mockMvc.perform(get("/raport/trainingId=3").with(user("Marcus")))
                .andExpect(MockMvcResultMatchers.view().name("/raport/raport"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("raportInfo"));
    }




}
