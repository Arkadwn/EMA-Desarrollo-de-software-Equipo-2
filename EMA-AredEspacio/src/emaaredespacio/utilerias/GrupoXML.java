package emaaredespacio.utilerias;

import emaaredespacio.modelo.Grupo;
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
 *
 * @author Adrián Bustamante Zarate
 * @date 17/04/2018
 * @time 06:44:59 PM
 */
public class GrupoXML {

    private static final String PATHUSRSISTEMA = System.getProperty("user.home");
    private static final String PATHARED = PATHUSRSISTEMA + "/aredEspacio";
    private static final String PATHUSRXML = PATHARED + "/XML";

    public static boolean guardarGrupo(Grupo grupoGuardar, String dias, String horas, String fechaIni, String fechaFin) {
        String[] diasGrupo = dias.split("/");
        String[] horasGrupo = horas.split("/");
        boolean bandera;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/horario.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(file);

                List<Node> nodes = document.selectNodes("//dia");
                int con = 1;
                for (Node node : nodes) {
                    Element dia = (Element) node;
                    for (int i = 0; i < diasGrupo.length; i++) {
                        if (Integer.parseInt(diasGrupo[i]) == con) {
                            Element grupo = dia.addElement("grupo").addAttribute("id", "" + grupoGuardar.getIdGrupo());
                            grupo.addElement("id").addText("" + grupoGuardar.getIdGrupo());
                            String[] horasDia = horasGrupo[i].split("-");
                            if (horasDia.length > 1) {
                                grupo.addElement("horaIni").addText(horasDia[0]);
                                grupo.addElement("horaFin").addText(horasDia[1]);
                            } else {
                                grupo.addElement("horaIni").addText(horasDia[0]);
                                grupo.addElement("horaFin").addText("null");
                            }
                            grupo.addElement("fechaIni").addText(fechaIni);
                            grupo.addElement("fechaFin").addText(fechaFin);
                            grupo.addElement("colaborador").addText("" + grupoGuardar.getIdColaborador());
                            grupo.addElement("noDia").addText(diasGrupo[i]);
                        }
                    }
                    con++;
                }
                bandera = guardarDocumento(document);
            } catch (DocumentException ex) {
                System.out.println("Error en metodo guardarGrupo de GrupoXML " + ex.getMessage());
                bandera = false;
            }
        } else {
            guardarDocumento(null);
            bandera = guardarGrupo(grupoGuardar, dias, horas, fechaIni, fechaFin);
        }

        return bandera;
    }

    public static Grupo obtenerGrupoSegunID(String id) {
        Grupo resultado = new Grupo();
        boolean banderaID = false;
        boolean banderaFecha = false;
        boolean banderaHoras = false;

        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/horario.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document documento = reader.read(file);

                List<Node> nodes = documento.selectNodes("//grupo[@id='" + id + "']");
                for (Node node : nodes) {
                    Element elementosGrupo = (Element) node;
                    Iterator<Element> iteratorID = elementosGrupo.elementIterator("id");
                    Iterator<Element> iteratorHoraIni = elementosGrupo.elementIterator("horaIni");
                    Iterator<Element> iteratorHoraFin = elementosGrupo.elementIterator("horaFin");
                    Iterator<Element> iteratorFechaIni = elementosGrupo.elementIterator("fechaIni");
                    Iterator<Element> iteratorFechaFin = elementosGrupo.elementIterator("fechaFin");
                    Iterator<Element> iteratorColaborador = elementosGrupo.elementIterator("colaborador");
                    Iterator<Element> iteratorNoDia = elementosGrupo.elementIterator("noDia");
                    while (iteratorID.hasNext()) {
                        if (!banderaFecha) {
                            resultado.setDias(((Element) iteratorNoDia.next()).getText());
                            banderaFecha = true;
                        } else {
                            resultado.setDias(resultado.getDias() + "/" + ((Element) iteratorNoDia.next()).getText());
                        }

                        if (!banderaHoras) {
                            resultado.setHoras(((Element) iteratorHoraIni.next()).getText() + "-" + ((Element) iteratorHoraFin.next()).getText());
                            banderaHoras = true;
                        } else {
                            resultado.setHoras(resultado.getHoras() + "/" + ((Element) iteratorHoraIni.next()).getText() + "-" + ((Element) iteratorHoraFin.next()).getText());
                        }

                        resultado.setFechaFin(((Element) iteratorFechaFin.next()).getText());
                        resultado.setFechaInicio(((Element) iteratorFechaIni.next()).getText());

                        String idGrupo = ((Element) iteratorID.next()).getText();
                        resultado.setIdGrupo(Integer.parseInt(idGrupo));

                        String idColaborador = ((Element) iteratorColaborador.next()).getText();
                        resultado.setIdColaborador(Integer.parseInt(idColaborador));
                    }
                }

            } catch (NumberFormatException | DocumentException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Error?");
            }
        } else {
            guardarDocumento(null);
            resultado = obtenerGrupoSegunID(id);
        }
        return resultado;
    }

    public static void eliminarGrupoSegunID(String id) {
        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/horario.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document documento = reader.read(file);
                List<Node> nodesDias = documento.selectNodes("//dia");
                List<Node> nodesGrupos = documento.selectNodes("//grupo[@id='" + id + "']");
                for (Node node : nodesDias) {
                    Element elementoDia = (Element) node;
                    for (Node nodeG : nodesGrupos) {
                        elementoDia.remove((Element) nodeG);
                    }
                }
                guardarDocumento(documento);
            } catch (Exception ex) {
                System.out.println("Error en eliminar grupos GrupoXML: " + ex.getMessage());
            }
        } else {
            guardarDocumento(null);
            eliminarGrupoSegunID(id);
        }
    }

    public static List<Grupo> obtenerGruposDiaSemana(int diaSemana) {
        List<Grupo> lista = new ArrayList();
        File directorio = new File(PATHUSRXML);
        File file = new File(directorio.getAbsolutePath() + "/horario.xml");
        if (file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document documento = reader.read(file);

                Node node = documento.selectSingleNode("//dia[@no='" + diaSemana + "']");
                Element elementoDia = (Element) node;
                List<Element> elementosGrupos = new ArrayList<>();
                Iterator<Element> iterator = elementoDia.elementIterator("grupo");
                while (iterator.hasNext()) {
                    //System.out.println("Entre");
                    Element grupo = (Element) iterator.next();
                    elementosGrupos.add(grupo);
                }
                for (Element elementosGrupo : elementosGrupos) {
                    //System.out.println("Entre");
                    Iterator<Element> iteratorID = elementosGrupo.elementIterator("id");
                    Iterator<Element> iteratorHoraIni = elementosGrupo.elementIterator("horaIni");
                    Iterator<Element> iteratorHoraFin = elementosGrupo.elementIterator("horaFin");
                    Iterator<Element> iteratorFechaInicio = elementosGrupo.elementIterator("fechaIni");
                    Iterator<Element> iteratorFechaFin = elementosGrupo.elementIterator("fechaFin");
                    Iterator<Element> iteratorColaborador = elementosGrupo.elementIterator("colaborador");
                    while (iteratorID.hasNext()) {
                        //System.out.println("Entre");
                        Grupo grupo = new Grupo();
                        grupo.setHoraInicio(((Element) iteratorHoraIni.next()).getText());
                        grupo.setHoraFin(((Element) iteratorHoraFin.next()).getText());
                        grupo.setFechaInicio(((Element) iteratorFechaInicio.next()).getText());
                        grupo.setFechaFin(((Element) iteratorFechaFin.next()).getText());
                        String idGrupo = ((Element) iteratorID.next()).getText();
                        grupo.setIdGrupo(Integer.parseInt(idGrupo));
                        String idColaborador = ((Element) iteratorColaborador.next()).getText();
                        grupo.setIdColaborador(Integer.parseInt(idColaborador));
                        lista.add(grupo);
                    }
                }

            } catch (DocumentException ex) {
                System.out.println("Error en el metodo obtenerGruposDiaSemana de GrupoXML: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            guardarDocumento(null);
            lista = obtenerGruposDiaSemana(diaSemana);
        }
        return lista;
    }

    private static boolean guardarDocumento(Document documento) {
        boolean bandera;
        if (documento != null) {
            try {
                File directorio = new File(PATHUSRXML);
                File file = new File(directorio.getAbsolutePath() + "/horario.xml");
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
                //System.out.println(directorio.getAbsolutePath());
                if (!directorio.exists()) {
                    new File(PATHARED).mkdir();
                    directorio.mkdir();
                }
                File file = new File(directorio.getAbsolutePath() + "/horario.xml");
                fileWiter = new FileWriter(file);
                Document documentNuevo = DocumentHelper.createDocument();
                Element root = documentNuevo.addElement("horario");
                root.addElement("dia").addAttribute("no", "1");
                root.addElement("dia").addAttribute("no", "2");
                root.addElement("dia").addAttribute("no", "3");
                root.addElement("dia").addAttribute("no", "4");
                root.addElement("dia").addAttribute("no", "5");
                root.addElement("dia").addAttribute("no", "6");
                root.addElement("dia").addAttribute("no", "7");

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
}
