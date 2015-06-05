package com.party.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

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

import com.party.sqliteManager.User;
import com.party.util.APIErrorCode;
import com.party.util.PartyConstant;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class EditUserInformationTask extends AsyncTask<Void, Void, Boolean> {

	Activity host = null;
	String urlpath;

	User user;

	public EditUserInformationTask(Activity host, String urlpath, User user) {

		this.host = host;
		this.urlpath = urlpath;
		this.user = user;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		user.setu_id(PartyConstant.u_id);
		Log.d("Edit user info", "u_id="+user.getu_id());
		return SendUserInformation(urlpath, user);

	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (result) {
			Log.d("Edit user info", "Edit user info finish");
		}
		host.finish();
	}

	public boolean SendUserInformation(String urlpath, User user) {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("user", user);
		Log.d("Edit user info", "Edit user info to HashMap");
		try {
			return readParse(urlpath, map);

		} catch (Exception e) {
			Log.e("Edit user info exception", e.toString());
			e.printStackTrace();
		}

		return false;

	}

	private boolean readParse(String urlpath, Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		HttpPost httpPost;
		// ����һ��NameValuePair���飬���ڴ洢�����͵Ĳ���
		List<NameValuePair> parames = new ArrayList<NameValuePair>();
		HttpClient client;
		HttpResponse httpResponse;
		String errorCode = null;

		JSONArray ary = new JSONArray().fromObject(map.get("user"));	
		parames.add(new BasicNameValuePair("user", ary.toString()));// ��Ӳ�������User																	// ����

		Log.d("Edit user info", "Edit user info to List<NameValuePair>");
		httpPost = new HttpPost(urlpath);
		httpPost.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));// ���ñ��뷽ʽ��ʹ��UTF-8����
		client = new DefaultHttpClient();
		httpResponse = client.execute(httpPost);// ����post��������һ��HttpResponse����
		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			String result = EntityUtils.toString(httpResponse.getEntity());
			Log.d("result", result);
			JSONObject jsonObject = new JSONObject(result);
			errorCode = jsonObject.getString("errorCode");
			if (errorCode.equals(APIErrorCode.EDIT_USERINFO_SUCCESS)) {

				Log.d("set user information ", "Set user information successed");
				return true;
			} else {
				Log.d("set user information ", "Set user information failed");
			}
		}

		return false;
	}
}
