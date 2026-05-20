package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage stage) {

        stage.setTitle("Locadora Games");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
        stage.setScene(new TelaLogin(stage).getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
