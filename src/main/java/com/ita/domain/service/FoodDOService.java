package com.ita.domain.service;

import com.ita.domain.dataobject.FoodDO;
import java.util.List;

/**
 * (FoodDO)表服务接口
 *
 * @author makejava
 * @since 2020-11-30 16:42:13
 */
public interface FoodDOService {

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  FoodDO queryById(Integer id);

  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit 查询条数
   * @return 对象列表
   */
  List<FoodDO> queryAllByLimit(int offset, int limit);

  /**
   * 按照实体条件查询多条数据
   *
   * @param FoodDO 实体对象
   * @return 对象列表
   */
  List<FoodDO> queryAll(FoodDO FoodDO);

  /**
   * 新增数据
   *
   * @param FoodDO 实例对象
   * @return 实例对象
   */
  FoodDO insert(FoodDO FoodDO);

  /**
   * 修改数据
   *
   * @param FoodDO 实例对象
   * @return 实例对象
   */
  FoodDO update(FoodDO FoodDO);

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 是否成功
   */
  boolean deleteById(Integer id);

}