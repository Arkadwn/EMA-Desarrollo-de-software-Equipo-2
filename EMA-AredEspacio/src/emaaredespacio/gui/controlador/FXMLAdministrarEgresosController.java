/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Egreso;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class FXMLAdministrarEgresosController implements Initializable {

    @FXML
    private JFXTextArea txtAreaDescripcion;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private DatePicker itemFecha;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXTextArea txtAreaDescripcion2;
    @FXML
    private JFXTextField txtMonto2;
    @FXML
    private DatePicker itemFecha2;
    @FXML
    private JFXButton btnGuardar2;
    @FXML
    private TableView<Egreso> tbEventos;
    @FXML
    private Tab tabIngresarEvento;
    @FXML
    private Tab tabModificarVisualizarEvent;
    @FXML
    private TableColumn<Egreso, String> columnFecha;
    @FXML
    private TableColumn<Egreso, Double> columnMonto;
    @FXML
    private TableColumn<Egreso, String> columnDescripcion;
    @FXML
    private TableColumn<Egreso, Integer> columnIdEgresos;

    //Variables
    private boolean banderaFlujo;
    private boolean creoModificoEgreso;
    private Egreso seleccion;
    private List<Egreso> lista;
    private ArrayList<String> namesKeyCodeDigit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        banderaFlujo = true;
        columnDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        columnFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        columnIdEgresos.setCellValueFactory(new PropertyValueFactory<>("idEgreso"));
        columnMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));

        seleccion = null;
        lista = new ArrayList();
        creoModificoEgreso = true;
        llenarTabla();

        namesKeyCodeDigit = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            namesKeyCodeDigit.add(String.valueOf(i));
        }
    }

    @FXML
    private void quitaSeleccion(MouseEvent event) {
        tbEventos.getSelectionModel().clearSelection();
    }

    private void llenarTabla() {
        if (creoModificoEgreso) {
            lista = new Egreso().cargarEgresos();
            tbEventos.getItems().clear();
            tbEventos.setItems(FXCollections.observableArrayList(lista));
            creoModificoEgreso = false;
        }
    }

    @FXML
    private void posicion(MouseEvent event) {
        if (tbEventos.getSelectionModel().getSelectedIndex() >= 0) {
            seleccion = tbEventos.getSelectionModel().getSelectedItem();
            txtAreaDescripcion2.setText(seleccion.getDescripcion());
            txtMonto2.setText(String.valueOf(seleccion.getMonto()));
            itemFecha2.getEditor().setText(seleccion.getFecha());
            txtAreaDescripcion2.setDisable(false);
            txtMonto2.setDisable(false);
            itemFecha2.setDisable(false);
        }
    }

    private void vaciarDatos() {
        tbEventos.getSelectionModel().clearSelection();
        itemFecha2.getEditor().setText("");
        txtMonto2.setText("");
        txtAreaDescripcion2.setText("");
        txtAreaDescripcion2.setDisable(true);
        txtMonto2.setDisable(true);
        itemFecha2.setDisable(true);
        itemFecha.getEditor().setText("");
        txtMonto.setText("");
        txtAreaDescripcion.setText("");
    }

    @FXML
    private void guardarDatos(ActionEvent event) {
        if (banderaFlujo) {
            //Verificar monto solo numeros y punto
            if (!"".equals(txtMonto.getText())) {
                Double monto = Double.parseDouble(txtMonto.getText());
                if (!"".equals(txtAreaDescripcion.getText())) {
                    if (!"".equals(itemFecha.getEditor().getText())) {
                        new Egreso().guardarNuevoEgreso(new Egreso(txtAreaDescripcion.getText(), itemFecha.getEditor().getText(), monto));
                        creoModificoEgreso = true;
                        vaciarDatos();
                        MensajeController.mensajeInformacion("Se ha guardado el egreso correctamente");
                    } else {
                        MensajeController.mensajeInformacion("Coloque una fecha para el egreso");
                    }
                } else {
                    MensajeController.mensajeInformacion("Le hace falta una descripción al egreso");
                }
            } else {
                MensajeController.mensajeInformacion("Le hace falta un monto al egreso");
            }
        } else {
            //Verificar monto solo numeros y punto
            if (!"".equals(txtMonto2.getText())) {
                Double monto = Double.parseDouble(txtMonto2.getText());
                if (!"".equals(txtAreaDescripcion2.getText())) {
                    if (!"".equals(itemFecha2.getEditor().getText())) {
                        new Egreso().guardarCambios(new Egreso(seleccion.getIdEgreso(), txtAreaDescripcion2.getText(), itemFecha2.getEditor().getText(), monto));
                        creoModificoEgreso = true;
                        vaciarDatos();
                        MensajeController.mensajeInformacion("Se ha guardado el egreso correctamente");
                    } else {
                        MensajeController.mensajeInformacion("Coloque una fecha para el egreso");
                    }
                } else {
                    MensajeController.mensajeInformacion("Le hace falta una descripción al egreso");
                }
            } else {
                MensajeController.mensajeInformacion("Le hace falta un monto al egreso");
            }
        }
    }

    @FXML
    private void cambiarFlujo(Event event) {
        if ("tabIngresarEvento".equals(((Tab) event.getSource()).getId())) {
            banderaFlujo = true;
            if (tbEventos != null) {
                vaciarDatos();
            }
        } else {
            banderaFlujo = false;
            llenarTabla();
            vaciarDatos();
        }
    }

    @FXML
    private void verificarSoloNumeros(KeyEvent event) {
        char cadena = event.getCharacter().charAt(0);
        if (Character.isDigit(cadena) && ((JFXTextField) event.getSource()).getText().length() < 6) {

        } else {
            event.consume();
        }
    }

}
