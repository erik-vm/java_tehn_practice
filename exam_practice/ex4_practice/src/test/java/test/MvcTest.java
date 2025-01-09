package test;

import mvc.Init;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.NoPointsIfThisTestFails;
import runner.Points;
import util.FileFinder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static util.TestUtil.getChecksum;
import static util.TestUtil.removeWhitespace;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { Init.class })
public class MvcTest {

    @Test
    @NoPointsIfThisTestFails
    public void noErrorOnValidId() throws Exception {
        assertThat(doGet("/orders/1"), equalTo("some order data"));
    }

    @Test
    @NoPointsIfThisTestFails
    public void noErrorOnValidUsername() throws Exception {
        assertThat(doGet("/users/alice"), equalTo("some user info"));
    }

    @Test(expected = Exception.class)
    @NoPointsIfThisTestFails
    public void noJsonForProgrammingErrorExceptions() throws Exception {
        doGet("/customers/1");
    }

    @Test
    @Points(12)
    public void errorOnInvalidId() throws Exception {
        String actual = doGet("/orders/-1");

        String expected = """
                {"error":"not_found"}""";

        assertThat(removeWhitespace(actual), equalTo(expected));
    }

    @Test
    @Points(3)
    public void errorOnRestrictedUsername() throws Exception {
        String actual = doGet("/users/admin");

        String expected = """
                {"error":"prohibited"}""";

        assertThat(removeWhitespace(actual), equalTo(expected));
    }

    @Test
    @NoPointsIfThisTestFails
    public void noNewFiles() {
        var files = new FileFinder().getAllFilesFrom(
                Paths.get("src/main/java/mvc"), List.of("java"))
                .stream()
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted().toList();

        assertThat(files, contains(
                "A.java", "B.java", "C.java", "Ctrl.java", "Dao.java", "Init.java"));
    }

    @Test
    @NoPointsIfThisTestFails
    public void noChangesInForbiddenFiles() {
        assertThat(getChecksum("mvc/Ctrl.java"), equalTo(845837221));

        assertThat(getChecksum("mvc/Dao.java"), equalTo(1548091403));
    }

    private String doGet(String url) throws Exception {
        return mvc.perform(get(url))
                .andReturn()
                .getResponse().getContentAsString();
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
                .build();
    }



}