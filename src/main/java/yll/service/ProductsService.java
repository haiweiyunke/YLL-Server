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
import yll.entity.Products;
import yll.mapper.ProductsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ProductsVo;
import yll.service.model.vo.TaskCelebrityProductsVo;
import yll.service.model.vo.TaskProductsVo;

import java.util.List;

/**
 *  商品
 */
@Transactional
@Service
public class ProductsService {

    // ==============================Fields===========================================
    @Autowired
    private ProductsMapper productsMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Products vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Products entity = new Products();
        IdHelper.setIfEmptyId(entity);

        entity.setName(vo.getName());
        entity.setSid(vo.getSid());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setPicture(vo.getPicture());
        entity.setVideo(vo.getVideo());
        entity.setProfile(vo.getProfile());
        entity.setPrice(vo.getPrice());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());
        entity.setRegion(vo.getRegion());

        entity.setDepositRatio(vo.getDepositRatio());
        entity.setDiscountPrice(vo.getDiscountPrice());
        entity.setLink(vo.getLink());
        entity.setQuantity(vo.getQuantity());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setProductLicense(vo.getProductLicense());
        entity.setCertificate(vo.getCertificate());
        entity.setQualityCertificate(vo.getQualityCertificate());
        entity.setAdvertisement(vo.getAdvertisement());

        entity.setSales(vo.getSales());
        entity.setPlatform(vo.getPlatform());
        entity.setPlatform(vo.getPlatform());
        entity.setBringType(vo.getBringType());
        entity.setCommission(vo.getCommission());
        entity.setGiveBack(vo.getGiveBack());
        entity.setDeposit(vo.getDeposit());
        entity.setOther(vo.getOther());

        entity.setSlide(YllConstants.ZERO);
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、App新增
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }
        productsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        productsMapper.deleteById(id);
//        voRoleMapper.deleteByProductsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(Products vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Products entity = productsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setSid(vo.getSid());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setPicture(vo.getPicture());
        entity.setVideo(vo.getVideo());
        entity.setProfile(vo.getProfile());
        entity.setPrice(vo.getPrice());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());
        entity.setAdvertisement(vo.getAdvertisement());
        entity.setRegion(vo.getRegion());

        entity.setDepositRatio(vo.getDepositRatio());
        entity.setDiscountPrice(vo.getDiscountPrice());
        entity.setLink(vo.getLink());
        entity.setQuantity(vo.getQuantity());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setProductLicense(vo.getProductLicense());
        entity.setCertificate(vo.getCertificate());
        entity.setQualityCertificate(vo.getQualityCertificate());

        entity.setSales(vo.getSales());
        entity.setPlatform(vo.getPlatform());
        entity.setPlatform(vo.getPlatform());
        entity.setBringType(vo.getBringType());
        entity.setCommission(vo.getCommission());
        entity.setGiveBack(vo.getGiveBack());
        entity.setDeposit(vo.getDeposit());
        entity.setOther(vo.getOther());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        }
        if(null != vo.getOrdinal()){
            entity.setOrdinal(vo.getOrdinal());
        }

        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        productsMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Products entity = new Products();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        productsMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Products getById(String id) {
        Products entity = productsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Products> pagedQuery(Pagination pagination, Products condition) {
        return MybatisHelper.selectPage(pagination, () -> productsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Products> pagedQueryWithType(Pagination pagination, Products condition) {
        return MybatisHelper.selectPage(pagination, () -> productsMapper.findByWithType(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ProductsVo> getAppList(Pagination pagination, ProductsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> productsMapper.getAppList(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public ProductsVo getAppDetail(ProductsVo condition) {
        return productsMapper.getAppDetail(condition);
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Products> findBySlide(Products condition) {
        return productsMapper.findBySlide(condition);
    }

    /**
     * 轮播修改
     * @param
     * @return 分页结果
     */
    public void slide(ProductsVo condition) {
        String oldProductsId = condition.getOldSlideId();
        if(StringUtils.isNotBlank(oldProductsId)){
            Products oldProducts = productsMapper.getById(oldProductsId);
            oldProducts.setSlide(0);
            oldProducts.setOrdinal(YllConstants.LAST);
            productsMapper.update(oldProducts);
        }
        condition.setSlide(YllConstants.ONE);
        productsMapper.update(condition);
    }

    /**
     * 获取列表-企业主任务详情
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ProductsVo> getTaskProductList(Pagination pagination, TaskProductsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> productsMapper.getTaskProductList(condition));
    }


    /**
     * 获取列表-企业主任务达人商品列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<ProductsVo> getAppEnterpriseTaskCelebrityProductsList(TaskCelebrityProductsVo condition) {
        return productsMapper.getAppEnterpriseTaskCelebrityProductsList(condition);
    }


    /**
     * 获取列表-企业主任务达人商品列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ProductsVo> getAppTaskCelebrityProducts(Pagination pagination, TaskCelebrityProductsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> productsMapper.getAppTaskCelebrityProducts(condition));
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Products vo) {
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
