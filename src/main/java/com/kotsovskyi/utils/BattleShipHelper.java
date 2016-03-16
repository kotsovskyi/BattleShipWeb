package com.kotsovskyi.utils;

import com.kotsovskyi.domain.BattleField;
import com.kotsovskyi.domain.Ship;
import com.kotsovskyi.domain.Shot;

public class BattleShipHelper {

    public boolean isCorrectInputCoordinatesTarget(String coordinatesString) {
        coordinatesString = coordinatesString.replaceAll(" ", "");
        if (coordinatesString.length() != 3) {
            return false;
        }
        if (Character.isDigit(coordinatesString.charAt(0)) && Character.isDigit(coordinatesString.charAt(2)) &&
                (coordinatesString.charAt(1) == ';')) {
            return true;
        }
        return false;
    }

    public int[] getParsingCoordinates(String coordinatesString) {
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

    public boolean isTargetEmpty(BattleField battleField, int indexOfShot) {
        Shot[] shots = battleField.getShots();
        if (shots[indexOfShot].getHit()) {
            return false;
        }
        return true;
    }

    public boolean isThisCoordinateOfShip(int x, int y, BattleField battleField) {
        BattleShipHelper helper = new BattleShipHelper();
        if(battleField.getIndexOfShip(x, y) != -1) {
            return true;
        }
        return false;
    }

    public void makeShot(int x, int y, BattleField battleField) {
        Shot [] shots = battleField.getShots();
        Ship [] ships = battleField.getShips();
        BattleShipHelper helper = new BattleShipHelper();
        int k = battleField.getIndexOfShot(x,y);
        int index;

        shots[k].setHit(x, y);

        if(helper.isThisCoordinateOfShip(x,y,battleField)) {
            index = battleField.getIndexOfShip(x,y);
            ships[index].setWound();
        }
    }

    public boolean isThisDestroyedShip(int indexOfShip, BattleField battleField) {
        Ship [] ships = battleField.getShips();

        int length = ships[indexOfShip].getLength();
        int wounds = ships[indexOfShip].getWounds();

        if (wounds == length) {
            return true;
        }
        return false;
    }

    public void blocSpaceNearDestroyedShip(int indexOfShip, BattleField battleField) {
        Ship [] ships = battleField.getShips();
        Shot [] shots = battleField.getShots();

        int xHead = ships[indexOfShip].getXHead();
        int yHead = ships[indexOfShip].getYHead();
        int xTail = ships[indexOfShip].getXTail();

        int length = ships[indexOfShip].getLength();
        int indexOfShot;

        for (int k = 0; k < length; k++) {

            for (int i = yHead - 1; i <= yHead + 1; i++) {
                if(i < 0) {
                    i++;
                } else if (i == 10) {
                    break;
                }
                for (int j = xHead - 1; j <=xHead + 1; j++) {
                    if(j < 0) {
                        j++;
                    } else if(j == 10) {
                        break;
                    }
                    indexOfShot = battleField.getIndexOfShot(j, i);
                    if(!shots[indexOfShot].getHit()) {
                        shots[indexOfShot].setHit(j, i);
                    }
                }
            }

            if (xHead == xTail) {
                yHead++;
            } else {
                xHead++;
            }
        }
    }

    public boolean isTheEndOfGame(BattleField battleField) {
        int k = 0;
        for (int i = 0; i < 10; i++) {
            if (isThisDestroyedShip(i, battleField)) {
                k++;
            }
        }
        if (k == 10) {
            return true;
        }
        return false;
    }


}
