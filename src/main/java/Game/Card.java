package Game;

import java.io.Serializable;

public class Card implements Serializable {
    private final CardRank cardRank;
    private final CardSuit cardSuit;

    public CardRank getCardRank() {
        return cardRank;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }
    public int getCardValue(){
        return cardRank.getValue();
    }
    public String getCardColor(){
        return getCardSuit().getColor();
    }

    public Card(CardRank cardRank, CardSuit cardSuit) {
        this.cardRank = cardRank;
        this.cardSuit = cardSuit;
    }


    public String toPath() {
        return cardRank.getString() + "_of_"+cardSuit.getString();
    }
    public String toString() {
        return "Name: " + this.getCardValue() + " color: " + this.getCardColor();
    }
}
