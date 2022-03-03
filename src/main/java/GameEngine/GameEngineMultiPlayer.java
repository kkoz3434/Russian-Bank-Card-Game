package GameEngine;

import Command.Command;
import Command.CommandRegistry;
import Game.Player;
import Game.Table;
import GameEngine.RulesManage.MoveManager;
import GameEngine.utils.InitConfiguration;
import GameEngine.utils.Position;
import Network.Client;
import Network.IConnection;
import Network.Message.*;
import Command.MoveCardCommand;
import Network.Server;

import static java.lang.Thread.sleep;


public class GameEngineMultiPlayer extends GameEngine {
    protected IConnection connection;
    protected boolean isServer;

    public GameEngineMultiPlayer(boolean isServer) {
        this.isServer = isServer;
    }

    @Override
    public void init() {
        this.table = new Table();
        this.commandRegistry = new CommandRegistry(table);
        if (isServer) {
            serverInit();
        } else {
            clientInit();
        }
        if (isServer) {
            if (currPlayer == lowerPlayer)
                handleOpponent();
        }
        if (!isServer) {
            if (currPlayer == upperPlayer)
                handleOpponent();
        }
    }

    @Override
    public boolean isPlayerMove() {
        if (this.currPlayer == this.lowerPlayer && this.isServer) {
            return true;
        } else {
            if (this.currPlayer == this.upperPlayer && !this.isServer) {
                return true;
            }
            return false;
        }
    }

    public boolean canMoveCard(Position source, Position dest) {
        Player opponent = currPlayer == lowerPlayer ? upperPlayer : lowerPlayer;
        return moveManager.canMoveCard(source, dest, currPlayer, opponent);
    }

    @Override
    public boolean moveCard(Position source, Position dest) {
        Player opponent = currPlayer == lowerPlayer ? upperPlayer : lowerPlayer;
        if (moveManager.canMoveCard(source, dest, currPlayer, opponent)) {
            Command command = new MoveCardCommand(table, slotMap, source, dest);
            commandRegistry.executeCommand(command);
            Message message = new PositionMessage(source, dest);
            System.out.println("[Sending Position Message]");
            this.connection.send(message);
            if (!moveManager.availableMoveExists(currPlayer) &&
                    slotMap.get(source) == currPlayer.getHand() &&
                    slotMap.get(dest) == currPlayer.getWaste()) {
                currPlayer = opponent;
                System.out.println("[Sending EndTour Message]");
                this.connection.send(new EndTourMessage());
                //handleOpponent();
            }
            return true;
        }
        return false;
    }

    public void handleOpponent() {

        System.out.println("[Handle opponent]");
        Message message = connection.receive();
        System.out.println("[Handle opponent] odbieram wiadomosc przed while");
        while (message.getType() != MessageType.END_TOUR_MSG) {
            System.out.println("[Handle opponent] mam wiadomość i zmieniam karty u mnie");
            PositionMessage positionMessage = (PositionMessage) message;
            Command command = new MoveCardCommand(table, slotMap, positionMessage.getFrom(), positionMessage.getTo());
            commandRegistry.executeCommand(command);
            message = connection.receive();
        }

        Player opponent = currPlayer == lowerPlayer ? upperPlayer : lowerPlayer;
        currPlayer = opponent;

    }


    public void serverInit() {
        TableInit tableInit = new TableInit(new InitConfiguration(), table);
        this.slotMap = tableInit.initTable();
        this.moveManager = new MoveManager(this.slotMap);
        this.lowerPlayer = tableInit.getLowerPlayer();
        this.upperPlayer = tableInit.getUpperPlayer();
        this.currPlayer = getPlayerWithFirstMove();
        this.connection = new Server();

        // block here till the client appears
        System.out.println("[Serwer] otwieram połączenie");
        this.connection.openConnection();
        System.out.println("[Serwer] połączenie otwarte => wysyłam");
        this.connection.send(new InitMessage(slotMap, lowerPlayer, upperPlayer));
    }

    public void clientInit() {
        System.out.println("[Klient] wejście do init");
        this.connection = new Client();
        if (!this.connection.openConnection()) {
            throw new UnsupportedOperationException("Can't make connection");
        }
        System.out.println("[Klient] Udało sie połączyć czekam na wiadomość ...");
        Message message = this.connection.receive();
        MessageType messageType = message.getType();
        System.out.println("[Klient] odebrałem wiadomość typu: " + messageType.getValue());
        if (messageType == MessageType.INIT_MSG) {
            InitMessage initMessage = (InitMessage) message;
            this.slotMap = initMessage.getSlotMap();
            this.upperPlayer = initMessage.getClientPlayer();
            this.lowerPlayer = initMessage.getServerPlayer();
            slotMap.values().forEach(slot -> this.table.addSlot(slot));
            this.moveManager = new MoveManager(this.slotMap);
            this.currPlayer = getPlayerWithFirstMove();
            System.out.println("[Klient] zainicjalizowano stuff");
        }


    }

}
