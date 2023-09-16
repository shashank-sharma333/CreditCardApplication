package edu.sjsu.cmpe202.run;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.CreditCardType;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.handler.MasterCreditCardHandler;

public class DriverClass {
	static Client client = new Client();

	public static void main(String[] args) throws FileNotFoundException {

		String inputPath = args[0];
		String outputPath = args[1];

		System.out.println(" args[0]=" + args[0]);
		System.out.println(" args[1]=" + args[1]);
		System.out.println("---------------");

		File inputFile = new File(inputPath);
		if (!inputFile.exists()) {
			throw new FileNotFoundException(
					inputPath + " is not valid file, and does not exist. Please check the path of the input file !! ");
		}
		File outputFile = new File(outputPath);
		if (!outputFile.exists()) {
			File parentDir = outputFile.getParentFile();
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		client.setParsingStrategy(inputFile.getAbsolutePath());

		List<Record> result = client.getFileParser().parse(inputFile.getAbsolutePath());

		// invoke the code to find the card type
		CreditCardType creditCardType = new MasterCreditCardHandler();

		List<CreditCard> creditCards = new ArrayList<>();
		result.forEach(record -> {
			CreditCard creditCard = creditCardType.verifyCardAndProcess(record);
			creditCards.add(creditCard);
		});
		try {
			client.getFileParser().write(result, outputFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
