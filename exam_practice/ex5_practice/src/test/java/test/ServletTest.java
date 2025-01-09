package test;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import runner.Points;
import servlet.MyServlet;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletTest {

    MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @Points(10)
    public void canHandleInputJson() throws Exception {

        String json = """
           {"key1": [{"key2": 2}, {"key2": 5}, {"key2": 3}]}""";

        new MyServlet().doPost(createRequest(json), response);

        String actual = removeWs(response.getContentAsString());

        assertThat(actual, equalTo("10"));
    }

    @Test
    @Points(5)
    public void canHandleInputAndOutputJson() throws Exception {

        String json = """
           {"key1": [{"key2": 2}, {"key2": 5}, {"key2": 3}]}""";

        new MyServlet().doPut(createRequest(json), response);

        String expected = """
                [{"key2Values":[2,5,3]}]""";

        String actual = removeWs(response.getContentAsString());

        assertThat(actual, equalTo(expected));
    }

    private String removeWs(String input) {
        return input.replaceAll("[\n\r ]", "");
    }

    private HttpServletRequest createRequest(String json) {
        var request = new MockHttpServletRequest();

        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        return request;
    }

}