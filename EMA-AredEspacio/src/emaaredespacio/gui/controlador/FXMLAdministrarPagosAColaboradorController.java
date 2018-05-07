package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.IPagoAColaborador;
import emaaredespacio.modelo.Inscripcion;
import emaaredespacio.modelo.PagoAColaborador;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLAdministrarPagosAColaboradorController implements Initializable {

    @FXML
    private VBox vboxContenedor;
    @FXML
    private ChoiceBox<Colaborador> cbColaborador;
    @FXML
    private ChoiceBox<Grupo> cbGrupoColaborador;
    @FXML
    private ChoiceBox<Alumno> cbAlumno;
    @FXML
    private JFXTextField tfPago;
    @FXML
    private JFXTextArea tfComentario;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    private boolean edicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        edicion = true;
        IColaborador metodos = new Colaborador();
        cargarColaboradores(metodos.buscarColaboradoresEstados("A"));
        
        IPagoAColaborador metodosPago = new PagoAColaborador();
        
        try {
            cargarPagos(metodosPago.buscarPagoAColaborador("", false));
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministrarPagosAColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cbColaborador.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Colaborador>() {
            @Override
            public void changed(ObservableValue<? extends Colaborador> observable, Colaborador oldValue, Colaborador newValue) {
                if (newValue != null) {
                    Inscripcion metodo = new Inscripcion();
                    cargarGruposDeColaborador(metodo.buscarGruposDeColaborador(newValue.getIdColaborador()));
                }
            }
        });

        cbGrupoColaborador.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Grupo>() {
            @Override
            public void changed(ObservableValue<? extends Grupo> observable, Grupo oldValue, Grupo newValue) {
                if (newValue != null) {
                    IInscripcion metodo = new Inscripcion();
                    cargarAlumnosDeUnGrupo(metodo.sacarInscripcionesDeGrupo(newValue.getIdGrupo()));
                }
            }
        });
    }

    private void cargarColaboradores(List<Colaborador> lista) {
        cbColaborador.getItems().clear();
        cbColaborador.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarAlumnosDeUnGrupo(List<Alumno> lista) {
        cbAlumno.getItems().clear();
        cbAlumno.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarGruposDeColaborador(List<Grupo> lista) {
        cbGrupoColaborador.getItems().clear();
        cbGrupoColaborador.setItems(FXCollections.observableArrayList(lista));
    }

    private void cargarPagos(List<PagoAColaborador> lista) throws IOException {
        vboxContenedor.getChildren().clear();

        for (PagoAColaborador pago : lista) {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLFormatoPago.fxml"));
            Parent fxml = cargador.load();
            FXMLFormatoPagoController controlador = cargador.getController();
            controlador.cargarAlumno(pago, this);
            vboxContenedor.getChildren().addAll(fxml);
        }

    }

    @FXML
    private void accionGuardar() {
        if (edicion) {
            if (validarCampos()) {
                MensajeController.mensajeAdvertencia("Hay campos vacios");
            } else {
                PagoAColaborador pago = new PagoAColaborador();
                if (!tfComentario.getText().isEmpty()) {
                    pago.setComentario(tfComentario.getText().trim());
                }
                pago.setMonto(Integer.parseInt(tfPago.getText().trim()));
                pago.setGrupo(cbGrupoColaborador.getValue().toString());
                pago.setNombreAlumno(cbAlumno.getValue().toString());
                pago.setNombreColaborador(cbColaborador.getValue().toString());

                IPagoAColaborador metodos = new PagoAColaborador();

                if (metodos.guardarPagoAColaborador(pago)) {
                    MensajeController.mensajeInformacion("Se ha guardado el pago");
                    vaciarCampos();
                } else {
                    MensajeController.mensajeAdvertencia("No se ha podido guardar el pago");
                }
            }
        }
    }

    private boolean validarCampos() {
        return cbAlumno.getValue() == null || tfPago.getText().trim().isEmpty();
    }

    private void vaciarCampos() {
        cbAlumno.getItems().clear();
        cbGrupoColaborador.getItems().clear();
        cbColaborador.setValue(null);
        tfComentario.setText("");
        tfPago.setText("");
    }

    @FXML
    private void accionCancelarEdicion(ActionEvent event) {
        
    }
    
    public void cargarPago(PagoAColaborador pago){
        
    }

    public void quitarPago(Parent pago) {
        vboxContenedor.getChildren().removeAll(pago);
    }
}
