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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private String nombreImagen;
    private Colaborador seleccion;
    @FXML
    private TableView<Colaborador> tbListaColaboradores;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreImagen = "No";
        lista = new ArrayList();
        rellenarComboboxTipoPago();
        seleccion = null;
    }

    @FXML
    private void posicion() {
        if (tbListaColaboradores.getSelectionModel().getSelectedIndex() >= 0) {
            Image imagen = null;
            seleccion = tbListaColaboradores.getSelectionModel().getSelectedItem();
            tfNombre.setText(seleccion.getNombre());
            tfApellidos.setText(seleccion.getApellidos());
            tfTelefono.setText(seleccion.getTelefono());
            tfDireccion.setText(seleccion.getDireccion());
            tfMonto.setText(seleccion.getMontoAPagar());
            tfUsuario.setText(seleccion.getNombreUsuario());
            tfCorreo.setText(seleccion.getCorreo());
            File rutaImagen = new File(System.getProperty("user.home") + "\\imagenesAredEspacio\\imagenesColaboradores\\" + seleccion.getImagenPerfil());
            try {
                imagen = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imgPerfil.setImage(imagen);
            nombreImagen = rutaImagen.getName();
            
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
            System.out.println("No ha ingersado ningun caracter");
        } else {
            IColaborador metodosColaborador = new Colaborador();
            lista.clear();
            lista = metodosColaborador.buscarColaborador(tfPalabraClave.getText());
            llenarTabla();
        }
    }

    private void llenarTabla() {
        tbListaColaboradores.getItems().clear();
        tbListaColaboradores.setItems(FXCollections.observableArrayList(lista));
    }

    public void elegirImagen(ActionEvent elegirImagen) throws IOException {
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
    private void accionGuardarCambios(ActionEvent evento) {
        if (seleccion != null) {
            if (validarCamposVacios()) {
                System.out.println("Campos invalidos");
            } else {
                IColaborador metodosColaborador = new Colaborador();
                Colaborador colaborador = new Colaborador();
                boolean nuevaContraseña = false;
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
                    nuevaContraseña = true;
                }else{
                    colaborador.setContraseña("Acdc619Mljj");
                    reContraseña = "Acdc619Mljj";
                }
                colaborador.setImagenPerfil(nombreImagen);
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
                    if(!nuevaContraseña){
                        colaborador.setContraseña(seleccion.getContraseña());
                    }
                    
                    if(metodosColaborador.editarColaborador(colaborador, nuevaContraseña)){
                        System.out.println("Cambios guardados");
                    }else{
                        System.out.println("Cambios no guardados");
                    }
                    
                }
            }
        } else {
            System.out.println("No ha seleccionado un colaborador");
        }
    }

    private void rellenarComboboxTipoPago() {
        ObservableList<String> fichas = FXCollections.observableArrayList("Quisenal", "Mensual");
        cbTipoPago.setItems(fichas);
    }

    private boolean validarCamposVacios() {
        return tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfTelefono.getText().isEmpty()
                || tfCorreo.getText().isEmpty() || tfDireccion.getText().isEmpty() || tfUsuario.getText().isEmpty()
                || tfMonto.getText().isEmpty();
    }
}
