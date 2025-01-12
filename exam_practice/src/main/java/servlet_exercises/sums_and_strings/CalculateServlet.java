package servlet_exercises.sums_and_strings;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.el.MapELResolver;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//POST päring aadressile /calculate arvutaks sisendi põhjal väärtuse.
//
//        Sisend: [{"key1": 3}, {"key2": "a"}, {"key3": 5},
//        {"key2": "b"}, {"key1": 1}]
//Väljund: {"sum": 9, "letters": "ab"}.
//
//Ehk rakendus tagastab võtmete key1, key2 ja key3 väärtused kokku liidetuna.
//Kui väärtus on number, siis toimub aritmeetiline liitmine (1 + 2 = 3),
//kui väärtus on sõne, siis toimub sõnede liitmine ("a" + "b" = "ab").
//
//Sisendi ja väljundi töötluseks peate kasutama mingit teeki (nt. ObjectMapper).
//
//Kasuks võib tulla konstruktsioon
//Person p = new ObjectMapper().readValue(inputJson, new TypeReference<>() {});
//
//NB! Nõuded, mille eiramisel lahendust ei arvestatata.
//     - Tekstist käsitsi töötlemine (split, replace, match jne) ei ole lubatud.
//        - Programmis ei tohi olla ühtegi cast-i.

@WebServlet("/calculate")
public class CalculateServlet extends HttpServlet {

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

        ServletInputStream inputStream = request.getInputStream();

        List<Map<String, Object>> inputList = mapper.readValue(inputStream, new TypeReference<List<Map<String, Object>>>() {
        });

        int sum = 0;
       StringBuilder letters = new StringBuilder();

       for (Map<String, Object> item : inputList){
           for (Map.Entry entry : item.entrySet()){
               if (entry.getValue() instanceof Integer){
                   sum += (Integer) entry.getValue();
               }else if (entry.getValue() instanceof String){
                   letters.append(entry.getValue());
               }
           }
       }

       String output = String.format("{\"sum\": %s, \"letters\": \"%s\"}", sum, letters);
       response.setContentType("application/json");
       response.getWriter().write(output);

    }
}






