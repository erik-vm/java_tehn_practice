package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.Points;
import security.SecurityConfig;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SecurityConfig.class })
public class SecurityTest {

    private WebApplicationContext wac;

    @Autowired
    public void setWac(WebApplicationContext wac) {
        this.wac = wac;
    }

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void aliceCanSeeBothPages() throws Exception {
        mvc.perform(get("/security/admin.html?username=alice"))
                .andExpect(status().is(404));

        // 404 - these tests do not see html files.
        // Code 404 means that the request was passed through the filter.

        mvc.perform(get("/security/user.html?username=alice"))
                .andExpect(status().is(404));
    }

    @Test
    @Points(2)
    public void bobCanSeeUserPage() throws Exception {
        mvc.perform(get("/security/user.html?username=bob"))
                .andExpect(status().is(404));

        mvc.perform(get("/security/admin.html?username=bob"))
                .andExpect(isRestricted());
    }

    @Test
    @Points(2)
    public void carolCanNotSeeEitherPage() throws Exception {
        mvc.perform(get("/security/user.html?username=carol"))
                .andExpect(isRestricted());

        mvc.perform(get("/security/admin.html?username=carol"))
                .andExpect(isRestricted());
    }

    @Test
    @Points(2)
    public void restrictedAccessCodeIs444() throws Exception {
        mvc.perform(get("/security/admin.html?username=carol"))
                .andExpect(status().is(444));
    }

    @Test
    @Points(2)
    public void restrictedAccessContentIsError() throws Exception {
        MvcResult result = mvc.perform(get("/security/admin.html?username=carol"))
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content, equalTo("Error!"));
    }


    private ResultMatcher isRestricted() {
        return result -> assertThat(result.getResponse().getStatus(),
                    anyOf(is(401), is(403), is(444)));
    }
}