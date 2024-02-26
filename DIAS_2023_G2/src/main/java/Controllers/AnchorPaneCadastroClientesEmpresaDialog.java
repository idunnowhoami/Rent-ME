package Controllers;

import Model.Cliente;
import Model.ClienteEmpresa;
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


public class AnchorPaneCadastroClientesEmpresaDialog {
    private DataSingleton dadosCompartilhados =  DataSingleton.getInstance();
    public ArrayList<Integer> eliminados = new ArrayList<Integer>();

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField labelMoradaEmpresa;

    @FXML
    private TextField labelNifEmpresa;

    @FXML
    private TextField labelNomeEmpresa;

    @FXML
    private TextField labelValorContaCorrente;

    ArrayList<Cliente> clientes = new ArrayList<Cliente>();

    public void gravarClientesEmpresasParaBaseDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();

            for (Cliente aux : clientes) {
                if (aux instanceof ClienteEmpresa) {
                    basedados.Executar("INSERT INTO Cliente (nome, nif, morada, conta_corrente, id_role) VALUES ('" + aux.getNome() + "', '" + aux.getNif() + "','" + aux.getMorada() + "' , '" + ((ClienteEmpresa) aux).getContaCorrente() + "',2)");
                }
            }

            if (!eliminados.isEmpty()) {
                for (Integer aux : eliminados) {
                    basedados.Executar("DELETE FROM Cliente WHERE id_cliente = " + aux);
                }
                eliminados.clear();
            }

            basedados.Desligar();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void clickConfirmar(ActionEvent event) throws IOException {

        Cliente clienteEmpresa = null;
        try {

            String nomeCliente = labelNomeEmpresa.getText();
            String morada = labelMoradaEmpresa.getText();
            String nif = labelNifEmpresa.getText();
            double saldoConta = Double.parseDouble(labelValorContaCorrente.getText());

            clienteEmpresa = new ClienteEmpresa(0, nomeCliente, morada, nif, saldoConta);
            clientes.add(clienteEmpresa);

            dadosCompartilhados.setData(clienteEmpresa);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gravarClientesEmpresasParaBaseDados();
            currentStage.close();

            Mensagens.Informacao("Novo Cliente", "Novo cliente inserido com sucesso! na base de dados.");

        } catch (Exception e) {
            e.printStackTrace();
            if (clientes.contains(clienteEmpresa)) {
                clientes.remove(clienteEmpresa);
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
