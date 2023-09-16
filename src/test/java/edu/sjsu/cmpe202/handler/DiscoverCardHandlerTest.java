package edu.sjsu.cmpe202.handler;

import org.junit.Assert;
import org.junit.Test;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.RecordBuilder;
import edu.sjsu.cmpe202.creditcards.DiscoverCreditCard;
import edu.sjsu.cmpe202.handler.DiscoverCreditCardHandler;

public class DiscoverCardHandlerTest {
    @Test
    public void testNullEntry() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("")
                .withCcNumber("")
                .withExpDate("")
                .build();
        DiscoverCreditCardHandler discoverCardHandler = new DiscoverCreditCardHandler();

        CreditCard creditCard =  discoverCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard == null);
        Assert.assertTrue(record.getError() == "InvalidCardNumber");
        Assert.assertEquals(record.getCcType(), "Invalid");
    }
    @Test
    public void testInvalidCardNumberLength() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("test")
                .withCcNumber("601100000000000")
                .withExpDate("3/20/2030")
                .build();
        DiscoverCreditCardHandler discoverCardHandler = new DiscoverCreditCardHandler();

        CreditCard creditCard =  discoverCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard == null);
        Assert.assertTrue(record.getError() == "InvalidCardNumber");
        Assert.assertEquals(record.getCcType(), "Invalid");
    }
    @Test
    public void testInvalidVisaCard() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("test")
                .withCcNumber("jkhkhkjhkjh")
                .withExpDate("")
                .build();
        DiscoverCreditCardHandler discoverCardHandler = new DiscoverCreditCardHandler();

        CreditCard creditCard =  discoverCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard == null);
        Assert.assertTrue(record.getError() == "InvalidCardNumber");
        Assert.assertEquals(record.getCcType(), "Invalid");
    }
    @Test
    public void testValidVisaCard() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("Eve")
                .withCcNumber("6011000000000000")
                .withExpDate("3/20/2030")
                .build();
        DiscoverCreditCardHandler discoverCardHandler = new DiscoverCreditCardHandler();

        CreditCard creditCard =  discoverCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard.getClass() == DiscoverCreditCard.class);
        Assert.assertTrue(record.getError() == "None");
        Assert.assertTrue(record.getCcType() == "Discover");
    }
}
