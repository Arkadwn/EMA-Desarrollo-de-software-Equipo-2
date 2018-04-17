package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.EgresoFacebook;
import emaaredespacio.modelo.IEgresoFacebook;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private LocalDate fechaActual;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fechaActual = LocalDate.now();
    }

    @FXML
    private void accionRegistrarEgreso(ActionEvent evento) {
        if (validarCampos()) {
            System.out.println("Hay campos vacios");
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
                        System.out.println("Egreso registrado");
                    } else {
                        System.out.println("No se pudo guardar el egreso");
                    }
                } else {
                    System.out.println("Campos invalidos");
                }

            } else {
                System.out.println("La fecha inicio debe de ser antes que la fecha fin");
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

}
