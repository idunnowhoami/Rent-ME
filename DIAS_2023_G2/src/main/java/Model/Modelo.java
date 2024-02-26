package Model;

public class Modelo {
    public Modelo(Marca marca, String nome) {
        this.nome = nome;
        this.marca = marca;
    }

    public Modelo() {

    }

    public Modelo(String nomeModelo) {
        this.nome = nomeModelo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    private Marca marca;
    private String nome;

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return nome;

    }
}
