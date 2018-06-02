/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.gui.controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Ingreso;
import emaaredespacio.modelo.Renta;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author enriq
 */
public class FXMLVisualizarHistorialDePagoDeEspacioController implements Initializable {

    @FXML
    private JFXTextField txtBusqueda;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnGuardar;
    @FXML
    private VBox vboxContenedor;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtMonto;
    private List<Ingreso> ingresos;
    private List<Colaborador> colaboradores;
    private List<Cliente> clientes;
    private Ingreso ingresoSeleccionado;
    private boolean tipoPago;
    @FXML
    private JFXRadioButton rdBtnRenta;
    @FXML
    private ToggleGroup groupTipoPago;
    @FXML
    private JFXRadioButton rdBtnColaborador;
    @FXML
    private JFXButton btnCargarTodo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ingresos = new ArrayList();
        colaboradores = new ArrayList();
        colaboradores = new Colaborador().buscarColaboradoresEstados("A");
        ingresos = new Ingreso().cargarIngresos();
        ingresoSeleccionado = null;
        cargarTodos();
    }

    @FXML
    private void buscar(ActionEvent event) {
        int tipo=0;
        if(ingresos!=null)
            ingresos.clear();
        if(groupTipoPago.getSelectedToggle().equals(rdBtnColaborador)){
            tipo=1;
        }
        ingresos = new Ingreso().buscarPagosPorNombre(txtBusqueda.getText(),tipo);
        vboxContenedor.getChildren().clear();
        cargarIngresos();
    }
    
    private void cargarTodos(){
        ingresos.clear();
        ingresos = new Ingreso().cargarIngresos();
        cargarIngresos();
    }
    
    private void cargarIngresos(){
        vboxContenedor.getChildren().clear();
        if(ingresos != null){
            for(Ingreso ingreso : ingresos){
                Parent fxml = null;
                FXMLLoader cargador = new FXMLLoader(getClass().getResource("/emaaredespacio/gui/vista/FXMLFormatoPago.fxml"));
                try {
                    fxml = cargador.load();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLVisualizarHistorialDePagoDeEspacioController.class.getName()).log(Level.SEVERE, null, ex);
                }
                FXMLFormatoPagoController controlador = cargador.getController();
                controlador.setVisibleElements(false);
                if(ingreso.getPagoColaboradorID() !=null){
                    controlador.setTextLbActor("Colaborador");
                    Colaborador colaborador = new Colaborador().buscarColaboradorSegunID(ingreso.getPagoColaboradorID());
                    controlador.cargarIngreso(ingreso.getIdIngreso().toString(), colaborador.getNombreCompleto(), ingreso.getMonto().toString(), ingreso.getFecha(), this);
                }else{
                    controlador.setTextLbActor("Cliente");
                    Renta renta = new Renta().cargarRenta(ingreso.getPagoRentaID().toString());
                    Cliente cliente = new Cliente().obtenerClienteID(renta.getCliente().getId());
                    controlador.cargarIngreso(ingreso.getIdIngreso().toString(), cliente.getNombre(), ingreso.getMonto().toString(), ingreso.getFecha(), this);
                }
                vboxContenedor.getChildren().addAll(fxml);
            }
        }
    }
    
    public void cargarEdicion(String idIngreso,String monto){
        txtId.setText(idIngreso);
        txtMonto.setText(monto);
    }

    @FXML
    private void guadar(ActionEvent event) {
        System.out.println(ingresos.get(0).getIdIngreso());
        System.out.println(txtId.getText());
        for(int i=0;i<ingresos.size();i++){
            if(ingresos.get(i).getIdIngreso().toString().equals(txtId.getText())){
                Ingreso ingreso = new Ingreso();
                ingreso.setIdIngreso(ingresos.get(i).getIdIngreso());
                ingreso.setFecha(ingresos.get(i).getFecha());
                ingreso.setMonto(Double.valueOf(txtMonto.getText()));
                ingreso.setPagoColaboradorID(ingresos.get(i).getPagoColaboradorID());
                ingreso.setPagoRentaID(ingresos.get(i).getPagoRentaID());
                ingreso.setRecibo(ingresos.get(i).getRecibo());
                if(ingreso.ModificarRegistro(ingreso)){
                    MensajeController.mensajeInformacion("El ingreso a sido modificado correctamente");
                    vaciarCampos();
                }else{
                    MensajeController.mensajeAdvertencia("No se han podido guardar los cambios");
                }
            }else{
                System.out.println("NO");
            }
        }
    }
    
    public void vaciarCampos(){
        txtId.setText("");
        txtMonto.setText("");
        cargarTodos();
    }

    @FXML
    private void cargarTodo(ActionEvent event) {
        cargarTodos();
    }
    
}
