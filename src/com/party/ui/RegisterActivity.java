package com.party.ui;

import com.party.sqliteManager.DataBaseManager;
import com.party.sqliteManager.User;
import com.party.ui.R;
import com.party.util.PartyConstant;
import com.party.util.ToastTask;
import com.party.web.IdentifyingCodeCallbackTask;
import com.party.web.IdentifyingCodeTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends Activity {

	// UI
	EditText edittext_register_phonenumber, edittext_register_code;
	Button button_register_getcode, button_register;

	private EditText editText_setPassword;
	private EditText editText_setPassword_again;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		InitRegisterUI();
	}

	public void InitRegisterUI() {
		
		edittext_register_phonenumber = (EditText) findViewById(R.id.edittext_register_phonenumber);
		edittext_register_code = (EditText) findViewById(R.id.edittext_register_code);
		button_register_getcode = (Button) findViewById(R.id.button_register_getcode);
		button_register = (Button) findViewById(R.id.button_register);

		editText_setPassword = (EditText) findViewById(R.id.editText_setPassword);
		editText_setPassword_again = (EditText) findViewById(R.id.editText_setpassword_again);
		
		button_register_getcode
				.setOnClickListener(Listen_to_button_register_getcode);
		button_register.setOnClickListener(Listen_to_button_register);
		button_register.setText(receiveDatabetweenActivity());

	}

	OnClickListener Listen_to_button_register_getcode = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (checkPhoneNumberFromUI()) {
				getIdentifyingCode();

			}
		}
	};
	OnClickListener Listen_to_button_register = new OnClickListener() {

		@Override
		public void onClick(View v) {

			registerHandler();
			// if (register()) {
			//
			// PartyConstant.userPhoneNumber = edittext_register_phonenumber
			// .getText().toString();
			// Intent intent = new Intent(RegisterActivity.this,
			// SetPasswordActivity.class);
			// RegisterActivity.this.startActivityForResult(intent, 200);
			// }
		}
	};

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 200) {
			finish();
		}
	};

	/**
	 * @about ����LoginActivity����������Ϣ
	 * @return String, ����button���ַ���
	 */
	public String receiveDatabetweenActivity() {

		Bundle bundle = this.getIntent().getExtras();
		String partyId = bundle.getString("buttoSetText");
		return partyId;
	}

	/**
	 * 
	 * @return true or flase,true �ֻ������ʽ��ȷ������false�ֻ������ʽ����
	 */
	public boolean checkPhoneNumberFromUI() {
		String phoneNumber = null;

		phoneNumber = edittext_register_phonenumber.getText().toString();
		if (phoneNumber.length() != PartyConstant.PHONE_NUMBER_LENGTH) {
			// ���phone number Ϊ�գ�����������ȡ���㣬�ȴ�����
			edittext_register_phonenumber.setFocusable(true);
			edittext_register_phonenumber.setFocusableInTouchMode(true);
			edittext_register_phonenumber.requestFocus();
			// send a tip for input
			new ToastTask(RegisterActivity.this, "��������ȷ�ֻ�����").execute();
			return false;
		}

		return true;

	}

	/**
	 * 
	 * @return true or false,true��֤���ʽ��ȷ������false��֤���ʽ����
	 */
	public boolean checkidentifyingCodeFromUI() {
		String identifyingCode = null;

		identifyingCode = edittext_register_code.getText().toString();
		if (identifyingCode.length() == 0) {
			// ���phone number Ϊ�գ�����������ȡ���㣬�ȴ�����
			edittext_register_code.setFocusable(true);
			edittext_register_code.setFocusableInTouchMode(true);
			edittext_register_code.requestFocus();
			// send a tip for input
			new ToastTask(RegisterActivity.this, "��������֤��").execute();
			return false;
		}
		if (identifyingCode.length() != PartyConstant.IDENTIFYING_CODE_LENGTH) {
			// ���phone number Ϊ�գ�����������ȡ���㣬�ȴ�����
			edittext_register_code.setFocusable(true);
			edittext_register_code.setFocusableInTouchMode(true);
			edittext_register_code.requestFocus();
			// send a tip for input
			new ToastTask(RegisterActivity.this, "��ȷ����֤����ȷ").execute();
			return false;
		}

		return true;

	}

	/**
	 * @about �������������֤�룬���͵��༭�ı����Ӧ���ֻ���, ���������Ҫʹ��һ���߳���ʵ��
	 * @return
	 */
	public boolean getIdentifyingCode() {
		String phoneNumber = edittext_register_phonenumber.getText().toString();

		String urlpath = PartyConstant.URI_SERVER
				+ PartyConstant.URI_DIRECTORY_REGISTER;
		// ��ȡ��ȡ��֤��
		new IdentifyingCodeTask(RegisterActivity.this, urlpath, phoneNumber)
				.execute();

		return false;
	}

	public void registerHandler() {

		// ȷ���û���������ȷ�ֻ�����
		if (checkPhoneNumberFromUI()) {
			// ȷ���û�������������֤�룬���ٳ���һ��
			if (checkidentifyingCodeFromUI()&&checkpassword()) {
				register();
			}
		}

	}

	/**
	 * @about ʹ���߳���������֤�뵽�������Լ��ȴ���������Ӧ
	 */
	public void register() {
		String phoneNumber = edittext_register_phonenumber.getText().toString();
		String urlpath = PartyConstant.URI_SERVER
				+ PartyConstant.URI_DIRECTORY_SIGN;// TODO ���URI��ʲô
		String identifyingCode = edittext_register_code.getText().toString();
		String password = editText_setPassword.getText().toString();
		
		new IdentifyingCodeCallbackTask(RegisterActivity.this, urlpath,
				phoneNumber, identifyingCode,password).execute();

	}
	
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
				new ToastTask(RegisterActivity.this, "����������").execute();
				break;
			}
			repassword = editText_setPassword_again.getText().toString();
			if (repassword.length() == 0) {
				// ���passwordΪ�գ���password��������ȡ���㣬�ȴ�����
				editText_setPassword_again.setFocusable(true);
				editText_setPassword_again.setFocusableInTouchMode(true);
				editText_setPassword_again.requestFocus();
				// send a tip for input
				new ToastTask(RegisterActivity.this, "���ٴ���������").execute();
				break;
			}
			if (!password.equals(repassword)) {
				// ���passwordΪ�գ���password��������ȡ���㣬�ȴ�����
				editText_setPassword_again.setFocusable(true);
				editText_setPassword_again.setFocusableInTouchMode(true);
				editText_setPassword_again.requestFocus();
				// send a tip for input
				new ToastTask(RegisterActivity.this, "��ȷ��ǰ��������ͬ").execute();
				break;
			}
		} while (!password.equals(repassword));
		return password.equals(repassword);
	}
	
	public void nativeSendAndHandlePassword() {
		User user = new User();
		String password = editText_setPassword.getText().toString();
		user.setUser(PartyConstant.userPhoneNumber, password);
		DataBaseManager DBManager = new DataBaseManager(getApplicationContext());
		DBManager.addUserInfortmationForRegister(user);

	}
}
