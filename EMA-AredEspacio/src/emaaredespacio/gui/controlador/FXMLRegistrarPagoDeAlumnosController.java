/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.utilerias.Conexion;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IAlumno;
import emaaredespacio.modelo.IGrupo;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.IPagoAlumno;
import emaaredespacio.modelo.IPromocion;
import emaaredespacio.modelo.Inscripcion;
import emaaredespacio.modelo.PagoAlumno;
import emaaredespacio.modelo.Promocion;
import emaaredespacio.utilerias.ReciboDePagoAlumno;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarPagoDeAlumnosController implements Initializable {

    @FXML
    private JFXTextField tfAlumno;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXButton btnCancelar;
    @FXML
    private ComboBox comboBoxPromociones;
    @FXML
    private JFXTextField tfMonto;
    @FXML
    private JFXButton btnCrearPromocion;
    @FXML
    private TableView<Alumno> tbListaAlumnos;
    @FXML
    private TableColumn<Alumno, String> columnaNombre;
    @FXML
    private TableColumn<Alumno, String> columnaApellidos;
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private ComboBox comboBoxGrupos;
    List<Alumno> listaAlumnos;
    List<Grupo> listaGrupos;
    List<Promocion> listaPromociones;
    Colaborador colaborador;
    @FXML
    private JFXTextField tfTotal;
    @FXML
    private ComboBox comboBoxTipoPago;
    @FXML
    private JFXButton btnCalcular;
    Alumno seleccion;
    Grupo grupoSeleccionado;
    Promocion promocionSeleccionada;

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        tfNombre.setText(colaborador.getNombre() + " " + colaborador.getApellidos());
        buscarGrupos();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaAlumnos = new ArrayList<>();
        listaGrupos = new ArrayList<>();
        listaPromociones = new ArrayList<>();
        Colaborador colaborador = new Colaborador();
        colaborador.setIdColaborador(1);
        colaborador.setNombre("armando");
        colaborador.setApellidos("jimenez");
        this.setColaborador(colaborador);
        tfNombre.setText(colaborador.getNombre());
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        comboBoxGrupos.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (int i = 0; i < listaGrupos.size(); i++) {
                    if (listaGrupos.get(i).getTipoDeBaile().equals(comboBoxGrupos.getValue())) {
                        buscarAlumnos(listaGrupos.get(i));
                        grupoSeleccionado = listaGrupos.get(i);
                    }
                }
            }

        });

        tfMonto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfMonto.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        comboBoxPromociones.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for (int i = 0; i < listaPromociones.size(); i++) {
                    if (listaPromociones.get(i).getNombrePromocion().equals(comboBoxPromociones.getValue())) {

                        promocionSeleccionada = listaPromociones.get(i);
                    }
                }
            }

        });
        ObservableList<String> options = FXCollections.observableArrayList("Inscripcion", "Mensualidad");
        comboBoxTipoPago.setItems(options);
        comboBoxTipoPago.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                buscarPromociones(comboBoxTipoPago.getValue().toString());
                if (comboBoxTipoPago.getValue().toString().equals("Mensualidad")) {
                    tfMonto.setText(newValue);
                }
            }

        });

    }

    public void buscarGrupos() {
        IGrupo metodosGrupo = new Grupo();
        List<Grupo> listaTemporal = new ArrayList();
        listaTemporal.clear();
        listaTemporal = metodosGrupo.buscarGrupos();
        listaGrupos.clear();
        for (int i = 0; i < listaTemporal.size(); i++) {
            if (listaTemporal.get(i).getIdColaborador() == colaborador.getIdColaborador()) {
                listaGrupos.add(listaTemporal.get(i));
            }
        }
        List<String> nombreGrupos = new ArrayList();
        for (Grupo grupo : listaGrupos) {
            nombreGrupos.add(grupo.getTipoDeBaile());
        }
        ObservableList<String> options = FXCollections.observableArrayList(nombreGrupos);
        comboBoxGrupos.setItems(options);
    }

    public void buscarPromociones(String tipoPago) {
        IPromocion metodosPromocion = new Promocion();
        listaPromociones.clear();
        List<Promocion> listaPromocionesTemporal = metodosPromocion.buscarPromocion(colaborador.getIdColaborador());
        List<String> nombrePromociones = new ArrayList();
        for (Promocion promocion : listaPromocionesTemporal) {
            if (promocion.getTipoDescuento() && tipoPago == "Mensualidad") {
                nombrePromociones.clear();
                nombrePromociones.add(promocion.getNombrePromocion());
                listaPromociones.add(promocion);
            }
            if (promocion.getTipoDescuento() == false && tipoPago == "Inscripcion") {
                nombrePromociones.clear();
                nombrePromociones.add(promocion.getNombrePromocion());
                listaPromociones.add(promocion);
            }

        }
        ObservableList<String> options = FXCollections.observableArrayList(nombrePromociones);
        comboBoxPromociones.setItems(options);
    }

    public void buscarAlumnos(Grupo grupo) {
        IInscripcion metodosInscripcion = new Inscripcion();
        listaAlumnos.clear();
        listaAlumnos = metodosInscripcion.sacarInscripcionesDeGrupo(grupo.getIdGrupo());
        if (!listaAlumnos.isEmpty()) {
            for (int i = 0; i < listaAlumnos.size(); i++) {
                System.out.println(listaAlumnos.get(i).getNombre());
            }
            llenarTabla();
        }

    }

    public void llenarTabla() {
        tbListaAlumnos.getItems().clear();
        tbListaAlumnos.setItems(FXCollections.observableArrayList(listaAlumnos));
    }

    @FXML
    private void accionGuardar(ActionEvent event) {
        calcularTotal();
        IPagoAlumno metodosPago = new PagoAlumno();
        if (verificarCamposVacios() == false) {
            PagoAlumno pago = new PagoAlumno();
            pago.setMonto(tfMonto.getText());
            pago.setIdGrupo(grupoSeleccionado.getIdGrupo());
            pago.setMatricula(seleccion.getMatricula());
            pago.setFechaPago(sacarFecha(new Date()));
            if (comboBoxPromociones.getValue() != null) {
                pago.setPorcentajeDescuento(Integer.valueOf(promocionSeleccionada.getPorcentajeDescuento()));
            } else {
                pago.setPorcentajeDescuento(0);
            }
            if (tfTotal.getText().isEmpty()) {
                double total = calcularTotal();
                pago.setTotal(String.valueOf(total));
            } else {
                pago.setTotal(tfTotal.getText());
            }
            pago.setTipoPago(comboBoxTipoPago.getValue().toString());
            if (metodosPago.registrarPago(pago)) {
                MensajeController.mensajeInformacion("Pago de alumno registrado correctamente");
                int pagoBuscado = metodosPago.buscarUltimoPago().getIdPagoAlumno();
                ReciboDePagoAlumno.generarRecibo(pagoBuscado);
                limpiar();
            }
        } else {
            MensajeController.mensajeInformacion("Existen campos vacíos");
        }

    }

    public void limpiar() {
        tfAlumno.setText("");
        tfMonto.setText("");
        tfTotal.setText("");
        seleccion = null;
        grupoSeleccionado = null;
        promocionSeleccionada = null;
    }

    private String sacarFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(fecha);
    }

    public boolean verificarCamposVacios() {

        return (tfAlumno.getText().isEmpty() && tfMonto.getText().isEmpty() && comboBoxTipoPago.getValue() != null);
    }

    @FXML
    private void quitaSeleccion(MouseEvent event) {
        tbListaAlumnos.getSelectionModel().clearSelection();
    }

    @FXML
    private void posicion(MouseEvent event) {
        seleccion = new Alumno();
        if (tbListaAlumnos.getSelectionModel().getSelectedIndex() >= 0) {
            seleccion = tbListaAlumnos.getSelectionModel().getSelectedItem();
            tfAlumno.setText(seleccion.getNombre() + " " + seleccion.getApellidos());
        }
    }

    @FXML
    private void accionCrearPromocion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLRegistrarPromocion.fxml"));
            Parent parent = (Parent) loader.load();
            FXMLRegistrarPromocionController control = loader.getController();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            control.setSeleccion(colaborador);
        } catch (IOException ex) {
            Logger.getLogger(FXMLRegistrarPromocionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void accionCalcular(ActionEvent event) {
        calcularTotal();
    }

    public double calcularTotal() {
        double total = 0.0;
        double porcentajeDescuento = 0.0;
        if (comboBoxPromociones.getValue() != null) {
            for (Promocion promo : listaPromociones) {
                if (comboBoxPromociones.getValue().equals(promo.getNombrePromocion())) {
                    porcentajeDescuento = Double.valueOf(promo.getPorcentajeDescuento()) / 100;
                }
            }
            double descuento = Double.valueOf(tfMonto.getText()) * porcentajeDescuento;
            total = Double.valueOf(tfMonto.getText()) - descuento;
            tfTotal.setText(String.valueOf(total));
        } else {
            tfTotal.setText(tfMonto.getText());
        }
        return total;
    }

}
