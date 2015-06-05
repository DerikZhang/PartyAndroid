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
			// ��ȡparty Activity�е���Ϣ��party������
			if (getUIInformation()) {
				DBManager.addPartyInfortmation(party);// ��party����Ϣ��ӵ����ݿ���
				DBManager.close();// �ر����ݿ�
				/**
				 * showParty�����ʹ������ݴ���
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
	 * @about Activity��������ݴ���
	 * @param partyName
	 *            ��һ��String�ַ���
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
	 * @about ��ʼ�����ݿⷽ��ı���������
	 */
	public void InitDatabaseForCreateParty() {

		DBManager = new DataBaseManager(CreatePartyActivity.this);// create
																	// database
		party = new Party();// ��ʼ��Party����
	}

	/**
	 * @about ��ȡ�����û�����֮����û���Ϣ�����⣬�û��Ĵ󲿷ָ�����Ϣ�ڱ���Ҳ���б��ݵģ� �������������һ��Ϊnull���������ݲ������ݿ�
	 *        ������false�� ����򶼳ɹ���������ʱ������true
	 */
	public boolean getUIInformation() {

		do {
			partyName = edittext_set_partyName.getText().toString();
			if (partyName.length() == 0) {
				// ���partyName Ϊ�գ�����������ȡ���㣬�ȴ�����
				edittext_set_partyName.setFocusable(true);
				edittext_set_partyName.setFocusableInTouchMode(true);
				edittext_set_partyName.requestFocus();
				// send a tip for input
				new ToastTask(CreatePartyActivity.this, "����party��").execute();
				break;
			}
			partyIntroduction = edittext_set_introduction.getText().toString();
			if (partyIntroduction.length() == 0) {
				// ���partyIntroductionΪ�գ���partyIntroduction��������ȡ���㣬�ȴ�����
				edittext_set_introduction.setFocusable(true);
				edittext_set_introduction.setFocusableInTouchMode(true);
				edittext_set_introduction.requestFocus();
				// send a tip for input
				new ToastTask(CreatePartyActivity.this, "����party���").execute();
				break;
			}
			partyFunction = edittext_set_function.getText().toString();
			if (partyFunction.length() == 0) {
				// ���partyFunctionΪ�գ���partyFunction��������ȡ���㣬�ȴ�����
				edittext_set_function.setFocusable(true);
				edittext_set_function.setFocusableInTouchMode(true);
				edittext_set_function.requestFocus();
				// send a tip for input
				new ToastTask(CreatePartyActivity.this, "����party����").execute();
				break;
			}
		} while (partyName.length() == 0 || partyIntroduction.length() == 0
				|| partyFunction.length() == 0);
		// ��partyNamer��partyFunction��partyIntroduction����Ϊ�յĵ�ʱ����е�¼�ж�

		// ������EditText��������һ��Ϊnull���������ݲ������ݿ⣬����false
		if (partyName.length() == 0 || partyIntroduction.length() == 0
				|| partyFunction.length() == 0) {
			return false;
		} else {
			party.setParty(partyName, partyIntroduction, partyFunction);
			/**
			 * �˴�ȱʡ�����������ݵ�web server
			 */
			return true;
		}

	}

}
