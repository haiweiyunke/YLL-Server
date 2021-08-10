package yll.component.hesign.using;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.relucent.base.util.expection.ExceptionHelper;
import okhttp3.*;
import yll.component.hesign.entity.HeSignAuth;
import yll.component.hesign.entity.HeSignBase;
import yll.component.hesign.entity.HeSignFiles;
import yll.component.hesign.entity.HeSignResult;
import yll.component.hesign.util.SignConstants;
import yll.component.hesign.util.SignUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *  5.1 上传合同
 */
public class He51Upload {
    public final static int CONNECT_TIMEOUT =60;
    public final static int READ_TIMEOUT=100;
    public final static int WRITE_TIMEOUT=60;

    public static void main(String[] args) {
        upload();
    }

    /**
     * 上传合同
     */
    public static void upload() {

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;

        String version = "1" ;
        String projectSn = "test0_ps_09_cc" ;
        String projectName = "test0_pn_09_cc";
        // 请求接口的地址
        String uploadUrl = SignConstants.UPLOADURL_CONTRACT_UPLOAD_51;

        String notifyUrl = SignConstants.NOTIFYURL;

        String fileSn = "test0_fs_09" ;
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
        File file = new File(SignConstants.UPLOAD_PDF);
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
                .addFormDataPart("files[0][name]","cctest_09")
                //.addFormDataPart("files[0][file]", s)
                //.addFormDataPart("files[0][file]", Arrays.toString(buffer))
                .addFormDataPart("files[0][file]","filesFileName_09.pdf", fileBody)
                .addFormDataPart("files[0][permission]","*")
                //openId 与 auth 只传一个即可 ,如果openId传空,则默认openId已经传递,且为空值,则会验证不通过
//                .addFormDataPart("files[0][sign][0][openId]",openId)

                //auth 下数四行 皆为 auth
                .addFormDataPart("files[0][sign][0][position]","1,0.1,0.2")
                .addFormDataPart("files[0][sign][0][idCardType]","2")
                .addFormDataPart("files[0][sign][0][idCardNo]","110101198403075431")
                .addFormDataPart("files[0][sign][0][mobile]", "18810866477")
                .addFormDataPart("files[0][sign][0][name]","小明")
                .addFormDataPart("files[0][sign][0][auto]","0")
                .addFormDataPart("files[0][sign][0][reason]","本次签署已获得授权")

                .addFormDataPart("files[0][sign][1][position]","2,0.2,0.8")
                .addFormDataPart("files[0][sign][1][idCardType]","2")
                .addFormDataPart("files[0][sign][1][idCardNo]","520112196806021127")
                .addFormDataPart("files[0][sign][1][mobile]", "18810866477")
                .addFormDataPart("files[0][sign][1][name]","小红")
                .addFormDataPart("files[0][sign][1][auto]","0")
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

    /**
     * 上传合同
     */
    public static void uploadContract(HeSignBase heSignBase, HeSignFiles heSignFiles, List<HeSignAuth> heSignAuthList) throws Exception {        //搞三个实体

        String appSecret = SignConstants.APPSECRET;
        String openId = SignConstants.OPENID;

        String version = heSignBase.getVersion();
        String projectSn = heSignBase.getProjectSn();
        String projectName = heSignBase.getProjectName();
        // 请求接口的地址
        String uploadUrl = SignConstants.UPLOADURL_CONTRACT_UPLOAD_51;

        String notifyUrl = SignConstants.NOTIFYURL;

        String fileSn = heSignFiles.getFileSn() ;
        String nonce  = UUID.randomUUID().toString();

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
        File file = new File(SignConstants.UPLOAD_PDF);
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
                .addFormDataPart("files[0][name]", heSignFiles.getFilesName())
                //.addFormDataPart("files[0][file]", s)
                //.addFormDataPart("files[0][file]", Arrays.toString(buffer))
                .addFormDataPart("files[0][file]", heSignFiles.getFilesFileName(), fileBody)
                .addFormDataPart("files[0][permission]","*")
                //openId 与 auth 只传一个即可 ,如果openId传空,则默认openId已经传递,且为空值,则会验证不通过
//                .addFormDataPart("files[0][sign][0][openId]",openId)

                //auth 下数四行 皆为 auth
                .addFormDataPart("files[0][sign][0][position]", heSignAuthList.get(0).getPosition())    //原文案："1,0.1,0.2"
                .addFormDataPart("files[0][sign][0][idCardType]",heSignAuthList.get(0).getIdCardType())
                .addFormDataPart("files[0][sign][0][idCardNo]",heSignAuthList.get(0).getIdCardNo())
                .addFormDataPart("files[0][sign][0][mobile]", heSignAuthList.get(0).getMobile())
                .addFormDataPart("files[0][sign][0][name]",heSignAuthList.get(0).getName())
                .addFormDataPart("files[0][sign][0][auto]", "0") //原文案：1，0-未签约
                .addFormDataPart("files[0][sign][0][reason]",heSignAuthList.get(0).getReason()) //原文案：本次签署已获得授权




                .addFormDataPart("files[0][sign][1][position]",heSignAuthList.get(1).getPosition())
                .addFormDataPart("files[0][sign][1][idCardType]",heSignAuthList.get(1).getIdCardType())
                .addFormDataPart("files[0][sign][1][idCardNo]",heSignAuthList.get(1).getIdCardNo())
                .addFormDataPart("files[0][sign][1][mobile]", heSignAuthList.get(1).getMobile())
                .addFormDataPart("files[0][sign][1][name]",heSignAuthList.get(1).getName())
                .addFormDataPart("files[0][sign][1][auto]","0")
                .addFormDataPart("files[0][sign][1][reason]",heSignAuthList.get(1).getReason())

                .build();

        System.out.println("start to send");
        long startTime = System.currentTimeMillis();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(body)
                .build();
            Response response = okHttpClient.newCall(request).execute(); //同步请求
            if (response.isSuccessful()) {
                System.out.println(request.body().contentLength());
                String responseBody = response.body().string();
                System.out.println("Upload 执行成功,并得到响应");
                System.out.println("响应值的参数:"+ responseBody);
                System.out.println(System.currentTimeMillis()-startTime);

                //返回格式化响应结果
                /*String resultStr = responseBody;
                ObjectMapper objectMapper = new ObjectMapper();
                HeSignResult result = objectMapper.readValue(resultStr, HeSignResult.class);
                if("0".equals(result.getSuccess())){
                    String message = result.getMessage();
                    String errno = result.getErrno();
                    String error = result.getError();
                    throw ExceptionHelper.prompt(message + "：" + errno + "--" + error);
                }*/

            }

    }



    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(HeSignBase heSignBase, HeSignFiles heSignFiles, List<HeSignAuth> heSignAuthList) {

        int size = heSignAuthList.size();
        if (size != 2) {
            throw ExceptionHelper.prompt("签章人数不符");
        }

//        heSignBase

//        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
