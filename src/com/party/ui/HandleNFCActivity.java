package com.party.ui;

import com.party.util.PartyConstant;
import com.party.util.ToastTask;
import com.party.web.InPartyTask;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;

public class HandleNFCActivity extends Activity {
	@Override
	protected void onResume() {
		super.onResume();
		handleIntent();
		finish();
	}

	/**
	 * @About handle intent
	 * @return void
	 * 
	 */
	private void handleIntent() {
		resolveNdefMessagesIntent(getIntent());
	}

	/**
	 * @About resolve NdefMessage intent,get NdefMessage[] data
	 * @param intent
	 *            The extra intent
	 * @return void
	 * 
	 */
	void resolveNdefMessagesIntent(Intent intent) {

		String action = intent.getAction();
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

			NdefMessage[] messages = null;
			Parcelable[] rawMsgs = intent
					.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			if (rawMsgs != null) {
				messages = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					messages[i] = (NdefMessage) rawMsgs[i];

				}
			} else {
				// Unknown tag type
				byte[] empty = new byte[] {};
				NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
						empty, empty, empty);
				NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
				messages = new NdefMessage[] { msg };
			}

			processNDEFTag_MIME(messages);
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {

		} else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {

		} else {

			finish();
			return;
		}
	}

	/**
	 * @About resolve NdefMessage[] ,process NdefMessage data
	 * @param messages
	 * 
	 * @return void
	 * 
	 */
	private void processNDEFTag_MIME(NdefMessage[] messages) {
		// TODO Auto-generated method stub
		if (messages == null || messages.length == 0) {

			return;
		}

		for (int i = 0; i < messages.length; i++) {
			int length = messages[i].getRecords().length;

			NdefRecord[] records = messages[i].getRecords();
			for (int j = 0; j < length; j++) {
				for (NdefRecord record : records) {
					if (isMIMERecord(record)) {
						parseMIMERecord(record);
					}
				}
			}
		}
	}

	/**
	 * @About resolve NdefMessage[] ,process NdefMessage data
	 * 
	 * 
	 * @return return true or false,true represent MIME Record
	 * 
	 */
	public static boolean isMIMERecord(NdefRecord record) {
		if (record.getTnf() == NdefRecord.TNF_MIME_MEDIA) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @about ·ÖÎö MIME Record ÄÚÈÝ£¬
	 * @param NdefRecord
	 *            record
	 * @return void
	 */
	void parseMIMERecord(NdefRecord record) {

		byte[] payload = record.getPayload();
		new ToastTask(HandleNFCActivity.this, new String(payload)).execute();
		String[] split = new String(payload).split(";");
		if (split.length==2) {
			String uriPath=split[0];
			String partyId=split[1];
			String u_id=String.valueOf(PartyConstant.u_id);
			new InPartyTask(HandleNFCActivity.this, uriPath,partyId,u_id).execute();
		}else{
			Log.e("NFC handler", "payload error");
		}

	}

}
