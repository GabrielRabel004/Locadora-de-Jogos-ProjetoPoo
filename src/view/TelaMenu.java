package view;

import dao.Conexao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ProdutoResumo;
import service.ProdutoService;

public class TelaMenu {

    private final Stage stage;
    private final ProdutoService produtoService = new ProdutoService();

    private final ObservableList<ProdutoResumo> produtos = FXCollections.observableArrayList();
    private final Label lblTotalProdutos = new Label("0");
    private final Label lblTotalJogos = new Label("0");
    private final Label lblTotalConsoles = new Label("0");
    private final Label lblStatus = new Label("Carregando catalogo...");

    private Scene scene;

    public TelaMenu(Stage stage) {
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
        root.getStyleClass().add("app-shell");

        VBox sidebar = buildSidebar();
        VBox content = new VBox(20);
        content.setPadding(new Insets(24));

        VBox header = new VBox(6);

        Label title = new Label("Painel principal");
        title.getStyleClass().add("section-title");

        Label subtitle = new Label(
                "Catalogo consolidado de jogos e consoles com pesquisa e atualizacao."
        );
        subtitle.getStyleClass().add("muted-text");

        header.getChildren().addAll(title, subtitle);

        HBox statsRow = new HBox(16);
        statsRow.getStyleClass().add("stats-row");
        statsRow.getChildren().addAll(
                createStatCard("Produtos no catalogo", lblTotalProdutos),
                createStatCard("Jogos cadastrados", lblTotalJogos),
                createStatCard("Consoles cadastrados", lblTotalConsoles)
        );

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar por nome, tipo, plataforma ou marca");

        ChoiceBox<String> cbFiltro = new ChoiceBox<>();
        cbFiltro.getItems().addAll("Todos", "Jogos", "Consoles");
        cbFiltro.setValue("Todos");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("secondary-button");

        HBox toolbar = new HBox(12, txtPesquisa, cbFiltro, btnAtualizar);
        txtPesquisa.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(txtPesquisa, Priority.ALWAYS);

        TableView<ProdutoResumo> table = buildTable();
        table.setItems(createFilteredList(txtPesquisa, cbFiltro, table));

        lblStatus.getStyleClass().add("muted-text");

        VBox statusStrip = new VBox(lblStatus);
        statusStrip.getStyleClass().add("status-strip");

        btnAtualizar.setOnAction(event -> carregarProdutos());

        content.getChildren().addAll(header, statsRow, toolbar, table, statusStrip);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.setLeft(sidebar);
        root.setCenter(content);

        carregarProdutos();

        return StyleSupport.createScene(root, 1320, 820);
    }

    private VBox buildSidebar() {

        VBox sidebar = new VBox(12);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(270);

        Label brand = new Label("Locadora");
        brand.getStyleClass().add("sidebar-title");

        Label description = new Label(
                "Base original modernizada sem trocar a conexao JDBC."
        );
        description.getStyleClass().add("sidebar-subtitle");
        description.setWrapText(true);

        Button btnProdutos = createSidebarButton("Catalogo de produtos");
        btnProdutos.setOnAction(event ->
                StyleSupport.openChildStage("Produtos", new TelaProdutos().getScene()));

        Button btnClientes = createSidebarButton("Clientes");
        btnClientes.setOnAction(event ->
                StyleSupport.openChildStage("Clientes", new TelaClientes().getScene()));

        Button btnJogos = createSidebarButton("Cadastro de jogos");
        btnJogos.setOnAction(event ->
                StyleSupport.openChildStage("Jogos", new TelaJogos(this::carregarProdutos).getScene()));

        Button btnConsoles = createSidebarButton("Cadastro de consoles");
        btnConsoles.setOnAction(event ->
                StyleSupport.openChildStage("Consoles", new TelaConsoles(this::carregarProdutos).getScene()));

        Button btnFuncionarios = createSidebarButton("Cadastro de funcionarios");
        btnFuncionarios.setOnAction(event ->
                StyleSupport.openChildStage("Funcionarios", new TelaFuncionario().getScene()));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Button btnSair = createSidebarButton("Encerrar sessao");
        btnSair.getStyleClass().add("logout-button");
        btnSair.setOnAction(event -> {
            Conexao.desconectar();
            stage.setScene(new TelaLogin(stage).getScene());
        });

        sidebar.getChildren().addAll(
                brand,
                description,
                btnProdutos,
                btnClientes,
                btnJogos,
                btnConsoles,
                btnFuncionarios,
                spacer,
                btnSair
        );

        return sidebar;
    }

