/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.GrupoXML;
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
import javafx.scene.Node;
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
    @FXML
    private JFXButton limpiarSeleccion;
    @FXML
    private JFXButton btnActualizarFechas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modificar = false;
        menuDesplegado = false;
        llenarTablaHorarioGlobal();
        cargarGruposHorarioFlotante();
        cargarRentasHorarioFlotante();
        reactivarDiaHorarioCheckBox(1, false);
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
            String fecha = renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + renta.getFecha().get(Calendar.MONTH) + "/" + renta.getFecha().get(Calendar.YEAR);
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
        cargarGruposHorarioFlotante();
        cargarRentasHorarioFlotante();
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
                    String string = renta.getId() + " | " + renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + renta.getFecha().get(Calendar.MONTH) + "/" + renta.getFecha().get(Calendar.YEAR)
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
            //Mapear horas
            HorarioGlobal hora = new HorarioGlobal(j);
            for (int i = 0; i < 2; i++) {
                StackPane panel = new StackPane();
                Label lb;
                JFXCheckBox check;
                panel.setPrefWidth(74.1327);
                panel.setMinHeight(25.0);
                if (i == 0) {
                    lb = new Label();
                    lb.setText(hora.getHora());
                    panel.getChildren().add(lb);
                    StackPane.setAlignment(lb, Pos.CENTER);
                } else {
                    check = new JFXCheckBox();
                    check.setId(i + "," + j);
                    check.setText("     ");
                    check.setOnAction((evento) -> {
                        deshabilitarCheckBoxCercano(evento);
                    });
                    check.setMinHeight(10.0);
                    check.setPrefHeight(12.5);
                    check.setMaxHeight(15.0);
                    check.setDisable(true);
                    panel.getChildren().add(check);
                    StackPane.setAlignment(check, Pos.CENTER);
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

    private void cargarGruposHorarioFlotante() {
        for (int i = 1; i < 2; i++) {
            int diaSemana;
            if (renta == null) {
                diaSemana = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                //diaSemana = renta.getFecha().get(Calendar.DAY_OF_WEEK);
                diaSemana += -1;
                if (diaSemana == 0) {
                    diaSemana = 7;
                }
            } else {
                diaSemana = renta.getFecha().get(Calendar.DAY_OF_WEEK);
                diaSemana += -1;
                if (diaSemana == 0) {
                    diaSemana = 7;
                }
            }
            List<Grupo> gruposXML = GrupoXML.obtenerGruposDiaSemana(diaSemana);
            if (!gruposXML.isEmpty()) {
                for (Grupo grupo : gruposXML) {
                    colocarGrupoHorarioFlotante(i, grupo, false);
                }
            }
        }
    }

    private void colocarGrupoHorarioFlotante(int j, Grupo grupo, boolean bandera) {
        float ini = Float.valueOf(grupo.getFecha_inicio());
        float fin = Float.valueOf(grupo.getFecha_fin());
        String textCheck = "G" + grupo.getIdGrupo();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;
        if (bandera) {
            for (int i = (int) ini; i < fin + 1; i++) {
                JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + j + "," + i);

                if (i == (int) ini || i == (int) fin) {
                    if (i == (int) ini) {
                        Node nodoCercano = gridPaneTabla.lookup("#" + j + "," + (i - 1));
                        JFXCheckBox check2 = (JFXCheckBox) nodoCercano;
                        if (check2 != null) {
                            check2.setDisable(false);
                        }
                    } else {
                        Node nodoCercano = gridPaneTabla.lookup("#" + j + "," + (i + 1));
                        JFXCheckBox check2 = (JFXCheckBox) nodoCercano;
                        if (check2 != null) {
                            check2.setDisable(false);
                        }
                    }
                    check.setText(textCheck);
                    check.setSelected(true);
                    check.setDisable(false);
                } else {
                    check.setText(textCheck);
                    check.setSelected(true);
                    check.setDisable(true);
                }

            }
        } else {
            for (int i = (int) ini; i < fin + 1; i++) {
                JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + j + "," + i);
                check.setText(textCheck);
                check.setSelected(true);
                check.setDisable(true);
            }
        }
    }

    private void cargarRentasHorarioFlotante() {
        List<Renta> lista = new Renta().cargarRentas();
        String id = "R";
        if (renta != null) {
            id += renta.getId();
        } else {
            id += "enta";
        }
        for (int j = 1; j < 2; j++) {
            for (int i = 0; i < lista.size(); i++) {
                Calendar calendarioAux = lista.get(i).getFecha();
                int diaAnio = calendarioAux.get(Calendar.DAY_OF_YEAR);
                if (renta != null) {
                    if (diaAnio == renta.getFecha().get(Calendar.DAY_OF_YEAR)) {
                        if (("R" + lista.get(i).getId()).equals(id)) {
                            colocarRentaHorarioFlotante(j, lista.get(i), true);
                        } else {
                            colocarRentaHorarioFlotante(j, lista.get(i), false);
                        }
                    }
                } else {
                    if (diaAnio == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                        colocarRentaHorarioFlotante(1, lista.get(i), false);
                        reactivarDiaHorarioCheckBox(1, false);
                    }
                }
            }
        }
    }

    private void colocarRentaHorarioFlotante(int j, Renta get, boolean bandera) {
        float ini = get.getHoraInicio();
        float fin = get.getHoraFin();
        String textCheck = "R" + get.getId();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;

        if (bandera) {
            for (int i = (int) ini; i < fin + 1; i++) {
                JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + j + "," + i);

                if (i == (int) ini || i == (int) fin) {
                    if (i == (int) ini) {
                        Node nodoCercano = gridPaneTabla.lookup("#" + j + "," + (i - 1));
                        JFXCheckBox check2 = (JFXCheckBox) nodoCercano;
                        if (check2 != null) {
                            check2.setDisable(false);
                        }
                    } else {
                        Node nodoCercano = gridPaneTabla.lookup("#" + j + "," + (i + 1));
                        JFXCheckBox check2 = (JFXCheckBox) nodoCercano;
                        if (check2 != null) {
                            check2.setDisable(false);
                        }
                    }
                    check.setText(textCheck);
                    check.setSelected(true);
                    check.setDisable(false);
                } else {
                    check.setText(textCheck);
                    check.setSelected(true);
                    check.setDisable(true);
                }

            }
        } else {
            for (int i = (int) ini; i < fin + 1; i++) {
                JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + j + "," + i);
                check.setText(textCheck);
                check.setSelected(true);
                check.setDisable(true);
            }
        }
    }

    private void deshabilitarCheckBoxCercano(ActionEvent evento) {
        JFXCheckBox check = (JFXCheckBox) evento.getSource();
        String id = "R";
        if (renta != null) {
            id += renta.getId();
        } else {
            id += "enta";
        }
        int x = Integer.parseInt(check.getId().split(",")[0]);
        int y = Integer.parseInt(check.getId().split(",")[1]);
        Node nodoCercano = gridPaneTabla.lookup("#" + x + "," + (y - 1));
        JFXCheckBox checkArriba = (JFXCheckBox) nodoCercano;
        Node nodoCercano2 = gridPaneTabla.lookup("#" + x + "," + (y + 1));
        JFXCheckBox checkAbajo = (JFXCheckBox) nodoCercano2;
        if (check.isSelected()) {
            if (y - 1 != 0 && y + 1 < 49) {
                if (checkArriba.isSelected()) {
                    if (checkArriba.getText().equals(id)) {
                        checkAbajo.setDisable(false);
                        checkArriba.setDisable(true);
                        check.setText(id);
                    } else {
                        checkAbajo.setDisable(false);
                        check.setText(id);
                    }
                } else {
                    if (checkAbajo.getText().equals(id)) {
                        checkArriba.setDisable(false);
                        checkAbajo.setDisable(true);
                        check.setText(id);
                    } else {
                        check.setText(id);
                    }
                }
            } else if (y - 1 == 0) {
                checkAbajo.setDisable(true);
                check.setText(id);
            } else if (y + 1 == 49) {
                checkArriba.setDisable(true);
                check.setText(id);
            }
        } else {
            if (y - 1 != 0 && y + 1 < 49) {
                if (checkArriba.isSelected()) {
                    if (checkArriba.getText().equals(id)) {
                        checkAbajo.setDisable(true);
                        checkArriba.setDisable(false);
                        check.setText("     ");
                    } else {
                        checkAbajo.setDisable(true);
                        check.setText("     ");
                    }
                } else {
                    if (checkAbajo.getText().equals(id)) {
                        checkArriba.setDisable(true);
                        checkAbajo.setDisable(false);
                        check.setText("     ");
                    } else {
                        check.setText("     ");
                    }
                }
            } else if (y - 1 == 0) {
                checkAbajo.setDisable(false);
                check.setText("     ");
            } else if (y + 1 == 49) {
                checkArriba.setDisable(false);
                check.setText("     ");
            }
        }
        if (comprobarDia(x) == 1) {
            reactivarDiaHorarioCheckBox(x, true);
            desactivarCheckBox(x);
        }
        if (comprobarDia(x) == 0) {
            reactivarDiaHorarioCheckBox(x, false);
        }
    }

    private int comprobarDia(int dia) {
        //boolean bandera = false;
        int cont = 0;
        int result = 0;
        String id = "R";
        if (renta != null) {
            id += renta.getId();
        } else {
            id += "enta";
        }
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + i);
            if (check.getText().equals(id)) {
                if (check.isSelected()) {
                    cont++;
                    result++;
                    //bandera = false;
                    if (cont > 1) {
                        result = 2;
                        //bandera = true;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private void desactivarCheckBox(int dia) {
        int y = 0;
        String id = "R";
        if (renta != null) {
            id += renta.getId();
        } else {
            id += "enta";
        }
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + i);
            if (check.getText().equals("     ")) {
                check.setDisable(true);
            }
            if (check.getText().equals(id)) {
                y = i;
            }
        }
        int abajo = y + 1;
        int arriba = y - 1;
        JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + y);
        check.setDisable(false);
        if (arriba > 0) {
            JFXCheckBox checkArriba = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + arriba);
            if ("     ".equals(checkArriba.getText())) {
                checkArriba.setDisable(false);
            }
        }
        if (abajo < 49) {
            JFXCheckBox checkAbajo = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + abajo);
            if ("     ".equals(checkAbajo.getText())) {
                checkAbajo.setDisable(false);
            }
        }
    }

    private void reactivarDiaHorarioCheckBox(int dia, boolean bandera) {
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + i);
            if (check.getText().equals("     ")) {
                check.setDisable(bandera);
            }
        }
    }

    @FXML
    private void cambiarColorSale(MouseEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        btn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
    }

    @FXML
    private void cambiarColorPasa(MouseEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }

    private void vaciarDiaHorarioCheckBox(int dia) {
        String id = "R";
        if (renta != null) {
            id += renta.getId();
        } else {
            id += "enta";
        }
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + dia + "," + i);
            if (check.getText().equals(id)) {
                check.setSelected(false);
                check.setDisable(true);
                check.setText("     ");
            }
        }

    }

    @FXML
    private void limpiarSeleccion(ActionEvent event) {
        vaciarDiaHorarioCheckBox(1);
        reactivarDiaHorarioCheckBox(1, false);
    }

    @FXML
    private void actualizarFechas(ActionEvent event) {
    }
}
