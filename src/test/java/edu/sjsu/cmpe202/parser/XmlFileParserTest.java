package edu.sjsu.cmpe202.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import edu.sjsu.cmpe202.Record;

public class XmlFileParserTest {
	@Test
	public void testXmlParser() throws IOException, ParserConfigurationException, SAXException {
		XmlFileParser xmlFileParser = new XmlFileParser();
		List<Record> result = xmlFileParser.parse("src/test/resources/sample.xml");
		Assert.assertTrue(result.size() == 12);
		String value = result.get(1).getCcNumberStr();
		Assert.assertEquals(value, "59012345678901234567890");
		if (result.size() != 0) {
			xmlFileParser.write(result, "src/test/resources/SampleOutput.xml");
		}
		File xmlFile = new File("src/test/resources/SampleOutput.xml");
		Assert.assertEquals(xmlFile.exists(), true);
		xmlFile.delete();
	}

	@Test
	public void testEmptyFile() {
		XmlFileParser xmlFileParser = new XmlFileParser();
		List<Record> result = xmlFileParser.parse("src/test/resources/Sample2.xml");
		Assert.assertTrue(result.size() == 0);
	}
}
