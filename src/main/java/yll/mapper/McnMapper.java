package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Mcn;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.McnVo;

import java.util.List;

/**
 * 机构（MCN）_Mapper接口
 * @author cc
 */
@Mapper
public interface McnMapper extends BasicMapper<Mcn, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Mcn> findBy(Mcn condition);

    /**
     * 查询网红
     * @param condition
     * @return 网红信息
     */
    Mcn findByCondition(Mcn condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 根据条件查询-app
     * @param condition 查询条件
     * @return 列表
     */
    List<Mcn> findAll4App(Mcn condition);

    /**
     * 根据条件查询-app认证详情
     * @param condition 查询条件
     * @return 列表
     */
    McnVo getAppAuth(Mcn condition);

    /**
     * 获取详情-app
     * @param condition
     */
    McnVo getAppDetail(McnVo condition);


    /**
     * 根据条件查询-后台使用
     * @param condition 查询条件
     * @return 列表
     */
    List<McnVo> findByAdminList(McnVo condition);

    /**
     * 根据条件查询(shop编辑页使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<McnVo> find4Shop(Mcn condition);
}
