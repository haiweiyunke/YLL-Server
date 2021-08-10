package yll.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.identifier.IdHelper;
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.component.util.ConvertSecondUtil;
import yll.entity.Platform;
import yll.entity.PlatformAttributeValue;
import yll.entity.PlatformGroup;
import yll.mapper.PlatformAttributeValueMapper;
import yll.mapper.PlatformGroupMapper;
import yll.mapper.PlatformMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ExcelExportChartVo;
import yll.service.model.vo.PlatformAttributeValueVo;
import yll.service.model.vo.PlatformVo;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 自定义属性值信息
 */
@Transactional
@Service
public class PlatformAttributeValueService {

    // ==============================Fields===========================================
    @Autowired
    private PlatformAttributeValueMapper platformAttributeValueMapper;
    @Autowired
    private PlatformGroupMapper platformGroupMapper;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public PlatformAttributeValue insert(PlatformAttributeValue vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        PlatformAttributeValue entity = new PlatformAttributeValue();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setType(vo.getType());
        entity.setName(vo.getName());
        entity.setKid(vo.getKid());
        entity.setPid(vo.getPid());
        entity.setCid(vo.getCid());
        entity.setGid(vo.getGid());
        entity.setLiveTime(vo.getLiveTime());
        entity.setOrdinal(vo.getOrdinal());

        entity.setRemark(vo.getRemark());

        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }
        platformAttributeValueMapper.insert(entity);
        return entity;
    }

    /**
     * 批量新增
     * @param condition
     * @param creator
     * @return
     */
    public void insert(PlatformVo condition, String creator) {
        List<PlatformAttributeValue> list = condition.getAttributes();
        if(null != list &&  list.size() >0 && StringUtils.isNotBlank(creator)){
            String type = condition.getPlatformType();
            String image = condition.getImage();
            //获取平台信息 若无则新增
            Platform platform = new Platform();
            platform.setCreator(creator);
            platform.setPlatformType(type);
            platform = platformService.getByCondition(platform);
            if(null == platform){
                //新增平台信息
                platform = new Platform();
                BeanUtils.copyProperties(condition, platform);
                platform.setPlatformType(type);
                platform.setCreator(creator);
                platform = platformService.insert(platform);
            }
            String pid = platform.getId();
            if(StringUtils.isNotBlank(pid)){
                //分组id
                String gid = IdHelper.nextId();
                //组表新增数据
                PlatformGroup pg = new PlatformGroup();
                pg.setId(gid);
                pg.setPid(pid);
                pg.setImage(image);
                pg.setState(YllConstants.ONE);
                pg.setEnabled(YllConstants.ONE);
                pg.setDeleted(YllConstants.ZERO);
                pg.setCreator(creator);
                pg.setCreatedTime(DateUtil.now());
                //属性处理
                for (PlatformAttributeValue vo :
                        list) {
                    vo.setGid(gid);
                    vo.setPid(pid);
                    vo.setType(type);
                    vo.setCid(creator);
                    //需要特殊处理的属性
                    specialAttribute(vo, pg, list);
                }
                //储存
                for (PlatformAttributeValue vo :
                        list) {
                    //新增|修改
                    PlatformAttributeValue pav = isInsert(vo);
                    if(null != pav){
                        //删除原数据
                        String pavGid = pav.getGid();
                        platformGroupMapper.deleteById(pavGid);
                        //删除value值表
                        platformAttributeValueMapper.deleteByGid(pavGid);
                    }
                    insert(vo);
                }
                platformGroupMapper.insert(pg);
            }
        }
    }

    /**
     * 需要特殊处理的属性
     * @param vo
     */
    private void specialAttribute(PlatformAttributeValue vo, PlatformGroup pg, List<PlatformAttributeValue> valueList) {
        String kid = vo.getKid();
        String type = vo.getType();
        if("p-pinduoduo".equals(type)){
            String [] liveTimeArray = {"2020052716392870100002","2020052716542829400002"};
            if(Arrays.asList(liveTimeArray).contains(kid)){
                //直播时长处理
                long minutes = ConvertSecondUtil.getMinutes(vo.getName());
                vo.setName(minutes + "");
            }
            if("2020070613480374000002".equals(kid)){
                //直播日期 放入group表
                String name = vo.getName();
                //日期存入value表
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Calendar cal = Calendar.getInstance();
                try {
                    int year = cal.get(Calendar.YEAR);
                    String strDate = year + "/" + name;
                    Date date = new Date(formatter.parse(strDate).getTime());
                    for (PlatformAttributeValue pav:
                    valueList) {
                        pav.setLiveTime(date);
                    }
                    pg.setRemark(strDate);
                    pg.setLiveTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

    }


    /**
     * 是否新增
     * @param vo
     */
    private PlatformAttributeValue isInsert(PlatformAttributeValue vo) {
        String type = vo.getType();
        String kid = vo.getKid();
        if("p-pinduoduo".equals(type)){
            if("2020052716394013900002".equals(kid)){
                //根据直播时间确定 新增|修改
                PlatformAttributeValue condition = new PlatformAttributeValue();
                condition.setName(vo.getName());
                condition.setType(vo.getType());
                condition.setCreator(AppSecuritysUtil.getCustomerId());
                PlatformAttributeValue pav = platformAttributeValueMapper.findByCondition(condition);
                return pav;
            }
        }
        return null;
    }

    /**
     * 批量修改
     * @param condition
     * @return
     */
    public void update(PlatformVo condition) {
        List<PlatformAttributeValue> list = condition.getAttributes();
        if(null != list &&  list.size() >0){
            for (PlatformAttributeValue vo :
                    list) {
                String id = vo.getId();
                PlatformAttributeValue pv = platformAttributeValueMapper.getById(id);
                if(null != pv){
                    pv.setName(vo.getName());
                    platformAttributeValueMapper.update(pv);
                }
            }
        }
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        platformAttributeValueMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public PlatformAttributeValue update(PlatformAttributeValue vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        PlatformAttributeValue entity = platformAttributeValueMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setType(vo.getType());
        entity.setName(vo.getName());
        entity.setKid(vo.getKid());
        entity.setPid(vo.getPid());
        entity.setCid(vo.getCid());
        entity.setGid(vo.getGid());
        entity.setLiveTime(vo.getLiveTime());
        entity.setOrdinal(vo.getOrdinal());

        entity.setRemark(vo.getRemark());

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
        CommonAttributeUtil.setUpdated(entity, principal);
        platformAttributeValueMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        PlatformAttributeValue entity = new PlatformAttributeValue();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        platformAttributeValueMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public PlatformAttributeValue getById(String id) {
        PlatformAttributeValue entity = platformAttributeValueMapper.getById(id);
        return entity;
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public  List<PlatformAttributeValue> getBySelf(PlatformAttributeValue condition) {
        List<PlatformAttributeValue> list = platformAttributeValueMapper.getBySelf(condition);
        return list;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public PlatformAttributeValue getByCondition(PlatformAttributeValue condition) {
        return platformAttributeValueMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<PlatformAttributeValue> pagedQuery(Pagination pagination, PlatformAttributeValue condition) {
        return MybatisHelper.selectPage(pagination, () -> platformAttributeValueMapper.findBy(condition));
    }

    /**
     * 查询图表
     * @param
     * @return 实体
     */
    public  List<PlatformAttributeValueVo> chart(PlatformAttributeValueVo condition) {
        List<PlatformAttributeValueVo> list = platformAttributeValueMapper.chart(condition);
        return list;
    }

    /**
     * 特殊计算图表-拼多多
     * @param
     * @return 实体
     */
    public  List<PlatformAttributeValueVo> chartCalculationPinduoduo(PlatformAttributeValueVo condition) {
        List<PlatformAttributeValueVo> list = platformAttributeValueMapper.chartCalculationPinduoduo(condition);
        return list;
    }

    /**
     * Excel导出-拼多多
     * @param
     * @return 实体
     */
    public  List<ExcelExportChartVo> exportExcelForPinduoduo(PlatformAttributeValueVo condition) {
        List<ExcelExportChartVo> list = platformAttributeValueMapper.exportExcelForPinduoduo(condition);
        return list;
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(PlatformAttributeValue vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
