package servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import model.Post;

@WebListener
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletRegistration.Dynamic mnv = sce.getServletContext().addServlet("mnv", MyNewServlet.class);

        mnv.addMapping("/MyNewServlet");

        
//        This was example to HelloServlet doGet method

//        Post post = new Post();
//
//        post.setTitle("Post from MyListener");
//
//        sce.getServletContext().setAttribute("post", post);
    }
}
