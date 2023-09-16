package edu.sjsu.cmpe202.factory;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.constants.StringConstants;
import edu.sjsu.cmpe202.creditcards.AmexCreditCard;
import edu.sjsu.cmpe202.creditcards.DiscoverCreditCard;
import edu.sjsu.cmpe202.creditcards.Generic;
import edu.sjsu.cmpe202.creditcards.MasterCreditCard;
import edu.sjsu.cmpe202.creditcards.VisaCreditCard;

public class CreditCardFactoryImpl implements CreditCardFactory {

	public CreditCard getCreditCard(String cardType, Record record) {
		if (StringConstants.MASTER_CARD.equals(cardType)) {
			return new MasterCreditCard(record);
		} else if (StringConstants.DISCOVER.equals(cardType)) {
			return new DiscoverCreditCard(record);
		} else if (StringConstants.AMEX.equals(cardType)) {
			return new AmexCreditCard(record);
		} else if (StringConstants.VISA.equals(cardType)) {
			return new VisaCreditCard(record);
		} else {
			return new Generic(record);
		}
	}
}
