package com.party.ui;

import com.party.sqliteManager.DataBaseManager;
import com.party.sqliteManager.MaritalStatus;
import com.party.sqliteManager.User;
import com.party.ui.R;
import com.party.util.PartyConstant;
import com.party.util.ToastTask;
import com.party.web.EditUserInformationTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserActivity extends Activity {

	// UI
	TextView textview_show_phonenumber;
	EditText edittext_set_userName;
	RadioGroup radiogroup_set_gender;
	RadioButton radiobutton_male, radiobutton_female;
	EditText edittext_set_age;
	EditText edittext_set_job;
	EditText edittext_set_motto;
	RadioGroup radiogroup_set_maritalStatus;
	RadioButton radiobutton_single, radiobutton_married, radiobutton_divorced,
			radiobutton_widowed;
	Button button_save_information;

	// Database
	DataBaseManager DBManager;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_information);

		initUserUI();
		initDatabaseForUser();

		showUserInformationToUI();
	}

	@Override
	protected void onResume() {

		super.onResume();
		// phoneNumber=UserActivity.this.getIntent().getDataString();
	}

	public void initUserUI() {
		textview_show_phonenumber = (TextView) findViewById(R.id.textview_show_phonenumber);
		edittext_set_userName = (EditText) findViewById(R.id.edittext_set_userName);

		// gender RadioGroup
		radiogroup_set_gender = (RadioGroup) findViewById(R.id.radiogroup_set_gender);
		radiobutton_male = (RadioButton) findViewById(R.id.radiobutton_male);
		radiobutton_female = (RadioButton) findViewById(R.id.radiobutton_female);

		edittext_set_age = (EditText) findViewById(R.id.edittext_set_age);
		edittext_set_job = (EditText) findViewById(R.id.edittext_set_job);
		edittext_set_motto = (EditText) findViewById(R.id.edittext_set_motto);

		// mStatus RadioGroup
		radiogroup_set_maritalStatus = (RadioGroup) findViewById(R.id.radiogroup_set_maritalStatus);
		radiobutton_single = (RadioButton) findViewById(R.id.radiobutton_single);
		radiobutton_married = (RadioButton) findViewById(R.id.radiobutton_married);
		radiobutton_divorced = (RadioButton) findViewById(R.id.radiobutton_divorced);
		radiobutton_widowed = (RadioButton) findViewById(R.id.radiobutton_widowed);

		button_save_information = (Button) findViewById(R.id.button_save_information);

		button_save_information
				.setOnClickListener(Listen_to_button_save_information);
	}

	OnClickListener Listen_to_button_save_information = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (checkUIInformation()) {
				getUserInformationFromUI();
				DBManager.updateUserInfortmation(user);
				DBManager.close();

				String urlpath = PartyConstant.URI_SERVER
						+ PartyConstant.URI_DEIRECTORY_EDIT_USER_INFO;
				new EditUserInformationTask(UserActivity.this, urlpath, user)
						.execute();
				// finish();
			}

		}
	};

	public void initDatabaseForUser() {
		DBManager = new DataBaseManager(this);
		user = new User();
	}

	/**
	 * @about 根据数据库显示用户的个人信息
	 */
	public void showUserInformationToUI() {
		String number = PartyConstant.userPhoneNumber;
		user = DBManager.queryUser(number);

		// UI
		textview_show_phonenumber.setText(number);
		if (user.getUserName() != null) {

			edittext_set_userName.setText(user.getUserName());
			setGender(user.getGender());
			edittext_set_age.setText(Integer.valueOf(user.getAge()).toString());
			edittext_set_job.setText(user.getJob());
			edittext_set_motto.setText(user.getMotto());
			setMaritalStatus(user.getMaritalStatus());
		}

	}

	/**
	 * @about 根据根据数据库的信息显示（设置）用户情感状态
	 * @param gender
	 *            ，一个String字符串，表示用户性别
	 */
	public void setGender(String gender) {

		if (gender.equals(User.Gender.MALE) ? true : false) {
			radiobutton_male.setChecked(true);
		} else {
			radiobutton_female.setChecked(true);
		}
	}

	/**
	 * @about 根据数据库的信息显示（设置）用户情感状态
	 * @param maritalStatus
	 *            ，一个integer类型的数，表示用户当前的情感状态
	 */
	public void setMaritalStatus(int maritalStatus) {

		switch (maritalStatus) {
		case MaritalStatus.SINGLE:
			radiobutton_single.setChecked(true);
			break;
		case MaritalStatus.MARRIED:
			radiobutton_married.setChecked(true);
		case MaritalStatus.DOVORCED:
			radiobutton_divorced.setChecked(true);
		case MaritalStatus.WIDOWED:
			radiobutton_widowed.setChecked(true);
		default:

		}
	}

	public boolean checkUIInformation() {
		String phoneNumber = null;

		phoneNumber = edittext_set_userName.getText().toString();
		if (phoneNumber.length() == 0) {
			// 如果phone number 为空，则输入栏获取焦点，等待输入
			edittext_set_userName.setFocusable(true);
			edittext_set_userName.setFocusableInTouchMode(true);
			edittext_set_userName.requestFocus();
			// send a tip for input
			new ToastTask(UserActivity.this, "用户名不能为空").execute();
			return false;
		}

		return true;

	}

	/**
	 * @about 从用户界面获取用户个人信息,并封装到一个User对象中
	 */
	public void getUserInformationFromUI() {
		String userName = edittext_set_userName.getText().toString();
		String gender = getGender();
		int age = Integer.valueOf(edittext_set_age.getText().toString());
		String job = edittext_set_job.getText().toString();
		String motto = edittext_set_motto.getText().toString();
		int mStatus = getMaritalStatus();
		user.setUser(PartyConstant.userPhoneNumber, userName, gender, age, job,
				motto, mStatus);
	}

	/**
	 * @about 获取用户的性别
	 * @return String ，male or female
	 */
	public String getGender() {

		int radioButtonId = radiogroup_set_gender.getCheckedRadioButtonId();
		int male = radiobutton_male.getId();
		return radioButtonId == male ? User.Gender.MALE : User.Gender.FEMALE;
	}

	/**
	 * @about 获取当前用户的情感状态
	 * @return integer ，表示情感状态
	 */
	public int getMaritalStatus() {

		int radioButtonId = radiogroup_set_maritalStatus
				.getCheckedRadioButtonId();

		switch (radioButtonId) {
		case R.id.radiobutton_single:
			return MaritalStatus.SINGLE;
		case R.id.radiobutton_married:
			return MaritalStatus.MARRIED;
		case R.id.radiobutton_divorced:
			return MaritalStatus.DOVORCED;
		case R.id.radiobutton_widowed:
			return MaritalStatus.WIDOWED;
		default:
			return MaritalStatus.SINGLE;
		}
	}
}
