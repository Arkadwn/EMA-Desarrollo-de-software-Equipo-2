/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.HorarioGlobal;
import emaaredespacio.modelo.Renta;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
    private List<HorarioGlobal> horario;
    private List<Grupo> grupos;
    private List<Renta> rentas;
    private boolean cambioFecha;
    private Calendar fechaConsultada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cambioFecha = false;
        fechaConsultada = Calendar.getInstance();
        fechaConsultada.setTime(new Date(fechaConsultada.get(Calendar.YEAR) - 1900, fechaConsultada.get(Calendar.MONTH), fechaConsultada.get(Calendar.DAY_OF_MONTH)));
        llenarTablaHorarioGlobal();
        actualizarTablaSegunPicker(Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR), true);
        cargarRentasHorario();
    }

    private void cargarRentasHorario() {

        List<Renta> lista = new Renta().cargarRentas();

        for (int j = 1; j < 8; j++) {
            for (int i = 0; i < lista.size(); i++) {
                Calendar calendarioAux = lista.get(i).getFecha();
                int diaRenta = calendarioAux.get(Calendar.DAY_OF_YEAR);
                int diaFechaConsult = obtenerFechaSegunDiaSemana(j, fechaConsultada).get(Calendar.DAY_OF_YEAR);
                //System.out.println(diaFechaConsult+"-"+diaRenta);
                if (diaRenta == diaFechaConsult) {
                    //System.out.println("pase "+lista.get(i).getId());
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
                JFXButton btn;
                Label lb;
                if (i != 0) {
                    btn = new JFXButton();
                    switch (i) {
                        case 1:
                            btn.setText(hora.getLunes());
                            break;
                        case 2:
                            btn.setText(hora.getMartes());
                            break;
                        case 3:
                            btn.setText(hora.getMiercoles());
                            break;
                        case 4:
                            btn.setText(hora.getJueves());
                            break;
                        case 5:
                            btn.setText(hora.getViernes());
                            break;
                        case 6:
                            btn.setText(hora.getSabado());
                            break;
                        case 7:
                            btn.setText(hora.getDomingo());
                            break;
                    }
                    if (!"".equals(btn.getText())) {
                        btn.setPrefWidth(74.1327);
                        btn.setPrefHeight(10.0);
                        panel.getChildren().add(btn);
                        StackPane.setAlignment(btn, Pos.CENTER);
                    } else {
                        lb = new Label();
                        lb.setText("");
                        lb.setPrefWidth(74.1327);
                        panel.getChildren().add(lb);
                        StackPane.setAlignment(lb, Pos.CENTER);
                    }
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
        cambioFecha = true;
        int dia, mes, annio;
        String fecha = itemDate.getEditor().getText();
        dia = Integer.parseInt(fecha.split("/")[0]);
        mes = Integer.parseInt(fecha.split("/")[1]);
        annio = Integer.parseInt(fecha.split("/")[2]);

        actualizarTablaSegunPicker(dia, mes, annio, false);
        fechaConsultada.setTime(new Date(Integer.parseInt(itemDate.getEditor().getText().split("/")[2]) - 1900, Integer.parseInt(itemDate.getEditor().getText().split("/")[1]) - 1, Integer.parseInt(itemDate.getEditor().getText().split("/")[0])));
        llenarTablaHorarioGlobal();
        cargarRentasHorario();
        
    }

    private void colocarRenta(int j, Renta get) {
        //System.out.println(get.getHoraInicio());
        float ini = get.getHoraInicio();
        float fin = get.getHoraFin();
        String textoBtn = "R" + get.getId();
        ini = ((ini / 100) * 2) + 1;
        fin = (fin / 100) * 2;

        for (int i = (int) ini; i < fin + 1; i++) {
            JFXButton button = new JFXButton(textoBtn);
            button.setPrefWidth(74.35);
            button.setPrefHeight(10.0);
            gridHorario.add(button, j, i);
        }
    }
    
    @FXML
    private void accionBotonGeneral(ActionEvent event){
        
    }

}
