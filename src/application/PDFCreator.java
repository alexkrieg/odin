package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.beans.property.StringProperty;

import javax.swing.table.DefaultTableModel;

import org.controlsfx.dialog.Dialogs;

import application.controller.MainWindowController;
import application.model.Lesson;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFCreator { 

    public PDFCreator() { 
        final String[] colNames = new String[] { "Stunde", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag" };
        
        Lesson[][][] dataFromDb = null;
        if (MainWindowController.lastSelectedTeacher != null) {
        	dataFromDb = MainApplication.globalMain.sharedSQLManager().getAllLessonByTeacher(MainWindowController.lastSelectedTeacher);
        }	
        else if(MainWindowController.lastSelectedClass != null) {
        	dataFromDb = MainApplication.globalMain.sharedSQLManager().getAllLessonByClass(MainWindowController.lastSelectedClass);
        }
        else {
        	dataFromDb = null;
        	Dialogs.create()
	        .title("Fehler")
	        .message("Bitte wählen Sie zunächst einen Lehrer oder eine Klasse aus!")
	        .showError();
        	return;
        }
        
        
        
        String[][] data = new String[10][6];
        
        for (int i = 0; i < 10; i++) {
        	for (int j = 0; j < 6; j++) {
        		data[i][j]="";
        	}
        }
        
        
        for (int i = 0; i < 10; i++) {
        	data[i][0]=""+(i+1)+".\n"+this.getTime(i);
        	for (int j = 0; j < 5; j++){
        		for (int k = 0; k < 5; k++){
        			Lesson tmpLesson = dataFromDb[i][j][k];
        			if (tmpLesson != null) {
        				data[i][j]=data[i][j]+tmpLesson.makeMePretty() + "\n\n";
        			}
        			else {
        				data[i][j] = data[i][j] +"";
        			}
        		}
        	}
        }
        
        final DefaultTableModel model = new DefaultTableModel(data, colNames);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddkkmmss");
        String strDate = dateFormat.format(new Date());
        String s = System.getProperty("user.name");
        String path = "C:\\Users\\"+s+"\\Desktop\\Stundenplan_"+strDate+".pdf";
        writePDF(path, model, colNames); 
        Dialogs.create()
        .title("PDF")
        .message("PDF gespeichert unter "+ path)
        .showInformation();
        
    } 
    
    private String getTime(int hour){
		
		switch (hour) {
		case 0:
			return ("08:00 - 08:45");
		case 1:
			return ("08:45 - 09:30");
		case 2:
			return ("09:45 - 10:30");
		case 3:
			return ("10:30 - 11:15");
		case 4:
			return ("11:30 - 12:15");
		case 5:
			return ("12:15 - 13:00");
		case 6:
			return ("13:30 - 14:15");
		case 7:
			return ("14:15 - 15:00");
		case 8:
			return ("15:15 - 16:00");
		case 9:
			return ("16:00 - 16:45");
		default:
			return ("00:00 - 00:00");
		}
	}

    public void writePDF(final String fileName, final DefaultTableModel model, final String[] colNames) { 
        if (model == null) { 
            System.err.println("Couldn't save table as *.pdf: model is null");
            return; 
        } 
        
        StringProperty headInfo = null;
        if (MainWindowController.lastSelectedTeacher!=null) {
        	headInfo = MainWindowController.lastSelectedTeacher.getIdentifier();
        }
        else if (MainWindowController.lastSelectedClass!=null) {
        	headInfo = MainWindowController.lastSelectedClass.getName();
        }
        
        PdfPTable table = new PdfPTable(model.getColumnCount());
        table.setWidthPercentage(100); 
        table.getDefaultCell().enableBorderSide(PdfPCell.BOX);
        // Titel 
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT); 
        Paragraph head = new Paragraph("Stundenplan - "+ headInfo.get() +"\n" + df.format(new GregorianCalendar().getTime()), new Font(FontFamily.HELVETICA, 14, Font.BOLD));
        head.setSpacingAfter(15f); 

        // Spaltentitel 
        for (int i = 0; i < colNames.length; i++) { 
        	PdfPCell cell = new PdfPCell(new Phrase(colNames[i], new Font(FontFamily.HELVETICA, 10))); 
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            table.addCell(cell); 
        } 
        // Tabelleninhalt 
        for (int clmCnt = model.getColumnCount(), rowCnt = model.getRowCount(), i = 0; i < rowCnt; i++) {
        	for (int j = 0; j < clmCnt; j++) { 
        		String value = model.getValueAt(i, j).toString();
                table.addCell(new Phrase(value, new Font(
                FontFamily.HELVETICA, 10))); 
        	} 
        } 

        Document document = new Document(PageSize.A4.rotate(), 20, 15, 15, 15); 
        try { 
        	PdfWriter.getInstance(document, new FileOutputStream(fileName)); 
        	document.open(); 
            document.add(head); 
            document.add(table); 
            document.close(); 
            
        } catch (FileNotFoundException e) { 
        	e.printStackTrace(); 
        } catch (DocumentException e) { 
        	e.printStackTrace(); 
        } 
    }  
}