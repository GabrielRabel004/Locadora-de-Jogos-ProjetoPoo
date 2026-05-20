package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.AutenticacaoService;

public class TelaLogin {

    private final Stage stage;
    private final AutenticacaoService autenticacaoService = new AutenticacaoService();
    private Scene scene;

    public TelaLogin(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {

        if (scene == null) {
            scene = buildScene();
        }

        return scene;
    }

    private Scene buildScene() {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("login-root");

        VBox branding = new VBox(12);
        branding.getStyleClass().add("hero-panel");
        branding.setPadding(new Insets(38));
        branding.setPrefWidth(430);

        Label eyebrow = new Label("LEGADO PRESERVADO");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Locadora Games");
        title.getStyleClass().add("hero-title");

        Label copy = new Label(
                "Base SQL Server mantida, telas modernizadas e fluxo de acesso "
                + "organizado em cima do projeto original."
        );
        copy.getStyleClass().add("muted-text");
        copy.setWrapText(true);

        Label credentials = new Label(
                "Login legado padrao: admin / 123\n"
                + "Ou use um funcionario ja cadastrado no banco."
        );
        credentials.getStyleClass().add("muted-text");

        branding.getChildren().addAll(eyebrow, title, copy, credentials);

        VBox formCard = new VBox(16);
        formCard.getStyleClass().add("content-card");
        formCard.setPadding(new Insets(32));
        formCard.setMaxWidth(420);

        Label formTitle = new Label("Acesso de funcionario");
        formTitle.getStyleClass().add("section-title");

        Label formSubtitle = new Label("Conecte-se ao banco antes de seguir para o menu.");
        formSubtitle.getStyleClass().add("muted-text");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");

        Label feedback = new Label("Informe suas credenciais para continuar.");
        feedback.getStyleClass().add("muted-text");
        feedback.setWrapText(true);

        Button btnEntrar = new Button("Entrar no sistema");
        btnEntrar.getStyleClass().add("primary-button");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);

        Runnable submit = () -> {

            try {

                boolean autenticado = autenticacaoService.autenticar(
                        txtUsuario.getText(),
                        txtSenha.getText()
                );

                if (autenticado) {
                    feedback.setText("Login validado com sucesso.");
                    feedback.getStyleClass().setAll("success-text");
                    stage.setScene(new TelaMenu(stage).getScene());
                    stage.centerOnScreen();
                } else {
                    feedback.setText("Usuario ou senha invalidos.");
                    feedback.getStyleClass().setAll("error-text");
                }

            } catch (IllegalArgumentException ex) {
                feedback.setText(ex.getMessage());
                feedback.getStyleClass().setAll("error-text");
            } catch (IllegalStateException ex) {
                feedback.setText(ex.getMessage());
                feedback.getStyleClass().setAll("error-text");
            }
        };

        btnEntrar.setOnAction(event -> submit.run());
        txtSenha.setOnAction(event -> submit.run());
        txtUsuario.setOnAction(event -> submit.run());

        formCard.getChildren().addAll(
                formTitle,
                formSubtitle,
                txtUsuario,
                txtSenha,
                btnEntrar,
                feedback
        );

        StackPane center = new StackPane(formCard);
        center.setPadding(new Insets(36));
        StackPane.setAlignment(formCard, Pos.CENTER);

        root.setLeft(branding);
        root.setCenter(center);

        return StyleSupport.createScene(root, 1180, 760);
    }
}
