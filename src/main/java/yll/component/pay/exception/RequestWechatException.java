package yll.component.pay.exception;


/**
 * 请求微信异常,只有在请求微信地址不通时才会抛出该异常
 * @author yll
 */
public class RequestWechatException extends Exception  {

    public RequestWechatException() {
        super("请求微信异常");
    }
}
