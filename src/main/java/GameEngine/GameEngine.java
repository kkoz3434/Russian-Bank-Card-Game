package GameEngine;

import Command.Command;
import Command.MoveCardCommand;
import Command.CommandRegistry;
import Game.*;
import GameEngine.RulesManage.MoveManager;
import GameEngine.utils.Position;


import java.util.*;

public abstract class GameEngine implements IGameEngine{

    protected Table table;
    protected Map<Position, Slot> slotMap;
    protected MoveManager moveManager;
    protected CommandRegistry commandRegistry;
    protected Player lowerPlayer;
    protected Player upperPlayer;
    protected Player currPlayer;
    
    public GameEngine(){
    }



    @Override
    public boolean canFlip() {
        //TODO:
        return true;
    }

    @Override
    public Map<Position, Slot> getSlots() {
        return this.slotMap;
    }

    @Override
    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    @Override
    public void runBot(){
//        while(currPlayer == bot){
//            System.out.println("bot zacznyna ruchy");
//            Pair<Position, Position> chosenMove = this.bot.move(this.moveManager.availableMoves(bot, player));
//            if(chosenMove == null){
//                currPlayer = player;
//                break;
//            }
//            Position from = chosenMove.getKey();
//            Position to = chosenMove.getValue();
//            while(!moveCard(from, to)){
//                chosenMove = this.bot.move(this.moveManager.availableMoves(bot, player));
//                from = chosenMove.getKey();
//                to = chosenMove.getValue();
//            }
//        }
    }


    protected Player getPlayerWithFirstMove(){
        if(lowerPlayer.getReserveFirstCard().isPresent()&& upperPlayer.getReserveFirstCard().isPresent())
            return lowerPlayer.getReserveFirstCard().get().getCardValue()<= upperPlayer.getReserveFirstCard().get().getCardValue()? lowerPlayer : upperPlayer;
        return null;
    }

}
