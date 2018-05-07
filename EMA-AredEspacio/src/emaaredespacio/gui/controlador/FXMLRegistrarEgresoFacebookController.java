package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.EgresoFacebook;
import emaaredespacio.modelo.IColaborador;
import emaaredespacio.modelo.IEgresoFacebook;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLRegistrarEgresoFacebookController implements Initializable {

    @FXML
    private JFXTextField tfCreador;
    @FXML
    private DatePicker cFenchaFin;
    @FXML
    private DatePicker cFechaInicio;
    @FXML
    private JFXTextField tfCosto;
    @FXML
    private JFXTextField tfLink;
    @FXML
    private JFXTextArea tfDescripcion;
    @FXML
    private JFXButton btnAgregarColaborador;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAgregarColaborador.setStyle("-fx-background-image: url('emaaredespacio/imagenes/Search.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 42px 42px 42px 42px;");
        tfCreador.setText(System.getProperty("colaborador"));
    }

    private void accionRegistrarEgreso(ActionEvent evento) {
        if (validarCampos()) {
            MensajeController.mensajeAdvertencia("Hay campos vacios");
        } else {
            LocalDate fechaInicio = cFechaInicio.getValue();
            LocalDate fechaFin = cFenchaFin.getValue();

            if (fechaInicio.isBefore(fechaFin) || fechaInicio.isEqual(fechaFin)) {
                EgresoFacebook egreso = new EgresoFacebook();
                IEgresoFacebook metodos = new EgresoFacebook();

                DecimalFormat digitos = new DecimalFormat("#.00");
                Double costo = Double.parseDouble(tfCosto.getText());

                egreso.setCosto(Double.parseDouble(digitos.format(costo)));
                egreso.setDescripcion(tfDescripcion.getText().trim());
                egreso.setFechaFin(editarFecha(fechaFin));
                egreso.setFechaInicio(editarFecha(fechaInicio));
                egreso.setLink(tfLink.getText().trim());
                egreso.setCreador(tfCreador.getText().trim());

                boolean[] validaciones = metodos.validarCampos(egreso);

                if (validaciones[4]) {
                    if (metodos.registrarEgreso(egreso)) {
                        MensajeController.mensajeInformacion("El egreso ha sido guardado exitosamente");
                        limpiarCampos();
                    } else {
                        MensajeController.mensajeAdvertencia("Ha ocurrido un error al guardar el egreso");
                    }
                } else {
                    System.out.println("Campos invalidos");
                }

            } else {
                MensajeController.mensajeAdvertencia("Hay campos invalidos, cheque los datos ingresados");
            }

        }
    }

    private boolean validarCampos() {
        return tfCosto.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfLink.getText().isEmpty()
                || cFechaInicio.getValue() == null || cFenchaFin.getValue() == null || tfCreador.getText().isEmpty();
    }

    private String editarFecha(LocalDate fechaActual) {
        String fecha = "";

        fecha = fechaActual.getDayOfMonth() + "/" + fechaActual.getMonthValue() + "/" + fechaActual.getYear();

        return fecha;
    }

    @FXML
    private void soloNumeros(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);

        if (Character.isDigit(caracter) || caracter == '.') {
            if (tfCosto.getText().split("\\.").length < 2 || Character.isDigit(caracter)) {

            } else {
                evento.consume();
            }
        } else {
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoLink(KeyEvent evento) {
        if (tfLink.getText().length() > 9999) {
            evento.consume();
        }
    }
    
    @FXML
    private void restringirCampoDescripcion(KeyEvent evento) {
        if (tfDescripcion.getText().length() > 39999) {
            evento.consume();
        }
    }

    @FXML
    private void buscarColaborador() {
        List<String> contenido = new ArrayList<>();
        contenido.add(System.getProperty("colaborador"));

        IColaborador metodos = new Colaborador();
        List<Colaborador> colaboradores = metodos.buscarColaboradoresEstados("A");

        if (colaboradores != null) {
            for (Colaborador colaborador : colaboradores) {
                contenido.add(colaborador.getNombre() + " " + colaborador.getApellidos());
            }
            
            ChoiceDialog<String> dialog = new ChoiceDialog<>(System.getProperty("colaborador"), contenido);
            dialog.setTitle(null);
            dialog.setHeaderText(null);
            dialog.setContentText("Elige un colaborador:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                tfCreador.setText(result.get());
            }
        }
    }
    
    private void limpiarCampos(){
        tfCosto.setText("");
        tfCreador.setText(System.getProperty("colaborador"));
        tfDescripcion.setText("");
        tfLink.setText("");
    }

}
