package com.ita.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
