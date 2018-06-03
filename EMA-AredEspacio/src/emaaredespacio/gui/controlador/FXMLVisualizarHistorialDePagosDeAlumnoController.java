package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.IPagoAlumno;
import emaaredespacio.modelo.Inscripcion;
import emaaredespacio.modelo.PagoAlumno;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

        cbTipoPago.setItems(FXCollections.observableArrayList("Inscripcion", "Mensualidad"));
        List<Grupo> grupos = new Inscripcion().buscarGruposDeColaborador(Integer.parseInt(System.getProperty("idColaborador")));
        cargarGrupos(grupos);

        cbGrupos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Grupo>() {
            @Override
            public void changed(ObservableValue<? extends Grupo> observable, Grupo oldValue, Grupo newValue) {
                if (newValue != null) {
                    IInscripcion metodos = new Inscripcion();
                    cargarAlumnos(metodos.sacarInscripcionesDeGrupo(newValue.getIdGrupo()));
                }
            }
        });
    }

    @FXML
    private void accionBuscar(ActionEvent evento) {
        if (cbAlumnos.getValue() != null) {
            vboxContenedor.getChildren().clear();
            IPagoAlumno metodos = new PagoAlumno();

            List<PagoAlumno> resultadoBusqueda = metodos.cargarListaPagosDeAlumnosDeGrupo(cbAlumnos.getValue().getMatricula(), cbGrupos.getValue().getIdGrupo());

            try {
                cargarResultadosDeBusqueda(resultadoBusqueda);
            } catch (IOException ex) {
                Logger.getLogger(FXMLVisualizarHistorialDePagosDeAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            if(cbGrupos.getValue() == null){
                MensajeController.mensajeAdvertencia("No ha seleccionado el grupo ni el alumno");
            }else{
                MensajeController.mensajeAdvertencia("No ha seleccionado el alumno");
            }
        }
    }

    public void quitarElemento(Parent fxml) {
        if (fxml != null) {
            vboxContenedor.getChildren().removeAll(fxml);
        }
    }

    @FXML
    private void restringirLetras(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        if (Character.isDigit(caracter) && ((JFXTextField) evento.getSource()).getText().length() < 6) {

        } else {
            evento.consume();
        }
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (pagoSeleccionado != null) {
            if (validarCampos()) {
                MensajeController.mensajeAdvertencia("Debe seleccionar todos los datos");
            } else {
                IPagoAlumno metodos = new PagoAlumno();
                PagoAlumno pago = new PagoAlumno();
                
                pago.setFechaPago(pagoSeleccionado.getFechaPago());
                pago.setMatricula(cbEdicionAlumno.getValue().getMatricula());
                pago.setIdGrupo(cbEdicionGrupo.getValue().getIdGrupo());
                pago.setIdPagoAlumno(pagoSeleccionado.getIdPagoAlumno());
                pago.setTotal(tfMonto.getText().trim());
                pago.setTipoPago(cbTipoPago.getValue());
                pago.setMonto(pagoSeleccionado.getMonto());
                pago.setPorcentajeDescuento(pagoSeleccionado.getPorcentajeDescuento());
                
                if(metodos.editarPago(pago)){
                    MensajeController.mensajeInformacion("Se han guardado los cambios");
                    vaciarCampos();
                }else{
                    MensajeController.mensajeAdvertencia("No se han podido guardar los cambios");
                }
            }
        }else{
            MensajeController.mensajeAdvertencia("Debe seleccionar un pago");
        }
    }

    private void cargarAlumnos(List<Alumno> lista) {
        cbAlumnos.getItems().clear();
        cbAlumnos.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarGrupos(List<Grupo> lista) {
        cbGrupos.getItems().clear();
        cbGrupos.setItems(FXCollections.observableArrayList(lista));
        cbEdicionGrupo.getItems().clear();
        cbEdicionGrupo.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarAlumnosEdicion(List<Alumno> lista) {
        cbEdicionAlumno.getItems().clear();
        cbEdicionAlumno.setItems(FXCollections.observableArrayList(lista));
    }

    public void cargarEdicion(PagoAlumno pago) {
        if (pago != null) {
            pagoSeleccionado = pago;

            IInscripcion metodos = new Inscripcion();
            List<Alumno> alumnos = metodos.sacarInscripcionesDeGrupo(pago.getIdGrupo());
            cargarAlumnosEdicion(alumnos);
            for (Alumno alumno : alumnos) {
                if (alumno.getMatricula().equals(pago.getMatricula())) {
                    cbEdicionAlumno.getSelectionModel().select(alumno);
                    break;
                }
            }
            ObservableList<Grupo> grupos = cbEdicionGrupo.getItems();
            for (Grupo grupo : grupos) {
                if (grupo.getIdGrupo() == pago.getIdGrupo()) {
                    cbEdicionGrupo.getSelectionModel().select(grupo);
                    break;
                }
            }

            cbTipoPago.getSelectionModel().select(pago.getTipoPago());
            tfMonto.setText(pago.getMonto());
        }
    }

    private void cargarResultadosDeBusqueda(List<PagoAlumno> lista) throws IOException {
        if (lista != null) {
            for (PagoAlumno pago : lista) {
                FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLFormatoPago.fxml"));
                Parent fxml = cargador.load();
                FXMLFormatoPagoController controlador = cargador.getController();
                controlador.cargarPagoAlumno(pago, cbAlumnos.getValue().getNombreCompleto(), cbGrupos.getValue().getTipoDeBaile(), System.getProperty("colaborador"), this);
                vboxContenedor.getChildren().addAll(fxml);
            }
        }else if(lista.isEmpty()){
            MensajeController.mensajeInformacion("No hay pagos de ese alumno");
        }
    }

    private boolean validarCampos() {
        return cbEdicionAlumno.getValue() == null || cbEdicionGrupo.getValue() == null || cbTipoPago.getValue() == null || tfMonto.getText().trim().isEmpty();
    }
    
    private void vaciarCampos(){
        vboxContenedor.getChildren().clear();
        tfMonto.setText("");
        cbAlumnos.getItems().clear();
        cbEdicionAlumno.getItems().clear();
        cbTipoPago.getSelectionModel().select(null);
        cbGrupos.getSelectionModel().select(null);
        cbEdicionGrupo.getSelectionModel().select(null);
    }

    @FXML
    private void accionCancelar(ActionEvent event) {
        vaciarCampos();
    }
}
