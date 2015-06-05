package com.party.ui;

import com.party.sqliteManager.DataBaseManager;
import com.party.sqliteManager.Party;
import com.party.ui.R;
import com.party.util.PartyConstant;
import com.party.util.ToastTask;
import com.party.web.CreatePartyTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreatePartyActivity extends Activity {

	// UI
	EditText edittext_set_partyName, edittext_set_introduction,
			edittext_set_function;
	Button button_save_party;

	// UI String
	String partyName = null;
	String partyIntroduction = null;
	String partyFunction = null;

	// Database
	DataBaseManager DBManager;
	Party party;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_party);
		InitCreatePartyUI();
		InitDatabaseForCreateParty();
	}

	/**
	 * @about initial user interface
	 */
	public void InitCreatePartyUI() {

		edittext_set_partyName = (EditText) findViewById(R.id.edittext_set_partyName);
		edittext_set_introduction = (EditText) findViewById(R.id.edittext_set_introduction);
		edittext_set_function = (EditText) findViewById(R.id.edittext_set_function);
		button_save_party = (Button) findViewById(R.id.button_save_party);

		button_save_party.setOnClickListener(buttonListener);

	}

	OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// 获取party Activity中的信息到party对象中
			if (getUIInformation()) {
				DBManager.addPartyInfortmation(party);// 把party的信息添加到数据库中
				DBManager.close();// 关闭数据库
				/**
				 * showParty启动和传递数据代码
				 */
				transmitBetweenActivity(party.getPartyName());

				String urlpath = PartyConstant.URI_SERVER
						+ PartyConstant.URI_DEIRECTORY_CREATE_PARTY;
				new CreatePartyTask(CreatePartyActivity.this, urlpath,
						PartyConstant.userPhoneNumber, party).execute();
				CreatePartyActivity.this.finish();
			}

		}
	};

	/**
	 * @about Activity间进行数据传递
	 * @param partyName
	 *            ，一个String字符串
	 */
	public void transmitBetweenActivity(String partyName) {

		Intent intent = new Intent(CreatePartyActivity.this,
				ShowPartyActivity.class);

		Bundle bundle = new Bundle();
		bundle.putString("party_name", partyName);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * @about 初始化数据库方面的变量（对象）
	 */
	public void InitDatabaseForCreateParty() {

		DBManager = new DataBaseManager(CreatePartyActivity.this);// create
																	// database
		party = new Party();// 初始化Party对象
	}

	/**
	 * @about 获取除了用户密码之外的用户信息。另外，用户的大部分个人信息在本地也是有备份的； 输入框中有任意一个为null，不把数据插入数据库
	 *        ，返回false； 输入框都成功输入数据时，返回true
	 */
	public boolean getUIInformation() {

		do {
			partyName = edittext_set_partyName.getText().toString();
			if (partyName.length() == 0) {
				// 如果partyName 为空，则输入栏获取焦点，等待输入
				edittext_set_partyName.setFocusable(true);
				edittext_set_partyName.setFocusableInTouchMode(true);
				edittext_set_partyName.requestFocus();
				// send a tip for input
				new ToastTask(CreatePartyActivity.this, "输入party名").execute();
				break;
			}
			partyIntroduction = edittext_set_introduction.getText().toString();
			if (partyIntroduction.length() == 0) {
				// 如果partyIntroduction为空，则partyIntroduction输入栏获取焦点，等待输入
				edittext_set_introduction.setFocusable(true);
				edittext_set_introduction.setFocusableInTouchMode(true);
				edittext_set_introduction.requestFocus();
				// send a tip for input
				new ToastTask(CreatePartyActivity.this, "输入party简介").execute();
				break;
			}
			partyFunction = edittext_set_function.getText().toString();
			if (partyFunction.length() == 0) {
				// 如果partyFunction为空，则partyFunction输入栏获取焦点，等待输入
				edittext_set_function.setFocusable(true);
				edittext_set_function.setFocusableInTouchMode(true);
				edittext_set_function.requestFocus();
				// send a tip for input
				new ToastTask(CreatePartyActivity.this, "输入party功能").execute();
				break;
			}
		} while (partyName.length() == 0 || partyIntroduction.length() == 0
				|| partyFunction.length() == 0);
		// 当partyNamer、partyFunction和partyIntroduction都不为空的的时候进行登录判断

		// 当三个EditText中有任意一个为null，不把数据插入数据库，返回false
		if (partyName.length() == 0 || partyIntroduction.length() == 0
				|| partyFunction.length() == 0) {
			return false;
		} else {
			party.setParty(partyName, partyIntroduction, partyFunction);
			/**
			 * 此处缺省发送数据内容到web server
			 */
			return true;
		}

	}

}
