package Game;

import GameEngine.utils.Position;
import javafx.util.Pair;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Player implements Serializable {

    private Slot hand;
    private Slot reserve;
    private Slot waste;

    private Position reservePosition;
    private Position wastePosition;

    public Position getReservePosition() {
        return reservePosition;
    }

    public Position getWastePosition() {
        return wastePosition;
    }

    public Position getHandPosition() {
        return handPosition;
    }

    private Position handPosition;

    public Player(Slot hand, Slot reserve, Slot waste, Position reservePosition, Position wastePosition, Position handPosition) {
        this.hand = hand;
        this.reserve = reserve;
        this.waste = waste;
        this.reservePosition = reservePosition;
        this.wastePosition = wastePosition;
        this.handPosition = handPosition;
    }

    public Slot getHand() {
        return hand;
    }

    public void setHand(Slot hand) {
        this.hand = hand;
    }

    public Slot getReserve() {
        return reserve;
    }

    public void setReserve(Slot reserve) {
        this.reserve = reserve;
    }

    public Slot getWaste() {
        return waste;
    }

    public void setWaste(Slot waste) {
        this.waste = waste;
    }

    public Pair<Position, Position> randomMove(List<Pair<Position, Position>> moves) {
        Random rand = new Random();
        if(moves.size()>0)
            return moves.get(rand.nextInt(moves.size()));
        return null;
    }

    public Optional<Card> getReserveFirstCard(){
        return reserve.peek();
    }

}
