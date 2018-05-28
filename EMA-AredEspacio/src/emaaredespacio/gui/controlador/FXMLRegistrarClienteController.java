/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.ICliente;
import emaaredespacio.utilerias.Imagen;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarClienteController implements Initializable {

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
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    DecimalFormat format = new DecimalFormat("#.0");
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private AnchorPane anchorPane;
    private File imagen;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image("emaaredespacio/imagenes/perfil.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        nombreImagen = "No";
        anchorPane.setStyle("-fx-background-image: url('emaaredespacio/imagenes/fondo.jpg')");
        tfTelefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfTelefono.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (tfTelefono.getText().length() > 10) {
                    String s = tfTelefono.getText().substring(0, 10);
                    tfTelefono.setText(s);
                }
            }
        });
        tfNombre.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tfNombre.getText().length() > 100) {
                    String s = tfNombre.getText().substring(0, 100);
                    tfNombre.setText(s);
                }
            }
        });
        tfApellidos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tfApellidos.getText().length() > 100) {
                    String s = tfApellidos.getText().substring(0, 100);
                    tfApellidos.setText(s);
                }
            }
        });

        tfCorreo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tfCorreo.getText().length() > 50) {
                    String s = tfCorreo.getText().substring(0, 50);
                    tfCorreo.setText(s);
                }
            }
        });

        tfDireccion.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tfDireccion.getText().length() > 50) {
                    String s = tfDireccion.getText().substring(0, 50);
                    tfDireccion.setText(s);
                }
            }
        });

    }

    private boolean validarFormatoCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }

    public boolean validarCamposVacios() {
        boolean validaciones = true;
        if (!tfNombre.getText().trim().isEmpty() && !tfApellidos.getText().trim().isEmpty() && !tfDireccion.getText().trim().isEmpty()) {
            validaciones = false;
        }
        return validaciones;
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos obligatorios vacíos");
        } else {
            ICliente metodosCliente = new Cliente();
            Cliente cliente = new Cliente();
            cliente.setNombre(tfNombre.getText() + " " + tfApellidos.getText());
            cliente.setCorreoElectronico(tfCorreo.getText());
            cliente.setDireccion(tfDireccion.getText());
            cliente.setImagenPerfil(nombreImagen);
            cliente.setTelefono(tfTelefono.getText());
            cliente.setImagenPerfil(cliente.getNombre()+ ".jpg");
            if (tfCorreo.getText().isEmpty()) {
                if (metodosCliente.guardarCliente(cliente)) {
                    MensajeController.mensajeInformacion("Cliente guardado exitosamente");
                    Imagen.moverImagen(imagen, cliente.getNombre(), Imagen.CLIENTE);
                    limpiarCampos();
                } else {
                    MensajeController.mensajeAdvertencia("No se pudo guardar el cliente");
                }
            } else {
                System.out.println("no vacio");
                if (validarFormatoCorreo(tfCorreo.getText())) {
                    if (metodosCliente.guardarCliente(cliente)) {
                        MensajeController.mensajeInformacion("Cliente guardado exitosamente");
                        limpiarCampos();
                    } else {
                        MensajeController.mensajeAdvertencia("No se pudo guardar el cliente");
                    }
                } else {
                    MensajeController.mensajeAdvertencia("El correo no tiene un formato correcto");
                }
            }

        }
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
    
    public void limpiarCampos(){
        tfNombre.setText("");
        tfApellidos.setText("");
        tfCorreo.setText("");
        tfDireccion.setText("");
        tfTelefono.setText("");
        Image imagen = new Image("emaaredespacio/imagenes/perfil.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        imagen = null;
    }

    @FXML
    private void salir(ActionEvent event) {
        anchorPane.getChildren().remove(this);
    }

}
