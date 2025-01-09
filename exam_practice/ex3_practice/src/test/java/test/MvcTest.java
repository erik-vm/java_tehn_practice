package test;

import mvc.Init;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.Points;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { Init.class })
public class MvcTest {

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

    @Test
    @Points(15)
    public void calculatesTheSum() throws Exception {
        String input = "[{\"a\": 1}, {\"b\": 2}, {\"c\": 7}]";

        String output = mvc.perform(post("/calculate")
                .contentType(MediaType.APPLICATION_JSON).content(input))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = """
                {"values":[1,2,7],"sum":10}""";

        assertThat(removeWs(output), is(expected));
    }

    private String removeWs(String input) {
        return input.replaceAll("[\n\r ]", "");
    }
}