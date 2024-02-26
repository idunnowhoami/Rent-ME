package Controllers;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistoVeiculosDialog implements Initializable {

    private DataSingleton dadosCompartilhados =  DataSingleton.getInstance();
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;
    @FXML
    private ComboBox<Marca> comboBoxMarca;

    @FXML
    private ComboBox<Modelo> comboBoxModelo;

    @FXML
    private TextField textoAnoFabric;

    @FXML
    private TextField textoCor;

    @FXML
    private TextField textoMatricula;

    @FXML
    private TextField labelPrecoDia;



    @FXML
    private TextField textoNLugares;
    ArrayList<Veiculo> veiculos = new ArrayList<>();
    ObservableList<Marca> listaDeMarcas = FXCollections.observableArrayList();
    ObservableList<Modelo> listaDeModelos = FXCollections.observableArrayList();

    public void gravarVeiculosParaBaseDados() throws SQLException {
        for (Veiculo veiculo : veiculos) {
            if (veiculo instanceof Carro) {
                    BaseDados baseDados = new BaseDados();
                    baseDados.Ligar();
                    String marcaQuery = "SELECT id_marca FROM Marca WHERE nome = '" + veiculo.getMarca().getNome() + "'";
                    ResultSet marcaResultado = baseDados.Selecao(marcaQuery);
                    int idMarca = 0;

                    if (marcaResultado.next()) {
                        idMarca = marcaResultado.getInt("id_marca");
                    }


                    String modeloQuery = "SELECT id_modelo FROM Modelo WHERE nome = '" + veiculo.getModelo().getNome() + "'";
                    ResultSet modeloResult = baseDados.Selecao(modeloQuery);

                    int idModelo = 0;
                    if (modeloResult.next()) {
                        idModelo = modeloResult.getInt("id_modelo");
                    }


                    String query = "INSERT INTO Veiculo (matricula, id_marca, id_modelo, cor, ano_fabrico, n_lugares, estado, preco_dia) " +
                            "VALUES ('" + veiculo.getMatricula() + "', " + idMarca + ", " +
                            idModelo + ", '" + veiculo.getCor() + "', " + veiculo.getAnoFabrico() + ", " +
                            veiculo.getnLugares() + ", '" + veiculo.isEstado() + "', " + veiculo.getPrecoDia() + ")";

                    baseDados.Executar(query);
                    baseDados.Desligar();
            }
        }
    }



    @FXML
    void clickConfirmar(ActionEvent event) throws IOException {
        Veiculo veiculo=null;
        try {
            String matricula = textoMatricula.getText();
            Marca marca = comboBoxMarca.getSelectionModel().getSelectedItem();
            Modelo modelo = comboBoxModelo.getSelectionModel().getSelectedItem();
            String cor = textoCor.getText();
            int anoFabrico = Integer.parseInt(textoAnoFabric.getText());
            String nLugares = textoNLugares.getText();
            double precoDia = Double.parseDouble(labelPrecoDia.getText());

            veiculo = new Carro(matricula, marca, modelo, cor, anoFabrico, nLugares, true, precoDia);

            veiculos.add(veiculo);

            dadosCompartilhados.setDataVeiculo(veiculo);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gravarVeiculosParaBaseDados();
            currentStage.close();

            Mensagens.Informacao("Novo veículo", "Novo veículo inserido com sucesso! na base de dados.");

        } catch (Exception e) {
            e.printStackTrace();
            if (veiculos.contains(veiculo))
            {
                veiculos.remove(veiculo);
                Mensagens.Erro("Erro", "Ocorreu um erro ao gravar o veículo: "+e.getMessage());
            }
        }
    }

    @FXML
    void clickMarca(ActionEvent event) {
        Marca marca = comboBoxMarca.getSelectionModel().getSelectedItem();

        if (marca != null) {
            try {
                BaseDados baseDados = new BaseDados();
                baseDados.Ligar();

                ResultSet rs = baseDados.Selecao("SELECT Modelo.nome FROM Modelo INNER JOIN Marca ON Marca.id_marca = Modelo.id_marca WHERE Marca.nome = '" + marca.getNome() + "'");

                ArrayList<Modelo> listaModelos = new ArrayList<>();

                while (rs.next()) {
                    String nomeModelo = rs.getString("nome");
                    Modelo modelo = new Modelo(nomeModelo);
                    listaModelos.add(modelo);
                }

                baseDados.Desligar();

                comboBoxModelo.setItems(FXCollections.observableArrayList(listaModelos));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void getMarcas() {
        listaDeMarcas.clear();

        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();

            ResultSet rs = basedados.Selecao("SELECT * FROM Marca");

            while (rs.next()) {
                String nomeMarca = rs.getString("nome");
                Marca marca = new Marca(nomeMarca);
                listaDeMarcas.add(marca);


                // Defina o modelo da lista de marcas
                comboBoxMarca.setItems(listaDeMarcas);
            }

            basedados.Desligar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void getModelos () {
        listaDeModelos.clear();

        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();

            ResultSet rs = basedados.Selecao("SELECT * FROM Modelo");

            while (rs.next()) {
                String nomeModelo = rs.getString("nome");
                Modelo modelo = new Modelo(nomeModelo);

                listaDeModelos.add(modelo);

                // Defina o modelo da lista de modelos
                comboBoxModelo.setItems(listaDeModelos);
            }

            basedados.Desligar();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

    @FXML void clickCancelar (ActionEvent event){
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void initialize (URL location, ResourceBundle resources){
        getMarcas();
        getModelos();

    }
}

