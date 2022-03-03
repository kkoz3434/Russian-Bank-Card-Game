package Game;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

public class Deck implements Serializable {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        this.refill();
    }

    public void refill() {
        cards.clear();

        for (CardSuit suit : CardSuit.values()) {
            for (CardRank rank : CardRank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public Card drawCard() {
        int index = (int)(Math.random() * cards.size());
        Card card = cards.get(index);
        cards.remove(card);
        return card;
    }
    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
