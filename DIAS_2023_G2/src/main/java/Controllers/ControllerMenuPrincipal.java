package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ControllerMenuPrincipal {


    @FXML
    private Menu MenuItemRegisto;

    @FXML
    private Menu MenuItemRegisto1;

    @FXML
    private MenuItem MenuItemRegistoClientes;

    @FXML
    private MenuItem MenuItemRegistoCondutores;

    @FXML
    private MenuItem MenuItemRegistoFuncionarios;

    @FXML
    private MenuItem MenuItemRegistoVeiculos;

    @FXML
    private MenuItem MenuItemRepacaoVeiculos;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Menu menuItemPrecos;

    @FXML
    private MenuItem tabelaSeguros;

    @FXML
    private MenuItem veiculosPorDia;
    public void handleMenuItemRegistoClientes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneCadastroClientes.fxml"));
            AnchorPane root = loader.load();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleMenuIntemRegistoVeiculos(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneCadastroVeiculos.fxml"));
            AnchorPane root = loader.load();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

public void handleMenuIntemRegistoFuncionarios(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneCadastroFuncionarios.fxml"));
            AnchorPane root = loader.load();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

}

public void handleMenuRegistoCondutores(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneCadastroCondutores.fxml"));
            AnchorPane root = loader.load();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
}

public void handleMenuReservasVeiculos(){
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneAluguer.fxml"));
        AnchorPane root = loader.load();

        anchorPane.getChildren().setAll(root);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void handleMenuDevolucao(){
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneDevolucao.fxml"));
        AnchorPane root = loader.load();

        anchorPane.getChildren().setAll(root);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void handleMenuItemRegistoReparacao(){
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPaneReparacao.fxml"));
        AnchorPane root = loader.load();

        anchorPane.getChildren().setAll(root);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void handleMenuPrecoVeiculo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AnchorPanePrecoVeiculo.fxml"));
            AnchorPane root = loader.load();

            anchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //handleMenuPrecosSeguros

}
