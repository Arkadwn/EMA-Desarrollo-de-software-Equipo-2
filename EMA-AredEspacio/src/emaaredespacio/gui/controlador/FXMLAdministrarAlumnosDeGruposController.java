package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.Inscripcion;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLAdministrarAlumnosDeGruposController implements Initializable {

    @FXML
    private TableView<Alumno> tbAlumnos;
    @FXML
    private TableColumn<Alumno, String> clNombreAlumnos;
    @FXML
    private TableView<Grupo> tbGrupos;
    @FXML
    private TableColumn<Grupo, String> clNombreGrupo;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnInscribir;
    @FXML
    private JFXButton btnDarDeBajaAlumno;
    @FXML
    private JFXButton btnActivarInscripcion;
    private boolean inscribir;
    @FXML
    private Label lbMensualidad;
    @FXML
    private Label lbPromocion;
    @FXML
    private Label lbTotal;
    @FXML
    private ImageView imgPerfil;
    @FXML
    private JFXTextField tfNombreGrupo;
    @FXML
    private JFXTextField tfNombreAlumno;
    @FXML
    private JFXTextField tfPrecio;
    @FXML
    private JFXTextField tfMensualidad;
    @FXML
    private JFXTextField tfTotal;
    @FXML
    private JFXComboBox<String> cbPromocion;
    @FXML
    private JFXButton btnBaja;
    private Grupo grupoSeleccionado;
    private Alumno alumnoSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grupoSeleccionado = null;
        alumnoSeleccionado = null;
        inscribir = true;
        clNombreAlumnos.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        clNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("tipoDeBaile"));
        llenarTablaGrupos(new Inscripcion().buscarGruposDeColaborador(1));
        llenarTablaAlumnos(new Alumno().buscarAlumno(""));
    }    
    
    private void llenarTablaGrupos(List<Grupo> grupos){
        tbGrupos.getItems().clear();
        tbGrupos.setItems(FXCollections.observableArrayList(grupos));
    }
    
    private void llenarTablaAlumnos(List<Alumno> alumnos){
        tbAlumnos.getItems().clear();
        tbAlumnos.setItems(FXCollections.observableArrayList(alumnos));
    }
    
    @FXML
    private void cargarDatosGrupo(){
        if(tbGrupos.getSelectionModel().getSelectedIndex() >= 0){
            grupoSeleccionado = tbGrupos.getSelectionModel().getSelectedItem();
            tfNombreAlumno.setText("");
            alumnoSeleccionado = null;
            
            tfNombreGrupo.setText(grupoSeleccionado.getTipoDeBaile());
        }
    }
    
    @FXML
    private void cargarDatosAlumno(){
        if(tbAlumnos.getSelectionModel().getSelectedIndex() >= 0){
            alumnoSeleccionado = tbAlumnos.getSelectionModel().getSelectedItem();
            
            tfNombreAlumno.setText(alumnoSeleccionado.getNombreCompleto());
        }
    }
    
    @FXML
    private void accionIncripcion(ActionEvent evento){
        if(inscribir){
            if(validarCampos()){
                MensajeController.mensajeAdvertencia("Hay campos vacios");
            }else{
                tfTotal.setText(tfPrecio.getText());
                IInscripcion metodos = new Inscripcion();
                Inscripcion inscripcion = new Inscripcion(grupoSeleccionado.getIdGrupo(), alumnoSeleccionado.getMatricula(),Integer.parseInt(tfMensualidad.getText()), Integer.parseInt(tfTotal.getText()), sacarFecha(new Date()));
                
                if(metodos.inscribirAlumno(inscripcion)){
                    MensajeController.mensajeInformacion("Se ha inscrito el alumno");
                }else{
                    MensajeController.mensajeAdvertencia("No se ha podido inscribir el alumno");
                }
            }
        }else{
            
        }
    }
    
    private boolean validarCampos(){
        return tfPrecio.getText().isEmpty() || tfMensualidad.getText().isEmpty();
    }
    
    private String sacarFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(fecha);
    }
    
    private void vaciarCampos(){
        
    }
    
    @FXML
    private void activarDardeBaja(){
        lbMensualidad.setVisible(false);
        lbPromocion.setVisible(false);
        lbTotal.setVisible(false);
        cbPromocion.setVisible(false);
        btnInscribir.setVisible(false);
        btnDarDeBajaAlumno.setVisible(true);
        btnBuscar.setVisible(true);
        tfPalabraClave.setVisible(true);
        tbGrupos.getItems().clear();
        tbAlumnos.getItems().clear();
        
        
    }
}
