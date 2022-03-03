package GameEngine;

import Command.Command;
import Command.CommandRegistry;
import Game.Player;
import Game.Table;
import GameEngine.RulesManage.MoveManager;
import GameEngine.utils.InitConfiguration;
import GameEngine.utils.Position;
import Command.MoveCardCommand;
import java.util.HashMap;

public class GameEngineSinglePlayer extends GameEngine{

    @Override
    public void init() {
        System.out.println("single");
        this.slotMap = new HashMap<>();
        this.table = new Table();
        TableInit tableInit = new TableInit(new InitConfiguration(), table);
        this.slotMap = tableInit.initTable();
        this.moveManager = new MoveManager(this.slotMap);
        this.commandRegistry = new CommandRegistry(table);
        this.lowerPlayer = tableInit.getLowerPlayer();
        this.upperPlayer = tableInit.getUpperPlayer();
        this.currPlayer = getPlayerWithFirstMove();
    }

    @Override
    public boolean moveCard(Position source, Position dest) {
        Player opponent = currPlayer== lowerPlayer ? upperPlayer : lowerPlayer;
        if(moveManager.canMoveCard(source, dest, currPlayer, opponent)){
            Command command = new MoveCardCommand(table, slotMap, source, dest);
            commandRegistry.executeCommand(command);
            if(!moveManager.availableMoveExists(currPlayer) &&
                    slotMap.get(source) == currPlayer.getHand() &&
                    slotMap.get(dest) == currPlayer.getWaste()){
                currPlayer = opponent;
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean isPlayerMove(){
        return this.currPlayer == this.lowerPlayer;
    }

    @Override
    public void handleOpponent(){
        return;
    }

}
