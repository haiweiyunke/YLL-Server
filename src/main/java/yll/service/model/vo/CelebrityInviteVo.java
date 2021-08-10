package yll.service.model.vo;

import lombok.Data;
import yll.entity.CelebrityInvite;

import java.util.List;

/**
 * 达人邀请处理类
 */
@SuppressWarnings("serial")
@Data
public class CelebrityInviteVo extends CelebrityInvite {

    //================返回参数======================
    private String mcnId;
    private String mcnName;
    private String fenNum;
}
