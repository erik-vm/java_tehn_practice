package servlet_exercises.transform_dates;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

//Postitades aadressile /transform Json-i kujul
//
//  [ { "date": "17.12", "count": 5 },
//          { "date": "21.12", "count": 8 },
//          { "date": "18.12" },
//          { "date": "18.12", "count": 1 },
//          { "date": "23.12", "count": 3 },
//          { "date": "21.12", "count": 3 } ]
//
//Väljastab rakendus kõik count võtme väärtused kokku liidetuna (antud juhul 20).
//
//PUT pärning samale aadressile samade andmetega väljastab need kuupäevad,
//mille "count" on üle ühe alloleval kujul.
//
//        {"result":[{"data":"17.12"},{"data":"21.12"},{"data":"23.12"}]}
//
//Korduvad kuupäevad tuleb eemaldada ja esialgne järjekord peab säilima.

@WebServlet("/transform-date")
public class TransformDateServlet extends HttpServlet {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonNode jsonArray = mapper.readTree(req.getInputStream());

        int totalCount = 0;

        for (JsonNode node : jsonArray) {
            if (node.has("count")) {
                totalCount += node.get("count").asInt();
            }
        }

        resp.setContentType("application/json");
        resp.getWriter().write(mapper.writeValueAsString(totalCount));

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonNode jsonArray = mapper.readTree(req.getInputStream());
        HashSet<String> uniqueDates = new HashSet<>();

        for (JsonNode node : jsonArray) {
            if (node.has("count")) {
                int count = node.get("count").asInt();
                if (count > 1) {
                    String date = node.get("date").asText();
                    uniqueDates.add(date);
                }
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("{\"result\":[");

        Iterator<String> iterator = uniqueDates.iterator();
        while (iterator.hasNext()) {
            String date = iterator.next();
            result.append(String.format("{\"data\":\"%s\"}", date));
            if (iterator.hasNext()) {
                result.append(",");
            }
        }
        result.append("]}");

        resp.setContentType("application/json");
        resp.getWriter().write(result.toString());
    }
}
