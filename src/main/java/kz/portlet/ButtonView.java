/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.portlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletContext;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
public class ButtonView implements Serializable {

    private StreamedContent file;

    public StreamedContent getFile() {

        InputStream inputStream = null;
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        PortletContext portletContext = (PortletContext) externalContext.getContext();
        InputStream input = portletContext.getResourceAsStream("/resources/report/Report_A4.jasper");
        try {
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("curdate", "30.09.2017");
            java.util.Collection coll = TestFactory.generateCollection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(input, parameters, new JRBeanCollectionDataSource(coll));

            ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream ));
            SimpleXlsxReportConfiguration xls_config = new SimpleXlsxReportConfiguration();
            xls_config.setOnePagePerSheet(false);
            xls_config.setDetectCellType(true);
            xls_config.setWhitePageBackground(false);
            xls_config.setIgnoreGraphics(false);
            xls_config.setRemoveEmptySpaceBetweenRows(true);
            exporter.setConfiguration(xls_config);
            exporter.exportReport();
            //JasperExportManager.exportReportToPdfFile(jasperPrint, outFileNamePDF);
            
            inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());            
            input.close();
            byteArrayOutputStream.close();
        } catch (Exception ex) {
            System.out.println("Erorr - " + ex.getMessage());
        }
        return new DefaultStreamedContent(inputStream, "application/application/vnd.ms-excel", "Report_A4.xlsx");
    }
}
