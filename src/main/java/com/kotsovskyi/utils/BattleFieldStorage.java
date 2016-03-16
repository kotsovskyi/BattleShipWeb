package com.kotsovskyi.utils;

import com.kotsovskyi.domain.BattleField;

import java.util.HashMap;
import java.util.Map;

public class BattleFieldStorage {

    private static Map<String, BattleField> coordinates = new HashMap <String, BattleField>();

    public BattleFieldStorage(String key, int[][] coordinates){
        this.coordinates.put(key.toString(), new BattleField(coordinates));
    }

    public static int[][] getCoordinates(String key) {
        return coordinates.get(key).getShipCoordinates();
    }

    public static boolean [] getHits(String key) {
        return coordinates.get(key).getHits();
    }

    public static BattleField getBattleField(String key) {
        return coordinates.get(key);
    }

}
