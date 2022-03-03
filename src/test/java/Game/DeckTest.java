package Game;

import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void drawCardTest() {
        //given
        int N = 5;
        Deck myDeck = new Deck();
        List<Card> takenCards = new ArrayList<>();

        //when
        for(int i=0; i< N; i++) {
            takenCards.add(myDeck.drawCard());
        }
        List<Card> myCards = myDeck.getCards();

        //then
        assertEquals(myCards.size()+takenCards.size(), 52);
    }

    @Test
    void refillTest() {
        //given
        int N = 5;
        Deck myDeck = new Deck();
        for(int i=0; i< N; i++) {
            myDeck.drawCard();
        }

        //when
        myDeck.refill();
        List<Card> myCards = myDeck.getCards();

        int drawed = (int) myCards.stream().filter(Objects::isNull).count();

        //then
        assertEquals(drawed, 0);
    }
}