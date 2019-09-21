package org.a3.mandarin.runner;

import org.a3.mandarin.back.MandarinBackApplication;
import org.a3.mandarin.common.MandarinCommonApplication;
import org.a3.mandarin.common.dao.repository.BookRepository;
import org.a3.mandarin.common.dao.repository.BorrowingFineHistoryRepository;
import org.a3.mandarin.common.dao.repository.BorrowingHistoryRepository;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.front.MandarinFrontApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        MandarinRunnerApplication.class,
        MandarinCommonApplication.class,
        MandarinBackApplication.class,
        MandarinFrontApplication.class
})
public class MandarinRunnerApplicationTests {
    MockMvc mockMvc;
    MockHttpSession reader1Session, reader2Session, librarianSession, adminSessoin;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Resource
    protected UserRepository userRepository;

    @Resource
    protected BookRepository bookRepository;

    @Resource
    protected BorrowingHistoryRepository borrowingHistoryRepository;

    @Resource
    protected BorrowingFineHistoryRepository borrowingFineHistoryRepository;

    @Before
    public void setup() throws Exception{
        (new Initializer(webApplicationContext)).init();

        mockMvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        reader1Session =new MockHttpSession();
        reader2Session =new MockHttpSession();
        librarianSession=new MockHttpSession();
        adminSessoin=new MockHttpSession();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/token")
                .param("name", "reader1")
                .param("password", "passwd")
                .session(reader1Session)
        ).andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/token")
                .param("name", "reader2")
                .param("password", "passwd")
                .session(reader2Session)
        ).andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/token")
                .param("name", "librarian")
                .param("password", "passwd")
                .session(librarianSession)
        ).andExpect(jsonPath("$.success").value(true));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/token")
                .param("name", "admin")
                .param("password", "passwd")
                .session(adminSessoin)
        ).andExpect(jsonPath("$.success").value(true));

    }
}
