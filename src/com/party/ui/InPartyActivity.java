package com.party.ui;

import com.party.ui.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InPartyActivity extends Activity {
	// UI
	Button button_reset_information, button_user_list;
	Button button_write_NFC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.in_party);
		InitInPartyUI();
	}

	public void InitInPartyUI() {
		button_reset_information = (Button) findViewById(R.id.button_reset_information);
		button_user_list = (Button) findViewById(R.id.button_user_list);

		button_reset_information
				.setOnClickListener(Listen_to_button_reset_information);
		button_user_list.setOnClickListener(Listen_to_button_user_list);

		button_write_NFC = (Button) findViewById(R.id.button_write_NFC);
		button_write_NFC.setOnClickListener(Listen_to_button_write_NFC);
	}

	OnClickListener Listen_to_button_reset_information = new OnClickListener() {

		@Override
		public void onClick(View v) {
			 
			Intent intent = new Intent(InPartyActivity.this, UserActivity.class);
			InPartyActivity.this.startActivityForResult(intent, 100);
		}
	};
	OnClickListener Listen_to_button_user_list = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};
	OnClickListener Listen_to_button_write_NFC = new OnClickListener() {

		@Override
		public void onClick(View v) {
		 
			//transmitBetweenActivity(nativePartyId());
			Intent intent = new Intent(InPartyActivity.this,
					NFCWriterActivity.class);
			startActivity(intent);
		}
	};

	/**
	 * @about 发送写入NFC标签的内容懂NFCWriterActivity
	 * @param partyId
	 */
	public void transmitBetweenActivity(int partyId) {

		Intent intent = new Intent(InPartyActivity.this,
				NFCWriterActivity.class);

		Bundle bundle = new Bundle();
		bundle.putInt("partyId", partyId);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * @about 生成本地调试的partyId
	 * @return partyId,本地的整型 party ID
	 */
	public int nativePartyId() {

		int partyId = 2;
		//int partyId = PartyConstant.p_id; //TODO
		return partyId;

	}
}
