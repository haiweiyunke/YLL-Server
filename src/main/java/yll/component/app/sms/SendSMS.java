package yll.component.app.sms;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
import org.json.JSONObject;
import yll.service.model.YllConstants;

/**
 * 短信发送-正式
 * @author cc
 *
 */
public class SendSMS {
	
	private static String url="http://111.206.169.90:18042/templetSubmit/";   
	
	/**
	 * 验证码发送
	 * @param cellphone	手机号
	 * @throws JSONException
	 */
	public static void sendMessage(String cellphone,String verCode,String minute) throws JSONException{
		Map<Object,Object> params = new HashMap<>();
		params.put("code", verCode);
		params.put("minute", minute);
		//发送短信
		sendToSMS(cellphone, params, YllConstants.SMS_REGISTER);
	}
	
	/**
	 * 向短信平台调用短信接口
	 * @param cellphone 手机号
	 * @param parameter 短信模板参数
	 * @param type  发送的短信类型
	 */
	private static void sendToSMS(String cellphone,Map<Object,Object> params,String smsType) throws JSONException{
		try {
			//模板参数
			String parameter =  "";
			//模板id
			String SmsTempletID = "";
			//处理模板参数  {'参数名':'参数值'} 及模板
			if(YllConstants.SMS_REGISTER.equals(smsType)){
				//注册验证码发送
				parameter = "{\"code\":\"" + params.get("code") + "\",\"minute\":\""+ params.get("minute") +"\"}";
				SmsTempletID = "29101712191349390796312";
			} else{
				parameter = "{\"name\":\"" + params.get("name") + "\",\"type\":\""+ params.get("type") +"\"}";
				SmsTempletID = "29101806121502292502617";
			}
			//管理员分配的用户名
		    String LoginName = "291";
		    //要发送到的手机号（86+手机号）18395958225；18810866477
			String UserNumber = "86" + cellphone;
			String Timestamp = "" + System.currentTimeMillis();
			//管理员分配的密码
			String spPassword = "291";
		   
			TreeMap<String, String> inmap = new TreeMap<String, String>();
			inmap.put("SmsTempletID", SmsTempletID);
			inmap.put("LoginName", LoginName);
			inmap.put("UserNumber", UserNumber);
			inmap.put("Parameter", parameter );
			inmap.put("Timestamp", Timestamp);
			
			System.out.println(inmap.entrySet());
			
			String verifyReq = KeyedDigestMD5.getKeyedDigestGBKWithMap(inmap,
					spPassword);
			String verify = verifyReq;
	
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("SmsTempletID", SmsTempletID);
			jsonObject.put("LoginName", LoginName);
			jsonObject.put("UserNumber", UserNumber);
			jsonObject.put("Parameter", parameter );
			jsonObject.put("Timestamp", Timestamp);
			jsonObject.put("Verify", verify);
	
			String paraData = jsonObject.toString();
	
			System.out.println("请求参数 ： "+paraData.toString());
	
			HttpClientUtil httpClient = HttpClientUtil.getInstance();
			// 直接返回服务端的json串
			String respCon = httpClient.getResponseBodyAsString(url,
					paraData);
			System.out.println(respCon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
//		艺术家认证的 -> sendMessageAuthentication("18801348693", "傻fufu的猪猪飞", "_artist");
	}
}
