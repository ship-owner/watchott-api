package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : watchott.dto
 * fileName       : GenresDto
 * author         : shipowner
 * date           : 2023-09-11
 * description    : 영화 장르
 */

@Getter
@Setter
@NoArgsConstructor
public class GenresDto {

    private Integer id;
    private String name;

}
