package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.identifier.IdHelper;
import yll.common.standard.CommonAttributeUtil;
import yll.component.excel.vo.PlatformExcelVo;
import yll.entity.Customer;
import yll.entity.Platform;
import yll.mapper.CustomerMapper;
import yll.mapper.PlatformMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.PlatformVo;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 达人平台信息
 */
@Transactional
@Service
public class PlatformService {

    // ==============================Fields===========================================
    @Autowired
    private PlatformMapper platformMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Platform insert(Platform vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Platform entity = new Platform();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setPlatformType(vo.getPlatformType());
        entity.setPlatformId(vo.getPlatformId());
        entity.setOnlineName(vo.getOnlineName());
        entity.setHeadImg(vo.getHeadImg());
        entity.setFans(vo.getFans() == null ? 0 : vo.getFans() );
        entity.setDuration(vo.getDuration());
        entity.setSessions(vo.getSessions());
        entity.setLiveTime(vo.getLiveTime());
        entity.setFansGrowthRate(vo.getFansGrowthRate());
        entity.setHighestPopularity(vo.getHighestPopularity());
        entity.setAppearanceFee(vo.getAppearanceFee());
        entity.setPlatformJson(vo.getPlatformJson());
        entity.setGoodsNum(vo.getGoodsNum());
        entity.setMoneyNum(vo.getMoneyNum());
        entity.setLinkFeeOne(vo.getLinkFeeOne());
        entity.setLinkFeeTwo(vo.getLinkFeeTwo());
        entity.setSpecialFeeOne(vo.getSpecialFeeOne());
        entity.setSpecialFeeTwo(vo.getSpecialFeeTwo());

        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(DateUtil.now());
        }
        platformMapper.insert(entity);

        return entity;
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        platformMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public Platform update(Platform vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Platform entity = platformMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }
        entity.setFans(vo.getFans() == null ? 0 : vo.getFans());

        if(StringUtils.isNotBlank(vo.getPlatformType())){
            entity.setPlatformType(vo.getPlatformType());
        }

        if(StringUtils.isNotBlank(vo.getPlatformId())){
            entity.setPlatformId(vo.getPlatformId());
        }

        if(StringUtils.isNotBlank(vo.getHeadImg())){
            entity.setHeadImg(vo.getHeadImg());
        }

        if(StringUtils.isNotBlank(vo.getDuration())){
            entity.setDuration(vo.getDuration());
        }

        if(StringUtils.isNotBlank(vo.getSessions())){
            entity.setSessions(vo.getSessions());
        }

        if(StringUtils.isNotBlank(vo.getLiveTime())){
            entity.setLiveTime(vo.getLiveTime());
        }

        if(StringUtils.isNotBlank(vo.getFansGrowthRate())){
            entity.setFansGrowthRate(vo.getFansGrowthRate());
        }

        if(StringUtils.isNotBlank(vo.getHighestPopularity())){
            entity.setHighestPopularity(vo.getHighestPopularity());
        }


        if(StringUtils.isNotBlank(vo.getAppearanceFee())){
            entity.setAppearanceFee(vo.getAppearanceFee());
        }

        if(StringUtils.isNotBlank(vo.getOnlineName())){
            entity.setOnlineName(vo.getOnlineName());
        }

        if(StringUtils.isNotBlank(vo.getGoodsNum())){
            entity.setGoodsNum(vo.getGoodsNum());
        }

        if(StringUtils.isNotBlank(vo.getMoneyNum())){
            entity.setMoneyNum(vo.getMoneyNum());
        }

        if(StringUtils.isNotBlank(vo.getLinkFeeOne())){
            entity.setLinkFeeOne(vo.getLinkFeeOne());
        }
        if(StringUtils.isNotBlank(vo.getLinkFeeTwo())){
            entity.setLinkFeeTwo(vo.getLinkFeeTwo());
        }
        if(StringUtils.isNotBlank(vo.getSpecialFeeOne())){
            entity.setSpecialFeeOne(vo.getSpecialFeeOne());
        }
        if(StringUtils.isNotBlank(vo.getSpecialFeeTwo())){
            entity.setSpecialFeeTwo(vo.getSpecialFeeTwo());
        }

        if(StringUtils.isNotBlank(vo.getRemark())){
            entity.setRemark(vo.getRemark());
        }

