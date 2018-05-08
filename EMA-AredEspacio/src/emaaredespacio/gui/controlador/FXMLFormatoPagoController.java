package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import emaaredespacio.modelo.IPagoAColaborador;
import emaaredespacio.modelo.PagoAColaborador;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLFormatoPagoController implements Initializable {

    @FXML
    private JFXButton btnEditar;
    @FXML
    private Label lbMonto;
    @FXML
    private Label lbGrupo;
    @FXML
    private Label lbFecha;
    @FXML
    private Label lbNombreAlumno;
    @FXML
    private Label lbNombreColaborador;
    @FXML
    private JFXButton btnEntregado;
    private PagoAColaborador pago;
    private FXMLAdministrarPagosAColaboradorController controlador;
    @FXML
    private JFXTextArea tfComentario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEditar.setStyle("-fx-background-image: url('emaaredespacio/imagenes/editar.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 40px 40px 40px 40px;");
        btnEntregado.setStyle("-fx-background-image: url('emaaredespacio/imagenes/aceptar.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 40px 40px 40px 40px;");
    }

    public void cargarAlumno(PagoAColaborador pago, FXMLAdministrarPagosAColaboradorController controlador) {
        this.pago = pago;
        this.controlador = controlador;
        if (pago != null) {
            lbFecha.setText(pago.getFecha());
            lbGrupo.setText(pago.getGrupo());
            lbNombreAlumno.setText(pago.getNombreAlumno());
            lbNombreColaborador.setText(pago.getNombreColaborador());
            lbMonto.setText("" + pago.getMonto());
            tfComentario.setText(pago.getComentario());
        }
    }

    @FXML
    private void guardarEntrega(ActionEvent event) {
        IPagoAColaborador metodos = new PagoAColaborador();

        if (metodos.guardarEntrega(pago.getIdPago())) {
            controlador.quitarPago(lbFecha.getParent());
            MensajeController.mensajeInformacion("Pago entregado");
        } else {
            MensajeController.mensajeAdvertencia("No se ha podido guardar el cambio");
        }
    }

}
