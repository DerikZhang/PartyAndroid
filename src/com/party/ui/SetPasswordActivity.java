package com.party.ui;

import com.party.sqliteManager.DataBaseManager;
import com.party.sqliteManager.User;
import com.party.util.PartyConstant;
import com.party.util.ToastTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SetPasswordActivity extends Activity {

	private EditText editText_setPassword;
	private EditText editText_setPassword_again;
	private Button button_setPassword, button_skip;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_password);

		InitUISetPassword();

	}

	public void InitUISetPassword() {
		editText_setPassword = (EditText) findViewById(R.id.editText_setPassword);
		editText_setPassword_again = (EditText) findViewById(R.id.editText_setpassword_again);
		button_setPassword = (Button) findViewById(R.id.button_setPassword);
		button_skip = (Button) findViewById(R.id.button_skip);

		button_setPassword.setOnClickListener(buttonListener);
		button_skip.setOnClickListener(buttonListener);

	}

	OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.button_setPassword:
				setPasswordButtonhandler();
				break;
			case R.id.button_skip:
				skipButtonhandler();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * @about �����������������Ƿ���ͬ
	 * @return
	 */
	public boolean checkpassword() {

		String password = null;
		String repassword = null;
		do {
			password = editText_setPassword.getText().toString();
			if (password.length() == 0) {
				// ���phone number Ϊ�գ�����������ȡ���㣬�ȴ�����
				editText_setPassword.setFocusable(true);
				editText_setPassword.setFocusableInTouchMode(true);
				editText_setPassword.requestFocus();
				// send a tip for input
				new ToastTask(SetPasswordActivity.this, "����������").execute();
				break;
			}
			repassword = editText_setPassword_again.getText().toString();
			if (repassword.length() == 0) {
				// ���passwordΪ�գ���password��������ȡ���㣬�ȴ�����
				editText_setPassword_again.setFocusable(true);
				editText_setPassword_again.setFocusableInTouchMode(true);
				editText_setPassword_again.requestFocus();
				// send a tip for input
				new ToastTask(SetPasswordActivity.this, "���ٴ���������").execute();
				break;
			}
			if (!password.equals(repassword)) {
				// ���passwordΪ�գ���password��������ȡ���㣬�ȴ�����
				editText_setPassword_again.setFocusable(true);
				editText_setPassword_again.setFocusableInTouchMode(true);
				editText_setPassword_again.requestFocus();
				// send a tip for input
				new ToastTask(SetPasswordActivity.this, "��ȷ��ǰ��������ͬ").execute();
				break;
			}
		} while (!password.equals(repassword));
		return password.equals(repassword);
	}

	/**
	 * @about setPassword����ť�¼�
	 */
	public void setPasswordButtonhandler() {
		if (checkpassword()) {
			if (sendAndHandlePassword()) {

				Intent intent = new Intent(SetPasswordActivity.this,
						MenuActivity.class);
				SetPasswordActivity.this.startActivityForResult(intent, 100);
			}

		}
	}

	/**
	 * @about �������뵽�����������ȴ���������Ӧ�����������һ���߳���ʵ��
	 * @return true or false, true��ʾ�ɹ��������뵽���������õ�������ȷ�ϣ� false��ʾû�з��ͳɹ���Ȳ���������ȷ��
	 */
	public boolean sendAndHandlePassword() {

		nativeSendAndHandlePassword();
		return true;

	}

	/**
	 * @about ����skip��ť�¼�
	 */
	public void skipButtonhandler() {
		if (handleSkip()) {

			Intent intent = new Intent(SetPasswordActivity.this,
					MenuActivity.class);
			SetPasswordActivity.this.startActivityForResult(intent, 100);
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean handleSkip() {
		nativeSendAndHandlePassword();
		return true;

	}

	public void nativeSendAndHandlePassword() {
		User user = new User();
		String password = editText_setPassword.getText().toString();
		user.setUser(PartyConstant.userPhoneNumber, password);
		DataBaseManager DBManager = new DataBaseManager(getApplicationContext());
		DBManager.addUserInfortmationForRegister(user);

	}
}
