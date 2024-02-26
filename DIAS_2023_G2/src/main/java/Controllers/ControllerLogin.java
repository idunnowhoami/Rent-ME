package Controllers;

import Model.*;
import Utilidades.BaseDados;
import Utilidades.Mensagens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerLogin {

    ArrayList<Utilizador> utilizadores = new ArrayList<>();

    @FXML
    private Button btnLogin;

    @FXML
    private TextField textoEmail;

    @FXML
    private TextField textoPasse;

    public void lerLoginDaBaseDeDados() {
        try {
            BaseDados basedados = new BaseDados();
            basedados.Ligar();
            ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador");

            while (resultado.next()) {
                Utilizador aux;

                // enquanto existirem registos, vou ler 1 a 1
                int idRole = resultado.getInt("id_role");
                if (idRole == 1) {
                    aux = new UtilizadorAdm(
                            resultado.getInt("id_util"),
                            resultado.getString("user_name"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);

                } else if (idRole == 2) {
                    aux = new UtilizadorFuncionario(
                            resultado.getInt("id_util"),
                            resultado.getString("user_name"),
                            resultado.getString("password")
                    );
                    utilizadores.add(aux);
                }
            }
            basedados.Desligar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int verificarLogin(String email, String password) throws SQLException {
        BaseDados basedados = new BaseDados();
        basedados.Ligar();
        ResultSet resultado = basedados.Selecao("SELECT * FROM Utilizador WHERE user_name = '" + email + "' AND password = '" + password + "'");
        if (resultado.next()) {
            return resultado.getInt("id_role");
        } else {
            return 0;
        }
    }

    @FXML
    void clickLogin(ActionEvent event) throws IOException, SQLException {


        lerLoginDaBaseDeDados();

        String username = textoEmail.getText();
        String password = textoPasse.getText();

        if (verificarLogin(username, password) == 1) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(AplicacaoIniciar.class.getResource("MenuPrincipal.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Menu principal!");
            stage.setScene(scene);

            double width = 1325; // Defina a largura desejada da janela
            double height = 770; // Defina a altura desejada da janela
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();

        } else {
            Mensagens.Erro("Erro", "Ocorreu um erro ao realizar login ");
        }
    }
}


