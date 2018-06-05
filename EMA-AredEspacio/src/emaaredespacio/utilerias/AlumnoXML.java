/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.utilerias;

import emaaredespacio.modelo.Alumno;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Clase que realiza consultas y guarda registros a los archivos XML para la 
 * obtención de datos de los alumnos.
 * @author Adrian Bustamante Zarate
 * @date 17/04/2018
 * @time 06:44:59 PM
 */
public class AlumnoXML {

    private static final String PATHUSRSISTEMA = System.getProperty("user.home");
    private static final String PATHARED = PATHUSRSISTEMA + "/aredEspacio";
    private static final String PATHUSRXML = PATHARED + "/XML";

    /**
     * Guarda la asistencia de un día, de un grupo, de alumnos en el archivo XML
     * @param asistencia listado de tipos de asistencia en forma de cadena
     * @param idGrupo ID del grupo del que se tomara la asistencia
     * @param alumnos listado de alumnos a los que se le tomo asistencia
     * @param dia cadena de caracteres con el dia en el que se tomo la asistencia
     * @return true si se pudo guardar correctamente y false si no.
     */
    public static boolean guardarAsistencia(List<String> asistencia, String idGrupo, List<Alumno> alumnos, String dia) {
        boolean bandera;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(file);

                Node node = document.selectSingleNode("//dia[@fecha='" + dia + "']");
                if (node != null) {

                    Element elementoDia = (Element) node;
                    Element elementoGrupo = elementoDia.addElement("grupo");
                    elementoGrupo.addAttribute("id", idGrupo);
                    for (int i = 0; i < alumnos.size(); i++) {
                        Alumno alumno = alumnos.get(i);
                        String string = asistencia.get(i);
                        Element elementoAlumno = elementoGrupo.addElement("alumno");
                        elementoAlumno.addAttribute("matricula", ("" + alumno.getMatricula()));

                        elementoAlumno.addElement("asistencia").setText(string);
                        elementoAlumno.addElement("matricula").setText("" + alumno.getMatricula());
                        elementoAlumno.addElement("nombre").setText(alumno.getNombre() + "-" + alumno.getApellidos());

                    }
                } else {
                    Element elementoDia = document.getRootElement().addElement("dia");
                    elementoDia.addAttribute("fecha", dia);
                    Element elementoGrupo = elementoDia.addElement("grupo");
                    elementoGrupo.addAttribute("id", idGrupo);
                    for (int i = 0; i < alumnos.size(); i++) {
                        Alumno alumno = alumnos.get(i);
                        String string = asistencia.get(i);
                        Element elementoAlumno = elementoGrupo.addElement("alumno");
                        elementoAlumno.addAttribute("matricula", ("" + alumno.getMatricula()));

                        elementoAlumno.addElement("asistencia").setText(string);
                        elementoAlumno.addElement("matricula").setText("" + alumno.getMatricula());
                        elementoAlumno.addElement("nombre").setText(alumno.getNombre() + "-" + alumno.getApellidos());

                    }
                }

                bandera = guardarDocumento(document);
            } catch (DocumentException ex) {
                System.out.println("Error en metodo guardarGrupo de GrupoXML " + ex.getMessage());
                bandera = false;
            }
        } else {
            guardarDocumento(null);
            bandera = guardarAsistencia(asistencia, idGrupo, alumnos, dia);
        }

        return bandera;
    }

    /**
     * Retorna un arreglo de objetos de dos pocisiones donde se guarda una
     * lista de alumnos y una lista de asistencia de tipo cadena
     * @param dia cadena que indica el día por el que se filtrará el grupo
     * @param idGrupo el grupo del que se desea obtener la cadena
     * @return arrego de objetos que retorna la lista de asistencia y una lista
     * de alumnos a los que se tomo asistencia.
     */
    public static Object[] obtenerListaAsistenciaSegunGrupoFecha(String dia, String idGrupo) {
        Object[] resultado = null;
        List<Alumno> alumnos = null;
        List<String> asistencia = null;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document documento = reader.read(file);

                Node node = documento.selectSingleNode("//dia[@fecha='" + dia + "']");
                if (node != null) {
                    Element elementoDia = (Element) node;
                    //List<Element> elementosGrupos = new ArrayList<>();
                    Node nodeGrupo = elementoDia.selectSingleNode("//dia[@fecha='" + dia + "']//grupo[@id='" + idGrupo + "']");
                    if (nodeGrupo != null) {
                        Element elementGrupo = (Element) nodeGrupo;
                        List<Node> nodeAlumnos = elementGrupo.content();//"//grupo[@id='" + idGrupo + "']/alumno");
                        alumnos = new ArrayList<>();
                        asistencia = new ArrayList<>();
                        for (Node nodeAlumno : nodeAlumnos) {
                            Element elementoAlumno = (Element) nodeAlumno;

                            Iterator<Element> iteratorMatricula = elementoAlumno.elementIterator("matricula");
                            Iterator<Element> iteratorAsistencia = elementoAlumno.elementIterator("asistencia");
                            Iterator<Element> iteratorNombre = elementoAlumno.elementIterator("nombre");

                            Element elementAsistencia = (Element) iteratorAsistencia.next();
                            Element elementMatricula = (Element) iteratorMatricula.next();
                            Element elementNombre = (Element) iteratorNombre.next();

                            Alumno alumno = new Alumno();
                            String nombre = elementNombre.getText();
                            alumno.setMatricula(Integer.parseInt(elementMatricula.getText()));
                            alumno.setNombre(nombre.split("-")[0]);
                            alumno.setApellidos(nombre.split("-")[1]);
                            alumnos.add(alumno);
                            asistencia.add(elementAsistencia.getText());
                        }
                    }
                    resultado = new Object[2];
                    resultado[0] = alumnos;
                    resultado[1] = asistencia;
                } else {
                    resultado = new Object[2];
                    resultado[0] = alumnos;
                    resultado[1] = asistencia;
                }
            } catch (DocumentException ex) {
                System.out.println("Error en el metodo obtenerListaAsistenciaSegunGrupoFecha de AlumnoXML: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Error al obtener Lista de Asistencia Segun Grupo y Fecha: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            guardarDocumento(null);
            resultado = obtenerListaAsistenciaSegunGrupoFecha(dia, idGrupo);
        }

        return resultado;
    }

    private static boolean guardarDocumento(Document documento) {
        boolean bandera;
        if (documento != null) {
            try {
                File directorio = new File(PATHUSRXML);
                File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
                if (file.exists()) {
                    FileWriter fileWiter = new FileWriter(file);

                    XMLWriter writer = new XMLWriter(fileWiter);
                    writer.write(documento);
                    writer.close();
                    bandera = true;
                } else {
                    guardarDocumento(null);
                    guardarDocumento(documento);
                    bandera = true;
                }
            } catch (IOException ex) {
                System.out.println("Error al guardar: " + ex.getMessage());
                bandera = false;
            }
        } else {
            FileWriter fileWiter = null;
            try {
                File directorio = new File(PATHUSRXML);
                if (!directorio.exists()) {
                    new File(PATHARED).mkdir();
                    directorio.mkdir();
                }
                File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
                fileWiter = new FileWriter(file);
                Document documentNuevo = DocumentHelper.createDocument();
                Element root = documentNuevo.addElement("listasAsistencia");

                XMLWriter writer = new XMLWriter(fileWiter);
                writer.write(documentNuevo);
                writer.close();

            } catch (IOException ex) {
                System.out.println("Error al crear archivo plantilla XML recursivo");
            } finally {
                try {
                    fileWiter.close();
                } catch (IOException ex) {
                    System.out.println("Error al crear archivo plantilla XML recursivo, cerrar el writer");
                }
            }
            bandera = false;
        }
        return bandera;
    }

    /**
     * Borra la asistencia de un día de un grupo
     * @param dia indica el día en que se guardo la asistencia
     * @param idGrupo ID del grupo del que se tomo asistencia
     * @return true si se borro correctamente la asistencia, false si no.
     */
    public static boolean borrarAsistencia(String dia, String idGrupo) {
        boolean resultado;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document documento = reader.read(file);
                Node nodeDia = documento.selectSingleNode("//dia[@fecha='" + dia + "']");
                Node nodeGrupo = nodeDia.selectSingleNode("//grupo[@id='" + idGrupo + "']");
                Element elementoDia = (Element) nodeDia;
                elementoDia.remove((Element) nodeGrupo);
                guardarDocumento(documento);
                resultado = true;
            } catch (Exception ex) {
                System.out.println("Error en eliminar grupos GrupoXML: " + ex.getMessage());
                resultado = false;
            }
        } else {
            guardarDocumento(null);
            resultado = borrarAsistencia(dia, idGrupo);
        }

        return resultado;
    }

    /**
     * Modifica una asistencia registrada
     * @param asistencia listado de tipos de asistencia en forma de cadena 
     * @param alumnos listado de alumnos a los que se le tomo asistencia
     * @param dia fecha en la que se registra la asistencia
     * @param idGrupo ID del grupo del que se guardará la toma de asistencia
     * @return true si se modifico correctamente la asistencia, false si no.
     */
    public static boolean modificarAsistencia(List<String> asistencia, List<Alumno> alumnos, String dia, String idGrupo) {
        boolean resultado;

        resultado = borrarAsistencia(dia, idGrupo);
        if (resultado) {
            resultado = guardarAsistencia(asistencia, idGrupo, alumnos, dia);
        } else {
            resultado = false;
        }

        return resultado;
    }
}
