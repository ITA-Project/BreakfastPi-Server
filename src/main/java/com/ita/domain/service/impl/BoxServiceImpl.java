package com.ita.domain.service.impl;

import com.ita.domain.dto.OrderDTO;
import com.ita.domain.entity.Box;
import com.ita.domain.mapper.BoxMapper;
import com.ita.domain.service.BoxService;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Box selectById(Integer id) {
        return boxMapper.selectByPrimaryKey(id);
    }

    public List<Box> selectAll() {
        return boxMapper.selectAll();
    }

    public int create(Box box) {
        return boxMapper.insert(box);
    }

    public int delete(Integer id) {
        return boxMapper.deleteByPrimaryKey(id);
    }

    public int update(Box box) {
        return boxMapper.updateByPrimaryKey(box);
    }

    public boolean openAssociateOrdersBoxes(List<Integer> orderIds) {
        List<OrderDTO> orderList = orderService.getOrdersByIds(orderIds);
        List<String> boxIds = orderList.stream().map(orders -> orders.getBox().getId().toString()).collect(Collectors.toList());
        boxIds.stream().forEach(id -> mqttMessageService.send(id));
        return true;
    }
}
