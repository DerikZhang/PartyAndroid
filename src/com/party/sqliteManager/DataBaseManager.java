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
	 * @about 设置用户信息，在注册的时候
	 *        <code>configure user information for Register  </code>
	 * @param User
	 */
	public void addUserInfortmationForRegister(User user) {
		db.beginTransaction(); // 开始事务
		try {

			db.execSQL(
					"INSERT INTO User VALUES(null,?,?, null,null, null,null, null,null)",
					new Object[] { user.phone_number, user.password });

			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * @about Add party information
	 * @param party
	 *            Party对象
	 */
	public void addPartyInfortmation(Party party) {
		db.beginTransaction(); // 开始事务
		try {

			db.execSQL("INSERT INTO Party VALUES(null,?,?, ?,null, null)",
					new Object[] { party.partyName, party.introduction,
							party.function });

			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
		}
	}

	/**
	 * @about 向PartyMember表里面添加party成员信息，partyId、用户名、手机号码
	 * @param partyMember
	 *            ,PartyMember对象
	 */
	public void addPartyMemberInformation(PartyMember partyMember) {
		db.beginTransaction(); // 开始事务
		try {

			db.execSQL("INSERT INTO PartyMember VALUES(null,?,?, ? )", new Object[] {
					partyMember.partyId, partyMember.userName,
					partyMember.phoneNumber });

			db.setTransactionSuccessful(); // 设置事务成功完成
		} finally {
			db.endTransaction(); // 结束事务
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
	 * @about 根据party的名字筛选出party的信息
	 * @param partyName
	 *            ， 表示party name 的字符串
	 * @return party ，一个Party类的对象
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
			 * 暂时缺省location 和 QRcode 部分
			 */

		}
		c.close();
		return party;
	}

	/**
	 * @param sql
	 *            ,一个SQL语句
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
