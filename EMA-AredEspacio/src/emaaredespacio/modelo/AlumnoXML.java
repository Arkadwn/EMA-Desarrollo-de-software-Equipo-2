/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author arkadwn
 */
public class AlumnoXML {

    private static final String PATHUSRSISTEMA = System.getProperty("user.home");
    private static final String PATHUSRXML = PATHUSRSISTEMA + "/XML";

    public static boolean guardarAsistencia(List<String> asistencia, String idGrupo, List<Alumno> alumnos, String dia) {
        boolean bandera;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(file);

                if (document.matches("/dia[@fecha='" + dia + "']")) {
                    Node node = document.selectSingleNode("/dia[@fecha='" + dia + "']");
                    Element elementoDia = (Element) node;
                    Element elementoGrupo = elementoDia.addElement("grupo");
                    elementoGrupo.addAttribute("id", idGrupo);
                    for (Alumno alumno : alumnos) {
                        Element elementoAlumno = elementoGrupo.addElement("alumno");
                        //elementoAlumno.addAttribute("matricula", ("" + alumno.getMatricula()));
                        for (String string : asistencia) {
                            elementoAlumno.addElement("asistencia").setText(string);
                            elementoAlumno.addElement("matricula").setText("" + alumno.getMatricula());
                            elementoAlumno.addElement("nombre").setText(alumno.getNombre() + "-" + alumno.getApellidos());
                        }
                    }
                } else {
                    Element elementoDia = document.addElement("dia");
                    elementoDia.addAttribute("fecha", dia);
                    Element elementoGrupo = elementoDia.addElement("grupo");
                    elementoGrupo.addAttribute("id", idGrupo);
                    for (Alumno alumno : alumnos) {
                        Element elementoAlumno = elementoGrupo.addElement("alumno");
                        //elementoAlumno.addAttribute("matricula", ("" + alumno.getMatricula()));
                        for (String string : asistencia) {
                            elementoAlumno.addElement("asistencia").setText(string);
                            elementoAlumno.addElement("matricula").setText("" + alumno.getMatricula());
                            elementoAlumno.addElement("nombre").setText(alumno.getNombre() + "-" + alumno.getApellidos());
                        }
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

                Node node = documento.selectSingleNode("/dia[@fecha='" + dia + "']");
                if (node != null) {
                    Element elementoDia = (Element) node;
                    //List<Element> elementosGrupos = new ArrayList<>();
                    Node nodeGrupo = elementoDia.selectSingleNode("/grupo[@id='" + idGrupo + "']");
                    if (nodeGrupo != null) {
                        Element elementGrupo = (Element) nodeGrupo;
                        List<Node> nodeAlumnos = elementGrupo.selectNodes("/alumno");
                        alumnos = new ArrayList<>();
                        asistencia = new ArrayList<>();
                        for (Node nodeAlumno : nodeAlumnos) {
                            Element elementoAlumno = (Element) nodeAlumno;
                            Alumno alumno = new Alumno();

                            Element elementAsistencia = (Element) elementoAlumno.selectSingleNode("/asistencia");
                            Element elementMatricula = (Element) elementoAlumno.selectSingleNode("/matricula");
                            Element elementNombre = (Element) elementoAlumno.selectSingleNode("/nombre");
                            alumno.setMatricula(Integer.parseInt(elementMatricula.getText()));
                            alumno.setNombre(elementNombre.getText().split("-")[0]);
                            alumno.setApellidos(elementNombre.getText().split("-")[1]);
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
                    if (!directorio.exists()) {
                        directorio.mkdir();
                    }
                    FileWriter fileWiter = new FileWriter(file);

                    Document documentNuevo = DocumentHelper.createDocument();
                    Element root = documentNuevo.addElement("asistencias");

                    XMLWriter writer = new XMLWriter(fileWiter);

                    writer.write(documentNuevo);
                    writer.close();
                    fileWiter.close();
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
                File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
                if (!directorio.exists()) {
                    directorio.mkdir();
                }
                fileWiter = new FileWriter(file);
                Document documentNuevo = DocumentHelper.createDocument();
                Element root = documentNuevo.addElement("horario");

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

    public static boolean borrarAsistencia(String dia, String idGrupo) {
        boolean resultado;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/asistencia.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document documento = reader.read(file);
                Node nodeDia = documento.selectSingleNode("/dia[@fecha='" + dia + "']");
                Node nodeGrupo = nodeDia.selectSingleNode("/grupo[@id='" + idGrupo + "']");
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

    public static boolean modificarAsistencia(List<String> asistencia, List<Alumno> alumnos, String dia, String idGrupo) {
        boolean resultado;
        
        resultado = borrarAsistencia(dia, idGrupo);
        if(resultado)
            resultado = guardarAsistencia(asistencia, idGrupo, alumnos, dia);
        else
            resultado = false;
        
        return resultado;
    }
}
