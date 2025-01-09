package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
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
import util.FileFinder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtil.getChecksum;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { SecurityConfig.class })
public class SecurityTest {

    @Test
    @Points(3)
    public void showsCorrectStatusAndHeader() throws Exception {
        MockHttpServletResponse response = mvc.perform(get("/admin"))
                .andReturn()
                .getResponse();

        String header = response.getHeader("WWW-Authenticate");

        assertThat(header, is("Basic"));
        assertThat(response.getStatus(), is(401));
    }

    @Test
    @NoPointsIfThisTestFails
    public void canNotLogInWithWrongCredentials() throws Exception {
        mvc.perform(get("/admin").with(httpBasic("user","password")))
                .andExpect(isRestricted());
    }

    @Test
    @Points(12)
    public void canLogInWithCorrectCredentials() throws Exception {
        mvc.perform(get("/admin").with(httpBasic("alice","123")))
                .andExpect(status().isOk());
    }

    @Test
    @NoPointsIfThisTestFails
    public void noNewFiles() {
        var files = new FileFinder().getAllFilesFrom(
                        Paths.get("src/main/java/security"), List.of("java"))
                .stream()
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted().toList();

        assertThat(files.size(), is(4));
    }

    @Test
    @NoPointsIfThisTestFails
    public void noChangesInForbiddenFiles() {
        assertThat(getChecksum("security/SecurityConfig.java"), equalTo(-1992553007));
    }

    private ResultMatcher isRestricted() {
        return result -> assertThat(result.getResponse().getStatus(),
                anyOf(is(401), is(403)));
    }

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

}