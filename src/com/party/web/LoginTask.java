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

import com.party.ui.MenuActivity;
import com.party.util.APIErrorCode;
import com.party.util.PartyConstant;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {
	Activity host = null;
	String urlpath;
	String phoneNumber;
	String password;

	boolean successed = false;

	public LoginTask(Activity host, String urlpath, String phoneNumber,
			String password) {

		this.host = host;
		this.urlpath = urlpath;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		return login(urlpath, phoneNumber, password);
	}

	@Override
	protected void onPostExecute(Boolean result) {

		super.onPostExecute(result);
		if (result) {

			PartyConstant.userPhoneNumber = phoneNumber;
			Intent intent = new Intent(host, MenuActivity.class);
			host.startActivity(intent);
			// finish LoginActivity
			// host.finish();
		}

	}

	public boolean login(String urlpath, String phoneNumber, String password) {
		Log.d("Login", "set password");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("password", password);

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
		parames.add(new BasicNameValuePair("password", (String) map
				.get("password")));// TODO �����ַ������������ĸ�

		httpPost = new HttpPost(urlpath);
		httpPost.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));// ���ñ��뷽ʽ��ʹ��UTF-8����
		client = new DefaultHttpClient();
		httpResponse = client.execute(httpPost);// ����post��������һ��HttpResponse����
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			String result = EntityUtils.toString(httpResponse.getEntity());
			Log.d("result", result);
			JSONObject jsonObject = new JSONObject(result);
			errorCode = jsonObject.getString("errorCode");

			JSONObject jsonObjectuser = jsonObject.getJSONObject("user");
			PartyConstant.u_id = (Integer) jsonObjectuser.get("u_id");
			Log.d("get u_id from result", "u_id=" + jsonObjectuser.get("u_id"));

			if (errorCode.equals(APIErrorCode.LOGIN_SUCCESS)) {

				Log.d("Login", "Success!errorCode=" + errorCode);
				return true;

			} else {
				Log.d("Login", "failure!errorCode=" + errorCode);
				Toast.makeText(host, "��½ʧ��", Toast.LENGTH_SHORT).show();

			}
		}
		return false;
	}
}
