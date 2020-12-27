package com.ita.domain.dto;

import com.ita.domain.entity.PayInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author Dillon Xie
 * @date 12/22/2020
 */
@Data
@NoArgsConstructor
public class PayInfoDTO {
    private Integer id;
    private String orderNumber;
    private String payNumber;
    private Integer payStatus;
    private Integer userId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static PayInfoDTO of(PayInfo payInfo) {
        PayInfoDTO payInfoDTO = new PayInfoDTO();
        BeanUtils.copyProperties(payInfo, payInfoDTO);
        return payInfoDTO;
    }
}
