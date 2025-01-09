package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.NoPointsIfThisTestFails;
import runner.Points;
import security.B;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { B.class })
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
                .apply(sharedHttpSession())
                .build();
    }

    @Test
    @NoPointsIfThisTestFails
    public void canNotSeeAnyInfoWithoutLoggingIn() throws Exception {
        mvc.perform(get("/security/users/alice/info"))
                .andExpect(isRestricted());

        mvc.perform(get("/security/users/bob/info"))
                .andExpect(isRestricted());
    }

    @Test
    @Points(5)
    public void bobCanSeeOnlyOwnInfoIfLoggedIn() throws Exception {
        mvc.perform(post("/security/login").header("X-token", "t2"));

        mvc.perform(get("/security/users/bob/info"))
                .andExpect(status().isOk());

        mvc.perform(get("/security/users/alice/info"))
                .andExpect(isRestricted());
    }

    @Test
    @Points(3)
    public void aliceCanSeeAllInfoIfLoggedIn() throws Exception {
        mvc.perform(post("/security/login").header("X-token", "t1"));

        mvc.perform(get("/security/users/bob/info"))
                .andExpect(status().isOk());

        mvc.perform(get("/security/users/alice/info"))
                .andExpect(status().isOk());
    }

    private ResultMatcher isRestricted() {
        return result -> assertThat(result.getResponse().getStatus(),
                    anyOf(is(401), is(403)));
    }
}