package edu.sjsu.cmpe202.handler;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.CreditCardType;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.constants.NumberConstants;
import edu.sjsu.cmpe202.constants.StringConstants;
import edu.sjsu.cmpe202.factory.CreditCardFactoryImpl;

public class AmexCreditCardHandler extends CreditCardType {
	@Override
	public void setSuccessor() {
		this.next = new DiscoverCreditCardHandler();
	}

	@Override
	public CreditCard process(Record record) {
		String creditCardNumber = record.getCcNumberLongStr();
		char secondCharacter = creditCardNumber.charAt(1);

		if (creditCardNumber.charAt(0) == NumberConstants.THREE) {
			if (((secondCharacter == NumberConstants.FOUR) || (secondCharacter == NumberConstants.SEVEN))
					&& (creditCardNumber.length() == 15)) {
				record.setCcType("AmericanExpress");
				return new CreditCardFactoryImpl().getCreditCard(StringConstants.AMEX, record);
			} else {
				record.setCcType("Invalid");
				record.setError("InvalidCardNumber");
				return null;
			}
		} else {
			return this.next.process(record);
		}

	}
}
