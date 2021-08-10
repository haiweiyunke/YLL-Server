package yll.component.hesign.example;

import yll.component.hesign.util.SignConstants;
import yll.component.hesign.util.SignUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//接口5.2 根据模板创建合同
public class UploadTmplate {
    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;



    public static void main(String[] args) {

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;
        String tplId = "";

        String version = "1" ;
        String projectSn = "test_ps_cct01" ;
        String projectName = "test_pn_cct01";
        // 请求接口的地址
        String uploadUrl ="https://test-api.hesigning.com/contract/from/tpl";

        String notifyUrl = SignConstants.NOTIFYURL;

        String fileSn = "test_fs_5" ;
        String nonce  = UUID.randomUUID().toString(); ;

        TreeMap<String, Object> encryptParams = new TreeMap<>();

        encryptParams.put("files[0][sn]", fileSn);
        encryptParams.put("nonce", nonce);
        encryptParams.put("openId", openId);
        encryptParams.put("projectSn", projectSn);
        encryptParams.put("version", version);
        String sign = SignUtil.signature(encryptParams, nonce, appSecret);
        System.out.println("------------ sign:"+sign);

        //OkHttpClient okHttpClient = new OkHttpClient();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();

//        MediaType type=MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
//        File file = new File("test.pdf");
//        RequestBody fileBody=RequestBody.create(type,file);


        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("sign", sign)
                .addFormDataPart("openId", openId)
                .addFormDataPart("nonce", nonce)
                .addFormDataPart("version", version)
                .addFormDataPart("projectSn", projectSn)
                .addFormDataPart("projectName", projectName)
                .addFormDataPart("notifyUrl", notifyUrl)

                //第一个文件
                .addFormDataPart("files[0][sn]", fileSn)
                .addFormDataPart("files[0][name]","12345678901234567890")
                .addFormDataPart("files[0][templateId]",tplId)
                .addFormDataPart("files[0][permission]","*")
                .addFormDataPart("files[0][params]","{\"name\":\"This is file 2\",\"desc\":\"hello world file 2\"}\n")

                //第一个签署人
                .addFormDataPart("files[0][sign][0][openId]",openId)
                .addFormDataPart("files[0][sign][0][identity]","1")
                .addFormDataPart("files[0][sign][0][auto]","1")
                .addFormDataPart("files[0][sign][0][reason]","本次签署已获得授权")
                .addFormDataPart("files[0][sign][0][location]","授权设备IP:135.120.12.31")

                //第二个签署人
                .addFormDataPart("files[0][sign][1][idCardType]","2")
                .addFormDataPart("files[0][sign][1][idCardNo]","431322198703150534")
                .addFormDataPart("files[0][sign][1][mobile]","+8618211550908")
                .addFormDataPart("files[0][sign][1][name]","王小红")
                .addFormDataPart("files[0][sign][1][identity]","2")
                .addFormDataPart("files[0][sign][1][auto]","1")
                .addFormDataPart("files[0][sign][1][reason]","本次签署已获得授权")
                .addFormDataPart("files[0][sign][1][location]","授权设备IP:135.120.12.31")

                //第三个签署人
                .addFormDataPart("files[0][sign][2][idCardType]","2")
                .addFormDataPart("files[0][sign][2][idCardNo]","431322197702040953")
                .addFormDataPart("files[0][sign][2][mobile]","+8618521942398")
                .addFormDataPart("files[0][sign][2][name]","王小尽")
                .addFormDataPart("files[0][sign][2][identity]","3")
                .addFormDataPart("files[0][sign][2][auto]","1")
                .addFormDataPart("files[0][sign][2][reason]","本次签署已获得授权")
                .addFormDataPart("files[0][sign][2][location]","授权设备IP:135.120.12.31")
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
                System.out.println("Upload Template 执行成功,并得到响应");
                System.out.println("响应值的参数:"+response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
