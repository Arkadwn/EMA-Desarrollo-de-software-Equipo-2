package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IAlumno;
import emaaredespacio.modelo.IGrupo;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.Inscripcion;
import emaaredespacio.modelo.PagoAlumno;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLVisualizarHistorialDePagosDeAlumnoController implements Initializable {

    @FXML
    private VBox vboxContenedor;
    @FXML
    private JFXComboBox<Grupo> cbGrupos;
    @FXML
    private JFXComboBox<Alumno> cbAlumnos;
    @FXML
    private JFXTextField tfMonto;
    @FXML
    private JFXButton btnGuardar;
    private PagoAlumno pagoSeleccionado;
    @FXML
    private JFXComboBox<String> cbTipoPago;
    @FXML
    private JFXComboBox<Grupo> cbEdicionGrupo;
    @FXML
    private JFXComboBox<Alumno> cbEdicionAlumno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbTipoPago.setItems(FXCollections.observableArrayList("Inscripcion","Mensualidad"));
        
        cargarGrupos(new Inscripcion().buscarGruposDeColaborador(Integer.parseInt(System.getProperty("idColaborador"))));
        
        cbGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Grupo>() {
            @Override
            public void changed(ObservableValue<? extends Grupo> observable, Grupo oldValue, Grupo newValue) {
                if(newValue != null){
                    IInscripcion metodos = new Inscripcion();
                    cargarAlumnos(metodos.sacarInscripcionesDeGrupo(newValue.getIdGrupo()));
                }
            }
        });
    }

    @FXML
    private void accionBuscar(ActionEvent evento) {
        
    }

    @FXML
    private void restringirLetras(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        
        if(Character.isDigit(caracter) && ((JFXTextField)evento.getSource()).getText().length() < 6){
            
        }else{
            evento.consume();
        }
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        
    }
    
    private void llenarComboBoxs(int idAlumno, int idGrupo){
        
    }
    
    private void cargarAlumnos(List<Alumno> lista){
        cbAlumnos.getItems().clear();
        cbAlumnos.setItems(FXCollections.observableArrayList(lista));
    }
    
    private void cargarGrupos(List<Grupo> lista){
        cbGrupos.getItems().clear();
        cbGrupos.setItems(FXCollections.observableArrayList(lista));
    }
    
    public void cargarEdicion(PagoAlumno pago){
        if(pago != null){
            pagoSeleccionado = pago;
            
            IInscripcion metodos = new Inscripcion();
            cargarAlumnos(metodos.sacarInscripcionesDeGrupo(pago.getIdGrupo()));

            cargarGrupos(new Inscripcion().buscarGruposDeColaborador(Integer.parseInt(System.getProperty("idColaborador"))));
            
            IAlumno metodoAlumno = new Alumno();
            Alumno alumno = metodoAlumno.buscarAlumnoPorId(pago.getMatricula());
            IGrupo metodoGrupo = new Grupo();
            Grupo grupo = metodoGrupo.buscarGrupoPorId(pago.getIdGrupo());
            cbAlumnos.getSelectionModel().select(alumno);
            cbGrupos.getSelectionModel().select(grupo);
        }
    }
}