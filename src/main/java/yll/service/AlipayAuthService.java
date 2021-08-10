package yll.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.entity.Customer;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 支付宝处理类
 */
@Transactional
@Service
public class AlipayAuthService {        //TODO 把所有页面上传改成cos的

    // ==============================Fields===========================================
    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================

    /**
     * 获取支付宝Token（需要用code获取，oauth2方式，App登录使用）
     */
    public Customer getOauth2Token(String code) throws Exception {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(code);
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            //根据userId判断用户是否存在
            return checkCustomer(response);
        } else {
            System.out.println("调用失败");
            return null;
        }
    }

    /**
     * 获取支付宝用户信息
     * @return
     */
    protected Customer getUserInfo(String accessToken) throws Exception {
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = alipayClient.execute(request, accessToken);
        if(response.isSuccess()){
            System.out.println("调用成功");
            //新增用户
           return insertCustomer(response);
        } else {
            System.out.println("调用失败");
           return null;
        }
    }

    /**
     * 用户验证
     * @param response
     * @throws Exception
     */
    public Customer checkCustomer(AlipaySystemOauthTokenResponse response) throws Exception {
        String accessToken = response.getAccessToken();
        return getUserInfo(accessToken);
    }

    /**
     * 封装支付宝用户信息
     * @param response
     * @throws UnsupportedEncodingException
     */
    private Customer insertCustomer(AlipayUserInfoShareResponse response) throws UnsupportedEncodingException {
        String nickName = response.getNickName();
        Customer entity = new Customer();
        entity.setAliId(response.getUserId());
        entity.setNickname(Base64.getEncoder().encodeToString(nickName.getBytes("UTF-8")));
        entity.setHeadImg(response.getAvatar());
        if(StringUtils.isNotBlank(response.getPhone())){
            entity.setPhone(response.getPhone());
        }
        return entity;
    }


    /** 测试 */
    public static void main(String[] args) throws Exception {

        //TODO 测试此处

//        String pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjdUPx6F6rkJcJkhcX4l/HT/ENko82YFoVAueyIhf3KVo2dV2qfC/dlmF8fekyxos78vZdW3DgbMqjzuPoDgaRes6P5i2jsJBKZnrxGoLA4qg8AVY7J9gcDK5R0aUoq3dC6VFUcbQZlVpyKLZN2msmXoWp4/58eX9gkNkrTo8TI4zMEpSmD8k+ytsi31Y+noKJBRQbgJhF/Dc2l96H5h14OIh7kFqkGS7EM0MGhy6sYXHe7FBC9xgLsSMjNroY5NtOZX1FyonNnu5r22Zks19qRqBt03ZEvtEAwq+FDCu4GXNI8J31pRXsRGN4swDmJZwIdDjD1+u4nGSaLkhvPAhfQIDAQAB";
//        String pri = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC/6Sctl5nhNar5p2Zn5b8KxkmUs3DwvVdHrzP25luZnfxfN1749wp+hVBZXSg9o1S+e4qF/wOGWja0eGtVIxrt2Y1dUbrmie5Ogd23QitdJV35/HEdkXxSBc8Nao+JAYuecapUmnUiSutxfsxFJN06avwe9IgtK+8LwJDhvAAAB/vhREOqR0Hfke6R2i+2hj3YFszG/WGQN2e10TeCgArl5e1bD2fWwN9B7HaCMPv5PAU/V2IpVgIELJ+ZpvBgeU9CrcztFI0gcKvTOg8kkn0Ysb29mBsw6F90KEl/FjAo6l/X0y7h/niqpBMv7S2QueXwEWJvbUwIQKJDDnIril95AgMBAAECggEAbJYl/tway50sQtp/TSZn9FbB54uoye64Ze0yf+9TwRXtaIrSCBjwLSqNjiLx0/6IwsbwWGU0v15oObExOhkE8bP5EuZd1HgTCQTeSQT9uFSeWgaGkm5xY4+52iODV8gyEdLKx9glkuPFWJCgUDdNnfJO+czvCHAlcAbjc3aGoXzoGMf0Cf8Dw8CBztrObhw8/NZSPwO5SbMiNZfKv9sREW3pxDHFQCSsbINQ2Ul7ZtOHuOI7+l6j6XT9wuv8glh2NHntFUuAjupD0ep5RBzkf9aDEOFphXIUnZB9QUBwkI1kqQIivCa5M02HJE+pAiqh1oefrT3qwhzfW0d5oYkAoQKBgQDivSLc0+ziLgWQVLZzUnrCIifQDJ9gOjpmHGKw58sQP1hOeXCcnUPJ1NZVmJePXIQ8M7DgCrt/jjLSJZWG2oETd2WGDmka77iYhcopfvG6lY+fW/uCLXquQsPwhtF4X2s8/CY6/uIfdbyM4fUh/SG/0oAS/h9MgUMZmPHCsfWQhQKBgQDYrWNMSU4IIvWG7uPFm5KtF3yIE0InaAuWWn+TS6zzYnL6bC3avpdFqREe9iXW0LktE3q7vy1uNHfQE85B+iMWYj/wt8/l7vojUVCdkbP5qsfawfSPk76WndAGR7CU0gCOjUJAiv8mj8BA9znwJIuSCeVhlE8PsgQqPFpCaxtfZQKBgD/c2+PtAQyXv9NuONF0isUBT8tmssEsxWQbXKTNNMHHxbycsd464WwIubH0P8QOX+SA/rGr9DGyQJg2I86O3so31+2RQ4Nlv82d6VoUuks+cuyEwOtimepZPc0SfA92eML9llsJjXEk/U2FG0FOheH8jTxMPU6UHe0oyfd3nl3tAoGBAIiRiIO9tGQmfLCmg4O6kdFtez1dPhYOWzdSJRTW3tSsmaHDa4dzF299p1/nb2QdPInvvCz3y4+pnFbXFOq62UCzfm+fu3bGkHyQNQqLmm9juAHmu9l/GuJd5479wouabWVsHXUG5tkMQ/XunWMB9cJ/YEeHrelLn5prXcx9K8yNAoGBAMjR/TROq0mOgpUCgPEcihMG42Uelt0+Q51Y4kvXC8yefQrPa6CPGJ53UKTg9ekSszpdzIlD75gkH0Dx632y0Bcp9pu0WJfXo4yoabFQ4kQb6N4UCIodpLwKt1yULgsafUpFWWTrtPDEtJQA0bxv3qOmxaVuBqPto/w6p05uGaIF";
//        String url = "https://openapi.alipay.com/gateway.do";
//        String appid = "2019111169120214";

        String pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuuzKepLBsDVk59ioOiFwA2NxBJ5BmUcv5Tx4b4FDXOBlev67lZAzck2Abpgk9ObZP2I62R9qOAsyZ0EKSE5lMgxci5GXvJf2N0jLUcDcavCofDXy+z9/yokXL0d4+ZQJuy0dfKoLWMPWIo7lL6z/4ba+Orhob2XasUihPPgUktT4s0sufyigsLiQ18/yheQIf01JHXecWBU1sVfKCoOLnz0R+SnQ0m7c8bJwtRK4oZSTRK+mPodQdNs8s2gp/UMqh2I10e4nvIacRjTkFB2SBONV0P/rMvO2SOHyk+5iyKGExpCE74lGf7VE7cQyRejtnvT5ljsCUF9jofGQavfWXwIDAQAB";
        String pri = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCTC37v4wj0iTenGI9CD8pHKadOSwAYURAo6hclWhyf+z2qh2d63oPnd35mTG51k4qYo6W7xgzR6xkQk006GLfXMsikbitVZ3yadJ0ZXR7afgod36L+0m54hRlRWlIyOYIC9PXZiug+fvAh19GZV+94YFLDiw+QZU8atKKudrevmenv0ZX13P2X2lVtvwyeNj7nH0nKxJt7+0v/YKazvpaq3KOz9VyKw4A0pCnuNlz1/iMKg0aoTssES1M8gUyIdI4bcSMVCa7NUU72eHApTT8iIfZtpuSNauiC/K8Tbge5pXk1tGy333vmuWwFYFmOjWMgEzB4/laM8mScxmZ3h44zAgMBAAECggEAT9gUKyn6eN3xbDg3wcVVLuL9R9QNzyZlQfIqc0vE81G3IFu54svkj9Egv2eNKybcQRnLKnTWDT49M/ToID2/xYh7zl1MBmnVDQF5NIaN9FIwKByBIyEcfzC2mKgUrdTwNa7RuDhm91hyB85qr3tCOvkCt89QujVoiLXBCD7KavyyhLwB10Fcy1BZAvFDZnai/SITzzjDMBpDpQBIQEx9w59Z1jIHbiMCskf8LISaj5ekkiS7+w7jLkrforEhex+SEPwmhs1sRFGp1+SRhYZ/2vLSB4OZz9q0rp3nx7rdNPRYBbiKRrpy4Z9HOJL7YJYIzZVkP6aGnj4SgKXnWThjUQKBgQDV/sdMC8K8sT9cq9YZ+EhZlsaymXtd43945UyBZZWvKYOhI/b8w6flXGI+x0yqrM0/cZzQkHQyJsCjwtSaTcr8xeQhaC93xMN15M52wiFfLnlJ/3cJzr9q0lxJjc+YISov7E2PH9B9oUi3n0EH+Wy5QgggxFmzlYyCbaSBut3o7QKBgQCv6HnKA2beGIIMxpzdAtSMcVutWsarYb4U6pmRbK9isRemkicbvLssMbcWq85o3TlvJRlKxftTz3he1eE1wm5tAFS9ZhgaY6+oZg8jYrYHn2Kn9BTTKppALFAusXBq2VgwDN5psSybHGBtGDnZk/dncTLx+SMlQXX2rm6CB4YPnwKBgCZ4pU+wlZPisadxZQ5KKoegqZwWJ62bZqFPZ8+jeaOB7R4dHxwV+KstTqRGpCvS4RtUy4JbVehmOx+1uQ78iU+kK/0tg8seXp1lQxDqmaFI85kwCjCDMLp9kwXOLtIEtFblPpRKfer4AeCxCrnqR3eOvXNWmWHFsvsHxxljhN2JAoGBAKZYOsMz0FT57BFUyAWfpYthrwYRr4lllrCE4M/KbVfwi6Ly1pS+NmmmYbLIqRo1CRj3hiFVc0NgED3uF1gVEwN0qq2oG+bombWvPWWC9QvkRxD4GbFOuisxPGnLVblU6rYVlUVxS/MqNG012Y7NA8oIG9sCc5/JdVnevZWss7OVAoGBALpO8VusGWVQescaapahw+7U1ll7pukgg6JAtkUdXcTRGdfgryqqXxIzYLA4uc6su5YcDGjMppQr1YnH/VjeFedNTA5ZP/BeIO5AfhSI6LHO147YzCeGMxZSsnZOq5+pB1mORBR71eJWtePV6H+vaiAIm6HjKwzWvRzMyUX2poed";
        String url = "https://openapi.alipaydev.com/gateway.do";
        String appid = "2016080600177654";

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(url, appid, pri, "json", "UTF-8", pub, "RSA2");;
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
                request.setGrantType("authorization_code");
                request.setCode("6c47216a84444171bd75e03f2738SE61");
                //request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
                AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }

       AlipayUserInfoShareRequest request2 = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response2 = alipayClient.execute(request2, response.getAccessToken());
        if(response2.isSuccess()){

            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }


}
