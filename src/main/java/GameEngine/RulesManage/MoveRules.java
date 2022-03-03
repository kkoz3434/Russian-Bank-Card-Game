package GameEngine.RulesManage;

import Game.Card;
import Game.Player;
import Game.Slot;
import GameEngine.utils.Position;
import GameEngine.utils.State;

import java.util.Map;
import java.util.Optional;

public class MoveRules {

    private final Map<Position, Slot> slotMap;

    public MoveRules(Map<Position, Slot> slotMap){
        this.slotMap = slotMap;
    }

    public boolean houseRules(Position from, Position to){
        // if House: malejaco i inny kolor
        Optional<Card> cToMove = this.slotMap.get(from).peek();
        Optional<Card> cToCompare = this.slotMap.get(to).peek();

        if (slotMap.get(to).isEmpty()) {
            return true;
        } else if(cToMove.isPresent() && cToCompare.isPresent()){
            Card ToMove = cToMove.get();
            Card ToCompare = cToCompare.get();
            return ToMove.getCardValue() == ToCompare.getCardValue() - 1
                    && !ToMove.getCardColor().equals(ToCompare.getCardColor())
                    && ToMove.getCardValue() != 1;
        }
        return false;
    }

    public boolean foundationRules(Position from, Position to){
        // if Foundation: rosnaco, ten sam kolor
        // Foundations: an empty foundation space can only be filled by an available Ace. Each of the eight
        // foundations is then built up by adding cards of the same suit in ascending sequence: A23456789TJQK.
        Optional<Card> cToMove = this.slotMap.get(from).peek();
        Optional<Card> cToCompare = this.slotMap.get(to).peek();

        if (cToMove.isPresent() && slotMap.get(to).isEmpty() && slotMap.get(from).getState() != State.FOUNDATION ) {
            if(cToMove.get().getCardRank().getValue() == 1)
                System.out.println("ta karta powinna byc asem");
            return cToMove.get().getCardRank().getValue() == 1;
        }
        if(cToMove.isEmpty()){
            return false;
        }

        if(cToCompare.isPresent()){
            Card ToMove = cToMove.get();
            Card ToCompare = cToCompare.get();

            return ToMove.getCardValue() -1  == ToCompare.getCardValue()
                    && ToMove.getCardSuit()==ToCompare.getCardSuit();
        }
        return false;
    }

    public boolean reverseAndPileRules(Position from,Position to){
        Optional<Card> cToMove = this.slotMap.get(from).peek();
        Optional<Card> cToCompare = this.slotMap.get(to).peek();

        if (cToMove.isPresent() && cToCompare.isPresent()) {


            Card toMove = cToMove.get();
            Card toCompare = cToCompare.get();

            return (toMove.getCardValue() == toCompare.getCardValue() - 1)
                    || (toMove.getCardValue() == toCompare.getCardValue() + 1) && toMove.getCardSuit() == toCompare.getCardSuit();
        }

        return false;
    }

    public boolean fromHouseToHouse(Position from, Position to) {
        return slotMap.get(from).getState() == State.HOUSE && slotMap.get(to).getState() == State.HOUSE;
    }

    public boolean fromHouseToFoundation(Position from, Position to) {
        return slotMap.get(from).getState() == State.HOUSE && slotMap.get(to).getState() == State.FOUNDATION;
    }

    public boolean fromPlayerHandToFoundation(Position from, Position to, Player player) {
        return slotMap.get(from) == player.getHand() && slotMap.get(to).getState() == State.FOUNDATION;
    }

    public boolean fromPlayerReserveToFoundation(Position from, Position to, Player player) {
        return slotMap.get(from) == player.getReserve() && slotMap.get(to).getState() == State.FOUNDATION;
    }

    public boolean fromHouseToOpponentWasteOrReserve(Position from, Position to, Player player, Player opponent) {
        return slotMap.get(from).getState() == State.HOUSE && (slotMap.get(to) == opponent.getReserve() || slotMap.get(to) == player.getWaste());
    }

    public boolean fromPlayerReserveToOpponentWasteOrReserve(Position from, Position to, Player player, Player opponent) {
        return slotMap.get(from) == player.getReserve() && (slotMap.get(to) == opponent.getWaste() || slotMap.get(to) == opponent.getReserve());
    }

    public boolean fromPlayerHandToPlayerWaste(Position from, Position to, Player player) {
        return slotMap.get(from) == player.getHand() && slotMap.get(to) == player.getWaste();
    }

    public boolean fromPlayerReserveToHouse(Position from, Position to, Player player) {
        return slotMap.get(from) == player.getReserve() && slotMap.get(to).getState() == State.HOUSE;
    }

    public boolean fromCurrPlayerHandToHouse(Position from, Position to, Player player){
        return (slotMap.get(from) == player.getHand() && slotMap.get(to).getState() == State.HOUSE);
    }

    public boolean forbiddenPlaceToCard(Position to, Player player, Player opponent) {
        if(slotMap.get(to) == player.getReserve() ||
                slotMap.get(to) == player.getHand() ||
                slotMap.get(to) == opponent.getHand()){
            System.out.println("nie moge do swojego magazynka i do ktorej kolwiek reki");
            return true;
        }
        return false;
    }

    public boolean pickedForbiddenCard(Position from, Player player, Player opponent) {
        if (slotMap.get(from) == opponent.getHand() ||
                slotMap.get(from) == opponent.getWaste() ||
                slotMap.get(from) == opponent.getReserve()){
            System.out.println("Nie moge wziac karty przeciwnika");
            return true;
        }
        if (slotMap.get(from) == player.getWaste() ||
                slotMap.get(from).getState() == State.FOUNDATION) {
            System.out.println("Nie moge z kosza i z foundation");
            return true;
        }
        return false;
    }
}
