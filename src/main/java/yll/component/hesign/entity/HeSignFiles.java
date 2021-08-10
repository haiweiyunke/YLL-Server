package yll.component.hesign.entity;

import lombok.Data;

import java.io.File;

/**
 * 和签文件实体
 */
@Data
public class HeSignFiles {

    /** 表单名即为 files[0][sn]  */
    private String fileSn;

    /** 合同名称 */
    private String filesName;

    /** 文件流 正常上传文件方式 */
    private File filesFile;

    /** 文件流 正常上传文件方式名称 */
    private String filesFileName;

    /** 可见权限：* 表示项目下的所有签约人可见; */
    private String filesPermission;

}
