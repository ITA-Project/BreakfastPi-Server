package com.ita.domain.service.impl;

import com.ita.domain.dao.FoodDODao;
import com.ita.domain.dataobject.FoodDO;
import com.ita.domain.service.FoodDOService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * (FoodDO)表服务实现类
 *
 * @author makejava
 * @since 2020-11-30 16:42:15
 */
@Service("FoodDOService")
public class FoodDOServiceImpl implements FoodDOService {

  @Resource
  private FoodDODao FoodDODao;

  /**
   * 通过ID查询单条数据
   *
   * @param id 主键
   * @return 实例对象
   */
  @Override
  public FoodDO queryById(Integer id) {
    return this.FoodDODao.queryById(id);
  }

  /**
   * 查询多条数据
   *
   * @param offset 查询起始位置
   * @param limit 查询条数
   * @return 对象列表
   */
  @Override
  public List<FoodDO> queryAllByLimit(int offset, int limit) {
    return this.FoodDODao.queryAllByLimit(offset, limit);
  }

  /**
   * 按照实体条件查询多条数据
   *
   * @param FoodDO 实体对象
   * @return 对象列表
   */
  @Override
  public List<FoodDO> queryAll(FoodDO FoodDO) {
    return this.FoodDODao.queryAll(FoodDO);
  }

  /**
   * 新增数据
   *
   * @param FoodDO 实例对象
   * @return 实例对象
   */
  @Override
  public FoodDO insert(FoodDO FoodDO) {
    this.FoodDODao.insert(FoodDO);
    return FoodDO;
  }

  /**
   * 修改数据
   *
   * @param FoodDO 实例对象
   * @return 实例对象
   */
  @Override
  public FoodDO update(FoodDO FoodDO) {
    this.FoodDODao.update(FoodDO);
    return this.queryById(FoodDO.getId());
  }

  /**
   * 通过主键删除数据
   *
   * @param id 主键
   * @return 是否成功
   */
  @Override
  public boolean deleteById(Integer id) {
    return this.FoodDODao.deleteById(id) > 0;
  }
}