package com.watchott.movie.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : watchott.dto
 * fileName       : MovieSearchDto
 * author         : shipowner
 * date           : 2023-09-11
 * description    : 영화 검색
 */

@Getter
@Setter
@NoArgsConstructor
public class MovieSearchDto {

    //private String language;
    private String page;
    private String year;
    private String region;
    private String query;
    private String sortBy;
    private String releaseDateGte;
    private String releaseDateLte;
    private String voteAverageGte;
    private String voteAverageLte;

    private List<String> withCompanies = new ArrayList<>();
    private List<String> withGenres = new ArrayList<>();
    private List<String> withKeywords = new ArrayList<>();
    private List<String> withWatchProviders = new ArrayList<>();

}
