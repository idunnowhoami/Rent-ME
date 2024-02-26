package Controllers;

import Model.*;
import Utilidades.BaseDados;
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

public class AnchorPaneCadastroFuncionarios {

    private final ObservableList<Utilizador> utilizadores = FXCollections.observableArrayList();

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnRemover;

    @FXML
    private Label labelEmailFuncionario;

    @FXML
    private Label labelIdFuncionario;
    @FXML
    private Label labelTipo;

    @FXML
    private TableView<Utilizador> tabelaViewFuncionarios;


    public void lerUtilizadorDaBaseDeDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador");

            while (resultado.next()) {
                Utilizador aux;

                int idRole = resultado.getInt("id_role");
                if (idRole == 2) {
                    aux = new UtilizadorFuncionario(
                            resultado.getInt("id_util"),
                            resultado.getString("user_name"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);
                }
            }
            basedados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {
        TableColumn<Utilizador, String> colunaID = new TableColumn<>("ID");
        TableColumn<Utilizador, String> colunaTipo = new TableColumn<>("Tipo");

        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tabelaViewFuncionarios.getColumns().add(colunaID);

        tabelaViewFuncionarios.getColumns().add(colunaTipo);

        // Ler clientes da base de dados
        lerUtilizadorDaBaseDeDados();


        // Definir os dados da tabela
        tabelaViewFuncionarios.setItems(utilizadores);

    }

    @FXML
    void informacaoTabelaFuncionarios(MouseEvent event) {
        Utilizador utilizador = tabelaViewFuncionarios.getSelectionModel().getSelectedItem();
        if (utilizador != null) {
            labelIdFuncionario.setText(String.valueOf(utilizador.getId()));
            labelEmailFuncionario.setText(utilizador.getEmail());
            labelTipo.setText(String.valueOf(utilizador.getTipo()));
        } else {
            labelIdFuncionario.setText("");
            labelEmailFuncionario.setText("");
            labelTipo.setText("");
        }
    }

    @FXML
    void clickInserir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AplicacaoIniciar.class.getResource("RegistoFuncionariosDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registar funcionários");
        stage.setScene(scene);
        stage.show();

    }


}
