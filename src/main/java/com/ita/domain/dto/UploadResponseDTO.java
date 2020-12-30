package com.ita.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponseDTO {

    private Integer code;

    private String id;

    private String imgid;

    private String relativePath;

    private String url;

    private String thumbnailUrl;

    private Integer width;

    private Integer height;
}
