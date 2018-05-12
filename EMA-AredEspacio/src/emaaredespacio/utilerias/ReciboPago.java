package emaaredespacio.utilerias;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import emaaredespacio.modelo.Cliente;
import emaaredespacio.modelo.Colaborador;
import emaaredespacio.modelo.Renta;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 *
 * @author Adrian Bustamante Zarate
 */
public class ReciboPago {

    private static final String PATHUSRSISTEMA = System.getProperty("user.home");
    private static final String PATHPDFPAGORENTA = PATHUSRSISTEMA + "/PDF/PagosRenta/";
    private static final String PATHPDFPAGOCOLABORADOR = PATHUSRSISTEMA + "/PDF/PagosColaborador/";

    public static boolean generarReciboPagoRenta(Renta renta, Cliente cliente, String fecha) {
        boolean banderaGenerar = false;
        FileOutputStream archivo = null;

        File directorio = new File(PATHPDFPAGORENTA);
        String[] clienteArray = cliente.getNombre().split(" ");
        String clienteFormat = "";
        for (String string : clienteArray) {
            clienteFormat += string + "-";
        }

        clienteFormat = clienteFormat.substring(0, clienteFormat.length() - 1);

        File file = new File(directorio + "/Renta_ID-" + renta.getId() + "-" + clienteFormat + "-ID-" + cliente.getId() + ".pdf");

        if (!directorio.exists()) {
            directorio.mkdir();
        }
        try {

            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Paragraph parrafo = new Paragraph("Ared Espacio", new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC, BaseColor.LIGHT_GRAY));
            doc.add(parrafo);

            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
            parrafo = new Paragraph("== RECIBO DE PAGO DE RENTA ==", negrita);
            parrafo.setAlignment(Element.ALIGN_LEFT);
            doc.add(parrafo);

            parrafo = new Paragraph("El presente recibo es por concepto de el pago de renta del "
                    + "espacio en determinadas caracteristicas que se presentan "
                    + "a continuación:\n", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            parrafo.add("ID Renta: " + renta.getId());
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add("Fecha de renta: " + renta.getFecha().get(Calendar.DAY_OF_MONTH) + "/"
                    + (renta.getFecha().get(Calendar.MONTH) + 1) + "/" + renta.getFecha().get(Calendar.YEAR) + "\n");
            parrafo.add("Horario de la renta: de las " + mapeoHoras(renta.getHoraInicio()) + " horas, a las " + mapeoHoras(renta.getHoraFin()) + " horas\n");
            parrafo.add("El responsable directo de la renta es: " + cliente.getNombre() + " de ID: " + cliente.getId() + "\n");
            parrafo.add("El monto correspondiente a esta renta es de: $" + renta.getMonto());
            parrafo.add(" el cual será pagado en una sola exhibición el día: " + fecha);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.setAlignment(Chunk.ALIGN_JUSTIFIED);
            doc.add(parrafo);
            //Firmas o sellos
            parrafo = new Paragraph(10);
            parrafo.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE, BaseColor.BLACK));
            parrafo.add("Firma director o sello de la institucion");
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(cliente.getNombre());
            doc.add(parrafo);

            doc.close();
            archivo.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error al crear el archivo: " + ex.getMessage());
        } catch (DocumentException ex) {
            System.out.println("Error al instanciar el documento con el archivo: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al abrir o cerrar el archivo");
        }

        return banderaGenerar;
    }

    public static String mapeoHoras(int horaMilitar) {
        String[] mapa = {"--:--", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30",
            "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00",
            "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30",
            "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00",
            "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30",
            "22:00", "22:30", "23:00", "23:30", "24:00"};
        return mapa[horaMilitar / 50];
    }

    public static boolean generarReciboPagoColaborador(Colaborador colaborador, String fecha) {
        boolean banderaGeneracion = false;
        FileOutputStream archivo = null;

        File directorio = new File(PATHPDFPAGOCOLABORADOR);
        String[] colaboradorArray = colaborador.getNombre().split(" ");
        String colaboradorFormat = "";
        for (String string : colaboradorArray) {
            colaboradorFormat += string + "-";
        }
        colaboradorArray = colaborador.getApellidos().split(" ");
        for (String string : colaboradorArray) {
            colaboradorFormat += string + "-";
        }
        colaboradorFormat = colaboradorFormat.substring(0, colaboradorFormat.length() - 1);
        String fechaFormat = fecha.split("/")[0] + ":" + fecha.split("/")[1] + ":" + fecha.split("/")[2];
        File file = new File(directorio + "/Colaborador_ID-" + colaborador.getIdColaborador() + "-" + colaboradorFormat + "-" + fechaFormat + ".pdf");

        if (!directorio.exists()) {
            directorio.mkdir();
        }
        try {

            archivo = new FileOutputStream(file);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            Paragraph parrafo = new Paragraph("Ared Espacio", new Font(Font.FontFamily.TIMES_ROMAN, 6, Font.ITALIC, BaseColor.LIGHT_GRAY));
            doc.add(parrafo);

            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLACK);
            parrafo = new Paragraph("== RECIBO DE PAGO DE COLABORADOR ==", negrita);
            parrafo.setAlignment(Element.ALIGN_LEFT);
            doc.add(parrafo);

            parrafo = new Paragraph("El presente recibo es por concepto de el pago de la "+ colaborador.getTipoPago() +" correspondiente "
                    + "según los acuerdos para dar clases en AredEspacio del siguiente colaborador:\n", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            parrafo.add("ID Colaborador: " + colaborador.getIdColaborador());
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add("Tipo de pago: " + colaborador.getTipoPago() + "\n");
            parrafo.add("Correo del colaborador: "+colaborador.getCorreo()+"\n");
            parrafo.add("Numero de telefono:"+colaborador.getTelefono()+"\n");
            parrafo.add("Direccion actual: "+colaborador.getDireccion());
            parrafo.add("Colaborador de nombre: " + colaborador.getNombre() + " "+colaborador.getApellidos()+"\n");
            parrafo.add("El monto correspondiente a este pago es de: $" + colaborador.getMontoAPagar());
            parrafo.add(" el cual será pagado en una sola exhibición el día: " + fecha);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.add(Chunk.NEWLINE);
            parrafo.setAlignment(Chunk.ALIGN_JUSTIFIED);
            doc.add(parrafo);
            //Firmas o sellos
            parrafo = new Paragraph(10);
            parrafo.setFont(new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE, BaseColor.BLACK));
            parrafo.add("Firma director o sello de la institucion");
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(Chunk.SPACETABBING);
            parrafo.add(colaborador.getNombre()+" "+colaborador.getApellidos());
            doc.add(parrafo);

            doc.close();
            archivo.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error al crear el archivo: " + ex.getMessage());
        } catch (DocumentException ex) {
            System.out.println("Error al instanciar el documento con el archivo: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al abrir o cerrar el archivo");
        }

        return banderaGeneracion;
    }
}
