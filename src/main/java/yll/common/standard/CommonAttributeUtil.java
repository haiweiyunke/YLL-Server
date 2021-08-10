package yll.common.standard;

import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.util.lang.DateUtil;
import yll.common.security.app.AppPrincipal;

/**
 * 基础对象工具类
 * @author cc
 */
public class CommonAttributeUtil {

    /**
     * 设置创建信息
     * @param record 记录对象
     * @param principal 登录人
     */
    public static void setCreated(CommonAttribute record, Principal principal) {
        record.setCreator(principal.getUserId());
        record.setCreatedTime(DateUtil.now());
    }

    /**
     * 设置修改信息
     * @param record 记录对象
     * @param principal 登录人
     */
    public static void setUpdated(CommonAttribute record, Principal principal) {
        record.setModifier(principal.getUserId());
        record.setModifiedTime(DateUtil.now());
    }

    /**
     * 设置删除信息
     * @param record 记录对象
     * @param principal 登录人
     */
    public static void setDeleted(CommonAttribute record, Principal principal) {
        record.setDeleter(principal.getUserId());
        record.setDeletedTime(DateUtil.now());
    }


    /**
     * 设置创建信息-app使用
     * @param record 记录对象
     * @param principal 登录人
     */
    public static void setCreated(CommonAttribute record, AppPrincipal principal) {
        record.setCreator(principal.getCustomerId());
        record.setCreatedTime(DateUtil.now());
    }

    /**
     * 设置修改信息-app使用
     * @param record 记录对象
     * @param principal 登录人
     */
    public static void setUpdated(CommonAttribute record, AppPrincipal principal) {
        record.setModifier(principal.getCustomerId());
        record.setModifiedTime(DateUtil.now());
    }

    /**
     * 设置创建信息
     * @param record 记录对象
     * @param cid 登录人
     */
    public static void setCreated(CommonAttribute record, String cid) {
        record.setCreator(cid);
        record.setCreatedTime(DateUtil.now());
    }

}
