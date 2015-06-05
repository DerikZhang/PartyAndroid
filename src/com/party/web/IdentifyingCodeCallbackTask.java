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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class IdentifyingCodeCallbackTask extends AsyncTask<Void, Void, Boolean> {

	Activity host = null;
	String urlpath;
	String phoneNumber;
	String verificationCode;
	String password;

	static boolean successed = false;

	public IdentifyingCodeCallbackTask(Activity host, String urlpath,
			String phoneNumber, String verificationCode, String password) {

		this.host = host;
		this.urlpath = urlpath;
		this.phoneNumber = phoneNumber;
		this.verificationCode = verificationCode;
		this.password = password;
	}

	@Override
	protected Boolean doInBackground(Void... params) {

		boolean result = sendVertificationCode(urlpath, phoneNumber,
				verificationCode, password);

		return result;
	}

	@Override
	protected void onPostExecute(Boolean result) {

		super.onPostExecute(result);
		if (result) {

			PartyConstant.userPhoneNumber = phoneNumber;
			Intent intent = new Intent(host, MenuActivity.class);
			host.startActivity(intent);
		} else {

			Toast.makeText(host, "注册失败，账号已注册、验证码错误或其他原因", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public boolean sendVertificationCode(String urlpath, String phoneNumber,
			String verificationCode, String password) {
		Log.d("callback identifying code", "callback identifying code");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", phoneNumber);
		map.put("verificationCode", verificationCode);
		map.put("password", password);

		try {
			return readParse(urlpath, map);

		} catch (Exception e) {

			Log.e("Login exception", e.toString());
			e.printStackTrace();
		}

		return false;

	}

	@SuppressLint("NewApi")
	private boolean readParse(String urlpath, Map<String, Object> map)
			throws Exception {

		HttpPost httpPost;
		// 建立一个NameValuePair数组，用于存储欲传送的参数
		List<NameValuePair> parames = new ArrayList<NameValuePair>();
		HttpClient client;
		HttpResponse httpResponse;
		String errorCode = null;
		parames.add(new BasicNameValuePair("phone_number", (String) map
				.get("phoneNumber")));// 添加参数——电话号码
		parames.add(new BasicNameValuePair("verificationCode", (String) map
				.get("verificationCode")));
		parames.add(new BasicNameValuePair("password", (String) map
				.get("password")));

		httpPost = new HttpPost(urlpath);
		httpPost.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));// 设置编码方式，使用UTF-8编码
		client = new DefaultHttpClient();
		httpResponse = client.execute(httpPost);// 发送post，并返回一个HttpResponse对象
		Log.d("http response",
				"Send HttpPost,and return an HttpResponse object");

		if (httpResponse.getStatusLine().getStatusCode() == 200) {

			String result = EntityUtils.toString(httpResponse.getEntity());
			Log.d("result", result);
			JSONObject jsonObject = new JSONObject(result);
			errorCode = jsonObject.getString("errorCode");
			
			JSONObject jsonObjectuser = jsonObject.getJSONObject("user");
			PartyConstant.u_id = (Integer) jsonObjectuser.get("u_id");
			Log.d("get u_id from result",
					"u_id=" + jsonObjectuser.get("u_id"));
			
			// JSONArray array = new JSONArray();
			// JSONObject result = array.optJSONObject(0);
			// errorCode = result.getJSONObject("errorCode").toString();

			if (errorCode.equals(APIErrorCode.SIGN_SUCCESS)) {

				Log.d("callback verification code", "Success");
				return true;
			} else {

				Log.d("callback verification code", "failure");
			}
		}

		return false;
	}
}
