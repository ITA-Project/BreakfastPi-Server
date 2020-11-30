package com.ita.domain.controller;

import com.ita.domain.dataobject.FoodDO;
import com.ita.domain.service.FoodService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (FoodDO)表控制层
 *
 * @author makejava
 * @since 2020-11-30 16:42:18
 */
@RestController
@RequestMapping("FoodDO")
public class FoodController {

  /**
   * 服务对象
   */
  @Resource
  private FoodService FoodService;

  /**
   * 通过主键查询单条数据
   *
   * @param id 主键
   * @return 单条数据
   */
  @GetMapping("selectOne")
  public FoodDO selectOne(Integer id) {
    return this.FoodService.queryById(id);
  }

  /**
   * 通过特定条件查询多条数据
   *
   * @param FoodDO 实体条件
   * @return 多条数据
   */
  @GetMapping("selectByQuery")
  public List<FoodDO> selectByQuery(FoodDO FoodDO) {
    return this.FoodService.queryAll(FoodDO);
  }

}