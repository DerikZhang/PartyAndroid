package com.party.sqliteManager;

public class Party {

	Integer p_id;
	Integer u_id;
	String partyName;
	String introduction;
	String function;
	String location;
	String QRcode;

	public Party() {

	}

	/**
	 * @param String
	 *            partyName
	 * 
	 * @param String
	 *            introduction
	 * 
	 * @param String
	 *            function
	 * 
	 * @param String
	 *            location
	 * 
	 * @param String
	 *            QRcode
	 */
	public void setParty(String partyName, String introduction,
			String function, String location, String QRcode) {

		this.partyName = partyName;
		this.introduction = introduction;
		this.function = function;
		this.location = location;
		this.QRcode = QRcode;
	}

	/**
	 * @param String
	 *            partyName
	 * 
	 * @param String
	 *            introduction
	 * 
	 * @param String
	 *            function
	 */
	public void setParty(String partyName, String introduction, String function) {

		this.partyName = partyName;
		this.introduction = introduction;
		this.function = function;

	}

	/**
	 * @param String
	 *            location
	 */
	public void setPartyLocation(String location) {

		this.location = location;

	}

	public void setu_id(int u_id) {
		this.u_id = u_id;
	}

	public int getu_id() {
		return u_id;
	}

	public String getPartyName() {
		return partyName;
	}

	public String getIntroduction() {
		return introduction;
	}

	public String getFunction() {
		return function;
	}

	public String getLocation() {
		return location;
	}

	public String getQRcode() {
		return QRcode;
	}
}
