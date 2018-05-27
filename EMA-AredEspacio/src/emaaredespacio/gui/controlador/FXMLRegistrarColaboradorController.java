package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.utilerias.Imagen;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLRegistrarColaboradorController implements Initializable {
    
    @FXML
    private ImageView imgPerfil;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfApellidos;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private JFXTextField tfCorreo;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private JFXTextField tfUsuario;
    @FXML
    private JFXPasswordField tfContrasena;
    @FXML
    private JFXPasswordField tfRecontrasena;
    @FXML
    private JFXTextField tfMonto;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXButton btnRegistrar;
    @FXML
    private JFXComboBox<String> cbTipoPago;
    private Stage stage;
    private File imagen;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
    }
    
    @FXML
    private void accionRegistrarUsuario(ActionEvent evento) {
        
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacios o no ha seleccionado una imagen");
        } else {
            IColaborador metodosColaborador = new Colaborador();
            Colaborador colaborador = new Colaborador();
            
            colaborador.setNombre(tfNombre.getText());
            colaborador.setApellidos(tfApellidos.getText());
            colaborador.setTelefono(tfTelefono.getText());
            colaborador.setCorreo(tfCorreo.getText());
            colaborador.setDireccion(tfDireccion.getText());
            colaborador.setMontoAPagar(tfMonto.getText());
            colaborador.setNombreUsuario(tfUsuario.getText());
            colaborador.setContraseña(tfContrasena.getText());
            colaborador.setTipoPago(cbTipoPago.getValue());
            
            boolean[] validaciones = metodosColaborador.validarCampos(colaborador, tfRecontrasena.getText());
            if (validaciones[8]) {
                if (metodosColaborador.validarNombreUsuario(colaborador.getNombreUsuario())) {
                    colaborador.setImagenPerfil(colaborador.getNombreUsuario() + ".jpg");
                    if (metodosColaborador.registrarColaborador(colaborador)) {
                        Imagen.moverImagen(imagen, colaborador.getNombreUsuario(), Imagen.COLABORADOR);
                        MensajeController.mensajeInformacion("El colaborador ha sido guardado exitosamente");
                        limpiarCampos();
                    } else {
                        MensajeController.mensajeAdvertencia("Ha ocurrido un error al guardar el colaborador");
                    }
                } else {
                    MensajeController.mensajeAdvertencia("El nombre de usuario que usted ingreso no esta disponible favor de ingresar otro");
                }
            } else {
                MensajeController.mensajeAdvertencia("Hay campos invalidos, cheque los datos ingresados");
            }
        }
        
    }
    
    @FXML
    private void rellenarComboboxTipoPago() {
        ObservableList<String> fichas = FXCollections.observableArrayList("Quinsenal", "Mensual");
        cbTipoPago.setItems(fichas);
    }
    
    private boolean validarCamposVacios() {
        return tfNombre.getText().trim().isEmpty() || tfApellidos.getText().trim().isEmpty() || tfTelefono.getText().trim().isEmpty()
                || tfCorreo.getText().trim().isEmpty() || tfDireccion.getText().trim().isEmpty() || tfUsuario.getText().trim().isEmpty()
                || tfContrasena.getText().trim().isEmpty() || tfRecontrasena.getText().trim().isEmpty() || cbTipoPago.getValue() == null
                || tfMonto.getText().trim().isEmpty();
    }
    
    @FXML
    private void elegirImagen() throws IOException {
        FileChooser elegir = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Add Files(*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extensionPNG = new FileChooser.ExtensionFilter("Add Files(*.png)", "*.png");
        FileChooser.ExtensionFilter extensionJPEG = new FileChooser.ExtensionFilter("Add Files(*.jpeg)", "*.jpeg");
        elegir.getExtensionFilters().add(extension);
        elegir.getExtensionFilters().add(extensionPNG);
        elegir.getExtensionFilters().add(extensionJPEG);
        File rutaImagen = elegir.showOpenDialog(stage);
        if (rutaImagen == null) {
            MensajeController.mensajeAdvertencia("No es una imagen");
        } else {
            Image image = null;
            imagen = rutaImagen;
            try {
                image = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imgPerfil.setImage(image);
        }
    }
    
    @FXML
    private void restringirEspacios(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        
        if (caracter == ' ') {
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoNombre(KeyEvent evento) {
        restringir50Caracteres(evento, tfNombre.getText());
    }
    
    @FXML
    private void restringirCampoCorreo(KeyEvent evento) {
        restringir50Caracteres(evento, tfCorreo.getText());
    }
    
    @FXML
    private void restringirCampoDireccion(KeyEvent evento) {
        restringir50Caracteres(evento, tfDireccion.getText());
    }
    
    @FXML
    private void restringirCampoApellidos(KeyEvent evento) {
        restringir50Caracteres(evento, tfApellidos.getText());
    }
    
    @FXML
    private void restringirCampoTelefono(KeyEvent evento) {
        char cadena = evento.getCharacter().charAt(0);
        
        if (Character.isDigit(cadena) && tfTelefono.getText().length() < 10) {
            
        } else {
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoNombreUsuario(KeyEvent evento) {
        if (tfUsuario.getText().length() < 30) {
            
        } else {
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoPago(KeyEvent evento) {
        char cadena = evento.getCharacter().charAt(0);
        if (Character.isDigit(cadena) && tfMonto.getText().length() < 6) {
            
        } else {
            evento.consume();
        }
        
    }
    
    private void restringir50Caracteres(KeyEvent evento, String cadena) {
        if (cadena.length() > 49) {
            evento.consume();
        }
    }
    
    private void limpiarCampos() {
        tfTelefono.setText("");
        tfCorreo.setText("");
        tfContrasena.setText("");
        tfRecontrasena.setText("");
        tfMonto.setText("");
        tfNombre.setText("");
        tfApellidos.setText("");
        tfUsuario.setText("");
        tfDireccion.setText("");
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        imagen = null;
    }
}
