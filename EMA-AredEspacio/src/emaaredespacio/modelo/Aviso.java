package emaaredespacio.modelo;

import emaaredespacio.gui.controlador.FXMLMenuPrincipalController;
import emaaredespacio.utilerias.EditorDeFormatos;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesús Enrique Flores Nestozo
 * @date 03/06/2018
 * @time 05:43:28 PM
 */
public class Aviso implements Runnable {

    private String nombre = "";
    private String grupo = "";
    private String tipoDePago = "";
    private List<PagoAlumno> pagosVencidos = new ArrayList<>();
    private List<String> nombreDePagosVencidos = new ArrayList<>();
    private List<String> nombreGruposDePagos = new ArrayList<>();
    private List<Aviso> listaDeAvisos = new ArrayList<>();
    private Colaborador colaborador = null;
    private FXMLMenuPrincipalController menu = null;
    private boolean activo;

    public String getNombre() {
        return nombre;
    }

    public Aviso() {
        activo = false;
    }

    public void setNombre(String nombreAlumno) {
        this.nombre = nombreAlumno;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTipoDePago() {
        return tipoDePago;
    }

    public void setTipoDePago(String tipoDePago) {
        this.tipoDePago = tipoDePago;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    /**
     * Busca los pagos vencidos de los alumnos que pertenecen a algun grupo del
     * colaborador autenticado
     */
    public void buscarPagosVencidos() {
        PagoAlumno controlador = new PagoAlumno();
        pagosVencidos.clear();
        pagosVencidos = controlador.buscarPagosVencidos(colaborador);
        if (!pagosVencidos.isEmpty()) {
            buscarNombreDePagosVencidos();
        }
    }

    /**
     * Busca los nombres a los que pertenecen los pagos vencidos
     */
    public void buscarNombreDePagosVencidos() {
        IAlumno controlador = new Alumno();
        Alumno alumno = null;
        nombreDePagosVencidos.clear();
        for (int i = 0; i < pagosVencidos.size(); i++) {
            alumno = controlador.buscarAlumnoPorId(pagosVencidos.get(i).getMatricula());
            nombreDePagosVencidos.add(alumno.getNombre() + " " + alumno.getApellidos());
        }
    }

    /**
     * Busca los nombres de los grupos de los pagos vencidos
     */
    public void buscarGruposDePagosVencidos() {
        IGrupo controller = new Grupo();
        Grupo NombreGrupo = null;
        nombreGruposDePagos.clear();
        for (int i = 0; i < pagosVencidos.size(); i++) {
            NombreGrupo = controller.buscarGrupoPorId(pagosVencidos.get(i).getIdGrupo());
            nombreGruposDePagos.add(NombreGrupo.getTipoDeBaile());
        }
    }

    /**
     * Busca los pagos vencidos de maestros/colaboradores en caso de que el
     * usuario autenticado sea director
     */
    public void buscarPagosVencidosDeMaestros() {
        List<Colaborador> colaboradoresActivos = new Colaborador().buscarColaboradoresEstados("A");
        Aviso aviso;
        Ingreso ingreso;
        for (int i = 0; i < colaboradoresActivos.size(); i++) {
            ingreso = new Ingreso().buscarUltimoPagoColaborador(colaboradoresActivos.get(i).getIdColaborador());
            if (ingreso != null) {
                Date fecha = null;
                fecha = EditorDeFormatos.crearFecha(ingreso.getFecha());
                LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (date.plusMonths(1).isBefore(LocalDate.now()) || date.plusMonths(1).isEqual(LocalDate.now())) {
                    aviso = new Aviso();
                    aviso.setNombre(colaboradoresActivos.get(i).getNombreCompleto());
                    aviso.setTipoDePago("Mensualidad");
                    listaDeAvisos.add(aviso);
                }
            } else {
                aviso = new Aviso();
                aviso.setNombre(colaboradoresActivos.get(i).getNombreCompleto());
                aviso.setTipoDePago("Mensualidad");
                listaDeAvisos.add(aviso);
            }

        }
    }
    
    /**
     * Se dan de baja las rentas que ya han pasado de la base de datos
     */
    public void darDeBajaRentasVencidas() {
        List<Renta> rentasVencidas = new Renta().cargarRentas();
        IRenta rentaControlador = new Renta();
        for (Renta renta : rentasVencidas) {
            LocalDate date = renta.getFecha().getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.plusMonths(1).isBefore(LocalDate.now()) || date.plusMonths(1).isEqual(LocalDate.now())) {
                renta.setEstado(false);
                rentaControlador.guardarCambios(renta);
            }
        }
    }

    public void setVentanaPricipal(FXMLMenuPrincipalController menu) {
        this.menu = menu;
    }

    /**
     * Actualiza la lista de avisos en el menú principal
     */
    public void actualizarListaAvisos() {
        listaDeAvisos.clear();
        Aviso aviso;
        buscarPagosVencidos();
        buscarNombreDePagosVencidos();
        buscarGruposDePagosVencidos();
        for (int i = 0; i < pagosVencidos.size(); i++) {
            aviso = new Aviso();
            aviso.setNombre(nombreDePagosVencidos.get(i));
            aviso.setGrupo(nombreGruposDePagos.get(i));
            aviso.setTipoDePago(pagosVencidos.get(i).getTipoPago());
            listaDeAvisos.add(aviso);
        }

        if (colaborador.getCargo().equals(0)) {
            buscarPagosVencidosDeMaestros();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (activo) {
                break;
            }
            darDeBajaRentasVencidas();
            actualizarListaAvisos();
            menu.actualizarAvisos(listaDeAvisos);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Aviso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cerrarAvisos() {
        activo = true;
    }

}
