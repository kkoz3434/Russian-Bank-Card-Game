package Controller;

import Command.Command;
import Command.CommandRegistry;
import Command.MoveCardCommand;
import Game.Card;
import Game.Slot;
import Game.Stack;
import GameEngine.IGameEngine;
import GameEngine.utils.Position;
import GameEngine.utils.State;
import Guice.provider.FXMLLoaderProvider;
import com.google.inject.Inject;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.util.*;


public class CardGameOverviewController {
    final static int prefHeight = 70;
    final static int prefWidth = 90;
    final FXMLLoaderProvider fxmlLoaderProvider;
    private final MousePos mousePos = new MousePos();
    private final String cardsPath = "/View/PNG-cards-1.3/";
    private static final int gridPaneSize = 6;

    private CommandRegistry commandRegistry;
    private ImageView draggedCard;
    private ImageView fixedDragCard;

    private IGameEngine gameEngine;
    @FXML
    private ListView<Command> commandLogView;
    @FXML
    private GridPane slots = new GridPane();
    private Node[][] gridPaneArray = null;
    @FXML
    private Button yourTurn;


    @Inject
    public CardGameOverviewController(CommandRegistry commandRegistry, final FXMLLoaderProvider fxmlLoaderProvider) {
        this.commandRegistry = commandRegistry;
        this.fxmlLoaderProvider = fxmlLoaderProvider;

    }

    public void setGameEngine(IGameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    @FXML
    private void initialize() {

    }

    public void init() {
        gameEngine.init();
        commandRegistry = gameEngine.getCommandRegistry();
        initTableView();
        initializeGridPaneArray();
        initLogView();
        updateTurn();
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Runnable updater = new Runnable() {
//                    @Override
//                    public void run() {
//                        gameEngine.handleOpponent();
//                    }
//                };
//                while(true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Platform.runLater(updater);
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();

    }

    EventHandler<MouseEvent> onMouseClickedHandler = e -> {
    };

    EventHandler<MouseEvent> onMousePressedHandler = e -> {
        mousePos.x = e.getSceneX();
        mousePos.y = e.getSceneY();
        draggedCard = (ImageView) e.getSource();
        //comment if below to flip opponent card view when dragging
        if (GridPane.getRowIndex(draggedCard.getParent()) != 0) {
            dragCardView();
        }
    };


    EventHandler<MouseEvent> onMouseDraggedHandler = e -> {
        double offsetX = e.getSceneX() - mousePos.x;
        double offsetY = e.getSceneY() - mousePos.y;

        draggedCard.setTranslateX(offsetX);
        draggedCard.setTranslateY(offsetY);
        if (fixedDragCard != null) {
            fixedDragCard.setTranslateX(offsetX);
            fixedDragCard.setTranslateY(offsetY);
        }
    };

    EventHandler<MouseEvent> onMouseReleasedHandler = e -> {
        removeFixedDragCard();
        ImageView card = (ImageView) e.getSource();
        for (Node slot : slots.getChildren()) {
            if (slot.getBoundsInParent().contains(e.getSceneX(), e.getSceneY()) && slot != card.getParent()) {

                int sourceRow = GridPane.getRowIndex(card.getParent());
                int sourceCol = GridPane.getColumnIndex(card.getParent());
                int destRow = GridPane.getRowIndex(slot);
                int destCol = GridPane.getColumnIndex(slot);

                Position from = new Position(sourceRow, sourceCol);
                Position to = new Position(destRow, destCol);
                if (gameEngine.moveCard(from, to)) {
                    ((StackPane) slot).getChildren().add(card);
                    break;
                }
            }
            card.setTranslateX(0);
            card.setTranslateY(0);
            System.out.println("end of for mouseHandler");
        }
        updateTurn();
        if(gameEngine.isPlayerMove()){
            gameEngine.handleOpponent();
        }
    };

    @FXML
    private void handleUndoAction(ActionEvent event) {
        if (!commandRegistry.getCommandStack().isEmpty()) {
            commandRegistry.undo();
        }
    }


    @FXML
    private void handleRedoAction(ActionEvent event) {
        if (!commandRegistry.getCommandRedoStack().isEmpty()) {
            commandRegistry.redo();
        }
    }

    private void updateView(MoveCardCommand command) {
        System.out.println("[UpdateView method starts]");
        int fromRow = command.getFromPosition().getRow();
        int fromCol = command.getFromPosition().getCol();
        int toRow = command.getToPosition().getRow();
        int toCol = command.getToPosition().getCol();

        StackPane from = (StackPane) gridPaneArray[fromRow][fromCol];
        StackPane to = (StackPane) gridPaneArray[toRow][toCol];
        slots.getChildren().remove(from);
        slots.getChildren().remove(to);
        Node node1 = initSlotView(command.getFrom());
        Node node2 = initSlotView(command.getTo());
        slots.add(node1, fromCol, fromRow);
        slots.add(node2, toCol, toRow);
        gridPaneArray[fromRow][fromCol] = node1;
        gridPaneArray[toRow][toCol] = node2;
        System.out.println("[UpdateView method ends]");
        updateTurn();
    }

    private void initLogView() {
        commandLogView.setItems(commandRegistry.getCommandStack());
        commandLogView.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Command item, boolean empty) {
                super.updateItem(item, empty);
                setText((item != null && !empty) ? item.getName() : null);
            }
        });

