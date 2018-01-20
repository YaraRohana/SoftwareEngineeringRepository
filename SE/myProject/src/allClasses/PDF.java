package allClasses;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
//import java.io.IOException;
import java.sql.SQLException;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import da.DataAccess;

public class PDF {

	public static void main(String[] args) throws DocumentException, FileNotFoundException, SQLException {
		DataAccess da=new DataAccess();
		Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Result\\tempPDF.pdf"));
        document.open();
        Paragraph header = new Paragraph("Graph info",FontFactory.getFont(FontFactory.TIMES_BOLD,20,Font.BOLD,BaseColor.BLACK));
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(36, 36, 559, 806);
        rect.setBorder(Rectangle.BOX);
        rect.setBackgroundColor(BaseColor.LIGHT_GRAY);
        canvas.rectangle(rect);
        String level="First level";
        int levelNum = 0;
        switch(level) {
        case "First level":
        	levelNum = 0;
        	break;
        case "Second level":
        	levelNum = 1;
        	break;
        case "Third level":
        	levelNum = 2;
        	break;
        }
        int x = 190;
		int y = 670;
		ParkingSpot[][][] spots = da.getParkingLotImageNew("Univ");
        for(int i = 0 ; i < 2; i++) {
    		for(int j = 0; j < 3; j++) {
        		ParkingSpot spot = spots[levelNum][j][i];
                PdfContentByte innerCanvas = writer.getDirectContent();
				// Inner retangle size
        		Rectangle innerRect = new Rectangle(5, 5, 5, 5);
        		innerRect.setBorder(Rectangle.BOX);
        		BaseColor color;
        		// Color according to whether it's active, occupied, or reserved.
        		innerRect.setBackgroundColor(BaseColor.DARK_GRAY);
                innerCanvas.rectangle(innerRect);
				// increase x value.
			}
			// restore x value.
			// decrease y value.
        }
        Paragraph row = new Paragraph("1st row      2nd row      3rd row",FontFactory.getFont(FontFactory.TIMES_BOLD,16,BaseColor.BLACK));
        row.setAlignment(Element.ALIGN_CENTER);
        document.add(row);
        document.close();

	}

}
