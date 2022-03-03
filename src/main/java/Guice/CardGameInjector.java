package Guice;


import GameEngine.IGameEngine;
import Guice.provider.FXMLLoaderProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import javafx.stage.Stage;

public class CardGameInjector extends AbstractModule {

    Stage primaryStage;
    IGameEngine gameEngine;


    public CardGameInjector(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    public CardGameInjector(){

    }
   // public CardGameInjector(IGameEngine gameEngine){this.gameEngine = gameEngine;}
    @Override
    protected void configure(){
        bind(FXMLLoaderProvider.class).asEagerSingleton();
    }

    @Provides
    private Stage provideStage(){
        return this.primaryStage;
    }

    @Provides
    @Named("slots")
    int provideSlotNumber(){
        return 5;
    }
}
