package Controllers;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AnchorPaneCadastroVeiculos {

    private final ObservableList<Veiculo> veiculos = FXCollections.observableArrayList();

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnRemover;

    @FXML
    private Label labelAnoFabriVeiculo;

    @FXML
    private Label labelMarca;

    @FXML
    private Label labelCorVeiculo;

    @FXML
    private Label labelEstadoVeicu;

    @FXML
    private Label labelMatriculaVeiculo;

    @FXML
    private Label labelModeloVeiculo;

    @FXML
    private Label labemNLugares;

    @FXML
    private TableView<Veiculo> tableViewVeiculos;

    public void lerVeiculosDaBaseDeDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT Veiculo.*, Marca.nome AS nome_marca, Modelo.nome AS nome_modelo FROM Veiculo INNER JOIN Marca ON Veiculo.id_marca = Marca.id_marca INNER JOIN Modelo ON Veiculo.id_modelo = Modelo.id_modelo");



            while (resultado.next()) {
                Marca marca = new Marca();
                Modelo modelo = new Modelo();
                marca.setNome(resultado.getString("nome_marca"));
                modelo.setMarca(marca);
                modelo.setNome(resultado.getString("nome_modelo"));
                Carro aux = new Carro(
                        resultado.getString("matricula"),
                        marca,
                        modelo,
                        resultado.getString("cor"),
                        resultado.getInt("ano_fabrico"),
                        resultado.getString("n_lugares"),
                        resultado.getBoolean("estado"),
                        resultado.getDouble("preco_dia")
                );
                veiculos.add(aux);
            }

            basedados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        TableColumn<Veiculo, String> colunaMatricula = new TableColumn<>("Matrícula");
        TableColumn<Veiculo, String> colunaMarca = new TableColumn<>("Marca");
        TableColumn<Veiculo, String> colunaModelo = new TableColumn<>("Modelo");

        colunaMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        tableViewVeiculos.getColumns().add(colunaMatricula);
        tableViewVeiculos.getColumns().add(colunaMarca);
        tableViewVeiculos.getColumns().add(colunaModelo);

        // Ler veículos da base de dados
        lerVeiculosDaBaseDeDados();

        // Definir os dados da tabela
        tableViewVeiculos.setItems(veiculos);
    }



    @FXML
    void informacaoTabelaVeiculo(MouseEvent event) {
        Veiculo carro = tableViewVeiculos.getSelectionModel().getSelectedItem();
        labelMatriculaVeiculo.setText(carro.getMatricula());
        labelMarca.setText(carro.getMarca().getNome());
        labelModeloVeiculo.setText(carro.getModelo().getNome());
        labelCorVeiculo.setText(carro.getCor());
        labelAnoFabriVeiculo.setText(String.valueOf(carro.getAnoFabrico()));
        labemNLugares.setText(carro.getnLugares());

        String estado = carro.isEstado() ? "Não disponível" : "Disponível";
        labelEstadoVeicu.setText(estado);
    }




    @FXML
    void clickBtnInserir(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AplicacaoIniciar.class.getResource("RegistoVeiculosDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registar veículos");
        stage.setScene(scene);
        stage.showAndWait();

        DataSingleton data = DataSingleton.getInstance();
        veiculos.add(data.getDataVeiculo());

    }

}
