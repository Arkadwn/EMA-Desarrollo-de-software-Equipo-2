package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLMenuPrincipalController implements Initializable {

    private boolean menuDesplegado;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnInicio;
    @FXML
    private AnchorPane barraMenu;
    @FXML
    private AnchorPane panelPrincipal;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuDesplegado = false;
        panelPrincipal.setStyle("-fx-background-image: url('emaaredespacio/imagenes/fondo.jpg');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch;");
        btnInicio.setStyle("-fx-background-image: url('emaaredespacio/imagenes/inicio.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 30px 30px 30px 30px;");
        btnSalir.setStyle("-fx-background-image: url('emaaredespacio/imagenes/salir.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 30px 30px 30px 30px;");
    }
    
    @FXML
    private void mostrarMenu(ActionEvent evento){
        menuDesplegado = !menuDesplegado;
        barraMenu.setVisible(menuDesplegado);
    }
    
    @FXML
    private void ocultarMenu(){
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
}
