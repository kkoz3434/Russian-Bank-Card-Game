package Guice.provider;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLLoaderProvider{


    private final Injector injector;

    @Inject
    public FXMLLoaderProvider(final Injector injector) {
        this.injector = injector;
    }

    public FXMLLoader getLoader(final URL location) {
        return getLoader(location, null);
    }

    public FXMLLoader getLoader(final URL location, ResourceBundle resources) {
        return new FXMLLoader(location,
                resources,
                new JavaFXBuilderFactory(),
                injector::getInstance);
    }

}