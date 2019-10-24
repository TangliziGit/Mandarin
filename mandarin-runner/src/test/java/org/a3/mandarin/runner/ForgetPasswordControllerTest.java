package org.a3.mandarin.runner;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ForgetPasswordControllerTest extends MandarinRunnerApplicationTests {
    @Test
    public void testForgetPassword() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/reader/")
                .param("name", "reader3")
                .param("phoneNumber", "18681941700")
                .param("email", "1531959391@qq.com")
                .param("password", "passwd")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/forgetpassword/")
                .param("name", "reader3")
                .param("email", "1531959391@qq.com")
                .session(reader1Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));
    }
}
