package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import emaaredespacio.modelo.Pago;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import emaaredespacio.modelo.IPago;
import emaaredespacio.modelo.PagoAlumno;

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
    private Pago pago;
    private FXMLAdministrarPagosAColaboradorController controlador;
    private FXMLVisualizarHistorialDePagosDeAlumnoController controladorVisualizacion;
    private FXMLVisualizarHistorialDePagoDeEspacioController controladorVisualizar;
    @FXML
    private JFXTextArea tfComentario;
    private String tipoPago;
    private PagoAlumno pagoAlumno;
    @FXML
    private Label lbComentario;
    @FXML
    private Label lbActor;
    @FXML
    private Label lbAlumno;
    private String idIngreso;
    @FXML
    private Label lbMontoPagado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnEditar.setStyle("-fx-background-image: url('emaaredespacio/imagenes/editar.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 40px 40px 40px 40px;");
        btnEntregado.setStyle("-fx-background-image: url('emaaredespacio/imagenes/aceptar.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 40px 40px 40px 40px;");
    }
    
    public void setTextLbActor(String label){
        lbActor.setText(label);
    }
    
    public void setTextLbAlumno(String label){
        lbAlumno.setText(label);
    }
    
    public void setVisibleElements(boolean bol){
        lbAlumno.setVisible(bol);
        lbNombreAlumno.setVisible(bol);
        lbComentario.setVisible(bol);
        tfComentario.setVisible(bol);
        
    }
    
    public void cargarAlumno(Pago pago, FXMLAdministrarPagosAColaboradorController controlador) {
        tipoPago = "Pago";
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
    private void guardarEntrega(ActionEvent evento) {
        switch (tipoPago) {
            case "Pago":
                IPago metodos = new Pago();
                
                if (metodos.guardarEntrega(pago.getIdPago())) {
                    controlador.quitarPago(lbFecha.getParent());
                    MensajeController.mensajeInformacion("Pago entregado");
                } else {
                    MensajeController.mensajeAdvertencia("No se ha podido guardar el cambio");
                }
                break;
            case "alumno":
                controladorVisualizacion.quitarElemento(lbComentario.getParent());
                break;
        }
        
    }
    
    @FXML
    private void mostrarEdicion(ActionEvent evento) {
        switch (tipoPago) {
            case "Pago":
                controlador.cargarPago(pago);
                break;
            case "alumno":
                controladorVisualizacion.cargarEdicion(pagoAlumno);
                break;
            case "Ingreso":
                controladorVisualizar.cargarEdicion(idIngreso,lbMontoPagado.getText());
                System.out.println("edicion" + idIngreso);
                break;
        }
    }
    
    public void cargarPagoAlumno(PagoAlumno pago, String nombreAlumno, String nombreGrupo, String nombreColaborador, FXMLVisualizarHistorialDePagosDeAlumnoController controlador) {
        this.controladorVisualizacion = controlador;
        this.pagoAlumno = pago;
        btnEntregado.setStyle("-fx-background-image: url('emaaredespacio/imagenes/ojo.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 40px 40px 40px 40px;");
        tipoPago = "alumno";
        lbFecha.setText(pago.getFechaPago());
        lbGrupo.setText(nombreGrupo);
        lbNombreAlumno.setText(nombreAlumno);
        lbNombreColaborador.setText(nombreColaborador);
        lbMonto.setText(pago.getTotal());
        tfComentario.setText(pago.getTipoPago());
        lbComentario.setText("Tipo Pago:");
    }
    
    public void cargarIngreso(String id,String nombre, String monto, String fecha, FXMLVisualizarHistorialDePagoDeEspacioController controlador){
        this.controladorVisualizar = controlador;
         btnEntregado.setStyle("-fx-background-image: url('emaaredespacio/imagenes/cerrar.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 40px 40px 40px 40px;");
        lbNombreColaborador.setText(nombre);
        lbFecha.setText(fecha);
        tipoPago="Ingreso";
        idIngreso = id;
        lbMontoPagado.setText(monto);
        lbGrupo.setText("ID ingreso: " + id);
    }
    
}
