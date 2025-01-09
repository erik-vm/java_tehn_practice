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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.NoPointsIfThisTestFails;
import runner.Points;
import security.SecurityConfig;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
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
    @Points(1)
    public void canNotLogInWithWrongPassword() throws Exception {
        mvc.perform(post("/login")
                .contentType(MediaType.TEXT_PLAIN).content("user=alice&pass=wrong"))
                .andExpect(isRestricted());
    }

    @Test
    @NoPointsIfThisTestFails
    public void canNotLogInWithWrongUser() throws Exception {

        mvc.perform(post("/login")
                        .contentType(MediaType.TEXT_PLAIN).content("user=bob&pass=s3cr3t"))
                .andExpect(isRestricted());
    }

    @Test
    @Points(11)
    public void canLogInWithCorrectCredentials() throws Exception {

        mvc.perform(post("/login")
                .contentType(MediaType.TEXT_PLAIN).content("user=alice&pass=s3cr3t"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Points(3)
    public void canNotLogInWithGetRequest() throws Exception {

        mvc.perform(get("/login")
                .contentType(MediaType.TEXT_PLAIN).content("user=alice&pass=s3cr3t"))
                .andExpect(isRestricted());
    }

    private ResultMatcher isRestricted() {
        return result -> assertThat(result.getResponse().getStatus(),
                    anyOf(is(401), is(403)));
    }

}