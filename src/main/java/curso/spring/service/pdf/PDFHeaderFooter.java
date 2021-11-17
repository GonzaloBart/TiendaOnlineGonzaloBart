package curso.spring.service.pdf;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;

import curso.spring.model.UnidadesCarrito;


public class PDFHeaderFooter extends PdfPageEventHelper {
	
	  //Declaramos la imagen y texto de la cabecera
		Phrase linea;
	    Phrase imgCabecera;
	    Phrase txtCabecera;
	    Image imagen;
	    Phrase txtFecha;
	        
	    String path = "/img/";
	    String titulo;
	    UnidadesCarrito unitsCarrito;
	    String fechaFactura;
	    
	    Phrase txtNombreComercial;
	    Phrase txtNombreFiscal;
	    Phrase txtDireccionEmpresa;
	    Phrase txtCifEmpresa;
	    Phrase txtComentario;
	    
	    //Declaramos el pie de pagina como un Phrase
	    Phrase pie = new Phrase(); 
	    
	    public PDFHeaderFooter(String path, String titulo, UnidadesCarrito unitsCarrito, String fechaFactura) {
			super();
			this.path = path;
			this.titulo = titulo;
			this.unitsCarrito = unitsCarrito;
			//this.empresa = empresa;
			this.fechaFactura = fechaFactura;
		}

		//Template para el numero total de paginas
	    PdfTemplate total;
	    
	    //Evento cuando que se ejecuta en cada nueva pagina del pdf
	    
	    //CABECERA
	    public void onStartPage(PdfWriter writer, Document document) {  
	    	try {
	    	  
	    		//String carpetaLogo = "/img/logotype_dark.png";
	    		
	    		//FuncAux.getRelativePath(FuncExcel.RUTA_CONFIG, FuncExcel.RUTA_IMAGEN);

	    		//TODO: coger la imagen de la entidad Empresa de la BBDD
	    		String rutaImagen = "/img/logotype_dark.png";
	    		
	    		//System.out.println("imagen: " + rutaImagen);
	  	
	    		//Obtenemos la imagen
	    		imagen = Image.getInstance(rutaImagen);
	        
	    		//Convertimos la imagen a un Chunck (Chunck es la parte mas pequenna que puede ser annadida a un documento)
	    		Chunk chunk = new Chunk(imagen, 0, -60);   
	    		
	    		//Convertimos el Chunk en un Phrase (Phrase es una serie de Chunks)
	    		imgCabecera = new Phrase(chunk);   

	    		//Agregamos tambien un texto que acompanne la imagen
	    		//txtCabecera = new Phrase("I CIRCUITO NACIONAL OCTATLoN OPEN MASTERS");
	    		
	    		String nombreComercial = "Applause Wines";
	    		String nombreFiscal = "Applause inc";
	    		String direccionEmpresa = "Zamora";
	    		String cifEmpresa = "478912-T";
	    		String comentario = "";
	    		/*if(empresa!=null) {
	    			nombreComercial = empresa.getNombreComercial();
	    			nombreFiscal = empresa.getNombreFiscal();
	    			direccionEmpresa = empresa.getDireccion() + " " + empresa.getCodigoPostal() + " " + empresa.getLocalidad();
	    			cifEmpresa = empresa.getCif();
	    			comentario = empresa.getComentario();
	    		}*/
	    		txtNombreComercial = new Phrase(nombreComercial, new Font(FontFactory.getFont("Sans", 10, Font.BOLD, BaseColor.BLACK)));
	    		txtNombreFiscal = new Phrase(nombreFiscal, new Font(FontFactory.getFont("Sans", 9, Font.NORMAL, BaseColor.BLACK)));
	    		txtDireccionEmpresa = new Phrase(direccionEmpresa, new Font(FontFactory.getFont("Sans", 9, Font.NORMAL, BaseColor.BLACK)));
	    		txtCifEmpresa = new Phrase(cifEmpresa, new Font(FontFactory.getFont("Sans", 9, Font.NORMAL, BaseColor.BLACK)));
	    		txtComentario = new Phrase(comentario, new Font(FontFactory.getFont("Sans", 9, Font.NORMAL, BaseColor.BLACK)));
	    		
	    		//TODO: comprobacion parametros
	    		/*
	    		if(!Config.comprobarParam(tituloPDF)) {
	    			tituloPDF = FuncExcel.PDF_TITULO;
	    			FuncAux.printDebug("[AVISO] Se toma por defecto el valor: " + tituloPDF);
	    		}
	    		*/
	    		String tituloPDF = titulo; //Config.getParametro(FuncExcel.PDF_TITULO);
	    		txtCabecera = new Phrase(tituloPDF, new Font(FontFactory.getFont("Sans", 11, Font.BOLD, BaseColor.BLACK)));
	    		
	    		//txtCabecera.setFont(new Font(FontFactory.getFont("Sans",30,Font.BOLD, BaseColor.BLUE)));
	    		//txtCabecera.add("I CIRCUITO NACIONAL OCTATLoN OPEN MASTERS");
	    		
	    		txtFecha = new Phrase(fechaFactura, new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));
	    		//txtFecha = new Phrase(Util.getTodayString(), new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));

	    	} catch (BadElementException e){
	    		e.getMessage();
	    		//FuncAux.printDebug("[ERROR] " + e.toString());
	    	} catch (IOException e){
	    		e.getMessage();
	    		//FuncAux.printDebug("[ERROR] " + e.toString());
	    	}  

