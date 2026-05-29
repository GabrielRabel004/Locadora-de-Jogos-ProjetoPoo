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

    public static boolean showConfirmation(String title, String message) {

        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                message,
                ButtonType.YES,
                ButtonType.NO
        );
        alert.setTitle(title);
        alert.setHeaderText(null);

        return alert.showAndWait()
                .filter(ButtonType.YES::equals)
                .isPresent();
    }

    private static void show(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
