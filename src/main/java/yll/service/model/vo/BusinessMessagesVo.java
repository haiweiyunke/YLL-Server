package yll.service.model.vo;

import lombok.Data;
import yll.entity.BusinessMessages;

import java.util.Date;

/**
 * 商务洽谈处理类
 */
@SuppressWarnings("serial")
@Data
public class BusinessMessagesVo extends BusinessMessages {

    //================传递参数======================
    private Date startTime;
    private Date endTime;

    //================返回参数======================
    private String nickname;
    private String negotiatorNickname;
    private String appCreatedTime;


}
