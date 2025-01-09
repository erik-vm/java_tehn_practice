package test;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import runner.Points;
import servlet.MyServlet;
import util.FileUtil;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletTest {

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    @Points(12)
    public void canExtractInfoFromInputJson() throws Exception {

        String input = FileUtil.readFileFromClasspath("input.json");

        request.setContent(input.getBytes(StandardCharsets.UTF_8));

        new MyServlet().doPost(request, response);

        String actual = removeWs(response.getContentAsString());

        assertThat(actual, is("20"));
    }


    @Test
    @Points(8)
    public void canComposeOutputJson() throws Exception {

        String input = FileUtil.readFileFromClasspath("input.json");

        request.setContent(input.getBytes(StandardCharsets.UTF_8));

        new MyServlet().doPut(request, response);

        String actual = removeWs(response.getContentAsString());

        String expected = removeWs(
                FileUtil.readFileFromClasspath("expected.json"));

        assertThat(actual, equalTo(expected));
    }

    private String removeWs(String input) {
        return input.replaceAll("[\n\r ]", "");
    }

}