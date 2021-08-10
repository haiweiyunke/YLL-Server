package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
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
import yll.entity.Train;
import yll.mapper.TrainMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.TrainVo;

import java.util.List;

/**
 * 培训
 */
@Transactional
@Service
public class TrainService {

    // ==============================Fields===========================================
    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Train vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Train entity = new Train();
        IdHelper.setIfEmptyId(entity);

        entity.setName(vo.getName());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setContent(vo.getContent());
        entity.setFiles(vo.getFiles());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());

        entity.setSlide(YllConstants.ZERO);
        entity.setShare(YllConstants.ZERO);
        entity.setLikes(YllConstants.ZERO);
        entity.setCollects(YllConstants.ZERO);
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        trainMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        trainMapper.deleteById(id);
//        voRoleMapper.deleteByTrainId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(Train vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Train entity = trainMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setContent(vo.getContent());
        entity.setFiles(vo.getFiles());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());

        if(null != vo.getLikes()){
            entity.setLikes(vo.getLikes());
        }
        if(null != vo.getCollects()){
            entity.setCollects(vo.getCollects());
        }
        if(null != vo.getShare()){
            entity.setShare(vo.getShare());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        }

        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        trainMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Train entity = new Train();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        trainMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Train getById(String id) {
        Train entity = trainMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Train> pagedQuery(Pagination pagination, Train condition) {
        return MybatisHelper.selectPage(pagination, () -> trainMapper.findBy(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Train> pagedQueryWithType(Pagination pagination, Train condition) {
        return MybatisHelper.selectPage(pagination, () -> trainMapper.findByWithType(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TrainVo> getAppList(Pagination pagination, TrainVo condition) {
        return MybatisHelper.selectPage(pagination, () -> trainMapper.getAppList(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     */
    public TrainVo getAppDetail(TrainVo condition) {
        return trainMapper.getAppDetail(condition);
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Train> findBySlide(Train condition) {
        return trainMapper.findBySlide(condition);
    }

    /**
     * 轮播修改
     * @param
     * @return 分页结果
     */
    public void slide(TrainVo condition) {
        String oldTrainId = condition.getOldTrainId();
        if(StringUtils.isNotBlank(oldTrainId)){
            Train oldTrain = trainMapper.getById(oldTrainId);
            oldTrain.setSlide(0);
            oldTrain.setOrdinal(YllConstants.LAST);
            trainMapper.update(oldTrain);
        }
        condition.setSlide(YllConstants.ONE);
        trainMapper.update(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Train vo) {
        String id = vo.getId();
        String name = vo.getName();
        String content = vo.getContent();
        String type = vo.getType();

        if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("标题不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw ExceptionHelper.prompt("内容不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("类型不能为空");
        }
    }
}
