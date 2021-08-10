package yll.common.standard;

import java.util.Date;

/**
 * 业务对象(包含创建/修改时间信息)
 */
public interface CommonAttribute {

    /**
     * 获得创建者
     * @return 创建者ID
     */
    String getCreator();

    /**
     * 获得创建时间
     * @return 创建时间
     */
    Date getCreatedTime();

    /**
     * 获得修改者
     * @return 修改者ID
     */
    String getModifier();

    /**
     * 获得修改时间
     * @return 修改时间
     */
    Date getModifiedTime();

    /**
     * 获得删除者
     * @return 修改者ID
     */
    String getDeleter();

    /**
     * 获得删除时间
     * @return 修改者ID
     */
    Date getDeletedTime();

    /**
     * 设置创建者
     * @param 创建者ID
     */
    void setCreator(String creator);

    /**
     * 设置创建时间
     * @param 创建时间
     */
    void setCreatedTime(Date createdTime);

    /**
     * 设置修改者
     * @param 修改者ID
     */
    void setModifier(String modifier);

    /**
     * 设置修改时间
     * @param 修改时间
     */
    void setModifiedTime(Date modifiedTime);

    /**
     * 设置删除者
     * @param 删除者ID
     */
    void setDeleter(String deleter);

    /**
     * 设置删除时间
     * @param 删除时间
     */
    void setDeletedTime(Date deletedTime);
}
