package emaaredespacio.modelo;

import emaaredespacio.persistencia.controladores.EgresosJpaController;
import emaaredespacio.persistencia.entidad.Egresos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Adrian Bustamante Zarate
 */
public class Egreso implements IEgreso {

    private String descripcion;
    private Integer idEgreso;
    private String fecha;
    private Double monto;

    public Egreso(String descripcion, String fecha, Double monto) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.monto = monto;
    }

    public Egreso() {}

    public Egreso(Integer idEgreso, String descripcion, String fecha, Double monto) {
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.monto = monto;
        this.idEgreso = idEgreso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public Integer getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    
    
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    @Override
    public boolean guardarNuevoEgreso(Egreso egresoNuevo) {
        boolean guardado = false;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            EgresosJpaController controlador = new EgresosJpaController(entityManagerFactory);

            Egresos egresosNuevo = new Egresos();
            egresosNuevo.setDescripcion(egresoNuevo.getDescripcion());
            egresosNuevo.setFecha(egresoNuevo.getFecha());
            egresosNuevo.setMonto(egresoNuevo.getMonto());
            
            if(egresosNuevo.getMonto() != null && egresosNuevo.getFecha() != null && egresosNuevo.getDescripcion() != null){
                controlador.create(egresosNuevo);
                guardado = true;
            }

            
        } catch (Exception ex) {
            System.out.println("Fallo el guardar nuevo egresos: "+ex.getMessage());
            ex.printStackTrace();
        }
        return guardado;
    }

    @Override
    public boolean guardarCambios(Egreso egreso) {
        boolean guardado = false;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            EgresosJpaController controlador = new EgresosJpaController(entityManagerFactory);
            
            Egresos egresosNuevo = new Egresos();
            egresosNuevo.setDescripcion(egreso.getDescripcion());
            egresosNuevo.setFecha(egreso.getFecha());
            egresosNuevo.setMonto(egreso.getMonto());
            egresosNuevo.setIdEgreso(egreso.getIdEgreso());
            
            if(egresosNuevo.getMonto() != null && egresosNuevo.getFecha() != null && egresosNuevo.getDescripcion() != null){
                controlador.edit(egresosNuevo);
                guardado = true;
            }
        } catch (Exception ex) {
            System.out.println("Fallo el guardar cambios de egresos: "+ex.getMessage());
            ex.printStackTrace();
        }
        return guardado;
    }

    @Override
    public ArrayList<Egreso> cargarEgresos() {
        ArrayList<Egreso> resultado = new ArrayList();
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("EMA-AredEspacioPU", null);
            EgresosJpaController controlador = new EgresosJpaController(entityManagerFactory);
            
            resultado = convertirListaDOM(controlador.findEgresosEntities());
        }catch(Exception ex){
            System.out.println("Error al cargar todos los egresos: "+ex.getMessage());
            ex.printStackTrace();
        }
        return resultado;
    }

    private ArrayList<Egreso> convertirListaDOM(List<Egresos> listaJPA) {
        ArrayList<Egreso> resultado = new ArrayList();
        for (Egresos egresos : listaJPA) {
            Egreso egreso = new Egreso(egresos.getIdEgreso(), egresos.getDescripcion(), egresos.getFecha(), egresos.getMonto());
            resultado.add(egreso);
        }
        
        return resultado;
    }

}
