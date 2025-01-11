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
    @Points(12)
    public void transformsJsonWithNumbersOnly() throws Exception {

        String json = """
           [{"key1": 3}, {"key3": 5}, {"key1": 1}, {"key3": 3}]""";

        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        new MyServlet().doPost(request, response);

        String expected = """
                {"sum":12,"letters":""}""";

        String actual = response.getContentAsString()
                .replaceAll(" ", "").replaceAll("[\n\r]", "");

        assertThat(actual, equalTo(expected));
    }

    @Test
    @Points(12)
    public void transformsJsonNumbersAndLetters() throws Exception {

        String json = """
           [{"key1": 3}, {"key2": "a"}, {"key3": 5},
            {"key2": "b"}, {"key1": 1}]""";

        request.setContent(json.getBytes(StandardCharsets.UTF_8));

        new MyServlet().doPost(request, response);

        String expected = """
                {"sum":9,"letters":"ab"}""";

        String actual = response.getContentAsString()
                .replaceAll(" ", "").replaceAll("[\n\r]", "");

        assertThat(actual, equalTo(expected));
    }
}