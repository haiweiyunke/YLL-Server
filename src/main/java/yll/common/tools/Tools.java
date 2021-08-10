package yll.common.tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import yll.common.exception.DefinitionException;


public class Tools {

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 
	 * @author lisw @Description: 检测字符串是否为空(null,"","null") @creatTime:2015-12-14 下午2:10:06 @param s:待检测的字符串 @return
	 *         boolean。不为空则返回true，为空返回false @modifier: @modifTime: @throws
	 */
	public static boolean isNotEmpty(String s) {
		return s != null && s.trim().length() > 0;
	}

	/**
	 * 
	 * @author lisw @Description: 判断是否为空，判断的类型有""，null，"null","NULL","-1"。符合条件的为空，返回true，不为空，返回false。 @creatTime:2016-7-27 下午6:20:08 @parameter：@param
	 *         s @parameter：@return boolean @modifier: @modifTime: @throws
	 */
	public static boolean isEmptyWithNull(String str) {
		boolean flag = (str != null && str.trim().length() > 0 && !"null".equals(str) && !"NULL".equals(str) && !"-1".equals(str));
		return !flag;
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str 字符串
	 * @param splitRegex 分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str 字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	//
	public static Integer[] splitInteger(String value) {
		Collection<Integer> ints = new HashSet<Integer>();
		for (String v : StringUtils.split(StringUtils.defaultString(value), ',')) {
			try {
				ints.add(Integer.parseInt(StringUtils.trim(v)));
			} catch (Exception e) {
				//ignore
			}
		}
		return ints.toArray(ArrayUtils.EMPTY_INTEGER_OBJECT_ARRAY);
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 数组去重
	 * 
	 * @param s
	 * @return
	 */
	public static String[] toDiffArray(String[] s) {
		Set<String> set = new HashSet<String>();
		for (String sa : s) {
			set.add(sa);
		}
		return set.toArray(new String[] {});
	}

	/**
	 * double 取小数点后两位
	 * 
	 * @param pDouble
	 * @return double
	 */
	public static double Number2(double pDouble) {
		BigDecimal bd = new BigDecimal(pDouble);
		BigDecimal bd1 = bd.setScale(2, bd.ROUND_HALF_UP);
		pDouble = bd1.doubleValue();
		long ll = Double.doubleToLongBits(pDouble);
		return pDouble;
	}

	/**
	 * 获取随机时间戳
	 * 
	 * @return
	 * @author lixianwei
	 * @date 2014-2-17 下午02:58:53
	 * @comment
	 */
	public static String getRandomTime() {

		Date tempDate0 = new Date();
		String s = tempDate0.toString();
		long a = tempDate0.parse(s);
		String date = String.valueOf(a);
		return date;
	}

	/**
	 * 获取某个字符在字符串中第n次出现的位置
	 * 
	 * @author lisw
	 * @param string ，字符串
	 * @param char_ ,判断的字符
	 * @param index ,出现的次数
	 * @date 2015-07-27
	 * @return int 出现的位置
	 */
	public static int getCharacterPosition(String string, String char_, int index) {
		// 这里是获取"_"符号的位置
		Matcher slashMatcher = Pattern.compile(char_).matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"#"符号第N次出现的位置
			if (mIdx == index) {
				break;
			}
		}
		return slashMatcher.start();
	}

	/**
	 * 
	 * @author zhaoyanfei @Description: 获取当前登录系统的用户IP @creatTime:2015-8-7 下午3:25:36 @return @throws UnknownHostException
	 *         String @modifier: @modifTime: @throws
	 */
	public static String getIpAddress() throws UnknownHostException {
		InetAddress address = InetAddress.getLocalHost();
		return address.getHostAddress();
	}

	/**
	 * 
	 * @author zhaoyanfei @Description: 格式化当前时间，格式化后的时间为2015-08-07 10:55:56 @creatTime:2015-8-7 上午10:55:56 @return
	 *         String @modifier:lisw,将日期改为时间 @modifTime: @throws
	 */
	public static String getCurrFormatTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @author lisw @Description: 格式化当前时间，格式化后的时间为20150807105556 @creatTime:2016-7-27 下午2:09:26 @parameter：@return
	 *         String @modifier: @modifTime: @throws
	 */
	public static String getCurrFormatTimeWithNoInterval() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * @author lisw @Description: 格式化当前时间到毫秒，格式化后的时间为20150808235959000
	 */
	public static String getCurrFormatTimeSSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @author lisw @Description: 格式化当前日期，格式化后的日期为;2016-07-27 @creatTime:2015-11-13 上午12:00:56 @param:null @return String，转换后的日期 @modifTime: @throws
	 */
	public static String getCurrFormatDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 
	 * @author lisw @Description: 格式化当前日期，格式化后的日期为：20160727 @creatTime:2016-7-27 下午2:05:23 @parameter：@return String @modifier: @modifTime: @throws
	 */
	public static String getCurrFormatDateWithNoInterval() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}

