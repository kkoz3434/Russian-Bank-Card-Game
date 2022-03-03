import Controller.CardGameAppController;
import Game.Deck;
import Game.Table;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.IOException;
import Guice.CardGameInjector;

public class HelloApplication extends Application {

    private Stage primaryStage;
    private CardGameAppController appController;

    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new CardGameInjector(primaryStage));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Card Game");

        //this.appController = new CardGameAppController(primaryStage, table);

        this.appController = injector.getInstance(CardGameAppController.class);
        this.appController.initRootLayout();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
