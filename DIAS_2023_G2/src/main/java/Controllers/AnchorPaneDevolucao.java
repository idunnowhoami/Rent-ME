package Controllers;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AnchorPaneDevolucao {

    @FXML
    private Button btnDevolver;

    @FXML
    private DatePicker dataDevolucao;

    @FXML
    private TextField labelCustosAdc;

    @FXML
    private TextField labelValorRepa;

    @FXML
    private TableView<Aluguer> tableViewReservas;

    ObservableList<Aluguer> listaAluguer = FXCollections.observableArrayList();

    public void loadAluguers() {

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

    @FXML
    void clickDevolver(ActionEvent event) throws IOException {
        try {
            Double valorAluguer = Double.parseDouble(labelValorRepa.getText());
            Double custoAdicional = Double.parseDouble(labelCustosAdc.getText());
            LocalDate data = dataDevolucao.getValue();

            Aluguer aluguer = this.tableViewReservas.getFocusModel().getFocusedItem();

            aluguer.setValorAluguer(valorAluguer);
            aluguer.setDataDeEntrega(data);
            //reserva.setDescricaoEstado(custoAdicional);
            aluguer.setConcluida(true);
            gravarAluguersBaseDados(aluguer);
            Mensagens.Informacao("Devolução Aluguer", "Aluguer devolvido com sucesso.");
            lerReservasDaBaseDados();
        }catch(SQLException ex){
            Mensagens.Erro("Gravar base dados", "Ocorreu um erro a gravar na base de dados");
        }catch(Exception ex){
            Mensagens.Erro("Devolução Aluguer", "Ocorreu um erro na devolução do alguer. " + ex.getMessage());
        }

    }
    public void gravarAluguersBaseDados(Aluguer aluguer) throws SQLException {

        BaseDados baseDados = new BaseDados();
        baseDados.Ligar();

        String queryVeiculo = "SELECT * FROM Veiculo " +
                "WHERE matricula = '" + aluguer.getVeiculo().getMatricula() + "'" ;

        ResultSet veiculo = baseDados.Selecao(queryVeiculo);

        int idVeiculo = 0;
        if(veiculo.next()){
            idVeiculo = veiculo.getInt("id_veiculo");
        }

        int isConcluida = 0;
        if(aluguer.isConcluida()) isConcluida = 1;
        String query = "UPDATE Aluguer set valor = "+aluguer.getValorAluguer()+", estado = "+isConcluida+" where id_reserva = "+aluguer.getId()+";"+
                "UPDATE VEICULO SET estado = 0 where id_veiculo = " + idVeiculo;

        baseDados.Executar(query);

        baseDados.Desligar();

    }

    private void lerReservasDaBaseDados() {
        try {
            BaseDados baseDados = new BaseDados();
            baseDados.Ligar();

            listaAluguer.clear();
            ResultSet rs = baseDados.Selecao("SELECT Aluguer.id_reserva, Tipo_Seguro.nome AS tipo_seguro, Condutor.nome AS nome_condutor, Veiculo.matricula, Marca.nome AS nome_marca, Modelo.nome AS nome_modelo, Aluguer.id_cliente,Cliente.id_role, Cliente.nome, Tipo_Cliente.nome AS tipo_cliente, Aluguer.data_inicio, Aluguer.data_fim, Aluguer.valor, Aluguer.id_condutor, Aluguer.qnt_deposito, Aluguer.local_entrega, (CASE WHEN Aluguer.estado = 0 THEN 'Não concluído' ELSE 'Concluído' END) as estado " +
                    "FROM Aluguer " +
                    "INNER JOIN Cliente ON Aluguer.id_cliente = Cliente.id_cliente " +
                    "INNER JOIN Tipo_Seguro ON Aluguer.id_seguro = Tipo_Seguro.id_tipo_seguro " +
                    "INNER JOIN Seguro ON Aluguer.id_seguro = Seguro.id_seguro " +
                    "INNER JOIN Condutor on Aluguer.id_condutor = Condutor.id_condutor " +
                    "INNER JOIN Veiculo ON Aluguer.id_veiculo = Veiculo.id_veiculo " +
                    "INNER JOIN Marca ON Veiculo.id_marca = Marca.id_marca " +
                    "INNER JOIN Modelo ON Veiculo.id_modelo = Modelo.id_modelo " +
                    "INNER JOIN Tipo_Cliente ON Tipo_Cliente.id_role = Cliente.id_role where Aluguer.estado = 0");

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

    public void initialize() {
        loadAluguers();
    }

}
