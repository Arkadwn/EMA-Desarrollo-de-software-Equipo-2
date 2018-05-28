package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.Inscripcion;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLModificarGrupoController implements Initializable {

    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfTipoBaile;
    @FXML
    private CheckBox checkEstado;
    @FXML
    private TableView<Colaborador> tbListaGrupos;
    @FXML
    private TableColumn<Colaborador, String> columnaColaborador;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private Spinner<Integer> spinnerCupo;
    private Colaborador colaborador;
    private Grupo grupoSeleccionado;
    @FXML
    private JFXTextField tfMensualidad;
    @FXML
    private JFXTextField tfInscripcion;
    @FXML
    private JFXComboBox<Grupo> cbGrupo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaColaborador.setCellValueFactory(new PropertyValueFactory("nombreCompleto"));

        cbGrupo.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Grupo>() {
            @Override
            public void changed(ObservableValue<? extends Grupo> observable, Grupo oldValue, Grupo newValue) {
                if (newValue != null) {
                    grupoSeleccionado = newValue;
                    tfTipoBaile.setText(grupoSeleccionado.getTipoDeBaile());
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, grupoSeleccionado.getCupo());
                    spinnerCupo.setValueFactory(valueFactory);
                    checkEstado.setSelected(grupoSeleccionado.getEstado().equals("A"));
                    tfInscripcion.setText(""+grupoSeleccionado.getInscripcion());
                    tfMensualidad.setText(""+grupoSeleccionado.getMensualidad());
                }
            }
        });
        colaborador = null;
    }

    @FXML
    private void accionGuardarCambios(ActionEvent evento) {
        if (colaborador != null) {
            if (validarCampos()) {
                MensajeController.mensajeAdvertencia("Hay campos vacíos");
            } else {
                if (tfTipoBaile.getText().length() > 0 && tfTipoBaile.getText().length() <= 30) {
                    Grupo grupoModificado = new Grupo();
                    grupoModificado.setIdColaborador(colaborador.getIdColaborador());
                    grupoModificado.setCupo(spinnerCupo.getValue());
                    grupoModificado.setTipoDeBaile(tfTipoBaile.getText());
                    grupoModificado.setIdGrupo(grupoSeleccionado.getIdGrupo());
                    grupoModificado.setMensualidad(Integer.parseInt(tfMensualidad.getText()));
                    grupoModificado.setInscripcion(Integer.parseInt(tfInscripcion.getText()));
                    if (checkEstado.isSelected()) {
                        grupoModificado.setEstado("A");
                    } else {
                        grupoModificado.setEstado("B");
                    }
                    if (new Grupo().guardarCambios(grupoModificado)) {
                        MensajeController.mensajeInformacion("Grupo modificado exitosamente");
                        if(grupoModificado.getEstado().equals("B")){
                            new Grupo().darDeBajaAlumnosDeGrupo(grupoSeleccionado);
                        }
                    } else {
                        MensajeController.mensajeAdvertencia("No se pudieron guardar los cambios");
                    }
                } else {
                    MensajeController.mensajeInformacion("Nombre del tipo de baile muy grande");
                }

            }
        }
    }

    @FXML
    private void quitaSeleccion(MouseEvent evento) {
        tbListaGrupos.getSelectionModel().clearSelection();
    }

    private void llenarTabla(List<Colaborador> lista) {
        tbListaGrupos.getItems().clear();
        tbListaGrupos.setItems(FXCollections.observableArrayList(lista));
    }
    
    private void cargarGrupos(List<Grupo> lista){
        cbGrupo.getItems().clear();
        cbGrupo.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void seleccionarColaborador(MouseEvent evento) {
        if (tbListaGrupos.getSelectionModel().getSelectedIndex() >= 0) {
            colaborador = tbListaGrupos.getSelectionModel().getSelectedItem();
            tfNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidos());
            cargarGrupos(new Inscripcion().buscarGruposDeColaborador(colaborador.getIdColaborador()));
        }
    }

    @FXML
    private void accionBuscar(ActionEvent evento) {
        if (tfPalabraClave.getText().trim().isEmpty()) {
            MensajeController.mensajeInformacion("No ha ingresado ningún caracter");
        } else {
            List<Colaborador> listaColaborador = new ArrayList<>();
            colaborador = new Colaborador();
            listaColaborador = colaborador.buscarColaborador(tfPalabraClave.getText().trim());
            if (listaColaborador.isEmpty()) {
                MensajeController.mensajeAdvertencia("No se encontró al colaborador solicitado");
            } else {
                llenarTabla(listaColaborador);
            }

        }
    }

    @FXML
    private void restringirLetras(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        JFXTextField campo = (JFXTextField) evento.getSource();

        if (Character.isDigit(caracter) && campo.getText().length() < 6) {

        } else {
            evento.consume();
        }
    }

    private boolean validarCampos(){
        return tfMensualidad.getText().trim().isEmpty() || tfInscripcion.getText().trim().isEmpty() 
                || tfTipoBaile.getText().trim().isEmpty();
    }
}
