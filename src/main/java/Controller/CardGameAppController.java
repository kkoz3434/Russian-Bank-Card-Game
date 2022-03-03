package Controller;

import java.io.IOException;

import Game.Table;
import Guice.provider.FXMLLoaderProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Command.CommandRegistry;
import com.google.inject.Inject;
import Guice.CardGameInjector;


public class CardGameAppController {


    private final Stage primaryStage;
    private final  FXMLLoaderProvider fxmlLoaderProvider;

    @Inject
    public CardGameAppController(Stage primaryStage,final FXMLLoaderProvider fxmlLoaderProvider){
        this.primaryStage = primaryStage;
        this.fxmlLoaderProvider = fxmlLoaderProvider;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Card Game");


            // load layout from FXML file


//            Injector injector = Guice.createInjector(new CardGameInjector());
//
//
//            FXMLLoaderProvider loader = injector.getInstance(FXMLLoaderProvider.class);

            BorderPane rootLayout = fxmlLoaderProvider.getLoader(getClass().getResource("../View/GameModePanel.fxml")).load();
            // set initial data into controller



            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }
}
