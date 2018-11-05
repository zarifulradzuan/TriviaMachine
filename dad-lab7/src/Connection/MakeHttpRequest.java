package Connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class MakeHttpRequest {
	public static JSONObject makeRequest(List<NameValuePair> params, String function){
		
		InputStream is = null;
		String json = "";
		JSONObject jObj = null;
		
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			String strUrl = null;
			if(function=="getCategories")
				strUrl="https://opentdb.com/api_category.php";
			else if(function=="getToken")
				strUrl="https://opentdb.com/api_token.php?command=request";
			else if(function=="resetToken")
				strUrl="https://opentdb.com/api_token.php?command=reset";
			else if(function=="getQuestions")
				strUrl="https://opentdb.com/api.php?null=";
			else
				JOptionPane.showMessageDialog(null, "Error with function");
			HttpPost httpPost = new HttpPost(strUrl);
			
			if(params!=null) {
				params.add(new BasicNameValuePair("encode","url3986"));
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine())!=null) 
				sb.append(line+"\n");
			is.close();
			json = sb.toString();
			jObj = new JSONObject(json);
			
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return jObj;
	}
}
