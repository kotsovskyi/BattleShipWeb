package utils;

import com.kotsovskyi.domain.BattleField;
import com.kotsovskyi.domain.Ship;
import com.kotsovskyi.domain.Shot;
import com.kotsovskyi.utils.BattleShipHelper;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BattleShipHelperTest {
    private static int [][] coordinates1 = {
            {2, 1, 2, 4, 4},
            {5, 8, 7, 8, 3},
            {0, 6, 0, 8, 3},
            {8, 1, 9, 1, 2},
            {5, 0, 5, 1, 2},
            {2, 9, 3, 9, 2},
            {9, 4, 9, 4, 1},
            {5, 4, 5, 4, 1},
            {3, 7, 3, 7, 1},
            {0, 3, 0, 3, 1},};

    @Test
    public void testIsCorrectInputCoordinatesTarget() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        assertTrue(helper.isCorrectInputCoordinatesTarget("1;5"));

        assertFalse(helper.isCorrectInputCoordinatesTarget(""));
        assertFalse(helper.isCorrectInputCoordinatesTarget("1"));
        assertFalse(helper.isCorrectInputCoordinatesTarget("1;"));
        assertFalse(helper.isCorrectInputCoordinatesTarget(";5"));
        assertFalse(helper.isCorrectInputCoordinatesTarget("15"));
        assertFalse(helper.isCorrectInputCoordinatesTarget(";"));

        assertFalse(helper.isCorrectInputCoordinatesTarget("-1;5"));
        assertFalse(helper.isCorrectInputCoordinatesTarget("1;-5"));
        assertFalse(helper.isCorrectInputCoordinatesTarget("-1;-5"));
    }

    @Test
    public void testGetParsingCoordinates() throws Exception {
        //given
        BattleShipHelper helper = new BattleShipHelper();

        //when
        int [] coordinates = helper.getParsingCoordinates("1;5");

        //then
        assertTrue((coordinates[0] == 1) && (coordinates[1] == 5));
    }

    @Test
    public void testIsTargetEmpty() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        BattleField battleField = new BattleField(coordinates1);
        int indexOfShot = 0;
        assertTrue(helper.isTargetEmpty(battleField, indexOfShot));

        helper.makeShot(5, 5, battleField);
        indexOfShot = battleField.getIndexOfShot(5, 5);
        assertFalse(helper.isTargetEmpty(battleField, indexOfShot));
    }

    @Test
    public void testIsThisCoordinateOfShip() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        BattleField battleField = new BattleField(coordinates1);
        assertTrue(helper.isThisCoordinateOfShip(2, 1, battleField));
        assertTrue(helper.isThisCoordinateOfShip(2, 3, battleField));
        assertTrue(helper.isThisCoordinateOfShip(3, 7, battleField));

        assertFalse(helper.isThisCoordinateOfShip(6, 6, battleField));
    }

    @Test
    public void testMakeShot() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        BattleField battleField = new BattleField(coordinates1);
        Shot [] shots = battleField.getShots();

        helper.makeShot(0, 0, battleField);
        int indexOfShot = battleField.getIndexOfShot(0, 0);
        assertTrue(shots[indexOfShot].getHit());

        indexOfShot = battleField.getIndexOfShot(5, 5);
        assertFalse(shots[indexOfShot].getHit());
        helper.makeShot(5, 5, battleField);
        assertTrue(shots[indexOfShot].getHit());
    }

    @Test
    public void testIsThisDestroyedShip() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        BattleField battleField = new BattleField(coordinates1);
        // однопарусник
        int indexOfShip = battleField.getIndexOfShip(3, 7);
        assertFalse(helper.isThisDestroyedShip(indexOfShip, battleField));
        helper.makeShot(3, 7, battleField);
        assertTrue(helper.isThisDestroyedShip(indexOfShip, battleField));
        // двопарусник
        helper.makeShot(2, 9, battleField);
        indexOfShip = battleField.getIndexOfShip(2, 9);
        assertFalse(helper.isThisDestroyedShip(indexOfShip, battleField));
        helper.makeShot(3, 9, battleField);
        assertTrue(helper.isThisDestroyedShip(indexOfShip, battleField));
    }

    @Test
    public void testBlocSpaceNearDestroyedShip() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        BattleField battleField = new BattleField(coordinates1);
    }

    @Test
    public void testIsTheEndOfGame() throws Exception {
        BattleShipHelper helper = new BattleShipHelper();
        BattleField battleField = new BattleField(coordinates1);
        Ship [] ships = battleField.getShips();
        assertFalse(helper.isTheEndOfGame(battleField));
        for (int i = 0; i < 10; i++) {
            for(int j = 0; j < ships[i].getLength(); j++) {
                ships[i].setWound();
            }
            if(i != 9) {
                assertFalse(helper.isTheEndOfGame(battleField));
            }
        }
        assertTrue(helper.isTheEndOfGame(battleField));
    }
}