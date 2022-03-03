package GameEngine.RulesManage;

import Game.*;
import GameEngine.utils.Position;
import GameEngine.utils.State;
import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class MoveManager{

    private final Map<Position, Slot> slotMap;
    private final MoveRules ruleManager;

    public MoveManager(Map<Position, Slot> slotMap){
        this.slotMap = slotMap;
        this.ruleManager = new MoveRules(slotMap);
    }

    public boolean canMoveCard(Position from, Position to, Player player, Player opponent) {

        if (ruleManager.pickedForbiddenCard(from, player, opponent))
            return false;

        if (ruleManager.forbiddenPlaceToCard(to, player, opponent))
            return false;

        if (checkMoveFromReserveToFoundation(player))
            return isAllowedMoveFromReserveToFoundation(from, to, player);

        if(checkHouseToFoundation())
            return isAllowedMoveHouseToFoundation(from, to);

        if(isCardInReserve(player) && thereAreFreeSlotsInHouse())
            return this.ruleManager.houseRules(from, to) && slotMap.get(from).getState() == State.RESERVE;

        return (ruleManager.fromCurrPlayerHandToHouse(from, to, player) && this.ruleManager.houseRules(from, to)) ||
                (ruleManager.fromPlayerReserveToHouse(from, to, player) && this.ruleManager.houseRules(from, to)) ||
                (ruleManager.fromPlayerHandToPlayerWaste(from, to, player) && endOfTurn(player)) ||
                (ruleManager.fromPlayerReserveToOpponentWasteOrReserve(from, to, player, opponent) && this.ruleManager.reverseAndPileRules(from, to)) ||
                (ruleManager.fromHouseToOpponentWasteOrReserve(from, to, player, opponent) && this.ruleManager.reverseAndPileRules(from, to)) ||
                (ruleManager.fromPlayerReserveToFoundation(from, to, player) && this.ruleManager.foundationRules(from, to)) ||
                (ruleManager.fromPlayerHandToFoundation(from, to, player) && this.ruleManager.foundationRules(from, to)) ||
                (ruleManager.fromHouseToFoundation(from, to) && this.ruleManager.foundationRules(from, to)) ||
                (ruleManager.fromHouseToHouse(from, to) && this.ruleManager.houseRules(from, to));
    }

    private boolean endOfTurn(Player player) {
        return !availableMoveExists(player);
    }

    public Optional<Player> getWinner(Player player1, Player player2) {
        if (player1.getHand().isEmpty() && player1.getWaste().isEmpty() && player1.getReserve().isEmpty()) {
            return Optional.of(player1);
        } else if (player2.getHand().isEmpty() && player2.getWaste().isEmpty() && player2.getReserve().isEmpty()) {
            return Optional.of(player2);
        }
        return Optional.empty();
    }

    public boolean priorityChecker(Map<Position, Slot> map, Position source, Player player, Player opponent) {
        return !source.equals(player.getReservePosition()) &&
                map.keySet()
                        .stream()
                        .anyMatch(pos -> (map.get(pos).getState()==State.FOUNDATION &&
                                canMoveCard(player.getReservePosition(), pos, player, opponent))
                        );
    }

    private List<Position> getHouseFields(){
        return slotMap.keySet()
                .stream()
                .filter(pos -> slotMap.get(pos).getState() == State.FOUNDATION)
                .collect(Collectors.toList());
    }

    private boolean checkMoveFromReserveToFoundation(Player player){
        return getFoundationFields()
                .stream()
                .anyMatch(pos -> ruleManager.foundationRules(player.getReservePosition(), pos));
    }

    private boolean isAllowedMoveFromReserveToFoundation(Position from, Position to, Player player){
        return slotMap.get(from) == slotMap.get(player.getReservePosition()) &&
                slotMap.get(to).getState() == State.FOUNDATION &&
                ruleManager.foundationRules(from, to);
    }

    private boolean checkHouseToFoundation(){
        for(Position housePos: getHouseFields()){
            for(Position foundationPos: getFoundationFields()){
                if(this.ruleManager.foundationRules(housePos, foundationPos))
                    return true;
            }
        }
        return false;
    }

    private boolean isAllowedMoveHouseToFoundation(Position from, Position to){
        return slotMap.get(from).getState() == State.HOUSE &&
                slotMap.get(to).getState() == State.FOUNDATION &&
                ruleManager.foundationRules(from, to);
    }

    private boolean isCardInReserve(Player player){
        return !this.slotMap.get(player.getReservePosition()).isEmpty();
    }

    private boolean thereAreFreeSlotsInHouse(){
        return slotMap.keySet()
                .stream()
                .anyMatch(pos -> slotMap.get(pos).getState() == State.HOUSE && slotMap.get(pos).isEmpty());
    }

    private boolean checkHandToFoundation(Player player){
        return getFoundationFields()
                .stream()
                .anyMatch(pos -> this.ruleManager.foundationRules(player.getHandPosition(), pos));
    }

    private List<Position> getFoundationFields(){
        return slotMap.keySet()
                .stream()
                .filter(pos -> slotMap.get(pos).getState() == State.FOUNDATION)
                .collect(Collectors.toList());
    }

    public boolean availableMoveExists(Player player){
        return checkMoveFromReserveToFoundation(player) || checkHouseToFoundation() || checkHandToFoundation(player);
    }

    public List<Pair<Position, Position>> availableMoves(Player player, Player opponent){
        List<Pair<Position, Position>> result = new ArrayList<>();
        slotMap.keySet()
                .forEach( posFrom -> slotMap.keySet()
                    .stream()
                    .filter(posTo -> posFrom != posTo && canMoveCard(posFrom, posTo, player, opponent))
                    .forEach(posTo->result.add(new Pair<>(posFrom, posTo)))
        );
        return result;
    }
}
