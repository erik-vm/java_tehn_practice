package test;

import mvc.Ctrl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import runner.Points;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { Ctrl.class })
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
    @Points(10)
    public void getRequestReturnsStatus200() throws Exception {
        MvcResult result = mvc.perform(get("/echo")).andReturn();

        assertThat(result.getResponse().getStatus(), equalTo(200));
    }

    @Test
    @Points(5)
    public void postRequestEchoesInputBack() throws Exception {
        String inputJson = """
                {"a":1,"b":2,"c":3}""";

        MvcResult result = mvc.perform(post("/echo")
                .contentType(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        String output = result.getResponse().getContentAsString()
                .replaceAll(" ", "").replaceAll("[\n\r]", "");;

        assertThat(output, equalTo(inputJson));
    }

}