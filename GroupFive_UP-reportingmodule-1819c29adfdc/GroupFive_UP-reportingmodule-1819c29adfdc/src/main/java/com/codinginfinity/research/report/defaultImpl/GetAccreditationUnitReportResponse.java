/**
*	@file GetAccreditationUnitReportResponse.java
*	@author COS301 Reporting Alpha Team
*	@version 1.0 alpha*
*	@brief An object that represents a completed GetAccreditationUnitReportRequest
*	@section Description
*	This object is what is returned once a reporting request is complete. It contains the generated reports based off of the data sent in the GetAccreditationUnitReportRequest object.
*	The returned report will deal specifically with the accreditation units of people, groups, and publications.
*/

package com.codinginfinity.research.report.defaultImpl;

import com.codinginfinity.research.report.Response;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GetAccreditationUnitReportResponse implements Response
{
    private static final long serialVersionUID = 1L;
    private final JasperPrint print;
    /**
     * Assigns the JasperPrint file. This file allows us to generate XML, PDF and SVG formats of the report
     * @param print
     */
    public GetAccreditationUnitReportResponse(JasperPrint print){
        super();
        this.print = print;
    }

    public boolean getSVG(){
        try{
            DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
            Document document = domImpl.createDocument(null, "svg", null);
            ImageHandler v = new DefaultImageHandler();
            ExtensionHandler ex = new DefaultExtensionHandler();
            SVGGraphics2D grx = new SVGGraphics2D(document,v,ex,true);

            JRGraphics2DExporter exporter = new JRGraphics2DExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, grx);
            exporter.setParameter(JRExporterParameter.PAGE_INDEX, 0);
            exporter.exportReport();

            grx.stream(new FileWriter(new File("AccreditationUnitReport.svg")), true);
        }
        catch(JRException | IOException e){
            return false;
        }
        
        return true;
    }

    public boolean getPDF(){
        try{

            JasperExportManager.exportReportToPdfFile(print, "AccreditationUnitReport.pdf");

        }
        catch( JRException e){
            System.err.println( "JRException " + e);
            return false;
        }
        return true;

    }

    /**
     *
     */
    public String getXML(){

        String result="";
        try{
            result= JasperExportManager.exportReportToXml(print);

        }
        catch( JRException e){
            System.err.println( "JRException " + e);
        }

        return result;
    }

    /**
     *
     */
    public boolean getHTML(){
        try{
            JasperExportManager.exportReportToHtmlFile(print, "AccreditationUnitReport.html");

        }
        catch( JRException e){
            System.err.println( "JRException " + e);
            return false;
        }
        return true;

    }

}
