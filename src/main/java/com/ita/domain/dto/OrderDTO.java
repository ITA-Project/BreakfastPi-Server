package com.ita.domain.dto;

import com.ita.domain.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;

    private String orderNumber;

    private LocalDateTime paymentTime;

    private LocalDateTime deliverTime;

    private LocalDateTime storeTime;

    private LocalDateTime acceptedTime;

    private LocalDateTime completedTime;

    private LocalDateTime cancelTime;

    private Double amount;

    private Integer userId;

    private BoxDTO box;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private LocalDateTime estimatedTime;

    private Integer status;

    private List<OrderItemDTO> orderItems;

    public static OrderDTO of(Order order, BoxDTO box, List<OrderItemDTO> orderItems) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        orderDTO.setBox(box);
        orderDTO.setOrderItems(orderItems);
        return orderDTO;
    }
}
