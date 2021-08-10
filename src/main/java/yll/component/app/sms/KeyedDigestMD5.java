package yll.component.app.sms;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class KeyedDigestMD5 {

	  
	public static byte[] getKeyedDigest(byte[] buffer, byte[] key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(buffer);
            return md5.digest(key);
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }
	
		
	public static String getKeyedDigest(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("UTF8"));
            
            String result="";
            byte[] temp;
            temp=md5.digest(key.getBytes("UTF8"));
    		for (int i=0; i<temp.length; i++){
    			result+=Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
    		}
    		
    		return result.toUpperCase();
    		
        } catch (NoSuchAlgorithmException e) {
        	
        	e.printStackTrace();
        	
        }catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;
    }
	public static String getKeyedDigestGb2312(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("gb2312"));
            
            String result="";
            byte[] temp;
            temp=md5.digest(key.getBytes("UTF8"));
    		for (int i=0; i<temp.length; i++){
    			result+=Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
    		}
    		
    		return result.toUpperCase();
    		
        } catch (NoSuchAlgorithmException e) {
        	
        	e.printStackTrace();
        	
        }catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;
    }
	
	public static String getKeyedDigestGBK(String strSrc, String key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(strSrc.getBytes("GBK"));
            
            String result="";
            byte[] temp;
            temp=md5.digest(key.getBytes("UTF8"));
    		for (int i=0; i<temp.length; i++){
    			result+=Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
    		}
    		
    		return result.toUpperCase();
    		
        } catch (NoSuchAlgorithmException e) {
        	
        	e.printStackTrace();
        	
        }catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;
    }
	
	public static String getKeyedDigestSendSms(String strSrc, String key) {
		try {
	           MessageDigest md5 = MessageDigest.getInstance("MD5");
	           md5.update(strSrc.getBytes("GBK"));

	           String result = "";
	           byte[] temp;
	           temp = md5.digest();

	           for (int i = 0; i < temp.length; i++) {
	        	   result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
	           }

	           return result.toUpperCase();
	       } catch (NoSuchAlgorithmException e) {
	           e.printStackTrace();
	       } catch (Exception e) {
	           e.printStackTrace();
	       }

	       return null;


}
	 protected static String getKeyedDigestGBKWithMap(TreeMap<String, String> tm,String key) {
	        StringBuffer buf = new StringBuffer();
	        for (Map.Entry<String, String> en : tm.entrySet()) {
	            String name = en.getKey();
	            String value = en.getValue();
	            if (value != null && value.length() > 0 && !"null".equals(value)) {
	                buf.append(name).append('=').append(value).append('&');
	            }
	        }
	        String _buf = buf.toString();

	        String verifyReq = getKeyedDigestGBK(_buf.substring(0, _buf.length() - 1),key);

	        return verifyReq;
	    }
	
	public static String getPoundageParam(String params, String localKey) {
	    try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(params.getBytes("GBK"));
            String result="";
            byte[] temp;
            temp=md5.digest();
            for (int i=0; i<temp.length; i++){
               result+=Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        }catch(Exception e)
        {
          e.printStackTrace();
        }
        return null;

	}
	
}
