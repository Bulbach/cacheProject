package printTest;

import by.alex.dto.WagonDto;
import by.alex.util.print.PrintInfo;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
                WagonDto.builder()
                        .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                        .wagonNumber("WGN001")
                        .loadCapacity(5000)
                        .yearOfConstruction(2020)
                        .dateOfLastService("2022-01-01")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                        .wagonNumber("WGN002")
                        .loadCapacity(6000)
                        .yearOfConstruction(2019)
                        .dateOfLastService("2021-07-15")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                        .wagonNumber("WGN003")
                        .loadCapacity(4500)
                        .yearOfConstruction(2021)
                        .dateOfLastService("2022-09-30")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                        .wagonNumber("WGN004")
                        .loadCapacity(5500)
                        .yearOfConstruction(2020)
                        .dateOfLastService("2022-03-20")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("55555555-5555-5555-5555-555555555555"))
                        .wagonNumber("WGN006")
                        .loadCapacity(6500)
                        .yearOfConstruction(2019)
                        .dateOfLastService("2021-11-10")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("66666666-6666-6666-6666-666666666666"))
                        .wagonNumber("WGN002")
                        .loadCapacity(6000)
                        .yearOfConstruction(2019)
                        .dateOfLastService("2022-06-05")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("77777777-7777-7777-7777-777777777777"))
                        .wagonNumber("WGN007")
                        .loadCapacity(4800)
                        .yearOfConstruction(2021)
                        .dateOfLastService("2022-08-12")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("88888888-8888-8888-8888-888888888888"))
                        .wagonNumber("WGN008")
                        .loadCapacity(5200)
                        .yearOfConstruction(2020)
                        .dateOfLastService("2022-04-25")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("99999999-9999-9999-9999-999999999999"))
                        .wagonNumber("WGN009")
                        .loadCapacity(4300)
                        .yearOfConstruction(2018)
                        .dateOfLastService("2021-12-01")
                        .build()
        );
        testData.add(
                WagonDto.builder()
                        .id(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                        .wagonNumber("WGN010")
                        .loadCapacity(5800)
                        .yearOfConstruction(2019)
                        .dateOfLastService("2022-07-18")
                        .build()
        );
        return testData;
    }
}

