package Controllers;

import Model.Cliente;
import Model.ClienteEmpresa;
import Model.ClienteParticular;
import Model.Condutor;
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
import java.time.format.DateTimeFormatter;

public class AnchorPaneCadastroCondutores {

    private final ObservableList<Condutor> condutores = FXCollections.observableArrayList();

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnInserir;

    @FXML
    private Button btnRemover;

    @FXML
    private TableColumn<?, ?> colunaIdCondutor;

    @FXML
    private TableColumn<?, ?> colunaNomeCondutor;

    @FXML
    private Label dataInicioCartaConducao;

    @FXML
    private Label labelCcCondutor;

    @FXML
    private Label labelData_Nasc;

    @FXML
    private Label labelIdCondutor;

    @FXML
    private Label labelMoradaCondutor;

    @FXML
    private Label labelNomeCondutor;

    @FXML
    private Label labelNumCartaCondCondutor;

    @FXML
    private TableView<Condutor> tableViewCondutores;
    public void lerCondutoresDaBaseDeDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Condutor");

            while (resultado.next()) {
                Condutor aux;
                    aux = new Condutor(
                            resultado.getInt("id_condutor"),
                            resultado.getString("nome"),
                            resultado.getString("morada"),
                            resultado.getString("cc"),
                            resultado.getString("nr_cartaCondu"),
                            resultado.getDate("data_inicio_carta").toLocalDate(),
                            resultado.getDate("data_nasc").toLocalDate()
                    );
                    condutores.add(aux);

                }
                basedados.Desligar();
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
        }

        public void initialize(){
                TableColumn<Condutor, String> colunaID = new TableColumn<>("ID");
                TableColumn<Condutor, String> colunaNome = new TableColumn<>("Nome");

                colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
                colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
                tableViewCondutores.getColumns().add(colunaID);
                tableViewCondutores.getColumns().add(colunaNome);


                // Ler clientes da base de dados
                lerCondutoresDaBaseDeDados();


                // Definir os dados da tabela
            tableViewCondutores.setItems(condutores);

            }

    @FXML
    void informacaoTabelaCondutor(MouseEvent event) {

        Condutor condutor = tableViewCondutores.getSelectionModel().getSelectedItem();
        labelIdCondutor.setText(String.valueOf(condutor.getId()));
        labelNomeCondutor.setText(condutor.getNome());
        labelMoradaCondutor.setText(condutor.getMorada());
        labelCcCondutor.setText(condutor.getCc());
        labelNumCartaCondCondutor.setText(condutor.getNr_cartaCond());
        dataInicioCartaConducao.setText(condutor.getDataInicioCarta().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        labelData_Nasc.setText(condutor.getDataNasc().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));


    }


    @FXML
    void clickInserir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(AplicacaoIniciar.class.getResource("RegistoCondutoresDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registar condutores");
        stage.setScene(scene);
        stage.show();

    }

}
