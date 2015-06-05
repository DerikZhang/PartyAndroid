package com.party.nfc;

import java.nio.charset.Charset;

import android.annotation.SuppressLint;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

public class ConfigNdefMessage {
	/**
	 * @About create a TNF_MIME_MEDIA NDEF record
	 * @param payLoad
	 *            ,the MIME data
	 * @param mimeType
	 *            ,the MIME Type
	 * @param flagAddAAR
	 *            , true means add AAR
	 * @return NdefMessage
	 * 
	 */
	@SuppressLint("NewApi")
	public static NdefMessage getNdefMsg_from_MIME_MEDIA(byte[] payLoad,
			String mimeType, boolean flagAddAAR) {

		byte[] payLoadBytes = payLoad;

		NdefRecord mimeRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				mimeType.getBytes(Charset.forName("US-ASCII")), new byte[0],
				payLoadBytes);

		if (flagAddAAR) {
			return new NdefMessage(new NdefRecord[] { mimeRecord,
					NdefRecord.createApplicationRecord("com.party.ui") });
		} else {
			return new NdefMessage(new NdefRecord[] { mimeRecord });
		}
	}
}
