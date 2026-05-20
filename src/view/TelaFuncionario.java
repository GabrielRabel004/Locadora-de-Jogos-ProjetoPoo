package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Funcionario;
import service.FuncionarioService;
import util.AlertUtils;

public class TelaFuncionario {

    private final FuncionarioService funcionarioService = new FuncionarioService();
    private Scene scene;

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

        Label eyebrow = new Label("Equipe");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Cadastro de funcionarios com validacoes basicas.");
        title.getStyleClass().add("hero-title");
        title.setWrapText(true);

        Label subtitle = new Label(
                "A tela agora grava usuario e senha usando o DAO legado, mas sem misturar Swing na regra."
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
        txtNome.setPromptText("Nome completo");

        TextField txtCpf = new TextField();
        txtCpf.setPromptText("CPF");

        TextField txtTelefone = new TextField();
        txtTelefone.setPromptText("Telefone");

        TextField txtCargo = new TextField();
        txtCargo.setPromptText("Cargo");

        TextField txtUsuario = new TextField();
        txtUsuario.setPromptText("Usuario");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");

        Label feedback = new Label("Cadastre funcionarios que poderao acessar o sistema.");
        feedback.getStyleClass().add("muted-text");
        feedback.setWrapText(true);

        Button btnSalvar = new Button("Salvar funcionario");
        btnSalvar.getStyleClass().add("primary-button");

        Button btnLimpar = new Button("Limpar");
        btnLimpar.getStyleClass().add("secondary-button");

        btnSalvar.setOnAction(event -> {

            try {

                Funcionario funcionario = new Funcionario();
                funcionario.setNome(txtNome.getText());
                funcionario.setCpf(txtCpf.getText());
                funcionario.setTelefone(txtTelefone.getText());
                funcionario.setCargo(txtCargo.getText());
                funcionario.setUsuario(txtUsuario.getText());
                funcionario.setSenha(txtSenha.getText());

                funcionarioService.salvar(funcionario);

                feedback.setText("Funcionario salvo com sucesso.");
                feedback.getStyleClass().setAll("success-text");
                clearFields(txtNome, txtCpf, txtTelefone, txtCargo, txtUsuario, txtSenha);
                AlertUtils.showInfo(
                        "Funcionario cadastrado",
                        "O funcionario foi gravado no banco."
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
            clearFields(txtNome, txtCpf, txtTelefone, txtCargo, txtUsuario, txtSenha);
            feedback.setText("Formulario limpo.");
            feedback.getStyleClass().setAll("muted-text");
        });

        form.add(createFieldLabel("Nome"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(createFieldLabel("CPF"), 0, 1);
        form.add(txtCpf, 1, 1);
        form.add(createFieldLabel("Telefone"), 0, 2);
        form.add(txtTelefone, 1, 2);
        form.add(createFieldLabel("Cargo"), 0, 3);
        form.add(txtCargo, 1, 3);
        form.add(createFieldLabel("Usuario"), 0, 4);
        form.add(txtUsuario, 1, 4);
        form.add(createFieldLabel("Senha"), 0, 5);
        form.add(txtSenha, 1, 5);

        HBox actions = new HBox(12, btnSalvar, btnLimpar);
        actions.getStyleClass().add("actions-row");

        card.getChildren().addAll(form, actions, feedback);

        root.setTop(header);
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(24));

        return StyleSupport.createScene(root, 900, 680);
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        return label;
    }

    private void clearFields(TextField txtNome, TextField txtCpf,
                             TextField txtTelefone, TextField txtCargo,
                             TextField txtUsuario, PasswordField txtSenha) {

        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtCargo.clear();
        txtUsuario.clear();
        txtSenha.clear();
    }
}
