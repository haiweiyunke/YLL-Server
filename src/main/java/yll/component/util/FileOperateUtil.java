package yll.component.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.relucent.base.util.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import yll.common.exception.DefinitionException;
import yll.common.identifier.IdHelper;
import yll.common.tools.Tools;
import yll.service.model.YllConstants;

/**
 * 
 * 类名称 FileOperateUtil 类描述 文件上传下载工具类
 * 
 * @author zhaoyanfei、cc
 * @date 2015-11-5 下午1:01:46、2019-07-24 下午5:09:30
 * @modifier:
 * @modifierTime:
 * @version 1.1
 */
@Slf4j
public class FileOperateUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 *
	 * 方法名称: downLoad 方法描述: 文件下载方法
	 * @param filePath。下载的文件在服务器中的绝对路径，如：D:\Soft\Dev_Soft\apache-tomcat-7.0.55-windows-x64\webapps\xyreg_02\Upload/importFile/20160727/20160727134836_884_jqwidgets.docx
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception String
	 * @throws @author zhaoyanfei 创建时间 :2015-11-5下午1:02:31
	 * @modifier:
	 * @modifierTime:
	 * @version 1.0
	 */
	public static String download(String filePath, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		File attachFile = new File(filePath);
		boolean fileExisted = attachFile.exists();
		if (!fileExisted) { // 如果文件不存在，则显示错误信息
			response.setContentType("text/html; charset=gbk");
			// response.getWriter().println("下载失败，您下载的文件不存在！ <a href='javascript:window.history.back(-1)'><u>返回</u></a>");
		} else {

			// 创建要下载的文件的对象(参数为要下载的文件在服务器上的路径)
			File serverFile = new File(filePath);
			// 设置要显示在保存窗口的文件名，如果文件名中有中文的话，则要设置字符集，否则会出现乱码。另外，要写上文件后缀名
			// 浏览器乱码问题解决
			String fileName;
			String agent = request.getHeader("User-Agent");
			boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);

			if (isMSIE) {
				fileName = URLEncoder.encode(serverFile.getName(), "UTF-8");
			} else {
				fileName = new String(serverFile.getName().getBytes("UTF-8"), "ISO-8859-1");
			}

			int position = Tools.getCharacterPosition(fileName, "_", 2);// 获得fileName中第二次出现“_”的位置。
			fileName = fileName.substring(position + 1);

			// 该步是最关键的一步，使用setHeader()方法弹出"是否要保存"的对话框，打引号的部分都是固定的值，不要改变
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			// 以下四行代码经测试似乎可有可无，可能是我测试的文件太小或者其他什么原因。。。
			// response.setContentType("application/msword");
			response.setContentType("application/octet-stream");
			// 定义下载文件的长度 /字节
			long fileLength = serverFile.length();
			// 把长整形的文件长度转换为字符串
			String length = String.valueOf(fileLength);
			// 设置文件长度(如果是Post请求，则这步不可少)
			response.setHeader("content_Length", length);
			// 以上内容仅是下载一个空文件
			// 以下内容用于将服务器中相应的文件内容以流的形式写入到该空文件中
			// 获得一个 ServletOutputStream(向客户端发送二进制数据的输出流)对象
			OutputStream servletOutPutStream = response.getOutputStream();
			// 获得一个从服务器上的文件myFile中获得输入字节的输入流对象
			FileInputStream fileInputStream = new FileInputStream(serverFile);
			byte bytes[] = new byte[1024];// 设置缓冲区为1024个字节，即1KB
			int len = 0;
			// 读取数据。返回值为读入缓冲区的字节总数,如果到达文件末尾，则返回-1
			// while((len=fileInputStream.read(bytes))!=-1)
			// len=fileInputStream.read(bytes);
			while ((len = fileInputStream.read(bytes)) != -1) {
				// 将指定 byte数组中从下标 0 开始的 len个字节写入此文件输出流,(即读了多少就写入多少)
				servletOutPutStream.write(bytes, 0, len);
			}
			// log.debug("--文件下载完成---");
			servletOutPutStream.close();
			fileInputStream.close();
		}
		return null;
	}

	/**
	 * @Method previewFile
	 * @Description 浏览器浏览文件内容
	 * @Param
	 * @Return void
	 * @Throws
	 * @Author zhangHaoWei
	 * @DateTime 2017年6月8日-上午9:52:29
	 *
	 */
	public static void previewFile(String filePath, HttpServletRequest request, HttpServletResponse response) {
		OutputStream servletOutPutStream = null;
		FileInputStream fileInputStream = null;
		try {
			File previewFile = new File(filePath);
			if (!previewFile.exists()) { // 如果文件不存在，则显示错误信息
				response.setContentType("text/html; charset=gbk");
				response.getWriter().println("您浏览的文件不存在！ <a href='javascript:window.history.back(-1)'><u>返回</u></a>");
			} else {
				response.setContentType("*/*");
				// 定义下载文件的长度 /字节
				long fileLength = previewFile.length();
				// 把长整形的文件长度转换为字符串
				String length = String.valueOf(fileLength);
				// 设置文件长度(如果是Post请求，则这步不可少)
				response.setHeader("content_Length", length);
				String fileName = previewFile.getName();
				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				// 获得一个 ServletOutputStream(向客户端发送二进制数据的输出流)对象
				servletOutPutStream = response.getOutputStream();
				// 获得一个从服务器上的文件myFile中获得输入字节的输入流对象
				fileInputStream = new FileInputStream(previewFile);
				byte bytes[] = new byte[1024];// 设置缓冲区为1024个字节，即1KB
				int len = 0;
				while ((len = fileInputStream.read(bytes)) != -1) {
					// 将指定 byte数组中从下标 0 开始的 len个字节写入此文件输出流,(即读了多少就写入多少)
					servletOutPutStream.write(bytes, 0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != servletOutPutStream) {
					servletOutPutStream.close();
				}
				if (null != fileInputStream) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 *
	 * @author lisw @Description: 截取文件上传之前的文件名，如文件上传前的文件名是“工资.doc”，那么上传后通过url截取后的文件名为“工资.doc”，
	 *         这么做是因为文件在上传到服务器后文件名会进行更改，如可能会改为“20160727134836_884_工资.doc” @creatTime:2016-7-27 下午1:51:34 @parameter：@param
	 *         fileUrl：文件的绝对路径+文件名，如D:\Soft\Dev_Soft\apache-tomcat-7.0.55-windows-x64\webapps\xyreg_02\Upload/importFile/20160727/20160727134836_884_工资.doc @parameter：@return，截取后的文件名，如“工资.doc” @parameter：@throws
	 *         Exception String @modifier: @modifTime: @throws
	 */
	public static String getFileNameBeforeUploadFromUrl(String fileUrl) throws Exception {

		// 去掉文件路径的空格
		File tempFile = new File(fileUrl.trim());

		// 获取fileUrl路径中的文件名称
		String fileName = tempFile.getName();

		// 获得fileName中第二次出现“_”的位置
		int position = Tools.getCharacterPosition(fileName, "_", 2);

		// 截取url
		String newFileName = fileName.substring(position + 1);

		log.debug("befor upload file name is " + newFileName);
		return newFileName;
	}

	/**
	 *
	 * @author lisw @Description:
	 *         截取文件上传之前的文件名，如文件上传前的文件名是“工资.doc”，那么上传到服务器后文件名会进行更改，如可能会改为“20160727134836_884_工资.doc”。最终获取的就是“20160727134836_884_工资.doc” @creatTime:2016-7-27
	 *         下午1:53:14 @parameter：@param
	 *         fileUrl：文件的绝对路径+文件名，如D:\Soft\Dev_Soft\apache-tomcat-7.0.55-windows-x64\webapps\xyreg_02\Upload/importFile/20160727/20160727134836_884_工资.doc @parameter：@return
	 *         ：20160727134836_884_工资.doc @parameter：@throws Exception String @modifier: @modifTime: @throws
	 */
	public static String getFileNameAfterUploadFromUrl(String fileUrl) throws Exception {

		// 去掉文件路径的空格
		File tempFile = new File(fileUrl.trim());

		// 获取fileUrl路径中的文件名称
		String fileName = tempFile.getName();

		log.debug("after upload file name is " + fileName);
		return fileName;
	}

	/**
	 *
	 * @author lisw @Description: 获取文件的大小，单位：B（字节）。 @creatTime:2016-7-27 下午3:04:05 @parameter：@param file @parameter：@return
	 *         long。文件的大小 @modifier: @modifTime: @throws
	 */
	public static long getFileSize(MultipartFile file) {
		long fileSize = file.getSize();
		log.debug("file's size is " + fileSize);
		return fileSize;
	}

	/**
	 * @throws Exception
	 *
	 * @author lisw @Description: 文件是否超过指定大小 @creatTime:2016-7-27 下午3:26:53 @parameter：@param fileSize，需要判断的文件大小 @parameter：@param
	 *         fileRuleSize，指定的文件大小 @parameter：@return boolean，如果超过指定大小，返回true，否则返回false。 @modifier: @modifTime: @throws
	 */
	public static boolean isExceedRuleSize(long fileSize, long fileRuleSize) throws Exception {
		boolean isExceedRuleSize = false;
		// 规定需要判断的大小和规定大小都应该大于0
		if (fileSize > 0 && fileRuleSize > 0) {
			if (fileSize > fileRuleSize) {
				isExceedRuleSize = true;
			}
		} else {
			// 如果需要判断的大小和规定大小都不大于0或者其中有一个不大于0，抛出异常，告诉使用者两者必须同时大于0
			throw new DefinitionException("fileSize or fileRuleSize is wrong,all of them must greater than 0");
		}
		return isExceedRuleSize;
	}

	/**
	 *
	 * @author lisw @Description: 获取图片的宽与高，并将长、宽放置在map中返回，获取的时候通过map中的width、height获取到图片的宽与高 @creatTime:2016-7-27 下午2:54:45 @parameter：@param
	 *         file @parameter：@return 将宽、高放置在map中并返回。 @parameter：@throws Exception Map<String,Integer> @modifier: @modifTime: @throws
	 */
	public static Map<String, Integer> getDimension(MultipartFile file) throws Exception {
		Map<String, Integer> dimensionMap = new HashMap<String, Integer>();
		if (!file.isEmpty()) {
			// 将文件转换为图像
			BufferedImage image = ImageIO.read(file.getInputStream());
			if (image != null) {
				int width = image.getWidth();// 上传图片的宽度
				int height = image.getHeight();// 上传图片的高度
				log.debug("image's width is " + width + " and height is " + height);
				// 将图片的宽、高放在map中
				dimensionMap.put("width", width);
				dimensionMap.put("height", height);
			}
		} else {
			// 如果file为空，抛出异常
			throw new DefinitionException("file is empty!Please check");
		}
		return dimensionMap;
	}

	/**
	 *
	 * @author lisw @Description: 获取文件的扩展名，如“工资.doc”。获取到的扩展名为“doc” @creatTime:2016-7-27 下午3:09:24 @parameter：@param fileName,文件名 @parameter：@return
	 *         String。扩展名/后缀名 @modifier: @modifTime: @throws
	 */
	public static String getFileExtensionName(String fileName) {
		String fileExtensionName = "";
		if (Tools.isNotEmpty(fileName)) {
			fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		}
		return fileExtensionName;
	}

	/**
	 * @Method file2String
	 * @Description 读取文件内容并转为字符串 (建议: 纯文本文件)
	 * @Param filePath
	 * @Return String
	 * @Throws
	 * @Author zhangHaoWei
	 * @DateTime 2017年5月27日-下午3:21:29
	 *
	 */
	public static String file2String(String filePath) {
		StringBuffer file2Str = new StringBuffer();
		File file = new File(filePath);
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			while (br.read() != -1) {
				String readLine = br.readLine();
				file2Str.append(readLine + "\r\n");
				// file2Str.append(readLine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				isr.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file2Str.toString();
	}

	public static void main(String[] args) throws Exception {
//		log.debug(isExceedRuleSize(3, 1));
	}

	/**
	 *
	 * @author ruanhaidong @Description: 合同文件上传，利用Spring MVC进行文件上传 @creatTime:2017-8-4 下午19:40:53 @parameter：@param file。上传的文件 @parameter：@param
	 *         request @parameter：@param response @parameter：@return
	 *         String。上传后的文件及路径，如：D:\Soft\Dev_Soft\apache-tomcat-7.0.55-windows-x64\webapps\xyreg_02\Upload/importFile/20160727/20160727134836_884_jqwidgets.docx @modifier: @modifTime: @throws
	 */
	public static String contractUpload(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		log.debug("start deal file");
		// app 的话传给一个类型确定是app的然后做操作
		String url = null;
		String realPath = "E:/logistics/contractUpload/";
		// 当前项目所在的路径+项目名+upload。如“D:\Soft\Dev_Soft\apache-tomcat-7.0.55-windows-x64\webapps\xyreg_02\Upload/”
		/*
		 * realPath = request.getSession().getServletContext().getRealPath("/") + "Upload/";
		 */
		// 指定文件类型
		response.setContentType("text/html; charset=UTF-8");
		// 创建文件夹
		// 格式化当前日期，年月日时上传文件的第三级文件夹
		/*
		 * String ymd=Tools.getCurrFormatDateWithNoInterval(); realPath += ymd + "/";
		 */
		String fileType = "pdf,jpg,png,jpeg,JPG,PNG,JPEG";
		if (file.getSize() == 0) {
			return "文件不能为空！";
		}
		File saveDirFile = new File(realPath);
		if (!saveDirFile.exists()) {
			log.debug("start create folder");
			saveDirFile.mkdirs();
			log.debug("folder create complete");
		}
		// fileName.indexOf(".pdf")!=-1
		// 获得上传文件的文件名
		String fileName = file.getOriginalFilename();
		String newFileName = "";// 定义上传到服务器的文件名，
		String suffix = ""; // 后缀名
		suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 获得上传文件的扩展名
		if (!file.isEmpty()) {
			if (Arrays.<String>asList(fileType.split(",")).contains(suffix)) {
				// 重新设置上传的文件名：当前时间_1000以内随机数_上传前的文件名。

				newFileName = Tools.getCurrFormatTimeWithNoInterval() + fileName;
				/// newFileName = Tools.getCurrFormatTimeWithNoInterval() + "_" + new Random().nextInt(1000) +"_"+fileName;
				// 获取文件上传的最终路径
				url = realPath + newFileName;
				try {
					log.debug("start upload file!");
					file.transferTo(new File(realPath + newFileName));
					log.debug("file upload success,and after upload file name is " + newFileName);
				} catch (Exception e) {
					log.debug("file upload fail!");
					e.printStackTrace();
					return null;
				}

			} else {
				return "文件类型不正确，必须为" + fileType + "的文件！";
			}
		}
		return url;
	}

	/**
	 * 复制单个文件(原名复制)
	 * @param oldPathFile 准备复制的文件源
	 * @param targetPath 拷贝到新绝对路径带文件名(注：目录路径需带文件名)
	 * @return
	 */
	public static void CopySingleFileTo(String oldPathFile, String targetPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			String targetfile = targetPath + File.separator + oldfile.getName();
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(targetfile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除临时文件 folderPath：要删除文件的路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			// java.io.File myFilePath = new java.io.File(filePath);
			// myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				// delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 路径转换为文件对象(会验证文件路径的安全性)
	 * @param path 路径(相对路径), fileStoreDirectory 磁盘根目录
	 * @return 文件对象
	 */
	private File toAbsoluteFile(String fileStoreDirectory, String path) {
		return new File(fileStoreDirectory, path);
	}

	/**
	 * 获得当前日期字符串(年月日)
	 * @return 日期字符串
	 */
	@SuppressWarnings("unused")
	private static String getNowDate() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
	}

	/**
	 * 删除单个文件
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		System.out.println(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 新加的多文件上传类, 与以前不同的地方是, 只返回文件保存绝对路径的部分路径及新保存的文件名
	 * 返回文件路径例如 upload/files/card/20150808/20150808235959000_8888
	 * @param diskPath 磁盘路径[绝对路径],配置文件中读取,所有上传文件的父目录； realPath  要存放的具体文件夹路径（不包含磁盘符号）
	 */
	public static Object uploadMore(MultipartFile files[], String diskPath, String realPath, HttpServletRequest request,
			HttpServletResponse response, Object... objs) throws FileUploadException, IOException {
		log.debug("开始处理文件上传");

		// 最终要返回的文件路径
		String paths[] = new String[files==null?0:files.length];
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile f = files[i];
				if (!f.isEmpty()) {
					String fileUrl = "";
					// 指定文件类型
					response.setContentType("text/html; charset=UTF-8");
					//三级目录,文件所属类别
					String fileterName = "";
					String fileType = request.getParameter("type");// 获取上传类型定义文件夹
					if (fileType == null) {
						fileterName = "other";
					} else if (fileType.equals("1")) {// 封面图片文件夹
						fileterName = "cover";
					} else if (fileType.equals("2")) {// 一版图片文件夹(2张)
						fileterName = "general";
					} else if (fileType.equals("3")) {// 原图
						fileterName = "original";
					} else if (fileType.equals("4")) {// 卡片管理
						fileterName = "card";
					} else if (fileType.equals("5")) { // 用户（艺术机构、画家村）信息照片
						fileterName = "userInformation";
					} else if (fileType.equals("6")) {// 头像
						fileterName = "headImage";
					}

					//继续拼目录,加上日期
					realPath += fileterName + "/" + Tools.getCurrFormatDateWithNoInterval()+"/";
					fileUrl = realPath;

					// 给上传到服务器的文件命个新的名字，
					String newFileName = "";
					newFileName = Tools.getCurrFormatTimeSSS() + "_" + new Random().nextInt(10000);

					//要返回的路径
					fileUrl = fileUrl + newFileName;
					paths[i] = fileUrl;
					//存文件（diskPath-磁盘路径[绝对路径],， realPath-文件要存放的路径(文件夹)）
					realPath = diskPath + realPath;
					File saveDirFile = new File(realPath);
					if (!saveDirFile.exists()) {
						log.debug("start create folder");
						saveDirFile.mkdirs();
						log.debug("folder create complete");
					}
					try {
						log.debug("start upload file!");
						f.transferTo(new File(realPath + newFileName));
						log.debug("file upload success,and after upload file name is " + newFileName);
					} catch (Exception e) {
						log.debug("file upload fail!");
						e.printStackTrace();
					}
				}
			}
		}
		if (paths.length>0) {
			return Result.ok(paths);// D:/logistics/Upload/importFile/SignOver/
		} else {
			return Result.error("上传失败！");
		}
	}

	/**
	 * @Description 浏览器预览文件
	 * @param filePath 文件相对路径
	 * @param diskPath 磁盘路径
	 */
	public static void preview(String filePath,String diskPath, HttpServletRequest request, HttpServletResponse response){
		OutputStream servletOutPutStream = null;
		FileInputStream fileInputStream = null;
		try {
			File previewFile = new File(diskPath+filePath);
			if(!previewFile.exists()) { //如果文件不存在，则显示错误信息
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().println("您浏览的文件不存在！ <a href='javascript:window.history.back(-1)'><u>返回</u></a>");
			}else{
				// 定义下载文件的长度 /字节
				long fileLength = previewFile.length();
				// 把长整形的文件长度转换为字符串
				String length = String.valueOf(fileLength);
				// 设置文件长度(如果是Post请求，则这步不可少)
				response.setHeader("content_Length", length);
				String fileName = previewFile.getName();
				//获取contentType
				Path path = Paths.get(fileName);
				String contentType = Files.probeContentType(path);
//				contentType = "inline";
//				response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
				response.setHeader("Content-Disposition", contentType + "; filename=" + fileName);
				response.setHeader("Content-Type", contentType + ";charset=UTF-8");
				// 获得一个 ServletOutputStream(向客户端发送二进制数据的输出流)对象
				servletOutPutStream = response.getOutputStream();
				// 获得一个从服务器上的文件myFile中获得输入字节的输入流对象
				fileInputStream = new FileInputStream(previewFile);
				byte bytes[] = new byte[1024];// 设置缓冲区为1024个字节，即1KB
				int len = 0;
				while ((len = fileInputStream.read(bytes)) != -1) {
					// 将指定 byte数组中从下标 0 开始的 len个字节写入此文件输出流,(即读了多少就写入多少)
					servletOutPutStream.write(bytes, 0, len);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != servletOutPutStream){
					servletOutPutStream.close();
				}
				if(null != fileInputStream){
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * 支持断点续传
     * @param filePath
     * @param diskPath
     * @param request
     * @param response
     */
	public static void rang(String filePath,String diskPath, HttpServletRequest request, HttpServletResponse response){
        String path   = request.getServletPath();
        String recordPath = "";
        path = diskPath+filePath;
        BufferedInputStream bis = null;
       try {
         File file = new File(path);
         if (file.exists()) {
           long p = 0L;
           long toLength = 0L;
           long contentLength = 0L;
           int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
           long fileLength;
           String rangBytes = "";
           fileLength = file.length();

           // get file content
           InputStream ins = new FileInputStream(file);
           bis = new BufferedInputStream(ins);

           // tell the client to allow accept-ranges
           response.reset();
           response.setHeader("Accept-Ranges", "bytes");

           // client requests a file block download start byte
           String range = request.getHeader("Range");
           if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
             response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
             rangBytes = range.replaceAll("bytes=", "");
             if (rangBytes.endsWith("-")) {   // bytes=270000-
               rangeSwitch = 1;
               p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
               contentLength = fileLength - p;   // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
             } else { // bytes=270000-320000
               rangeSwitch = 2;
               String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
               String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
               p = Long.parseLong(temp1);
               toLength = Long.parseLong(temp2);
               contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
             }
           } else {
             contentLength = fileLength;
           }

           // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
           // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
           response.setHeader("Content-Length", new Long(contentLength).toString());

           // 断点开始
           // 响应的格式是:
           // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
           if (rangeSwitch == 1) {
             String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
                 .append(new Long(fileLength - 1).toString()).append("/")
                 .append(new Long(fileLength).toString()).toString();
             response.setHeader("Content-Range", contentRange);
             bis.skip(p);
           } else if (rangeSwitch == 2) {
             String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
             response.setHeader("Content-Range", contentRange);
             bis.skip(p);
           } else {
             String contentRange = new StringBuffer("bytes ").append("0-")
                 .append(fileLength - 1).append("/")
                 .append(fileLength).toString();
             response.setHeader("Content-Range", contentRange);
           }

           String fileName = file.getName();
//           response.setContentType("application/octet-stream");
//           response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

			 //获取contentType
			 Path contentPath = Paths.get(fileName);
			 String contentType = Files.probeContentType(contentPath);
			 response.addHeader("Content-Disposition", contentType + ";filename=" + fileName);
			 response.setHeader("Content-Type", contentType + ";charset=UTF-8");

           OutputStream out = response.getOutputStream();
           int n = 0;
           long readLength = 0;
           int bsize = 1024;
           byte[] bytes = new byte[bsize];
           if (rangeSwitch == 2) {
             // 针对 bytes=27000-39000 的请求，从27000开始写数据
             while (readLength <= contentLength - bsize) {
               n = bis.read(bytes);
               readLength += n;
               out.write(bytes, 0, n);
             }
             if (readLength <= contentLength) {
               n = bis.read(bytes, 0, (int) (contentLength - readLength));
               out.write(bytes, 0, n);
             }
           } else {
             while ((n = bis.read(bytes)) != -1) {
               out.write(bytes,0,n);
             }
           }
           out.flush();
           out.close();
           bis.close();
         } else {
           /*if (logger.isDebugEnabled()) {
             logger.debug("Error: file " + path + " not found.");
           }*/
         }
       } catch (IOException ie) {
         // 忽略 ClientAbortException 之类的异常
       } catch (Exception e) {
//         logger.error(e.getMessage());
       }
    }

	//========================== cc定义 ================================
	/**
	 * 文件上传
	 * @throws ParseException
	 * @throws IOException
	 */
	public static Result<?> upload(MultipartFile file, String fileStoreDirectory) throws  IOException {
		String diskPath = formateUrl(file, YllConstants.FILE_UPLOAD);
		File dest = new File(fileStoreDirectory, diskPath);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		dest.createNewFile();
		dest.setWritable(true, true);
		dest.setReadable(true, false);
		FileOutputStream out = new FileOutputStream(dest);
		out.write(file.getBytes());
		out.flush();
		out.close();
//        String filePath = dest.getAbsolutePath();
		return Result.ok(diskPath);
	}

	/**
	 *  格式化路径
	 * @param
	 * @return
	 */
	public static String formateUrl(MultipartFile file, String dir){
		String originalFilename = file.getOriginalFilename();
		//split正则分割
		String suffix = originalFilename.split("\\.")[1];
		String filePath = dir.concat(IdHelper.nextId()).concat(".").concat(suffix);
		return filePath;
	}

	/**
	 *  格式化文件名
	 * @param
	 * @return
	 */
	public static String formateFileName(MultipartFile file){
		String originalFilename = file.getOriginalFilename();
		//split正则分割
		String suffix = originalFilename.split("\\.")[1];
		String fileName = IdHelper.nextId().concat(".").concat(suffix);
		return fileName;
	}

	/**
	 *  获取服务器地址
	 * @param
	 * @return
	 */
	public static String serverUrlPrefix(HttpServletRequest request){
		String requestUrl = request.getScheme() //当前链接使用的协议
				+"://" + request.getServerName()//服务器地址
				+ ":" + request.getServerPort()//端口号
				//+ request.getContextPath() //应用名称
				//+ request.getServletPath() //请求的相对url
				//+ "?" + request.getQueryString(); //请求参数
				;
		return requestUrl;
	}

}