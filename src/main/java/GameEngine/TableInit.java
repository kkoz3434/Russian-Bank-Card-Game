package GameEngine;

import Game.*;
import GameEngine.utils.InitConfiguration;
import GameEngine.utils.Position;
import GameEngine.utils.State;

import java.util.*;

public class TableInit {

    private final InitConfiguration configuration;
    private final Table table;
    private final Map<Position, Slot> slotMap;
    private Player lowerPlayer;
    private Player upperPlayer;

    public TableInit(InitConfiguration configuration, Table table){
        this.configuration = configuration;
        this.table = table;
        this.slotMap = new HashMap<>();
        initPlayersSlot();
    }
    public Map<Position, Slot> initTable(){
        initTableau();
        initPlayersDeck(lowerPlayer);
        initPlayersDeck(upperPlayer);
        initTableauAces(lowerPlayer, configuration.getPlayerAceRow(), configuration.getPlayerTableauRow());
        initTableauAces(upperPlayer, configuration.getUpperAceRow(), configuration.getUpperTableauRow());
        return slotMap;
    }
    public Player getUpperPlayer(){
        return this.upperPlayer;
    }
    public Player getLowerPlayer(){
        return this.lowerPlayer;
    }
    private void initPlayersSlot() {
        Map<String, Position> startPositions = this.configuration.getStartPosition();

        Slot reserveSlotT = new Slot(State.RESERVE, true);
        table.addSlot(reserveSlotT);
        Position reservePosT = startPositions.get("reserveUpperPlayer");
        slotMap.put(reservePosT, reserveSlotT);

        Slot wasteSlotT = new Slot(State.WASTE);
        table.addSlot(wasteSlotT);
        Position wastePosT = startPositions.get("wasteUpperPlayer");
        slotMap.put(wastePosT, wasteSlotT);

        Slot handSlotT = new Slot(State.HAND, true);
        table.addSlot(handSlotT);
        Position handPosT = startPositions.get("handUpperPlayer");
        slotMap.put(handPosT, handSlotT);

        Slot handSlotB = new Slot(State.HAND, true);
        table.addSlot(handSlotB);
        Position handPosB = startPositions.get("handLowerPlayer");
        slotMap.put(handPosB, handSlotB);

        Slot wasteSlotB = new Slot(State.WASTE);
        table.addSlot(wasteSlotB);
        Position wastePosB = startPositions.get("wasteLowerPlayer");
        slotMap.put(wastePosB, wasteSlotB);

        Slot reserveSlotB = new Slot(State.RESERVE, true);
        table.addSlot(reserveSlotB);
        Position reservePosB = startPositions.get("reserveLowerPlayer");
        slotMap.put(reservePosB, reserveSlotB);

        this.upperPlayer = new Player(handSlotT, reserveSlotT, wasteSlotT,
                reservePosT, wastePosT, handPosT);
        this.lowerPlayer = new Player(handSlotB, reserveSlotB,  wasteSlotB,
                reservePosB,wastePosB,handPosB );
    }
    private void initTableau() {
        for (int i = 1; i <= 4; i++) {
            for (int k = 0; k < 4; k++) {
                State state = (i == 2 || i == 3) ? State.FOUNDATION : State.HOUSE;
                Slot slot = new Slot(state);
                table.addSlot(slot);
                slotMap.put(new Position(i, k), slot);
            }
        }
    }
    private void initPlayersDeck(Player player) {
        List<Card> cards = new Deck().getCards();
        Collections.shuffle(cards);
        player.getReserve().getStack().setCards(new ArrayList<>(cards.subList(0, configuration.getNumOfCardsInInitReserve())));
        cards = new ArrayList<>(cards.subList(configuration.getNumOfCardsInInitReserve(), configuration.getNumOfCardsInDeck()));
        player.getHand().getStack().setCards(cards);
    }
    private void initTableauAces(Player player, int aceRow, int tableauRow) {
        int AceColumn = 0;
        int tableauColumn = 0;
        for (int col = 0; col < 4; col++) {
            Optional<Card> card = player.getHand().getStack().pop();
            if (card.isPresent()) {
                if (card.get().getCardRank() == CardRank.ACE) {
                    Slot slot = slotMap.get(new Position(aceRow, AceColumn));
                    slot.getStack().push(card.get());
                    AceColumn++;
                } else {
                    Slot slot = slotMap.get(new Position(tableauRow, tableauColumn));
                    slot.getStack().push(card.get());
                    tableauColumn++;
                }
            } else {
                System.out.println("Something is wrong, NULL in setting cards in Init method");
            }
        }
    }
}
