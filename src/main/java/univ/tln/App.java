package univ.tln;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

/**
 * JavaFX App
 */
public class App extends Application {
    protected static final Properties properties = new Properties();

    public static void loadProperties(String propFileName) throws IOException {
        InputStream inputstream = App.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputstream == null) throw new FileNotFoundException();
        properties.load(inputstream);
    }

    public static void configureLogger() {
        //Regarder src/main/ressources/logging.properties pour fixer le niveau de log
        String path;
        path = Objects.requireNonNull(App.class
                        .getClassLoader()
                        .getResource("logging.properties"))
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
    }


    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(App.class.getResource("hello-view.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        Scene scene2 = new Scene(root, 540, 400);
        stage.setScene(scene2);
        stage.show();
    }


    public static String getProperty(String s) {
        return properties.getProperty(s);
    }

    public static void main(String[] args) {
        launch();
    }

}