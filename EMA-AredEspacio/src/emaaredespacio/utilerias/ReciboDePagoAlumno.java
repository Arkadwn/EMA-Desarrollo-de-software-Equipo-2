/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emaaredespacio.utilerias;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author enriq
 */
public class ReciboDePagoAlumno {
    public static void generarRecibo(int idPago){
        Connection con = Conexion.getConnection();
        try {
            JasperDesign jd = JRXmlLoader.load(new File("").getAbsolutePath()+"/src/emaaredespacio/utilerias/report1.jrxml");
                        HashMap param = new HashMap();
                        param.put("idPago",idPago);
                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        JasperPrint jp = JasperFillManager.fillReport(jr, param,con);
                        JasperViewer jv = new JasperViewer(jp,false);
                        jv.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
