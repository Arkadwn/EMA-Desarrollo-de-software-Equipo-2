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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarPromocionController implements Initializable {


    private List<Colaborador> lista;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private JFXTextField tfPromocion;
    @FXML
    private Spinner spinnerDescuento;
    @FXML
    private RadioButton radioButtonMensual;
    @FXML
    private RadioButton radioButtonInscripcion;
    private ToggleGroup group;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lista = new ArrayList();
        group = new ToggleGroup();
        radioButtonMensual.setToggleGroup(group);
        radioButtonInscripcion.setToggleGroup(group);
        radioButtonInscripcion.setSelected(true);

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50,5);
        spinnerDescuento.setValueFactory(valueFactory);

    }


    private boolean validarCamposVacios() {
        return tfPromocion.getText().trim().isEmpty();
    }

    @FXML
    private void accionGuardar(ActionEvent evento) {
        if (validarCamposVacios()) {
            MensajeController.mensajeAdvertencia("Hay campos vacíos o erroneos");
        } else {
            if (tfPromocion.getText().length() < 50) {
                IPromocion metodosPromocion = new Promocion();
                Promocion promo = new Promocion();
                promo.setNombrePromocion(tfPromocion.getText());
                if (radioButtonMensual.isSelected()) {
                    promo.setTipoDescuento(true);
                } else {
                    promo.setTipoDescuento(false);
                }
                promo.setPorcentajeDescuento(spinnerDescuento.getValue().toString());
                promo.setEstado("A");
                promo.setIdColaborador(Integer.parseInt(System.getProperty("idColaborador")));
                if (metodosPromocion.crearPromocion(promo)) {
                    MensajeController.mensajeInformacion("Promoción guardada");
                    limpiarCampos();
                    if(controlador!=null){
                        controlador.actualizarComboBox();
                        cerrar();
                    }
                } else {
                    MensajeController.mensajeAdvertencia("No se pudo guardar la información");
                }
            } else {
                MensajeController.mensajeInformacion("Nombre de promoción inválido");
            }

        }
    }

    @FXML
    private void Cerrar(ActionEvent event) {
        cerrar();
    }
    
    private void cerrar(){
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    FXMLRegistrarPagoDeAlumnosController controlador;
    
    public void referenciarVentanaPago(FXMLRegistrarPagoDeAlumnosController controlador){
        this.controlador = controlador;
    }
    
    public void limpiarCampos(){
        tfPromocion.setText("");
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50,5);
        spinnerDescuento.setValueFactory(valueFactory);
    }

}
