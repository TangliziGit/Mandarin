package org.a3.mandarin.runner;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BookControllerTest extends MandarinRunnerApplicationTests {
    @Test
    public void testReserve() throws Exception {
        // reserved, and borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/1")
                .session(reader1Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, but borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/2")
                .session(reader1Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // other one reserved, and borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/1")
                .session(reader2Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, but other one borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/2")
                .session(reader2Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, and not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/3")
                .session(reader2Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));

        // reserved, but not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/3")
                .session(reader2Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // other one reserved, but not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/3")
                .session(reader2Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    public void testBorrow() throws Exception {
        // reserved, and borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/1")
                .param("targetReaderId", "3")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, but borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/2")
                .param("targetReaderId", "3")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // other one reserved, and borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/1")
                .param("targetReaderId", "4")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, but other one borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/2")
                .param("targetReaderId", "4")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, and not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/4")
                .param("targetReaderId", "4")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));

        // reserved, but not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/3")
                .param("targetReaderId", "4")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));

        // other one reserved, but not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/borrow/3")
                .param("targetReaderId", "4")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    public void testReturn() throws Exception {
        // borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/return/4")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));

        // reserved, not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/reserve/5")
                .session(reader2Session))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(true));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/return/5")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));

        // not reserved, and not borrowed
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/book/return/6")
                .session(librarianSession))
                .andDo(print())
                .andExpect(jsonPath("$.success").value(false));
    }
}
