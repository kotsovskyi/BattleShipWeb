package com.kotsovskyi.utils;

import com.kotsovskyi.server.FightRoom;

import java.util.HashMap;
import java.util.Map;

public class FightRoomDirectory {
    private static Map<String, FightRoom> fightRoomMap = new HashMap<String, FightRoom>();

    public static void addFightRoom(String login, FightRoom fightRoom) {
        fightRoomMap.put(login, fightRoom);
    }

    public static FightRoom getFightRoom(String login) {
        FightRoom fightRoom = fightRoomMap.get(login);
        return fightRoom;
    }
}
