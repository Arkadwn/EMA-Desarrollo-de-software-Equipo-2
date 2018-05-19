/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Ingreso;
import emaaredespacio.modelo.Renta;
import emaaredespacio.utilerias.ReciboPago;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class FXMLRegistrarPagosRentaController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private ToggleGroup groupRBtn;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private TableView<Cliente> tbClientes;
    @FXML
    private JFXListView<Renta> listRentas;
    @FXML
    private JFXButton btnCerrarLista;
    @FXML
    private JFXButton btnSeleccionRenta;
    @FXML
    private TableColumn<Cliente, Integer> columnIDClientes;
    @FXML
    private TableColumn<Cliente, String> columnNombreClientes;
    @FXML
    private TableView<Colaborador> tbColaboradores;
    @FXML
    private TableColumn<Colaborador, Integer> columnIDColaborador;
    @FXML
    private TableColumn<Colaborador, String> columnNombreColaborador;
    @FXML
    private TableColumn<Colaborador, String> columnApellidosColaborador;
    @FXML
    private ToggleGroup groupTipoPago;
    @FXML
    private JFXRadioButton rdBtnRenta;
    @FXML
    private JFXRadioButton rdBtnColaborador;

    //Variables globales
    private boolean tipoPago;
    private Object seleccion;
    private Renta seleccionRenta;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private JFXRadioButton rdBtnNoGenerar;
    @FXML
    private JFXRadioButton rdBtnGenerar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tipoPago = true;
        columnIDColaborador.setCellValueFactory(new PropertyValueFactory<>("idColaborador"));
        columnNombreColaborador.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidosColaborador.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnIDClientes.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnNombreClientes.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        seleccion = null;
        seleccionRenta = null;
    }

    @FXML
    private void buscar(ActionEvent event) {
        if (tipoPago) {
            List<Colaborador> lista = new Colaborador().buscarColaborador(txtBusqueda.getText());
            tbColaboradores.getItems().clear();
            tbColaboradores.setItems(FXCollections.observableArrayList(lista));
        } else {
            List<Cliente> lista = new Cliente().buscarClienteRelacionado(txtBusqueda.getText());
            tbClientes.getItems().clear();
            tbClientes.setItems(FXCollections.observableArrayList(lista));
        }

        txtBusqueda.clear();
    }

    @FXML
    private void guadar(ActionEvent event) {
        if (rdBtnGenerar.isSelected()) {
            //Generar recibo
            if (seleccion == null) {
                MensajeController.mensajeInformacion("Debe seleccionar un colaborador o una renta de un cliente antes de registrar un pago");
            } else {

                if (tipoPago) {
                    Calendar fechaCalendar = Calendar.getInstance();
                    String fechaActual = fechaCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (fechaCalendar.get(Calendar.MONTH) + 1) + "/" + fechaCalendar.get(Calendar.YEAR);
                    ReciboPago.generarReciboPagoColaborador(((Colaborador) seleccion), fechaActual);
                    new Ingreso().guardarRegistro(new Ingreso(((Colaborador) seleccion).getIdColaborador(), null, Double.valueOf(txtMonto.getText()), false, fechaActual));
                    cambiarFlujo();
                    MensajeController.mensajeInformacion("Se ha guardado el ingreso correctamente");
                    vaciarCampos();

                } else if (seleccionRenta != null) {
                    Calendar fechaCalendar = Calendar.getInstance();
                    String fechaActual = fechaCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (fechaCalendar.get(Calendar.MONTH) + 1) + "/" + fechaCalendar.get(Calendar.YEAR);
                    ReciboPago.generarReciboPagoRenta(seleccionRenta, ((Cliente) seleccion), fechaActual);
                    
                    new Ingreso().guardarRegistro(new Ingreso(null, seleccionRenta.getId(), Double.valueOf(txtMonto.getText()), false, fechaActual));
                    seleccionRenta.setPagoRealizado(true);
                    new Renta().guardarCambios(seleccionRenta);
                    cambiarFlujo();
                    MensajeController.mensajeInformacion("Se ha guardado el ingreso correctamente");
                    vaciarCampos();

                } else {
                    MensajeController.mensajeInformacion("Debe seleccionar una renta de un cliente antes de registrar un pago");
                }
            }

        } else {
            //Solo guardar ingreso
            if (seleccion == null) {
                MensajeController.mensajeInformacion("Debe seleccionar un colaborador o una renta de un cliente antes de registrar un pago");
            } else {

                if (tipoPago) {
                    Calendar fechaCalendar = Calendar.getInstance();
                    String fechaActual = fechaCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (fechaCalendar.get(Calendar.MONTH) + 1) + "/" + fechaCalendar.get(Calendar.YEAR);
                    new Ingreso().guardarRegistro(new Ingreso(((Colaborador) seleccion).getIdColaborador(), null, Double.valueOf(txtMonto.getText()), false, fechaActual));
                    cambiarFlujo();
                    MensajeController.mensajeInformacion("Se ha guardado el ingreso correctamente");
                    vaciarCampos();
                } else if (seleccionRenta != null) {
                    Calendar fechaCalendar = Calendar.getInstance();
                    String fechaActual = fechaCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (fechaCalendar.get(Calendar.MONTH) + 1) + "/" + fechaCalendar.get(Calendar.YEAR);
                    new Ingreso().guardarRegistro(new Ingreso(null, seleccionRenta.getId(), Double.valueOf(txtMonto.getText()), false, fechaActual));
                    seleccionRenta.setPagoRealizado(true);
                    new Renta().guardarCambios(seleccionRenta);
                    MensajeController.mensajeInformacion("Se ha guardado el ingreso correctamente");
                    vaciarCampos();
                } else {
                    MensajeController.mensajeInformacion("Debe seleccionar una renta de un cliente antes de registrar un pago");
                }
            }
        }
    }

    @FXML
    private void cerrarLista(ActionEvent event) {
        btnCerrarLista.setVisible(false);
        btnSeleccionRenta.setVisible(false);
        listRentas.getItems().clear();
        listRentas.setVisible(false);
    }

    @FXML
    private void guardarSeleccionLista(ActionEvent event) {
        if (listRentas.getSelectionModel().getSelectedIndex() >= 0) {
            seleccionRenta = listRentas.getSelectionModel().getSelectedItem();
            txtMonto.setText(String.valueOf(seleccionRenta.getMonto()));
            rdBtnGenerar.setDisable(false);
            rdBtnNoGenerar.setDisable(false);
            cerrarLista(null);
        } else {
            MensajeController.mensajeInformacion("Primero seleccione una renta para poder realizar el pago");
        }
    }

    @FXML
    private void cambiarTipoPago(ActionEvent event) {
        if ("rdBtnRenta".equals(((JFXRadioButton) event.getSource()).getId())) {
            tipoPago = false;
        } else {
            tipoPago = true;
        }
        cambiarFlujo();
    }

    private void vaciarCampos() {
        tbColaboradores.getSelectionModel().clearSelection();
        tbClientes.getSelectionModel().clearSelection();
        rdBtnGenerar.setDisable(true);
        rdBtnNoGenerar.setDisable(true);
        txtMonto.clear();
        rdBtnNoGenerar.setSelected(true);
        seleccion = null;
        seleccionRenta = null;
    }

    private void cambiarFlujo() {
        if (!tipoPago) {
            tbColaboradores.setVisible(false);
            tbClientes.setVisible(true);
            tbColaboradores.getSelectionModel().clearSelection();
        } else {
            tbClientes.setVisible(false);
            tbClientes.getSelectionModel().clearSelection();
            tbColaboradores.setVisible(true);
        }
        rdBtnGenerar.setDisable(true);
        rdBtnNoGenerar.setDisable(true);
        txtMonto.clear();
    }

    @FXML
    private void quitaSeleccion(MouseEvent event) {
        ((TableView) event.getSource()).getSelectionModel().clearSelection();
    }

    @FXML
    private void posicion(MouseEvent event) {
        if (((TableView) event.getSource()).getSelectionModel().getSelectedIndex() >= 0) {
            if (tipoPago) {
                //Colaborador
                seleccion = tbColaboradores.getSelectionModel().getSelectedItem();
                String monto = ((Colaborador) seleccion).getMontoAPagar();
                txtMonto.setText(monto);
                rdBtnGenerar.setDisable(false);
                rdBtnNoGenerar.setDisable(false);
            } else {
                //Renta
                listRentas.setVisible(true);
                btnCerrarLista.setVisible(true);
                btnSeleccionRenta.setVisible(true);
                seleccion = tbClientes.getSelectionModel().getSelectedItem();
                List<Renta> lista = new Renta().cargarRentas((Cliente) seleccion);
                List<Renta> listaNoPago = new ArrayList<>();
                for (Renta renta : lista) {
                    if (!renta.isPagoRealizado()) {
                        listaNoPago.add(renta);
                    }
                }
                listRentas.getItems().clear();
                listRentas.setItems(FXCollections.observableArrayList(listaNoPago));
            }
        }
    }

}
