package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.IColaborador;
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
    private String nombreImagen;
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreImagen = "No";
    }

    @FXML
    private void accionRegistrarUsuario(ActionEvent evento) {

        if (validarCamposVacios()) {
            System.out.println("Campos vacios");
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
            colaborador.setImagenPerfil(nombreImagen);
            colaborador.setTipoPago(cbTipoPago.getValue());

            boolean[] validaciones = metodosColaborador.validarCampos(colaborador, tfRecontrasena.getText());
            
            if(validaciones[8]){
                if(metodosColaborador.registrarColaborador(colaborador)){
                    System.out.println("Usuario guardado");
                }else{
                    System.out.println("No se pudo guardar");
                }
            }else{
                System.out.println("Campos invalidos");
            }
        }

    }

    @FXML
    private void as(ActionEvent evento) {
        Image imagen = new Image("");
        
    }

    @FXML
    private void rellenarComboboxTipoPago() {
        ObservableList<String> fichas = FXCollections.observableArrayList("Quisenal", "Mensual");
        cbTipoPago.setItems(fichas);
    }

    private boolean validarCamposVacios() {
        return tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfTelefono.getText().isEmpty()
                || tfCorreo.getText().isEmpty() || tfDireccion.getText().isEmpty() || tfUsuario.getText().isEmpty()
                || tfContrasena.getText().isEmpty() || tfRecontrasena.getText().isEmpty() || cbTipoPago.getValue() == null
                || tfMonto.getText().isEmpty() || nombreImagen.equals("No");
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
//    File rutaImagen = new File(System.getProperty("user.home") + "\\imagenesAredEspacio\\perfil.jpg");
        if (rutaImagen == null) {
            System.out.println("no es imagen");
        } else {
            Image image = null;
            String rutaNueva = System.getProperty("user.home") + "\\imagenesAredEspacio\\imagenesColaboradores";
            String rutaOringen = rutaImagen.getAbsolutePath();
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"'+rutaOringen+'"').append(" ").append('"'+rutaNueva+'"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c",comando.toString());
            builder.redirectErrorStream(true);
            Process proceso = builder.start();
            try {
                image = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imgPerfil.setImage(image);
            nombreImagen = rutaImagen.getName();
        }
    }
    
    @FXML
    private void restringirEspacios(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);

        if (caracter == ' ') {
            evento.consume();
        }
    }
}
