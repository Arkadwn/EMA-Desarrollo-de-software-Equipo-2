package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import emaaredespacio.modelo.Egreso;
import emaaredespacio.modelo.Ingreso;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class FXMLGenerarReporteController implements Initializable {

    @FXML
    private LineChart<String, Number> lcGrafica;
    @FXML
    private JFXComboBox<String> cbAnio;
    @FXML
    private JFXComboBox<String> cbMes;
    @FXML
    private JFXButton btnRegresar;
    @FXML
    private JFXButton btnGenerar;

    final NumberAxis yAxis = new NumberAxis();
    @FXML
    private JFXRadioButton rdBtnEgresos;
    @FXML
    private ToggleGroup rdGroup;
    @FXML
    private JFXRadioButton rdBtnIngresos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarComboxes();
    }

    private void cargarComboxes() {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        cbMes.setItems(FXCollections.observableArrayList(meses));

        int anioActual = Calendar.getInstance().get(Calendar.YEAR);
        List<String> aniosTranscurridos = new ArrayList();
        for (int anioViejo = 2017; anioViejo < anioActual + 1; anioViejo++) {
            aniosTranscurridos.add(String.valueOf(anioViejo));
        }
        cbAnio.setItems(FXCollections.observableArrayList(aniosTranscurridos));
    }

    private void cargarEstadisticasGrafica(boolean eleccion) {
        lcGrafica.setTitle("Grafica lineal " + cbAnio.getValue() + " - " + cbMes.getValue());
        Series<String, Number> lineaXY = new Series<String, Number>();
        if (eleccion) {
            List<Ingreso> ingresos = new Ingreso().cargarIngresos();
            List<Ingreso> ingresosPrueba = new ArrayList<>();
            for (Ingreso ingreso : ingresos) {
                String fechaIngreso = ingreso.getFecha();
                String mes = fechaIngreso.split("/")[1];
                String anio = fechaIngreso.split("/")[2];

                if (Integer.parseInt(cbAnio.getValue()) == Integer.parseInt(anio) && Integer.parseInt(mes) == cbMes.getSelectionModel().getSelectedIndex() + 1) {
                    ingresosPrueba.add(ingreso);
                }
            }
            lineaXY.setName("Ingresos");
            for (int i = 1; i < 32; i++) {
                for (int j = 0; j < ingresosPrueba.size(); j++) {
                    if (ingresosPrueba.get(j).getFecha().split("/")[0].equals(Integer.toString(i))) {
                        lineaXY.getData().add(new Data<String, Number>(ingresosPrueba.get(j).getFecha().split("/")[0], ingresosPrueba.get(j).getMonto()));
                    }
                }
            }
        } else {
            List<Egreso> egresos = new Egreso().cargarEgresos();
            List<Egreso> egresosPrueba = new ArrayList<>();
            for (Egreso egreso : egresos) {
                String fechaEgreso = egreso.getFecha();
                String mes = fechaEgreso.split("/")[1];
                String anio = fechaEgreso.split("/")[2];

                if (Integer.parseInt(cbAnio.getValue()) == Integer.parseInt(anio) && Integer.parseInt(mes) == cbMes.getSelectionModel().getSelectedIndex() + 1) {
                    egresosPrueba.add(egreso);
                }

            }
            lineaXY.setName("Egresos");
            for (int i = 1; i < 32; i++) {
                for (int j = 0; j < egresosPrueba.size(); j++) {
                    if (egresosPrueba.get(j).getFecha().split("/")[0].equals(Integer.toString(i))) {
                        lineaXY.getData().add(new Data<String, Number>(egresosPrueba.get(j).getFecha().split("/")[0], egresosPrueba.get(j).getMonto()));
                    }
                }
            }
        }

        lcGrafica.getData().clear();
        lcGrafica.getData().setAll(lineaXY);
    }

    @FXML
    private void generarEstadisticas(ActionEvent event) {
        if (rdBtnIngresos.isSelected()) {
            cargarEstadisticasGrafica(true);
        } else {
            cargarEstadisticasGrafica(false);
        }

    }

}
