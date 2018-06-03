package emaaredespacio;

import emaaredespacio.gui.controlador.FXMLIniciarSesionController;
import emaaredespacio.gui.controlador.FXMLMenuPrincipalController;
import emaaredespacio.modelo.Colaborador;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Adrián Bustamante Zarate
 */
public class EMAAredEspacio extends Application {
    
    private Stage ventana;

    public Stage getVentana() {
        return ventana;
    }

    public void setVentana(Stage ventana) {
        this.ventana = ventana;
    }
    
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLIniciarSesion.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("emaaredespacio/imagenes/icono.png"));
        
        FXMLIniciarSesionController contrlador = cargador.getController();
        contrlador.setMain(this);
        
        stage.setScene(scene);
        stage.show();
        this.ventana = stage;
    }
    
    public void desplegarInicioDeSesion() throws IOException{
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLIniciarSesion.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);
        
        FXMLIniciarSesionController contrlador = cargador.getController();
        contrlador.setMain(this);
        
        ventana.setScene(scene);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void desplegarMenuPrincipal(Colaborador colaborador) throws IOException{
        FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLMenuPrincipal.fxml"));
        Parent root = (Parent) cargador.load();
        Scene scene = new Scene(root);
        
        FXMLMenuPrincipalController controlador = cargador.getController();
        controlador.setColaborador(colaborador);
        controlador.setMain(this);
        
        ventana.setScene(scene);
        
    }
}
