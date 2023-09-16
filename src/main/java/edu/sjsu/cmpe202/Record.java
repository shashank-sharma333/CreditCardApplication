package edu.sjsu.cmpe202;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
	Integer recordID;
	String ccNumberStr;
	String expDate;
	Date expDateObj;
	String ccHolderName;

	Long ccNumberLong;

	String ccType = null;

	String error = "None";

	public Record(Integer recordID, String ccNumberStr, String expDate, String ccHolderName) {
		this.recordID = recordID;

		try {
			this.expDate = expDate;
			this.ccHolderName = ccHolderName;

			this.ccNumberStr = ccNumberStr.trim();
			this.ccNumberLong = new BigDecimal(ccNumberStr).longValue();
		} catch (Exception e) {
			setError("InvalidCardNumber");
		}

		try {
			this.expDateObj = new SimpleDateFormat("MM/dd/yyyy").parse(expDate);
		} catch (Exception e) {

		}

	}

	public String getCcNumberLongStr() {
		if (ccNumberLong != null) {
			return ccNumberLong.toString();
		} else
			return null;
	}

	public Integer getRecordID() {
		return recordID;
	}

	public String getCcNumberStr() {
		return ccNumberStr;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getCcHolderName() {
		return ccHolderName;
	}

	public String getCcType() {
		return ccType;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Date getExpDateObj() {
		return expDateObj;
	}

	public void setExpDateObj(Date expDateObj) {
		this.expDateObj = expDateObj;
	}

	@Override
	public String toString() {
		return "Record{" + "recordID=" + recordID + ", ccNumber=" + ccNumberStr + ", expDate='" + expDate + '\''
				+ ", ccHolderName='" + ccHolderName + '\'' + '}';
	}
}
