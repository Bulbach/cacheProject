package by.alex.util.print;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class PrintInfo {

    private static final String OUTPUT_PDF = "output";
    public static final String CLEVERTEC_TEMPLATE_PDF = "src/main/resources/Clevertec_Template.pdf";

    public void print(Object object)  {
        PdfDocument pdf = createPdf(object.getClass().getSimpleName());
        Document document = createDocument(object, pdf);
        Paragraph paragraph = createParagraphPrintInfo(object);
        document.add(paragraph);
        document.close();
    }
    public void printAll(Collection<Object> list){

    }

    public PdfDocument createPdf(String path) {
        PdfDocument pdf;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        String dateAndTime = dateFormat.format(new Date());
        try {

            PdfDocument existingPdf = new PdfDocument(new PdfReader(CLEVERTEC_TEMPLATE_PDF));
            pdf = new PdfDocument(new PdfWriter(new FileOutputStream(
                    OUTPUT_PDF + "/" + path + dateAndTime + ".pdf"
            )));
            for (int pageNum = 1; pageNum <= existingPdf.getNumberOfPages(); pageNum++) {
                PdfPage page = existingPdf.getPage(pageNum).copyTo(pdf);
                pdf.addPage(page);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return pdf;
    }

    private static Document createDocument(Object object, PdfDocument pdf) {
        Document document = new Document(pdf, PageSize.A4, true);
            document.add(new Paragraph("Info about " + object.getClass().getSimpleName())
                    .setMargins(200, 100, 10, 200)
            );

            return document;

    }
    private Paragraph createParagraphPrintInfo(Object objectDto) {
        Paragraph paragraph = new Paragraph();
        paragraph.setMargins(10, 50, 50, 10);
        Field[] fields = objectDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Field fieldDto = null;
            try {
                fieldDto = objectDto.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            Object valueField = null;
            fieldDto.setAccessible(true);
            try {
                valueField = fieldDto.get(objectDto);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            paragraph.add(fieldName + " : " + valueField + "\n");
        }
        return paragraph;
    }


}
