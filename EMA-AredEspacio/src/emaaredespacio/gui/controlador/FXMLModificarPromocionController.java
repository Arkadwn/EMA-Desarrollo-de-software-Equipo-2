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
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLModificarPromocionController implements Initializable {

    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private Spinner spinnerDescuento;
    @FXML
    private JFXTextField tfNombre;

    @FXML
    private ComboBox comboBoxPromocion;

    private List<Colaborador> lista;
    private List<Promocion> listaPromociones;
    private Colaborador colaborador;
    Promocion promocionSeleccionada;
    String nombreColaborador = "eduardo";
    @FXML
    private CheckBox checkBoxEstado;
    @FXML
    private RadioButton radioButtonMensual;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton radioButtonInscripcion;

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        tfNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidos());
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        listaPromociones = new ArrayList();
        lista = new ArrayList();
        buscarColaborador();
        buscarPromociones();
        comboBoxPromocion.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (int i = 0; i < listaPromociones.size(); i++) {
                    if (listaPromociones.get(i).getNombrePromocion().equals(comboBoxPromocion.getValue())) {
                        promocionSeleccionada = listaPromociones.get(i);
                    }
                }
                SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, Integer.parseInt(promocionSeleccionada.getPorcentajeDescuento()));
                spinnerDescuento.setValueFactory(valueFactory);
                if (promocionSeleccionada.getEstado().equals("A")) {
                    checkBoxEstado.setSelected(true);
                } else {
                    checkBoxEstado.setSelected(false);
                }
                if (promocionSeleccionada.getTipoDescuento() == true) {
                    radioButtonMensual.setSelected(true);
                    System.out.println("a");
                } else {
                    System.out.println("b");
                    radioButtonInscripcion.setSelected(true);
                }
            }
        });
    }

    private boolean validarCamposVacios() {
        return comboBoxPromocion.getValue().toString() == null;
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacíos");
        } else {
            IPromocion metodosPromocion = new Promocion();
            Promocion promo = new Promocion();
            promo.setNombrePromocion(comboBoxPromocion.getValue().toString());
            if (checkBoxEstado.isSelected()) {
                promo.setEstado("A");
            } else {
                promo.setEstado("B");
            }
            promo.setPorcentajeDescuento(spinnerDescuento.getValue().toString());
            promo.setIdColaborador(colaborador.getIdColaborador());
            promo.setIdPromocion(promocionSeleccionada.getIdPromocion());
            if (radioButtonMensual.isSelected()) {
                promo.setTipoDescuento(true);
            } else {
                promo.setTipoDescuento(false);
            }
            if (metodosPromocion.modificarPromocion(promo)) {
                MensajeController.mensajeInformacion("Promoción modificada exitosamente");
            } else {
                MensajeController.mensajeAdvertencia("No se pudo guardar la promoción");
            }

        }
    }

    public boolean buscarColaborador() {
        boolean encontrado = false;
        IColaborador metodosColaborador = new Colaborador();
        lista.clear();
        lista = metodosColaborador.buscarColaborador(nombreColaborador);
        if (lista.isEmpty()) {
            MensajeController.mensajeInformacion("Colaborador no encontrado");
        } else {
            colaborador = lista.get(0);
            tfNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidos());
            encontrado = true;
        }
        return encontrado;
    }

    public void buscarPromociones() {
        IPromocion metodosPromocion = new Promocion();
        listaPromociones.clear();
        listaPromociones = metodosPromocion.buscarPromocion(colaborador.getIdColaborador());
        List<String> nombrePromociones = new ArrayList();
        for (Promocion promocion : listaPromociones) {
            nombrePromociones.add(promocion.getNombrePromocion());
        }
        ObservableList<String> options = FXCollections.observableArrayList(nombrePromociones);
        comboBoxPromocion.setItems(options);

    }

}
