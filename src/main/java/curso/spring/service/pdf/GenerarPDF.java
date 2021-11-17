package curso.spring.service.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import curso.spring.controller.MainController;
import curso.spring.model.DetallesPedido;
import curso.spring.model.Pedidos;
import curso.spring.service.DetallesPedidoService;

/**
 * Genera PDF Factura
 * @author Gonzalo
 *
 */
public class GenerarPDF {

	Logger logger = LogManager.getLogger(GenerarPDF.class);

	
	private static final long serialVersionUID = 1L;
	private static int registro;
	private static int puesto;
	
	private static final int NUM_COLUMNAS_PDF = 4;
	private static final int REG_X_PAGINA = 30;
	
	private PdfWriter writer = null;
	
	public String getPDF(Pedidos pedido, List<DetallesPedido> list) throws Exception {
	
	
		String fecha = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

		String ficheroPdf =  "/Users/Newland/eclipse-spring/TiendaOnline_Gonzalo_Bartolome_Boya/factura/factura.pdf"; 
			    	
    	escribirPDF(ficheroPdf, "FACTURA " + pedido.getNumFactura(), pedido, list);
	
    	return ficheroPdf;
	}
	
	public void escribirPDF(String ficheroPDF, String numFactura ,Pedidos pedido, List<DetallesPedido> list) throws Exception {
    	
		Document documento = crearPDF(ficheroPDF, "factura", "Applause", "2021");
	    
		
	    documento.open();
	    
	    try {
	    	
	    	/**Datos del Cliente*/
	    	documento.add(parrafo("Cliente", "Sans", 10, Font.BOLD, BaseColor.BLACK, 0, 10));
	    	
	    	String dni = "";
	    	if(pedido.getUsuario().getDni()!=null) dni = pedido.getUsuario().getDni();
	    	documento.add(parrafo(pedido.getUsuario().getNombre() + "\n" + pedido.getUsuario().getApellido1()+ "\n" + dni, "Sans", 9, Font.NORMAL, BaseColor.BLACK, 0, 0));
	    	
	    	String direccion = "";
	    	if(pedido.getUsuario().getDireccion()!=null) direccion += "\n" + pedido.getUsuario().getDireccion() + "\n";
	    	if(pedido.getUsuario().getLocalidad()!=null) direccion += pedido.getUsuario().getLocalidad() + " ";
	    	if(pedido.getUsuario().getProvincia()!=null) direccion += pedido.getUsuario().getProvincia() + " ";
	    	if(pedido.getUsuario().getTelefono()!=null) direccion += "- " + pedido.getUsuario().getTelefono();
	    	documento.add(parrafo(direccion, "Sans", 9, Font.NORMAL, BaseColor.BLACK, 0, 30));
	    	
	    	int paginas = 0;
	    	PdfPTable tabla;    
	    	/**Productos facturados*/

			Double total = new Double(0);
			
	    	//DetallesPedidoService ds = new DetallesPedidoService();

	    
			for (DetallesPedido detalle : list) {
				documento.add(parrafo("Producto: " + detalle.getProducto().getNombre()  + ", Precio: " + detalle.getPrecioUnidad() + ", Unidades: " + detalle.getUnidades() + ", Total " + detalle.getTotal(), "Sans", 12, Font.BOLD, BaseColor.BLACK, 20, 0));
				total += detalle.getTotal();
			}
	    	
			documento.add(parrafo("* " + pedido.getMetodoPago().getMetodoPago(), "Sans", 9, Font.NORMAL, BaseColor.BLACK, 0, 30));
			
			total=pedido.getTotal();
			documento.add(parrafo("TOTAL: " + total + " euros", "Sans", 12, Font.BOLD, BaseColor.BLACK, 20, 0));

			
	    } catch (DocumentException ex) {
	    	logger.error(ex.getMessage());
	    } finally {
		    documento.close();
		    writer.close(); 
	    }	    
	}
	
	public Document crearPDF(String ficheroPdf, String factura, String titulo, String fechaFactura) throws Exception {
		Document documento;
	   
		documento = new Document(PageSize.A4, 20, 20, 70, 50);

	    try {      
	    	writer = PdfWriter.getInstance(documento, new FileOutputStream(ficheroPdf));
		    writer.setCompressionLevel(9);
	    	
		    PDFHeaderFooter event = new PDFHeaderFooter(ficheroPdf, titulo, null, fechaFactura);
	    	
	        writer.setPageEvent(event);
	        
	    } catch (FileNotFoundException ex) {
	    	logger.error(ex.getMessage());
	    	
	    	throw ex;
	    } catch (DocumentException ex) {
	    	logger.error(ex.getMessage());
	    	throw ex;
	    }
	    
	    return documento;
	}


	


	public PdfPTable tablaCabeceraProductos() throws Exception {
	    PdfPTable tabla = new PdfPTable(NUM_COLUMNAS_PDF);
	    
        int[] values = new int[NUM_COLUMNAS_PDF];
        values = anchoCeldas(values);
        
        try {
			tabla.setWidths(values);
		} catch (DocumentException e) {
			logger.error(e + "error ancho pdf");
		}
	    tabla.setWidthPercentage(new Float(100));
	   
	    tabla = titulosCabecera(tabla);
	    
	    return tabla;
	}

	public Phrase getFormatoCelda(String contenido, String font, float tamanno, int style, BaseColor color) throws Exception {
		Phrase celda = new Phrase(contenido, new Font(new Font(FontFactory.getFont(font, tamanno, style, color))));
	    return celda;
	}
	
