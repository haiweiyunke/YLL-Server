package yll.service.model.vo;

import lombok.Data;
import yll.entity.TaskEnterpriseComplaint;

/**
 * 任务企业主申诉处理类
 */
@SuppressWarnings("serial")
@Data
public class TaskEnterpriseComplaintVo extends TaskEnterpriseComplaint {

    //================返回参数======================
    /** 名称 */
    private String name;
    /** 完成时间  */
    private String completeTimeStr;
    /** 企业主名称  */
    private String enterpriseName;
}
