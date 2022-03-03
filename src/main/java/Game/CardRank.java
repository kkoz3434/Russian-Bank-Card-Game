package Game;

import java.io.Serializable;

public enum CardRank implements Serializable {
    ACE(1, "ace"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "10"),
    JACK(11, "jack"),
    QUEEN(12, "queen"),
    KING(13, "king");

    private int cardRank;
    String cardRankPatch;

    CardRank(int i, String string) {
        this.cardRank = i;
        this.cardRankPatch = string;

    }

    public int getValue() {
        return cardRank;
    }
    public String getString(){
        return cardRankPatch;
    }
    
}
