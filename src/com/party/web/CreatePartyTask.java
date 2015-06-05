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

import com.party.sqliteManager.Party;
import com.party.util.APIErrorCode;
import com.party.util.PartyConstant;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class CreatePartyTask extends AsyncTask<Void, Void, Boolean> {

	final String log_CreateParty = "Create Party";
	Activity host = null;
	String urlpath;
	String phoneNumber;
	Party party;

	public CreatePartyTask(Activity host, String urlpath, String phoneNumber,
			Party party) {

		this.host = host;
		this.urlpath = urlpath;
		// this.phoneNumber = phoneNumber;
		this.party = party;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		party.setu_id(PartyConstant.u_id);
		Log.d("Create Party", "party.u_id=" + party.toString());
		creatParty(urlpath, party);
		return null;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		host.finish();
	}

	public boolean creatParty(String urlpath, Party party) {
		Log.d("Create Party", "Create Party infromation to HashMap");

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("party", party);

		try {
			return readParse(urlpath, map);

		} catch (Exception e) {
			Log.e("Login exception", e.toString());
			e.printStackTrace();
		}

		return false;

	}

	private boolean readParse(String urlpath, Map<String, Object> map)
			throws Exception {

		HttpPost httpPost;
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		List<NameValuePair> parames = new ArrayList<NameValuePair>();
		HttpClient client;
		HttpResponse httpResponse;
		String errorCode = null;

		parames.add(new BasicNameValuePair("party", map.get("party").toString()));

		httpPost = new HttpPost(urlpath);
		httpPost.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));// 设置编码方式，使用UTF-8编码
		client = new DefaultHttpClient();
		httpResponse = client.execute(httpPost);// 发送post，并返回一个HttpResponse对象
		Log.d("http response",
				"Send HttpPost,and return an HttpResponse object");

		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			String result = EntityUtils.toString(httpResponse.getEntity());
			Log.d("Create Party result", result);
			JSONObject jsonObject = new JSONObject(result);
			errorCode = jsonObject.getString("errorCode");

			if (errorCode == APIErrorCode.CREATE_PARTY_SUCCESS) {

				Log.d("Create Party ", "Create Party Success,errorCode="+errorCode);
				return true;
			} else {
				Log.d("Create Party ", "Create Party failure,errorCode"+errorCode);
				return false;
			}
		}

		return false;
	}
}
