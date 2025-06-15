package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MovieListDto {

    private Integer page;
    private Integer totalPages;
    private Integer totalResults;

    private List<MovieDto> movieList;

}