       /* if(StringUtils.isNotBlank(vo.getPlatformJson())){
            entity.setPlatformJson(vo.getPlatformJson());
        }*/
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        if(StringUtils.isBlank(vo.getModifier())){
            CommonAttributeUtil.setUpdated(entity, principal);
        } else {
            //后台直接添加、用户自己注册
            entity.setModifier(vo.getModifier());
            entity.setModifiedTime(vo.getModifiedTime());
        }
        platformMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Platform entity = new Platform();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        platformMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Platform getById(String id) {
        Platform entity = platformMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public Platform getByCondition(Platform condition) {
        return platformMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Platform> pagedQuery(Pagination pagination, Platform condition) {
        return MybatisHelper.selectPage(pagination, () -> platformMapper.findBy(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<PlatformVo> pagedQueryWithDic(Pagination pagination, PlatformVo condition) {
        Page<PlatformVo> page = MybatisHelper.selectPage(pagination, () -> platformMapper.findByDic(condition));
        return page;
    }

    /**
     * 查询详情-App
     * @param
     * @return 实体
     */
    public PlatformVo getAppDetail(PlatformVo condition) {
        PlatformVo entity = platformMapper.getAppDetail(condition);
        return entity;
    }


    /**
     * 查询-app认证详情
     * @param
     * @return 实体
     */
    public List<PlatformVo> getAppAuth(PlatformVo condition) {
        return platformMapper.getAppAuth(condition);
    }

    /**
     * 删除所属用户数据-App
     * @param
     */
    public void deleteByCreator(String creator) {
        platformMapper.deleteByCreator(creator);
    }

    /**
     * Excel 导入
     * @param list
     * @param params
     */
    public void excelSaveData(List<PlatformExcelVo> list, Object params) {

        Map<String, String> map = (HashMap<String, String>)params;
        String platformType = map.get("num");     //2-抖音，3-快手，4-淘宝，5-拼多多
        String fname = map.get("fileName");
        if("2".equals(platformType)){
            platformType = "p-douyin";
        } else if("3".equals(platformType)){
            platformType = "p-kuaishou";
        } else if("4".equals(platformType)){
            platformType = "p-taobao";
        } else if("5".equals(platformType)){
            platformType = "p-pinduoduo";
        }
        for (PlatformExcelVo vo :
                list) {
            //Customer
            Customer customer = new Customer();
            String nickname = vo.getNickname();
            try {
                System.out.println("index---->" + vo.getIndex());
                System.out.println("nickname---->" + nickname);
                System.out.println("============================" + nickname);
                nickname = Base64.getEncoder().encodeToString(nickname.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            customer.setNickname(nickname);
            customer = customerMapper.findByCondition(customer);
            if(null == customer){
                //不导入用户名未存在用户
                continue;
            }
            Platform platform = new Platform();
            platform.setCreator(customer.getId());
            platform.setPlatformType(platformType);
            Platform pla = platformMapper.findByCondition(platform);
            if(null != pla){
                //当前用户已存在此平台，修改平台信息
                platform = pla;
            }
            //获取头像地址
            String headImg = customer.getHeadImg();
            if(StringUtils.isNotBlank(headImg)){
                //图片处理
                String[] arrays = headImg.split(fname + "/");
                String index = arrays[1].split("\\.")[0];
                String prefix = "https://yshd-1256225403.cos.ap-beijing.myqcloud.com/yshd/prod/excel-upload/";
                String fileName = prefix + fname + "/" + index;
                String pheadImg = "";
                if("p-douyin".equals(platformType)){
                    pheadImg =  fileName + "_dy.jpg";
                } else if("p-kuaishou".equals(platformType)){
                    pheadImg =  fileName + "_ks.jpg";
                } else if("p-taobao".equals(platformType)){
                    pheadImg =  fileName + "_tb.jpg";
                } else if("p-taobao".equals(platformType)){
                    pheadImg =  fileName + "_tb.jpg";
                } else if("p-pinduoduo".equals(platformType)){
                    pheadImg =  fileName + "_pdd.jpg";
                }
                platform.setHeadImg(pheadImg);
            }
            //粉丝数（万）
            String f = vo.getFans();
            if(StringUtils.isNotBlank(f)){
                int fans = (new Double(f)).intValue() * 10000;
                platform.setFans(fans);
            }
            //第三方平台网名
            String onlineName = vo.getOnlineName();
            if(StringUtils.isNotBlank(onlineName)){
                try {
                    onlineName = Base64.getEncoder().encodeToString(onlineName.getBytes("UTF-8"));
                    platform.setOnlineName(onlineName);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            //其他属性
            platform.setPlatformId(vo.getPlatformId());
            platform.setDuration(vo.getDuration());
            platform.setSessions(vo.getSessions());
            platform.setLiveTime(vo.getLiveTime());
            platform.setLinkFeeOne(vo.getLinkFee());
            platform.setLinkFeeTwo(vo.getLinkFee());
            platform.setSpecialFeeOne(vo.getSpecialFee());
            platform.setSpecialFeeTwo(vo.getSpecialFee());
            platform.setHighestPopularity(vo.getHighestPopularity());
            platform.setGoodsNum(vo.getGoodsNum());
            platform.setMoneyNum(vo.getMoneyNum());

            if(null != pla){
                update(platform);
            } else{
                //新增
                insert(platform);
            }
        }

    }

    public static void main(String[] args) {
        String str = "https://yshd-1256225403.cos.ap-beijing.myqcloud.com/yshd/prod/excel-upload/2020062801/1.png";
        String[] split = str.split("2020062801/");
        System.out.println("ok");
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Platform vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }


}
