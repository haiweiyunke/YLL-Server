package yll.service;

import com.github.relucent.base.util.expection.ExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yll.common.security.app.AppSecuritys;
import yll.entity.*;
import yll.mapper.CustomerMapper;
import yll.mapper.InformationMapper;
import yll.service.model.YllConstants;

/**
 * 公用服务层
 */
@Service
public class CommonService {

    // ==============================Fields===========================================
    @Autowired
    private DicService dicService;
    @Autowired
    private DicTypeService dicTypeService;
    @Autowired
    private CustomerMapper customerService;
    @Autowired
    private CustomerHistoriesService customerHistoriesService;
    @Autowired
    private DynamicService dynamicService;
    @Autowired
    private InternetCelebrityService internetCelebrityService;

    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================

    /**
     * 处理操作数
     * @param id               操作对象id
     * @param type          操作的表
     * @param flag          参照常量（点赞、收藏、分享、浏览）
     * @param number     参照常量(+1  或 -1)
     */
    public void count(String id, String type, String flag, Integer number) {

        String dicId = type;
        type = dic(type);
        //处理
        if(YllConstants.TABLE_CUSTOMER.equals(type)){
            Customer entity = customerService.getById(id);
            if(YllConstants.YLL_LIKES.equals(flag)){
                //点赞
                entity.setLikes(entity.getLikes() + number < 0 ? 0 : entity.getLikes() + number);
            } else if(YllConstants.YLL_COLLECTS.equals(flag)){
                //收藏
                entity.setCollects(entity.getCollects() + number < 0 ? 0 : entity.getCollects() + number);
            } else if(YllConstants.YLL_SHARE.equals(flag)){
                //分享
                entity.setShare(entity.getShare() + number < 0 ? 0 : entity.getShare() + number);
            }
            customerService.update(entity);
        } else if(YllConstants.TABLE_DYNAMIC.equals(type)){  //搞一下这个地方
            Dynamic entity = dynamicService.getById(id);
            if(YllConstants.YLL_LIKES.equals(flag)){
                //点赞
                entity.setLikes(entity.getLikes() + number < 0 ? 0 : entity.getLikes() + number);
            } else if(YllConstants.YLL_COLLECTS.equals(flag)){
                //收藏
                entity.setCollects(entity.getCollects() + number < 0 ? 0 : entity.getCollects() + number);
            } else if(YllConstants.YLL_SHARE.equals(flag)){
                //分享
                entity.setShare(entity.getShare() + number < 0 ? 0 : entity.getShare() + number);
            }
            dynamicService.update(entity);
            //修改达人点赞数
            Customer cust = new Customer();
            cust = customerService.getById(entity.getCreator());
            if(null != cust){
                cust.setLikes(cust.getLikes() + number < 0 ? 0 : cust.getLikes() + number);
                customerService.update(cust);
            }
        } else{
            throw ExceptionHelper.prompt("参数type错误");
        }
        //新增历史记录
//        history(id, type, dicId);
    }

    /**
     * 获取类型
     * @param type
     * @return
     */
    public String dic(String type) {
        Dic dic = dicService.getByCode(type);
        if(null != dic && !YllConstants.TABLE_HUNDRED_DEDICATES.equals(dic.getTargetId() )){
            //type为字典表code时，执行此方法查找字典类型表code
            String targetId = dic.getTargetId();
            DicType dicType = dicTypeService.getByCode(targetId);
            type = dicType.getCode();
        }
        return type;
    }

    /**
     * 当前Service使用
     * @param id
     * @param type
     * @param dicId
     */
    public void history(String id, String type, String dicId){
        customerHistoriesService.operation(id, type, dicId);
    }

    /**
     * 历史记录-外部调用
     * @param id
     * @param type
     */
    public void history(String id, String type){
        String dicId = type;
        type = dic(type);
        history(id, type, dicId);
    }

}
