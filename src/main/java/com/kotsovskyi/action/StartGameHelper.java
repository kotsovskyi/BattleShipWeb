package com.kotsovskyi.action;

import com.kotsovskyi.server.FightRoom;
import com.kotsovskyi.utils.FightRoomDirectory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StartGameHelper implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        String loginJson = request.getParameter("login");

        if(loginJson != null) {
            String login = (String)JSONValue.parse(loginJson);

            FightRoom fightRoom = FightRoomDirectory.getFightRoom(login);
            JSONObject jsonObject = new JSONObject();

            if (fightRoom != null && fightRoom.getCl1().isBattleshipsOnField() && fightRoom.getCl2().isBattleshipsOnField()) {
                if (fightRoom.getCl1().getName().equals(login)) {
                    fightRoom.getCl1().setAttacker(true);
                    jsonObject.put("attacker", true);
                } else {
                    jsonObject.put("attacker", false);
                }
                response.getWriter().write(jsonObject.toString());
            }
        }

        return null;
    }
}
