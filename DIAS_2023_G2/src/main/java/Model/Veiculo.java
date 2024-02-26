package Model;

public abstract class Veiculo{

    public Veiculo(String matricula, Marca marca, Modelo modelo, String cor, int anoFabrico, String nLugares, boolean estado, double precoDia) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.anoFabrico = anoFabrico;
        this.nLugares = nLugares;
        this.estado = estado;
        this.precoDia = precoDia;
    }

    private String matricula;

    private Marca marca;
    private Modelo modelo;
    private String cor;
    private int anoFabrico;

    private String nLugares;
    private boolean estado;
    private double precoDia;


    public Veiculo(String matricula, Marca marca, Modelo modelo) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
    }

    public Veiculo() {

    }


    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getAnoFabrico() {
        return anoFabrico;
    }

    public void setAnoFabrico(int anoFabrico) {
        this.anoFabrico = anoFabrico;
    }

    public String getnLugares() {
        return nLugares;
    }

    public void setnLugares(String nLugares) {
        this.nLugares = nLugares;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getPrecoDia() {
        return precoDia;
    }

    public void setPrecoDia(double precoDia) {
        this.precoDia = precoDia;
    }

    @Override
    public String toString() {
        return matricula + " " +
                marca + " " +
        modelo;
    }

    public abstract TipoVeiculo getTipo();
}
