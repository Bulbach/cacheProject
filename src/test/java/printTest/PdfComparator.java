package printTest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfComparator {
    public boolean comparePdfFiles(String file1Path, String file2Path) {
        try {
            PDDocument doc1 = PDDocument.load(new File(file1Path));
            PDDocument doc2 = PDDocument.load(new File(file2Path));
            PDFTextStripper stripper = new PDFTextStripper();
            String text1 = stripper.getText(doc1);
            String text2 = stripper.getText(doc2);
            doc1.close();
            doc2.close();
            return text1.equals(text2);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
