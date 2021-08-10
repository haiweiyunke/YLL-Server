package yll.controller;

import com.github.relucent.base.util.model.Result;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yll.common.configuration.CustomProperties;
import yll.common.identifier.IdHelper;
import yll.component.util.FileOperateUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件处理
 */
@RestController
@RequestMapping(value = "/rest/file")
public class FileController {
    // ==============================Fields===========================================
    // @Autowired
    // private FileService fileService;
    @Autowired
    private CustomProperties customProperties;

    // ==============================Methods==========================================
    /**
     * [GET] /rest/file/read-file-to-text <br>
     * 读取文本文件
     */
    @PostMapping(value = "/read-file-to-text")
    public Result<?> readFileToText(MultipartFile file) {
        try {
            String data = IOUtils.toString(file.getBytes(), "UTF-8");
            return Result.ok(data);
        } catch (Exception e) {
            return Result.error();
        }
    }

    /**
     * [GET] /rest/file/read-file-to-base64 <br>
     * 读取文件，得到base64格式字符串
     */
    @PostMapping(value = "/read-file-to-base64")
    public Result<?> readFileToBase64(MultipartFile file) {
        try {
            String data = Base64.encodeBase64String(file.getBytes());
            return Result.ok(data);
        } catch (IOException e) {
            return Result.error();
        }
    }

    /**
     * 文件上传
     * [POST] /rest/file/upload <br>
     *
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping(value = "/upload")
    public Result<?> upload(MultipartFile file) throws  IOException {
        return FileOperateUtil.upload(file, customProperties.getFileStoreDirectory());
    }

    /**
     * 富文本上传使用
     * [POST] /rest/file/wangEditorUpload <br>
     * @param file
     * @return
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping(value = "/wangEditorUpload")
    public Result<?> wangEditorUpload(@RequestParam(value="wangEditor")MultipartFile file) throws IOException {
        return this.upload(file);    //将图片地址返回
    }

    /**
     * 文件预览
     * [GET] /rest/file/preview <br>
     *
     * @thrs ParseException
     * @throws IOException
     */
    @RequestMapping(value = "/preview")
    public void preview(HttpServletRequest request, HttpServletResponse response, String path) {
        FileOperateUtil.preview(path, customProperties.getFileStoreDirectory(), request, response);
    }

}
