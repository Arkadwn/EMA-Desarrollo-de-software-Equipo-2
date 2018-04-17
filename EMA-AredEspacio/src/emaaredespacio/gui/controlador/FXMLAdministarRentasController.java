/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.HorarioGlobal;
import emaaredespacio.modelo.Renta;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Adrián Bustamante Zarate
 */
public class FXMLAdministarRentasController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXListView<String> listaBusqueda;
    @FXML
    private ToggleButton btnToggleModificar;
    @FXML
    private ToggleGroup rentasGroup;
    @FXML
    private ToggleButton btnToggleCrear;
    @FXML
    private DatePicker itemFecha;
    @FXML
    private JFXTextField txtMonto;
    @FXML
    private JFXButton btnRegresar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelarRenta;
    private AnchorPane barraMenu;
    private boolean modificar;
    private boolean menuDesplegado;
    @FXML
    private ListView<String> listRentas;
    @FXML
    private JFXButton btnSeleccionarRentaVentana;
    @FXML
    private JFXButton btnSeleccionarRenta;
    private Renta renta;
    private Cliente cliente;
    @FXML
    private JFXButton btnCerrarRentaVentana;
    @FXML
    private GridPane gridPaneTabla;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modificar = false;
        menuDesplegado = false;
        llenarTablaHorarioGlobal();
    }

    @FXML
    private void buscarCliente(ActionEvent event) {
        listaBusqueda.setItems(FXCollections.observableArrayList());
        ObservableList<String> lista = listaBusqueda.getItems();
        if (!"".equals(txtBusqueda.getText())) {
            List<Cliente> clientes = new Cliente().buscarClienteRelacionado(txtBusqueda.getText());
            int c = 0;
            for (Cliente cliente : clientes) {
                String string = cliente.getId() + " | " + cliente.getNombre();
                c++;
                lista.add(string);
            }
            listaBusqueda.setItems(lista);
        } else {
            //Mensaje
        }
    }

    @FXML
    private void cambiarModificar(ActionEvent event) {
        if (!modificar) {
            modificar = true;
            btnToggleModificar.setStyle("-fx-background-color: #854c5d; -fx-text-fill: white;");
            btnToggleCrear.setStyle("-fx-background-color: lightpink; -fx-text-fill: black;");
            btnCancelarRenta.setVisible(modificar);
            btnSeleccionarRenta.setVisible(modificar);
            vaciarCampos();
        }
    }

    @FXML
    private void cambiarCrearRenta(ActionEvent event) {
        if (modificar) {
            modificar = false;
            btnToggleCrear.setStyle("-fx-background-color: #854c5d; -fx-text-fill: white;");
            btnToggleModificar.setStyle("-fx-background-color: lightpink; -fx-text-fill: black;");
            btnCancelarRenta.setVisible(modificar);
            btnSeleccionarRenta.setVisible(modificar);
            vaciarCampos();
        }
    }

    @FXML
    private void regresarMenuPrincipal(ActionEvent event) {

    }

    private void vaciarCampos() {
        txtBusqueda.setText("");
        txtMonto.setText("");
        itemFecha.getEditor().setText("");
        listRentas.setItems(FXCollections.observableArrayList());
        listaBusqueda.setItems(FXCollections.observableArrayList());
    }

    @FXML
    private void guardarDatos(ActionEvent event) {
        if (!modificar) {
            String seleccion = listaBusqueda.getSelectionModel().getSelectedItem();
            if (seleccion != null) {
                String[] parts = seleccion.split("|");
                Cliente cliente = new Cliente(parts[0]);
                Calendar fechaCalendar = Calendar.getInstance();
                fechaCalendar.set(Integer.parseInt(itemFecha.getEditor().getText().split("/")[2]), Integer.parseInt(itemFecha.getEditor().getText().split("/")[1]) - 1, Integer.parseInt(itemFecha.getEditor().getText().split("/")[0]));
                new Renta().guardarNuevaRenta(new Renta(0, Integer.parseInt(txtMonto.getText()), fechaCalendar, 0, 100, cliente, true));
                vaciarCampos();
            }
        } else {
            if (renta != null) {
                Calendar fechaCalendar = Calendar.getInstance();
                fechaCalendar.set(Integer.parseInt(itemFecha.getEditor().getText().split("/")[2]), Integer.parseInt(itemFecha.getEditor().getText().split("/")[1]) - 1, Integer.parseInt(itemFecha.getEditor().getText().split("/")[0]));
                new Renta().guardarCambios(new Renta(renta.getId(), Integer.parseInt(txtMonto.getText()), fechaCalendar, 100, 100, renta.getCliente(), true));
                vaciarCampos();
            }
        }
    }

    @FXML
    private void cancelarRenta(ActionEvent event) {
        String seleccion = listRentas.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            String[] parts = seleccion.split("|");
            new Renta().cancelarRenta(Integer.parseInt(parts[0]));
            vaciarCampos();
        }
    }

    private void mostrarMenu(ActionEvent event) {
        menuDesplegado = !menuDesplegado;
        barraMenu.setVisible(menuDesplegado);
    }

    private void ocultarMenu(MouseEvent event) {
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }

    @FXML
    private void guardarSeleccionRenta(ActionEvent event) {
        //guardarSeleccionRenta
        String seleccion = listRentas.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            String[] parts = seleccion.split(" | ");
            this.renta = new Renta().cargarRenta(parts[0]);
            txtMonto.setText(String.valueOf(renta.getMonto()));
            String fecha = renta.getFecha().get(Calendar.DAY_OF_MONTH) +"/"+ renta.getFecha().get(Calendar.MONTH) +"/"+ renta.getFecha().get(Calendar.YEAR);
            itemFecha.getEditor().setText(fecha);
            //Llenado de tablas de horario

            listRentas.setVisible(false);
            btnSeleccionarRentaVentana.setVisible(false);
            btnCerrarRentaVentana.setVisible(false);
            btnSeleccionarRenta.setVisible(true);
            btnToggleCrear.setDisable(false);
            listaBusqueda.setDisable(false);
            btnBuscar.setDisable(false);
            txtBusqueda.setDisable(false);
        }
    }

    @FXML
    private void seleccionarRentaListaEmergente(ActionEvent event) {
        if (modificar) {
            String seleccion = listaBusqueda.getSelectionModel().getSelectedItem();
            if (seleccion != null) {
                listRentas.setVisible(true);
                btnSeleccionarRentaVentana.setVisible(true);
                btnSeleccionarRenta.setVisible(false);
                btnToggleCrear.setDisable(true);
                listaBusqueda.setDisable(true);
                btnBuscar.setDisable(true);
                txtBusqueda.setDisable(true);
                btnCerrarRentaVentana.setVisible(true);
                listRentas.setItems(FXCollections.observableArrayList());
                ObservableList<String> lista = listRentas.getItems();

                String[] parts = seleccion.split("|");
                List<Renta> rentas = new Renta().cargarRentas(new Cliente(parts[0]));
                int c = 0;
                for (Renta renta : rentas) {
                    String string = renta.getId() + " | " + renta.getFecha().get(Calendar.DAY_OF_MONTH) +"/"+ renta.getFecha().get(Calendar.MONTH) +"/"+ renta.getFecha().get(Calendar.YEAR)
                            + " de " + renta.getHoraInicio() + " a las " + renta.getHoraFin() + " de $" + renta.getMonto();
                    c++;
                    lista.add(string);
                }
                listRentas.setItems(lista);
            }
        }
    }

    @FXML
    private void cerrarVentanaEmergente(ActionEvent event) {

        listRentas.setVisible(false);
        btnSeleccionarRentaVentana.setVisible(false);
        btnSeleccionarRenta.setVisible(true);
        btnToggleCrear.setDisable(false);
        listaBusqueda.setDisable(false);
        btnBuscar.setDisable(false);
        txtBusqueda.setDisable(false);
        btnCerrarRentaVentana.setVisible(false);
        listRentas.setItems(FXCollections.observableArrayList());

    }
    
    private void llenarTablaHorarioGlobal() {
        for (int j = 1; j < 49; j++) {
            HorarioGlobal hora = new HorarioGlobal(j);
            for (int i = 0; i < 2; i++) {
                StackPane panel = new StackPane();
                Label lb;
                if (i != 0) {
                    lb = new Label();
                    lb.setText("");
                    //lb.setPrefWidth(74.1327);
                    panel.getChildren().add(lb);
                    StackPane.setAlignment(lb, Pos.CENTER);
                } else {
                    lb = new Label();
                    lb.setText(hora.getHora());
                    panel.getChildren().add(lb);
                    StackPane.setAlignment(lb, Pos.CENTER);
                }
                if (j % 2 != 0) {
                    panel.setStyle("-fx-background-color: #FFF; -fx-border-color: #a2a2a2;");
                } else {
                    panel.setStyle("-fx-background-color: #f1f1f1; -fx-border-color: #a2a2a2;");
                }
                gridPaneTabla.add(panel, i, j);
            }
        }
    }

}
