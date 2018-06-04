package emaaredespacio.utilerias;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Miguel Leonardo Jimenez Jimenez
 * @date 27/05/2018
 * @time 09:24:50 AM
 */
public class Imagen {

    public final static String COLABORADOR = "colaborador";
    public final static String CLIENTE = "cliente";
    public final static String ALUMNO = "alumno";

    public static void moverImagen(File rutaImagen, String nombreUsuario, String tipo) {
        if (rutaImagen != null) {
            String ruta = "";
            File directorio = null;
            switch (tipo) {
                case COLABORADOR:
                    directorio = new File(System.getProperty("user.home")+"/aredEspacio/imagenesColaboradores");
                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }
                    ruta = System.getProperty("user.home") + "/aredEspacio/imagenesColaboradores/" + nombreUsuario + ".jpg";
                    break;
                case ALUMNO:
                    directorio = new File(System.getProperty("user.home")+"/aredEspacio/imagenesAlumnos");
                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }
                    ruta = System.getProperty("user.home") + "/aredEspacio/imagenesAlumnos/" + nombreUsuario + ".jpg";
                    break;
                case CLIENTE:
                    directorio = new File(System.getProperty("user.home")+"/aredEspacio/imangesClientes");
                    if (!directorio.exists()) {
                        directorio.mkdirs();
                    }
                    ruta = System.getProperty("user.home") + "/aredEspacio/imagenesClientes/" + nombreUsuario + ".jpg";
                    break;
            }

            FileInputStream entrada = null;
            FileOutputStream escritor = null;
            try {
                entrada = new FileInputStream(rutaImagen);
                int tamaño = (int) rutaImagen.length();
                byte[] bits = new byte[tamaño];
                entrada.read(bits, 0, tamaño);
                escritor = new FileOutputStream(ruta);
                escritor.write(bits, 0, tamaño);
                escritor.flush();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (entrada != null) {
                    try {
                        entrada.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (escritor != null) {
                    try {
                        escritor.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Imagen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
