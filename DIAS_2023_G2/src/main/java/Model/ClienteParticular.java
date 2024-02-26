package Model;

import java.time.LocalDate;

public class ClienteParticular extends Cliente {
    public ClienteParticular(int id, String nome, String morada, String nif, LocalDate dataDeNascimento) {
        super(id, nome, morada, nif);
        this.dataDeNascimento = dataDeNascimento;
    }

    private LocalDate dataDeNascimento;

    public ClienteParticular() {
        super();
    }


    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public TipoCliente getTipo() {
        return TipoCliente.Particular;
    }
}
