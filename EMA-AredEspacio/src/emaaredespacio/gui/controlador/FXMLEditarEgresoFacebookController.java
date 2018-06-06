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
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLEditarEgresoFacebookController implements Initializable {

    @FXML
    private DatePicker cFechaInicio;
    @FXML
    private DatePicker cFechaFin;
    @FXML
    private JFXTextField tfCreador;
    @FXML
    private JFXTextField tfCosto;
    @FXML
    private JFXTextArea tfDescripcion;
    @FXML
    private JFXTextField tfLink;
    @FXML
    private JFXTextField tfBusqueda;
    @FXML
    private CheckBox checkEstado;
    @FXML
    private TableColumn<EgresoFacebook, Boolean> clEstado;
    @FXML
    private TableColumn<EgresoFacebook, String> clCreador;
    @FXML
    private TableColumn<EgresoFacebook, String> clLink;
    @FXML
    private TableView<EgresoFacebook> tbEgresos;
    private List<EgresoFacebook> lista;
    private EgresoFacebook seleccion;
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
                + "-fx-background-position: center center; -fx-background-repeat: stretch; -fx-background-size: 14px 14px 14px 14px;");
        clCreador.setCellValueFactory(new PropertyValueFactory<>("creador"));
        clLink.setCellValueFactory(new PropertyValueFactory<>("link"));
        clEstado.setCellValueFactory(new PropertyValueFactory<>("activa"));
        clEstado.setCellValueFactory(cell -> {
            EgresoFacebook p = cell.getValue();
            return new ReadOnlyBooleanWrapper(p.getActiva());
        });
        clEstado.setCellFactory(CheckBoxTableCell.forTableColumn(clEstado));

        IEgresoFacebook metodos = new EgresoFacebook();

        lista = new ArrayList();

        lista = metodos.buscarEgresosDeFacebook("", true);

        tbEgresos.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void quitarSeleccion() {
        tbEgresos.getSelectionModel().clearSelection();
    }

    @FXML
    private void accionBuscar(ActionEvent evento) {
        if (tfBusqueda.getText().isEmpty()) {
            MensajeController.mensajeAdvertencia("Ingrese el nombre del creador de la publicidad");
        } else {
            IEgresoFacebook metodos = new EgresoFacebook();
            lista.clear();
            lista = metodos.buscarEgresosDeFacebook(tfCreador.getText(), !checkEstado.isSelected());
            cargarResultadosDeBusqueda();
        }
    }

    private void cargarResultadosDeBusqueda() {
        tbEgresos.getItems().clear();
        tbEgresos.setItems(FXCollections.observableArrayList(lista));
    }

    @FXML
    private void accionEditarEgreso(ActionEvent event) {
        if (seleccion != null) {
            if (validarCampos()) {
                MensajeController.mensajeAdvertencia("Hay campos vacios");
            } else {
                LocalDate fechaInicio = cFechaInicio.getValue();
                LocalDate fechaFin = cFechaFin.getValue();

                if (fechaInicio.isBefore(fechaFin) || fechaInicio.isEqual(fechaFin)) {
                    EgresoFacebook egreso = new EgresoFacebook();
                    IEgresoFacebook metodos = new EgresoFacebook();

                    DecimalFormat digitos = new DecimalFormat("#.00");
                    Double costo = Double.parseDouble(tfCosto.getText());

                    egreso.setIdEgresoFacebook(seleccion.getIdEgresoFacebook());
                    egreso.setCosto(Double.parseDouble(digitos.format(costo)));
                    egreso.setDescripcion(tfDescripcion.getText().trim());
                    egreso.setFechaFin(editarFecha(fechaFin));
                    egreso.setFechaInicio(editarFecha(fechaInicio));
                    egreso.setLink(tfLink.getText().trim());
                    egreso.setCreador(tfCreador.getText().trim());

                    boolean[] validaciones = metodos.validarCampos(egreso);

                    if (validaciones[4]) {
                        if (metodos.editarEgresoFacebook(egreso)) {
                            MensajeController.mensajeInformacion("Cambios guardados");
                            limpiarCampos();
                        } else {
                            MensajeController.mensajeAdvertencia("No se han podido guardar los cambios");
                        }
                    } else {
                        MensajeController.mensajeAdvertencia("Hay campos invalidos, cheque los datos ingresados");
                    }

                } else {
                    MensajeController.mensajeAdvertencia("La fecha inicio debe de ser antes que la fecha fin");
                }
            }
        } else {
            MensajeController.mensajeInformacion("No ha seleccionado un egreso");
        }
    }

    @FXML
    private void cargarDatosDeEgreso() {
        if (tbEgresos.getSelectionModel().getSelectedIndex() >= 0) {
            seleccion = tbEgresos.getSelectionModel().getSelectedItem();

            cFechaInicio.setValue(crearFecha(seleccion.getFechaInicio()));
            cFechaFin.setValue(crearFecha(seleccion.getFechaFin()));
            tfDescripcion.setText(seleccion.getDescripcion());
            tfCosto.setText(seleccion.getCosto().toString());
            tfLink.setText(seleccion.getLink());
            tfCreador.setText(seleccion.getCreador());

        }
    }

    private String editarFecha(LocalDate fechaActual) {
        String fecha = "";

        fecha = fechaActual.getDayOfMonth() + "/" + fechaActual.getMonthValue() + "/" + fechaActual.getYear();

        return fecha;
    }

    private LocalDate crearFecha(String cadena) {
        String[] partes = cadena.split("/");

        return LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
    }

    private boolean validarCampos() {
        return tfCosto.getText().isEmpty() || tfDescripcion.getText().isEmpty() || tfLink.getText().isEmpty()
                || cFechaInicio.getValue() == null || cFechaFin.getValue() == null || tfCreador.getText().isEmpty();
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
    
    @FXML
    private void limpiarCampos(){
        tfCosto.setText("");
        tfCreador.setText("");
        tfDescripcion.setText("");
        tfLink.setText("");
        tbEgresos.getItems().clear();
        seleccion = null;
        lista.clear();
    }
}
