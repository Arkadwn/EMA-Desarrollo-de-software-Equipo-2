/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IGrupo;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private TableView<Grupo> tbListaGrupos;
    @FXML
    private TableColumn<Grupo, String> columnaTipoBaile;
    @FXML
    private TableColumn<Grupo, String> columnaCupo;
    @FXML
    private TableColumn<Grupo, String> columnaEstado;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ComboBox<?> comboBoxGrupos;
    @FXML
    private Spinner<Integer> spinnerCupo;
    private List<Grupo> lista;
    private Grupo seleccion;
    Colaborador colaborador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaTipoBaile.setCellValueFactory(new PropertyValueFactory<>("tipoDeBaile"));
        columnaCupo.setCellValueFactory(new PropertyValueFactory<>("cupo"));
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        
        lista = new ArrayList();
        seleccion = null;
    }

    @FXML
    private void accionGuardarCambios(ActionEvent event) {
        if (seleccion != null) {
            if (tfTipoBaile.getText().trim().isEmpty()) {
                MensajeController.mensajeAdvertencia("Hay campos vacíos");
            } else {
                if (tfTipoBaile.getText().length() > 0 && tfTipoBaile.getText().length() <= 30) {
                    Grupo grupoModificado = new Grupo();
                    grupoModificado.setIdColaborador(colaborador.getIdColaborador());
                    grupoModificado.setCupo(spinnerCupo.getValue());
                    grupoModificado.setTipoDeBaile(tfTipoBaile.getText());
                    grupoModificado.setIdGrupo(seleccion.getIdGrupo());
                    if (checkEstado.isSelected()) {
                        grupoModificado.setEstado("A");
                    } else {
                        grupoModificado.setEstado("B");
                    }
                    if(new Grupo().guardarCambios(grupoModificado)){
                        MensajeController.mensajeInformacion("Grupo modificado exitosamente");
                    }else{
                        MensajeController.mensajeAdvertencia("No se pudieron guardar los cambios");
                    }
                }else{
                    MensajeController.mensajeInformacion("Nombre del tipo de nombre muy grande");
                }

            }
        }
    }

    @FXML
    private void quitaSeleccion(MouseEvent event) {
        tbListaGrupos.getSelectionModel().clearSelection();
    }

    private void llenarTabla() {
        tbListaGrupos.getItems().clear();
        tbListaGrupos.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void posicion(MouseEvent event) {
        if (tbListaGrupos.getSelectionModel().getSelectedIndex() >= 0) {
            seleccion = tbListaGrupos.getSelectionModel().getSelectedItem();
            tfNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidos());
            tfTipoBaile.setText(seleccion.getTipoDeBaile());
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, seleccion.getCupo());
            spinnerCupo.setValueFactory(valueFactory);
            checkEstado.setSelected(seleccion.getEstado().equals("A"));
            
        }
    }

    @FXML
    private void accionBuscar(ActionEvent event) {
        if (tfPalabraClave.getText().isEmpty()) {
            MensajeController.mensajeInformacion("No ha ingresado ningún caracter");;
        } else {
            List<Colaborador> listaColaborador = new ArrayList();;
            colaborador = new Colaborador();
            listaColaborador = colaborador.buscarColaborador(tfPalabraClave.getText());
            if (listaColaborador.isEmpty()) {
                MensajeController.mensajeAdvertencia("No se encontró al colaborador solicitado");
            } else {
                colaborador = listaColaborador.get(0);
                IGrupo metodosGrupo = new Grupo();
                List<Grupo> listaTemporal = new ArrayList();
                listaTemporal.clear();
                listaTemporal = metodosGrupo.buscarGrupos();
                lista.clear();
                for (int i = 0; i < listaTemporal.size(); i++) {
                    if (listaTemporal.get(i).getIdColaborador() == colaborador.getIdColaborador()) {
                        lista.add(listaTemporal.get(i));
                    }
                }
                llenarTabla();
            }

        }
    }

}
