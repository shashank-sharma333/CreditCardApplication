package edu.sjsu.cmpe202.handler;

import org.junit.Assert;
import org.junit.Test;

import edu.sjsu.cmpe202.CreditCard;
import edu.sjsu.cmpe202.Record;
import edu.sjsu.cmpe202.RecordBuilder;
import edu.sjsu.cmpe202.creditcards.MasterCreditCard;
import edu.sjsu.cmpe202.handler.MasterCreditCardHandler;

public class MasterCardHandlerTest {

    @Test
    public void testNullEntry() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("")
                .withCcNumber("")
                .withExpDate("")
                .build();
        MasterCreditCardHandler masterCardHandler = new MasterCreditCardHandler();

        CreditCard creditCard =  masterCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard == null);
        Assert.assertTrue(record.getError() == "InvalidCardNumber");
        Assert.assertEquals(record.getCcType(), "Invalid");
    }
    @Test
    public void testInvalidCardNumberLength() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("test")
                .withCcNumber("541000000000000")
                .withExpDate("3/20/2030")
                .build();

        MasterCreditCardHandler masterCardHandler = new MasterCreditCardHandler();

        CreditCard creditCard =  masterCardHandler.verifyCardAndProcess(record);
        Assert.assertEquals(record.getExpDateObj().toString(), "Wed Mar 20 00:00:00 PDT 2030");
        Assert.assertTrue(creditCard == null);
        Assert.assertTrue(record.getError() == "InvalidCardNumber");
        Assert.assertEquals(record.getCcType(), "Invalid");
    }
    @Test
    public void testInvalidMasterCard() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("test")
                .withCcNumber("jkhkhkjhkjh")
                .withExpDate("")
                .build();
        MasterCreditCardHandler masterCardHandler = new MasterCreditCardHandler();

        CreditCard creditCard =  masterCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard == null);
        Assert.assertTrue(record.getError() == "InvalidCardNumber");
        Assert.assertEquals(record.getCcType(), "Invalid");
        //Assert.assertTrue(creditCard.getClass() == MasterCreditCard.class);
    }
    @Test
    public void testValidMasterCard() {

        Record record = RecordBuilder.aRecord()
                .withRecordID(1)
                .withCcHolderName("Alice")
                .withCcNumber("5410000000000000")
                .withExpDate("3/20/2030")
                .build();
        MasterCreditCardHandler masterCardHandler = new MasterCreditCardHandler();

        CreditCard creditCard =  masterCardHandler.verifyCardAndProcess(record);
        Assert.assertTrue(creditCard.getClass() == MasterCreditCard.class);
        Assert.assertTrue(record.getError() == "None");
        Assert.assertTrue(record.getCcType() == "MasterCard");
    }


}
