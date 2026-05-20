package view;

import java.net.URL;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class StyleSupport {

    private StyleSupport() {
    }

    public static Scene createScene(Parent root, double width, double height) {

        Scene scene = new Scene(root, width, height);
        URL stylesheet = StyleSupport.class.getResource("app.css");

        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }

        return scene;
    }

    public static void openChildStage(String title, Scene scene) {

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setMinWidth(760);
        stage.setMinHeight(560);
        stage.setScene(scene);
        stage.show();
    }
}