    private Button createSidebarButton(String text) {

        Button button = new Button(text);
        button.getStyleClass().add("sidebar-button");
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private VBox createStatCard(String labelText, Label valueLabel) {

        Label label = new Label(labelText);
        label.getStyleClass().add("stats-label");

        valueLabel.getStyleClass().setAll("stats-value");

        VBox card = new VBox(8, label, valueLabel);
        card.getStyleClass().add("stats-card");
        card.setPadding(new Insets(18));
        card.setPrefWidth(220);

        return card;
    }

    private TableView<ProdutoResumo> buildTable() {

        TableView<ProdutoResumo> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Nenhum produto encontrado."));

        TableColumn<ProdutoResumo, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<ProdutoResumo, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        TableColumn<ProdutoResumo, String> colDetalhe = new TableColumn<>("Detalhes");
        colDetalhe.setCellValueFactory(new PropertyValueFactory<>("detalhe"));

        TableColumn<ProdutoResumo, String> colPreco = new TableColumn<>("Preco");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<ProdutoResumo, String> colEstoque = new TableColumn<>("Estoque");
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        table.getColumns().addAll(colNome, colCategoria, colDetalhe, colPreco, colEstoque);
        return table;
    }

    private SortedList<ProdutoResumo> createFilteredList(TextField txtPesquisa,
                                                         ChoiceBox<String> cbFiltro,
                                                         TableView<ProdutoResumo> table) {

        FilteredList<ProdutoResumo> filteredList =
                new FilteredList<>(produtos, item -> true);

        Runnable updatePredicate = () -> filteredList.setPredicate(item -> {

            String termo = txtPesquisa.getText() == null
                    ? ""
                    : txtPesquisa.getText().trim().toLowerCase();

            String filtro = cbFiltro.getValue();

            boolean matchesText = termo.isBlank()
                    || item.getTextoBusca().contains(termo);

            boolean matchesFilter = "Todos".equals(filtro)
                    || ("Jogos".equals(filtro) && "Jogo".equals(item.getCategoria()))
                    || ("Consoles".equals(filtro) && "Console".equals(item.getCategoria()));

            return matchesText && matchesFilter;
        });

        txtPesquisa.textProperty().addListener((obs, oldValue, newValue) -> updatePredicate.run());
        cbFiltro.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> updatePredicate.run());

        SortedList<ProdutoResumo> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        return sortedList;
    }

    private void carregarProdutos() {

        try {

            produtos.setAll(produtoService.listarProdutos());

            long jogos = produtos.stream()
                    .filter(produto -> "Jogo".equals(produto.getCategoria()))
                    .count();

            long consoles = produtos.stream()
                    .filter(produto -> "Console".equals(produto.getCategoria()))
                    .count();

            lblTotalProdutos.setText(Integer.toString(produtos.size()));
            lblTotalJogos.setText(Long.toString(jogos));
            lblTotalConsoles.setText(Long.toString(consoles));
            lblStatus.setText("Catalogo atualizado com sucesso.");
            lblStatus.getStyleClass().setAll("success-text");

        } catch (IllegalStateException ex) {
            produtos.clear();
            lblTotalProdutos.setText("0");
            lblTotalJogos.setText("0");
            lblTotalConsoles.setText("0");
            lblStatus.setText(ex.getMessage());
            lblStatus.getStyleClass().setAll("error-text");
        }
    }
}
