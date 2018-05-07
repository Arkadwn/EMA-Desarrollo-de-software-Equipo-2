package emaaredespacio.utilerias;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 6/05/2018
 * @time 12:36:21 PM
 */
public class EditorDeFormatos {
    public static String crearFormatoFecha(Date fecha){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        
        return formato.format(fecha);
    }
    
    public static Date crearFecha(String cadena){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = null;
        try {
            fecha = formato.parse(cadena);
        } catch (ParseException ex) {
            Logger.getLogger(EditorDeFormatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }
    
    public static LocalDate crearLocalDate(String cadena){
        String[] partes = cadena.split("/");

        return LocalDate.of(Integer.parseInt(partes[2]), Integer.parseInt(partes[1]), Integer.parseInt(partes[0]));
    }
    
    public static String editarFecha(LocalDate fechaActual) {
        String fecha = "";

        fecha = fechaActual.getDayOfMonth() + "/" + fechaActual.getMonthValue() + "/" + fechaActual.getYear();

        return fecha;
    }
}
