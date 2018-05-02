package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Alumno;
import emaaredespacio.modelo.Grupo;
import emaaredespacio.modelo.IAlumno;
import emaaredespacio.modelo.IInscripcion;
import emaaredespacio.modelo.Inscripcion;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLAdministrarAlumnosDeGruposController implements Initializable {

    @FXML
    private TableView<Alumno> tbAlumnos;
    @FXML
    private TableColumn<Alumno, String> clNombreAlumnos;
    @FXML
    private TableView<Grupo> tbGrupos;
    @FXML
    private TableColumn<Grupo, String> clNombreGrupo;
    @FXML
    private JFXButton btnInscribir;
    @FXML
    private JFXButton btnDarDeBajaAlumno;
    @FXML
    private JFXButton btnActivarInscripcion;
    private boolean inscribir;
    @FXML
    private Label lbMensualidad;
    @FXML
    private Label lbPromocion;
    @FXML
    private Label lbTotal;
    @FXML
    private ImageView imgPerfil;
    @FXML
    private JFXTextField tfNombreGrupo;
    @FXML
    private JFXTextField tfNombreAlumno;
    @FXML
    private JFXTextField tfPrecio;
    @FXML
    private JFXTextField tfMensualidad;
    @FXML
    private JFXTextField tfTotal;
    @FXML
    private JFXComboBox<String> cbPromocion;
    @FXML
    private JFXButton btnBaja;
    private Grupo grupoSeleccionado;
    private Alumno alumnoSeleccionado;
    @FXML
    private Label lbPrecio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grupoSeleccionado = null;
        alumnoSeleccionado = null;
        inscribir = true;
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        clNombreAlumnos.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        clNombreGrupo.setCellValueFactory(new PropertyValueFactory<>("tipoDeBaile"));
        llenarTablaGrupos(new Inscripcion().buscarGruposDeColaborador(Integer.parseInt(System.getProperty("idColaborador"))));
    }

    private void llenarTablaGrupos(List<Grupo> grupos) {
        tbGrupos.getItems().clear();
        tbGrupos.setItems(FXCollections.observableArrayList(grupos));
    }

    private void llenarTablaAlumnos(List<Alumno> alumnos) {
        tbAlumnos.getItems().clear();
        tbAlumnos.setItems(FXCollections.observableArrayList(alumnos));
    }

    @FXML
    private void cargarDatosGrupo() {
        if (tbGrupos.getSelectionModel().getSelectedIndex() >= 0) {
            grupoSeleccionado = tbGrupos.getSelectionModel().getSelectedItem();
            tfNombreAlumno.setText("");
            Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 300, 300, false, true, true);
            imgPerfil.setImage(imagen);
            alumnoSeleccionado = null;
            
            if(inscribir){
                IAlumno metodos = new Alumno();
                llenarTablaAlumnos(metodos.buscarAlumnosNoInscritos(grupoSeleccionado.getIdGrupo()));
            }else{
                IInscripcion metodos = new Inscripcion();
                llenarTablaAlumnos(metodos.sacarInscripcionesDeGrupo(grupoSeleccionado.getIdGrupo()));
            }

            tfNombreGrupo.setText(grupoSeleccionado.getTipoDeBaile());
        }
    }

    @FXML
    private void cargarDatosAlumno() {
        if (tbAlumnos.getSelectionModel().getSelectedIndex() >= 0) {
            alumnoSeleccionado = tbAlumnos.getSelectionModel().getSelectedItem();

            tfNombreAlumno.setText(alumnoSeleccionado.getNombreCompleto());
            File rutaImagen = new File(System.getProperty("user.home") + "/imagenesAredEspacio/imagenesAlumnos/" + alumnoSeleccionado.getImagenPerfil());
            Image imagen = null;
            if (rutaImagen.exists()) {
                try {
                    imagen = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FXMLAdministrarAlumnosDeGruposController.class.getName()).log(Level.SEVERE, null, ex);
                }
                imgPerfil.setImage(imagen);
            } else {
                imagen = new Image("emaaredespacio/imagenes/User.jpg", 300, 300, false, true, true);
                imgPerfil.setImage(imagen);
            }
        }
    }

    @FXML
    private void accionIncripcion(ActionEvent evento) {
        if (inscribir) {
            if (alumnoSeleccionado != null && validarCampos()) {
                MensajeController.mensajeAdvertencia("Hay campos vacios o no a seleccionado un alumno");
            } else {
                IInscripcion metodos = new Inscripcion();
                Inscripcion inscripcion = new Inscripcion(grupoSeleccionado.getIdGrupo(), alumnoSeleccionado.getMatricula(), Integer.parseInt(tfMensualidad.getText()), Integer.parseInt(tfTotal.getText()), sacarFecha(new Date()));

                if (metodos.inscribirAlumno(inscripcion)) {
                    MensajeController.mensajeInformacion("Se ha inscrito el alumno");
                    vaciarCampos();
                } else {
                    MensajeController.mensajeAdvertencia("No se ha podido inscribir el alumno");
                }
            }
        } else {
            if (alumnoSeleccionado != null) {
                IInscripcion metodos = new Inscripcion();

                if (metodos.darDeBajaAlumno(grupoSeleccionado.getIdGrupo(),alumnoSeleccionado.getMatricula())) {
                    MensajeController.mensajeInformacion("Se ha dado de baja el alumno");
                } else {
                    MensajeController.mensajeAdvertencia("No se ha podido dar de baja el alumno");
                }

            } else {
                MensajeController.mensajeAdvertencia("No ha seleccionado un alumno");
            }
        }
    }

    private boolean validarCampos() {
        return tfPrecio.getText().isEmpty() || tfMensualidad.getText().isEmpty();
    }

    private String sacarFecha(Date fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(fecha);
    }

    private void vaciarCampos() {
        tfMensualidad.setText("");
        tfTotal.setText("");
        tfNombreAlumno.setText("");
        tfNombreGrupo.setText("");
        tfPrecio.setText("");
        tbAlumnos.getItems().clear();
        alumnoSeleccionado = null;
        grupoSeleccionado = null;
        Image imagen = new Image("emaaredespacio/imagenes/User.jpg", 300, 300, false, true, true);
        imgPerfil.setImage(imagen);
    }

    @FXML
    private void activarDardeBaja() {
        lbMensualidad.setVisible(false);
        lbPromocion.setVisible(false);
        lbTotal.setVisible(false);
        lbPrecio.setVisible(false);
        cbPromocion.setVisible(false);
        btnInscribir.setVisible(false);
        btnDarDeBajaAlumno.setDisable(true);
        btnBaja.setVisible(true);
        btnActivarInscripcion.setDisable(false);
        tfMensualidad.setVisible(false);
        tfPrecio.setVisible(false);
        tfTotal.setVisible(false);
        inscribir = false;
        vaciarCampos();
    }

    @FXML
    private void activarInscripcion() {
        lbMensualidad.setVisible(true);
        lbPromocion.setVisible(true);
        lbTotal.setVisible(true);
        lbPrecio.setVisible(true);
        cbPromocion.setVisible(true);
        btnInscribir.setVisible(true);
        btnDarDeBajaAlumno.setDisable(false);
        btnBaja.setVisible(false);
        btnActivarInscripcion.setDisable(true);
        tfMensualidad.setVisible(true);
        tfPrecio.setVisible(true);
        tfTotal.setVisible(true);
        inscribir = true;
        vaciarCampos();
    }

    @FXML
    private void calcularTotal(KeyEvent evento) {
        if(!tfPrecio.getText().isEmpty()){
            Integer total = Integer.parseInt(tfPrecio.getText());
            tfTotal.setText(""+total);
        }else{
            tfTotal.setText("0");
        }
    }

    @FXML
    private void restringirCaracteres(KeyEvent evento) {
        char caracter = evento.getCharacter().charAt(0);
        JFXTextField entrada = (JFXTextField) evento.getSource();

        if (entrada.getText().length() > 6 || !Character.isDigit(caracter)) {
            evento.consume();
        }
    }
}
