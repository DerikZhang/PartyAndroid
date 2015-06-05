package com.party.ui;

import com.party.ui.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity {

	Button button_create_party,button_join_party,button_setInformation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		InitMenuUI();
	}
	public void InitMenuUI(){
		button_create_party=(Button)findViewById(R.id.button_create_party);
		button_join_party=(Button)findViewById(R.id.button_join_party);
		button_setInformation=(Button)findViewById(R.id.button_setInformation);
		
		button_create_party.setOnClickListener(Listen_to_button_create_party);
		button_join_party.setOnClickListener(Listen_to_button_join_party);
		button_setInformation.setOnClickListener(Listen_to_button_setInformation);
	}
	OnClickListener Listen_to_button_create_party=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MenuActivity.this,
					CreatePartyActivity.class);
			MenuActivity.this.startActivityForResult(intent, 100);
		}
	};
	OnClickListener Listen_to_button_join_party=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MenuActivity.this,
					InPartyActivity.class);
			MenuActivity.this.startActivityForResult(intent, 100);
		}
	};
	OnClickListener Listen_to_button_setInformation=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MenuActivity.this,
					UserActivity.class);
			MenuActivity.this.startActivityForResult(intent, 100);
		}
	};
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//暂时不作处理所有的Activity 都使用Standard style 启动
		
	};
}
