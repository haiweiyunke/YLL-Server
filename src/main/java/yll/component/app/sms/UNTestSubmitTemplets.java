package yll.component.app.sms;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.util.TreeMap;

/**
 * 短信接口 测试类
 * @author 
 *
 */
public class UNTestSubmitTemplets {
	
	public static void main(String[] args) throws JSONException {
		
		//生成验证码
		String verCode = VerifyCodeFactory.createSixCode();
		//拼接短信内容
		//StringBuilder content = new StringBuilder();
		//content.append("【汇洋物流】验证码:");
		//content.append(verCode);
		//content.append("。此验证码有效期为十分钟，逾期后请重新申请验证码。如非本人操作，请忽略本短信。");
		//System.out.println(content.toString());
		
		String url="http://111.206.169.90:18042/templetSubmit/";   
		//模板id
		String SmsTempletID = "5555501706011055070387699";
		//管理员分配的用户名
	    String LoginName = "291";
	    //要发送到的手机号（86+手机号）18395958225；18810866477
		String UserNumber = "86" + "18810866477";
		//{'参数名':'参数值'}
		String Parameter= "{\"code\":\"" + verCode + "\"}";
		String Timestamp = "" + System.currentTimeMillis();
		//管理员分配的密码
		String spPassword = "291";
	   
		TreeMap<String, String> inmap = new TreeMap<String, String>();
		inmap.put("SmsTempletID", SmsTempletID);
		inmap.put("LoginName", LoginName);
		inmap.put("UserNumber", UserNumber);
		inmap.put("Parameter", Parameter);
		inmap.put("Timestamp", Timestamp);
		
		System.out.println(inmap.entrySet());
		
		String verifyReq = KeyedDigestMD5.getKeyedDigestGBKWithMap(inmap,
				spPassword);
		String verify = verifyReq;

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("SmsTempletID", SmsTempletID);
		jsonObject.put("LoginName", LoginName);
		jsonObject.put("UserNumber", UserNumber);
		jsonObject.put("Parameter", Parameter);
		jsonObject.put("Timestamp", Timestamp);
		jsonObject.put("Verify", verify);

		String paraData = jsonObject.toString();

		System.out.println("请求参数 ： "+paraData.toString());

		HttpClientUtil httpClient = HttpClientUtil.getInstance();

		try {
			// 直接返回服务端的json串
			String respCon = httpClient.getResponseBodyAsString(url,
					paraData);
			System.out.println(respCon);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
