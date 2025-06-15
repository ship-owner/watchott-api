package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : watchott.dto
 * fileName       : ActorDto
 * author         : shipowner
 * date           : 2023-09-14
 * description    : 영화 배우
 */

@Getter
@Setter
@NoArgsConstructor
public class ActorDto {

    private Integer id;
    private Integer castId;
    private Integer gender;
    private String name;
    private String profilePath;
    private String character;
    private Boolean adult;

}
