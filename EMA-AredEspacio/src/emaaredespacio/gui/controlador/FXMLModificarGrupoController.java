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
import javafx.scene.control.CheckBox;
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
public class FXMLModificarGrupoController implements Initializable {

    @FXML
    private JFXTextField tfNombre;

    @FXML
    private JFXTextField tfTipoBaile;
    @FXML
    private JFXTextField tfCupo;
    @FXML
    private CheckBox checkEstado;
    @FXML
    private TableView<Grupo> tbListaGrupos;
    @FXML
    private TableColumn<Grupo, String> columnaNombre;

    @FXML
    private TableColumn<Grupo, String> columnaTipoBaile;
    @FXML
    private TableColumn<Grupo, String> columnaCupo;
    @FXML
    private TableColumn<Grupo, String> columnaEstado;

    private List<Grupo> lista;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;
    private Grupo seleccion;
    private String idColaborador;
    private List<Colaborador> colaborador;
    private Colaborador colaboradorActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaTipoBaile.setCellFactory(new PropertyValueFactory<>("tipoDeBaile"));
        columnaCupo.setCellFactory(new PropertyValueFactory<>("cupo"));
        columnaEstado.setCellFactory(new PropertyValueFactory<>("estado"));
        lista = new ArrayList();
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
    private void accionBuscar() {

        if (tfPalabraClave.getText().isEmpty()) {
            System.out.println("No ha ingersado ningun caracter");
        } else {
            if (buscarColaborador(tfPalabraClave.getText())) {
                IGrupo metodosGrupo = new Grupo();
                lista.clear();
                lista = metodosGrupo.buscarGrupo(colaboradorActual.getIdColaborador());
                llenarTabla();
            }

        }
    }

    private boolean buscarColaborador(String nombreColaborador) {
        IColaborador metodosColaborador = new Colaborador();
        colaborador = metodosColaborador.buscarColaborador(nombreColaborador);
        for (int i = 0; i < colaborador.size(); i++) {
            if (colaborador.get(i).getNombre().equals(tfPalabraClave.getText()) || colaborador.get(i).getApellidos().equals(tfPalabraClave.getText())) {
                colaboradorActual = colaborador.get(i);
                return true;
            }
        }
        return false;
    }

    @FXML
    private void posicion(MouseEvent event) {
        if (tbListaGrupos.getSelectionModel().getSelectedIndex() >= 0) {
            seleccion = tbListaGrupos.getSelectionModel().getSelectedItem();
            tfNombre.setText(colaboradorActual.getNombre());
            tfTipoBaile.setText(seleccion.getTipoDeBaile());
            tfCupo.setText(String.valueOf(seleccion.getCupo()));
            checkEstado.setSelected(seleccion.getEstado().equals("A"));
        }

    }
    
    @FXML
    private void accionGuardarCambios(ActionEvent event) {
        if(validarCamposVacios()){
            System.out.println("Campos vacios");
        }else{
            IGrupo metodosGrupos = new Grupo();
            Grupo grupo = new Grupo();
            grupo.setIdColaborador(idColaborador);
            grupo.setTipoDeBaile(tfTipoBaile.getText());
            grupo.setCupo(Integer.valueOf(tfTipoBaile.getText()));
            grupo.setIdGrupo(seleccion.getIdGrupo());
            if(checkEstado.isSelected()){
                grupo.setEstado("A");
            }else{
                grupo.setEstado("B");
            }
            
            if (tfTipoBaile.getLength() >= 2 && tfTipoBaile.getLength() <= 30 && cupoEsNumero() && tfCupo.getLength() >=0) {
                if (metodosGrupos.guardarCambios(grupo)) {
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
        return tfNombre.getText().isEmpty() || tfCupo.getText().isEmpty()
                || tfTipoBaile.getText().isEmpty();
    }
}
