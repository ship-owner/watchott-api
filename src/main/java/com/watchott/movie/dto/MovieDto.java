package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * packageName    : watchott.dto
 * fileName       : MovieDto.java
 * author         : shipowner
 * date           : 2023-09-11
 * description    : 영화 정보 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class MovieDto {

    private Integer id;
    private String title;
    private Boolean video;  //비디오 공개여부
    private Boolean adult;  //성인관람가
    private String posterPath;
    private String backdropPath;
    private String overview;
    private BigDecimal voteAverage;
    private Integer voteCount;
    private BigDecimal popularity;
    private String releaseDate;
    private Integer runtime;

    //장르
    private List<Map> genres;
    //회사
    private List<Map> productionCompanies;

    private List<ProviderDto> flatrateList;
    private List<ProviderDto> buyList;
    private List<ProviderDto> rentList;

    private List<ActorDto> actorList;

    private List<CommentDto> commentList;


}
