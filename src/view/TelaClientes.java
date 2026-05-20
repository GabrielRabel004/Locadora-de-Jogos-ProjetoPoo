package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Cliente;
import service.ClienteService;
import util.AlertUtils;

public class TelaClientes {

    private final ClienteService clienteService = new ClienteService();
    private final Runnable onSave;
    private Scene scene;

    public TelaClientes() {
        this(null);
    }

    public TelaClientes(Runnable onSave) {
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

        Label eyebrow = new Label("Cadastro de clientes");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Cadastro com validacao de CPF, email e idade.");
        title.getStyleClass().add("hero-title");
        title.setWrapText(true);

        Label subtitle = new Label(
                "Use esta tela para registrar clientes maiores de 18 anos "
                + "sem quebrar a base legada."
        );
        subtitle.getStyleClass().add("muted-text");
        subtitle.setWrapText(true);

        header.getChildren().addAll(eyebrow, title, subtitle);

        VBox card = new VBox(20);
        card.getStyleClass().add("content-card");
        card.setPadding(new Insets(24));

        GridPane form = new GridPane();
        form.setHgap(14);
        form.setVgap(14);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome completo");

        DatePicker dtNascimento = new DatePicker();
        dtNascimento.setPromptText("Data de nascimento");

        TextField txtCpf = new TextField();
        txtCpf.setPromptText("Somente numeros ou formato livre");

        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("DDD + numero");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("email@dominio.com");

        Label feedback = new Label("Clientes menores de 18 anos sao bloqueados.");
        feedback.getStyleClass().add("muted-text");
        feedback.setWrapText(true);

        Button btnSalvar = new Button("Salvar cliente");
        btnSalvar.getStyleClass().add("primary-button");

        Button btnLimpar = new Button("Limpar");
        btnLimpar.getStyleClass().add("secondary-button");

        btnSalvar.setOnAction(event -> {

            try {

                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText());
                cliente.setDataNascimento(dtNascimento.getValue());
                cliente.setCpf(txtCpf.getText());
                cliente.setTelefone(txtTelefone.getText());
                cliente.setEmail(txtEmail.getText());

                clienteService.salvar(cliente);

                feedback.setText("Cliente salvo com sucesso.");
                feedback.getStyleClass().setAll("success-text");
                clearFields(txtNome, txtCpf, txtTelefone, txtEmail);
                dtNascimento.setValue(null);

                if (onSave != null) {
                    onSave.run();
                }

                AlertUtils.showInfo(
                        "Cliente cadastrado",
                        "O cliente foi registrado com sucesso."
                );

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
            clearFields(txtNome, txtCpf, txtTelefone, txtEmail);
            dtNascimento.setValue(null);
            feedback.setText("Formulario limpo.");
            feedback.getStyleClass().setAll("muted-text");
        });

        form.add(createFieldLabel("Nome"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(createFieldLabel("Nascimento"), 0, 1);
        form.add(dtNascimento, 1, 1);
        form.add(createFieldLabel("CPF"), 0, 2);
        form.add(txtCpf, 1, 2);
        form.add(createFieldLabel("Telefone"), 0, 3);
        form.add(txtTelefone, 1, 3);
        form.add(createFieldLabel("Email"), 0, 4);
        form.add(txtEmail, 1, 4);

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

    private void clearFields(TextField txtNome, TextField txtCpf,
                             TextField txtTelefone, TextField txtEmail) {

        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
    }
}
