package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.IAlumno;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLRegistrarAlumnoController implements Initializable {

    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfApellidos;
    @FXML
    private JFXTextField tfCorreo;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private ImageView imgPerfil;
    private String nombreImagen;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 400, 400, false, true, true);
        imgPerfil.setImage(imagen);
        nombreImagen = "No";
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacios o no ha seleccionado una imagen");
        } else {
            IAlumno metodosAlumnos = new Alumno();
            Alumno alumno = new Alumno();
            alumno.setNombre(tfNombre.getText());
            alumno.setApellidos(tfApellidos.getText());
            alumno.setCorreo(tfCorreo.getText());
            alumno.setDireccion(tfDireccion.getText());
            alumno.setImagenPerfil(nombreImagen);
            alumno.setTelefono(tfTelefono.getText());

            boolean[] validacion = metodosAlumnos.validarCampos(alumno);
            if (validacion[5]) {
                if (metodosAlumnos.guardarAlumno(alumno)) {
                    MensajeController.mensajeInformacion("El alumno ha sido guardado exitosamente");
                    limpiarCampos();
                } else {
                    MensajeController.mensajeAdvertencia("Ha ocurrido un error al guardar el alumno");
                }
            } else {
                MensajeController.mensajeAdvertencia("Hay campos invalidos, cheque los datos ingresados");
            }
        }
    }

    private boolean validarCamposVacios() {
        return tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfTelefono.getText().isEmpty()
                || tfDireccion.getText().isEmpty() || tfCorreo.getText().isEmpty() || nombreImagen.equals("No");
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
        File rutaImagen = elegir.showOpenDialog(null);
//    File rutaImagen = new File(System.getProperty("user.home") + "\\imagenesAredEspacio\\perfil.jpg");
        if (rutaImagen == null) {
            System.out.println("no es imagen");
        } else {
            Image image = null;
            String rutaNueva = System.getProperty("user.home") + "\\imagenesAredEspacio\\imagenesAlumnos";
            String rutaOringen = rutaImagen.getAbsolutePath();
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"' + rutaOringen + '"').append(" ").append('"' + rutaNueva + '"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
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
    private void restringirCampoNombre(KeyEvent evento) {
        restringir50Caracteres(evento, tfNombre.getText());
    }
    
    @FXML
    private void restringirCampoCorreo(KeyEvent evento){
        restringir50Caracteres(evento, tfCorreo.getText());
    }

    @FXML
    private void restringirCampoTelefono(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);

        if (Character.isDigit(caracter) && tfTelefono.getText().length() < 10) {

        } else {
            evento.consume();
        }
    }

    @FXML
    private void restringirCampoDireccion(KeyEvent evento) {
        restringir50Caracteres(evento, tfDireccion.getText());
    }

    private void restringir50Caracteres(KeyEvent evento, String cadena) {
        if (cadena.length() > 49) {
            evento.consume();
        }
    }

    @FXML
    private void restringirCampoApellidos(KeyEvent evento) {
        if (tfApellidos.getText().length() > 100) {
            evento.consume();
        }
    }
    
    private void limpiarCampos(){
        tfNombre.setText("");
        tfApellidos.setText("");
        tfCorreo.setText("");
        tfDireccion.setText("");
        tfTelefono.setText("");
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 400, 400, false, true, true);
        imgPerfil.setImage(imagen);
        nombreImagen = "No";
    }
}
