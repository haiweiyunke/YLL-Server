package yll.component.hesign.using;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.relucent.base.util.expection.ExceptionHelper;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import yll.component.hesign.entity.HeSignAuth;
import yll.component.hesign.entity.HeSignBase;
import yll.component.hesign.entity.HeSignFiles;
import yll.component.hesign.entity.HeSignResult;
import yll.component.hesign.util.SignConstants;
import yll.component.hesign.util.SignUtil;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//接口5.4 页面签署
public class He54SignH5 {
    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;

    public static void main(String[] args) {
        signH5();
    }

    public static void signH5() {

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;

//        String transactionId = UUID.randomUUID().toString();
        String transactionId = UUID.randomUUID().toString();
        String fileSn = "test0_fs_09";
        String projectSn = "test0_ps_09_cc";
        String idCardType = "2";
        String mobile = "+18810866477";
//        String idCardNo = "110101198403075431";
//        String name = "小明";
        String idCardNo = "520112196806021127";
        String name = "小红";
        String version = "1";
        // 请求接口的地址
        String uploadUrl = SignConstants.UPLOADURL_CONTRACT_SIGN_H5_53;

        String notifyUrl = SignConstants.NOTIFYURL + "/2";

        String nonce  = UUID.randomUUID().toString();
        TreeMap<String, Object> encryptParams = new TreeMap();
        encryptParams.put("fileSn", fileSn);
        encryptParams.put("nonce", nonce);
        encryptParams.put("openId", openId);
        encryptParams.put("projectSn", projectSn);
        encryptParams.put("version", version);
        encryptParams.put("idCardType", idCardType );
        encryptParams.put("idCardNo", idCardNo);
        String sign = SignUtil.signature(encryptParams, nonce, appSecret);

        System.out.println("------------ sign:"+sign);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();


        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("sign", sign)
                .addFormDataPart("openId", openId)
                .addFormDataPart("nonce", nonce)
                .addFormDataPart("version", version)

                .addFormDataPart("transactionId", transactionId)
                .addFormDataPart("projectSn", projectSn)
                .addFormDataPart("fileSn", fileSn)
                .addFormDataPart("method", "handwrite")
                .addFormDataPart("smsMobile", mobile)
                .addFormDataPart("redirectUrl", notifyUrl)
                .addFormDataPart("idCardType", idCardType)
                .addFormDataPart("idCardNo", idCardNo)
                .addFormDataPart("name", name)
                .addFormDataPart("mobile", mobile)
                .build();

        System.out.println("start to send");
        long startTime = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(body)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute(); //同步请求
            if (response.isSuccessful()) {
                System.out.println(request.body().contentLength());
                System.out.println("Sign H5 执行成功,并得到响应");
                System.out.println("响应值的参数:"+response.body().string());
                System.out.println(System.currentTimeMillis()-startTime);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 页面签署
     * @param heSignBase
     * @param heSignFiles
     * @param heSignAuth
     */
    public static HeSignResult signH5(HeSignBase heSignBase, HeSignFiles heSignFiles, HeSignAuth heSignAuth) throws Exception {

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;

        String transactionId = UUID.randomUUID().toString();
        String fileSn = heSignFiles.getFileSn();
        String projectSn =  heSignBase.getProjectSn();
        String idCardType = heSignAuth.getIdCardType();
        String idCardNo = heSignAuth.getIdCardNo();
        String mobile = heSignAuth.getMobile();
        String name = heSignAuth.getName();
        String method = heSignAuth.getMethod();
        String version = heSignBase.getVersion();      //原文案：1
        // 请求接口的地址
        String uploadUrl = SignConstants.UPLOADURL_CONTRACT_SIGN_H5_53;

        String notifyUrl = SignConstants.NOTIFYURL + "/" + heSignAuth.getType();

        String nonce  = UUID.randomUUID().toString();
        TreeMap<String, Object> encryptParams = new TreeMap();
        encryptParams.put("fileSn", fileSn);
        encryptParams.put("nonce", nonce);
        encryptParams.put("openId", openId);
        encryptParams.put("projectSn", projectSn);
        encryptParams.put("version", version);
        encryptParams.put("idCardType", idCardType );
        encryptParams.put("idCardNo", idCardNo);
        String sign = SignUtil.signature(encryptParams, nonce, appSecret);

        System.out.println("------------ sign:"+sign);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();


        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("sign", sign)
                .addFormDataPart("openId", openId)
                .addFormDataPart("nonce", nonce)
                .addFormDataPart("version", version)

                .addFormDataPart("transactionId", transactionId)
                .addFormDataPart("projectSn", projectSn)
                .addFormDataPart("fileSn", fileSn)
                .addFormDataPart("method", method)
                .addFormDataPart("smsMobile", mobile)
                .addFormDataPart("redirectUrl", notifyUrl)
                .addFormDataPart("idCardType", idCardType)
                .addFormDataPart("idCardNo", idCardNo)
                .addFormDataPart("name", name)
                .addFormDataPart("mobile", mobile)
                .build();

        System.out.println("start to send");
        long startTime = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute(); //同步请求

        HeSignResult result = new HeSignResult();   //响应结果
        if (response.isSuccessful()) {
            System.out.println(request.body().contentLength());
            String responseBody = response.body().string();
            System.out.println("Sign H5 执行成功,并得到响应");
            System.out.println("响应值的参数:"+responseBody);
            System.out.println(System.currentTimeMillis()-startTime);

            //返回格式化响应结果
            String resultStr = responseBody;
            ObjectMapper objectMapper = new ObjectMapper();
            /*result = objectMapper.readValue(resultStr, HeSignResult.class);
            if("0".equals(result.getSuccess())){
                String message = result.getMessage();
                String errno = result.getErrno();
                String error = result.getError();
                throw ExceptionHelper.prompt(message + "：" + errno + "--" + error);
            }*/

            //返回地址转map
            Map map= objectMapper.readValue(resultStr, Map.class);
            Object urlObj = map.get("url");
            if(null != urlObj){
                String url = urlObj.toString();
                result.setUrl(url);
            }

        }
        return result;
    }

}
