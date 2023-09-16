package edu.sjsu.cmpe202.handler;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.CreditCardType;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.constants.NumberConstants;
import edu.sjsu.cmpe202.constants.StringConstants;
import edu.sjsu.cmpe202.factory.CreditCardFactoryImpl;

public class VisaCreditCardHandler extends CreditCardType {
	@Override
	public void setSuccessor() {
		this.next = new AmexCreditCardHandler();
	}

	@Override
	public CreditCard process(Record record) {
		String creditCardNumber = record.getCcNumberLongStr();

		if (creditCardNumber.charAt(0) == NumberConstants.FOUR) {
			if ((creditCardNumber.length() == 13) || (creditCardNumber.length() == 16)) {
				record.setCcType("Visa");
				return new CreditCardFactoryImpl().getCreditCard(StringConstants.VISA, record);
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
