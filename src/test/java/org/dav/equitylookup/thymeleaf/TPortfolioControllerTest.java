package org.dav.equitylookup.thymeleaf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TPortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void viewUsers() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/users/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("userName", is("Michal"))
                        )
                )))
                .andDo(print());
    }
}