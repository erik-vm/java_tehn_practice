package servlet;

import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        Post post = (Post) getServletContext().getAttribute("post");

        response.getWriter().print(post.getTitle());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String input = req.getReader().lines().collect(Collectors.joining("\n"));

        Post post = new ObjectMapper().readValue(input, Post.class);

        post.setTitle("New title!");

        String output = new ObjectMapper().writeValueAsString(post);

        resp.addHeader("Content-Type", "application/json");

        resp.getWriter().print(output);
    }
}
