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
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IGrupo;
import emaaredespacio.persistencia.entidad.Colaboradores;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarGrupoController implements Initializable {

    int idColaborador = 0;
    @FXML
    private JFXTextField tfTipoBaile;
    @FXML
    private Spinner spinnerCupo;
    @FXML
    private TableView<Colaborador> tbListaColaboradores;
    @FXML
    private TableColumn<Colaboradores, String> columnaNombre;
    private List<Colaborador> lista;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXTextField tfCupo;

    @FXML
    private ComboBox comboBoxCupo;
    private Colaborador seleccion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        lista = new ArrayList();
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
            idColaborador = seleccion.getIdColaborador();
            System.out.println(idColaborador);
        }
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            System.out.println("Hay campos vacios");
        } else {
            IGrupo metodosGrupo = new Grupo();
            Grupo grupo = new Grupo();
            grupo.setTipoDeBaile(tfTipoBaile.getText());
            grupo.setCupo(Integer.valueOf(tfCupo.getText()));
            grupo.setIdColaborador(String.valueOf(idColaborador));
            if (tfTipoBaile.getLength() >= 2 && tfTipoBaile.getLength() <= 30 && cupoEsNumero()) {
                if (metodosGrupo.guardarGrupo(grupo)) {
                    System.out.println("Grupo guardado");
                } else {
                    System.out.println("No se pudo");
                }
            } else {
                System.out.println("Campos invalidos");
            }
        }
    }

    private boolean cupoEsNumero() {
        try {
            double d = Double.parseDouble(tfCupo.getText());
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private boolean validarCamposVacios() {
        return tfTipoBaile.getText().isEmpty() || cupoEsNumero() || idColaborador == 0;
    }

    @FXML
    private void accionBuscar() {
        if (tfPalabraClave.getText().isEmpty()) {
            System.out.println("No ha ingersado ningun caracter");
        } else {
            IColaborador metodosColaborador = new Colaborador();
            lista.clear();
            lista = metodosColaborador.buscarColaborador(tfPalabraClave.getText());
            llenarTabla();
        }
    }

}
