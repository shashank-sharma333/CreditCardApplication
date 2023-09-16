package edu.sjsu.cmpe202.parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import edu.sjsu.cmpe202.Record;

public class JsonFileParserTest {
	@Test
	public void testJsonParser() throws IOException, ParseException {
		JsonFileParser jsonFileParser = new JsonFileParser();
		List<Record> result = jsonFileParser.parse("src/test/resources/Sample.json");
		Assert.assertTrue(result.size() == 4);
		String value = result.get(1).getCcNumberStr();

		Assert.assertEquals(value, "4120000000000");
		if (result.size() != 0) {
			jsonFileParser.write(result, "src/test/resources/SampleOutput.json");
		}
		File outputFile = new File("src/test/resources/SampleOutput.json");
		Assert.assertEquals(outputFile.exists(), true);
		JSONParser parser = new JSONParser();
		Object obj = parser.parse(new FileReader(outputFile));
		JSONArray cards = (JSONArray) obj;

		Assert.assertEquals(cards.size(), 4);
		JSONObject firstObj = (JSONObject) cards.get(1);
		Assert.assertEquals(firstObj.get("CardNumber"), "4120000000000");
		outputFile.delete();
	}

	@Test
	public void testInvalidJsonParameters() {
		JsonFileParser jsonFileParser = new JsonFileParser();
		List<Record> result = jsonFileParser.parse("src/test/resources/Sample1.json");
		String value = result.get(4).getError();
		System.out.println("value:-" + value);
		Assert.assertEquals(value, "InvalidCardNumber");
		result.forEach(record -> {
			if (record.getRecordID() <= 4) {
				Assert.assertEquals(record.getError(), "None");
			}
		});
	}

	@Test
	public void testEmptyFile() {
		JsonFileParser jsonFileParser = new JsonFileParser();
		List<Record> result = jsonFileParser.parse("src/test/resources/Sample2.json");
		Assert.assertTrue(result.size() == 0);
	}
}
