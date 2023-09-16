package edu.sjsu.cmpe202.handler;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.CreditCardType;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.constants.StringConstants;
import edu.sjsu.cmpe202.factory.CreditCardFactoryImpl;

public class DiscoverCreditCardHandler extends CreditCardType {
	@Override
	public void setSuccessor() {
	}

	@Override
	public CreditCard process(Record record) {
		String creditCardNumber = record.getCcNumberLongStr();

		if (creditCardNumber.startsWith("6011") && creditCardNumber.length() == 16) {
			record.setCcType("Discover");
			return new CreditCardFactoryImpl().getCreditCard(StringConstants.DISCOVER, record);
		} else {
			record.setCcType("Invalid");
			record.setError("InvalidCardNumber");
			return null;
		}
	}
}
