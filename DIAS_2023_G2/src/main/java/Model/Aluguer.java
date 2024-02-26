package Model;

import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Aluguer {

    public Aluguer(int id, Seguro seguro, Veiculo veiculo,Cliente cliente, LocalDate dataDoAluguer, LocalDate dataDeEntrega, double valorAluguer,Condutor condutor, double deposito, String localDeEntrega, boolean concluido) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dataDoAluguer = dataDoAluguer;
        this.dataDeEntrega = dataDeEntrega;
        this.deposito = deposito;
        this.localDeEntrega = localDeEntrega;
        this.condutor = condutor;
        this.seguro = seguro;
        this.valorAluguer = valorAluguer;
        this.concluida = concluido;
        this.id = id;

        if(this.concluida) this.descricaoEstado = "Concluída";
        else this.descricaoEstado = "Não concluída";

    }



    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDataDoAluguer() {
        return dataDoAluguer;
    }

    public void setDataDoAluguer(LocalDate dataDoAluguer) {
        this.dataDoAluguer = dataDoAluguer;
    }

    public LocalDate getDataDeEntrega() {
        return dataDeEntrega;
    }

    public void setDataDeEntrega(LocalDate dataDeEntrega) {
        this.dataDeEntrega = dataDeEntrega;
    }

    public double getDeposito() {
        return deposito;
    }

    public void setDeposito(double deposito) {
        this.deposito = deposito;
    }

    public String getLocalDeEntrega() {
        return localDeEntrega;
    }

    public void setLocalDeEntrega(String localDeEntrega) {
        this.localDeEntrega = localDeEntrega;
    }

    public Pagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(Pagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Condutor getCondutor() {
        return condutor;
    }

    public void setCondutor(Condutor condutor) {
        this.condutor = condutor;
    }

    public String getDescricaoEstado() {
        return descricaoEstado;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    public double getValorAluguer() {
        return valorAluguer;
    }

    public void setValorAluguer(double valorAluguer) {
        this.valorAluguer = valorAluguer;
    }
    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public void setDescricaoEstado(String descricaoEstado) {
        this.descricaoEstado = descricaoEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private Cliente cliente;
    private Veiculo veiculo;
    private LocalDate dataDoAluguer;
    private LocalDate dataDeEntrega;

    private double deposito;

    private String localDeEntrega;

    private Pagamento tipoPagamento;

    private Condutor condutor;

    private Seguro seguro;

    private double valorAluguer;
    private boolean concluida;

    private String descricaoEstado;

    @Override
    public String toString() {
        return "Aluguer{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", veiculo=" + veiculo +
                ", dataDoAluguer=" + dataDoAluguer +
                ", dataDeEntrega=" + dataDeEntrega +
                ", deposito='" + deposito + '\'' +
                ", localDeEntrega='" + localDeEntrega + '\'' +
                ", condutor=" + condutor +
                ", seguro=" + seguro +
                ", valorAluguer=" + valorAluguer +
                '}';
    }
}
