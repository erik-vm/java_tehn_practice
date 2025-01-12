package servlet_exercises.transform_keys;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//POST ja PUT päringud aadressile /transform arvutaks sisendi põhjal väärtuse.
//
//        POST päring
//Sisend: {"key1": [{"key2": 2}, {"key2": 5}, {"key2": 3}]}
//Väljund: 10.
//
//Ehk rakendus tagastab kõik "key2" välja väärtused kokku liidetuna (2 + 5 + 3 = 10).
//
//PUT päring
//Sisend: {"key1": [{"key2": 2}, {"key2": 5}, {"key2": 3}]}
//Väljund: [{"key2Values":[2,5,3]}].
//
//Ehk rakendus tagastab kõik "key2" välja väärtused näidatud kujul.
//
//Sisendi ja väljundi töötluseks peate kasutama mingit teeki (nt. ObjectMapper).
//
//NB! Nõuded, mille eiramisel lahendust ei arvestatata.
//     - Teksti käsitsi töötlemine (split, replace, match jne) ei ole lubatud.

@WebServlet("/transform-keys")
public class TransformKeysServlet extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        response.getWriter().print("Hello!");
    }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        Map<String,Object> objectMap = mapper.readValue(request.getInputStream(), Map.class);

        List<Map<String, Integer>> mapList = (List<Map<String, Integer>>) objectMap.get("key1");
        int sum = mapList.stream().mapToInt(map -> map.get("key2")).sum();

        response.setContentType("application/json");
        response.getWriter().write(mapper.writeValueAsString(sum));

    }

    @Override
    public void doPut(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

      Map<String, Object> objectMap = mapper.readValue(request.getInputStream(), Map.class);
      List<Map<String, Integer>> mapList = (List<Map<String, Integer>>) objectMap.get("key1");
      List<Integer> integerList = mapList.stream().map(map -> map.get("key2")).toList();

      String json = mapper.writeValueAsString(List.of(Map.of("key2Values", integerList)));

        response.setContentType("application/json");

        response.getWriter().write(json);


    }

}

