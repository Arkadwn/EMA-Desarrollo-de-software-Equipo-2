package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.utilerias.GrupoXML;
import emaaredespacio.modelo.Renta;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Adrián Bustamante Zarate
 */
public class FXMLAdministrarHorariosController implements Initializable {

    @FXML
    private DatePicker itemDate;
    @FXML
    private Label lbIdentificador;
    @FXML
    private Label lbClaseRenta;
    @FXML
    private Label lbEncargado;
    @FXML
    private Label lbDescripcion;
    @FXML
    private Label lbHorario;
    @FXML
    private JFXButton btnEditar;
    @FXML
    private GridPane gridHorario;
    @FXML
    private ScrollPane root;
    @FXML
    private Label lbLunes;
    @FXML
    private Label lbMartes;
    @FXML
    private Label lbMiercoles;
    @FXML
    private Label lbJueves;
    @FXML
    private Label lbViernes;
    @FXML
    private Label lbSabado;
    @FXML
    private Label lbDomingo;
    @FXML
    private JFXButton btnActualizarTabla;
    private Calendar fechaConsultada;
    @FXML
    private GridPane gridFlotante;
    @FXML
    private ScrollPane root1;
    @FXML
    private GridPane gridHorario1;
    @FXML
    private JFXButton btnEditarLanzador;
    @FXML
    private JFXButton btnCerrarGrid;
    @FXML
    private Label lbLunesHoras;
    @FXML
    private Label lbMartesHoras;
    @FXML
    private Label lbViernesHoras;
    @FXML
    private Label lbSabadoHoras;
    @FXML
    private Label lbDomingoHoras;
    @FXML
    private Label lbMiercolesHoras;
    @FXML
    private Label lbJuevesHoras;
    private boolean creoHorarioFlotante;
    @FXML
    private JFXButton btn1;
    @FXML
    private JFXButton btn2;
    @FXML
    private JFXButton btn3;
    @FXML
    private JFXButton btn4;
    @FXML
    private JFXButton btn5;
    @FXML
    private JFXButton btn6;
    @FXML
    private JFXButton btn7;
    @FXML
    private JFXButton btnActualizarHoras;
    @FXML
    private AnchorPane panelFondo;
    @FXML
    private JFXComboBox<Grupo> cbxGrupos;
    @FXML
    private JFXButton btnAsignarHorario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaConsultada = Calendar.getInstance();
        fechaConsultada.setTime(new Date(fechaConsultada.get(Calendar.YEAR) - 1900, fechaConsultada.get(Calendar.MONTH), fechaConsultada.get(Calendar.DAY_OF_MONTH)));
        llenarTablaHorarioGlobal();
        actualizarTablaSegunPicker(Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR), true);
        cargarRentasHorario();
        cargarGrupos();
        creoHorarioFlotante = false;
    }

    private void cargarRentasHorario() {
        List<Renta> lista = new Renta().cargarRentas();
        for (int j = 1; j < 8; j++) {
            for (int i = 0; i < lista.size(); i++) {
                Calendar calendarioAux = lista.get(i).getFecha();
                int diaRenta = calendarioAux.get(Calendar.DAY_OF_YEAR);
                int diaFechaConsult = obtenerFechaSegunDiaSemana(j, fechaConsultada).get(Calendar.DAY_OF_YEAR);
                if (diaRenta == diaFechaConsult) {
                    colocarRenta(j, lista.get(i));
                }
            }
        }
    }

    private Calendar obtenerFechaSegunDiaSemana(int dia, Calendar fecha) {
        int diaSemana = fecha.get(Calendar.DAY_OF_WEEK);
        Calendar copiaFecha = fecha;
        if (diaSemana == 1) {
            diaSemana = 7;
        } else {
            diaSemana = diaSemana - 1;
        }
        diaSemana = diaSemana - dia;
        copiaFecha.add(Calendar.DATE, -diaSemana);

        return copiaFecha;
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
            for (int i = 0; i < 8; i++) {
                StackPane panel = new StackPane();
                Label lb;
                panel.setPrefWidth(74.1327);
                panel.setPrefHeight(5.0);
                if (i == 0) {
                    lb = new Label();
                    lb.setText(hora);
                    panel.getChildren().add(lb);
                    StackPane.setAlignment(lb, Pos.CENTER);
                }
                if (j % 2 != 0) {
                    panel.setStyle("-fx-background-color: #FFF; -fx-border-color: #a2a2a2;");
                } else {
                    panel.setStyle("-fx-background-color: #f1f1f1; -fx-border-color: #a2a2a2;");
                }
                gridHorario.add(panel, i, j);
            }
        }
    }

    private void actualizarTablaSegunPicker(int dia, int mes, int anio, boolean inicializa) {
        if (!inicializa) {
            mes = mes - 1;
        }
        Calendar calendar = Calendar.getInstance();
        int diaSemana;
        calendar.set(anio, mes, dia);
        diaSemana = calendar.get(Calendar.DAY_OF_WEEK);
        switch (diaSemana) {
            case 2:
                pintarNegroLabels();
                lbLunes.setStyle("-fx-text-fill: red;");
                break;
            case 3:
                pintarNegroLabels();
                lbMartes.setStyle("-fx-text-fill: red;");
                break;
            case 4:
                pintarNegroLabels();
                lbMiercoles.setStyle("-fx-text-fill: red;");
                break;
            case 5:
                pintarNegroLabels();
                lbJueves.setStyle("-fx-text-fill: red;");
                break;
            case 6:
                pintarNegroLabels();
                lbViernes.setStyle("-fx-text-fill: red;");
                break;
            case 7:
                pintarNegroLabels();
                lbSabado.setStyle("-fx-text-fill: red;");
                break;
            case 1:
                pintarNegroLabels();
                lbDomingo.setStyle("-fx-text-fill: red;");
                break;
        }
    }

    private void pintarNegroLabels() {
        lbLunes.setStyle("-fx-text-fill: black;");
        lbMartes.setStyle("-fx-text-fill: black;");
        lbMiercoles.setStyle("-fx-text-fill: black;");
        lbJueves.setStyle("-fx-text-fill: black;");
        lbViernes.setStyle("-fx-text-fill: black;");
        lbSabado.setStyle("-fx-text-fill: black;");
        lbDomingo.setStyle("-fx-text-fill: black;");
    }

    @FXML
    private void actualizarTablaHorario(ActionEvent event) {
        int dia, mes, annio;
        String fecha = itemDate.getEditor().getText();
        if (!"".equals(fecha)) {
            dia = Integer.parseInt(fecha.split("/")[0]);
            mes = Integer.parseInt(fecha.split("/")[1]);
            annio = Integer.parseInt(fecha.split("/")[2]);

            actualizarTablaSegunPicker(dia, mes, annio, false);
            fechaConsultada.setTime(new Date(Integer.parseInt(itemDate.getEditor().getText().split("/")[2]) - 1900, Integer.parseInt(itemDate.getEditor().getText().split("/")[1]) - 1, Integer.parseInt(itemDate.getEditor().getText().split("/")[0])));
            llenarTablaHorarioGlobal();
            cargarRentasHorario();
            cargarGrupos();
        } else {
            dia = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            mes = Calendar.getInstance().get(Calendar.MONTH);
            annio = Calendar.getInstance().get(Calendar.YEAR);
            actualizarTablaSegunPicker(dia, mes, annio, false);
            fechaConsultada.setTime(new Date(annio - 1900, mes, dia));
            llenarTablaHorarioGlobal();
            cargarRentasHorario();
            cargarGrupos();
        }
    }

    private void colocarRenta(int j, Renta get) {
        float ini = get.getHoraInicio();
        float fin = get.getHoraFin();
        String textoBtn = "R" + get.getId();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;

        for (int i = (int) ini; i < fin + 1; i++) {
            JFXButton button = new JFXButton(textoBtn);
            button.setPrefWidth(74.1327);
            button.setPrefHeight(5.0);
            button.setOnAction((event) -> {
                actionBtnRenta(button, j);
            });
            gridHorario.add(button, j, i);
        }
    }

    private void colocarGrupo(int j, Grupo grupo) {
        float ini = Float.valueOf(grupo.getHoraInicio());
        float fin = Float.valueOf(grupo.getHoraFin());
        String textoBtn = "G" + grupo.getIdGrupo();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;

        for (int i = (int) ini; i < fin + 1; i++) {
            JFXButton button = new JFXButton(textoBtn);

            button.setPrefWidth(74.1327);
            button.setPrefHeight(5.0);
            button.setOnAction((event) -> {
                actionBtnGrupo(button);
            });
            gridHorario.add(button, j, i);
        }
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

    private void cargarGrupos() {
        List<Grupo> gruposNoAsignados = new Grupo().buscarGrupos();
        if (!gruposNoAsignados.isEmpty()) {
            for (int i = 0; i < gruposNoAsignados.size(); i++) {
                if (gruposNoAsignados.get(i).getHorario_asignado() == 1) {
                    gruposNoAsignados.remove(i);
                }
            }
        }
        if (gruposNoAsignados.isEmpty()) {
            cbxGrupos.setDisable(true);
            btnAsignarHorario.setDisable(true);
        } else {
            cbxGrupos.getItems().clear();
            cbxGrupos.setItems(FXCollections.observableArrayList(gruposNoAsignados));
        }
        for (int i = 1; i < 8; i++) {
            List<Grupo> gruposXML = GrupoXML.obtenerGruposDiaSemana(i);
            if (!gruposXML.isEmpty()) {
                for (Grupo grupo : gruposXML) {
                    //System.out.println("Entre");
                    colocarGrupo(i, grupo);
                }
            }
        }
    }

    private void actionBtnRenta(Node nodo, int dia) {
        JFXButton button = (JFXButton) nodo;
        String idRenta = button.getText().split("R")[1];
        Renta renta = new Renta().cargarRenta(idRenta);
        lbIdentificador.setText(button.getText());
        lbClaseRenta.setText("Renta");
        lbEncargado.setText(new Cliente().obtenerClienteID(renta.getCliente().getId()).getNombre());
        lbDescripcion.setText("$" + renta.getMonto());
        int ini = renta.getHoraInicio();
        int fin = renta.getHoraFin();
        String fecha = renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + (renta.getFecha().get(Calendar.MONTH) + 1) + "/" + renta.getFecha().get(Calendar.YEAR);
        String[] mapaDias = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        lbHorario.setText(mapaDias[dia] + " " + fecha + " de: " + convertirHora(String.valueOf(ini)) + " a " + convertirHora(String.valueOf(fin)));
    }

    private void actionBtnGrupo(JFXButton button) {
        String idGrupo = button.getText().split("G")[1];
        Grupo grupo = GrupoXML.obtenerGrupoSegunID(idGrupo);
        //System.out.println(grupo.getDias());
        lbIdentificador.setText(button.getText());
        lbClaseRenta.setText("Grupo");
        Colaborador nombreColaborador = new Colaborador().buscarColaboradorSegunID(grupo.getIdColaborador());
        lbEncargado.setText(nombreColaborador.getNombre() + " " + nombreColaborador.getApellidos());
        //lbDescripcion.setText("$");
        String[] mapaDias = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        String[] diasGrupo = grupo.getDias().split("/");
        String[] horasGrupos = grupo.getHoras().split("/");
        String horario = "";
        for (int i = 0; i < diasGrupo.length; i++) {

            String[] horarioGrupo = horasGrupos[i].split("-");
            if (horarioGrupo.length > 1) {
                horario += mapaDias[Integer.parseInt(diasGrupo[i])] + " de " + convertirHora(horarioGrupo[0]) + " a " + convertirHora(horarioGrupo[1]) + "\n";
            } else {
                horario += mapaDias[Integer.parseInt(diasGrupo[i])] + " a las " + convertirHora(horarioGrupo[0]) + "\n";
            }
        }
        lbHorario.setText(horario);
    }

    @FXML
    private void editarHorarioLanzar(ActionEvent event) {
        String[] mapaHoras = {"", "00:01-00:30", "00:31-01:00", "01:01-01:30", "01:31-02:00", "02:01-02:30", "02:31-03:00",
            "03:01-03:30", "03:31-04:00", "04:01-04:30", "04:31-05:00", "05:01-05:30", "05:31-06:00", "06:01-06:30", "06:31-07:00",
            "07:01-07:30", "07:31-08:00", "08:01-08:30", "08:31-09:00", "09:01-09:30", "09:31-10:00", "10:01-10:30", "10:31-11:00", "11:01-11:30", "11:31-12:00",
            "12:01-12:30", "12:31-13:00", "13:01-13:30", "13:31-14:00", "14:01-14:30", "14:31-15:00", "15:01-15:30", "15:31-16:00",
            "16:01-16:30", "16:31-17:00", "17:01-17:30", "17:31-18:00", "18:01-18:30", "18:31-19:00", "19:01-19:30", "19:31-20:00",
            "20:00-20:30", "20:31-21:00", "21:01-21:30", "21:31-22:00", "22:01-22:30", "22:31-23:00", "23:01-23:30", "23:31-24:00"};
        if (!"Identificador".equals(lbIdentificador.getText())) {
            if (lbIdentificador.getText().charAt(0) == 'G') {
                itemDate.setDisable(true);
                btnActualizarTabla.setDisable(true);
                gridHorario.setDisable(true);
                gridFlotante.setVisible(true);
                if (!creoHorarioFlotante) {
                    creoHorarioFlotante = true;
                    for (int j = 1; j < 49; j++) {
                        //Mapear horas
                        String hora = mapaHoras[j];
                        for (int i = 0; i < 8; i++) {
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
                            gridHorario1.add(panel, i, j);
                        }
                    }
                    cargarRentasHorarioFlotante();
                    cargarGruposHorarioFlotante();
                    actualizarHoras(null);
                } else {
                    cargarRentasHorarioFlotante();
                    cargarGruposHorarioFlotante();
                    actualizarHoras(null);
                }
            } else {
                try {
                    panelFondo.getChildren().clear();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLAdministrarRentas.fxml"));
                    Parent parent = (Parent) loader.load();
                    FXMLAdministarRentasController control = loader.getController();
                    Renta nuevaRenta = new Renta().cargarRenta(lbIdentificador.getText().split("R")[1]);
                    control.setRentaLlegada(nuevaRenta);
                    panelFondo.getChildren().addAll(parent.getChildrenUnmodifiable());
                } catch (IOException ex) {
                    System.out.println("Error al cargar rentas");
                    Alert alerta = new Alert(Alert.AlertType.ERROR, "Error no verificado?", ButtonType.OK);
                    alerta.show();
                }
            }
        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR, "Primero se debe seleccionar un grupo o una renta", ButtonType.OK);
            alerta.show();
        }
    }

    private int comprobarDia(int dia) {
        //boolean bandera = false;
        int cont = 0;
        int result = 0;
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + i);
            if (check.getText().equals(lbIdentificador.getText())) {
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
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + i);
            if (check.getText().equals("     ")) {
                check.setDisable(true);
            }
            if (check.getText().equals(lbIdentificador.getText())) {
                y = i;
            }
        }
        int abajo = y + 1;
        int arriba = y - 1;
        JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + y);
        check.setDisable(false);
        if (arriba > 0) {
            JFXCheckBox checkArriba = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + arriba);
            if ("     ".equals(checkArriba.getText())) {
                checkArriba.setDisable(false);
            }
        }
        if (abajo < 49) {
            JFXCheckBox checkAbajo = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + abajo);
            if ("     ".equals(checkAbajo.getText())) {
                checkAbajo.setDisable(false);
            }
        }
    }

    private void reactivarDiaHorarioCheckBox(int dia, boolean bandera) {
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + i);
            if (check.getText().equals("     ")) {
                check.setDisable(bandera);
            }
        }
    }

    private void vaciarDiaHorarioCheckBox(int dia) {

        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + dia + "," + i);
            if (check.getText().equals(lbIdentificador.getText())) {
                check.setSelected(false);
                check.setDisable(true);
                check.setText("     ");
            }
        }

    }

    private void vaciarHorarioCheckBox() {
        for (int j = 1; j < 8; j++) {
            for (int i = 1; i < 49; i++) {
                JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + j + "," + i);
                check.setSelected(false);
                check.setDisable(true);
                check.setText("     ");
            }
        }
    }

    private void deshabilitarCheckBoxCercano(ActionEvent evento) {
        JFXCheckBox check = (JFXCheckBox) evento.getSource();
        int x = Integer.parseInt(check.getId().split(",")[0]);
        int y = Integer.parseInt(check.getId().split(",")[1]);
        Node nodoCercano = gridHorario1.lookup("#" + x + "," + (y - 1));
        JFXCheckBox checkArriba = (JFXCheckBox) nodoCercano;
        Node nodoCercano2 = gridHorario1.lookup("#" + x + "," + (y + 1));
        JFXCheckBox checkAbajo = (JFXCheckBox) nodoCercano2;
        String textoGrupo = lbIdentificador.getText();
        if (check.isSelected()) {
            if (y - 1 != 0 && y + 1 < 49) {
                if (checkArriba.isSelected()) {
                    if (checkArriba.getText().equals(textoGrupo)) {
                        checkAbajo.setDisable(false);
                        checkArriba.setDisable(true);
                        check.setText(textoGrupo);
                    } else {
                        checkAbajo.setDisable(false);
                        check.setText(textoGrupo);
                    }
                } else {
                    if (checkAbajo.getText().equals(textoGrupo)) {
                        checkArriba.setDisable(false);
                        checkAbajo.setDisable(true);
                        check.setText(textoGrupo);
                    } else {
                        check.setText(textoGrupo);
                    }
                }
            } else if (y - 1 == 0) {
                checkAbajo.setDisable(true);
                check.setText(textoGrupo);
            } else if (y + 1 == 49) {
                checkArriba.setDisable(true);
                check.setText(textoGrupo);
            }
        } else {
            if (y - 1 != 0 && y + 1 < 49) {
                if (checkArriba.isSelected()) {
                    if (checkArriba.getText().equals(textoGrupo)) {
                        checkAbajo.setDisable(true);
                        checkArriba.setDisable(false);
                        check.setText("     ");
                    } else {
                        checkAbajo.setDisable(true);
                        check.setText("     ");
                    }
                } else {
                    if (checkAbajo.getText().equals(textoGrupo)) {
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

    private void cargarGruposHorarioFlotante() {
        for (int i = 1; i < 8; i++) {
            List<Grupo> gruposXML = GrupoXML.obtenerGruposDiaSemana(i);
            if (!gruposXML.isEmpty()) {
                for (Grupo grupo : gruposXML) {
                    if (!String.valueOf(grupo.getIdGrupo()).equals(lbIdentificador.getText().split("G")[1])) {
                        colocarGrupoHorarioFlotante(i, grupo, false);
                    } else {
                        colocarGrupoHorarioFlotante(i, grupo, true);
                    }
                }
            }
        }
    }

    private void colocarGrupoHorarioFlotante(int j, Grupo grupo, boolean bandera) {
        float ini = Float.valueOf(grupo.getHoraInicio());
        float fin = Float.valueOf(grupo.getHoraFin());
        String textCheck = "G" + grupo.getIdGrupo();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;
        if (bandera) {
            for (int i = (int) ini; i < fin + 1; i++) {
                JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + j + "," + i);

                if (i == (int) ini || i == (int) fin) {
                    if (i == (int) ini) {
                        Node nodoCercano = gridHorario1.lookup("#" + j + "," + (i - 1));
                        JFXCheckBox check2 = (JFXCheckBox) nodoCercano;
                        if (check2 != null) {
                            check2.setDisable(false);
                        }
                    } else {
                        Node nodoCercano = gridHorario1.lookup("#" + j + "," + (i + 1));
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
                JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + j + "," + i);
                check.setText(textCheck);
                check.setSelected(true);
                check.setDisable(true);
            }
        }
    }

    private void cargarRentasHorarioFlotante() {
        List<Renta> lista = new Renta().cargarRentas();
        for (int j = 1; j < 8; j++) {
            for (int i = 0; i < lista.size(); i++) {
                Calendar calendarioAux = lista.get(i).getFecha();
                int diaSemana = calendarioAux.get(Calendar.DAY_OF_WEEK);
                diaSemana = diaSemana - 1;
                if (diaSemana == 0) {
                    diaSemana = 7;
                }
                if (j == diaSemana) {
                    colocarRentaHorarioFlotante(j, lista.get(i));
                }
            }
        }
    }

    private void colocarRentaHorarioFlotante(int j, Renta get) {
        float ini = get.getHoraInicio();
        float fin = get.getHoraFin();
        String textCheck = "R" + get.getId();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;

        for (int i = (int) ini; i < fin + 1; i++) {
            JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + j + "," + i);
            check.setText(textCheck);
            check.setSelected(true);
            check.setDisable(true);
        }
    }


    @FXML
    private void guardarCambios(ActionEvent event) {
        actualizarHoras(null);
        boolean validacion = false;

        Alert ventanaPregunta = new Alert(Alert.AlertType.CONFIRMATION, "¿Desea guardar los cambios?");
        ButtonType btnSi = new ButtonType("Si", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        ventanaPregunta.getButtonTypes().setAll(btnSi, btnNo);
        //ventanaPregunta.setX(ventanaPregunta.getX() - 100);
        Optional<ButtonType> resultado = ventanaPregunta.showAndWait();

        if (resultado.isPresent()) {
            validacion = resultado.get() == btnSi;
        }

        if (validacion) {
            if ("Sin asignar".equals(lbHorario.getText())) {
                
                Grupo grupo = cbxGrupos.getSelectionModel().getSelectedItem();
                String dias = "";
                String horas = "";
                boolean bandera = true;
                for (int i = 1; i < 8; i++) {
                    if (comprobarDia(i) > 0) {
                        if (bandera) {
                            dias += i;
                            int[] horas2 = retornarHoras(i);
                            if (horas2[1] != 0) {
                                horas += horas2[0] + "-" + horas2[1];
                            } else {
                                horas += horas2[0];
                            }
                            bandera = false;
                        } else {
                            dias += "/" + i;
                            int[] horas2 = retornarHoras(i);
                            if (horas2[1] != 0) {
                                horas += "/" + horas2[0] + "-" + horas2[1];
                            } else {
                                horas += "/" + horas2[0];
                            }
                        }
                    }
                }
                if(!dias.equals("")){
                GrupoXML.eliminarGrupoSegunID(String.valueOf(grupo.getIdGrupo()));
                GrupoXML.guardarGrupo(grupo, dias, horas, "Agregar", "Agregar");
                MensajeController.mensajeInformacion("Se guardaron los cambios correctamente");
                grupo.setHorario_asignado(1);
                new Grupo().guardarCambios(grupo);
                cerrarGridFlotante(null);
                actualizarTablaHorario(null);
                }else{
                    MensajeController.mensajeInformacion("Lo lamento, no se pudo guardar el horario de este grupo, no hay horas asignadas");
                }
            } else {
                Grupo grupo = GrupoXML.obtenerGrupoSegunID(lbIdentificador.getText().split("G")[1]);

                String dias = "";
                String horas = "";
                boolean bandera = true;
                for (int i = 1; i < 8; i++) {
                    if (comprobarDia(i) > 0) {
                        if (bandera) {
                            dias += i;
                            int[] horas2 = retornarHoras(i);
                            if (horas2[1] != 0) {
                                horas += horas2[0] + "-" + horas2[1];
                            } else {
                                horas += horas2[0];
                            }
                            bandera = false;
                        } else {
                            dias += "/" + i;
                            int[] horas2 = retornarHoras(i);
                            if (horas2[1] != 0) {
                                horas += "/" + horas2[0] + "-" + horas2[1];
                            } else {
                                horas += "/" + horas2[0];
                            }
                        }
                    }
                }
                GrupoXML.eliminarGrupoSegunID(lbIdentificador.getText().split("G")[1]);
                GrupoXML.guardarGrupo(grupo, dias, horas, "Agregar", "Agregar");
                MensajeController.mensajeInformacion("Se guardaron los cambios correctamente");
                cerrarGridFlotante(null);
                actualizarTablaHorario(null);
            }
            lbIdentificador.setText("");
            lbClaseRenta.setText("");
            lbEncargado.setText("");
            lbHorario.setText("");
        }
    }

    @FXML
    private void cerrarGridFlotante(ActionEvent event) {
        itemDate.setDisable(false);
        btnActualizarTabla.setDisable(false);
        gridHorario.setDisable(false);
        gridFlotante.setVisible(false);
        vaciarHorarioCheckBox();
    }

    @FXML
    private void cambiarColorSalir(MouseEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        btn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
    }

    @FXML
    private void cambiarColorPasar(MouseEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        btn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }

    @FXML
    private void vaciarColumna(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        int dia = Integer.parseInt(btn.getId().split("btn")[1]);
        vaciarDiaHorarioCheckBox(dia);
        reactivarDiaHorarioCheckBox(dia, false);
    }

    private int[] retornarHoras(int x) {
        int[] horas = new int[2];
        int cont = 0;
        horas[1] = 0;
        int[] mapaHoras = new int[49];
        for (int i = 0; i < 49; i++) {
            mapaHoras[i] = i * 50;
        }
        for (int i = 1; i < 49; i++) {
            JFXCheckBox check = (JFXCheckBox) gridHorario1.lookup("#" + x + "," + i);
            if (check.getText().equals(lbIdentificador.getText())) {
                if (cont == 0) {
                    horas[0] = mapaHoras[i - 1];
                    cont++;
                } else {
                    horas[1] = mapaHoras[i];
                }
            }
        }
        return horas;
    }

    @FXML
    private void actualizarHoras(ActionEvent event) {
        String[] mapaDias = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        for (int i = 1; i < 8; i++) {
            Label lb = (Label) gridFlotante.lookup("#lb" + mapaDias[i] + "Horas");
            if (comprobarDia(i) != 0) {
                int[] horas = retornarHoras(i);
                if (horas[1] == 0) {
                    lb.setText("a las " + convertirHora(horas[0] + ""));
                } else if (horas[1] != 0) {
                    lb.setText("de " + convertirHora(horas[0] + "") + " a las " + convertirHora(horas[1] + ""));
                }
            } else {
                lb.setText("---");
            }
        }
    }

    @FXML
    private void asignarHorarioNuevoGrupo(ActionEvent event) {
        String[] mapaHoras = {"", "00:01-00:30", "00:31-01:00", "01:01-01:30", "01:31-02:00", "02:01-02:30", "02:31-03:00",
            "03:01-03:30", "03:31-04:00", "04:01-04:30", "04:31-05:00", "05:01-05:30", "05:31-06:00", "06:01-06:30", "06:31-07:00",
            "07:01-07:30", "07:31-08:00", "08:01-08:30", "08:31-09:00", "09:01-09:30", "09:31-10:00", "10:01-10:30", "10:31-11:00", "11:01-11:30", "11:31-12:00",
            "12:01-12:30", "12:31-13:00", "13:01-13:30", "13:31-14:00", "14:01-14:30", "14:31-15:00", "15:01-15:30", "15:31-16:00",
            "16:01-16:30", "16:31-17:00", "17:01-17:30", "17:31-18:00", "18:01-18:30", "18:31-19:00", "19:01-19:30", "19:31-20:00",
            "20:00-20:30", "20:31-21:00", "21:01-21:30", "21:31-22:00", "22:01-22:30", "22:31-23:00", "23:01-23:30", "23:31-24:00"};
        if (cbxGrupos.getSelectionModel().getSelectedItem() != null) {
            itemDate.setDisable(true);
            btnActualizarTabla.setDisable(true);
            gridHorario.setDisable(true);
            gridFlotante.setVisible(true);
            if (!creoHorarioFlotante) {
                creoHorarioFlotante = true;
                for (int j = 1; j < 49; j++) {
                    //Mapear horas
                    String hora = mapaHoras[j];
                    for (int i = 0; i < 8; i++) {
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
                        gridHorario1.add(panel, i, j);
                    }
                }
                cargarRentasHorarioFlotante();
                cargarGruposHorarioFlotante();
                actualizarHoras(null);
            } else {
                cargarRentasHorarioFlotante();
                cargarGruposHorarioFlotante();
                actualizarHoras(null);
            }
        } else {
            MensajeController.mensajeInformacion("Debe primero seleccionar un horario nuevo");
        }
    }

    @FXML
    private void asignarHorario(ActionEvent event) {
        if (cbxGrupos.getSelectionModel().getSelectedItem() != null) {
            Grupo grupo = cbxGrupos.getSelectionModel().getSelectedItem();
            lbIdentificador.setText("G" + grupo.getIdGrupo());
            lbClaseRenta.setText("Grupo");
            Colaborador nombreColaborador = new Colaborador().buscarColaboradorSegunID(grupo.getIdColaborador());
            lbEncargado.setText(nombreColaborador.getNombre() + " " + nombreColaborador.getApellidos());
            //lbDescripcion.setText("$");
            lbHorario.setText("Sin asignar");
        } else {

        }
    }
}
