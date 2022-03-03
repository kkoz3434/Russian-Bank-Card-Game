package Command;

import Game.Card;
import Game.Slot;
import Game.Table;
import GameEngine.utils.Position;

import java.util.Map;
import java.util.Optional;

public class MoveCardCommand implements Command{
    private Table table;
    private Slot from;
    private Slot to;
    private Map map;
    private Position fromPosition;
    private Position toPosition;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Position getFromPosition() {
        return fromPosition;
    }

    public void setFromPosition(Position fromPosition) {
        this.fromPosition = fromPosition;
    }

    public Position getToPosition() {
        return toPosition;
    }

    public void setToPosition(Position toPosition) {
        this.toPosition = toPosition;
    }

    public MoveCardCommand(Table table, Map<Position, Slot> map, Position from, Position to) {
        this.table = table;
        this.from = map.get(from);
        this.to = map.get(to);
        this.map = map;
        fromPosition = from;
        toPosition = to;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Slot getFrom() {
        return from;
    }

    public void setFrom(Slot from) {
        this.from = from;
    }

    public Slot getTo() {
        return to;
    }

    public void setTo(Slot to) {
        this.to = to;
    }

    @Override
    public void execute() {
        from.getStack().pop().ifPresent(card -> to.getStack().push(card));
    }

    @Override
    public String getName() {
        Optional<Card> movedCard = to.getStack().peek();
        return movedCard.map(card -> "Card " + card +
                " moved to: " + to.getState() + "  " + toPosition.toString() + " from " + from.getState()).orElse("None");
    }

    @Override
    public void undo() {
        Optional<Card> cardToMove = to.getStack().pop();
        cardToMove.ifPresent(card -> from.getStack().push(card));
    }
}
