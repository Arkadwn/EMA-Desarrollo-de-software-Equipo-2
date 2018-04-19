package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.GrupoXML;
import emaaredespacio.modelo.HorarioGlobal;
import emaaredespacio.modelo.Renta;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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

    /**
     * Initializes the controller class.
     */
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
        for (int j = 1; j < 49; j++) {
            HorarioGlobal hora = new HorarioGlobal(j);
            for (int i = 0; i < 8; i++) {
                StackPane panel = new StackPane();
                Label lb;
                panel.setPrefWidth(74.1327);
                panel.setPrefHeight(5.0);
                if (i == 0) {
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
        dia = Integer.parseInt(fecha.split("/")[0]);
        mes = Integer.parseInt(fecha.split("/")[1]);
        annio = Integer.parseInt(fecha.split("/")[2]);

        actualizarTablaSegunPicker(dia, mes, annio, false);
        fechaConsultada.setTime(new Date(Integer.parseInt(itemDate.getEditor().getText().split("/")[2]) - 1900, Integer.parseInt(itemDate.getEditor().getText().split("/")[1]) - 1, Integer.parseInt(itemDate.getEditor().getText().split("/")[0])));
        llenarTablaHorarioGlobal();
        cargarRentasHorario();
        cargarGrupos();
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
        float ini = Float.valueOf(grupo.getFecha_inicio());
        float fin = Float.valueOf(grupo.getFecha_fin());
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
            horaConvertida += horaBase.charAt(0);
            horaConvertida += horaBase.charAt(1);
            horaConvertida += ":";
            if (horaBase.charAt(2) == '5') {
                horaConvertida += "3";
            } else {
                horaConvertida += "0";
            }
            horaConvertida += "0";
        }
        return horaConvertida;
    }

    private void cargarGrupos() {
        for (int i = 1; i < 8; i++) {
            List<Grupo> gruposXML = GrupoXML.obtenerGruposDiaSemana(i);
            if (!gruposXML.isEmpty()) {
                for (Grupo grupo : gruposXML) {
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
        float ini = renta.getHoraInicio();
        float fin = renta.getHoraFin();
        String fecha = renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/" + (renta.getFecha().get(Calendar.MONTH) + 1) + "/" + renta.getFecha().get(Calendar.YEAR);
        String[] mapaDias = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        lbHorario.setText(mapaDias[dia] + " " + fecha + " de: " + convertirHora(ini + "") + " a " + convertirHora(fin + ""));
    }

    private void actionBtnGrupo(JFXButton button) {
        String idGrupo = button.getText().split("G")[1];
        Grupo grupo = GrupoXML.obtenerGrupoSegunID(idGrupo);
        System.out.println(grupo.getDias());
        lbIdentificador.setText(button.getText());
        lbClaseRenta.setText("Grupo");
        Colaborador nombreColaborador = new Colaborador().buscarColaboradorSegunID(grupo.getIdColaborador());
        lbEncargado.setText(nombreColaborador.getNombre() + " " + nombreColaborador.getApellidos());
        lbDescripcion.setText("$");
        String[] mapaDias = {"", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        String[] diasGrupo = grupo.getDias().split("/");
        String[] horasGrupos = grupo.getHoras().split("/");
        String horario = "";
        for (int i = 0; i < diasGrupo.length; i++) {
            String[] horarioGrupo = horasGrupos[i].split("-");
            horario += mapaDias[Integer.parseInt(diasGrupo[i])] + " de " + convertirHora(horarioGrupo[0]) + " a " + convertirHora(horarioGrupo[1]) + "\n";
        }

        lbHorario.setText(horario);
    }

    @FXML
    private void editarHorarioLanzar(ActionEvent event) {
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
                        HorarioGlobal hora = new HorarioGlobal(j);
                        for (int i = 0; i < 8; i++) {
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
                //Lanzar controladorRenta
            }
        } else {
            //mostrar mensaje seleccionar un grupo o renta
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
        if (check.isSelected()) {
            if (y - 1 != 0 && y + 1 < 49) {
                if (checkArriba.isSelected()) {
                    if (checkArriba.getText().equals(lbIdentificador.getText())) {
                        checkAbajo.setDisable(false);
                        checkArriba.setDisable(true);
                        check.setText(lbIdentificador.getText());
                    } else {
                        checkAbajo.setDisable(false);
                        check.setText(lbIdentificador.getText());
                    }
                } else {
                    if (checkAbajo.getText().equals(lbIdentificador.getText())) {
                        checkArriba.setDisable(false);
                        checkAbajo.setDisable(true);
                        check.setText(lbIdentificador.getText());
                    } else {
                        check.setText(lbIdentificador.getText());
                    }
                }
            } else if (y - 1 == 0) {
                checkAbajo.setDisable(true);
                check.setText(lbIdentificador.getText());
            } else if (y + 1 == 49) {
                checkArriba.setDisable(true);
                check.setText(lbIdentificador.getText());
            }
        } else {
            if (y - 1 != 0 && y + 1 < 49) {
                if (checkArriba.isSelected()) {
                    if (checkArriba.getText().equals(lbIdentificador.getText())) {
                        checkAbajo.setDisable(true);
                        checkArriba.setDisable(false);
                        check.setText("     ");
                    } else {
                        checkAbajo.setDisable(true);
                        check.setText("     ");
                    }
                } else {
                    if (checkAbajo.getText().equals(lbIdentificador.getText())) {
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
                //System.out.println(gruposXML.size());
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
        float ini = Float.valueOf(grupo.getFecha_inicio());
        float fin = Float.valueOf(grupo.getFecha_fin());
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
    private void regresarMenuPrincipal(ActionEvent event) {
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
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
                }
                dias += "/" + i;
                int[] horas2 = retornarHoras(i);
                if (horas2[1] != 0) {
                    horas += "/" + horas2[0] + "-" + horas2[1];
                } else {
                    horas += "/" + horas2[0];
                }
            }
        }
        grupo.setDias(dias);
        grupo.setHoras(horas);
        GrupoXML.eliminarGrupoSegunID(lbIdentificador.getText().split("G")[1]);
        GrupoXML.guardarGrupo(grupo);
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
}
