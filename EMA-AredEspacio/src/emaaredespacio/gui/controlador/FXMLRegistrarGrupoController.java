/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.GrupoXML;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IGrupo;
import emaaredespacio.modelo.Renta;
import emaaredespacio.persistencia.entidad.Colaboradores;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarGrupoController implements Initializable {

    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfTipoBaile;
    @FXML
    private Spinner spinnerCupo;
    @FXML
    private TableView<Colaborador> tbListaColaboradores;
    @FXML
    private TableColumn<Colaboradores, String> columnaNombre;
    @FXML
    private TableColumn<Colaboradores, String> columnaApellidos;
    private List<Colaborador> lista;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;

    private Colaborador seleccion;

    private List<Grupo> grupos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        lista = new ArrayList();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 25);
        spinnerCupo.setValueFactory(valueFactory);
        grupos = new ArrayList();
        seleccion = new Colaborador();
    }

    @FXML
    private void quitaSeleccion(MouseEvent event) {
        tbListaColaboradores.getSelectionModel().clearSelection();
    }

    private void llenarTabla() {
        tbListaColaboradores.getItems().clear();
        tbListaColaboradores.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void posicion(MouseEvent event) {
        if (tbListaColaboradores.getSelectionModel().getSelectedIndex() >= 0) {
            seleccion = tbListaColaboradores.getSelectionModel().getSelectedItem();
            tfNombre.setText(seleccion.getNombre() + " " + seleccion.getApellidos());
        }
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacíos");
        } else {
            if (tfTipoBaile.getLength() >= 2 && tfTipoBaile.getLength() <= 30) {
                Grupo grupo = new Grupo();
                grupo.setTipoDeBaile(tfTipoBaile.getText());
                grupo.setCupo(Integer.valueOf(spinnerCupo.getValue().toString()));
                grupo.setIdColaborador(seleccion.getIdColaborador());
                grupo.setHorarioAsignado(0);
                IGrupo metodos = new Grupo();
                if (metodos.guardarGrupo(grupo)) {
                    MensajeController.mensajeInformacion("El grupo se ha guardado correctamente");
                    //Grupo grup = new Grupo();
                    //List<Grupo> grupos = grup.buscarGrupos();
                    //grup = grupos.get(grupos.size() - 1);
                    //grupo.setIdGrupo(grup.getIdGrupo());
                    //GrupoXML.guardarGrupo(grupo);
                } else {
                    MensajeController.mensajeAdvertencia("No se pudo registrar el grupo");
                }

            } else {
                MensajeController.mensajeAdvertencia("Campos inválidos");
            }

        }
    }

    private boolean validarCamposVacios() {
        return tfTipoBaile.getText().trim().isEmpty() || tfNombre.getText().isEmpty();
    }

    @FXML
    private void accionBuscar() {
        if (tfPalabraClave.getText().isEmpty()) {
            MensajeController.mensajeInformacion("No ha ingersado ningun caracter");
        } else {
            IColaborador metodosColaborador = new Colaborador();
            lista.clear();
            lista = metodosColaborador.buscarColaborador(tfPalabraClave.getText());
            if (lista.isEmpty()) {
                MensajeController.mensajeInformacion("Colaborador no encontrado");
            }
            llenarTabla();
        }
    }

}
