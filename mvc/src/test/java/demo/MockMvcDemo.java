package demo;

import demo.config.MvcConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MvcConfig.class })
@ActiveProfiles("test")
public class MockMvcDemo {

    @Autowired
    private WebApplicationContext ctx;

    @Test
    public void controllerTest() {
        var controller = ctx.getBean(PostController.class);

        controller.savePost(new Post(1L, "Title", "Text"));
    }

    @Test
    public void mockMvcExampleTest() throws Exception {

        MockMvc mvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        String json = "{ \"id\": 3, \"title\": \"hello\" }";

        MvcResult result = mvc.perform(
                post("/posts")
                        .content(json)
                        .header("Content-type", "application/json"))
                .andReturn();

        var response = result.getResponse();

        System.out.println(response.getStatus());
        System.out.println(response.getHeader("Content-type"));
        System.out.println(response.getContentAsString());
   }

}