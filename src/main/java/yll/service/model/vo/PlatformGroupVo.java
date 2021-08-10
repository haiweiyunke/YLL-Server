package yll.service.model.vo;

import lombok.Data;
import yll.entity.Enterprise;
import yll.entity.PlatformGroup;

/**
 * DH
 * 平台自定义属性-中间表
 */
@SuppressWarnings("serial")
@Data
public class PlatformGroupVo extends PlatformGroup {
    /** 平台名称 */
    private String pname;
    //================返回参数======================
    //TODO  继续新建PlatformAttributeKey、PlatformAttributeValue的xml和mapper（附带关联查询）
    // 可参考https://www.cnblogs.com/zitai/p/11830698.html ，mybatis的resultMap的 <collection> 标签
    // 以及自定义平台所有的后台管理、前台接口
}
