package edu.sjsu.cmpe202.parser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cedarsoftware.util.io.JsonWriter;

import edu.sjsu.cmpe202.FileParser;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.RecordBuilder;

public class JsonFileParser implements FileParser {
	@Override
	public List<Record> parse(String inputFile) {
		List<Record> result = new ArrayList<>();

		String jsonFile = inputFile;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(jsonFile));
			JSONObject ob = (JSONObject) obj;
			JSONArray cards = (JSONArray) ob.get("cards");

			int count = 1;
			Iterator<JSONObject> iterator = cards.iterator();
			while (iterator.hasNext()) {
				JSONObject card = iterator.next();
				String cNum = String.valueOf(card.get("cardNumber"));
				String cExp = (String) card.get("expirationDate");
				String cHolder = (String) card.get("cardHolderName");

				Record record1 = RecordBuilder.aRecord().withRecordID(count).withCcNumber(cNum).withExpDate(cExp)
						.withCcHolderName(cHolder).build();
				result.add(record1);
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void write(List<Record> records, String outputFileName) throws IOException {
		JSONArray arr = new JSONArray();

		for (int i = 0; i < records.size(); i++) {
			JSONObject card = new JSONObject();
			card.put("CardNumber", records.get(i).getCcNumberStr());
			card.put("CardType", String.valueOf(records.get(i).getCcType()));
			card.put("Error", records.get(i).getError());
			arr.add(card);
		}
		File out = new File(outputFileName);
		FileWriter writer = new FileWriter(out);
		String niceFormattedJson = JsonWriter.formatJson(arr.toJSONString());
		writer.write(niceFormattedJson);
		writer.close();

	}
}