        commandRegistry.getCommandStack().addListener((ListChangeListener<Command>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    List<? extends Command> added = change.getAddedSubList();
                    System.out.println("LISTENER SMTH ADDED");
                    MoveCardCommand command = (MoveCardCommand) added.get(0);
                    updateView(command);
                } else if (change.wasRemoved()) {
                    List<? extends Command> removed = change.getRemoved();
                    System.out.println("LISTENER SMTH UNDO");
                    MoveCardCommand command = (MoveCardCommand) removed.get(0);
                    updateView(command);
                }
            }
        });
    }

    public void initTableView() {
        slots.getChildren().clear();
        Map<Position, Slot> slotMap = this.gameEngine.getSlots();
        for (Position pos : slotMap.keySet()) {
            this.slots.add(initSlotView(slotMap.get(pos)), pos.getCol(), pos.getRow());
        }
    }

    private void initializeGridPaneArray() {
        this.gridPaneArray = new Node[gridPaneSize][gridPaneSize];
        for (Node node : this.slots.getChildren()) {
            this.gridPaneArray[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)] = node;
        }
    }

    public StackPane initSlotView(Slot slot) {

        StackPane stackpane = new StackPane();
        stackpane.setPrefHeight(prefWidth);
        stackpane.setPrefWidth(prefHeight);

        stackpane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, new CornerRadii(4), BorderWidths.DEFAULT)));


        for (Card c : slot.getStack().getCards()) {
            String card = c.toPath();
            ImageView cv;
            if ((slot.getState() == State.RESERVE &&
                    slot.getStack().getCards().indexOf(c) == slot.getStack().getCards().size() - 1)) {
                cv = new ImageView(new Image(cardsPath + card + ".png"));
            } else if (slot.getStack().isHidden()) {
                cv = new ImageView(new Image(cardsPath + "card_back.png"));
            } else {
                cv = new ImageView(new Image(cardsPath + card + ".png"));
            }

            cv.setFitWidth(prefHeight);
            cv.setFitHeight(prefWidth);
            cv.setOnMouseClicked(onMouseClickedHandler);
            cv.setOnMouseDragged(onMouseDraggedHandler);
            cv.setOnMousePressed(onMousePressedHandler);
            cv.setOnMouseReleased(onMouseReleasedHandler);

            stackpane.getChildren().add(cv);
        }

        return stackpane;
    }

    private void dragCardView() {
        StackPane slot = (StackPane) gridPaneArray[GridPane.getRowIndex(draggedCard.getParent())][GridPane.getColumnIndex(draggedCard.getParent())];
        int row = GridPane.getRowIndex(draggedCard.getParent());
        int col = GridPane.getColumnIndex(draggedCard.getParent());
        Stack stack = gameEngine.getSlots().get(new Position(row, col)).getStack();
        Optional<Card> card = stack.peek();
        card.ifPresent(value -> fixedDragCard = flipCard(value));
        slot.getChildren().add(fixedDragCard);
    }

    private ImageView flipCard(Card c) {
        String card = c.toPath();
        ImageView cv = new ImageView(new Image(cardsPath + card + ".png"));

        cv.setFitWidth(70);
        cv.setFitHeight(90);
        cv.setOnMouseClicked(onMouseClickedHandler);
        cv.setOnMouseDragged(onMouseDraggedHandler);
        cv.setOnMousePressed(onMousePressedHandler);
        cv.setOnMouseReleased(onMouseReleasedHandler);

        return cv;
    }

    private void updateTurn() {
        System.out.println("Updating Turn");
        if (gameEngine.isPlayerMove()) {
            this.yourTurn.setText("Yours turn!");
            System.out.println("Changing to Your Turn");
            this.yourTurn.setBackground(new Background(new BackgroundFill(Color.rgb(0, 204, 102), new CornerRadii(5), new Insets(0))));
        } else {
            this.yourTurn.setText("Bot's turn!");
            System.out.println("Changing to Bot Turn");
            this.yourTurn.setBackground(new Background(new BackgroundFill(Color.rgb(255, 77, 77), new CornerRadii(5), new Insets(0))));
        }
    }

    private void removeFixedDragCard() {
        StackPane slot = (StackPane) gridPaneArray[GridPane.getRowIndex(draggedCard.getParent())][GridPane.getColumnIndex(draggedCard.getParent())];
        slot.getChildren().remove(fixedDragCard);
    }

    private static class MousePos {
        double x, y;
    }

}
