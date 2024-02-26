package Model;

public class Marca {
    public Marca(String nome) {
        this.nome = nome;
    }

    public Marca() {
    }


    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;

    }
}
