/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.utilerias.AlumnoXML;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.Inscripcion;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author arkadwn
 */
public class FXMLTomarAsistenciaController implements Initializable {

    @FXML
    private JFXButton btnGuardar;
    @FXML
    private JFXDatePicker itemFecha;
    @FXML
    private JFXComboBox<String> cbxGrupos;
    @FXML
    private GridPane gridLista;
    private int cupoGrupoElegido;
    private int idColaborador;
    private List<Alumno> alumnos;
    private Grupo grupoSeleccion;
    @FXML
    private JFXButton btnActualizarFechas;
    @FXML
    private Label lbAsistenciaFecha;
    private List<String> asistencias;
    private final String TXTLABELFECHA = "asistencia del día: ";
    private String fecha;
    private int diasActuales;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idColaborador = Integer.parseInt(System.getProperty("idColaborador") );
        cupoGrupoElegido = 51;
        Calendar diaActual = Calendar.getInstance();
        int diaEntero = diaActual.get(Calendar.DAY_OF_MONTH);
        int mesEntero = (diaActual.get(Calendar.MONTH) + 1);
        String mes;

        if (mesEntero < 10) {
            mes = "0" + mesEntero;
        } else {
            mes = mesEntero + "";
        }

        diasActuales = diaActual.get(Calendar.DAY_OF_YEAR);
        fecha = diaEntero + "/" + mes + "/" + diaActual.get(Calendar.YEAR);
        lbAsistenciaFecha.setText(TXTLABELFECHA + fecha);
        llenarTablaAsistencia();
        llenarComboBoxGrupos();
        itemFecha.getEditor().setText(fecha);
    }

    private void llenarTablaAsistencia() {
        ArrayList<String> estados = new ArrayList<>();
        estados.addAll(Arrays.asList("Asistio", "Falto", "Permiso", "Aviso"));
        ObservableList<String> estadosCbx = FXCollections.observableArrayList(estados);
        if (cupoGrupoElegido == 51) {

            for (int j = 1; j < cupoGrupoElegido; j++) {
                for (int i = 0; i < 2; i++) {
                    StackPane panel = new StackPane();
                    Label lb;
                    JFXComboBox<String> cbxAsistencia;
                    panel.setPrefWidth(74.1327);
                    panel.setPrefHeight(30.0);
                    if (i == 0) {
                        lb = new Label();
                        lb.setId(i + "," + j);
                        lb.setText("     ");
                        panel.getChildren().add(lb);
                        StackPane.setAlignment(lb, Pos.CENTER);
                    } else {
                        cbxAsistencia = new JFXComboBox();
                        cbxAsistencia.setId(i + "," + j);
                        cbxAsistencia.setPromptText("Tipo de asistencia:");
                        cbxAsistencia.setDisable(true);
                        panel.getChildren().add(cbxAsistencia);
                        StackPane.setAlignment(cbxAsistencia, Pos.CENTER);
                    }
                    if (j % 2 != 0) {
                        panel.setStyle("-fx-background-color: #FFF; -fx-border-color: #a2a2a2;");
                    } else {
                        panel.setStyle("-fx-background-color: #f1f1f1; -fx-border-color: #a2a2a2;");
                    }
                    gridLista.add(panel, i, j);
                }
            }
        } else if (cupoGrupoElegido != 0) {
            for (int j = 1; j < cupoGrupoElegido + 1; j++) {
                for (int i = 0; i < 2; i++) {
                    if (alumnos.size() >= j) {
                        if (i == 0) {
                            Label lb = (Label) gridLista.lookup("#" + i + "," + j);
                            lb.setText(alumnos.get(j - 1).getNombre() + " " + alumnos.get(j - 1).getApellidos());
                        } else {
                            JFXComboBox cbxAsistencia = (JFXComboBox) gridLista.lookup("#" + i + "," + j);
                            cbxAsistencia.setDisable(false);
                            cbxAsistencia.setItems(estadosCbx);
                            if (!asistencias.isEmpty()) {
                                cbxAsistencia.setValue(asistencias.get(j - 1));
                            }
                        }
                    }
                }
            }
        } else {
            for (int j = 1; j < 50; j++) {
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        Label lb = (Label) gridLista.lookup("#" + i + "," + j);
                        lb.setText("     ");
                    } else {
                        JFXComboBox cbxAsistencia = (JFXComboBox) gridLista.lookup("#" + i + "," + j);
                        cbxAsistencia.setItems(null);
                        cbxAsistencia.setDisable(true);
                    }
                }
            }
        }
    }

    private void llenarComboBoxGrupos() {
        List<Grupo> grupos = new Grupo().buscarGrupos();
        ArrayList<String> gruposColaborador = new ArrayList();
        for (Grupo grupo : grupos) {
            if (grupo.getIdColaborador() == idColaborador) {
                gruposColaborador.add(grupo.getIdGrupo() + "|" + grupo.getTipoDeBaile());
            }
        }
        ObservableList<String> gruposCbx = FXCollections.observableArrayList(gruposColaborador);
        cbxGrupos.setItems(gruposCbx);
    }

    @FXML
    private void guardarPaseLista(ActionEvent event) {
        if (verificarPaseLista()) {
            if (asistencias.isEmpty()) {
                int i = 1;
                for (int j = 1; j < cupoGrupoElegido + 1; j++) {

                    if (alumnos.size() >= j) {
                        JFXComboBox cbx = (JFXComboBox) gridLista.lookup("#" + i + "," + j);
                        if (!cbx.isDisabled()) {
                            String valor = (String) cbx.getValue();
                            asistencias.add(valor);
                        }
                    }

                }
                AlumnoXML.guardarAsistencia(asistencias, String.valueOf(idColaborador), alumnos, fecha);
            } else {
                int i = 1;
                asistencias.clear();
                for (int j = 1; j < cupoGrupoElegido + 1; j++) {

                    if (alumnos.size() >= j) {
                        JFXComboBox cbx = (JFXComboBox) gridLista.lookup("#" + i + "," + j);
                        if (!cbx.isDisabled()) {
                            String valor = (String) cbx.getValue();
                            asistencias.add(valor);
                        }
                    }

                }
                AlumnoXML.modificarAsistencia(asistencias, alumnos, fecha, String.valueOf(idColaborador));
            }
            vaciarCampos();
        } else {
            MensajeController.mensajeInformacion("Oye no terminaste de pasar lista");
        }
    }

    private boolean verificarPaseLista() {
        boolean banderaVerificacion = true;
        int i = 1;
        for (int j = 1; j < cupoGrupoElegido + 1; j++) {

            if (alumnos.size() >= j) {
                JFXComboBox cbx = (JFXComboBox) gridLista.lookup("#" + i + "," + j);
                if (!cbx.isDisabled()) {
                    String valor = (String) cbx.getValue();
                    if (!"Asistio".equals(valor) && !"Falto".equals(valor) && !"Permiso".equals(valor) && !"Aviso".equals(valor)) {
                        banderaVerificacion = false;
                        break;
                    }
                }
            }

        }
        return banderaVerificacion;
    }

    @FXML
    private void seleccionarGrupo(ActionEvent event) {
        String seleccion = cbxGrupos.getValue();
        //if (seleccion != null) {
        grupoSeleccion = new Grupo().buscarGrupoPorId(Integer.parseInt(seleccion.split("|")[0]));
        cupoGrupoElegido = grupoSeleccion.getCupo();
        actualizarFechas(null);
        //}
    }

    @FXML
    private void actualizarFechas(ActionEvent event) {
        if (!"".equals(itemFecha.getEditor().getText())) {
            fecha = itemFecha.getEditor().getText();
            
            Calendar fechaActualizada = Calendar.getInstance();

            int diaEntero = Integer.parseInt(fecha.split("/")[0]);
            int mesEntero = Integer.parseInt(fecha.split("/")[1]);

            fechaActualizada.set(Integer.parseInt(fecha.split("/")[2]), mesEntero - 1, diaEntero);
            if (diasActuales >= fechaActualizada.get(Calendar.DAY_OF_YEAR)) {
                if (grupoSeleccion != null) {
                    lbAsistenciaFecha.setText(TXTLABELFECHA + fecha);
                    
                    Object[] resultados = AlumnoXML.obtenerListaAsistenciaSegunGrupoFecha(fecha, String.valueOf(grupoSeleccion.getIdGrupo()));
                    
                    if (resultados[0] != null) {
                        int cupoTemporal = cupoGrupoElegido;
                        cupoGrupoElegido = 0;
                        llenarTablaAsistencia();
                        alumnos = (List<Alumno>) resultados[0];
                        asistencias = (List<String>) resultados[1];
                        cupoGrupoElegido = cupoTemporal;

                        llenarTablaAsistencia();
                    } else {
                        int cupoTemporal = cupoGrupoElegido;
                        cupoGrupoElegido = 0;
                        llenarTablaAsistencia();
                        alumnos = new Inscripcion().sacarInscripcionesDeGrupo(grupoSeleccion.getIdGrupo());
                        asistencias = new ArrayList<>();
                        cupoGrupoElegido = cupoTemporal;
                        llenarTablaAsistencia();
                    }
                } else {
                    MensajeController.mensajeInformacion("Debe seleccionar un grupo primero");
                }
            } else {
                MensajeController.mensajeInformacion("No se puede tomar o modificar asistencia de días futuros");
            }
        } else {
            MensajeController.mensajeInformacion("Por favor, primero escoja una fecha para actualizar");
        }
    }

    private void vaciarCampos() {
        Calendar diaActual = Calendar.getInstance();
        int diaEntero = diaActual.get(Calendar.DAY_OF_MONTH);
        int mesEntero = (diaActual.get(Calendar.MONTH) + 1);
        String mes;

        if (mesEntero < 10) {
            mes = "0" + mesEntero;
        } else {
            mes = mesEntero + "";
        }

        fecha = diaEntero + "/" + mes + "/" + diaActual.get(Calendar.YEAR);
        lbAsistenciaFecha.setText(TXTLABELFECHA + fecha);
        itemFecha.getEditor().setText(fecha);
        actualizarFechas(null);
        //llenarComboBoxGrupos();
    }
}
