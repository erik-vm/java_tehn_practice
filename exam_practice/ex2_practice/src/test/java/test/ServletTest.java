package test;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import runner.Points;
import servlet.MyServlet;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletTest {

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @Points(16)
    public void canHandleInputJson() throws Exception {

        String json = """
           {"key1": [{"key2": 2}, {"key2": 5}, {"key2": 3}]}""";

        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        new MyServlet().doPost(request, response);

        String actual = response.getContentAsString()
                .replaceAll("[\n\r]", "");

        assertThat(actual, equalTo("10"));
    }

    @Test
    @Points(8)
    public void canHandleInputAndOutputJson() throws Exception {

        String json = """
           {"key1": [{"key2": 2}, {"key2": 5}, {"key2": 3}]}""";

        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        new MyServlet().doPut(request, response);

        String expected = """
                [{"key2Values":[2,5,3]}]""";

        String actual = response.getContentAsString()
                .replaceAll(" ", "").replaceAll("[\n\r]", "");

        assertThat(actual, equalTo(expected));
    }
}