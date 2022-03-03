package GameEngine;

import Command.CommandRegistry;
import Game.Slot;
import GameEngine.utils.Position;

import java.util.Map;

public interface IGameEngine {

    //checks if moving card from one slot to another is possible
    boolean moveCard(Position source, Position dest);

    //init game: give cards and set initial stacks
    void init();

    // return list of changes based on random/computer player

    Map<Position, Slot> getSlots();

    CommandRegistry getCommandRegistry();

    boolean canFlip();

    boolean isPlayerMove();

    void runBot();

    void handleOpponent();

}

