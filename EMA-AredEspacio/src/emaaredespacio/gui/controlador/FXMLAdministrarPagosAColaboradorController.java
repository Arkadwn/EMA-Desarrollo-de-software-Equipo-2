package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IAlumno;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IGrupo;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.Inscripcion;
import emaaredespacio.modelo.Pago;
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
import emaaredespacio.modelo.IPago;
import java.util.Iterator;

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
    private Pago pago;
    @FXML
    private JFXButton btnCancelar;
    private List<Colaborador> colaboradores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IColaborador metodos = new Colaborador();
        colaboradores = metodos.buscarColaboradoresEstados("A");
        cargarColaboradores(colaboradores);
        
        IPago metodosPago = new Pago();
        
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

    private void cargarPagos(List<Pago> lista) throws IOException {
        vboxContenedor.getChildren().clear();

        for (Pago pago : lista) {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLFormatoPago.fxml"));
            Parent fxml = cargador.load();
            FXMLFormatoPagoController controlador = cargador.getController();
            controlador.cargarAlumno(pago, this);
            vboxContenedor.getChildren().addAll(fxml);
        }

    }

    @FXML
    private void accionGuardar() {
        if (pago != null) {
            if (validarCampos()) {
                MensajeController.mensajeAdvertencia("Hay campos vacios");
            } else {
                Pago pago = new Pago();
                if (!tfComentario.getText().trim().isEmpty()) {
                    pago.setComentario(tfComentario.getText().trim());
                }
                pago.setMonto(Integer.parseInt(tfPago.getText().trim()));
                pago.setGrupo(cbGrupoColaborador.getValue().toString());
                pago.setNombreAlumno(cbAlumno.getValue().toString());
                pago.setNombreColaborador(cbColaborador.getValue().toString());
                pago.setIdAlumno(cbAlumno.getValue().getMatricula());
                pago.setIdGrupo(cbGrupoColaborador.getValue().getIdGrupo());
                pago.setIdColaborador(cbColaborador.getValue().getIdColaborador());

                IPago metodos = new Pago();

                if (metodos.guardarPagoAColaborador(pago)) {
                    MensajeController.mensajeInformacion("Se ha guardado el pago");
                    vaciarCampos();
                } else {
                    MensajeController.mensajeAdvertencia("No se ha podido guardar el pago");
                }
            }
        }else{
            if(validarCampos()){
                if(tfComentario.getText().trim().isEmpty()){
                    pago.setComentario("");
                }else{
                    pago.setComentario(tfComentario.getText().trim());
                }
                pago.setNombreAlumno(cbAlumno.getValue().getNombreCompleto());
                pago.setIdAlumno(cbAlumno.getValue().getMatricula());
                pago.setNombreColaborador(cbColaborador.getValue().getNombre() + " " +cbColaborador.getValue().getApellidos());
                pago.setIdColaborador(cbColaborador.getValue().getIdColaborador());
                pago.setIdGrupo(cbGrupoColaborador.getValue().getIdGrupo());
                pago.setGrupo(cbGrupoColaborador.getValue().toString());
                pago.setMonto(Integer.parseInt(tfPago.getText().trim()));
                
                IPago metodos = new Pago();
                
                if(metodos.editarPagoAColaborador(pago)){
                    MensajeController.mensajeInformacion("Se han guadado los cambios");
                    vaciarCampos();
                }
            }
        }
    }

    private boolean validarCampos() {
        return cbColaborador.getValue() == null || cbGrupoColaborador.getValue() == null || cbAlumno.getValue() == null || tfPago.getText().trim().isEmpty();
    }

    private void vaciarCampos() {
        pago = null;
        cbAlumno.getItems().clear();
        cbGrupoColaborador.getItems().clear();
        cbColaborador.setValue(null);
        tfComentario.setText("");
        tfPago.setText("");
    }

    @FXML
    private void accionCancelarEdicion(ActionEvent event) {
        pago = null;
        btnCancelar.setVisible(false);
    }
    
    public void cargarPago(Pago pago){
        btnCancelar.setVisible(true);
        IGrupo metodosGrupos = new Grupo();
        IAlumno metodo = new Alumno();
        Alumno alumno = metodo.buscarAlumnoPorId(pago.getIdAlumno());
        Grupo grupo = metodosGrupos.buscarGrupoPorId(pago.getIdGrupo());
        Colaborador colaborador = null;
        for (Iterator<Colaborador> it = colaboradores.iterator(); it.hasNext();) {
            colaborador = it.next();
            if(colaborador.getIdColaborador().equals(pago.getIdColaborador()))
                break;
        }
        cbColaborador.getSelectionModel().select(colaborador);
        cbGrupoColaborador.getSelectionModel().select(grupo);
        cbGrupoColaborador.getSelectionModel().selectFirst();
        cbAlumno.getSelectionModel().select(alumno);
        cbAlumno.getSelectionModel().selectFirst();
        tfComentario.setText(pago.getComentario());
        tfPago.setText(""+pago.getMonto());
        this.pago = pago;
    }

    public void quitarPago(Parent pago) {
        vboxContenedor.getChildren().removeAll(pago);
    }
}
