package com.kotsovskyi.action;

import com.google.gson.JsonObject;
import com.kotsovskyi.utils.BattleFieldStorage;
import com.kotsovskyi.utils.ClientsDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartGameAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String shipsCoordinates = request.getParameter("arrObjects");

        int [][] shipCoordinates = new int[10][5];
        String login = null;

        if(shipsCoordinates != null) {
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(shipsCoordinates);
                JSONObject jsonObj = (JSONObject) obj;

                int[] list = new int[5];
                for (Integer i = 1; i <= 10; i++) {
                    JSONArray jsonArray = (JSONArray) jsonObj.get(i.toString());
                    if (jsonArray != null) {
                        int len = jsonArray.size();
                        for (int j = 0; j < len; j++) {
                            list[j] = Integer.valueOf(jsonArray.get(j).toString());
                            shipCoordinates[i - 1][j] = list[j];
                        }
                    }
                }

                login = (String)jsonObj.get("login");
                System.out.println("login " + login);
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 5; j++) {
                        System.out.println((j+1)+ ") " + shipCoordinates[i][j]);
                    }
                    System.out.println("------------------------------");
                }
                new BattleFieldStorage(login, shipCoordinates);
                ClientsDirectory.getClient(login).setBattleshipsOnField();

                String message = "Please wait when somebody connects to you ...";
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", message);
                response.getWriter().write(jsonObject.toString());

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
            }
        }

        return null;
    }
}
