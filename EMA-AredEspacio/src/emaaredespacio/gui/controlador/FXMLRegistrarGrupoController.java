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
import emaaredespacio.modelo.GrupoXML;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IGrupo;
import emaaredespacio.modelo.Renta;
import emaaredespacio.persistencia.entidad.Colaboradores;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.control.DatePicker;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

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

    ArrayList<String> horasInvalidasLunes = new ArrayList<>();
    ArrayList<String> horasInvalidasMartes = new ArrayList<>();
    ArrayList<String> horasInvalidasMiercoles = new ArrayList<>();
    ArrayList<String> horasInvalidasJueves = new ArrayList<>();
    ArrayList<String> horasInvalidasViernes = new ArrayList<>();
    ArrayList<String> horasInvalidasSabado = new ArrayList<>();
    ArrayList<String> horasInvalidasDomingo = new ArrayList<>();
    ArrayList<String> horasInvalidasLunesFin = new ArrayList<>();
    ArrayList<String> horasInvalidasMartesFin = new ArrayList<>();
    ArrayList<String> horasInvalidasMiercolesFin = new ArrayList<>();
    ArrayList<String> horasInvalidasJuevesFin = new ArrayList<>();
    ArrayList<String> horasInvalidasViernesFin = new ArrayList<>();
    ArrayList<String> horasInvalidasSabadoFin = new ArrayList<>();
    ArrayList<String> horasInvalidasDomingoFin = new ArrayList<>();

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

        datePickerFechaIni.setOnAction(event -> {
            datePickerFechaFin.setDisable(false);
            if (datePickerFechaFin.getValue() != null) {
                addHours();
            }
        });

        datePickerFechaFin.setOnAction(event -> {
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
        Renta renta = new Renta();

        List<Renta> rentas = new ArrayList<>();
        rentas = renta.cargarRentas();
        ArrayList<String> listaHorasIni = new ArrayList<>();
        listaHorasIni.addAll(Arrays.asList("00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30",
                "07:00", "07:30", "08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"));
        //por cada día se obtienen los grupos
        for (int i = 1; i < 8; i++) {
            List<Grupo> gruposDia = GrupoXML.obtenerGruposDiaSemana(i);

            //llenado de las horas de un dia
            ArrayList<String> listaHoraIExcluyen = new ArrayList<>();
            ArrayList<String> listaHoraFExcluyen = new ArrayList<>();
            for (Grupo grupoSolo : gruposDia) {
                String[] fechaDeGrupo = grupoSolo.getFecha_fin().split("-");
                String[] fechaElegida = datePickerFechaIni.getValue().format(DateTimeFormatter.ISO_DATE).split("-");
                String fecha1 = fechaDeGrupo[0] + fechaDeGrupo[1] + fechaDeGrupo[2];
                String fecha2 = fechaElegida[0] + fechaElegida[1] + fechaElegida[2];
                Grupo grupoBD = new Grupo();
                grupoBD = grupoBD.buscarGrupoPorId(grupoSolo.getIdGrupo());
                if (Integer.parseInt(fecha1) > Integer.parseInt(fecha2) && grupoBD.getEstado().equals("A")) {
                    int horaI = Integer.parseInt(grupoSolo.getHora_inicio());
                    int horaF = Integer.parseInt(grupoSolo.getHora_fin());
                    for (int j = 0; j < listaHorasIni.size(); j++) {
                        if (Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) == horaI) {
                            listaHoraIExcluyen.add(listaHorasIni.get(j));
                        }
                        if (Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) > horaI && horaF > Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j)))) {

                            listaHoraIExcluyen.add(listaHorasIni.get(j));
                        }
                    }
                    for (int j = 0; j < listaHorasIni.size(); j++) {
                        if (Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) == horaF) {
                            listaHoraFExcluyen.add(listaHorasIni.get(j));
                        }
                        if (horaI < Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) && Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) < horaF) {
                            listaHoraFExcluyen.add(listaHorasIni.get(j));
                        }
                    }
                }

            }
            for (Renta rentaTempo : rentas) {
                Calendar fecha = rentaTempo.getFecha();
                String año = String.valueOf(fecha.get(Calendar.YEAR));
                String mes = String.valueOf(fecha.get(Calendar.MONTH) + 1);
                if (mes.length() == 1) {
                    mes = "0" + mes;
                }
                String dia = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));
                if (dia.length() == 1) {
                    dia = "0" + dia;
                }
                String fechaRenta = año + mes + dia;
                String[] fechaElegida2 = datePickerFechaIni.getValue().format(DateTimeFormatter.ISO_DATE).split("-");
                String fecha1 = fechaElegida2[0] + fechaElegida2[1] + fechaElegida2[2];
                String[] fechaElegida = datePickerFechaFin.getValue().format(DateTimeFormatter.ISO_DATE).split("-");
                String fecha2 = fechaElegida[0] + fechaElegida[1] + fechaElegida[2];
                System.out.println(fechaRenta);
                System.out.println(fecha1);
                System.out.println(fecha.get(Calendar.DAY_OF_WEEK));
                if (Integer.parseInt(fechaRenta) > Integer.parseInt(fecha1) && Integer.parseInt(fechaRenta) < Integer.parseInt(fecha2) && fecha.get(Calendar.DAY_OF_WEEK) - 1 == i) {
                    System.out.println("agregar las horas");
                    int horaI = rentaTempo.getHoraInicio();
                    int horaF = rentaTempo.getHoraFin();
                    for (int j = 0; j < listaHorasIni.size(); j++) {
                        if (Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) == horaI) {
                            listaHoraIExcluyen.add(listaHorasIni.get(j));
                        }
                        if (Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) > horaI && horaF > Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j)))) {

                            listaHoraIExcluyen.add(listaHorasIni.get(j));
                        }
                    }
                    for (int j = 0; j < listaHorasIni.size(); j++) {
                        if (Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) == horaF) {
                            listaHoraFExcluyen.add(listaHorasIni.get(j));
                        }
                        if (horaI < Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) && Integer.parseInt(convertirHoraInversa(listaHorasIni.get(j))) < horaF) {
                            listaHoraFExcluyen.add(listaHorasIni.get(j));
                        }
                    }
                }
            }

            System.out.println(listaHoraIExcluyen);
            System.out.println("......");
            System.out.println(listaHoraFExcluyen);
            ArrayList<String> listaHorasNuevoI = new ArrayList<>();
            ArrayList<String> listaHorasNuevoF = new ArrayList<>();
            for (int k = 0; k < listaHorasIni.size(); k++) {
                if (!listaHoraIExcluyen.contains(listaHorasIni.get(k))) {
                    listaHorasNuevoI.add(listaHorasIni.get(k));
                }
            }
            for (int k = 0; k < listaHorasIni.size(); k++) {
                if (!listaHoraFExcluyen.contains(listaHorasIni.get(k))) {
                    listaHorasNuevoF.add(listaHorasIni.get(k));
                }
            }
            llenarComboBoxes(listaHorasNuevoI, listaHorasNuevoF, listaHoraIExcluyen, listaHoraFExcluyen, i);

        }

    }

    public void llenarComboBoxes(ArrayList<String> listaHorasI, ArrayList<String> listaHorasF, ArrayList<String> listaHorasIExcliye, ArrayList<String> listaHorasFExcluyen, int dia) {
        ObservableList<String> options = FXCollections.observableArrayList(listaHorasI);
        ObservableList<String> options2 = FXCollections.observableArrayList(listaHorasF);
        switch (dia) {
            case 1:
                comboBoxLunesIni.setItems(options);
                comboBoxLunesFin.setItems(options2);
                horasInvalidasLunes = listaHorasIExcliye;
                horasInvalidasLunesFin = listaHorasFExcluyen;
                break;
            case 2:
                comboBoxMartesIni.setItems(options);
                comboBoxMartesFin.setItems(options2);
                horasInvalidasMartes = listaHorasIExcliye;
                horasInvalidasMartesFin = listaHorasFExcluyen;
                break;
            case 3:
                comboBoxMiercolesIni.setItems(options);
                comboBoxMiercolesFin.setItems(options2);
                horasInvalidasMiercoles = listaHorasIExcliye;
                horasInvalidasMiercolesFin = listaHorasFExcluyen;
                break;
            case 4:
                comboBoxJuevesIni.setItems(options);
                comboBoxJuevesFin.setItems(options2);
                horasInvalidasJueves = listaHorasIExcliye;
                horasInvalidasJuevesFin = listaHorasFExcluyen;
                break;
            case 5:
                comboBoxViernesIni.setItems(options);
                comboBoxViernesFin.setItems(options2);
                horasInvalidasViernes = listaHorasIExcliye;
                horasInvalidasViernesFin = listaHorasFExcluyen;
                break;
            case 6:
                comboBoxSabadoIni.setItems(options);
                comboBoxSabadoFin.setItems(options2);
                horasInvalidasSabado = listaHorasIExcliye;
                horasInvalidasSabadoFin = listaHorasFExcluyen;
                break;
            case 7:
                comboBoxDomingoIni.setItems(options);
                comboBoxDomingoFin.setItems(options2);
                horasInvalidasDomingo = listaHorasIExcliye;
                horasInvalidasDomingoFin = listaHorasFExcluyen;
                break;
        }

    }

    public String convertirHoraInversa(String hora) {
        String horaConvertida = "";
        String[] horaC = hora.split(":");
        if (horaC[1].equals("30")) {
            horaConvertida += horaC[0] + "50";
        } else {
            horaConvertida += horaC[0] + horaC[1];
        }

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
            String[] horaISeparada = comboBoxLunesIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxLunesFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxMartesIni.isDisabled()) {
            String[] horaISeparada = comboBoxMartesIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxMartesFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxMiercolesIni.isDisabled()) {
            String[] horaISeparada = comboBoxMiercolesIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxMiercolesFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxJuevesIni.isDisabled()) {
            String[] horaISeparada = comboBoxJuevesIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxJuevesFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxViernesIni.isDisabled()) {
            String[] horaISeparada = comboBoxViernesIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxViernesFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxSabadoIni.isDisabled()) {
            String[] horaISeparada = comboBoxSabadoIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxSabadoFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        if (!comboBoxDomingoIni.isDisabled()) {
            String[] horaISeparada = comboBoxDomingoIni.getValue().toString().split(":");
            if (horaISeparada[1].equals("30")) {
                horaISeparada[1] = "50";
            }
            String[] horaFSeparada = comboBoxDomingoFin.getValue().toString().split(":");
            if (horaFSeparada[1].equals("30")) {
                horaFSeparada[1] = "50";
            }
            cadenaHoras += horaISeparada[0] + horaISeparada[1] + "-" + horaFSeparada[0] + horaFSeparada[1] + "/";
        }
        return cadenaHoras;
    }

    private int verificarHorasDePorMedio() {
        int horasDePorMedio = 100;
        int horaInicial = 0;
        int horaFinal = 0;
        if (!comboBoxLunesIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxLunesIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxLunesFin.getValue().toString()));
            for (String hora : horasInvalidasLunes) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 1;
                }
            }
        }
        if (!comboBoxMartesIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxMartesIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxMartesFin.getValue().toString()));
            for (String hora : horasInvalidasMartes) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 2;
                }
            }
        }
        if (!comboBoxMiercolesIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxMiercolesIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxMiercolesFin.getValue().toString()));
            for (String hora : horasInvalidasMiercoles) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 3;
                }
            }
        }
        if (!comboBoxJuevesIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxJuevesIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxJuevesFin.getValue().toString()));
            for (String hora : horasInvalidasJueves) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 4;
                }
            }
        }
        if (!comboBoxViernesIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxViernesIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxViernesFin.getValue().toString()));
            for (String hora : horasInvalidasViernes) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 5;
                }
            }
        }
        if (!comboBoxSabadoIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxSabadoIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxSabadoFin.getValue().toString()));
            for (String hora : horasInvalidasSabado) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 6;
                }
            }
        }
        if (!comboBoxDomingoIni.isDisable()) {
            horaInicial = Integer.parseInt(convertirHoraInversa(comboBoxDomingoIni.getValue().toString()));
            horaFinal = Integer.parseInt(convertirHoraInversa(comboBoxDomingoFin.getValue().toString()));
            for (String hora : horasInvalidasDomingo) {
                if (Integer.parseInt(convertirHoraInversa(hora)) > horaInicial && Integer.parseInt(convertirHoraInversa(hora)) < horaFinal) {
                    horasDePorMedio = 7;
                }
            }
        }
        return horasDePorMedio;
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
            checkBoxLunes.setDisable(true);
            checkBoxMartes.setDisable(true);
            checkBoxMiercoles.setDisable(true);
            checkBoxJueves.setDisable(true);
            checkBoxViernes.setDisable(true);
            checkBoxSabado.setDisable(true);
            checkBoxDomingo.setDisable(true);
            checkBoxLunes.setSelected(false);
            checkBoxMartes.setSelected(false);
            checkBoxMiercoles.setSelected(false);
            checkBoxJueves.setSelected(false);
            checkBoxViernes.setSelected(false);
            checkBoxSabado.setSelected(false);
            checkBoxDomingo.setSelected(false);
        }
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacíos");
        } else {
            if (datePickerFechaFin.getValue().isAfter(datePickerFechaIni.getValue())) {

                if (tfTipoBaile.getLength() >= 2 && tfTipoBaile.getLength() <= 30) {
                    if (validarComboBoxes()) {
                        if (verificarHorasDePorMedio() == 100) {
                            String dias = convertirDias();
                            System.out.println(dias);
                            String horas = convertirHoras();
                            System.out.println(horas);
                            Grupo grupo = new Grupo();
                            String fechaIni = datePickerFechaIni.getValue().format(DateTimeFormatter.ISO_DATE);
                            String fechaFin = datePickerFechaFin.getValue().format(DateTimeFormatter.ISO_DATE);
                            grupo.setHoras(horas);
                            grupo.setDias(dias);
                            grupo.setTipoDeBaile(tfTipoBaile.getText());
                            grupo.setCupo(Integer.valueOf(spinnerCupo.getValue().toString()));
                            grupo.setIdColaborador(seleccion.getIdColaborador());
                            grupo.setFecha_inicio(fechaIni);
                            grupo.setFecha_fin(fechaFin);
                            IGrupo metodos = new Grupo();
                            if (metodos.guardarGrupo(grupo)) {
                                MensajeController.mensajeInformacion("El grupo se ha guardado correctamente");
                                Grupo grup = new Grupo();
                                List<Grupo> grupos = grup.buscarGrupos();
                                grup = grupos.get(grupos.size()-1);
                                grupo.setIdGrupo(grup.getIdGrupo());
                                GrupoXML.guardarGrupo(grupo);
                            } else {
                                MensajeController.mensajeAdvertencia("No se pudo registrar el grupo");
                            }
                        }else{
                            String mensaje="Hay horas ocupadas entre las horas elejidas para el día ";
                            switch(verificarHorasDePorMedio()){
                                case 1:
                                    mensaje+="lunes";
                                    break;
                                case 2:
                                    mensaje+="martes";
                                    break;
                                case 3:
                                    mensaje+="miercoles";
                                    break;
                                case 4:
                                    mensaje+="jueves";
                                    break;
                                case 5:
                                    mensaje+="viernes";
                                    break;
                                case 6:
                                    mensaje+="sabado";
                                    break;
                                case 7:
                                    mensaje+="domingo";
                                    break;
                            }
                            MensajeController.mensajeAdvertencia(mensaje);
                        }

                    } else {
                        MensajeController.mensajeAdvertencia("Hay horas estan mal o no se han elegido, porfavor ingrese valores correctos");
                    }

                } else {
                    MensajeController.mensajeAdvertencia("Campos inválidos");
                }
            } else {
                MensajeController.mensajeAdvertencia("Las fechas estan mal, porfavor ingresa una fecha correcta");
            }

        }
    }

    private boolean validarCamposVacios() {
        return tfTipoBaile.getText().trim().isEmpty() || tfNombre.getText().isEmpty() || datePickerFechaIni.getValue() == null || datePickerFechaFin.getValue() == null;
    }

    private boolean validarComboBoxes() {
        boolean validos = true;
        if (!comboBoxLunesIni.isDisable()) {
            if (comboBoxLunesIni.getValue() == null && comboBoxLunesFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxLunesFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxLunesIni.getValue().toString()))) {
                    validos = false;
                }
            }
        }
        if (!comboBoxMartesIni.isDisable()) {
            if (comboBoxMartesIni.getValue() == null && comboBoxMartesFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxMartesFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxMartesIni.getValue().toString()))) {
                    validos = false;
                }
            }
        }
        if (!comboBoxMiercolesIni.isDisable()) {
            if (comboBoxMiercolesIni.getValue() == null && comboBoxMiercolesFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxMiercolesFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxMiercolesIni.getValue().toString()))) {
                    validos = false;
                }
            }
        }
        if (!comboBoxJuevesIni.isDisable()) {
            if (comboBoxJuevesIni.getValue() == null && comboBoxJuevesFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxJuevesFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxJuevesIni.getValue().toString()))) {

                }
            }
        }
        if (!comboBoxViernesIni.isDisable()) {
            if (comboBoxViernesIni.getValue() == null && comboBoxViernesFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxViernesFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxViernesIni.getValue().toString()))) {
                    validos = false;
                }
            }
        }
        if (!comboBoxSabadoIni.isDisable()) {
            if (comboBoxSabadoIni.getValue() == null && comboBoxSabadoFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxSabadoFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxSabadoIni.getValue().toString()))) {
                    validos = false;
                }
            }
        }
        if (!comboBoxDomingoIni.isDisable()) {
            if (comboBoxDomingoIni.getValue() == null && comboBoxDomingoFin.getValue() == null) {
                validos = false;
            } else {
                if (Integer.parseInt(convertirHoraInversa(comboBoxDomingoFin.getValue().toString())) < Integer.parseInt(convertirHoraInversa(comboBoxDomingoIni.getValue().toString()))) {
                    validos = false;
                }
            }
        }

        return validos;
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
