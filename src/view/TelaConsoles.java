package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Console;
import service.ConsoleService;
import util.AlertUtils;

public class TelaConsoles {

    private final ConsoleService consoleService = new ConsoleService();
    private final Runnable onSave;
    private Scene scene;

    public TelaConsoles() {
        this(null);
    }

    public TelaConsoles(Runnable onSave) {
        this.onSave = onSave;
    }

    public Scene getScene() {

        if (scene == null) {
            scene = buildScene();
        }

        return scene;
    }

    private Scene buildScene() {

        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-shell");

        VBox header = new VBox(6);
        header.getStyleClass().add("hero-panel");
        header.setPadding(new Insets(24));

        Label eyebrow = new Label("Produtos");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Cadastro de consoles sem loop de navegacao.");
        title.getStyleClass().add("hero-title");
        title.setWrapText(true);

        Label subtitle = new Label(
                "A antiga tela que abria ela mesma foi convertida em um formulario funcional."
        );
        subtitle.getStyleClass().add("muted-text");
        subtitle.setWrapText(true);

        header.getChildren().addAll(eyebrow, title, subtitle);

        VBox card = new VBox(18);
        card.getStyleClass().add("content-card");
        card.setPadding(new Insets(24));

        GridPane form = new GridPane();
        form.setHgap(14);
        form.setVgap(14);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do console");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Marca");

        TextField txtGeracao = new TextField();
        txtGeracao.setPromptText("Geracao ou modelo");

        TextField txtQuantidade = new TextField();
        txtQuantidade.setPromptText("Quantidade");

        Label feedback = new Label("Informe nome, marca, geracao e quantidade.");
        feedback.getStyleClass().add("muted-text");
        feedback.setWrapText(true);

        Button btnSalvar = new Button("Salvar console");
        btnSalvar.getStyleClass().add("primary-button");

        Button btnLimpar = new Button("Limpar");
        btnLimpar.getStyleClass().add("secondary-button");

        btnSalvar.setOnAction(event -> {

            try {

                Console console = new Console();
                console.setNome(txtNome.getText());
                console.setMarca(txtMarca.getText());
                console.setGeracao(txtGeracao.getText());
                console.setQuantidade(Integer.parseInt(txtQuantidade.getText().trim()));

                consoleService.salvar(console);

                feedback.setText("Console salvo com sucesso.");
                feedback.getStyleClass().setAll("success-text");
                clearFields(txtNome, txtMarca, txtGeracao, txtQuantidade);

                if (onSave != null) {
                    onSave.run();
                }

                AlertUtils.showInfo("Console cadastrado", "O console foi salvo no banco.");

            } catch (NumberFormatException ex) {
                feedback.setText("A quantidade deve ser um numero inteiro.");
                feedback.getStyleClass().setAll("error-text");
            } catch (IllegalArgumentException ex) {
                feedback.setText(ex.getMessage());
                feedback.getStyleClass().setAll("error-text");
            } catch (IllegalStateException ex) {
                feedback.setText(ex.getMessage());
                feedback.getStyleClass().setAll("error-text");
                AlertUtils.showError("Falha ao salvar", ex.getMessage());
            }
        });

        btnLimpar.setOnAction(event -> {
            clearFields(txtNome, txtMarca, txtGeracao, txtQuantidade);
            feedback.setText("Formulario limpo.");
            feedback.getStyleClass().setAll("muted-text");
        });

        form.add(createFieldLabel("Nome"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(createFieldLabel("Marca"), 0, 1);
        form.add(txtMarca, 1, 1);
        form.add(createFieldLabel("Geracao"), 0, 2);
        form.add(txtGeracao, 1, 2);
        form.add(createFieldLabel("Quantidade"), 0, 3);
        form.add(txtQuantidade, 1, 3);

        HBox actions = new HBox(12, btnSalvar, btnLimpar);
        actions.getStyleClass().add("actions-row");

        card.getChildren().addAll(form, actions, feedback);

        root.setTop(header);
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(24));

        return StyleSupport.createScene(root, 860, 620);
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        return label;
    }

    private void clearFields(TextField txtNome, TextField txtMarca,
                             TextField txtGeracao, TextField txtQuantidade) {

        txtNome.clear();
        txtMarca.clear();
        txtGeracao.clear();
        txtQuantidade.clear();
    }
}
