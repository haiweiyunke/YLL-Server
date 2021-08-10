package yll.component.file;

import java.io.Serializable;

import lombok.Data;
import yll.common.standard.Idable;

@SuppressWarnings("serial")
@Data
public class FileMeta implements Idable, Serializable {
    /** 文件ID */
    private String id;
    /** 文件名称 */
    private String name;
    /** 文件内容类型 */
    private String contentType;
    /** 文件尺寸 */
    private Long length;
    /** 存储空间 */
    private String bucket;
}
