package Model;

public abstract class Utilizador {

    public Utilizador(int id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;

        if (id > proximoID){
            proximoID = id;
        }
        if (id == 0){
            this.id = ++proximoID;
        }

    }
    private String email;
    private String password;
    private static int proximoID;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public abstract TipoUtilizador getTipo();
}