	    	//Creamos un objeto PdfContentByte donde se guarda el contenido de una pagina en el pdf. Graficos y texto.
	    	PdfContentByte cb = writer.getDirectContent();
	    	//writer.setMargins(10, 10, 10, 10);
	    	
	    	/*
	      	BaseFont bf = null;
				try {
				bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "", true);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	      	cb.setFontAndSize(bf, new Float(10).floatValue());
	    	*/
	      
	    	//Agregamos la imagen y el texto al documento con la siguiente nomenclaruta
	    	//ColumnText.showTextAligned(lienzo, alineacion, Phrase, posicion x, posicion y, rotacion);

	    	ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, txtFecha, (document.right() - document.left()), document.top()+30, 0);
	    	
	    	//Numero factura
	    	//ColumnText.showTextAligned(cb,Element.ALIGN_RIGHT, txtCabecera, (document.right() - document.left()) / 2 + document.leftMargin(), document.top(), 0);
	    	ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, txtCabecera, (document.right() - document.left()), document.top(), 0);
	      
	    	//Empresa
	    	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, txtNombreComercial, (document.right() - document.left()) / 2, document.top()+45, 0);
	    	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, txtNombreFiscal, (document.right() - document.left()) / 2, document.top()+30, 0);
	    	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, txtCifEmpresa, (document.right() - document.left()) / 2, document.top()+20, 0);
	    	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, txtDireccionEmpresa, (document.right() - document.left()) / 2, document.top()+10, 0);
	    	ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, txtComentario, (document.right() - document.left()) / 2, document.top()+0, 0);
	    	
	    	//Esta es la imagen
	    	//ColumnText.showTextAligned(cb,Element.ALIGN_LEFT, imgCabecera, (document.right() - document.left()) / 2 + document.leftMargin(), document.top()+60, 0);
	    	ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, imgCabecera, document.rightMargin()+10, document.top()+60, 0);

	    	//linea de arriba
	    	linea = new Phrase();
	    	linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
	    	ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, document.rightMargin(), document.top()-10, 0);
	    	
	    	//linea de abajo
	    	linea = new Phrase();
	    	linea.add(new LineSeparator(1, new Float(2.8), BaseColor.BLACK, Element.ALIGN_LEFT, 0));
	    	ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, linea, document.rightMargin(), document.top()-730, 0);
	    	
		    //Para dejar un margen debajo de la linea
	    	try {
				document.add(new Paragraph(" "));
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//FuncAux.printDebug("[ERROR] " + e.toString());
			}
	      
	    }   
	    
	    //PIE
	    /*
	    public void onEndPage(PdfWriter writer, Document document) {
	        //Creamos un objeto PdfContentByte donde se guarda el contenido 
	        //de una pagina en el pdf. Graficos y texto.
	        PdfContentByte cb = writer.getDirectContent();
	   
	        //Asignamos el contenido al pie de pagina
	        //getCurrentPageNumber() regresa la pagina actual
	        pie = new Phrase(String.format("Pagina %d", writer.getCurrentPageNumber()));
	   
	        //Agregamos el pie a la pagina
	        //con la siguiente nomenclaruta
	        //ColumnText.showTextAligned(lienzo, alineacion, Phrase, posicion x, posicion y, rotacion);
	        ColumnText.showTextAligned(cb,Element.ALIGN_CENTER, pie, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom()-20, 0);  
	    } 
	    */
	    
	    //Evento cuando que se ejecuta al comenzar un documento
	    public void onOpenDocument(PdfWriter writer, Document document){
	        //Definimos las medidas del template
	        total = writer.getDirectContent().createTemplate(16, 12);
	    }
	    
	    //Evento cuando que se ejecuta al terminar una pagina
	    public void onEndPage(PdfWriter writer, Document document) {
	    	//Creamos una tabla de dos culumnas
	    	PdfPTable table = new PdfPTable(2);
	    	table.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    	try {
	    		//Establecemos las medidas de la tabla
	    		table.setWidths(new int[]{40, 40});
	    		table.setTotalWidth(100);
	    		//Establecemos la altura de la celda
	    		table.getDefaultCell().setFixedHeight(20);
	    		//Quitamos el borde
	    		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);      
	    		//Alineamos el contenido a la derecha
	    		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
	    		//Escribimos el primer texto en la primera celda
	    		Phrase numPag = new Phrase(String.valueOf(writer.getCurrentPageNumber()) + " / ", new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));
	    		table.addCell(numPag);
	    		//table.addCell(String.format("%d / ", writer.getCurrentPageNumber()));
	    		//Obtenemos el template total y lo agregamos a la celda
	    		PdfPCell cell = new PdfPCell(Image.getInstance(total));
	    		//quitamos el borde
	    		cell.setBorder(Rectangle.NO_BORDER);
	    		//lo agregamos a la tabla
	    		table.addCell(cell);
	    		//Ponemos a la derecha el numero de la hoja --520 posicion numero hoja
	    		table.writeSelectedRows(0, -1, 500, 40, writer.getDirectContent());
	    	} catch(DocumentException de) {
	    		throw new ExceptionConverter(de);
	    	} 
	    }
	    
	    public void onCloseDocument(PdfWriter writer, Document document) {  
	    	//Escribimos el contenido al cerrar el documento
	    	Phrase numPag = new Phrase(String.valueOf(writer.getPageNumber() - 1), new Font(FontFactory.getFont("Sans", 8, Font.NORMAL, BaseColor.BLACK)));
	    	ColumnText.showTextAligned(total, Element.ALIGN_LEFT, numPag, 0, 2, 0);  
	        //ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);      
	    }

}
