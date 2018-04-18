package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import emaaredespacio.modelo.Colaborador;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Miguel Leonardo Jimenez Jimenez
 */
public class FXMLMenuPrincipalController implements Initializable {

    private boolean menuDesplegado;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnInicio;
    @FXML
    private AnchorPane barraMenu;
    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private Label labelNombreSesion;
    private Colaborador colaborador;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        menuDesplegado = false;
        panelPrincipal.setStyle("-fx-background-image: url('emaaredespacio/imagenes/fondo.jpg');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch;");
        btnInicio.setStyle("-fx-background-image: url('emaaredespacio/imagenes/inicio.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch; "
                + "-fx-background-size: 30px 30px 30px 30px;");
        btnSalir.setStyle("-fx-background-image: url('emaaredespacio/imagenes/salir.png');"
                + "-fx-background-position: center center; -fx-background-repeat: stretch;"
                + " -fx-background-size: 30px 30px 30px 30px;");
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        labelNombreSesion.setText(this.colaborador.getNombre()+" "+this.colaborador.getApellidos());
    }
    
    @FXML
    private void mostrarMenu(ActionEvent evento){
        menuDesplegado = !menuDesplegado;
        barraMenu.setVisible(menuDesplegado);
    }
    
    @FXML
    private void ocultarMenu(){
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void desplegarVentanaAdministrarColaboradores(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLRegistrarColaborador.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void limpiar(ActionEvent evento){
        panelPrincipal.getChildren().clear();
    }
    
    @FXML
    public void desplegarVentana(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLEditarColaborador.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void desplegarAdminstrarAlumnos(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLRegistrarAlumno.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void desplegarEditarAlumno(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLEditarAlumno.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void desplegarAdministrarRentas(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLAdministrarRentas.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void desplegarRegistrarEgresoFacebook(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLRegistrarEgresoFacebook.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
    
    @FXML
    private void desplegarEditarEgresoFacebook(ActionEvent evento) throws IOException{
        panelPrincipal.getChildren().clear();
        Parent fxml = FXMLLoader.load(getClass().getResource("/emaaredespacio/gui/vista/FXMLEditarEgresoFacebook.fxml"));
        panelPrincipal.getChildren().addAll(fxml.getChildrenUnmodifiable());
        barraMenu.setVisible(false);
        menuDesplegado = false;
    }
}
