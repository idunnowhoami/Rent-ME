package Model;

public class UtilizadorFuncionario extends Utilizador {

    public UtilizadorFuncionario(int id, String email, String password) {
        super(id, email, password);
    }

    public TipoUtilizador getTipo() {
        return TipoUtilizador.Funcionario;
    }
}
