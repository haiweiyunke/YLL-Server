package yll.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.identifier.IdHelper;
import yll.common.standard.CommonAttributeUtil;
import yll.component.excel.vo.InternetCelebrityExcelVo;
import yll.component.util.CommonUtil;
import yll.entity.*;
import yll.entity.InternetCelebrity;
import yll.mapper.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerCollectsVo;
import yll.service.model.vo.CustomerVo;
import yll.service.model.vo.ExcelExportVo;
import yll.service.model.vo.InternetCelebrityVo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * 网红（达人）信息
 */
@Transactional
@Service
public class InternetCelebrityService {

    // ==============================Fields===========================================
    @Autowired
    private InternetCelebrityMapper internetCelebrityMapper;
    @Autowired
    private CustomerPointsService customerPointsService;
    @Autowired
    private CustomerCollectsMapper customerCollectsMapper;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private DicMapper dicMapper;
    @Autowired
    private SlideMapper slideMapper;
    @Autowired
    private McnMapper mcnMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public InternetCelebrity insert(InternetCelebrity vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        InternetCelebrity entity = new InternetCelebrity();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setRealName(vo.getRealName());
        entity.setIdCard(vo.getIdCard());
        entity.setMcnId(vo.getMcnId());
        entity.setExpertise(vo.getExpertise());
        entity.setCooperation(vo.getCooperation());
        entity.setDescription(vo.getDescription());
        entity.setPersonalPortraits(vo.getPersonalPortraits());
        entity.setHeight(vo.getHeight());
        entity.setMicroblog(vo.getMicroblog());
        entity.setDisclosure(vo.getDisclosure());
        entity.setAddress(vo.getAddress());

        entity.setLinkFee(vo.getLinkFee());
        entity.setAttendanceFee(vo.getAttendanceFee());
        entity.setSpecialFee(vo.getSpecialFee());
        entity.setUnderFee(vo.getUnderFee());
        entity.setCommission(vo.getCommission());
        entity.setOther(vo.getOther());
        entity.setImage(vo.getImage());
        entity.setPicture(vo.getPicture());

        entity.setSlide(YllConstants.ZERO);
        entity.setHot(YllConstants.ZERO);
        entity.setRemark(vo.getRemark());

        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(null == principal || "0".equals(principal.getUserId())){
            //管理员添加无需认证
            entity.setState(YllConstants.TWO);
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, vo.getCreator());
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
            //修改用户角色
            Customer customer = new Customer();
            customer.setId(vo.getCreator());
            customer.setRoleType(YllConstants.TWO.toString());
            customerMapper.update(customer);
        }
        internetCelebrityMapper.insert(entity);
        return entity;
    }


    /**
     * 新增-Excel
     * @param
     */
    public InternetCelebrity insertForExcel(InternetCelebrity vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        InternetCelebrity entity = new InternetCelebrity();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setRealName(vo.getRealName());
        entity.setMcnId(vo.getMcnId());
        entity.setExpertise(vo.getExpertise());
        entity.setCooperation(vo.getCooperation());
        entity.setDescription(vo.getDescription());
        entity.setPersonalPortraits(vo.getPersonalPortraits());
        entity.setHeight(vo.getHeight());
        entity.setMicroblog(vo.getMicroblog());
        entity.setDisclosure(vo.getDisclosure());
        entity.setAddress(vo.getAddress());

        entity.setLinkFee(vo.getLinkFee());
        entity.setAttendanceFee(vo.getAttendanceFee());
        entity.setSpecialFee(vo.getSpecialFee());
        entity.setUnderFee(vo.getUnderFee());
        entity.setCommission(vo.getCommission());
        entity.setOther(vo.getOther());

        entity.setSlide(YllConstants.ZERO);
        entity.setHot(YllConstants.ZERO);
        entity.setRemark(vo.getRemark());

        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);

        if(null != vo.getIdCard()){
            entity.setIdCard(vo.getIdCard());
        }

        if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(null == principal || "0".equals(principal.getUserId())){
            //管理员添加无需认证
            entity.setState(YllConstants.TWO);
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(null == vo.getCreatedTime() ? DateUtil.now() : vo.getCreatedTime());
        }
        internetCelebrityMapper.insert(entity);
        return entity;
    }


    /**
     * 新增达人信息-app
     * @param
     */
    public InternetCelebrity insert(InternetCelebrityVo vo) {
        InternetCelebrity ic = new InternetCelebrity();
        BeanUtils.copyProperties(vo, ic);
        //新增
        ic = insert(ic);
        //修改基本资料customer
        updateCustomer(vo, ic);
        //平台
        updatePlatform(vo.getPlatformJson(), vo.getCreator());
        return ic;
    }

    /**
     * 修改达人信息-app
     * @param
     */
    public InternetCelebrity update(InternetCelebrityVo vo) {
        InternetCelebrity ic = new InternetCelebrity();
        BeanUtils.copyProperties(vo, ic);
        //修改
        ic = update(ic);
        //修改基本资料customer
        updateCustomer(vo, ic);
        //平台
        updatePlatform(vo.getPlatformJson(), vo.getCreator());
        return ic;
    }

    /**
     * 修改平台资料-表结构版
     * @param platformJsonStr
     * @param creator
     */
    public void updatePlatform4Table(String platformJsonStr, String creator) {


    }


    /**
     * 修改平台资料-json版
     * @param platformJsonStr
     * @param creator
     */
    public void updatePlatform(String platformJsonStr, String creator) {
        if(StringUtils.isNotBlank(platformJsonStr)){
            //json 转换
            ObjectMapper objectMapper = new ObjectMapper();
            List<Platform> list = new ArrayList<>();
            try {
                //封装平台实体
                JsonNode jsonNode = objectMapper.readTree(platformJsonStr);
                Iterator<JsonNode> iterator = jsonNode.iterator();
                while(iterator.hasNext()){
                    JsonNode json = iterator.next();
                    String headImg = null == json.get("headImg") ? "" :  json.get("headImg").textValue();
                    String platformType = null == json.get("platformType") ? "" :  json.get("platformType").textValue();
                    String platformId = null == json.get("platformId") ? "" :  json.get("platformId").textValue();
                    String onlineName = null == json.get("onlineName") ? "" :  json.get("onlineName").textValue();
                    String fansStr = null == json.get("fans") ? "" :  json.get("fans").textValue();
                    Integer fans = StringUtils.isBlank(fansStr) ? 0 : Integer.parseInt(fansStr);
                    String duration = null == json.get("duration") ? "" :  json.get("duration").textValue();
                    String sessions = null == json.get("sessions") ? "" : json.get("sessions").textValue();
                    String liveTime = null == json.get("liveTime") ? "" :  json.get("liveTime").textValue();
                    String fansGrowthRate = null == json.get("fansGrowthRate") ? "" :  json.get("fansGrowthRate").textValue();
                    String highestPopularity = null == json.get("highestPopularity") ? "" : json.get("highestPopularity") .textValue();
                    String goodsNum = null == json.get("goodsNum") ? "" :  json.get("goodsNum").textValue();
                    String moneyNum = null == json.get("moneyNum") ? "" :  json.get("moneyNum").textValue();
                    String appearanceFee = null == json.get("appearanceFee") ? "" :  json.get("appearanceFee").textValue();
                    String platformJson = null == json.get("platformJson") ? "" :  json.get("platformJson").textValue();
                    String linkFeeOne = null == json.get("linkFeeOne") ? "" :  json.get("linkFeeOne").textValue();
                    String linkFeeTwo = null == json.get("linkFeeTwo") ? "" :  json.get("linkFeeTwo").textValue();
                    String specialFeeOne = null == json.get("specialFeeOne") ? "" :  json.get("specialFeeOne").textValue();
                    String specialFeeTwo = null == json.get("specialFeeTwo") ? "" :  json.get("specialFeeTwo").textValue();

                    Platform p = new Platform();
                    p.setHeadImg(headImg);
                    p.setPlatformType(platformType);
                    p.setPlatformId(platformId);
                    p.setOnlineName(onlineName);
                    p.setFans(fans);
                    p.setDuration(duration);
                    p.setSessions(sessions);
                    p.setLiveTime(liveTime);
                    p.setFansGrowthRate(fansGrowthRate);
                    p.setHighestPopularity(highestPopularity);
                    p.setAppearanceFee(appearanceFee);
                    p.setGoodsNum(goodsNum);
                    p.setMoneyNum(moneyNum);
                    p.setPlatformJson(platformJson);
                    p.setLinkFeeOne(linkFeeOne);
                    p.setLinkFeeTwo(linkFeeTwo);
                    p.setSpecialFeeOne(specialFeeOne);
                    p.setSpecialFeeTwo(specialFeeTwo);
                    list.add(p);
                }
                //平台 新增|修改
                modifyPlatform(creator, list);
            } catch (IOException e) {
                e.printStackTrace();
                throw ExceptionHelper.prompt("平台Json转换失败");
            }
        }
    }

    /**
     * 平台 新增|修改
     * @param creator
     * @param list
     */
    private void modifyPlatform(String creator, List<Platform> list) {
        Platform pvo = new Platform();
        for (Platform p :
                list) {
            p.setCreator(creator);
            if(StringUtils.isNotBlank(p.getPlatformType())){
                //查询平台是否存在
                pvo.setCreator(creator);
                pvo.setPlatformType(p.getPlatformType());
                Platform pla = platformService.getByCondition(pvo);
                if(null != pla){
                    p.setId(pla.getId());
                    p.setModifier(creator);
                    p.setModifiedTime(DateUtil.now());
                    platformService.update(p);
                } else{
                    platformService.insert(p);
                }
            }
        }
    }

    /**
     * 修改customer基本资料
     * @param vo
     * @param ic
     */
    private void updateCustomer(InternetCelebrityVo vo, InternetCelebrity ic) {
        String headImg = vo.getHeadImg();
        Date birthday = vo.getCustBirthday();
        String location = vo.getLocation();
        if(StringUtils.isNotBlank(headImg) || null !=birthday || StringUtils.isNotBlank(location)){
            Customer cust = customerMapper.getById(ic.getCreator());
            if(StringUtils.isNotBlank(headImg)){
                cust.setHeadImg(headImg);
            }
            if(null != birthday){
                cust.setBirthday(birthday);
            }
            if(StringUtils.isNotBlank(location)){
                cust.setLocation(location);
            }
            cust.setRoleType(YllConstants.TWO.toString());
            cust.setPassword(null);
            customerMapper.update(cust);
        }
    }


    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            throw ExceptionHelper.prompt("缺少id");
        }
        InternetCelebrity entity = internetCelebrityMapper.getById(id);
        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }
        String creator = entity.getCreator();
        internetCelebrityMapper.deleteById(id);
        //删除关注数据
        CustomerCollectsVo ccv = new CustomerCollectsVo();
        ccv.setTargetId(creator);
        customerCollectsMapper.deleteByTargetId(ccv);
    }


    /**
     * 更新
     * @param
     */
    public InternetCelebrity update(InternetCelebrity vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        InternetCelebrity entity = internetCelebrityMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setRealName(vo.getRealName());
        entity.setMcnId(vo.getMcnId());
        entity.setExpertise(vo.getExpertise());
        entity.setCooperation(vo.getCooperation());
        entity.setDescription(vo.getDescription());
        entity.setPersonalPortraits(vo.getPersonalPortraits());
        entity.setHeight(vo.getHeight());
        entity.setMicroblog(vo.getMicroblog());
        entity.setDisclosure(vo.getDisclosure());
        entity.setAddress(vo.getAddress());
        entity.setOther(vo.getOther());

        entity.setLinkFee(vo.getLinkFee());
        entity.setAttendanceFee(vo.getAttendanceFee());
        entity.setSpecialFee(vo.getSpecialFee());
        entity.setUnderFee(vo.getUnderFee());
        entity.setCommission(vo.getCommission());

        entity.setRemark(vo.getRemark());

        if(null != vo.getIdCard()){
            entity.setIdCard(vo.getIdCard());
        }
        if(null != vo.getImage()){
            entity.setImage(vo.getImage());
        }
        if(null != vo.getPicture()){
            entity.setPicture(vo.getPicture());
        }

        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        }
        if(null != vo.getOrdinal()){
            entity.setOrdinal(vo.getOrdinal());
        }
        if(null != vo.getHot()){
            entity.setHot(vo.getHot());
        }
        if(null != vo.getHotOrdinal()){
            entity.setHotOrdinal(vo.getHotOrdinal());
        }

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        internetCelebrityMapper.update(entity);
        //修改轮播图
        Slide svo = new Slide();
        svo.setTargetId(entity.getId());
        svo = slideMapper.findByCondition(svo);
        if(null != svo && StringUtils.isNotBlank(entity.getPersonalPortraits())){
            String[] imgs = entity.getPersonalPortraits().split(",");
            svo.setImg(imgs[0]);
        }
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        InternetCelebrity entity = new InternetCelebrity();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        internetCelebrityMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public InternetCelebrity getById(String id) {
        InternetCelebrity entity = internetCelebrityMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public InternetCelebrity getByCondition(InternetCelebrity condition) {
        return internetCelebrityMapper.findByCondition(condition);
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public InternetCelebrityVo getByConditionVo(InternetCelebrity condition) {
        return internetCelebrityMapper.findByConditionVo(condition);
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public List<InternetCelebrity> findBy(InternetCelebrity condition) {
        return internetCelebrityMapper.findBy(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<InternetCelebrity> pagedQuery(Pagination pagination, InternetCelebrity condition) {
        return MybatisHelper.selectPage(pagination, () -> internetCelebrityMapper.findBy(condition));
    }

    /**
     * 分页查询-轮播图
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<InternetCelebrityVo> pagedQuerySlide(Pagination pagination, InternetCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> internetCelebrityMapper.findBySlide(condition));
    }

    /**
     * 分页查询-热门
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<InternetCelebrityVo> pagedQueryHot(Pagination pagination, InternetCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> internetCelebrityMapper.findByHot(condition));
    }

    /**
     * 分页查询-后台使用
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<InternetCelebrityVo> findByAdminList(Pagination pagination, InternetCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> internetCelebrityMapper.findByAdminList(condition));
    }


    /**
     * 分页查询-app邀请列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<InternetCelebrityVo> getAppList(Pagination pagination, InternetCelebrityVo condition) {
        //拼接order by   orderBy
        String orderBy = "";
        String isHot = condition.getIsHot();
        String isLike = condition.getIsLike();
        Integer attendanceFeeOrder = condition.getAttendanceFeeOrder();
        Integer fansQuantityOrder = condition.getFansQuantityOrder();
        Integer likeNumOrder = condition.getLikeNumOrder();

        //热门
        if(StringUtils.isNotBlank(isHot)){
            orderBy += " dc.hot_ordinal,";
        }

        //最爱、点赞数（口碑）
        if(StringUtils.isNotBlank(isLike) || (null != likeNumOrder && 2 == likeNumOrder)){
            orderBy += " c.likes desc,";
        } else if(null != likeNumOrder && 1 == likeNumOrder){
            orderBy += " c.likes,";
        }
        //出场费用
        if(null != attendanceFeeOrder){
            if(1 == attendanceFeeOrder){
                orderBy += " attendance_fee,";
            } else if(2 == attendanceFeeOrder){
                orderBy += " attendance_fee desc,";
            }
        }
        if(null != fansQuantityOrder){
            //粉丝数量
            if(1 == fansQuantityOrder){
                orderBy += " tab.fans,";
            } else if(2 == fansQuantityOrder){
                orderBy += " tab.fans desc,";
            }
        }
        if(StringUtils.isBlank(orderBy)){
            orderBy += " c.created_time desc";
        } else{
            orderBy = orderBy.substring(0, orderBy.length()-1);
        }
        condition.setOrderBy(orderBy);
        appQueryParams(condition);
        return MybatisHelper.selectPage(pagination, () -> internetCelebrityMapper.getAppList(condition));
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public InternetCelebrityVo getAppDetail(InternetCelebrityVo condition) {
        return internetCelebrityMapper.getAppDetail(condition);
    }

    /**
     * 查询-app认证详情
     * @param
     * @return 实体
     */
    public InternetCelebrityVo getAppAuth(InternetCelebrityVo condition) {
        return internetCelebrityMapper.getAppAuth(condition);
    }

    /**
     * 查询-app认证详情
     * @param
     * @return 实体
     */
    public Page<InternetCelebrityVo> getAppInviteSearch(Pagination pagination, InternetCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> internetCelebrityMapper.getAppInviteSearch(condition));
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<InternetCelebrityVo> findBySlide(InternetCelebrityVo condition) {
        return internetCelebrityMapper.findBySlide(condition);
    }


    /**
     * 轮播修改
     * @param
     */
    public void slide(InternetCelebrityVo condition) {
        String oldInternetCelebrityId = condition.getOldSlideId();
        if(StringUtils.isNotBlank(oldInternetCelebrityId)){
            InternetCelebrity oldInternetCelebrity = internetCelebrityMapper.getById(oldInternetCelebrityId);
            oldInternetCelebrity.setSlide(0);
            oldInternetCelebrity.setOrdinal(YllConstants.LAST);
            internetCelebrityMapper.update(oldInternetCelebrity);
        }
        condition.setSlide(YllConstants.ONE);
        internetCelebrityMapper.update(condition);
    }


    /**
     *  热门
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<InternetCelebrityVo> findByHot(InternetCelebrityVo condition) {
        return  internetCelebrityMapper.findByHot(condition);
    }


    /**
     * 热门修改
     * @param
     */
    public void hot(InternetCelebrityVo condition) {
        String oldInternetCelebrityId = condition.getOldHotId();
        if(StringUtils.isNotBlank(oldInternetCelebrityId)){
            InternetCelebrity oldInternetCelebrity = internetCelebrityMapper.getById(oldInternetCelebrityId);
            oldInternetCelebrity.setHot(0);
            oldInternetCelebrity.setHotOrdinal(YllConstants.LAST);
            internetCelebrityMapper.update(oldInternetCelebrity);
        }
        condition.setHot(YllConstants.ONE);
        internetCelebrityMapper.update(condition);
    }


    /**
     * 封装查询参数
     * @param condition
     */
    private void appQueryParams(InternetCelebrityVo condition){
        String linkFee = condition.getLinkFee();
        String attendanceFee = condition.getAttendanceFee();
        String specialFee = condition.getSpecialFee();
        String underFee = condition.getUnderFee();
        String fans = condition.getFans();
        String commission = condition.getCommission();
        String age = condition.getAge();
        String gender = condition.getGender();

        //链接费用 - 20200623改为前端手输入范围
        /* XML 备份
        <if test=" linkFeeOne != null and linkFeeOne != '' and linkFeeTwo != null and linkFeeTwo != '' ">
                    and (link_fee &gt;= ${linkFeeOne}  and  link_fee &lt; ${linkFeeTwo})
        </if>
        <if test=" linkFeeOne != null and linkFeeOne != '' and (linkFeeTwo == null or linkFeeTwo == '') ">
                    and (link_fee &gt;= ${linkFeeOne})
        </if>*/
        /*if(StringUtils.isNotBlank(linkFee)) {
            Dic dic = dicMapper.getByCode(linkFee);
            if(null != dic){
                linkFee = dic.getRemark();
                String[] linkArgs = linkFee.split("-");
                if (linkArgs.length == 1) {
                    //以上
                    String linkStr = linkArgs[0].split("以上")[0];
                    condition.setLinkFeeOne(linkStr);
                } else if (linkArgs.length == 2) {
                    //区间
                    condition.setLinkFeeOne(linkArgs[0]);
                    condition.setLinkFeeTwo(linkArgs[1]);
                }
            }
        }*/

            //出场费用
            if(StringUtils.isNotBlank(attendanceFee)){
                Dic dic = dicMapper.getByCode(attendanceFee);
                if(null != dic){
                    attendanceFee = dic.getRemark();
                    String[] attendanceArgs = attendanceFee.split("-");
                    if(attendanceArgs.length == 1){
                        //以上
                        String attendanceStr = attendanceArgs[0].split("以上")[0];
                        condition.setAttendanceFeeOne(attendanceStr);
                    } else if(attendanceArgs.length == 2){
                        //区间
                        condition.setAttendanceFeeOne(attendanceArgs[0]);
                        condition.setAttendanceFeeTwo(attendanceArgs[1]);
                    }
                }
            }

            //专场费用- 20200623改为前端手输入范围
           /*  XML 备份
            <if test=" specialFeeOne != null and specialFeeOne != '' and specialFeeTwo != null and specialFeeTwo != '' ">
                        and (special_fee &gt;= ${specialFeeOne}  and  special_fee &lt; ${specialFeeTwo})
            </if>
            <if test=" specialFeeOne != null and specialFeeOne != '' and (specialFeeTwo == null or specialFeeTwo == '') ">
                        and (special_fee &gt;= ${specialFeeOne})
            </if>*/
           /* if(StringUtils.isNotBlank(specialFee)){
                Dic dic = dicMapper.getByCode(specialFee);
                if(null != dic){
                    specialFee = dic.getRemark();
                    String[] specialArgs = specialFee.split("-");
                    if(specialArgs.length == 1){
                        //以上
                        String specialStr = specialArgs[0].split("以上")[0];
                        condition.setSpecialFeeOne(specialStr);
                    } else if(specialArgs.length == 2){
                        //区间
                        condition.setSpecialFeeOne(specialArgs[0]);
                        condition.setSpecialFeeTwo(specialArgs[1]);
                    }
                }
            }*/

            //线下活动费用
            if(StringUtils.isNotBlank(underFee)){
                Dic dic = dicMapper.getByCode(underFee);
                if(null != dic){
                    underFee = dic.getRemark();
                    String[] underArgs = underFee.split("-");
                    if(underArgs.length == 1){
                        //以上
                        String underStr = underArgs[0].split("以上")[0];
                        condition.setUnderFeeOne(underStr);
                    } else if(underArgs.length == 2){
                        //区间
                        condition.setUnderFeeOne(underArgs[0]);
                        condition.setUnderFeeTwo(underArgs[1]);
                    }
                }
            }

            //粉丝量
            if(StringUtils.isNotBlank(fans)){
                Dic dic = dicMapper.getByCode(fans);
                if(null != dic){
                    fans = dic.getRemark();
                    String[] fansArgs = fans.split("-");
                    if(fansArgs.length == 1){
                        //以上
                        String fansStr = fansArgs[0].split("以上")[0];
                        condition.setFansOne(fansStr);
                    } else if(fansArgs.length == 2){
                        //区间
                        condition.setFansOne(fansArgs[0]);
                        condition.setFansTwo(fansArgs[1]);
                    }
                }
            }

            //带货佣金
            if(StringUtils.isNotBlank(commission)){
                Dic dic = dicMapper.getByCode(commission);
                if(null != dic){
                    commission = dic.getRemark();
                    String[] commissionArgs = commission.split("-");
                    if(commissionArgs.length == 1){
                        //以上
                        String commissionStr = commissionArgs[0].split("以上")[0];
                        condition.setCommissionOne(commissionStr);
                    } else if(commissionArgs.length == 2){
                        //区间
                        condition.setCommissionOne(commissionArgs[0]);
                        condition.setCommissionTwo(commissionArgs[1]);
                    }
                }
            }

            //年龄
            if(StringUtils.isNotBlank(age)){
                Dic dic = dicMapper.getByCode(age);
                if(null != dic){
                    age = dic.getRemark();
                    String[] ageArgs = age.split("-");
                    if(ageArgs.length == 1){
                        //以上
                        String ageStr = ageArgs[0].split("以上")[0];
                        condition.setAgeOne(ageStr);
                    } else if(ageArgs.length == 2){
                        //区间
                        condition.setAgeOne(ageArgs[0]);
                        condition.setAgeTwo(ageArgs[1]);
                    }
                }
            }

            //性别
            if(StringUtils.isNotBlank(gender)){
                Dic dic = dicMapper.getByCode(gender);
                if(null != dic){
                    gender = dic.getRemark();
                    condition.setGender(gender);
                }
            }
    }

    /**
     * 导出excel
     * @return
     */
    public List<ExcelExportVo> excelexportData2(InternetCelebrityVo condition) throws Exception {
//        List<ExcelExportVo> list = internetCelebrityMapper.findByExcel(condition);  TODO xml新增 findByExcel 方法
        List<ExcelExportVo> list = new ArrayList<>();
        BASE64Decoder decoder = new BASE64Decoder();
        if(null != list && list.size() > 0){
            for (ExcelExportVo vo :
                    list) {
                String nickname = vo.getNickname();
                if(StringUtils.isNotBlank(nickname)){
                    nickname = new String(decoder.decodeBuffer(nickname), "UTF-8");
                    vo.setNickname(nickname);
                }
            }
        }
        return list;
    }


    /**
     *  Excel 导入
     * @param list
     * @param params
     */
    public void excelSaveData(List<InternetCelebrityExcelVo> list, Object params) {
        //遍历存储
        for (InternetCelebrityExcelVo vo :
                list) {
            //Customer
            Customer customer = new Customer();
            String nickname = vo.getNickname();
            if(StringUtils.isNotBlank(nickname)){
                try {
                    nickname = Base64.getEncoder().encodeToString(nickname.getBytes("UTF-8"));
                    customer.setNickname(nickname);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
//            Customer cust = customerMapper.findByCondition(customer);
//            if(null != cust){
//                //不导入用户名已存在用户
//                continue;
//            }
            customer.setPassword("111111");     //默认密码
            customer.setGender("女".equals(vo.getGender()) ? 1 : 2);
            customer.setRoleType("2");    //（1-普通，2-达人，3-mcn，4-企业主）
            //InternetCelebrity
            InternetCelebrity internetCelebrity = new InternetCelebrity();
            //擅长领域处理
            String expertise = vo.getExpertise();
            if(StringUtils.isNotBlank(expertise)){
                String[] exps = expertise.split(",");
                StringBuffer sb = new StringBuffer();
                for (String e :
                        exps) {
                    Dic d = new Dic();
                    d.setCodename(e);
                    List<Dic> dList = dicMapper.findBy(d);
                    if(dList.size() > 0){
                        d = dList.get(0);
                        sb.append(d.getCode()).append(",");
                    }
                }
                if(sb.length() > 0){
                    expertise = sb.substring(0, sb.length() - 1);
                    internetCelebrity.setExpertise(expertise);
                }
            }
            //mcn
            String mcnName = vo.getMcn();
            if(StringUtils.isNotBlank(mcnName)){
                Mcn mcn = new Mcn();
                mcn.setMcnName(mcnName);
                mcn = mcnMapper.findByCondition(mcn);
                if(null != mcn){
                    internetCelebrity.setMcnId(mcn.getId());
                }
            }
            //公开状态
            internetCelebrity.setDisclosure("是".equals(vo.getDisclosure()) ? 2 : 1);

            //图片处理
            String prefix = "https://yshd-1256225403.cos.ap-beijing.myqcloud.com/yshd/prod/excel-upload/";
            String fileName = prefix + params.toString() + "/" + vo.getIndex() ;
            //粉条头像
            customer.setHeadImg(fileName + ".jpg");
            //形象照片两张
            String personalPortraits = fileName + "_1.jpg," + fileName + "_2.jpg" ;
            internetCelebrity.setPersonalPortraits(personalPortraits);

            //其他属性
            internetCelebrity.setRealName(vo.getRealName());
            internetCelebrity.setHeight(vo.getHeight());
            internetCelebrity.setAttendanceFee(vo.getAttendanceFee());
            internetCelebrity.setCommission(vo.getCommission());
            internetCelebrity.setDescription(vo.getDescription());

            //新增
            customer = customerService.insertForExcel(customer);
            internetCelebrity.setCreator(customer.getId());
            insertForExcel(internetCelebrity);
        }
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(InternetCelebrity vo) {
        String id = vo.getId();
//        String realName = vo.getRealName();
        //String password = vo.getPassword();
       /* if (StringUtils.isEmpty(realName)) {
            throw ExceptionHelper.prompt("真实姓名不能为空");
        }*/
    }

    /** 验证数据 */
    private void validateApp(InternetCelebrityVo vo) {
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }


}
