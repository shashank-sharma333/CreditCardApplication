package edu.sjsu.cmpe202.parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import edu.sjsu.cmpe202.FileParser;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.RecordBuilder;

public class CsvFileParser implements FileParser {

	@Override
	public List<Record> parse(String inputFile) {
		List<Record> result = new ArrayList<>();

		String csvFile = inputFile;

		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(csvFile));
			String[] line;
			int count = 0;
			while ((line = reader.readNext()) != null) {
				// Record record = new Record(count, line[0], line[1], line[2]);
				if (count > 0) {
					Record record1 = RecordBuilder.aRecord().withRecordID(count).withCcNumber(line[0])
							.withExpDate(line[1]).withCcHolderName(line[2]).build();
					// System.out.println("Credit Card [id= " + record1.getRecordID() +
					// ",cardNumber= " + record1.getCcNumberStr() + ", code= " +
					// record1.getExpDate() + " , name=" + record1.getCcHolderName() + "]");
					result.add(record1);
				}
				count++;
			}
		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void write(List<Record> records, String outputFileName) throws IOException {
		String csvFile = outputFileName;
		CSVWriter csvWriter = null;
		try {
			Writer writer = Files.newBufferedWriter(Paths.get(csvFile));

			csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
					CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

			String[] headerRecord = { "CardNumber", "CardType", "Error" };
			csvWriter.writeNext(headerRecord);

			for (int i = 0; i < records.size(); i++) {
				csvWriter.writeNext(new String[] { records.get(i).getCcNumberStr(),
						String.valueOf(records.get(i).getCcType()), records.get(i).getError() });
			}
			csvWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}

}
