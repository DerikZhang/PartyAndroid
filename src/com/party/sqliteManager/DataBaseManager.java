package com.party.sqliteManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {
	DataBaseHelper DBHelper;
	SQLiteDatabase db;

	public DataBaseManager(Context context) {

		DBHelper = new DataBaseHelper(context);

		DBHelper.createDataBase();
		// do nothing - if database already exist
		DBHelper.openDataBase();
		db = DBHelper.getDataBase();
	}

	public void close() {
		DBHelper.close();
		db.close();
	}

	/**
	 * @about �����û���Ϣ����ע���ʱ��
	 *        <code>configure user information for Register  </code>
	 * @param User
	 */
	public void addUserInfortmationForRegister(User user) {
		db.beginTransaction(); // ��ʼ����
		try {

			db.execSQL(
					"INSERT INTO User VALUES(null,?,?, null,null, null,null, null,null)",
					new Object[] { user.phone_number, user.password });

			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	/**
	 * @about Add party information
	 * @param party
	 *            Party����
	 */
	public void addPartyInfortmation(Party party) {
		db.beginTransaction(); // ��ʼ����
		try {

			db.execSQL("INSERT INTO Party VALUES(null,?,?, ?,null, null)",
					new Object[] { party.partyName, party.introduction,
							party.function });

			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	/**
	 * @about ��PartyMember���������party��Ա��Ϣ��partyId���û������ֻ�����
	 * @param partyMember
	 *            ,PartyMember����
	 */
	public void addPartyMemberInformation(PartyMember partyMember) {
		db.beginTransaction(); // ��ʼ����
		try {

			db.execSQL("INSERT INTO PartyMember VALUES(null,?,?, ? )", new Object[] {
					partyMember.partyId, partyMember.userName,
					partyMember.phoneNumber });

			db.setTransactionSuccessful(); // ��������ɹ����
		} finally {
			db.endTransaction(); // ��������
		}
	}

	/**
	 * @about update user information
	 * @param user
	 */
	public void updateUserInfortmation(User user) {
		ContentValues cv = new ContentValues();
		cv.put("userName", user.user_name);
		cv.put("gender", user.gender);
		cv.put("age", user.age);
		cv.put("job", user.job);
		cv.put("motto", user.motto);
		cv.put("maritalStatus", user.m_Status);
		db.update("User", cv, "phoneNumber = ?",
				new String[] { user.phone_number });
	}

	/**
	 * @about update party's information
	 * 
	 * @param Party
	 */
	public void updatePartyInformation(Party party) {
		ContentValues cv = new ContentValues();
		cv.put("partyName", party.partyName);
		cv.put("introduction", party.introduction);
		cv.put("function", party.function);
		db.update("Party", cv, "partyName = ?",
				new String[] { party.partyName });
	}

	/**
	 * @about update party's location
	 * @param party
	 *            , Party object
	 */
	public void updatePartyLocation(Party party) {
		ContentValues cv = new ContentValues();
		cv.put("location", party.location);
		db.update("Party", cv, "partyName = ?",
				new String[] { party.partyName });
	}

	/**
	 * @about user phone number
	 * 
	 * @param String
	 * 
	 * @return User
	 */
	public User queryUser(String phoneNumber) {
		User user = new User();
		user.phone_number = phoneNumber;
		String sql = "select User.userName,User.gender,User.age,User.job,User.motto,User.maritalStatus from User where phoneNumber="
				+ phoneNumber;
		Cursor c = queryTheCursor(sql);
		while (c.moveToNext()) {
			user.user_name = c.getString(c.getColumnIndex("userName"));
			user.gender = c.getString(c.getColumnIndex("gender"));
			user.age = c.getInt(c.getColumnIndex("age"));
			user.job = c.getString(c.getColumnIndex("job"));
			user.motto = c.getString(c.getColumnIndex("motto"));
			user.m_Status = c.getInt(c.getColumnIndex("maritalStatus"));
		}
		c.close();
		return user;
	}

	/**
	 * @about ����party������ɸѡ��party����Ϣ
	 * @param partyName
	 *            �� ��ʾparty name ���ַ���
	 * @return party ��һ��Party��Ķ���
	 */
	public Party queryParty(String partyName) {

		Party party = new Party();
		party.partyName = partyName;
		String sql = "select introduction,function from Party where partyName=\""
				+ partyName + "\"";
		Cursor c = queryTheCursor(sql);
		while (c.moveToNext()) {
			party.introduction = c.getString(c.getColumnIndex("introduction"));
			party.function = c.getString(c.getColumnIndex("function"));

			/**
			 * ��ʱȱʡlocation �� QRcode ����
			 */

		}
		c.close();
		return party;
	}

	/**
	 * @param sql
	 *            ,һ��SQL���
	 * @return c,a cursor object
	 */
	public Cursor queryTheCursor(String sql) {
		// Cursor c = db.rawQuery("SELECT * FROM User", null);

		Cursor c = db.rawQuery(sql, null);
		return c;
	}

	/**
	 * @about query user existent or non-existent and get user phone number
	 * @param phoneNumber
	 * @param password
	 * @return user,The User class object
	 */
	public User queryUser(String phoneNumber, String password) {
		User user = new User();

		Cursor c = queryTheCursor(phoneNumber, password);
		while (c.moveToNext()) {
			user.phone_number = c.getString(c.getColumnIndex("phoneNumber"));
		}
		c.close();
		return user;
	}

	/**
	 * @about query user existent or non-existent
	 * @param phoneNumber
	 *            The String phone number
	 * @param password
	 *            The String password
	 * @return c, a cursor object
	 */
	public Cursor queryTheCursor(String phoneNumber, String password) {
		// Cursor c = db.rawQuery("SELECT * FROM User", null);
		String sql = "select phoneNumber from User where phoneNumber=\""
				+ phoneNumber + "\" and password=\"" + password + "\"";
		Cursor c = db.rawQuery(sql, null);
		return c;

	}

}
