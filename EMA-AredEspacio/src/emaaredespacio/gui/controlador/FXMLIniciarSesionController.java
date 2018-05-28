package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.EMAAredEspacio;
import emaaredespacio.modelo.UsuarioSistema;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Adrián Bustamante Zarate
 */
public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnIniciarSesion;
    @FXML
    private JFXPasswordField txtContrasenia;
    @FXML
    private JFXTextField txtUsuario;
    private EMAAredEspacio main;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void btnCerrar(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void autenticar(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        UsuarioSistema user = new UsuarioSistema();
        user.setContrasenia(txtContrasenia.getText());
        user.setUsuario(txtUsuario.getText());
        if(user.autenticarSesion(user)){
            main.desplegarMenuPrincipal(user.getColaborador());
        }else{
            MensajeController.mensajeInformacion("El usuario o la contraseña es erronea");
        }
    }

    public void setMain(EMAAredEspacio main) {
        this.main = main;
    }
    
}
