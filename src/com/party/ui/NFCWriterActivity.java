package com.party.ui;

import com.party.nfc.ConfigNdefMessage;
import com.party.util.PartyConstant;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class NFCWriterActivity extends Activity {

	// NFC Declarations
	private NfcAdapter mNfcAdapter = null;
	private IntentFilter[] mFilters = null;
	private PendingIntent mPendingIntent = null;
	private String[][] mTechLists = null;
	private Context mContext;
	private NdefMessage ndefMessage = null;

	private byte[] payload = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcwriter);
		mContext = this;

		payload = configPayload( );
		checkNFCFunction(); // NFC Check
		initNFC();// Init NFC
	}

	@Override
	public void onResume() {
		super.onResume();
		enableForegroundDispatch();
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
			// get NFC object
			Tag detectTag = getIntent()
					.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			// validate that this tag can be written
			if (supportedTechs(detectTag.getTechList())) {

				// By AsyncTask Class
				ndefMessage = ConfigNdefMessage.getNdefMsg_from_MIME_MEDIA(
						payload, "image/jpeg", true);

				new WriteTask(NFCWriterActivity.this, ndefMessage, detectTag)
						.execute();
				// 用一个异步线程把数据写入到NFC卡中
			} else {
				Toast.makeText(mContext, "This tag type is not supported",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	@Override
	public void onNewIntent(Intent intent) {

		setIntent(intent);

	}

	@Override
	public void onPause() {
		super.onPause();

		disableForegroundDispatch();// disable TagWrite
	}

	/**
	 * @about 接收InpartyActivity发过来的信息
	 * @return Long,party ID
	 */
	public int receiveDatabetweenActivity() {

		Bundle bundle = this.getIntent().getExtras();
		int partyId = bundle.getInt("partyId");
		return partyId;
	}

	/**
	 * @about 将partyId转换成byte类型数据
	 * @param partyId
	 * @return payload
	 */
	public byte[] configPayload( ) {

		int partyId=30;
		String uripath=PartyConstant.URI_SERVER+PartyConstant.URI_DEIRECTORY_JOIN_PARTY_IN_ADNROID;
		String directory=";"+Integer.toString(partyId);
		byte[] payload = (uripath+directory).getBytes();

		return payload;
	}

	/**
	 * @about 初始化NFC，创建一个前台调度的过滤器
	 * 
	 * @return void
	 */
	private void initNFC() {

		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter ndefDetected = new IntentFilter(
				NfcAdapter.ACTION_NDEF_DISCOVERED);
		new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
		// ndef.addDataScheme("http");
		// Intent filters for writing to a tag
		mFilters = new IntentFilter[] { ndefDetected, };// just trying to find a
														// tag,not ndef or tech

		mTechLists = new String[][] { new String[] { Ndef.class.getName() },
				new String[] { NdefFormatable.class.getName() } };
	}

	/**
	 * @Title supportedTechs
	 * @Description: Check Support Technology
	 * @param @param techs
	 * @param @return
	 * @return boolean
	 * 
	 */
	public static boolean supportedTechs(String[] techs) {

		boolean ultralight = false;
		boolean nfcA = false;
		boolean ndef = false;
		for (String tech : techs) {
			if (tech.equals("android.nfc.tech.IsoDep")
					|| tech.equals("android.nfc.tech.MifareUltralight")) {

				ultralight = true;

			} else if (tech.equals("android.nfc.tech.NfcA")) {
				nfcA = true;

			} else if (tech.equals("android.nfc.tech.Ndef")
					|| tech.equals("android.nfc.tech.NdefFormatable")) {
				ndef = true;

			} else if (tech.equals("android.nfc.tech.MifareClassic")) {

			}
		}
		if (ultralight && nfcA && ndef) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * @about Enable foreground dispatch to the given Activity
	 * 
	 * @return void
	 */
	private void enableForegroundDispatch() {
		if (mNfcAdapter != null) {
			mNfcAdapter.enableForegroundDispatch(this, mPendingIntent,
					mFilters, mTechLists);
		}
	}

	/**
	 * @about Disable foreground dispatch to the given activity.
	 * 
	 * @return void
	 */
	private void disableForegroundDispatch() {
		if (mNfcAdapter != null) {
			mNfcAdapter.disableForegroundDispatch(this);
		}
	}

	/**
	 * @about NFC Function Check
	 */
	private void checkNFCFunction() {
		
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		// check the NFC adapter first
		if (mNfcAdapter == null) {
			// mTextView.setText("NFC apdater is not available");
			Toast.makeText(this, "此手机没有NFC功能", Toast.LENGTH_LONG).show();
			return;
		} else {
			if (!mNfcAdapter.isEnabled()) {
				Toast.makeText(this, "NFC 功能没打开", Toast.LENGTH_LONG).show();
			}
		}
	}

	static class WriteTask extends AsyncTask<Void, Void, Void> {
		Activity host = null;

		NdefMessage msg = null;

		Tag tag = null;

		String text = null;

		/**
		 * 
		 * @about 用一个异步任务完成NdefMessage写入NFC卡的操作
		 * 
		 */
		WriteTask(Activity host, NdefMessage msg, Tag tag) {

			this.host = host;
			this.msg = msg;
			this.tag = tag;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			int size = msg.toByteArray().length;

			try {
				Ndef ndef = Ndef.get(tag);

				if (ndef == null) {
					NdefFormatable formatable = NdefFormatable.get(tag);
					if (formatable != null) {
						try {
							formatable.connect();
							try {
								formatable.format(msg);
							} catch (Exception e) {

							}
						} catch (Exception e) {

						} finally {
							formatable.close();
						}
					} else {

					}
				} else {
					ndef.connect();

					try {
						if (!ndef.isWritable()) {
							text = "Tag is read-only";
						} else if (ndef.getMaxSize() < size) {

						} else {
							ndef.writeNdefMessage(msg);

						}
					} catch (Exception e) {

					} finally {
						ndef.close();
					}
				}
			} catch (Exception e) {

			}

			return (null);
		}

		@Override
		protected void onPostExecute(Void unused) {

			Toast.makeText(host, "Message is written tag!", Toast.LENGTH_SHORT)
					.show();
			host.finish(); // after writed, auto finish
		}
	}

}
