package view;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Cliente;
import service.ClienteService;
import util.AlertUtils;

public class TelaClientes {

    private final ClienteService clienteService = new ClienteService();
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private final Runnable onSave;

    private TextField txtBusca;
    private TextField txtNome;
    private DatePicker dtNascimento;
    private TextField txtCpf;
    private TextField txtTelefone;
    private TextField txtEmail;
    private TableView<Cliente> tabelaClientes;
    private Label feedback;
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

        Label eyebrow = new Label("Clientes");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Cadastro, pesquisa, atualizacao e exclusao de clientes.");
        title.getStyleClass().add("hero-title");
        title.setWrapText(true);

        Label subtitle = new Label(
                "Pesquise por ID, nome ou CPF, selecione um cliente e mantenha "
                + "os dados atualizados no banco."
        );
        subtitle.getStyleClass().add("muted-text");
        subtitle.setWrapText(true);

        header.getChildren().addAll(eyebrow, title, subtitle);

        VBox card = new VBox(18);
        card.getStyleClass().add("content-card");
        card.setPadding(new Insets(24));

        txtBusca = new TextField();
        txtBusca.setPromptText("Pesquisar por ID, nome ou CPF");

        Button btnPesquisar = new Button("Pesquisar");
        btnPesquisar.getStyleClass().add("secondary-button");

        HBox searchBar = new HBox(12, txtBusca, btnPesquisar);
        txtBusca.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(txtBusca, Priority.ALWAYS);

        GridPane form = buildForm();
        tabelaClientes = buildTable();
        tabelaClientes.setItems(clientes);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("primary-button");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("secondary-button");
        btnAtualizar.disableProperty().bind(
                tabelaClientes.getSelectionModel().selectedItemProperty().isNull()
        );

        Button btnExcluir = new Button("Excluir");
        btnExcluir.getStyleClass().addAll("secondary-button", "logout-button");
        btnExcluir.disableProperty().bind(
                tabelaClientes.getSelectionModel().selectedItemProperty().isNull()
        );

        Button btnLimpar = new Button("Limpar");
        btnLimpar.getStyleClass().add("secondary-button");

        HBox actions = new HBox(12, btnSalvar, btnAtualizar, btnExcluir, btnLimpar);
        actions.getStyleClass().add("actions-row");

        feedback = new Label("Carregando clientes...");
        feedback.getStyleClass().add("muted-text");
        feedback.setWrapText(true);

        VBox statusStrip = new VBox(feedback);
        statusStrip.getStyleClass().add("status-strip");

