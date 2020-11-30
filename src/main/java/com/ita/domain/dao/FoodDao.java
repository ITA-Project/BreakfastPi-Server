package com.ita.domain.dao;

import com.ita.domain.dataobject.FoodDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (FoodDO)表数据库访问层
 *
 * @author makejava
 * @since 2020-11-30 16:42:12
 */
@Mapper
public interface FoodDao {

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  FoodDO queryById(Integer id);

  /**
   * 查询指定行数据
   *
   * @param offset 查询起始位置
   * @param limit 查询条数
   * @return 对象列表
   */
  List<FoodDO> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


  /**
   * 通过实体作为筛选条件查询
   *
   * @param FoodDO 实例对象
   * @return 对象列表
   */
  List<FoodDO> queryAll(FoodDO FoodDO);

  /**
   * 新增数据
   *
   * @param FoodDO 实例对象
   * @return 影响行数
   */
  int insert(FoodDO FoodDO);

  /**
   * 修改数据
   *
   * @param FoodDO 实例对象
   * @return 影响行数
   */
  int update(FoodDO FoodDO);

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 影响行数
   */
  int deleteById(Integer id);

}