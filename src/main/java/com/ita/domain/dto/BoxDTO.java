package com.ita.domain.dto;

import com.ita.domain.entity.Box;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxDTO {

    private Integer id;

    private String address;

    private Integer number;

    private Integer status;

    public static BoxDTO of(Box box) {
        BoxDTO boxDTO = new BoxDTO();
        BeanUtils.copyProperties(box, boxDTO);
        return boxDTO;
    }
}
