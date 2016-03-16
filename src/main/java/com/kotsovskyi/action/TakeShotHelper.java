package com.kotsovskyi.action;

import com.kotsovskyi.server.FightRoom;
import com.kotsovskyi.utils.BattleFieldStorage;
import com.kotsovskyi.utils.BattleShipHelper;
import com.kotsovskyi.utils.FightRoomDirectory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeShotHelper implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException, JSONException {
        String loginJson = request.getParameter("login");
        JSONObject jsonObject = new JSONObject();
        BattleShipHelper helper = new BattleShipHelper();

        if(loginJson != null) {
            String login = (String) JSONValue.parse(loginJson);
            FightRoom fightRoom = FightRoomDirectory.getFightRoom(login);
            jsonObject.put("attacker", false);

            if(fightRoom.getCl2().isAttacker() && fightRoom.getCl2().getName().equals(login)) {
                jsonObject.put("attacker", true);
            } else if(fightRoom.getCl1().isAttacker() && fightRoom.getCl1().getName().equals(login)) {
                jsonObject.put("attacker", true);
            }

            if(helper.isTheEndOfGame(BattleFieldStorage.getBattleField(login))) {
                jsonObject.put("loser", true);
            }

            jsonObject.put("hits", BattleFieldStorage.getHits(login));
            response.getWriter().write(jsonObject.toString());
        }
        return "/jsp/battlefields.jsp";
    }
}
