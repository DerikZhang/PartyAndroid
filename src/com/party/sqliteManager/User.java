package com.party.sqliteManager;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int u_id;
	String phone_number;
	String password;
	String user_name;
	String gender;
	int age;
	String job;
	String motto;
	int m_Status;

	public class Gender {
		public static final String MALE = "male";
		public static final String FEMALE = "female";
	}

	public User() {

		this.user_name = null;

	}

	// set user information
	public void setUser(String phone_number, String userName, String gender,
			int age, String job, String motto, int maritalStatus) {

		this.phone_number = phone_number;
		this.user_name = userName;
		this.gender = gender;
		this.age = age;
		this.job = job;
		this.motto = motto;
		this.m_Status = maritalStatus;
	}

	// register
	public void setUser(String phoneNumber, String password) {

		this.phone_number = phoneNumber;
		this.password = password;
	}

	public void setu_id(int u_id) {
		this.u_id = u_id;
	}

	public int getu_id() {
		return u_id;
	}

	public String getPhoneNumber() {
		return phone_number;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return user_name;
	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getJob() {
		return job;
	}

	public String getMotto() {
		return motto;
	}

	public int getMaritalStatus() {
		return m_Status;
	}

}
