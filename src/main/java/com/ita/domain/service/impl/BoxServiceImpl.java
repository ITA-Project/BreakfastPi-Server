package com.ita.domain.service.impl;

import com.ita.domain.dto.OrderDTO;
import com.ita.domain.entity.Box;
import com.ita.domain.mapper.BoxMapper;
import com.ita.domain.service.BoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BoxServiceImpl implements BoxService {

    private final BoxMapper boxMapper;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private MqttMessageServiceImpl mqttMessageService;

    public BoxServiceImpl(BoxMapper boxMapper) {
        this.boxMapper = boxMapper;
    }

    @Override
    public Box selectById(Integer id) {
        return boxMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Box> selectAll() {
        return boxMapper.selectAll();
    }

    @Override
    public List<Box> selectByStatus(Integer status) {
        return boxMapper.selectByStatus(status);
    }

    @Override
    public int create(Box box) {
        return boxMapper.insert(box);
    }

    @Override
    public int delete(Integer id) {
        return boxMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Box box) {
        return boxMapper.updateByPrimaryKey(box);
    }

    public boolean openAssociateOrdersBoxes(List<Integer> orderIds) {
        List<OrderDTO> orderList = orderService.getOrdersByIds(orderIds);
        List<String> boxIds = orderList.stream().map(orders -> orders.getBox().getId().toString()).collect(Collectors.toList());
        boxIds.stream().forEach(id -> mqttMessageService.send(id));
        return true;
    }

    @Override
    public boolean updateStatusById(Integer id, Integer status) {
        return boxMapper.updateStatusById(id, status) == 1;
    }
}
