package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public final class AlertUtils {

    private AlertUtils() {
    }

    public static void showInfo(String title, String message) {
        show(Alert.AlertType.INFORMATION, title, message);
    }

    public static void showError(String title, String message) {
        show(Alert.AlertType.ERROR, title, message);
    }

    private static void show(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
