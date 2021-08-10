package yll.component.app.sms;

import java.util.Random;

/**
 * 
 * @类描述 生成验证码
 * @创建人 张晓磊
 * @创建时间 2015-10-16 下午3:33:09
 */
public class VerifyCodeFactory {

	/**
	 * 产生随机的六位数
	 * 
	 * @return String
	 */
	public static String createSixCode() {
		Random rad = new Random();
		return rad.nextInt(1000000) + "";
	}

}
