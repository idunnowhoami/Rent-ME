package Model;

public class Carro extends Veiculo{

    public Carro (String matricula, Marca marca, Modelo modelo, String cor, int anoFabrico, String nLugares, boolean estado, double precoDia) {
        super(matricula, marca, modelo, cor, anoFabrico, nLugares, estado, precoDia);
    }

    public Carro(String matricula, Marca marca, Modelo modelo) {
        super(matricula, marca, modelo);
    }

    public Carro() {
        super();
    }


    @Override
    public TipoVeiculo getTipo() {
        return TipoVeiculo.Carro;
    }
}
