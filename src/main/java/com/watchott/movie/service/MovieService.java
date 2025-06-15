package com.watchott.movie.service;

import com.watchott.movie.dto.MovieDto;
import com.watchott.movie.dto.MovieListDto;
import com.watchott.movie.dto.MovieSearchDto;
import com.watchott.movie.dto.ProviderDto;
import com.watchott.external.tmdb.client.TmdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * packageName    : watchott.core.service
 * fileName       : MovieService
 * author         : shipowner
 * date           : 2023-09-12
 */

@Service
@RequiredArgsConstructor
public class MovieService {

    private final TmdbClient tmdbClient;

    /**
     * methodName : getMovieTrends
     * author : shipowner
     * description : 최신 트렌드 영화 목록 조회
     *
     */
    public List<MovieDto> getMovieTrends(){
        return tmdbClient.getMovieTrends();
    }

    /**
     * methodName : getLatestMovie
     * author : shipowner
     * description : 최신 영화 목록 조회
     *
     */
    public List<MovieDto> getLatestMovie(){
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, -1);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        MovieSearchDto movieSearchDto = new MovieSearchDto();
        movieSearchDto.setRegion("KR");
        movieSearchDto.setSortBy("popularity.desc");
        movieSearchDto.setReleaseDateLte(sdf.format(now));
        movieSearchDto.setReleaseDateGte(sdf.format(calendar.getTime()));

        return tmdbClient.findMovieList(movieSearchDto);
    }


    /**
     * methodName : getProviders
     * author : shipowner
     * description : 영화 제공 OTT 목록 조회
     *
     */
    public List<ProviderDto> getProviders(){
        return tmdbClient.getProviders();
    }

    /**
     * methodName : getProviders
     * author : shipowner
     * description : 검색 필터에 따른 영화 목록 조회
     *
     */
    public List<MovieDto> findMovieList(MovieSearchDto movieSearchDto){
        return tmdbClient.findMovieList(movieSearchDto);
    }

    /**
     * methodName : findDetailByMovieId
     * author : shipowner
     * description : 영화 상세 정보 조회
     *
     */
    public MovieDto findDetailByMovieId(Integer movieId){
        MovieDto movieDto = tmdbClient.findDetailByMovieId(movieId);

        movieDto.setActorList(tmdbClient.findAcotrsByMovieId(movieId));
        tmdbClient.findProviderByMovieId(movieDto);

        return movieDto;
    }

    public MovieListDto searchMovies(MovieSearchDto movieSearchDto){
        return tmdbClient.searchMovies(movieSearchDto);
    }

}
