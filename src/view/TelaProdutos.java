package view;

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
import javafx.scene.layout.VBox;
import model.ProdutoResumo;
import service.ProdutoService;

public class TelaProdutos {

    private final ProdutoService produtoService = new ProdutoService();
    private final ObservableList<ProdutoResumo> produtos = FXCollections.observableArrayList();
    private final Label lblStatus = new Label("Carregando produtos...");
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

        Label eyebrow = new Label("Catalogo");
        eyebrow.getStyleClass().add("eyebrow");

        Label title = new Label("Pesquisa, filtro e leitura unificada de jogos e consoles.");
        title.getStyleClass().add("hero-title");
        title.setWrapText(true);

        Label subtitle = new Label(
                "Esta tela consolida os produtos do legado sem misturar regras de tela com acesso a dados."
        );
        subtitle.getStyleClass().add("muted-text");
        subtitle.setWrapText(true);

        header.getChildren().addAll(eyebrow, title, subtitle);

        VBox body = new VBox(18);
        body.setPadding(new Insets(24));

        TextField txtPesquisa = new TextField();
        txtPesquisa.setPromptText("Pesquisar produto");

        ChoiceBox<String> cbFiltro = new ChoiceBox<>();
        cbFiltro.getItems().addAll("Todos", "Jogos", "Consoles");
        cbFiltro.setValue("Todos");

        Button btnAtualizar = new Button("Atualizar");
        btnAtualizar.getStyleClass().add("secondary-button");

        HBox toolbar = new HBox(12, txtPesquisa, cbFiltro, btnAtualizar);
        txtPesquisa.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(txtPesquisa, Priority.ALWAYS);

        TableView<ProdutoResumo> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("Sem produtos para exibir."));

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
        table.setItems(sortedList);

        lblStatus.getStyleClass().add("muted-text");
        VBox statusStrip = new VBox(lblStatus);
        statusStrip.getStyleClass().add("status-strip");

        btnAtualizar.setOnAction(event -> carregarProdutos());

        body.getChildren().addAll(toolbar, table, statusStrip);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.setTop(header);
        root.setCenter(body);

        carregarProdutos();

        return StyleSupport.createScene(root, 1080, 720);
    }

    private void carregarProdutos() {

        try {
            produtos.setAll(produtoService.listarProdutos());
            lblStatus.setText("Produtos carregados com sucesso.");
            lblStatus.getStyleClass().setAll("success-text");
        } catch (IllegalStateException ex) {
            produtos.clear();
            lblStatus.setText(ex.getMessage());
            lblStatus.getStyleClass().setAll("error-text");
        }
    }
}
