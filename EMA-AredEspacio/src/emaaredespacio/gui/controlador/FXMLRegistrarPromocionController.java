/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IPromocion;
import emaaredespacio.modelo.Promocion;
import emaaredespacio.persistencia.entidad.Colaboradores;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarPromocionController implements Initializable {

    @FXML
    private JFXTextField tfNombre;
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
    private JFXTextField tfPromocion;
    @FXML
    private Spinner spinnerDescuento;
    @FXML
    private DatePicker datePickerFechaIni;
    @FXML
    private DatePicker datePickerFechaFin;
    @FXML
    private CheckBox checkBoxDescuento;
    String nombreColaborador = "eduardo";

    public Colaborador getSeleccion() {
        return seleccion;
    }

    public void setSeleccion(Colaborador seleccion) {
        this.seleccion = seleccion;
        tfNombre.setText(seleccion.getNombre() + " " + seleccion.getApellidos());
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista = new ArrayList();
        

        checkBoxDescuento.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkBoxDescuento.setSelected(!oldValue);
                if (checkBoxDescuento.isSelected()) {
                    spinnerDescuento.setDisable(false);
                } else {
                    spinnerDescuento.setDisable(true);
                }
            }
        });
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50);
        spinnerDescuento.setValueFactory(valueFactory);

    }
     public boolean buscarColaborador() {
        boolean encontrado = false;
        IColaborador metodosColaborador = new Colaborador();
        lista.clear();
        lista = metodosColaborador.buscarColaborador(nombreColaborador);
        if (lista.isEmpty()) {
            MensajeController.mensajeInformacion("Maestro no encontrado");
        } else {
            seleccion = lista.get(0);
            tfNombre.setText(seleccion.getNombre() + " " + seleccion.getApellidos());
            encontrado = true;
        }
        return encontrado;
    }


    private boolean validarCamposVacios() {
        return tfPromocion.getText().trim().isEmpty() || seleccion.getIdColaborador().equals(0) || datePickerFechaIni.getValue() == null || datePickerFechaFin.getValue() == null;
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacíos o erroneos");
        } else {
            if (datePickerFechaFin.getValue().isAfter(datePickerFechaIni.getValue())) {
                if (tfPromocion.getText().length() < 50) {
                    IPromocion metodosPromocion = new Promocion();
                    Promocion promo = new Promocion();
                    promo.setNombrePromocion(tfPromocion.getText());
                    promo.setAplicaDescuento(checkBoxDescuento.isSelected());
                    if (checkBoxDescuento.isSelected()) {
                        promo.setPorcentajeDescuento(spinnerDescuento.getValue().toString());
                    } else {
                        promo.setPorcentajeDescuento("0");
                    }
                    String fechaIni = datePickerFechaIni.getValue().format(DateTimeFormatter.ISO_DATE);
                    String fechaFin = datePickerFechaFin.getValue().format(DateTimeFormatter.ISO_DATE);
                    promo.setFechaInicio(fechaIni);
                    promo.setFechaFin(fechaFin);
                    System.out.println(seleccion.getIdColaborador());
                    promo.setIdColaborador(seleccion.getIdColaborador());
                    if (metodosPromocion.crearPromocion(promo)) {
                        MensajeController.mensajeInformacion("Promoción guardada");
                    } else {
                        MensajeController.mensajeAdvertencia("No se pudo guardar la información");
                    }
                } else {
                    MensajeController.mensajeInformacion("Nombre de promoción inválido");
                }

            } else {
                MensajeController.mensajeInformacion("La fecha fin debe ser mayor a la fecha actual");
            }
        }
    }

}
