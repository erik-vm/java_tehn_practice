package test;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import runner.NoPointsIfThisTestFails;
import runner.Points;
import servlet.MyServlet;
import util.FileFinder;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static util.TestUtil.removeWhitespace;

public class ServletTest {

    MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @Points(10)
    public void canHandleInputJson() throws Exception {

        String json = """
           {"a":{"b":[3,1,9]}}""";

        new MyServlet().doPost(createRequest(json), response);

        String actual = removeWhitespace(response.getContentAsString());

        assertThat(actual, equalTo("13"));
    }

    @Test
    @Points(5)
    public void canConstructOutputJson() throws Exception {

        String json = """
           {"a":{"b":[3,1,9]}}""";

        new MyServlet().doPut(createRequest(json), response);

        String actual = removeWhitespace(response.getContentAsString());

        String expected = """
                {"result":[3,1,9]}""";

        assertThat(actual, equalTo(expected));
    }

    @Test
    @NoPointsIfThisTestFails
    public void noNewFiles() {
        var files = new FileFinder().getAllFilesFrom(
                        Paths.get("src/main/java/servlet"), List.of("java"))
                .stream()
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted().toList();

        assertThat(files, contains(
                "A.java", "B.java", "C.java", "MyServlet.java"));
    }

    private HttpServletRequest createRequest(String json) {
        var request = new MockHttpServletRequest();

        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        return request;
    }

}