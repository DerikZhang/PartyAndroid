package com.party.util;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class ToastTask extends AsyncTask<Void, Void, Void> {
	Activity host = null;

	String text = null;

	public ToastTask(Activity host, String text) {

		this.host = host;
		this.text = text;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		Toast.makeText(host, text, Toast.LENGTH_SHORT).show();
	}

}
