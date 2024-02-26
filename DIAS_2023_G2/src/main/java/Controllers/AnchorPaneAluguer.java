package Controllers;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class AnchorPaneAluguer {


    @FXML
    private Button btnAlugar;

    @FXML
    private ComboBox<Cliente> comboBoxCliente;

    @FXML
    private ComboBox<Condutor> comboBoxCondutor;

    @FXML
    private TextField labelLocalEntregas;

    @FXML
    private ComboBox<Veiculo> comboBoxVeiculos;
    @FXML
    private ComboBox<Seguro> comboBoxTipoSeguro;

    @FXML
    private DatePicker dataAluguer;

    @FXML
    private DatePicker dataDevolucao;

    @FXML
    private TextField labelDeposito;

    @FXML
    private TableView<Aluguer> tableViewReservas;

    ObservableList<Aluguer> listaAluguer = FXCollections.observableArrayList();
    ObservableList<Seguro> listaSeguros = FXCollections.observableArrayList();

    // ObservableList<Pagamento> listaPagamento = FXCollections.observableArrayList();
    ObservableList<Condutor> listaCondutores = FXCollections.observableArrayList();
    ObservableList<Veiculo> listaVeiculos = FXCollections.observableArrayList();
    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    void clickAlugar(ActionEvent event) throws SQLException, IOException {

        try {
            Seguro seguro = comboBoxTipoSeguro.getSelectionModel().getSelectedItem();
            Veiculo veiculo = comboBoxVeiculos.getSelectionModel().getSelectedItem();
            Cliente cliente = comboBoxCliente.getSelectionModel().getSelectedItem();
            LocalDate dataInicio = dataAluguer.getValue();
            LocalDate dataFim = dataDevolucao.getValue();
            Condutor condutor = comboBoxCondutor.getSelectionModel().getSelectedItem();
            double deposito = Double.parseDouble(labelDeposito.getText());
            String localEntrega = labelLocalEntregas.getText();

            double precoPorDia = (veiculo.getPrecoDia());
            double valorSeguro = (seguro.getValor());
            long diasAluguel = ChronoUnit.DAYS.between(dataInicio, dataFim);
            double valor = diasAluguel * precoPorDia + valorSeguro;

            Aluguer aluguer = new Aluguer(0, seguro, veiculo, cliente, dataInicio, dataFim, valor, condutor, deposito, localEntrega, false);
            veiculo.setEstado(false);

            listaAluguer.add(aluguer);

            gravarAluguersBaseDados(aluguer);

            RegistoVeiculosDialog gravar = new RegistoVeiculosDialog();
            gravar.gravarVeiculosParaBaseDados();
        }catch(SQLException ex){
            Mensagens.Erro("Gravar base dados", "Ocorreu um erro a gravar na base de dados");
        }catch(Exception ex){
            Mensagens.Erro("Inserir Aluguer", "Ocorreu um erro a inserir aluguer. " + ex.getMessage());
        }


    }

    public void gravarAluguersBaseDados(Aluguer aluguer) throws SQLException {

            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            String seguroQuery = "SELECT * FROM Seguro " +
                    "INNER JOIN Tipo_Seguro ON Seguro.tipo_seguro = Tipo_Seguro.id_tipo_seguro " +
                    "WHERE nome = '" + aluguer.getSeguro().getTipoSeguro() + "'";

            ResultSet tipoSeguro = baseDados.Selecao(seguroQuery);

            int idTipoSeguro = 0;
            if(tipoSeguro.next()){
                idTipoSeguro = tipoSeguro.getInt("tipo_seguro");
            }

            String queryVeiculo = "SELECT * FROM Veiculo " +
                    "INNER JOIN Marca ON Veiculo.id_marca = Marca.id_marca " +
                    "INNER JOIN Modelo ON Veiculo.id_modelo = Modelo.id_modelo " +
                    "WHERE matricula = '" + aluguer.getVeiculo().getMatricula() + "'" ;

            ResultSet veiculo = baseDados.Selecao(queryVeiculo);

            int idVeiculo = 0;
            if(veiculo.next()){
                idVeiculo = veiculo.getInt("id_veiculo");
            }

            String queryCliente = "SELECT * FROM Cliente WHERE id_cliente = " + aluguer.getCliente().getId();

            ResultSet cliente = baseDados.Selecao(queryCliente);

            int idCliente = 0;
            if(cliente.next()){
                idCliente = cliente.getInt("id_cliente");
            }

            String queryCondutor = "SELECT * FROM Condutor WHERE id_condutor = " + aluguer.getCondutor().getId();

            ResultSet condutor = baseDados.Selecao(queryCondutor);

            int idCondutor = 0;
            if(condutor.next()){
                idCondutor = condutor.getInt("id_condutor");
            }

            int isConcluida = 0;
            if(aluguer.isConcluida()) isConcluida = 1;
            String query = "INSERT INTO Aluguer (id_seguro, id_veiculo, id_cliente, data_inicio, data_fim, valor, id_condutor, qnt_deposito, local_entrega, estado) " +
                    "VALUES (" + idTipoSeguro + "," + idVeiculo + "," + idCliente + ",'" + aluguer.getDataDoAluguer() + "','" + aluguer.getDataDeEntrega() + "',"
                    + aluguer.getValorAluguer() + "," + idCondutor + "," + aluguer.getDeposito() + ",'" + aluguer.getLocalDeEntrega() + "'," + isConcluida + "); " +
                    "UPDATE VEICULO SET estado = 1 where id_veiculo = " + idVeiculo;

            baseDados.Executar(query);

            String queryAluguer = "SELECT TOP 1 id_reserva FROM Aluguer ORDER BY id_reserva DESC";

            ResultSet ultimoAluguer = baseDados.Selecao(queryAluguer);
            if (ultimoAluguer.next()){
                aluguer.setId(ultimoAluguer.getInt("id_reserva"));
            }
            baseDados.Desligar();

    }


    public void loadAluguers(){

        TableColumn<Aluguer, String> idAluguer = new TableColumn<>("Id da Reserva");
        TableColumn<Aluguer, String> tipoSeguro = new TableColumn<>("Seguro");
        TableColumn<Aluguer, String> veiculo = new TableColumn<>("Veículo");
        TableColumn<Aluguer, String> cliente = new TableColumn<>("Cliente");
        TableColumn<Aluguer, String> dataInicio = new TableColumn<>("Data do Aluguer");
        TableColumn<Aluguer, String> dataFim = new TableColumn<>("Data da devolução");
        TableColumn<Aluguer, String> valor = new TableColumn<>("Valor a pagar");
        TableColumn<Aluguer, String> condutor = new TableColumn<>("Condutor");
        TableColumn<Aluguer, String> deposito = new TableColumn<>("Depósito");
        TableColumn<Aluguer, String> localEntrega = new TableColumn<>("Local de entrega");
        TableColumn<Aluguer, String> estado = new TableColumn<>("Estado");


        idAluguer.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipoSeguro.setCellValueFactory(new PropertyValueFactory<>("seguro"));
        veiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));
        cliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        dataInicio.setCellValueFactory(new PropertyValueFactory<>("dataDoAluguer"));
        dataFim.setCellValueFactory(new PropertyValueFactory<>("dataDeEntrega"));
        valor.setCellValueFactory(new PropertyValueFactory<>("valorAluguer"));
        condutor.setCellValueFactory(new PropertyValueFactory<>("condutor"));
        deposito.setCellValueFactory(new PropertyValueFactory<>("deposito"));
        localEntrega.setCellValueFactory(new PropertyValueFactory<>("localDeEntrega"));
        estado.setCellValueFactory(new PropertyValueFactory<>("descricaoEstado"));


        tableViewReservas.getColumns().add(idAluguer);
        tableViewReservas.getColumns().add(tipoSeguro);
        tableViewReservas.getColumns().add(veiculo);
        tableViewReservas.getColumns().add(cliente);
        tableViewReservas.getColumns().add(dataInicio);
        tableViewReservas.getColumns().add(dataFim);
        tableViewReservas.getColumns().add(valor);
        tableViewReservas.getColumns().add(condutor);
        tableViewReservas.getColumns().add(deposito);
        tableViewReservas.getColumns().add(localEntrega);
        tableViewReservas.getColumns().add(estado);

        lerReservasDaBaseDados();

        tableViewReservas.setItems(listaAluguer);

        }

    private void lerReservasDaBaseDados() {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            ResultSet rs = baseDados.Selecao("SELECT Aluguer.id_reserva, Tipo_Seguro.nome AS tipo_seguro, Condutor.nome AS nome_condutor, Veiculo.matricula, Marca.nome AS nome_marca, Modelo.nome AS nome_modelo, Aluguer.id_cliente,Cliente.id_role, Cliente.nome, Tipo_Cliente.nome AS tipo_cliente, Aluguer.data_inicio, Aluguer.data_fim, Aluguer.valor, Aluguer.id_condutor, Aluguer.qnt_deposito, Aluguer.local_entrega, (CASE WHEN Aluguer.estado = 0 THEN 'Não concluído' ELSE 'Concluído' END) as estado " +
                    "FROM Aluguer " +
                    "INNER JOIN Cliente ON Aluguer.id_cliente = Cliente.id_cliente " +
                    "INNER JOIN Tipo_Seguro ON Aluguer.id_seguro = Tipo_Seguro.id_tipo_seguro " +
                    "INNER JOIN Seguro ON Aluguer.id_seguro = Seguro.id_seguro " +
                    "INNER JOIN Condutor on Aluguer.id_condutor = Condutor.id_condutor " +
                    "INNER JOIN Veiculo ON Aluguer.id_veiculo = Veiculo.id_veiculo " +
                    "INNER JOIN Marca ON Veiculo.id_marca = Marca.id_marca " +
                    "INNER JOIN Modelo ON Veiculo.id_modelo = Modelo.id_modelo " +
                    "INNER JOIN Tipo_Cliente ON Tipo_Cliente.id_role = Cliente.id_role");

            while (rs.next()) {

                int id = rs.getInt("id_reserva");

                Seguro seguro = new Seguro();
                seguro.setTipoSeguro(rs.getString("tipo_seguro"));
                seguro.setValor(rs.getDouble("valor"));

                Veiculo veiculo = new Carro();
                veiculo.setMatricula(rs.getString("matricula"));

                Marca marca = new Marca();
                marca.setNome(rs.getString("nome_marca"));
                veiculo.setMarca(marca);

                Modelo modelo = new Modelo();
                modelo.setNome(rs.getString("nome_modelo"));
                veiculo.setModelo(modelo);

                Cliente cliente = null;
                int role = rs.getInt("id_role");
                if( role == 1) {
                    ClienteParticular clienteParticular = new ClienteParticular();
                    clienteParticular.setId(rs.getInt("id_cliente"));
                    clienteParticular.setNome(rs.getString("nome"));
                    cliente = clienteParticular;
                    cliente.setTipo(TipoCliente.Particular);

                } else if( role == 2){
                    ClienteEmpresa clienteEmpresa = new ClienteEmpresa();
                    clienteEmpresa.setId(rs.getInt("id_cliente"));
                    clienteEmpresa.setNome(rs.getString("nome"));
                    cliente = clienteEmpresa;
                    cliente.setTipo(TipoCliente.Empresa);
                }

                LocalDate dataInicio = rs.getDate("data_inicio").toLocalDate();
                LocalDate dataFim = rs.getDate("data_fim").toLocalDate();
                double valor = rs.getDouble("valor");

                Condutor condutor = new Condutor();
                condutor.setId(rs.getInt("id_condutor"));
                condutor.setNome(rs.getString("nome_condutor"));

                double qntDeposito = rs.getDouble("qnt_deposito");
                String localEntrega = rs.getString("local_entrega");

                boolean concluida = rs.getBoolean("estado");

                Aluguer aluguer = new Aluguer(id,seguro, veiculo, cliente, dataInicio, dataFim, valor, condutor, qntDeposito, localEntrega, concluida);

                listaAluguer.add(aluguer);

        }
            baseDados.Desligar();

        } catch (Exception e){

            throw new RuntimeException(e);
        }
    }

    public void loadVeiculos() {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            ResultSet rs = baseDados.Selecao("SELECT Veiculo.matricula, Marca.nome AS marca, Modelo.nome AS modelo, Veiculo.preco_dia " +
                    "FROM Veiculo " +
                    "INNER JOIN Marca ON Veiculo.id_marca = Marca.id_marca " +
                    "INNER JOIN Modelo ON Veiculo.id_modelo = Modelo.id_modelo " +
                    "WHERE Veiculo.estado = 0");


            while (rs.next()) {

                String matricula = rs.getString("matricula");


                String marca = rs.getString("marca");
                Marca marca1 = new Marca();
                marca1.setNome(marca);


                String modelo = rs.getString("modelo");
                Modelo modelo1 = new Modelo();
                modelo1.setNome(modelo);

                Veiculo carro = new Carro(matricula, marca1, modelo1);
                carro.setPrecoDia(rs.getDouble("preco_dia"));
                listaVeiculos.add(carro);

            }

            baseDados.Desligar();

            comboBoxVeiculos.setItems(FXCollections.observableArrayList(listaVeiculos));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadClientes(){
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            ResultSet rs = baseDados.Selecao("SELECT * from Cliente");


            while (rs.next()) {
                int id_role = rs.getInt("id_role");
                int id = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                String nif = rs.getString("nif");
                String morada = rs.getString("morada");


               if (id_role == 1) {
                   String dataNasc = rs.getDate("data_nasc").toString();
                   LocalDate dataNasc1 = LocalDate.parse(dataNasc);

                   ClienteParticular clienteP = new ClienteParticular(id, nome, morada, nif, dataNasc1);
                   listaClientes.add(clienteP);

                } else if (id_role == 2) {
                   double contaCorrente = rs.getDouble("conta_corrente");
                    ClienteEmpresa clienteE = new ClienteEmpresa(id, nome, morada, nif, contaCorrente);
                   listaClientes.add(clienteE);
                }

            }

            baseDados.Desligar();

            comboBoxCliente.setItems(FXCollections.observableArrayList(listaClientes));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadCondutor(){
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            ResultSet rs = baseDados.Selecao("SELECT * from Condutor");


            while (rs.next()) {
                String nome = rs.getString("nome");
                String morada = rs.getString("morada");
                String cc = rs.getString("cc");
                String cartaCond = rs.getString("nr_CartaCondu");
                String dataNasc = rs.getDate("data_inicio_carta").toString();
                LocalDate dataInicioCarta = LocalDate.parse(dataNasc);

                String dataNascimento = rs.getDate("data_nasc").toString();
                LocalDate dataDeNascimento = LocalDate.parse(dataNascimento);

                Condutor condutor = new Condutor(0, nome, morada, cc, cartaCond, dataInicioCarta, dataDeNascimento);
                listaCondutores.add(condutor);

            }

            baseDados.Desligar();

            comboBoxCondutor.setItems(FXCollections.observableArrayList(listaCondutores));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /*
    public void tipoPagamentos(){
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            ResultSet rs = baseDados.Selecao("SELECT * from Pagamento");


            while (rs.next()) {
                String nome = rs.getString("tipo_pagamento");
                Pagamento tipoPagamento = new Pagamento(nome);

                listaPagamento.add(tipoPagamento);

            }

            comboBoxTipoPag.setItems(FXCollections.observableArrayList(listaPagamento));
            baseDados.Desligar();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



     */


    public void tipoSeguro() {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            ResultSet rs = baseDados.Selecao("SELECT Seguro.*, Tipo_Seguro.nome AS tipo_de_seguro " +
                    "FROM Seguro " +
                    "INNER JOIN Tipo_Seguro ON Seguro.tipo_seguro = Tipo_Seguro.id_tipo_seguro");

            while (rs.next()) {
                String nome = rs.getString("tipo_de_seguro");
                double val = rs.getDouble("valor");

                Seguro tipoSeguro1 = new Seguro(nome, val);

                listaSeguros.add(tipoSeguro1);

            }

            baseDados.Desligar();

            comboBoxTipoSeguro.setItems(FXCollections.observableArrayList(listaSeguros));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void initialize() {
        loadVeiculos();
        loadClientes();
        //tipoPagamentos();
        tipoSeguro();
        loadCondutor();
        loadAluguers();
    }
}


