package com.party.ui;

import com.party.sqliteManager.DataBaseManager;
import com.party.sqliteManager.User;
import com.party.ui.R;
import com.party.util.PartyConstant;
import com.party.util.ToastTask;
import com.party.web.LoginTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	// UI
	Button button_gotoRegister, button_login_getcode, button_login;
	EditText edittext_login_phonenumber, edittext_password;
	// edittext_login_code;

	// Database
	DataBaseManager DBManager;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		InitLoginUI();
		InitDatabaseForLogin();
	}

	public void InitLoginUI() {

		button_gotoRegister = (Button) findViewById(R.id.button_gotoRegister);
		button_login_getcode = (Button) findViewById(R.id.button_login_getcode);
		button_login = (Button) findViewById(R.id.button_login);

		edittext_login_phonenumber = (EditText) findViewById(R.id.edittext_login_phonenumber);
		edittext_password = (EditText) findViewById(R.id.edittext_password);
		// edittext_login_code = (EditText)
		// findViewById(R.id.edittext_login_code);

		button_gotoRegister.setOnClickListener(Listen_to_button_gotoRegister);
		button_login_getcode.setOnClickListener(Listen_to_button_login_getcode);
		button_login.setOnClickListener(Listen_to_button_login);
	}

	/*
	 * @about register user
	 */
	OnClickListener Listen_to_button_gotoRegister = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("buttoSetText", "ע��");
			intent.putExtras(bundle);
			LoginActivity.this.startActivity(intent);
		}
	};
	/*
	 * @about get verification code
	 */
	OnClickListener Listen_to_button_login_getcode = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("buttoSetText", "��½");
			intent.putExtras(bundle);
			LoginActivity.this.startActivity(intent);
		}
	};
	/*
	 * about Login
	 */
	OnClickListener Listen_to_button_login = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			buttonHandler();

		}
	};

	public void InitDatabaseForLogin() {
		DBManager = new DataBaseManager(LoginActivity.this);
		user = new User();
	}

	public boolean getLoginInformation() {

		String phoneNumber = null;
		String password = null;

		do {
			phoneNumber = edittext_login_phonenumber.getText().toString();
			if (phoneNumber.length() != PartyConstant.PHONE_NUMBER_LENGTH) {
				// ���phone number Ϊ�գ�����������ȡ���㣬�ȴ�����
				edittext_login_phonenumber.setFocusable(true);
				edittext_login_phonenumber.setFocusableInTouchMode(true);
				edittext_login_phonenumber.requestFocus();
				// send a tip for input
				new ToastTask(LoginActivity.this, "��������ȷ�ֻ�����").execute();
				break;
			}
			password = edittext_password.getText().toString();
			if (password.length() == 0) {
				// ���passwordΪ�գ���password��������ȡ���㣬�ȴ�����
				edittext_password.setFocusable(true);
				edittext_password.setFocusableInTouchMode(true);
				edittext_password.requestFocus();
				// send a tip for input
				new ToastTask(LoginActivity.this, "����������").execute();
				break;
			}
		} while (phoneNumber.length() != PartyConstant.PHONE_NUMBER_LENGTH
				|| password.length() == 0);
		// ��phone number��password����Ϊ�յĵ�ʱ����е�¼�ж�

		/*
		 * phoneNumber not null ,and password not null,handle to Dababase else
		 * continue
		 */
		if (phoneNumber.length() != PartyConstant.PHONE_NUMBER_LENGTH
				|| password.length() == 0) {
			return false;
		} else {
			
			String urlpath = PartyConstant.URI_SERVER+PartyConstant.URI_DIRECTORY_LOGIN;
			new LoginTask(LoginActivity.this, urlpath, phoneNumber, password).execute();
			
			// �˺���Ϊ���ص���ʹ�ã����ӷ�����֮���ʹ���Ĳ���һ�������õķ�����һ��
			//user = DBManager.queryUser(phoneNumber, password);
			// �ж��û��Ƿ����
			if (user.getPhoneNumber() != null)
			// user.phoneNumberΪnull��������user.getPhoneNumber()��length(),�����ָ���쳣
			{
				return true;
			} else {
				return false;
			}
		}

	}

	public void buttonHandler() {

		// ��ȡ��������Ϣ���ж��������ǲ��ǿգ�Ϊ�ջ�ȡ����
		if (getLoginInformation()) {

			/**
			 * �洢�ɹ���½���û��˺ţ�phone number��
			 */
			PartyConstant.userPhoneNumber = user.getPhoneNumber();

			Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
			LoginActivity.this.startActivity(intent);
		}

	}
	 
}
