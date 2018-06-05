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
import emaaredespacio.utilerias.GrupoXML;
import emaaredespacio.modelo.Renta;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private JFXButton btnCerrarRentaVentana;
    @FXML
    private GridPane gridPaneTabla;
    @FXML
    private JFXButton limpiarSeleccion;
    @FXML
    private JFXButton btnActualizarFechas;
    private Calendar calendario;
    private Renta rentaLlegada;
    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private GridPane gridPaneLista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //if()
        modificar = false;
        menuDesplegado = false;
        calendario = Calendar.getInstance();
        String fecha = calendario.get(Calendar.DATE) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR);
        itemFecha.getEditor().setText(fecha);
        llenarTablaHorarioGlobal();
        cargarGruposHorario();
        cargarRentasHorario();
        reactivarDiaHorarioCheckBox(1, false);
    }

    public void setRentaLlegada(Renta renta) {
        rentaLlegada = renta;
        cambiarModificar(null);
        txtMonto.setDisable(true);
        txtBusqueda.setDisable(true);
        btnToggleCrear.setDisable(true);
        btnBuscar.setDisable(true);
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
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe colocar un nombre para buscar primero", ButtonType.OK);
            alerta.show();
        }
    }

    @FXML
    private void cambiarModificar(ActionEvent event) {
        if (!modificar) {
            if (rentaLlegada != null) {
                modificar = true;
                calendario = rentaLlegada.getFecha();
                String fecha = calendario.get(Calendar.DATE) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR);
                itemFecha.getEditor().setText(fecha);
                btnToggleModificar.setStyle("-fx-background-color: #854c5d; -fx-text-fill: white;");
                btnToggleCrear.setStyle("-fx-background-color: lightpink; -fx-text-fill: black;");
                calendario = rentaLlegada.getFecha();
                txtMonto.setText(String.valueOf(rentaLlegada.getMonto()));
                renta = rentaLlegada;
                actualizarFechas(null);
            } else {
                modificar = true;
                btnToggleModificar.setStyle("-fx-background-color: #854c5d; -fx-text-fill: white;");
                btnToggleCrear.setStyle("-fx-background-color: lightpink; -fx-text-fill: black;");
                btnCancelarRenta.setVisible(modificar);
                btnSeleccionarRenta.setVisible(modificar);
                gridPaneTabla.setDisable(true);
                btnActualizarFechas.setDisable(true);
                vaciarCampos();
                calendario = Calendar.getInstance();
                String fecha = calendario.get(Calendar.DATE) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR);
                itemFecha.getEditor().setText(fecha);
                vaciarHorarioCheckBox();
                cargarGruposHorario();
                cargarRentasHorario();
                actualizarFechas(null);
            }
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
            renta = null;
            calendario = Calendar.getInstance();
            String fecha = calendario.get(Calendar.DAY_OF_MONTH) + "/" + (calendario.get(Calendar.MONTH) + 1) + "/" + calendario.get(Calendar.YEAR);
            itemFecha.getEditor().setText(fecha);

            vaciarHorarioCheckBox();
            cargarGruposHorario();
            cargarRentasHorario();
            btnActualizarFechas.setDisable(false);
            gridPaneTabla.setDisable(false);
        }
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
                int[] horas = retornarHoras(1);
                if (horas[1] != 0) {
                    String[] parts = seleccion.split("|");
                    Cliente cliente = new Cliente(parts[0]);
                    if (!"".equals(txtMonto.getText())) {
                        try {
                            new Renta().guardarNuevaRenta(new Renta(0, Integer.parseInt(txtMonto.getText()), calendario, horas[0], horas[1], cliente, true));
                            vaciarCampos();
                            vaciarHorarioCheckBox();
                            cargarGruposHorario();
                            cargarRentasHorario();
                            MensajeController.mensajeInformacion("La renta se ha guardado satisfactoriamente");
                        } catch (Exception ex) {
                            Alert alerta = new Alert(Alert.AlertType.ERROR, "Se debe introducir un monto valido", ButtonType.OK);
                            alerta.show();
                        }

                    } else {
                        Alert alerta = new Alert(Alert.AlertType.ERROR, "Se debe introducir un monto para la renta", ButtonType.OK);
                        alerta.show();
                    }
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar un horario para la renta", ButtonType.OK);
                    alerta.show();
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar un cliente primero", ButtonType.OK);
                alerta.show();
            }
        } else {
            if (renta != null) {
                int[] horas = retornarHoras(1);
                if (horas[1] != 0) {
                    try {
                        new Renta().guardarCambios(new Renta(renta.getId(), Integer.parseInt(txtMonto.getText()), calendario, horas[0], horas[1], renta.getCliente(), true));
                        vaciarCampos();
                        vaciarHorarioCheckBox();
                        cargarGruposHorario();
                        cargarRentasHorario();
                        btnActualizarFechas.setDisable(true);
                        gridPaneTabla.setDisable(true);
                        MensajeController.mensajeInformacion("La renta se ha guardado satisfactoriamente");
                        if (panelPrincipal != null) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLAdministrarHorarios.fxml"));
                            Parent parent = (Parent) loader.load();
                            FXMLAdministrarHorariosController control = loader.getController();
                            control.setPanelPrincipal(panelPrincipal);
                            panelPrincipal.getChildren().clear();
                            panelPrincipal.getChildren().addAll(parent.getChildrenUnmodifiable());
                        }
                    } catch (Exception ex) {
                        Alert alerta = new Alert(Alert.AlertType.ERROR, "Se debe introducir un monto valido", ButtonType.OK);
                        alerta.show();
                    }

                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar un horario para la renta", ButtonType.OK);
                    alerta.show();
                }
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "No puede guardar una renta que no ha sido elegida de un cliente", ButtonType.OK);
                alerta.setWidth(400);
                alerta.show();
            }
        }
    }

    @FXML
    private void cancelarRenta(ActionEvent event) {
        String seleccion = listRentas.getSelectionModel().getSelectedItem();
        if (seleccion != null) {
            String[] parts = seleccion.split("|");
            new Renta().cancelarRenta(Integer.parseInt(parts[0]));
            MensajeController.mensajeInformacion("se ha cancelado la renta correctamente");
            vaciarCampos();
            vaciarHorarioCheckBox();
            cargarGruposHorario();
            cargarRentasHorario();
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar una renta primero de la lista", ButtonType.OK);
            alerta.show();
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
            //System.out.println(this.renta);
            txtMonto.setText(String.valueOf(renta.getMonto()));
            String fecha = renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + (renta.getFecha().get(Calendar.MONTH) + 1) + "/" + renta.getFecha().get(Calendar.YEAR);
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
            btnActualizarFechas.setVisible(true);
            actualizarFechas(null);
            gridPaneTabla.setDisable(false);
            gridPaneLista.setVisible(false);
            btnActualizarFechas.setDisable(false);
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar una una renta para salir", ButtonType.OK);
            alerta.show();
        }

    }

    @FXML
    private void seleccionarRentaListaEmergente(ActionEvent event) {
        if (modificar) {
            String seleccion = listaBusqueda.getSelectionModel().getSelectedItem();
            if (seleccion != null) {
                listRentas.getItems().clear();
                ObservableList<String> lista = listRentas.getItems();

                String[] parts = seleccion.split("|");
                List<Renta> rentas = new Renta().cargarRentas(new Cliente(parts[0]));
                for (Renta renta : rentas) {
                    String string = renta.getId() + " | " + renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + (renta.getFecha().get(Calendar.MONTH) + 1) + "/" + renta.getFecha().get(Calendar.YEAR)
                            + " de " + convertirHora(String.valueOf(renta.getHoraInicio())) + " a las " + convertirHora(String.valueOf(renta.getHoraFin())) + " de $" + renta.getMonto();
                    lista.add(string);
                }
                if (lista.isEmpty()) {
                    MensajeController.mensajeInformacion("No hay rentas que modificar para este cliente");
                } else {
                    listRentas.setVisible(true);
                    btnSeleccionarRentaVentana.setVisible(true);
                    btnSeleccionarRenta.setVisible(false);
                    btnToggleCrear.setDisable(true);
                    listaBusqueda.setDisable(true);
                    btnBuscar.setDisable(true);
                    btnActualizarFechas.setVisible(false);
                    txtBusqueda.setDisable(true);
                    btnCerrarRentaVentana.setVisible(true);
                    gridPaneLista.setVisible(true);
                    listRentas.setItems(FXCollections.observableArrayList());
                }
                listRentas.setItems(lista);
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar un cliente primero", ButtonType.OK);
                alerta.show();
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
        gridPaneLista.setVisible(false);
        btnCerrarRentaVentana.setVisible(false);
        btnActualizarFechas.setVisible(true);
        listRentas.setItems(FXCollections.observableArrayList());

    }
    
        public static String convertirHora(String horaBase) {
        String horaConvertida = "";
        if (horaBase.length() == 3) {
            horaConvertida += horaBase.charAt(0);
            horaConvertida += ":";
            if (horaBase.charAt(1) == '5') {
                horaConvertida += "3";
            } else {
                horaConvertida += "0";
            }
            horaConvertida += "0";
        } else {
            if (horaBase.length() > 3) {
                horaConvertida += horaBase.charAt(0);
                horaConvertida += horaBase.charAt(1);
                horaConvertida += ":";
                if (horaBase.charAt(2) == '5') {
                    horaConvertida += "3";
                } else {
                    horaConvertida += "0";
                }
                horaConvertida += "0";
            } else {
                horaConvertida += "00";
                horaConvertida += ":";
                if (horaBase.charAt(0) == '5') {
                    horaConvertida += "3";
                } else {
                    horaConvertida += "0";
                }
                horaConvertida += "0";
            }
        }
        return horaConvertida;
    }

    private void llenarTablaHorarioGlobal() {
        String[] mapaHoras = {"", "00:01-00:30", "00:31-01:00", "01:01-01:30", "01:31-02:00", "02:01-02:30", "02:31-03:00",
            "03:01-03:30", "03:31-04:00", "04:01-04:30", "04:31-05:00", "05:01-05:30", "05:31-06:00", "06:01-06:30", "06:31-07:00",
            "07:01-07:30", "07:31-08:00", "08:01-08:30", "08:31-09:00", "09:01-09:30", "09:31-10:00", "10:01-10:30", "10:31-11:00", "11:01-11:30", "11:31-12:00",
            "12:01-12:30", "12:31-13:00", "13:01-13:30", "13:31-14:00", "14:01-14:30", "14:31-15:00", "15:01-15:30", "15:31-16:00",
            "16:01-16:30", "16:31-17:00", "17:01-17:30", "17:31-18:00", "18:01-18:30", "18:31-19:00", "19:01-19:30", "19:31-20:00",
            "20:00-20:30", "20:31-21:00", "21:01-21:30", "21:31-22:00", "22:01-22:30", "22:31-23:00", "23:01-23:30", "23:31-24:00"};
        for (int j = 1; j < 49; j++) {
            String hora = mapaHoras[j];
            for (int i = 0; i < 2; i++) {
                StackPane panel = new StackPane();
                Label lb;
                JFXCheckBox check;
                panel.setPrefWidth(74.1327);
                panel.setMinHeight(25.0);
                if (i == 0) {
                    lb = new Label();
                    lb.setText(hora);
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

    private void cargarGruposHorario() {
        int diaSemana;
        diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        diaSemana += -1;
        if (diaSemana == 0) {
            diaSemana = 7;
        }

        List<Grupo> gruposXML = GrupoXML.obtenerGruposDiaSemana(diaSemana);
        if (!gruposXML.isEmpty()) {
            for (Grupo grupo : gruposXML) {
                colocarGrupoHorario(1, grupo, false);
            }
        }
    }

    private void colocarGrupoHorario(int j, Grupo grupo, boolean bandera) {
        float ini = Float.valueOf(grupo.getHoraInicio());
        float fin = Float.valueOf(grupo.getHoraFin());
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

    private void cargarRentasHorario() {
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
                    if (diaAnio == calendario.get(Calendar.DAY_OF_YEAR)) {
                        if (("R" + lista.get(i).getId()).equals(id)) {
                            colocarRentaHorarioFlotante(j, lista.get(i), true);
                        } else {
                            colocarRentaHorarioFlotante(j, lista.get(i), false);
                        }
                    }
                } else {
                    if (diaAnio == calendario.get(Calendar.DAY_OF_YEAR)) {
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
                            if ("     ".equals(check2.getText())) {
                                check2.setDisable(false);
                            }
                        }
                    } else {
                        Node nodoCercano = gridPaneTabla.lookup("#" + j + "," + (i + 1));
                        JFXCheckBox check2 = (JFXCheckBox) nodoCercano;
                        if (check2 != null) {
                            if ("     ".equals(check2.getText())) {
                                check2.setDisable(false);
                            }
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
                        if ("     ".equals(checkAbajo.getText())) {
                            checkAbajo.setDisable(false);
                        }
                        checkArriba.setDisable(true);
                        check.setText(id);
                    } else {
                        checkAbajo.setDisable(true);
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
        if ("".equals(itemFecha.getEditor().getText())) {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Debe seleccionar una fecha primero", ButtonType.OK);
            alerta.show();
        } else {
            if (rentaLlegada == null) {
                vaciarHorarioCheckBox();
                int dia = Integer.parseInt(itemFecha.getEditor().getText().split("/")[0]);
                int mes = Integer.parseInt(itemFecha.getEditor().getText().split("/")[1]);
                int anio = Integer.parseInt(itemFecha.getEditor().getText().split("/")[2]);
                calendario.set(anio, mes - 1, dia);
                reactivarDiaHorarioCheckBox(1, true);
                cargarGruposHorario();
                cargarRentasHorario();
            } else {
                if (rentaLlegada.getEstado()) {
                    vaciarHorarioCheckBox();
                    calendario = rentaLlegada.getFecha();
                    reactivarDiaHorarioCheckBox(1, true);
                    cargarGruposHorario();
                    cargarRentasHorario();
                    rentaLlegada.setEstado(false);
                } else {
                    vaciarHorarioCheckBox();
                    int dia = Integer.parseInt(itemFecha.getEditor().getText().split("/")[0]);
                    int mes = Integer.parseInt(itemFecha.getEditor().getText().split("/")[1]);
                    int anio = Integer.parseInt(itemFecha.getEditor().getText().split("/")[2]);
                    calendario.set(anio, mes - 1, dia);
                    reactivarDiaHorarioCheckBox(1, true);
                    cargarGruposHorario();
                    cargarRentasHorario();
                }

            }
        }
    }

    private int[] retornarHoras(int x) {
        String id = "R";
        if (renta != null) {
            id += renta.getId();
        } else {
            id += "enta";
        }
        int[] horas = new int[2];
        int cont = 0;
        int[] mapaHoras = new int[49];
        for (int i = 0; i < 49; i++) {
            mapaHoras[i] = i * 50;
        }
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + x + "," + i);
            if (check.getText().equals(id)) {
                if (cont == 0) {
                    horas[0] = mapaHoras[i - 1];
                    cont++;
                } else {
                    horas[1] = mapaHoras[i];
                }
            }
        }
        if (cont != 0) {
            if (horas[0] != 0 && horas[1] == 0) {
                horas[1] = horas[0] + 50;
            }
            if (horas[0] == 0 && horas[1] == 0) {
                horas[1] = 50;
            }
        }
        return horas;
    }

    private void vaciarHorarioCheckBox() {
        for (int j = 1; j < 2; j++) {
            for (int i = 1; i < 49; i++) {
                JFXCheckBox check = (JFXCheckBox) gridPaneTabla.lookup("#" + j + "," + i);
                check.setSelected(false);
                check.setDisable(true);
                check.setText("     ");
            }
        }
    }

    @FXML
    private void verificarSoloDigitos(KeyEvent event) {
        char cadena = event.getCharacter().charAt(0);
        if (Character.isDigit(cadena) && ((JFXTextField) event.getSource()).getText().length() < 6) {

        } else {
            event.consume();
        }
    }

    public void setPanelPrincipal(AnchorPane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

}
