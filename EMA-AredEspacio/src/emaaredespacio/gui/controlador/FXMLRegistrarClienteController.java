/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.ICliente;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLRegistrarClienteController implements Initializable {
    @FXML
    private JFXTextField tfNombre;
    @FXML
    private JFXTextField tfApellidos;
    @FXML
    private JFXTextField tfCorreo;
    @FXML
    private JFXTextField tfTelefono;
    @FXML
    private JFXTextField tfDireccion;
    @FXML
    private ImageView imgPerfil;
    private String nombreImagen;
    private static final String PATRON_CORREO = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imagen = new Image("emaaredespacio/imagenes/perfil.jpg",300, 300, false, true, true);
        imgPerfil.setImage(imagen);
        nombreImagen = "No";
    }
    
    private boolean validarFormatoCorreo(String email) {
        Pattern patron = Pattern.compile(PATRON_CORREO);
        Matcher concordancia = patron.matcher(email);
        return concordancia.matches();
    }
    
    public boolean[] validarCampos() {
        boolean[] validaciones = new boolean[7];
        
        //Nombre
        validaciones[0] = tfNombre.getLength() >= 2;
        //Apellidos
        validaciones[1] = tfApellidos.getLength() >= 2;
        //Nombre y Apellidos
        validaciones[2] = tfNombre.getLength() + tfApellidos.getLength() <=400;
        //Correo
        validaciones[3] = validarFormatoCorreo(tfCorreo.getText());
        //Direccion
        validaciones[4] = tfDireccion.getLength() >= 2 && tfDireccion.getLength() <= 50;
        //Telefono
        validaciones[5] = tfTelefono.getLength() >= 2 && tfTelefono.getLength() <= 10;
        
        validaciones[6] = validaciones[0] && validaciones[1] && validaciones[2] && validaciones[3] && validaciones[4] && validaciones[5];
        
        return validaciones;
    }
    
    @FXML
    private void accionGuardar(ActionEvent evento){
        if(validarCamposVacios()){
            System.out.println("Hay campos vacios");
        }else{
            ICliente metodosCliente = new Cliente();
            Cliente cliente = new Cliente();
            cliente.setNombre(tfNombre.getText() + " " + tfApellidos.getText());
            cliente.setCorreoElectronico(tfCorreo.getText());
            cliente.setDireccion(tfDireccion.getText());
            cliente.setImagenPerfil(nombreImagen);
            cliente.setTelefono(tfTelefono.getText());
            
            boolean[] validacion = validarCampos();
            if(validacion[6]){
                if(metodosCliente.guardarCliente(cliente)){
                    System.out.println("Cliente guardado");
                }else{
                    System.out.println("No se pudo");
                }
            }else{
                System.out.println("Campos invalidos");
            }
        }
    }
    
    private boolean validarCamposVacios(){
        return tfNombre.getText().isEmpty() || tfApellidos.getText().isEmpty() || tfTelefono.getText().isEmpty()
                || tfDireccion.getText().isEmpty() || tfCorreo.getText().isEmpty() || nombreImagen.equals("No");
    }
    
    @FXML
    private void elegirImagen() throws IOException {
        FileChooser elegir = new FileChooser();
        FileChooser.ExtensionFilter extension = new FileChooser.ExtensionFilter("Add Files(*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extensionPNG = new FileChooser.ExtensionFilter("Add Files(*.png)", "*.png");
        FileChooser.ExtensionFilter extensionJPEG = new FileChooser.ExtensionFilter("Add Files(*.jpeg)", "*.jpeg");
        elegir.getExtensionFilters().add(extension);
        elegir.getExtensionFilters().add(extensionPNG);
        elegir.getExtensionFilters().add(extensionJPEG);
        File rutaImagen = elegir.showOpenDialog(null);
//    File rutaImagen = new File(System.getProperty("user.home") + "\\imagenesAredEspacio\\perfil.jpg");
        if (rutaImagen == null) {
            System.out.println("no es imagen");
        } else {
            Image image = null;
            String rutaNueva = System.getProperty("user.home") + "\\imagenesAredEspacio\\imagenesAlumnos";
            String rutaOringen = rutaImagen.getAbsolutePath();
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"'+rutaOringen+'"').append(" ").append('"'+rutaNueva+'"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c",comando.toString());
            builder.redirectErrorStream(true);
            Process proceso = builder.start();
            try {
                image = new Image(rutaImagen.toURI().toURL().toString(), 225, 225, false, true, true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLEditarColaboradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            imgPerfil.setImage(image);
            nombreImagen = rutaImagen.getName();
        }
    }
    
    
}
