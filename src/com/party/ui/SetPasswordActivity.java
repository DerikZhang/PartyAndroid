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
	 * @about 检查两次输入的密码是否相同
	 * @return
	 */
	public boolean checkpassword() {

		String password = null;
		String repassword = null;
		do {
			password = editText_setPassword.getText().toString();
			if (password.length() == 0) {
				// 如果phone number 为空，则输入栏获取焦点，等待输入
				editText_setPassword.setFocusable(true);
				editText_setPassword.setFocusableInTouchMode(true);
				editText_setPassword.requestFocus();
				// send a tip for input
				new ToastTask(SetPasswordActivity.this, "请输入密码").execute();
				break;
			}
			repassword = editText_setPassword_again.getText().toString();
			if (repassword.length() == 0) {
				// 如果password为空，则password输入栏获取焦点，等待输入
				editText_setPassword_again.setFocusable(true);
				editText_setPassword_again.setFocusableInTouchMode(true);
				editText_setPassword_again.requestFocus();
				// send a tip for input
				new ToastTask(SetPasswordActivity.this, "请再次输入密码").execute();
				break;
			}
			if (!password.equals(repassword)) {
				// 如果password为空，则password输入栏获取焦点，等待输入
				editText_setPassword_again.setFocusable(true);
				editText_setPassword_again.setFocusableInTouchMode(true);
				editText_setPassword_again.requestFocus();
				// send a tip for input
				new ToastTask(SetPasswordActivity.this, "请确保前后密码相同").execute();
				break;
			}
		} while (!password.equals(repassword));
		return password.equals(repassword);
	}

	/**
	 * @about setPassword处理按钮事件
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
	 * @about 发送密码到服务器，并等待服务器回应。这个功能用一个线程来实现
	 * @return true or false, true表示成功发送密码到服务器并得到服务器确认， false表示没有发送成功或等不到服务器确认
	 */
	public boolean sendAndHandlePassword() {

		nativeSendAndHandlePassword();
		return true;

	}

	/**
	 * @about 处理skip按钮事件
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
