package edu.sjsu.cmpe202.run;

import edu.sjsu.cmpe202.FileParser;
import edu.sjsu.cmpe202.parser.CsvFileParser;
import edu.sjsu.cmpe202.parser.JsonFileParser;
import edu.sjsu.cmpe202.parser.XmlFileParser;

public class Client {

	// Default Parser
	private FileParser fileParser = new CsvFileParser();

	public FileParser getFileParser() {
		return fileParser;
	}

	public void setParsingStrategy(String inputFile) {
		if (inputFile.endsWith("json")) {
			this.fileParser = new JsonFileParser();
		} else if (inputFile.endsWith("xml")) {
			this.fileParser = new XmlFileParser();
		}
	}

}