	/**
	 * @throws DefinitionException
	 * 
	 * @author lisw @Description: 获取指定长度的随机数 @param :n多少以内的随机数 @creatTime:2015-12-7 下午1:55:56 @return 生成的随机数 @modifTime: @throws
	 */
	public static String getRandomNum(int n) throws DefinitionException {
		String str = "";
		if (n > 0) {
			Random random = new Random();
			str = String.valueOf(random.nextInt(n));
		} else {
			throw new DefinitionException("您输入的参数必须大于0");
		}

		return str;
	}

	/**
	 * @throws DefinitionException @author lisw @Description: String转double类型 @creatTime:2015-11-26 上午12:00:00 @param
	 *         str：需要转换成double的String字符串 @return doubleThing：转换后的double数据。 @modifTime: @throws
	 */
	public static double strToDouble(String str) throws DefinitionException {
		double doubleThing = 0.0;
		if (str != null && str.trim().length() > 0) {
			doubleThing = Double.parseDouble(str);
		} else {
			throw new DefinitionException("faild");
		}
		return doubleThing;
	}

	/**
	 * @throws DefinitionException @author lisw @Description: String转int类型 @creatTime:2015-11-27 10:30:00 @param str：需要转换成int的String字符串 @return
	 *         intThing：转换后的int数据。 @modifTime: @throws
	 */
	public static double strToInt(String str) throws DefinitionException {
		int intThing = 0;
		if (str != null && str.trim().length() > 0) {
			intThing = Integer.parseInt(str);
		} else {
			throw new DefinitionException("faild");
		}
		return intThing;
	}

