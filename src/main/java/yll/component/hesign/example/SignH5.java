package yll.component.hesign.example;

import yll.component.hesign.util.SignConstants;
import yll.component.hesign.util.SignUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//接口5.4 页面签署
public class SignH5 {
    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;



    public static void main(String[] args) {

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;

        String transactionId = UUID.randomUUID().toString();
        String fileSn = "test0_fs";
        String projectSn = "test0_ps";
        String idCardType = "2";
        String idCardNo = "431322198703150534";
        String mobile = "+8618211550908";
        String name = "王小红";
        String version = "1";
        // 请求接口的地址
        String uploadUrl ="https://test-api.hesigning.com/contract/sign/h5";

        String notifyUrl = SignConstants.NOTIFYURL;

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
                .addFormDataPart("method", "sms")
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




}
