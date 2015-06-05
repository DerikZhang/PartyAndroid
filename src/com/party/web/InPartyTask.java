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

public class InPartyTask extends AsyncTask<Void, Void, Void> {

	Activity host;
	String uriPath;
	String partyId;
	String u_id;

	public InPartyTask(Activity host, String uriPath, String partyId,
			String u_id) {

		this.host = host;
		this.uriPath = uriPath;
		this.partyId = partyId;
		this.u_id = u_id;
	}

	@Override
	protected Void doInBackground(Void... params) {

		inParty(uriPath, partyId, u_id);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	public boolean inParty(String uriPath, String partyId, String u_id) {
		Log.d("In party", "In party data to HashMap");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("partyId", partyId);
		map.put("u_id", u_id);

		try {
			return readParse(uriPath, map);

		} catch (Exception e) {
			Log.e("Login exception", e.toString());
			e.printStackTrace();
		}

		return false;

	}

	private boolean readParse(String urlpath, Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		HttpPost httpPost;
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		List<NameValuePair> parames = new ArrayList<NameValuePair>();
		HttpClient client;
		HttpResponse httpResponse;
		String errorCode = null;
		parames.add(new BasicNameValuePair("partyID", (String) map
				.get("partyId")));// 添加参数——User对象
		parames.add(new BasicNameValuePair("u_id", (String) map.get("u_id")));

		httpPost = new HttpPost(urlpath);
		httpPost.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));// 设置编码方式，使用UTF-8编码
		client = new DefaultHttpClient();
		httpResponse = client.execute(httpPost);// 发送post，并返回一个HttpResponse对象
		Log.d("http response",
				"Send HttpPost,and return an HttpResponse object");
		
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			String result = EntityUtils.toString(httpResponse.getEntity());
			Log.d("In party result", result);
			JSONObject jsonObject = new JSONObject(result);
			errorCode = jsonObject.getString("errorCode");

			if (errorCode == APIErrorCode.JOIN_PARTY_SUCCESS) {

				Log.d("In party", "In party successed,errorCode:" + errorCode);
				return true;
			} else {
				Log.d("In party", "In party failed,errorCode:" + errorCode);
			}
		}

		return false;
	}
}
