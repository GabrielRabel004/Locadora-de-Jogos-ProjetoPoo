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
import model.Jogo;
import service.JogoService;
import util.AlertUtils;

public class TelaJogos {

    private final JogoService jogoService = new JogoService();
    private final Runnable onSave;
    private Scene scene;

    public TelaJogos() {
        this(null);
    }

    public TelaJogos(Runnable onSave) {
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

        Label title = new Label("Cadastro de jogos conectado ao banco legado.");
        title.getStyleClass().add("hero-title");
        title.setWrapText(true);

        Label subtitle = new Label(
                "O formulario valida os dados antes de enviar para a tabela jogos."
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
        txtNome.setPromptText("Nome do jogo");

        TextField txtGenero = new TextField();
        txtGenero.setPromptText("Genero");

        TextField txtPlataforma = new TextField();
        txtPlataforma.setPromptText("Plataforma");

        TextField txtAno = new TextField();
        txtAno.setPromptText("Ano");

        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Preco");

        Label feedback = new Label("Campos obrigatorios: nome, genero, plataforma, ano e preco.");
        feedback.getStyleClass().add("muted-text");
        feedback.setWrapText(true);

        Button btnSalvar = new Button("Salvar jogo");
        btnSalvar.getStyleClass().add("primary-button");

        Button btnLimpar = new Button("Limpar");
        btnLimpar.getStyleClass().add("secondary-button");

        btnSalvar.setOnAction(event -> {

            try {

                Jogo jogo = new Jogo();
                jogo.setNome(txtNome.getText());
                jogo.setGenero(txtGenero.getText());
                jogo.setPlataforma(txtPlataforma.getText());
                jogo.setAno(Integer.parseInt(txtAno.getText().trim()));
                jogo.setPreco(Double.parseDouble(txtPreco.getText().trim().replace(",", ".")));

                jogoService.salvar(jogo);

                feedback.setText("Jogo salvo com sucesso.");
                feedback.getStyleClass().setAll("success-text");
                clearFields(txtNome, txtGenero, txtPlataforma, txtAno, txtPreco);

                if (onSave != null) {
                    onSave.run();
                }

                AlertUtils.showInfo("Jogo cadastrado", "O jogo foi salvo no banco.");

            } catch (NumberFormatException ex) {
                feedback.setText("Ano e preco devem conter valores numericos validos.");
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
            clearFields(txtNome, txtGenero, txtPlataforma, txtAno, txtPreco);
            feedback.setText("Formulario limpo.");
            feedback.getStyleClass().setAll("muted-text");
        });

        form.add(createFieldLabel("Nome"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(createFieldLabel("Genero"), 0, 1);
        form.add(txtGenero, 1, 1);
        form.add(createFieldLabel("Plataforma"), 0, 2);
        form.add(txtPlataforma, 1, 2);
        form.add(createFieldLabel("Ano"), 0, 3);
        form.add(txtAno, 1, 3);
        form.add(createFieldLabel("Preco"), 0, 4);
        form.add(txtPreco, 1, 4);

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

    private void clearFields(TextField txtNome, TextField txtGenero,
                             TextField txtPlataforma, TextField txtAno,
                             TextField txtPreco) {

        txtNome.clear();
        txtGenero.clear();
        txtPlataforma.clear();
        txtAno.clear();
        txtPreco.clear();
    }
}
