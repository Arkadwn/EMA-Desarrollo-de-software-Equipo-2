/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.ICliente;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLModificarClienteController implements Initializable {

    @FXML
    private JFXTextField tfNombre;

    @FXML
    private JFXTextField tfCorreo;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private ImageView imgPerfil;
    @FXML
    private CheckBox checkEstado;
    @FXML
    private TableView<Cliente> tbListaClientes;
    @FXML
    private TableColumn<Cliente, String> columnaNombre;

    private String nombreImagen;
    private List<Cliente> lista;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;
    private Cliente seleccion;
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreImagen = "No";
        lista = new ArrayList();
        Image imagen = new Image("emaaredespacio/imagenes/perfil.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        tfTelefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfTelefono.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
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
        buscarTodos();
    }

    @FXML
    private void quitaSeleccion(MouseEvent event) {
        tbListaClientes.getSelectionModel().clearSelection();
    }

    private void llenarTabla() {
        tbListaClientes.getItems().clear();
        tbListaClientes.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void posicion(MouseEvent event) {
        if (tbListaClientes.getSelectionModel().getSelectedIndex() >= 0) {
            Image imagen = null;
            seleccion = tbListaClientes.getSelectionModel().getSelectedItem();
            tfNombre.setText(seleccion.getNombre());
            tfDireccion.setText(seleccion.getDireccion());
            if (seleccion.getTelefono() != null) {
                tfTelefono.setText(seleccion.getTelefono());
            }
            if (seleccion.getCorreoElectronico() != null) {
                tfCorreo.setText(seleccion.getCorreoElectronico());
            }
            if (!seleccion.getImagenPerfil().equals("No")) {
                File rutaImagen = new File(System.getProperty("user.home") + "\\imagenesAredEspacio\\imagenesAlumnos\\" + seleccion.getImagenPerfil());
                try {
                    imagen = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
                }
                imgPerfil.setImage(imagen);
            }
            //nombreImagen = rutaImagen.getName();
            checkEstado.setSelected(seleccion.getEstado().equals("A"));
        }
    }

    @FXML
    private void accionBuscar() {
        if (tfPalabraClave.getText().isEmpty()) {
            MensajeController.mensajeInformacion("No se ha ingresado ningún caracter");
        } else {
            ICliente metodosCliente = new Cliente();
            lista.clear();
            lista = metodosCliente.buscarCliente(tfPalabraClave.getText());
            llenarTabla();
        }
    }

    private boolean validarFormatoCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }

    public void buscarTodos() {
        ICliente metodosCliente = new Cliente();
        lista.clear();
        lista = metodosCliente.buscarClientes();
        llenarTabla();
    }

    public boolean validarCamposVacios() {
        boolean validaciones = true;
        if (!tfNombre.getText().trim().isEmpty() && !tfDireccion.getText().trim().isEmpty()) {
            validaciones = false;
        }
        return validaciones;
    }

    @FXML
    private void accionGuardarCambios(ActionEvent event) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos obligatorios vacíos");
        } else {
            ICliente metodosCliente = new Cliente();
            Cliente cliente = new Cliente();
            cliente.setId(seleccion.getId());
            cliente.setNombre(tfNombre.getText());
            cliente.setCorreoElectronico(tfCorreo.getText());
            cliente.setDireccion(tfDireccion.getText());
            cliente.setImagenPerfil(nombreImagen);
            cliente.setTelefono(tfTelefono.getText());
            if (checkEstado.isSelected()) {
                cliente.setEstado("A");
            } else {
                cliente.setEstado("B");
            }

            if (tfCorreo.getText().isEmpty()) {
                if (metodosCliente.guardarDatos(cliente)) {
                    MensajeController.mensajeInformacion("Cliente modificado exitosamente");
                    vaciarCampos();
                } else {
                    MensajeController.mensajeAdvertencia("No se pudo modificar el cliente");
                }
            } else {
                System.out.println("no vacio");
                if (validarFormatoCorreo(tfCorreo.getText())) {
                    if (metodosCliente.guardarDatos(cliente)) {
                        MensajeController.mensajeInformacion("Cliente modificado exitosamente");
                        vaciarCampos();
                    } else {
                        MensajeController.mensajeAdvertencia("No se pudo modificar el cliente");
                    }
                } else {
                    MensajeController.mensajeAdvertencia("El correo no tiene un formato correcto");
                }
            }
        }
    }

    public void vaciarCampos() {
        tfNombre.setText("");
        tfCorreo.setText("");
        tfDireccion.setText("");
        tfTelefono.setText("");
        checkEstado.setSelected(false);
        Image imagen = new Image("emaaredespacio/imagenes/perfil.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        seleccion = null;
        lista.clear();
        buscarTodos();
        llenarTabla();
    }

    @FXML
    public void elegirImagen() throws IOException {
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
            MensajeController.mensajeAdvertencia("No es una imagen");
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
}