	public Paragraph getFormatoParrafo(String contenido, String font, float tamanno, int style, BaseColor color, float spacBefore, float spacAfter) throws Exception {
		Paragraph paragraph = new Paragraph();
	    //paragraph.setAlignment(Element.ALIGN_CENTER);
	    paragraph.setFont(new Font(FontFactory.getFont(font, tamanno, style, color)));
	    paragraph.add(contenido);
	    paragraph.setSpacingBefore(spacBefore);
	    paragraph.setSpacingAfter(spacAfter);
	    //paragraph.setAlignment(Element.ALIGN_CENTER);
	    return paragraph;
	}
	
	public Paragraph parrafoTitulo(String contenido) throws Exception {
	    return getFormatoParrafo(contenido, "Sans", 11, Font.BOLD, BaseColor.BLACK, 0, 5);
	}
	
	public Paragraph parrafo(String contenido, String font, float sizeText, int styleText, BaseColor textColor, float spacBefore, float spacAfter) throws Exception {
	    return getFormatoParrafo(contenido, font, sizeText, styleText, textColor, spacBefore, spacAfter);
	}
	
	public Paragraph parrafo(String contenido, String font, float sizeText, int styleText, BaseColor textColor, float spacBefore, float spacAfter, int alignment) throws Exception {
	    return getFormatoParrafo(contenido, font, sizeText, styleText, textColor, spacBefore, spacAfter, alignment);
	}

	public Paragraph getFormatoParrafo(String contenido, String font, float tamanno, int style, BaseColor color, float spacBefore, float spacAfter, int alignment) throws Exception {
		Paragraph paragraph = new Paragraph();
	    paragraph.setAlignment(alignment);
	    paragraph.setFont(new Font(FontFactory.getFont(font, tamanno, style, color)));
	    paragraph.add(contenido);
	    paragraph.setSpacingBefore(spacBefore);
	    paragraph.setSpacingAfter(spacAfter);
	    return paragraph;
	}
	
	public PdfPCell celda(String contenido, Float textSize, int style, BaseColor textColor, BaseColor backgroundColor, float borderWidth,
			BaseColor borderColor, int horizontalAlignment, int verticalAlignment, float paddingBottom) throws Exception {
		Phrase cab = getFormatoCelda(contenido, "my_bold_font", textSize, style, textColor);
		
		PdfPCell celda;
	    celda = new PdfPCell(cab);
	    //Propiedades concretas de formato
	    celda.setBackgroundColor(backgroundColor);
	    celda.setBorderWidth(borderWidth);
	    celda.setBorderColor(borderColor);
	    celda.setHorizontalAlignment(horizontalAlignment);
	    //celda.setVerticalAlignment(verticalAlignment);
	    celda.setPaddingBottom(paddingBottom);
	    
		return celda;
	}
	
	public PdfPCell celdaCabeceraIVA(String contenido) throws Exception {
		Phrase cab = getFormatoCelda(contenido, "my_bold_font", 9, Font.NORMAL, BaseColor.BLACK);
		
		PdfPCell celda;
	    celda = new PdfPCell(cab);
	    //Propiedades concretas de formato
	    celda.setBackgroundColor(BaseColor.WHITE);
	    celda.setBorderWidth(1);
	    celda.setBorderColor(BaseColor.WHITE);
	    celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	    //celda.setVerticalAlignment(PdfPCell.ALIGN_TOP);
	    celda.setPaddingBottom(3);
	    
		return celda;
	}
		
	public PdfPCell celdaCabecera(String contenido) throws Exception {
		Phrase cab = getFormatoCelda(contenido, "my_bold_font", 10, Font.BOLD, BaseColor.BLACK);
		
		PdfPCell celda;
	    celda = new PdfPCell(cab);
	    //Propiedades concretas de formato
	    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    celda.setBorderWidth(1);
	    celda.setBorderColor(BaseColor.WHITE);
	    celda.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    //celda.setVerticalAlignment(PdfPCell.ALIGN_TOP);
	    celda.setPaddingBottom(3);
	    
		return celda;
	}
	
	public PdfPCell celdaNormal(String contenido, int alineacion, BaseColor color) throws Exception {
		Phrase con = getFormatoCelda(contenido, "my_bold_font", 9, Font.NORMAL, color);
		
		PdfPCell celda;
	    celda = new PdfPCell(con);
	    //Propiedades concretas de formato
	    celda.setBorderWidth(1);
	    celda.setBorderColor(BaseColor.WHITE);
	    celda.setHorizontalAlignment(alineacion);
	    
		return celda;
	}
	
	public PdfPCell celdaNormal(String contenido, int alineacion) throws Exception {
		return celdaNormal(contenido, alineacion, BaseColor.BLACK);
	}

	public PdfPTable titulosCabecera(PdfPTable tabla) throws Exception {
		tabla.addCell(celdaCabecera("Id"));
		tabla.addCell(celdaCabecera("Concepto"));
	    tabla.addCell(celdaCabecera("Unidades"));
	    tabla.addCell(celdaCabecera("Precio"));
	    
	    return tabla;
	}

	public int[] anchoCeldas(int[] values) {
		values[0] = 45; //numero
		values[1] = 130; //nombre
		values[2] = 45; //unidades
	    values[3] = 60; //precio
	    
	    return values;
	}
	
}
