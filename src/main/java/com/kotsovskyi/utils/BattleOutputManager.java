package com.kotsovskyi.utils;


import com.kotsovskyi.domain.BattleField;
import com.kotsovskyi.domain.Shot;
import com.kotsovskyi.domain.User;

public class BattleOutputManager {

    // todo replace this shit with method toString
    public void printBattleField(boolean isThisAttacker, BattleField battleField) {
        BattleShipHelper helper = new BattleShipHelper();
        Shot[] shots = battleField.getShots();
        int k = 0;

        for (int i = 0; i < 10; i++) {
            if (i == 0)
                System.out.print("    ");
            System.out.print(i + ".\t");
        }

        System.out.println("\n   ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + ".  ");
            for (int j = 0; j < 10; j++) {
                k = battleField.getIndexOfShot(j, i);
                if(((battleField.getIndexOfShip(j, i) != -1) && shots[k].getHit() && helper.isThisDestroyedShip(battleField.getIndexOfShip(j, i), battleField)) ||
                        (isThisAttacker && (battleField.getIndexOfShip(j, i) != -1) && shots[k].getHit())) {
                    System.out.print("\u2588\t");
                } else if(battleField.getIndexOfShip(j, i) != -1){
                    if(shots[k].getHit() || isThisAttacker) {
                        System.out.print("\u2592\t");
                    } else {
                        System.out.print("\u25AB\t");
                    }
                } else if((shots[k].getHit()) &&  (battleField.getIndexOfShip(j, i) == -1)) {
                    System.out.print("\u25AA\t");
                } else {
                    System.out.print("\u25AB\t");
                }
            }
            System.out.println("\n   ⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        }
    }

    public String printCongratulation(User user){
        return user.getName() + ", Вітаємо з перемогою !!!!!!!!!!!!!!!!";
    }

    public String printWarningInvalidInput() {
        return "Неправильний ввід !!!\n" + "Ввведіть ще раз координати: ";
    }

    public String printWarningTargetWasFired () {
        return "Неможливо вистрілити в дану точку !!!\n" + "Введіть інші координати";
    }

}
