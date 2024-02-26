package Controllers;

import Model.Cliente;
import Model.ClienteParticular;
import Utilidades.BaseDados;
import Utilidades.DataSingleton;
import Utilidades.Mensagens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AnchorPaneCadastroClienteParticularDialog {

   private final DataSingleton dadosCompartilhados =  DataSingleton.getInstance();

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField labelDataNasc;

    @FXML
    private TextField labelNomeCliente;

    @FXML
    private TextField labelNomeMorada;

    @FXML
    private TextField labelNumNif;

    ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public void gravarClientesParticularesParaBaseDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();

            for (Cliente aux : clientes) {
                if (aux instanceof ClienteParticular) {
                    basedados.Executar("INSERT INTO Cliente (nome, nif, data_nasc, morada, id_role) VALUES ('" + aux.getNome() + "', '" + aux.getNif() + "', '" + ((ClienteParticular) aux).getDataDeNascimento() + "', '" + aux.getMorada() + "',1)");
                }
            }


            basedados.Desligar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clickConfirmar(ActionEvent event) throws IOException {
        Cliente clienteParticular = null;
        try {
            String nomeCliente = labelNomeCliente.getText();
            String morada = labelNomeMorada.getText();
            String nif = labelNumNif.getText();

            String dataNascimentoString = labelDataNasc.getText();
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            clienteParticular = new ClienteParticular(0, nomeCliente, morada, nif, dataNascimento);
            clientes.add(clienteParticular);

            dadosCompartilhados.setData(clienteParticular);

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gravarClientesParticularesParaBaseDados();
            currentStage.close();

            Mensagens.Informacao("Novo Cliente", "Novo cliente inserido com sucesso! na base de dados.");

        } catch (Exception e) {
            e.printStackTrace();
            if (clientes.contains(clienteParticular)) {
                clientes.remove(clienteParticular);
                Mensagens.Erro("Erro", "Ocorreu um erro ao gravar o cliente: " + e.getMessage());
            }

        }
    }

    @FXML
    void clickCancelar(ActionEvent event) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
    }

}
