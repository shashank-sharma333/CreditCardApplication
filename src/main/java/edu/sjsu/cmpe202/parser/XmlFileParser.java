package edu.sjsu.cmpe202.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.sjsu.cmpe202.FileParser;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.RecordBuilder;

public class XmlFileParser implements FileParser {
	@Override
	public List<Record> parse(String inputFile) {
		List<Record> result = new ArrayList<>();
		File xmlFile = new File(inputFile);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CARD");

			int count = 1;
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String cNum = eElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
					String cExp = eElement.getElementsByTagName("EXPIRATION_DATE").item(0).getTextContent();
					String cHolder = eElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();
					Record record1 = RecordBuilder.aRecord().withRecordID(count).withCcNumber(cNum).withExpDate(cExp)
							.withCcHolderName(cHolder).build();
					result.add(record1);
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void write(List<Record> records, String outputFileName) throws IOException {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			doc.setXmlStandalone(true);
			// root element
			Element rootElement = doc.createElement("CARDS");
			doc.appendChild(rootElement);

			for (int i = 0; i < records.size(); i++) {
				Element rowElement = doc.createElement("CARD");
				Element CardNumber = doc.createElement("CARD_NUMBER");
				CardNumber.appendChild(doc.createTextNode(records.get(i).getCcNumberStr()));
				rowElement.appendChild(CardNumber);
				Element CardType = doc.createElement("CARD_TYPE");
				CardType.appendChild(doc.createTextNode(String.valueOf(records.get(i).getCcType())));
				rowElement.appendChild(CardType);
				Element Error = doc.createElement("Error");
				Error.appendChild(doc.createTextNode(records.get(i).getError()));
				rowElement.appendChild(Error);
				rootElement.appendChild(rowElement);
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(outputFileName));
			transformer.transform(source, result);

			// Output to console for testing
//			StreamResult consoleResult = new StreamResult(System.out);
//			transformer.transform(source, consoleResult);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Record> parseOutputFile(String inputFile) {
		List<Record> result = new ArrayList<>();
		File xmlFile = new File(inputFile);
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("CARD");
			int count = 1;
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String cNum = eElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
					String cExp = eElement.getElementsByTagName("CARD_TYPE").item(0).getTextContent();
					String cHolder = eElement.getElementsByTagName("ERROR").item(0).getTextContent();
					Record record1 = RecordBuilder.aRecord().withRecordID(count).withCcNumber(cNum).withExpDate(cExp)
							.withCcHolderName(cHolder).withError(cHolder).build();
					result.add(record1);
					count++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
