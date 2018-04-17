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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.util.Hashtable;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarGrupoController implements Initializable {

    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfTipoBaile;
    @FXML
    private Spinner spinnerCupo;
    @FXML
    private TableView<Colaborador> tbListaColaboradores;
    @FXML
    private TableColumn<Colaboradores, String> columnaNombre;
    @FXML
    private TableColumn<Colaboradores, String> columnaApellidos;
    private List<Colaborador> lista;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField tfPalabraClave;
    @FXML
    private JFXButton btnBuscar;

    private Colaborador seleccion;
    @FXML
    private CheckBox checkBoxLunes;
    @FXML
    private CheckBox checkBoxMartes;
    @FXML
    private CheckBox checkBoxMiercoles;
    @FXML
    private CheckBox checkBoxJueves;
    @FXML
    private CheckBox checkBoxViernes;
    @FXML
    private CheckBox checkBoxSabado;
    @FXML
    private CheckBox checkBoxDomingo;
    @FXML
    private ComboBox comboBoxMartesIni;
    @FXML
    private ComboBox comboBoxLunesIni;
    @FXML
    private ComboBox comboBoxMiercolesIni;
    @FXML
    private ComboBox comboBoxJuevesIni;
    @FXML
    private ComboBox comboBoxViernesIni;
    @FXML
    private ComboBox comboBoxSabadoIni;
    @FXML
    private ComboBox comboBoxDomingoIni;
    @FXML
    private ComboBox comboBoxLunesFin;
    @FXML
    private ComboBox comboBoxMartesFin;
    @FXML
    private ComboBox comboBoxMiercolesFin;
    @FXML
    private ComboBox comboBoxJuevesFin;
    @FXML
    private ComboBox comboBoxViernesFin;
    @FXML
    private ComboBox comboBoxSabadoFin;
    @FXML
    private ComboBox comboBoxDomingoFin;

    private List<Grupo> grupos;
    @FXML
    private DatePicker datePickerFechaIni;
    @FXML
    private DatePicker datePickerFechaFin;
    LocalDate date;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        lista = new ArrayList();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 25);
        spinnerCupo.setValueFactory(valueFactory);
        grupos = new ArrayList();
        addListeners();
        buscarGrupos();
        datePickerFechaIni.setOnAction(event -> {
            date = datePickerFechaIni.getValue();
            checkBoxLunes.setDisable(false);
            checkBoxMartes.setDisable(false);
            checkBoxMiercoles.setDisable(false);
            checkBoxJueves.setDisable(false);
            checkBoxViernes.setDisable(false);
            checkBoxSabado.setDisable(false);
            checkBoxDomingo.setDisable(false);
            addHours();
        });

        seleccion = new Colaborador();
    }

    public void addListeners() {
        checkBoxLunes.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxLunes.setSelected(!oldValue);
                if (checkBoxLunes.isSelected()) {
                    comboBoxLunesIni.setDisable(false);
                    comboBoxLunesFin.setDisable(false);
                } else {
                    comboBoxLunesIni.setDisable(true);
                    comboBoxLunesFin.setDisable(true);
                }
            }
        });

        checkBoxMartes.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxMartes.setSelected(!oldValue);
                if (checkBoxMartes.isSelected()) {
                    comboBoxMartesIni.setDisable(false);
                    comboBoxMartesFin.setDisable(false);
                } else {
                    comboBoxMartesIni.setDisable(true);
                    comboBoxMartesFin.setDisable(true);
                }
            }
        });

        checkBoxMiercoles.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxMiercoles.setSelected(!oldValue);
                if (checkBoxMiercoles.isSelected()) {
                    comboBoxMiercolesIni.setDisable(false);
                    comboBoxMiercolesFin.setDisable(false);
                } else {
                    comboBoxMiercolesIni.setDisable(true);
                    comboBoxMiercolesFin.setDisable(true);
                }
            }
        });

        checkBoxJueves.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxJueves.setSelected(!oldValue);
                if (checkBoxJueves.isSelected()) {
                    comboBoxJuevesIni.setDisable(false);
                    comboBoxJuevesFin.setDisable(false);
                } else {
                    comboBoxJuevesIni.setDisable(true);
                    comboBoxJuevesFin.setDisable(true);
                }
            }
        });

        checkBoxViernes.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxViernes.setSelected(!oldValue);
                if (checkBoxViernes.isSelected()) {
                    comboBoxViernesIni.setDisable(false);
                    comboBoxViernesFin.setDisable(false);
                } else {
                    comboBoxViernesIni.setDisable(true);
                    comboBoxViernesFin.setDisable(true);
                }
            }
        });

        checkBoxSabado.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxSabado.setSelected(!oldValue);
                if (checkBoxSabado.isSelected()) {
                    comboBoxSabadoIni.setDisable(false);
                    comboBoxSabadoFin.setDisable(false);
                } else {
                    comboBoxSabadoIni.setDisable(true);
                    comboBoxSabadoFin.setDisable(true);
                }
            }
        });

        checkBoxDomingo.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxDomingo.setSelected(!oldValue);
                if (checkBoxDomingo.isSelected()) {
                    comboBoxDomingoIni.setDisable(false);
                    comboBoxDomingoFin.setDisable(false);
                } else {
                    comboBoxDomingoIni.setDisable(true);
                    comboBoxDomingoFin.setDisable(true);
                }
            }
        });

    }

    public void addHours() {
        ArrayList<String> listaHoras = new ArrayList<>();

        listaHoras.addAll(Arrays.asList("00:00-00:30", "00:30-01:00", "01:00-01:30", "01:30-02:00", "02:00-02:30", "02:30-03:00", "03:00-03:30", "03:30-04:00", "04:00-04:30", "04:30-05:00", "05:00-05:30", "05:30-06:00", "06:00-06:30", "06:30-07:00",
                "07:00-07:30", "07:30-08:00", "08:00-08:30", "08:30-09:00", "09:00-09:30", "09:30-10:00", "10:00-10:30", "10:30-11:00", "11:00-11:30", "11:30-12:00", "12:00-12:30", "12:30-13:00", "13:00-13:30", "13:30-14:00", "14:00-14:30", "14:30-15:00", "15:00-15:30", "15:30-16:00", "16:00-16:30", "16:30-17:00",
                "17:00-17:30", "17:30-18:00", "18:00-18:30", "18:30-19:00", "19:00-19:30", "19:30-20:00", "20:00-20:30", "20:30-21:00", "21:00-21:30", "21:30-22:00", "22:00-22:30", "22:30-23:00", "23:00-23:30", "23:30-00:00"));
        ArrayList<String> listaHorasLunes = new ArrayList<>();
        ArrayList<String> listaHorasMartes = new ArrayList<>();
        ArrayList<String> listaHorasMiercoles = new ArrayList<>();
        ArrayList<String> listaHorasJueves = new ArrayList<>();
        ArrayList<String> listaHorasViernes = new ArrayList<>();
        ArrayList<String> listaHorasSabado = new ArrayList<>();
        ArrayList<String> listaHorasDomingo = new ArrayList<>();
        if (grupos.size() > 0) {
            for (Grupo group : grupos) {
                System.out.println(group.getDias());
                String fecha = date.format(DateTimeFormatter.ISO_DATE);
                String[] fecha2 = fecha.split("-");
                String[] fechaGrupo = group.getFecha_fin().split("-");

                if (Integer.parseInt(fecha2[0] + fecha2[1] + fecha2[2]) < Integer.parseInt(fechaGrupo[0] + fechaGrupo[1] + fechaGrupo[2])) {
                    System.out.println("paso1");
                    String[] dias = group.getDias().split("/");
                    String[] horas = group.getHoras().split("/");
                    for (int k = 0; k < dias.length; k++) {
                        //ArrayList<String> HorasValidas = new ArrayList<>();
                        ArrayList<String> horasPorDia = new ArrayList<>();
                        for (int i = 0; i < horas.length; i++) {

                            String[] horasSIniFin = horas[i].split("-");

                            horasPorDia.add(convertirHora(horasSIniFin[0]) + "-" + convertirHora(horasSIniFin[1]));
                        }
                        if (dias[k].equals("1")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] h = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(h[1]);
                                //System.out.println("con " + h[1]);
                                String convert = convertirHoraInversa(h[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && h[0].equals(horasSeparadas[0])) || h[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasLunes.add(listaHoras.get(m));
                                }
                            }
                        }
                        if (dias[k].equals("2")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] h = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(h[1]);
                                //System.out.println("con " + h[1]);
                                String convert = convertirHoraInversa(h[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && h[0].equals(horasSeparadas[0])) || h[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasMartes.add(listaHoras.get(m));
                                }
                            }
                        }
                        if (dias[k].equals("3")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] h = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(h[1]);
                                //System.out.println("con " + h[1]);
                                String convert = convertirHoraInversa(h[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && h[0].equals(horasSeparadas[0])) || h[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasMiercoles.add(listaHoras.get(m));
                                }
                            }
                        }
                        if (dias[k].equals("4")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] viernes = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(viernes[1]);
                                //System.out.println("con " + viernes[1]);
                                String convert = convertirHoraInversa(viernes[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && viernes[0].equals(horasSeparadas[0])) || viernes[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasJueves.add(listaHoras.get(m));
                                }
                            }
                        }
                        if (dias[k].equals("5")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] viernes = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(viernes[1]);
                                //System.out.println("con " + viernes[1]);
                                String convert = convertirHoraInversa(viernes[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && viernes[0].equals(horasSeparadas[0])) || viernes[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasViernes.add(listaHoras.get(m));
                                }
                            }
                        }
                        if (dias[k].equals("6")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] viernes = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(viernes[1]);
                                //System.out.println("con " + viernes[1]);
                                String convert = convertirHoraInversa(viernes[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && viernes[0].equals(horasSeparadas[0])) || viernes[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasSabado.add(listaHoras.get(m));
                                }
                            }
                        }
                        if (dias[k].equals("7")) {
                            boolean entran = true;
                            for (int m = 0; m < listaHoras.size(); m++) {
                                String[] viernes = listaHoras.get(m).split("-");
                                String convertido = convertirHoraInversa(viernes[1]);
                                //System.out.println("con " + viernes[1]);
                                String convert = convertirHoraInversa(viernes[0]);
                                String[] horasSeparadas = horasPorDia.get(k).split("-");
                                String convertido2 = convertirHoraInversa(horasSeparadas[1]);
                                //System.out.println("con2 " + horasSeparadas[1]);
                                String convert2 = convertirHoraInversa(horasSeparadas[0]);
                                if (listaHoras.get(m).equals(horasPorDia.get(k)) || ((Integer.parseInt(convertido2) > Integer.parseInt(convertido)) && viernes[0].equals(horasSeparadas[0])) || viernes[1].equals(horasSeparadas[1])) {
                                    //System.out.println("se agrega " + listaHoras.get(m));
                                    listaHorasDomingo.add(listaHoras.get(m));
                                }
                            }
                        }
                    }
                }
            }
            ArrayList<String> listaHorasViernesBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasViernes.contains(listaHoras.get(i))) {
                    listaHorasViernesBueno.add(listaHoras.get(i));
                }
            }
            ObservableList<String> options = FXCollections.observableList(listaHorasViernesBueno);
            comboBoxViernesIni.setItems(options);
            comboBoxViernesFin.setItems(options);

            ArrayList<String> listaHorasLunesBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasLunes.contains(listaHoras.get(i))) {
                    listaHorasLunesBueno.add(listaHoras.get(i));
                }
            }
            options = FXCollections.observableList(listaHorasLunesBueno);
            comboBoxLunesIni.setItems(options);
            comboBoxLunesFin.setItems(options);

            ArrayList<String> listaHorasMartesBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasMartes.contains(listaHoras.get(i))) {
                    listaHorasMartesBueno.add(listaHoras.get(i));
                }
            }
            options = FXCollections.observableList(listaHorasMartesBueno);
            comboBoxMartesIni.setItems(options);
            comboBoxMartesFin.setItems(options);

            ArrayList<String> listaHorasMiercolesBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasMiercoles.contains(listaHoras.get(i))) {
                    listaHorasMiercolesBueno.add(listaHoras.get(i));
                }
            }
            options = FXCollections.observableList(listaHorasMiercolesBueno);
            comboBoxMiercolesIni.setItems(options);
            comboBoxMiercolesFin.setItems(options);

            ArrayList<String> listaHorasJuevesBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasJueves.contains(listaHoras.get(i))) {
                    listaHorasJuevesBueno.add(listaHoras.get(i));
                }
            }
            options = FXCollections.observableList(listaHorasJuevesBueno);
            comboBoxJuevesIni.setItems(options);
            comboBoxJuevesFin.setItems(options);

            ArrayList<String> listaHorasSabadoBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasSabado.contains(listaHoras.get(i))) {
                    listaHorasSabadoBueno.add(listaHoras.get(i));
                }
            }
            options = FXCollections.observableList(listaHorasSabadoBueno);
            comboBoxSabadoIni.setItems(options);
            comboBoxSabadoFin.setItems(options);

            ArrayList<String> listaHorasDomingoBueno = new ArrayList<>();
            for (int i = 0; i < listaHoras.size(); i++) {
                if (!listaHorasDomingo.contains(listaHoras.get(i))) {
                    listaHorasDomingoBueno.add(listaHoras.get(i));
                }
            }
            options = FXCollections.observableList(listaHorasDomingoBueno);
            comboBoxDomingoIni.setItems(options);
            comboBoxDomingoFin.setItems(options);
        }

    }

    public String convertirHoraInversa(String hora) {
        String horaConvertida = "";
        String[] horaC = hora.split(":");
        horaConvertida += horaC[0] + horaC[1];
        return horaConvertida;
    }

    public String convertirHora(String horaBase) {
        String horaConvertida = "";
        horaConvertida += horaBase.charAt(0);
        horaConvertida += horaBase.charAt(1);
        horaConvertida += ":";
        if (horaBase.charAt(2) == '5') {
            horaConvertida += "3";
        } else {
            horaConvertida += "0";
        }
        horaConvertida += "0";
        return horaConvertida;
    }

    public void buscarGrupos() {
        IGrupo metodosGrupo = new Grupo();
        grupos.clear();
        grupos = metodosGrupo.buscarGrupos();

    }

    public void compararHorarios(String diasElegidos, String horasElegidas) {
        buscarGrupos();
        String[] diasE = diasElegidos.split("/");
        String[] horasE = horasElegidas.split("/");
        for (Grupo grupo : grupos) {
            String[] dias = grupo.getDias().split("/");
            String[] horas = grupo.getHoras().split("/");
            //System.out.println(grupo.getHoras());
            //System.out.println(dias[0]);
            //System.out.println(horas[0]);
            for (int diasGrupo = 0; diasGrupo < dias.length; diasGrupo++) {
                for (int diasAsig = 0; diasAsig < diasE.length; diasAsig++) {
                    for (int horasGrupo = 0; horasGrupo < horas.length; horasGrupo++) {
                        for (int horasAsig = 0; horasAsig < horasE.length; horasAsig++) {
                            if (dias[diasGrupo].equals(diasE[diasAsig]) && horas[horasGrupo].equals(horasE[horasAsig])) {
                                System.out.println("Dias repetidos");
                                System.out.println(dias[diasGrupo]);
                                System.out.println(horas[horasGrupo]);
                            }
                        }
                    }

                }

            }
        }
    }

    public String convertirDias() {
        String cadenaDias = "";
        if (checkBoxLunes.isSelected()) {
            cadenaDias += "1" + "/";
        }
        if (checkBoxMartes.isSelected()) {
            cadenaDias += "2" + "/";
        }
        if (checkBoxMiercoles.isSelected()) {
            cadenaDias += "3" + "/";
        }
        if (checkBoxJueves.isSelected()) {
            cadenaDias += "4" + "/";
        }
        if (checkBoxViernes.isSelected()) {
            cadenaDias += "5" + "/";
        }
        if (checkBoxSabado.isSelected()) {
            cadenaDias += "6" + "/";
        }
        if (checkBoxDomingo.isSelected()) {
            cadenaDias += "7" + "/";
        }
        return cadenaDias;
    }

    public String convertirHoras() {
        String cadenaHoras = "";
        if (!comboBoxLunesIni.isDisabled()) {
            String[] horai = comboBoxLunesIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxLunesFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxMartesIni.isDisabled()) {
            String[] horai = comboBoxMartesIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxMartesFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxMiercolesIni.isDisabled()) {
            String[] horai = comboBoxMiercolesIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxMiercolesFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxJuevesIni.isDisabled()) {
            String[] horai = comboBoxJuevesIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxJuevesFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxViernesIni.isDisabled()) {
            String[] horai = comboBoxViernesIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxViernesFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxSabadoIni.isDisabled()) {
            String[] horai = comboBoxSabadoIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxSabadoFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxDomingoIni.isDisabled()) {
            String[] horai = comboBoxDomingoIni.getValue().toString().split("-");
            String[] horaISeparada = horai[0].split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaf = comboBoxDomingoFin.getValue().toString().split("-");
            String[] horaFSeparada = horaf[1].split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        return cadenaHoras;
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
            tfNombre.setText(seleccion.getNombre() + " " + seleccion.getApellidos());
        }
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            System.out.println("Hay campos vacios");
        } else {
            if (datePickerFechaFin.getValue().isAfter(datePickerFechaIni.getValue())) {
                String dias = convertirDias();
                System.out.println(dias);
                String horas = convertirHoras();
                System.out.println(horas);
                //compararHorarios(dias, horas);
                IGrupo metodosGrupo = new Grupo();
                Grupo grupo = new Grupo();
                String fechaIni = datePickerFechaIni.getValue().format(DateTimeFormatter.ISO_DATE);
                String fechaFin = datePickerFechaFin.getValue().format(DateTimeFormatter.ISO_DATE);
                grupo.setFecha_inicio(fechaIni);
                grupo.setFecha_fin(fechaFin);
                grupo.setHoras(horas);
                grupo.setDias(dias);
                grupo.setTipoDeBaile(tfTipoBaile.getText());
                grupo.setCupo(Integer.valueOf(spinnerCupo.getValue().toString()));
                grupo.setIdColaborador(seleccion.getIdColaborador());
                if (tfTipoBaile.getLength() >= 2 && tfTipoBaile.getLength() <= 30) {
                if (metodosGrupo.guardarGrupo(grupo)) {
                    System.out.println("Grupo guardado");
                } else {
                    System.out.println("No se pudo");
                }
                } else {
                    System.out.println("Campos invalidos");
                }
            } else {
                System.out.println("Las fechas estan mal, porfavor ingresa una fecha correcta");
            }

        }
    }

    private boolean validarCamposVacios() {
        return tfTipoBaile.getText().trim().isEmpty() || tfNombre.getText().isEmpty() || datePickerFechaIni.getValue() == null || datePickerFechaFin.getValue() == null;
    }

    @FXML
    private void accionBuscar() {
        if (tfPalabraClave.getText().isEmpty()) {
            System.out.println("No ha ingersado ningun caracter");
        } else {
            IColaborador metodosColaborador = new Colaborador();
            lista.clear();
            lista = metodosColaborador.buscarColaborador(tfPalabraClave.getText());
            if (lista.isEmpty()) {
                System.out.println("Maestro no encontrado");
            }
            llenarTabla();
        }
    }

}
