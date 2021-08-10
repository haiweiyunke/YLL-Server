package yll.component.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.relucent.base.util.web.WebUtil.DownloadFile;

import lombok.extern.slf4j.Slf4j;
import yll.common.configuration.CustomProperties;
import yll.common.identifier.IdHelper;

/**
 * 文件存储管理
 */
@Component
@Slf4j
public class FileStoreManager {
    // ==============================Fields===========================================
    @Autowired
    private CustomProperties customProperties;

    // ==============================Methods==========================================
    /**
     * 创建文件
     * @param name 文件名称
     * @param bucket 存储空间
     * @param contentType 文件内容类型
     * @return 文件信息
     */
    public FileMeta create(String name, String bucket, String contentType) {
        FileMeta meta = new FileMeta();
        meta.setId(IdHelper.nextId());
        meta.setName(name);
        meta.setContentType(contentType);
        meta.setBucket(bucket);
        meta.setLength(0L);
        return meta;
    }

    /**
     * 删除文件
     * @param meta 文件信息
     */
    public void delete(FileMeta meta) {
        if (meta == null) {
            return;
        }
        File file = getAbsoluteFile(meta);
        if (!file.delete()) {
            file.deleteOnExit();
        }
        FileUtils.deleteQuietly(file);
    }

    /**
     * 获得下载实体
     * @param meta 文件信息
     * @return 下载实体
     */
    public DownloadFile getDownloadFile(FileMeta meta) {
        if (meta == null) {
            return getNullDownloadFile();
        }
        final File file = getAbsoluteFile(meta);
        if (file == null) {
            return getNullDownloadFile();
        }
        return new DownloadLocalFile(meta, file);
    }

    /**
     * 获得一个空文件
     * @return 空文件
     */
    public DownloadFile getNullDownloadFile() {
        return new NullDownloadFile();
    }

    /**
     * 获得文件输出流
     * @param meta 文件信息
     * @return 文件输出流
     */
    public OutputStream openOutputStream(FileMeta meta) throws IOException {
        File file = getAbsoluteFile(meta);
        file.getParentFile().mkdirs();
        file.createNewFile();
        file.setWritable(true, true);
        file.setReadable(true, false);
        return openOutputStream(file);
    }

    /**
     * 获得文件输入流
     * @param meta 文件信息
     * @return 文件输入流
     */
    public InputStream openInputStream(FileMeta meta) {
        File file = getAbsoluteFile(meta);
        return openInputStream(file);
    }

    /**
     * 将文件写入到输出流中
     * @param meta 文件信息
     * @param output 输出流
     */
    public void writeTo(FileMeta meta, OutputStream output) {
        File file = getAbsoluteFile(meta);
        writeTo(file, output);
    }

    /**
     * 获得文件输出流
     * @param file 文件
     * @return 文件输出流
     */
    private static OutputStream openOutputStream(File file) throws IOException {
        OutputStream output = null;
        try {
            // 存储的文件使用ZIP格式进行编码
            return new GZIPOutputStream(output = FileUtils.openOutputStream(file));
        } catch (Exception e) {
            // 出现异常，需要释放文件资源
            IOUtils.closeQuietly(output);
            if (e instanceof IOException) {
                throw (IOException) e;
            } else {
                throw new IOException(e);
            }
        }
    }

    /**
     * 获得文件输入流
     * @param file 文件
     * @return 文件输入流
     */
    private static InputStream openInputStream(File file) {
        if (file != null && file.canRead()) {
            InputStream input = null;
            try {
                // 存储的文件使用ZIP格式解码
                return new GZIPInputStream(input = FileUtils.openInputStream(file));
            } catch (Exception e) {
                // 出现异常，需要释放文件资源
                IOUtils.closeQuietly(input);
                if (e instanceof ZipException) {
                    log.error("File[{}] Store Format Error!", file);
                } else {
                    log.error("!", e);
                }
            }
        }
        log.warn("File[{}] notCanRead!", file);
        return new ByteArrayInputStream(new byte[0]);
    }

    /**
     * 将文件写入到输出流中
     * @param file 文件
     * @param output 输出流
     */
    private static void writeTo(File file, OutputStream output) {
        if (file == null || !file.canRead()) {
            return;
        }
        byte[] buffer = new byte[4096];
        InputStream input = openInputStream(file);
        try {
            for (int n = 0; -1 != (n = input.read(buffer)); output.write(buffer, 0, n));
        } catch (IOException e) {
            log.error("!", e);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }

    /**
     * 获得绝对路径文件
     * @param path 文件路径
     * @return 绝对路径文件
     */
    private File getAbsoluteFile(final FileMeta meta) {
        final String id = meta != null ? meta.getId() : null;
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        final String bucket = meta.getBucket();
        final String directory = StringUtils.isEmpty(bucket) ? StringUtils.EMPTY : bucket + "/";
        final String path;
        if (id.length() > 20) {
            path = directory + id.substring(0, 4) + "/" + id.substring(4, 8) + "/" + id;
        } else {
            path = directory + "_/" + id;
        }
        return new File(customProperties.getFileStoreDirectory(), path);
    }

    // ==============================InnerClass=======================================
    private static final class NullDownloadFile implements DownloadFile {
        @Override
        public String getName() {
            return "null";
        }

        @Override
        public String getContentType() {
            return "text/plain";
        }

        @Override
        public void writeTo(OutputStream output) {}

        @Override
        public long getLength() {
            return 0;
        }
    }

    private static final class DownloadLocalFile implements DownloadFile {
        private final String name;
        private final String contentType;
        private final Long length;
        private final File file;

        public DownloadLocalFile(FileMeta meta, File file) {
            this.name = meta.getName();
            this.contentType = meta.getContentType();
            this.length = meta.getLength();
            this.file = file;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getContentType() {
            return contentType;
        }

        @Override
        public long getLength() {
            return length;
        }

        @Override
        public void writeTo(OutputStream output) {
            FileStoreManager.writeTo(file, output);
        }
    }
}
