package Model;

import java.time.LocalDate;

public abstract class Cliente {
    public Cliente(int id, String nome, String morada, String nif) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;

        if (id > proximoId){
            proximoId = id;
        }
        if (id == 0){
            this.id = ++proximoId;
        }
    }
    private static int proximoId = 0;
    private int id;
    private String nome;
    private String morada;
    private String nif;
    private TipoCliente tipo;

    public Cliente() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }


    public abstract TipoCliente getTipo();

    public void setTipo(TipoCliente tipo){
        this.tipo = tipo;
    }



    @Override
    public String toString() {
        return id + " | " + nome + " | " + getTipo();
    }
}
