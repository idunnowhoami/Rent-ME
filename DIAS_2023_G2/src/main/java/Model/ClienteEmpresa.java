package Model;

public class ClienteEmpresa extends Cliente {

    public ClienteEmpresa(int id, String nome, String morada, String nif, double contaCorrente) {
        super(id, nome, morada, nif);
        this.contaCorrente = contaCorrente;
    }
    private double contaCorrente;

    public ClienteEmpresa() {

    }

    public double getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(double contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    @Override
    public TipoCliente getTipo() {
        return TipoCliente.Empresa;
    }
}
