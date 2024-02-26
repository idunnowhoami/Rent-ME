package Controllers;

import Model.Cliente;
import Model.ClienteEmpresa;
import Model.ClienteParticular;
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

public class AnchorPaneCadastroClientes {

        @FXML
        private Label LabelClienteData_nasc;
        @FXML
         private Label labelSaldoConta;

        @FXML
        private Label LabelClienteMorada;

        @FXML
        private Label LabelClienteNif;

        @FXML
        private Label LabelClienteNome;

        @FXML
        private Button btnAlterar;

        @FXML
        private Button btnInserir;

        @FXML
        private Button btnRemover;

        @FXML
        private Label labelClienteID;

        @FXML
        private TableView<Cliente> tabelaViewClientes;

        private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();


    @FXML
    void cliqueBtnInserir(ActionEvent event) throws IOException {
        Cliente cliente = tabelaViewClientes.getSelectionModel().getSelectedItem();

        // se o cliente for do tipo empresarial, abri o menu inserir clientes empresariais (dialog)
        if (cliente instanceof ClienteEmpresa){
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(AplicacaoIniciar.class.getResource("AnchorPaneCadastroClientesEmpresaDialog.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Registar clientes empresariais");
            stage.setScene(scene);
            stage.showAndWait();

            DataSingleton data = DataSingleton.getInstance();
            clientes.add(data.getData());

        } else if (cliente instanceof ClienteParticular){
            //senao abrir o de clientes particulares (dialog)
            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader1 = new FXMLLoader(AplicacaoIniciar.class.getResource("AnchorPaneCadastroClienteParticularDialog.fxml"));
            Scene scene1 = new Scene(fxmlLoader1.load());
            stage1.setTitle("Registar clientes particulares");
            stage1.setScene(scene1);
            stage1.showAndWait();

            DataSingleton data = DataSingleton.getInstance();
            clientes.add(data.getData());

        }



    }

    public void lerClientesDaBaseDeDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Cliente");

            while (resultado.next()) {
                Cliente aux;

                // enquanto existirem registos, vou ler 1 a 1
                int idRole = resultado.getInt("id_role");
                if (idRole == 1) {
                    aux = new ClienteParticular(
                            resultado.getInt("id_cliente"),
                            resultado.getString("nome"),
                            resultado.getString("morada"),
                            resultado.getString("nif"),
                            resultado.getDate("data_nasc").toLocalDate()
                    );
                    clientes.add(aux);

                } else if (idRole == 2) {
                    aux = new ClienteEmpresa(
                            resultado.getInt("id_cliente"),
                            resultado.getString("nome"),
                            resultado.getString("morada"),
                            resultado.getString("nif"),
                            resultado.getDouble("conta_corrente")
                    );
                    clientes.add(aux);
                }
            }
            basedados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadClientes(){
        TableColumn<Cliente, String> colunaID = new TableColumn<>("ID");
        TableColumn<Cliente, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Cliente, String> colunaTipo = new TableColumn<>("Tipo");

        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tabelaViewClientes.getColumns().add(colunaID);
        tabelaViewClientes.getColumns().add(colunaNome);
        tabelaViewClientes.getColumns().add(colunaTipo);

        // Ler clientes da base de dados
        lerClientesDaBaseDeDados();


        // Definir os dados da tabela
        tabelaViewClientes.setItems(clientes);
    }

    public void initialize() {
        loadClientes();

    }

    @FXML
    void informacaoTabela(MouseEvent event) {

        Cliente cliente = tabelaViewClientes.getSelectionModel().getSelectedItem();
        labelClienteID.setText(String.valueOf(cliente.getId()));
        LabelClienteNome.setText(cliente.getNome());
        LabelClienteNif.setText(cliente.getNif());
        LabelClienteMorada.setText(cliente.getMorada());

        //se for do tipo cliente empresarial
        if (cliente instanceof ClienteEmpresa){
            labelSaldoConta.setText(String.valueOf(((ClienteEmpresa) cliente).getContaCorrente()));
            LabelClienteData_nasc.setText("");

        } else if (cliente instanceof ClienteParticular){
            LabelClienteData_nasc.setText(String.valueOf((((ClienteParticular) cliente).getDataDeNascimento())));
            labelSaldoConta.setText("");
        }

    }


}





