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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
public class FXMLEditarColaboradorController implements Initializable {

    @FXML
    private ImageView imgPerfil;
    @FXML
    private CheckBox checkEstado;
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
    private JFXComboBox<String> cbTipoPago;
    @FXML
    private JFXTextField tfMonto;
    @FXML
    private JFXPasswordField tfContraseña;
    @FXML
    private JFXPasswordField tfRecontraseña;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private TableColumn<Colaborador, String> columnaNombre;
    @FXML
    private TableColumn<Colaborador, String> columnaApellidos;
    private List<Colaborador> lista;
    private Stage stage;
    private File imagen;
    private Colaborador seleccion;
    @FXML
    private TableView<Colaborador> tbListaColaboradores;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Label labelContraseña;
    @FXML
    private Label labelRecontraseña;
    private boolean edicionContraseña;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edicionContraseña = false;
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 225, 225, false, true, true);
        imgPerfil.setImage(imagen);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        lista = new ArrayList();
        rellenarComboboxTipoPago();
        seleccion = null;
    }

    @FXML
    private void cargarDatosColaborador() {
        if (tbListaColaboradores.getSelectionModel().getSelectedIndex() >= 0) {
            Image imagen = null;
            seleccion = tbListaColaboradores.getSelectionModel().getSelectedItem();
            tfNombre.setText(seleccion.getNombre().trim());
            tfApellidos.setText(seleccion.getApellidos().trim());
            tfTelefono.setText(seleccion.getTelefono().trim());
            tfDireccion.setText(seleccion.getDireccion().trim());
            tfMonto.setText(seleccion.getMontoAPagar().trim());
            tfUsuario.setText(seleccion.getNombreUsuario().trim());
            tfCorreo.setText(seleccion.getCorreo().trim());
            cbTipoPago.getSelectionModel().select(seleccion.getTipoPago());
            File rutaImagen = new File(System.getProperty("user.home") + "/aredEspacio/imagenesColaboradores/" + seleccion.getImagenPerfil());
            try {
                if(rutaImagen.exists()){
                    imagen = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
                }else{
                    imagen = new Image("emaaredespacio/imagenes/User.jpg", 225, 225, false, true, true);
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imgPerfil.setImage(imagen);
            
            checkEstado.setSelected(seleccion.getEstado().equals("A"));
            

        }
    }

    @FXML
    private void quitaSeleccion() {
        tbListaColaboradores.getSelectionModel().clearSelection();
    }

    @FXML
    private void accionBuscar(ActionEvent evento) {
        if (tfPalabraClave.getText().isEmpty()) {
            MensajeController.mensajeAdvertencia("No ha ingresado nada para realizar la busqueda");
        } else {
            IColaborador metodosColaborador = new Colaborador();
            lista.clear();
            lista = metodosColaborador.buscarColaborador(tfPalabraClave.getText());
            cargarResultadosDeBusqueda();
        }
    }

    private void cargarResultadosDeBusqueda() {
        tbListaColaboradores.getItems().clear();
        tbListaColaboradores.setItems(FXCollections.observableArrayList(lista));
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
    private void accionGuardarCambios(ActionEvent evento) {
        if (seleccion != null) {
            if (validarCamposVacios()) {
                MensajeController.mensajeAdvertencia("Hay campos vacios");
            } else {
                IColaborador metodosColaborador = new Colaborador();
                Colaborador colaborador = new Colaborador();
                String reContraseña = tfRecontraseña.getText();
                colaborador.setNombre(tfNombre.getText());
                colaborador.setApellidos(tfApellidos.getText());
                colaborador.setTelefono(tfTelefono.getText());
                colaborador.setCorreo(tfCorreo.getText());
                colaborador.setDireccion(tfDireccion.getText());
                colaborador.setMontoAPagar(tfMonto.getText());
                colaborador.setNombreUsuario(tfUsuario.getText());
                if(!tfContraseña.getText().isEmpty()){
                    colaborador.setContraseña(tfContraseña.getText());
                }else{
                    colaborador.setContraseña("Acdc619Mljj");
                    reContraseña = "Acdc619Mljj";
                }
                colaborador.setImagenPerfil(seleccion.getImagenPerfil());
                if(cbTipoPago.getValue() != null){
                    colaborador.setTipoPago(cbTipoPago.getValue());
                }else{
                    colaborador.setTipoPago(seleccion.getTipoPago());
                }
                if(checkEstado.isSelected()){
                    colaborador.setEstado("A");
                }else{
                    colaborador.setEstado("B");
                }
                if(reContraseña == null){
                    reContraseña = "";
                }
                
                boolean[] validaciones = metodosColaborador.validarCampos(colaborador, reContraseña);
                
                if(validaciones[8]){
                    colaborador.setIdColaborador(seleccion.getIdColaborador());
                    colaborador.setIdUsuario(seleccion.getIdUsuario());
                    if(!edicionContraseña){
                        colaborador.setContraseña(seleccion.getContraseña());
                    }
                    
                    if(metodosColaborador.editarColaborador(colaborador, edicionContraseña)){
                        if(imagen != null){
                            Imagen.moverImagen(imagen, colaborador.getNombreUsuario(), Imagen.COLABORADOR);
                        }
                        MensajeController.mensajeInformacion("Cambios guardados");
                        limpiarCampos();
                    }else{
                        MensajeController.mensajeAdvertencia("No se han podido guardar los cambios");
                    }
                    
                }else{
                    MensajeController.mensajeAdvertencia("Hay campos invalidos, cheque los datos ingresados");
                }
            }
        } else {
            MensajeController.mensajeInformacion("No ha seleccionado un colaborador");
        }
    }

    private void rellenarComboboxTipoPago() {
        ObservableList<String> fichas = FXCollections.observableArrayList("Quisenal", "Mensual");
        cbTipoPago.setItems(fichas);
    }

    private boolean validarCamposVacios() {
        return tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfTelefono.getText().isEmpty()
                || tfCorreo.getText().isEmpty() || tfDireccion.getText().isEmpty()
                || tfMonto.getText().isEmpty();
    }
    
    @FXML
    private void restringirEspacios(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);

        if (caracter == ' ') {
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoNombre(KeyEvent evento){
        restringir50Caracteres(evento, tfNombre.getText());
    }
    
    @FXML
    private void restringirCampoCorreo(KeyEvent evento){
        restringir50Caracteres(evento, tfCorreo.getText());
    }
    
    @FXML
    private void restringirCampoDireccion(KeyEvent evento){
        restringir50Caracteres(evento, tfDireccion.getText());
    }
    
    @FXML
    private void restringirCampoApellidos(KeyEvent evento){
        restringir50Caracteres(evento, tfApellidos.getText());
    }
    
    @FXML
    private void restringirCampoTelefono(KeyEvent evento){
        char cadena = evento.getCharacter().charAt(0);
        
        if(Character.isDigit(cadena) && tfTelefono.getText().length() < 10){
            
        }else{
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoNombreUsuario(KeyEvent evento){
        if(tfUsuario.getText().length() < 30){
            
        }else{
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoPago(KeyEvent evento){
        char cadena = evento.getCharacter().charAt(0);
        if(Character.isDigit(cadena) && tfMonto.getText().length() < 6){
            
        }else{
            evento.consume();
        }
            
    }
    
    private void restringir50Caracteres(KeyEvent evento, String cadena){
        if(cadena.length() > 49){
            evento.consume();
        }
    }
    
    @FXML
    private void limpiarCampos(){
        tfTelefono.setText("");
        tfCorreo.setText("");
        tfContraseña.setText("");
        tfRecontraseña.setText("");
        tfMonto.setText("");
        tfNombre.setText("");
        tfApellidos.setText("");
        tfUsuario.setText("");
        tfDireccion.setText("");
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 225, 225, false, true, true);
        imgPerfil.setImage(imagen);
        seleccion = null;
        tbListaColaboradores.getItems().clear();
        tfPalabraClave.setText("");
        this.imagen = null;
    }

    @FXML
    private void activarEdicionDeContrasena(ActionEvent event) {
        edicionContraseña = !edicionContraseña;
        labelContraseña.setVisible(edicionContraseña);
        labelRecontraseña.setVisible(edicionContraseña);
        tfContraseña.setVisible(edicionContraseña);
        tfRecontraseña.setVisible(edicionContraseña);
        tfContraseña.setText("");
        tfRecontraseña.setText("");
    }
}
