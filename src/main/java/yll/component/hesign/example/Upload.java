package yll.component.hesign.example;

import yll.component.hesign.util.SignConstants;
import yll.component.hesign.util.SignUtil;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//接口5.1 上传合同
public class Upload {
    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;



    public static void main(String[] args) {

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;

        String version = "1" ;
        String projectSn = "test0_ps_4" ;
        String projectName = "test0_pn_4";
        // 请求接口的地址
        String uploadUrl ="https://test-api.hesigning.com/contract/upload";

        String notifyUrl = SignConstants.NOTIFYURL;

        String fileSn = "test0_fs_4" ;
        String nonce  = UUID.randomUUID().toString(); ;

        TreeMap<String, Object> encryptParams = new TreeMap<>();

        encryptParams.put("files[0][sn]", fileSn);
        encryptParams.put("nonce", nonce);
        encryptParams.put("openId", openId);
        encryptParams.put("projectSn", projectSn);
        encryptParams.put("version", version);
        String sign = SignUtil.signature(encryptParams, nonce, appSecret);
        System.out.println("------------ sign:"+sign);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT,TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT,TimeUnit.SECONDS)//设置连接超时时间
                .build();

        MediaType type=MediaType.parse("application/octet-stream");
        File file = new File("test2.pdf");
        RequestBody fileBody=RequestBody.create(type,file);


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
                //.addFormDataPart("files[0][file]", s)
                //.addFormDataPart("files[0][file]", Arrays.toString(buffer))
                .addFormDataPart("files[0][file]","test0.pdf", fileBody)
                .addFormDataPart("files[0][permission]","*")
                .addFormDataPart("files[0][sign][0][position]","1,0.1,0.2")
                //openId 与 auth 只传一个即可 ,如果openId传空,则默认openId已经传递,且为空值,则会验证不通过
//                .addFormDataPart("files[0][sign][0][openId]",openId)

                //auth 下数四行 皆为 auth
                .addFormDataPart("files[0][sign][0][idCardType]","2")
                .addFormDataPart("files[0][sign][0][idCardNo]","110101198403075431")
                .addFormDataPart("files[0][sign][0][mobile]", "18521942399")
                .addFormDataPart("files[0][sign][0][name]","张小明")
                .addFormDataPart("files[0][sign][0][auto]","1")
                .addFormDataPart("files[0][sign][0][reason]","本次签署已获得授权")




                .addFormDataPart("files[0][sign][1][position]","2,0.2,0.8")
                .addFormDataPart("files[0][sign][1][idCardType]","2")
                .addFormDataPart("files[0][sign][1][idCardNo]","520112196806021127")
                .addFormDataPart("files[0][sign][1][mobile]", "18521942390")
                .addFormDataPart("files[0][sign][1][name]","王哲")

                .addFormDataPart("files[0][sign][1][auto]","1")
                .addFormDataPart("files[0][sign][1][reason]","本次签署已获得授权")

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
                System.out.println("Upload 执行成功,并得到响应");
                System.out.println("响应值的参数:"+response.body().string());
                System.out.println(System.currentTimeMillis()-startTime);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