        tabelaClientes.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, cliente) -> carregarClienteSelecionado(cliente));

        btnPesquisar.setOnAction(event -> pesquisarClientes());
        txtBusca.setOnAction(event -> pesquisarClientes());
        btnSalvar.setOnAction(event -> salvarCliente());
        btnAtualizar.setOnAction(event -> atualizarCliente());
        btnExcluir.setOnAction(event -> excluirCliente());
        btnLimpar.setOnAction(event -> limparTudo());

        card.getChildren().addAll(searchBar, form, actions, tabelaClientes, statusStrip);
        VBox.setVgrow(tabelaClientes, Priority.ALWAYS);

        root.setTop(header);
        root.setCenter(card);
        BorderPane.setMargin(card, new Insets(24));

        carregarClientes();

        return StyleSupport.createScene(root, 1120, 760);
    }

    private GridPane buildForm() {

        GridPane form = new GridPane();
        form.setHgap(14);
        form.setVgap(14);

        txtNome = new TextField();
        txtNome.setPromptText("Nome completo");

        dtNascimento = new DatePicker();
        dtNascimento.setPromptText("Data de nascimento");
        dtNascimento.setMaxWidth(Double.MAX_VALUE);

        txtCpf = new TextField();
        txtCpf.setPromptText("Somente numeros ou formato livre");

        txtTelefone = new TextField();
        txtTelefone.setPromptText("DDD + numero");

        txtEmail = new TextField();
        txtEmail.setPromptText("email@dominio.com");

        GridPane.setHgrow(txtNome, Priority.ALWAYS);
        GridPane.setHgrow(dtNascimento, Priority.ALWAYS);
        GridPane.setHgrow(txtCpf, Priority.ALWAYS);
        GridPane.setHgrow(txtTelefone, Priority.ALWAYS);
        GridPane.setHgrow(txtEmail, Priority.ALWAYS);

        form.add(createFieldLabel("Nome"), 0, 0);
        form.add(txtNome, 1, 0);
        form.add(createFieldLabel("Nascimento"), 0, 1);
        form.add(dtNascimento, 1, 1);
        form.add(createFieldLabel("CPF"), 0, 2);
        form.add(txtCpf, 1, 2);
        form.add(createFieldLabel("Telefone"), 2, 2);
        form.add(txtTelefone, 3, 2);
        form.add(createFieldLabel("Email"), 0, 3);
        form.add(txtEmail, 1, 3, 3, 1);

        return form;
    }

    private TableView<Cliente> buildTable() {

        TableView<Cliente> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Nenhum cliente encontrado."));

        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setMaxWidth(80);

        TableColumn<Cliente, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Cliente, LocalDate> colNascimento = new TableColumn<>("Nascimento");
        colNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));

        TableColumn<Cliente, String> colCpf = new TableColumn<>("CPF");
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        TableColumn<Cliente, String> colTelefone = new TableColumn<>("Telefone");
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().addAll(
                colId,
                colNome,
                colNascimento,
                colCpf,
                colTelefone,
                colEmail
        );

        return table;
    }

    private void carregarClientes() {

        try {
            clientes.setAll(clienteService.listar());
            setFeedback("Clientes carregados com sucesso.", "success-text");
        } catch (IllegalStateException ex) {
            clientes.clear();
            setFeedback(ex.getMessage(), "error-text");
            AlertUtils.showError("Falha ao carregar", ex.getMessage());
        }
    }

    private void pesquisarClientes() {

        try {
            List<Cliente> resultado = clienteService.buscarClientes(txtBusca.getText());
            clientes.setAll(resultado);
            tabelaClientes.getSelectionModel().clearSelection();
            limparCampos();
            setFeedback(resultado.size() + " cliente(s) encontrado(s).", "success-text");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            clientes.clear();
            setFeedback(ex.getMessage(), "error-text");
            AlertUtils.showError("Falha ao pesquisar", ex.getMessage());
        }
    }

    private void salvarCliente() {

        try {
            Cliente cliente = criarClienteFormulario(0);
            clienteService.salvar(cliente);
            recarregarResultadoAtual();
            limparFormulario();
            setFeedback("Cliente salvo com sucesso.", "success-text");
            notificarAlteracao();
            AlertUtils.showInfo("Cliente cadastrado", "O cliente foi registrado com sucesso.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            setFeedback(ex.getMessage(), "error-text");
            AlertUtils.showError("Falha ao salvar", ex.getMessage());
        }
    }

    private void atualizarCliente() {

        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            setFeedback("Selecione um cliente para atualizar.", "error-text");
            return;
        }

        try {
            Cliente cliente = criarClienteFormulario(selecionado.getId());
            clienteService.atualizar(cliente);
            recarregarResultadoAtual();
            selecionarClientePorId(cliente.getId());
            setFeedback("Cliente atualizado com sucesso.", "success-text");
            notificarAlteracao();
            AlertUtils.showInfo("Cliente atualizado", "Os dados do cliente foram atualizados.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            setFeedback(ex.getMessage(), "error-text");
            AlertUtils.showError("Falha ao atualizar", ex.getMessage());
        }
    }

    private void excluirCliente() {

        Cliente selecionado = tabelaClientes.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            setFeedback("Selecione um cliente para excluir.", "error-text");
            return;
        }

        boolean confirmado = AlertUtils.showConfirmation(
                "Excluir cliente",
                "Tem certeza que deseja excluir este cliente?"
        );

        if (!confirmado) {
            return;
        }

        try {
            clienteService.excluir(selecionado.getId());
            recarregarResultadoAtual();
            limparFormulario();
            setFeedback("Cliente excluido com sucesso.", "success-text");
            notificarAlteracao();
            AlertUtils.showInfo("Cliente excluido", "O cliente foi removido do banco.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            setFeedback(ex.getMessage(), "error-text");
            AlertUtils.showError("Falha ao excluir", ex.getMessage());
        }
    }

    private void carregarClienteSelecionado(Cliente clienteSelecionado) {

        if (clienteSelecionado == null) {
            return;
        }

        try {
            Cliente clienteAtual = clienteService.buscarPorId(clienteSelecionado.getId());

            if (clienteAtual == null) {
                setFeedback("Cliente nao encontrado no banco.", "error-text");
                return;
            }

            preencherCampos(clienteAtual);
            setFeedback("Cliente selecionado para edicao.", "success-text");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            setFeedback(ex.getMessage(), "error-text");
            AlertUtils.showError("Falha ao carregar cliente", ex.getMessage());
        }
    }

    private void recarregarResultadoAtual() {

        String termo = txtBusca.getText();

        if (termo == null || termo.trim().isEmpty()) {
            clientes.setAll(clienteService.listar());
        } else {
            clientes.setAll(clienteService.buscarClientes(termo));
        }
    }

    private Cliente criarClienteFormulario(int id) {

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(txtNome.getText());
        cliente.setDataNascimento(dtNascimento.getValue());
        cliente.setCpf(txtCpf.getText());
        cliente.setTelefone(txtTelefone.getText());
        cliente.setEmail(txtEmail.getText());
        return cliente;
    }

    private void preencherCampos(Cliente cliente) {

        txtNome.setText(cliente.getNome());
        dtNascimento.setValue(cliente.getDataNascimento());
        txtCpf.setText(cliente.getCpf());
        txtTelefone.setText(cliente.getTelefone());
        txtEmail.setText(cliente.getEmail());
    }

    private void limparTudo() {

        txtBusca.clear();
        limparFormulario();
        carregarClientes();
        setFeedback("Formulario limpo.", "muted-text");
    }

    private void limparFormulario() {

        tabelaClientes.getSelectionModel().clearSelection();
        limparCampos();
    }

    private void limparCampos() {

        txtNome.clear();
        dtNascimento.setValue(null);
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
    }

    private void selecionarClientePorId(int id) {

        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                tabelaClientes.getSelectionModel().select(cliente);
                tabelaClientes.scrollTo(cliente);
                return;
            }
        }
    }

    private void notificarAlteracao() {

        if (onSave != null) {
            onSave.run();
        }
    }

    private void setFeedback(String message, String styleClass) {

        feedback.setText(message);
        feedback.getStyleClass().setAll(styleClass);
    }

    private Label createFieldLabel(String text) {

        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        return label;
    }
}
