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
            tfTelefono.setText(seleccion.getTelefono());
            tfDireccion.setText(seleccion.getDireccion());
            tfCorreo.setText(seleccion.getCorreoElectronico());

            File rutaImagen = new File(System.getProperty("user.home") + "\\imagenesAredEspacio\\imagenesAlumnos\\" + seleccion.getImagenPerfil());
            try {
                imagen = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //imgPerfil.setImage(imagen);
            //nombreImagen = rutaImagen.getName();
            checkEstado.setSelected(seleccion.getEstado().equals("A"));
        }
    }

    @FXML
    private void accionBuscar() {
        if (tfPalabraClave.getText().isEmpty()) {
            System.out.println("No ha ingersado ningun caracter");
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

    public boolean[] validarCampos() {
        boolean[] validaciones = new boolean[5];

        //Nombre
        validaciones[0] = tfNombre.getText().trim().length() >= 2 && tfNombre.getText().trim().length() <= 400;
        //Correo
        validaciones[1] = validarFormatoCorreo(tfCorreo.getText());
        //Direccion
        validaciones[2] = tfDireccion.getText().trim().length() >= 2 && tfDireccion.getText().trim().length() <= 50;
        //Telefono
        validaciones[3] = tfTelefono.getText().trim().length() == 10;

        validaciones[4] = validaciones[0] && validaciones[1] && validaciones[2] && validaciones[3];

        return validaciones;
    }

    @FXML
    private void accionGuardarCambios(ActionEvent event) {
        if (validarCamposVacios()) {
            System.out.println("Campos vacios");
        } else {
            ICliente metodosClientes = new Cliente();
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

            boolean[] validacion = validarCampos();
            if (validacion[4]) {
                if (metodosClientes.guardarDatos(cliente)) {
                    System.out.println("Cliente guardado");
                } else {
                    System.out.println("No se pudo");
                }
            } else {
                System.out.println("Campos invaldios");
            }
        }
    }

    @FXML
    public void elegirImagen(ActionEvent elegirImagen) throws IOException {
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

    private boolean validarCamposVacios() {
        return tfNombre.getText().trim().isEmpty() || tfTelefono.getText().trim().isEmpty()
                || tfDireccion.getText().trim().isEmpty() || tfCorreo.getText().trim().isEmpty();
    }
}
