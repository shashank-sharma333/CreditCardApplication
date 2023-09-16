package edu.sjsu.cmpe202.parser;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.parser.CsvFileParser;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvFileParserTest {
    @Test
    public void testCsvParser() throws IOException, CsvException {
        CsvFileParser csvFileParser = new CsvFileParser();
        List<Record> result = csvFileParser.parse("src/test/resources/Sample.csv");
        Assert.assertTrue(result.size() == 4);
        String value = result.get(1).getCcNumberStr();
        Assert.assertEquals(value,"4.12E+12");
        if (result.size() != 0){
            csvFileParser.write(result,"src/test/resources/SampleOutput.csv");
        }
        File outputFile = new File("src/test/resources/SampleOutput.csv");
        Assert.assertEquals(outputFile.exists(), true);
        FileReader fileReader = new FileReader(outputFile);
        CSVReader csvReader = new CSVReader(fileReader);
        List<String[]>  allLines = csvReader.readAll();

        Assert.assertEquals(allLines.size(), 5);
        String header [] = {"CardNumber", "CardType", "Error"};
        Assert.assertArrayEquals(allLines.get(0), header);
        outputFile.delete();
    }

    @Test
    public void testInvalidCsvParameters(){
        CsvFileParser csvFileParser = new CsvFileParser();
        List<Record> result = csvFileParser.parse("src/test/resources/Sample1.csv");
        String value = result.get(4).getError();
        Assert.assertEquals(value,"InvalidCardNumber");
        result.forEach(record -> {
            if (record.getRecordID() <= 4){
                Assert.assertEquals(record.getError(),"None");
            }
        });
    }
    @Test
    public void testEmptyFile(){
        CsvFileParser csvFileParser = new CsvFileParser();
        List<Record> result = csvFileParser.parse("src/test/resources/Sample2.csv");
        Assert.assertTrue(result.size() == 0);
    }

}
