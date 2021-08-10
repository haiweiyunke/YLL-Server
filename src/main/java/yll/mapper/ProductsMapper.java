package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Products;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ProductsVo;
import yll.service.model.vo.TaskCelebrityProductsVo;
import yll.service.model.vo.TaskProductsVo;

import java.util.List;

/**
 * 商品_Mapper接口
 * @author cc
 */
@Mapper
public interface ProductsMapper extends BasicMapper<Products, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Products> findBy(Products condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<Products> findByWithType(Products condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<ProductsVo> getAppList(ProductsVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    ProductsVo getAppDetail(ProductsVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<Products> findBySlide(Products condition);

    /**
     * 获取列表-企业主任务详情
     * @param condition
     */
    List<ProductsVo> getTaskProductList(TaskProductsVo condition);

    /**
     * 获取列表-企业主任务达人商品列表
     * @param condition
     */
    List<ProductsVo> getAppEnterpriseTaskCelebrityProductsList(TaskCelebrityProductsVo condition);


    /**
     * 获取列表-达人任务达人商品列表
     * @param condition
     */
    List<ProductsVo> getAppTaskCelebrityProducts(TaskCelebrityProductsVo condition);


}
