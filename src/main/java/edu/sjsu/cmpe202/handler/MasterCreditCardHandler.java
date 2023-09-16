package edu.sjsu.cmpe202.handler;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.CreditCardType;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.constants.NumberConstants;
import edu.sjsu.cmpe202.constants.StringConstants;
import edu.sjsu.cmpe202.factory.CreditCardFactoryImpl;

public class MasterCreditCardHandler extends CreditCardType {

	@Override
	public void setSuccessor() {
		this.next = new VisaCreditCardHandler();
	}

	@Override
	public CreditCard process(Record record) {
		String creditCardNumber = record.getCcNumberLongStr();
		char second = creditCardNumber.charAt(1);
		int sec = Integer.parseInt(second + "");

		if (creditCardNumber.charAt(0) == NumberConstants.FIVE) {
			if (((sec >= 1) && (sec <= 5)) && (creditCardNumber.length() == 16)) {
				record.setCcType("MasterCard");
				return new CreditCardFactoryImpl().getCreditCard(StringConstants.MASTER_CARD, record);
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
