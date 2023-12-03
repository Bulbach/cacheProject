package printTest;

import by.alex.dto.WagonDto;
import by.alex.util.print.PrintInfo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PrintInfoTest {

    @Test
    public void testCreatePdf() {

        // when
        PrintInfo printInfo = new PrintInfo();
        Collection<Object> testData = createTestData();

        // given
        printInfo.printAll(testData);
        // then
        assertTrue(new File("output/").exists());

    }

    @Test
    public void testComperePdfTrue() {

        // when
        PdfComparator pdfComparator = new PdfComparator();
        String file1Path = "src/test/resources/TestPageForOneWagonDto.pdf";
        String file2Path = "output/WagonDto2023-12-03 12-29.pdf";

        // given
        boolean result = pdfComparator.comparePdfFiles(file1Path, file2Path);

        // then
        assertTrue(result);
    }

    @Test
    public void testComperePdfFalse() {

        // when
        PdfComparator pdfComparator = new PdfComparator();
        String file1Path = "src/test/resources/TestPageForOneWagonDto.pdf";
        String file2Path = "output/WagonDtotable2023-12-03 12-29.pdf";

        // given
        boolean result = pdfComparator.comparePdfFiles(file1Path, file2Path);

        // then
        assertFalse(result);
    }

    @Test
    public void testArraysByteFromFile() {
        // when
        File file1 = new File("src/test/resources/TestPageForOneWagonDto.pdf");
        File file2 = new File("src/test/resources/TestPageForOneWagonDto.pdf");

        byte[] file1Bytes = new byte[(int) file1.length()];
        byte[] file2Bytes = new byte[(int) file2.length()];

        try (FileInputStream fis1 = new FileInputStream(file1);
             FileInputStream fis2 = new FileInputStream(file2)) {
            fis1.read(file1Bytes);
            fis2.read(file2Bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // given
        Boolean equalsResult = Arrays.equals(file1Bytes, file2Bytes);

        //when
        assertTrue(equalsResult);
    }

    private Collection<Object> createTestData() {

        Collection<Object> testData = new ArrayList<>();
        testData.add(
                new WagonDto(UUID.fromString("11111111-1111-1111-1111-111111111111")
                        , "WGN001"
                        , 5000
                        , 2020
                        , LocalDate.of(2022, 01, 01)));
        testData.add(new WagonDto(UUID.fromString("22222222-2222-2222-2222-222222222222")
                , "WGN002"
                , 6000
                , 2019
                , LocalDate.of(2021, 07, 15))
        );
        testData.add(new WagonDto(UUID.fromString("33333333-3333-3333-3333-333333333333")
                , "WGN003"
                , 4500
                , 2021
                , LocalDate.of(2022, 9, 30))
        );
        testData.add(new WagonDto(UUID.fromString("44444444-4444-4444-4444-444444444444")
                , "WGN004"
                , 5500
                , 2020
                , LocalDate.of(2022, 03, 20))
        );
        testData.add(new WagonDto(UUID.fromString("55555555-5555-5555-5555-555555555555")
                , "WGN006"
                , 6500
                , 2019
                , LocalDate.of(2022, 06, 05))
        );
        testData.add(new WagonDto(UUID.fromString("66666666-6666-6666-6666-666666666666")
                , "WGN002"
                , 6000
                , 2019
                , LocalDate.of(2021, 07, 15))
        );
        testData.add(new WagonDto(UUID.fromString("77777777-7777-7777-7777-777777777777")
                , "WGN007"
                , 4800
                , 2021
                , LocalDate.of(2022, 8, 12))
        );
        testData.add(new WagonDto(UUID.fromString("88888888-8888-8888-8888-888888888888")
                , "WGN008"
                , 5200
                , 2020
                , LocalDate.of(2022, 4, 25))
        );
        testData.add(new WagonDto(UUID.fromString("99999999-9999-9999-9999-999999999999")
                , "WGN009"
                , 4300
                , 2018
                , LocalDate.of(2021, 12, 01))
        );
        testData.add(new WagonDto(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                , "WGN010"
                , 5800
                , 2019
                , LocalDate.of(2022, 7, 18))
        );
        return testData;
    }
}

