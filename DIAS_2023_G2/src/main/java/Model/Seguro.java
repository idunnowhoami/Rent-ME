package Model;

public class Seguro {

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public Seguro() {
    }

    public Seguro(String tipoSeguro, double valor) {
        this.valor = valor;
        this.tipoSeguro = tipoSeguro;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    private double valor;
    private String tipoSeguro;

    @Override
    public String toString() {
        return tipoSeguro;
    }
}
