package com.kotsovskyi.action;

import com.kotsovskyi.domain.BattleField;
import com.kotsovskyi.server.Client;
import com.kotsovskyi.server.FightRoom;
import com.kotsovskyi.utils.BattleFieldStorage;
import com.kotsovskyi.utils.BattleShipHelper;
import com.kotsovskyi.utils.FightRoomDirectory;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeShotAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, JSONException {
        String coordinates = request.getParameter("coordinatesOfShot");

        if(coordinates != null) {
            String login = coordinates.substring(3, coordinates.length()-1);
            FightRoom fightRoom = FightRoomDirectory.getFightRoom(login);
            Client attacker, protector;

            if(fightRoom.getCl1().isAttacker()) {
                attacker = fightRoom.getCl1();
                protector = fightRoom.getCl2();
            } else {
                attacker = fightRoom.getCl2();
                protector = fightRoom.getCl1();
            }

            JSONObject jsonObject = new JSONObject();
            String message;

            if(attacker.getName().equals(login)) {
                System.out.println("Attacker login >>>> " + login);
                coordinates = coordinates.substring(1,3);
                int [] coordinatesOfShot = getParsingCoordinates(coordinates);
                System.out.println("CoordinatesOfShot >> " + coordinates);

                BattleField protectorBattlefield = BattleFieldStorage.getBattleField(protector.getName());
                BattleShipHelper helper = new BattleShipHelper();

                int x = coordinatesOfShot[0];
                int y = coordinatesOfShot[1];
                int indexOfShot = protectorBattlefield.getIndexOfShot(x, y);

                boolean isDestroyedShip = false;
                boolean isAttackerYet = false;

                if(helper.isTargetEmpty(protectorBattlefield, indexOfShot)) {
                    // якщо є попадання в корабель противника
                    if (helper.isThisCoordinateOfShip(x, y, protectorBattlefield)) {
                        int indexOfShip = protectorBattlefield.getIndexOfShip(x,y);
                        isAttackerYet = true;
                        helper.makeShot(x, y, protectorBattlefield);

                        if (helper.isThisDestroyedShip(indexOfShip, protectorBattlefield)) {
                            helper.blocSpaceNearDestroyedShip(indexOfShip, protectorBattlefield);
                            isDestroyedShip = true;
                            if(helper.isTheEndOfGame(protectorBattlefield)) {
                                jsonObject.put("winner", true);
                            }
                        }
                        message = "You are lucky... Try one more time";
                    } else {
                        // мимо
                        helper.makeShot(x, y, protectorBattlefield);
                        message = "You do not hit the target. Wait for the opponent's shot !";
                        attacker.setAttacker(false);
                        protector.setAttacker(true);
                    }

                    jsonObject.put("attacker", isAttackerYet);
                    jsonObject.put("isDestroyedShip", isDestroyedShip);
                    jsonObject.put("message", message);
                    response.getWriter().write(jsonObject.toString());
                }
            }
        }
        return null;
    }

    private int[] getParsingCoordinates(String coordinatesString) {
        int[] coordinates = new int[2];
        String s;
        int k = 0;

        for (int i = 0; i < coordinatesString.length(); i++) {
            if (Character.isDigit(coordinatesString.charAt(i))) {
                s = Character.toString(coordinatesString.charAt(i));
                coordinates[k] = Integer.parseInt(s);
                k++;
            }
        }
        return coordinates;
    }
}
