package yll.service.model.vo;

import lombok.Data;
import yll.entity.TaskCelebrity;
import yll.entity.TaskCelebrityProducts;

/**
 * 任务达人商品处理类
 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrityProductsVo extends TaskCelebrityProducts {

    //================返回参数======================
    /** 任务达人商品id */
    private String tcpId;
    /** 商品名称 */
    private String name;

}
