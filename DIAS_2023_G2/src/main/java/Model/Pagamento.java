package Model;

public class Pagamento {

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Pagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    private String tipoPagamento;

    @Override
    public String toString() {
        return tipoPagamento;

    }
}
