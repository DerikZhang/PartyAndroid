package com.party.sqliteManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	// The Android's default system path of your application database.
	// private static String DB_PATH = "/data/data/YOUR_PACKAGE/databases/";
	private static String DB_PATH = null;// "/data/data/com.party.ui/databases/";
	private static String DB_NAME = "party.sqlite";

	private SQLiteDatabase myDataBase;

	private final Context myContext;

	/**
	 * Constructor Takes and keeps a reference of the passed context in order to
	 * access to the application assets and resources.
	 * 
	 * @param context
	 */
	public DataBaseHelper(Context context) {

		super(context, DB_NAME, null, 1);
		this.myContext = context;
		// get database path
		DB_PATH = context.getDatabasePath(DB_NAME).getPath();

	}

	/**
	 * Creates a empty database on the system and rewrites it with your own
	 * database.
	 * */
	public void createDataBase() {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			copyDataBase();
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH;// + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

			// database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() {

		try {
			// Open your local db as the input stream
			InputStream myInput = myContext.getAssets().open(DB_NAME);

			// Path to the just created empty db
			String outFileName = DB_PATH;// + DB_NAME;

			// Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			// Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// transfer bytes from the inputfile to the outputfile

	}

	/**
	 * @about open a database,database path: myPath = DB_PATH
	 */
	public void openDataBase() {

		// Open the database
		String myPath = DB_PATH;// + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
		/*
		 * OPEN_READONLY，OPEN_READWRITE的选择要看数据库有没有数据，
		 * 如果数据库没有数据就不能成功打开数据库，抛出异常（令人抓狂的异常） 本app中数据库需要读写，所以选择OPEN_READWRITE
		 */

	}

	/**
	 * 
	 * @return SQLiteDatabase 返回一个已经打开的数据库对象
	 */
	public SQLiteDatabase getDataBase() {
		return myDataBase;
	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	 

}