package test;

import app.PostController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import conf.MvcConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import validation.ValidationError;
import validation.ValidationErrors;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {MvcConfig.class})
public class MockMvcTests {

    private WebApplicationContext ctx;

    @Autowired
    public void setCtx(WebApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Test
    public void controllerTest() {
        var controller = ctx.getBean(PostController.class);

        System.out.println(controller.getPosts());
    }

    @Test
    public void mockMvcExampleTest() throws UnsupportedEncodingException {
        String json = "{ \"id\": 3, \"title\": \"hello\"  }";

        MockHttpServletResponse response = simulatePost("/sample", json);

        System.out.println(response.getStatus());
        System.out.println(response.getContentAsString());
    }

    @Test
    public void showErrorsOnInvalidInput() throws Exception {

        String json = "{}";

        MockHttpServletResponse response = simulatePost("/posts", json);

        var errors = getErrorCodes(response.getContentAsString());

        assertThat(response.getStatus(), is(400));

        assertThat(errors, containsInAnyOrder(
                "NotNull.post.title",
                "NotNull.post.text"));
    }

    @Test
    public void noErrorsOnValidData() {

        String json = "{\"text\": \"aa\", \"title\":\"bb\"}";

        MockHttpServletResponse response = simulatePost("/posts", json);

        assertThat(response.getStatus(), is(201));
    }

    private MockHttpServletResponse simulatePost(String url, String input) {
        MockMvc mvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        try {
            return mvc.perform(post(url)
                            .content(input)
                            .header("Content-type", "application/json"))
                    .andReturn()
                    .getResponse();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getErrorCodes(String json) {
        try {
            ValidationErrors errors = new ObjectMapper().readValue(json, ValidationErrors.class);

            return errors.getErrors().stream().map(ValidationError::getCode).toList();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}