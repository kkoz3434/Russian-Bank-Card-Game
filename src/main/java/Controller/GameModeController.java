package Controller;


import Command.CommandRegistry;
import GameEngine.GameEngineMultiPlayer;
import GameEngine.GameEngineSinglePlayer;
import GameEngine.IGameEngine;
import Guice.provider.FXMLLoaderProvider;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class GameModeController {

    @FXML
    private Button singleMode;

    @FXML
    private Button multiMode;

    @FXML
    private Button joinMode;

    @FXML
    private Button hostMode;

    private final Stage primaryStage;

    private boolean singlePlayerMode;

    private boolean startHost;

    private final FXMLLoaderProvider fxmlLoaderProvider;

    @Inject
    public GameModeController(Stage primaryStage, CommandRegistry commandRegistry, final FXMLLoaderProvider fxmlLoaderProvider){
        this.primaryStage = primaryStage;
//        this.commandRegistry = commandRegistry;
        this.fxmlLoaderProvider = fxmlLoaderProvider;
        this.singlePlayerMode = true;
        this.startHost = false;
    }

    @FXML
    public void initialize() {
        updateModeToggle();

    }

    @FXML
    private void handleOkAction(ActionEvent event) {
        primaryStage.close();
        try {
            IGameEngine gameEngine = null;

            if (this.singlePlayerMode){
                System.out.println("singleplayer mode running");
                gameEngine = new GameEngineSinglePlayer();
            }
            else{
                System.out.println("multiplayer mode running");
                gameEngine = new GameEngineMultiPlayer(this.startHost);
            }

            //Injector injector = Guice.createInjector(new CardGameInjector(gameEngine));
           //BorderPane rootLayout = fxmlLoaderProvider.getLoader(getClass().getResource("../View/CardGameOverviewPanel.fxml")).load();
            FXMLLoader loader = fxmlLoaderProvider.getLoader(getClass().getResource("../View/CardGameOverviewPanel.fxml"));
            BorderPane rootLayout = loader.load();
            CardGameOverviewController cardGameOverviewController = loader.getController();
            cardGameOverviewController.setGameEngine(gameEngine);
            cardGameOverviewController.init();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        updateModel();
//        approved = true;
    }

    @FXML
    private void handleCancelAction(ActionEvent event) {
        primaryStage.close();
    }

    @FXML
    private void selectSingleAction(ActionEvent event) {
        this.singlePlayerMode = true;
        updateModeToggle();
    }

    @FXML
    private void selectMultiAction(ActionEvent event) {
        this.singlePlayerMode = false;
        updateModeToggle();
    }

    @FXML
    private void selectHostAction(ActionEvent event) {
        this.startHost = true;
        updateHostToggle();
    }

    @FXML
    private void selectJoinAction(ActionEvent event) {
        this.startHost = false;
        updateHostToggle();
    }



    private void updateModeToggle(){
        if (this.singlePlayerMode){
            disableMultiOptions();
            this.singleMode.setBackground(new Background(new BackgroundFill(Color.rgb(0, 204, 102), new CornerRadii(5), new Insets(0))));
            this.multiMode.setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), new CornerRadii(5), new Insets(0))));
        }
        else{
            enableMultiOptions();
            this.multiMode.setBackground(new Background(new BackgroundFill(Color.rgb(0, 204, 102), new CornerRadii(5), new Insets(0))));
            this.singleMode.setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), new CornerRadii(5), new Insets(0))));
            updateHostToggle();
        }
    }

    private void updateHostToggle(){
        if (this.startHost){
            this.joinMode.setBackground(new Background(new BackgroundFill(Color.rgb(0, 204, 102), new CornerRadii(5), new Insets(0))));
            this.hostMode.setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), new CornerRadii(5), new Insets(0))));
        }
        else{
            this.hostMode.setBackground(new Background(new BackgroundFill(Color.rgb(0, 204, 102), new CornerRadii(5), new Insets(0))));
            this.joinMode.setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), new CornerRadii(5), new Insets(0))));
        }
    }

    private void enableMultiOptions(){
        joinMode.setDisable(false);
        hostMode.setDisable(false);
    }

    private void disableMultiOptions(){
        joinMode.setDisable(true);
        hostMode.setDisable(true);
    }

    private void updateModel() {

    }

    private void updateControls() {

    }

}
