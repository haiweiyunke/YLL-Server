package yll.entity;

import lombok.Data;
import yll.common.standard.Idable;

import java.io.Serializable;
import java.util.Date;

/** app版本更新记录 */
@SuppressWarnings("serial")
@Data
public class AppVersion implements Idable , Serializable {

    /** id */
    private String id;
    /** 版本类型*/
    private String versionType;
    /** 版本号 */
    private String versionName;
    /** 更新人 */
    private String creator;
    /** 创建时间 */
    private Date createdTime;
    /** 版本更新详情 */
    private String versionDetails;
    /** 文件地址 */
    private String fileUrl;
    /** 有效状态 */
    private Integer state;
    /** 版本更新次数 */
    private Integer versionCode;

}
