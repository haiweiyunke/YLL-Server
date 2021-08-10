package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.mapper.SearchMapper;
import yll.service.model.vo.SearchVo;

/**
 * 统一查询
 */
@Transactional
@Service
public class SearchService {

    // ==============================Fields===========================================
    @Autowired
    private SearchMapper searchMapper;

    // ==============================Methods==========================================
    /**
     * 查询集合-学习版块
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<SearchVo> getStudyAppList(Pagination pagination, SearchVo condition) {
        return MybatisHelper.selectPage(pagination, () -> searchMapper.getStudyAppList(condition));
    }

    /**
     * 查询集合-学习版块
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<SearchVo> getZoneAppList(Pagination pagination, SearchVo condition) {
        return MybatisHelper.selectPage(pagination, () -> searchMapper.getZoneAppList(condition));
    }

    // ==============================ToolMethods======================================

}
