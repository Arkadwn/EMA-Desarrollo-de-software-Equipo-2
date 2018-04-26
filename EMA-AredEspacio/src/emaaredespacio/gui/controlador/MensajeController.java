package emaaredespacio.gui.controlador;


import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 18/04/2018
 * @time 12:24:17 AM
 */
public class MensajeController {
    
    /**
     * Muestra una ventana de advertencia.
     * 
     * @param mensaje Mensaje a mostrar.
     */
    public static void mensajeAdvertencia(String mensaje){
        Alert ventana = new Alert(Alert.AlertType.WARNING);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ButtonType boton = new ButtonType("Aceptar", ButtonData.OK_DONE);
        ventana.getButtonTypes().setAll(boton);
        ventana.showAndWait();
    }
    
    /**
     * Muestra una ventana de información.
     * 
     * @param mensaje Mensaje a mostrar.
     */
    public static void mensajeInformacion(String mensaje){
        Alert ventana = new Alert(Alert.AlertType.INFORMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ButtonType boton = new ButtonType("Aceptar", ButtonData.OK_DONE);
        ventana.getButtonTypes().setAll(boton);
        ventana.showAndWait();
    }
    
    /**
     * Muestra una ventana de desición.
     * 
     * @param mensaje Mensaje a mostrar.
     * @return La desición tomada por el usuario.
     */
    public static boolean mensajeDesicion(String mensaje){
        boolean validacion = false;
        
        Alert ventana = new Alert(Alert.AlertType.CONFIRMATION);
        ventana.setTitle(null);
        ventana.setHeaderText(null);
        ventana.setContentText(mensaje);
        ButtonType botonSi = new ButtonType("Si", ButtonData.YES);
        ButtonType botonNo = new ButtonType("No", ButtonData.NO);
        ventana.getButtonTypes().setAll(botonNo,botonSi);
        
        Optional<ButtonType> resultado = ventana.showAndWait();
        
        if(resultado.isPresent()){
            validacion = resultado.get() == botonSi;
        }
        
        
        return validacion;
    }
}