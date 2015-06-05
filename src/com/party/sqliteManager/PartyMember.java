package com.party.sqliteManager;

public class PartyMember {
	
	int partyId;
	String userName;
	String phoneNumber;

	public PartyMember() {
		 
	}

	public void setPartyMember(int partyId, String userName, String phoneNumber) {
		
		this.partyId = partyId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
	}

	public int getPartyId() {
		return partyId;
	}

	public String getUserName() {
		return userName;
	}

	public String getphoneNumber() {
		return phoneNumber;
	}
}
