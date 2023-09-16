package edu.sjsu.cmpe202.factory;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.Record;

public interface CreditCardFactory {
	public CreditCard getCreditCard(String cardType, Record record);

}
