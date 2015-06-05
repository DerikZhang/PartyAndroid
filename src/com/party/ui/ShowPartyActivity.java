package com.party.ui;

import com.party.sqliteManager.DataBaseManager;
import com.party.sqliteManager.Party;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPartyActivity extends Activity {

	// UI
	TextView textview_show_partyName;
	TextView textview_show_introduction;
	TextView textview_show_function;
	TextView textview_show_location;
	ImageView imageview_QRcode;
	Button button_join_thisParty;

	// Database
	DataBaseManager DBManager;
	Party party;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_party);

		initPartyUI();
		initDatabaseForParty();
		String partyName = receiveDatabetweenActivity();
		showPartyInformationToUI(partyName);
	}

	public void initPartyUI() {
		textview_show_partyName = (TextView) findViewById(R.id.textview_show_partyName);
		textview_show_introduction = (TextView) findViewById(R.id.textview_show_introduction);
		textview_show_function = (TextView) findViewById(R.id.textview_show_function);
		textview_show_location = (TextView) findViewById(R.id.textview_show_location);
		imageview_QRcode = (ImageView) findViewById(R.id.imageview_show_QRcode);
		button_join_thisParty = (Button) findViewById(R.id.button_join_thisParty);
		button_join_thisParty.setOnClickListener(buttonListener);
	}

	public void initDatabaseForParty() {
		
		DBManager = new DataBaseManager(getApplicationContext());
		party = new Party();
	}

	OnClickListener buttonListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ShowPartyActivity.this,
					InPartyActivity.class);
			ShowPartyActivity.this.startActivityForResult(intent, 100);
		}
	};

	/**
	 * @about 接收Activity间发送过来的数据
	 * @return String ,返回接收到的数据（partyName）
	 */
	public String receiveDatabetweenActivity() {
		Bundle bundle = this.getIntent().getExtras();
		String partyNanme = bundle.getString("party_name");
		return partyNanme;
	}

	/**
	 * @about 根据用户名返回party的信息
	 * @param partyName
	 */
	public void showPartyInformationToUI(String partyName) {
		party = DBManager.queryParty(partyName);

		// UI
		textview_show_partyName.setText(party.getPartyName());
		textview_show_introduction.setText(party.getIntroduction());
		textview_show_function.setText(party.getFunction());
	}
}
