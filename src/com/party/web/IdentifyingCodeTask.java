package com.party.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.party.util.APIErrorCode;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class IdentifyingCodeTask extends AsyncTask<Void, Void, Boolean> {

	Activity host = null;
	String urlpath;
	String phoneNumber;

	public IdentifyingCodeTask(Activity host, String urlpath, String phoneNumber) {

		this.host = host;
		this.urlpath = urlpath;
		this.phoneNumber = phoneNumber;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		return getvertificationCode(urlpath, phoneNumber);

	}

	@Override
	protected void onPostExecute(Boolean result) {

		super.onPostExecute(result);

		if (result) {

			Toast.makeText(host, "������֤��ɹ�", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(host, "������֤��ʧ��,��������", Toast.LENGTH_SHORT).show();
		}

	}

	public boolean getvertificationCode(String urlpath, String phoneNumber) {
		Log.d("getCode", "into verCode");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", phoneNumber);

		try {
			return readParse(urlpath, map);
		} catch (Exception e) {

			e.printStackTrace();
			Log.e("Login exception", e.toString());
		}

		return false;
	}

	private boolean readParse(String urlpath, Map<String, Object> map)
			throws Exception {

		HttpPost httpPost;
		// ����һ��NameValuePair���飬���ڴ洢�����͵Ĳ���
		List<NameValuePair> parames = new ArrayList<NameValuePair>();
		HttpClient client;
		HttpResponse httpResponse;
		String errorCode = null;
		parames.add(new BasicNameValuePair("phone_number", (String) map
				.get("phoneNumber")));// ��Ӳ��������绰����

		httpPost = new HttpPost(urlpath);
		httpPost.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));// ���ñ��뷽ʽ��ʹ��UTF-8����
		client = new DefaultHttpClient();
		httpResponse = client.execute(httpPost);// ����post��������һ��HttpResponse����
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			String result = EntityUtils.toString(httpResponse.getEntity());
			Log.d("result", result);
			JSONObject jsonObject = new JSONObject(result);
			errorCode = jsonObject.getString("errorCode");
			if (errorCode.equals(APIErrorCode.SEND_VERIFICATION_SUCCESS)) {

				Log.d("get verCode ", "Success:" + errorCode);
				return true;

			} else {

				Log.d("get verCode ", "failure:" + errorCode);

			}
		}

		return false;
	}
}
