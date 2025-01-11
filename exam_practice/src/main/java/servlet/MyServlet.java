package servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/calculate")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.getWriter().print("Hello!");
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> inputList = mapper.readValue(request.getInputStream(), new TypeReference<List<Map<String, Object>>>() {});

        int sum = 0;
        StringBuilder letters = new StringBuilder();

        for (Map<String, Object> item : inputList){
            for (Map.Entry<String, Object> entry : item.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Integer){
                    sum += (Integer) value;
                } else if (value instanceof String) {
                    letters.append(value);
                }
            }
        }

        A output = new A(sum, letters.toString());

        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), output);

    }
}

