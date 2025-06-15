package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : watchott.dto
 * fileName       : ProviderDto
 * author         : shipowner
 * date           : 2023-09-11
 * description    : OTT 정보 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class ProviderDto {

    private Integer providerId;
    private String logoPath;
    private String providerName;

}
