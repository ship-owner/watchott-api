package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : watchott.dto
 * fileName       : Companies
 * author         : shipowner
 * date           : 2023-09-11
 * description    : 영화 제작 회사
 */

@Getter
@Setter
@NoArgsConstructor
public class CompaniesDto {

    private Integer id;
    private String name;
    private String description;
    private String headquarters;
    private String homepage;
    private String logoPath;
    private String originCountry;
    private String parentCompany;


}