	/**
	 * @author lisw @Description: 计算两个double类型数据的和。因为直接使用double相加会损失精度问题。如果需要计算的两个参数是小数，可以使用这个方法 。 如果需要计算的两个参数是整数类型，不建议使用这种方法。 @creatTime:2015-11-26
	 *         15:08:00 @param d1 、d2：需要计算的两个double类型数据 @return 求和后的数据 @modifTime: @throws
	 */
	public static double doubleSum(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * @throws DefinitionException @author lisw @Description: 计算两个String类型数据的和。计算的结果为double类型。如果需要计算的两个参数是小数，可以使用这个方法。
	 *         如果需要计算的两个参数是整数类型，不建议使用这种方法。传递的String类型不能为空 @creatTime:2015-11-26 15:08:00 @param str1、str2：需要计算的两个String类型数据 @return
	 *         求和后double类型的数据 @modifTime: @throws
	 */
	public static double strSum(String str1, String str2) throws DefinitionException {
		BigDecimal bd1 = null;
		BigDecimal bd2 = null;
		double returnDouble = 0.0;
		if (str1 != null && str1.trim().length() > 0) {
			bd1 = new BigDecimal(str1);
		}
		if (str2 != null && str2.trim().length() > 0) {
			bd2 = new BigDecimal(str2);
		}
		if (bd1 != null && bd2 != null) {
			returnDouble = bd1.add(bd2).doubleValue();
		} else {
			throw new DefinitionException("");
		}

		return returnDouble;
	}

	/**
	 * @author lisw @Description: 保留小数点后n位小数，如将20.8657保留2位小数则转化为20.87 @creatTime:2015-11-26 上午11:27:56 @param beforDecimal
	 *         :需要运算的小数。n:保留小数点的位数，如需要保留2为小数，则将n设置为2 @return afterDecimal：运算后保留n位小数的小数。 @modifTime: @throws
	 */
	public static double getNumDecimal(double beforDecimal, int n) {
		BigDecimal bg = new BigDecimal(beforDecimal);
		double afterDecimal = bg.setScale(n, BigDecimal.ROUND_HALF_UP).doubleValue();
		return afterDecimal;
	}

	/**
	 * @author lisw @Description: 去掉小数点，保留整数。如将20.8657转化为21 @creatTime:2015-11-26 上午18:50:56 @param beforDecimal :需要运算的小数。 @return
	 *         afterDecimal：运算后保留n位小数的小数。 @modifTime: @throws
	 */
	public static int getNumInt(double beforInt) {
		double afterDecimal = getNumDecimal(beforInt, 0);
		int afterInt = (int) afterDecimal;
		return afterInt;
	}

	/**
	 * @throws DefinitionException @author lisw @Description: 计算集合中元素的和，集合元素中不能有空字符串。并且传递进去的元素全部为String类型 @creatTime:2015-11-27 10:06:56 @param
	 *         objList：存放需要计算的String类型的集合 @return returnObj：返回String类型，计算的结果。 @modifTime: @throws
	 */

	public static String addSumInt(List<String> objList) throws DefinitionException {
		//返回值
		String returnObj;

		BigDecimal bd0 = null;

		//便利循环集合
		for (int i = 0; i < objList.size(); i++) {
			String listi = objList.get(i);
			if (listi != null && listi.trim().length() > 0) {
				BigDecimal bd1 = new BigDecimal(listi);
				if (i == 0) {//第一次进入循环将第一个取出来的值赋给bd0
					bd0 = bd1;
				} else {
					//第二次以进入的循环，每次都在前一次结果的基础上相加本次的值。并将计算后的值赋给bd0。bd0即为本次计算后的值。
					bd1 = bd0.add(bd1);
					bd0 = bd1;
				}
			} else {
				throw new DefinitionException("您输入的数组中，可能有其中一个字符串为空！");
				//log.debug("计算有误：传入的字符串不能为空");
				//				bd0=new BigDecimal("-1");//如果传入的字符串中含有空，那么将之前计算的结果都赋为-1。以示错误。
				//				break;
			}
		}
		returnObj = bd0.toString();//转为String类型。
		return returnObj;
	}

	/**
	 * 
	 * 项目名称：ksh 包名称：com.byit.util 类描述： TODO 方法名称：sessionOutTime 方法描述：session过期跳登录 返回类型：int 传入参数： @param beforInt 传入参数： @return 创建人：ljg 创建时间：2015-12-23
	 * 下午2:14:22 修改人： 修改时间：2015-12-23 下午2:14:22 修改备注：
	 * @version
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	public static boolean sessionOutTime(HttpServletRequest request, HttpServletResponse response, Object object) {
		try {
			String loginUrl = request.getContextPath();
			if (object == null) {
				String str = "<script language='javascript'>alert('会话过期,请重新登录');" + "window.top.location.href='" + loginUrl + "';</script>";
				response.setContentType("text/html;charset=UTF-8");// 解决中文乱码

				PrintWriter writer = response.getWriter();
				writer.write(str);
				writer.flush();
				writer.close();

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}

		return false;
	}

	/**
	 * 
	 * @author whm @Description: 编码 @creatTime:2015-12-30 上午11:29:09 @param str @return String @modifier: @modifTime: @throws
	 */
	public static String enCodeStr(String str) {
		try {
			return new String(str.getBytes("iso-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @author whm @Description: 日期减天数 @creatTime:2016-1-8 上午11:30:30 @param date @return String @modifier: @modifTime: @throws
	 */
	public static String getDateBefore(Date date, Integer day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//减天
		cal.add(Calendar.DATE, day);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	/**
	 * @author weihm @Description: 两个数相除 保留两位小数 @creatTime:2016-3-28 上午10:41:24 @param sub @param visit @return String @modifier: @modifTime: @throws
	 */
	public static String getPro(String sub, String visit) {
		Integer iSub = Integer.parseInt(sub);
		Integer iVisit = Integer.parseInt(visit);
		float num;
		if ((iSub == 0 || iSub == null) || (iVisit == 0 || iVisit == null)) {

			num = 0;
		} else {
			num = (float) iSub / iVisit;
		}
		DecimalFormat df = new DecimalFormat("0.00%");//格式化小数 
		String s = df.format(num);//返回的是String类型 
		return s;
	}

	/**
	 * 
	 * @author lisw @Description: 如果传递的参数为""，null，"null","NULL","-1"。那么将其赋值为""。并返回。 @creatTime:2016-8-9 下午5:40:56 @parameter：@param
	 *         strParam @parameter：@return String @modifier: @modifTime: @throws
	 */
	public static String setValueIsNull(String strParam) {
		if (Tools.isEmptyWithNull(strParam)) {
			strParam = "";
		}
		return strParam;
	}

	//	 public static void main(String[] args) throws Exception {
	//		 
	//	 		log.debug("'"+setValueIsNull(" ")+"'");
	//	 		log.debug("'"+setValueIsNull("2")+"'");
	//	 		log.debug("'"+setValueIsNull("null")+"'");
	//	 		log.debug("'"+setValueIsNull("NULL")+"'");
	//	 		log.debug("'"+setValueIsNull("-1")+"'");
	//	 }

	
	/**
	 * 
	 * @author lisw @Description:从字符串中截取数字 @creatTime:2016-08-26 下午15:37:39 @parameter：@param 需要截取的字符串 @parameter：@return
	 *         截取后的数字 @modifier: @modifTime: @throws
	 */
	public static Integer getNumFromStr(String str) {
		Integer intStr = null;
		if (Tools.isNotEmpty(str)) {
			str = str.trim();
			String regEx = "[^0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			intStr = Integer.parseInt(m.replaceAll("").trim());
		}
		return intStr;
	}

	/**
	 * 
	 * @Title: subList
	 * @Description: 将list按blockSize大小等分，最后多余的单独一份
	 * @param @param list
	 * @param @param blockSize
	 * @param @return 设定文件
	 * @return List<List<T>> 返回类型
	 * @throws @author zhaoyanfei
	 */
	public static <T> List<List<T>> subList(List<T> list, int blockSize) {
		List<List<T>> lists = new ArrayList<List<T>>();
		if (list != null && blockSize > 0) {
			int listSize = list.size();
			if (listSize <= blockSize) {
				lists.add(list);
				return lists;
			}
			int batchSize = listSize / blockSize;
			int remain = listSize % blockSize;
			for (int i = 0; i < batchSize; i++) {
				int fromIndex = i * blockSize;
				int toIndex = fromIndex + blockSize;
				System.out.println("fromIndex=" + fromIndex + ", toIndex=" + toIndex);
				lists.add(list.subList(fromIndex, toIndex));
			}
			if (remain > 0) {
				System.out.println("fromIndex=" + (listSize - remain) + ", toIndex=" + (listSize));
				lists.add(list.subList(listSize - remain, listSize));
			}
		}
		return lists;
	}

	public static <T> void main(String[] args) throws Exception {
		List<T> list = new ArrayList<>();
		//	    	list.add(e);
		//	    	subList(list,100);
	}

}
