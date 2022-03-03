package GameEngine.utils;

import java.util.HashMap;
import java.util.Map;

public class InitConfiguration {

    private final Map<String, Position> startPosition = new HashMap<String, Position>();

    private final int upperTableauRow = 1;
    private final int upperAceRow = 2;
    private final int playerAceRow = 3;
    private final int playerTableauRow = 4;

    private final int numOfCardsInInitReserve = 12;
    private final int numOfCardsInDeck = 52;

    public InitConfiguration(){
        startPosition.put("reserveUpperPlayer" , new Position(0, 3));
        startPosition.put("wasteUpperPlayer" , new Position(0, 4));
        startPosition.put("handUpperPlayer" , new Position(0, 5));

        startPosition.put("handLowerPlayer", new Position(5, 3));
        startPosition.put("wasteLowerPlayer", new Position(5, 4));
        startPosition.put("reserveLowerPlayer", new Position(5, 5));
    }
    public Map<String, Position> getStartPosition(){
        return this.startPosition;
    }
    public int getUpperTableauRow() {
        return upperTableauRow;
    }

    public int getUpperAceRow() {
        return upperAceRow;
    }

    public int getPlayerAceRow() {
        return playerAceRow;
    }

    public int getPlayerTableauRow() {
        return playerTableauRow;
    }

    public int getNumOfCardsInInitReserve() {
        return numOfCardsInInitReserve;
    }

    public int getNumOfCardsInDeck() {
        return numOfCardsInDeck;
    }
}
