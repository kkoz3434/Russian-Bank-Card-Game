package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Stack implements Serializable {
    private boolean isHidden;
    private List<Card> cards = new ArrayList<>();

    //0:hidden 1:notHidden
    public Stack(boolean isHidden, List<Card> cards) {
       this.isHidden = isHidden;
       this.cards = cards;
    }

    public Stack(boolean isHidden) {
        this.isHidden = isHidden;

    }

    //public Stack() {
       // this(true, new ArrayList<>());
    //}


    public void push(Card c) {
        cards.add(c);
    }

    public void push(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public void clear() {
        cards.clear();
    }


    public Optional<Card> pop() {
        Card card = cards.get(cards.size() - 1);
        cards.remove(card);
        return Optional.ofNullable(card);
    }


    public Optional<Card> peek() {
        Card card = null;
        if (!cards.isEmpty()) {
            card = cards.get(cards.size() - 1);
        }
        return Optional.ofNullable(card);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setIsHidden(boolean hidden) {
        isHidden = hidden;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

}
