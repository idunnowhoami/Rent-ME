package Model;

import java.time.LocalDate;

public class Condutor {
    public Condutor(int id, String nome, String morada, String cc, String nr_cartaCond, LocalDate dataInicioCarta, LocalDate dataNasc) {
        this.id = id;
        this.nome = nome;
        this.morada = morada;
        this.cc = cc;
        this.nr_cartaCond = nr_cartaCond;
        this.dataInicioCarta = dataInicioCarta;
        this.dataNasc = dataNasc;
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
    private String cc;
    private String nr_cartaCond;
    private LocalDate dataInicioCarta;
    private LocalDate dataNasc;

    public Condutor() {

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

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getNr_cartaCond() {
        return nr_cartaCond;
    }

    public void setNr_cartaCond(String nr_cartaCond) {
        this.nr_cartaCond = nr_cartaCond;
    }

    public LocalDate getDataInicioCarta() {
        return dataInicioCarta;
    }

    public void setDataInicioCarta(LocalDate dataInicioCarta) {
        this.dataInicioCarta = dataInicioCarta;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    @Override
    public String toString() {
        return id + " | " +  nome;

    }
}
