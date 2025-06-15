package com.watchott.movie.controller;

import com.watchott.common.dto.ApiResponse;
import com.watchott.common.service.RedisService;
import com.watchott.movie.dto.MovieDto;
import com.watchott.movie.dto.MovieListDto;
import com.watchott.movie.dto.MovieSearchDto;
import com.watchott.movie.dto.ProviderDto;
import com.watchott.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * packageName    : watchott.controller
 * fileName       : MovieController
 * author         : shipowner
 * date           : 2023-09-11
 */

@RestController
@RequestMapping(value = "/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    private final RedisService redisService;

    @GetMapping(value = "/detail/{movieId}")
    public ApiResponse<MovieDto> detail(@PathVariable(value = "movieId") Integer movieId) {
      return ApiResponse.success(movieService.findDetailByMovieId(movieId));
    }


    @GetMapping(value = "/trend")
    public ApiResponse<List<MovieDto>> getMovieTrends(){

        List<MovieDto> trends = (List<MovieDto>) redisService.getData("movieTrends");

        if (trends == null || trends.isEmpty()) {
            trends = movieService.getMovieTrends();
            redisService.setData("movieTrends",trends);
        }

        return ApiResponse.success(trends);
    }

    @GetMapping(value = "/latest")
    public ApiResponse<List<MovieDto>> getLatestMovies()
    {
        List<MovieDto> latest = (List<MovieDto>) redisService.getData("movieLatest");

        if (latest == null || latest.isEmpty()) {
            latest = movieService.getLatestMovie();
            redisService.setData("movieLatest", latest);
        }

        return ApiResponse.success(latest);
    }

    @GetMapping(value = "/providers")
    public ApiResponse<List<ProviderDto>> getProviders(){
        return ApiResponse.success(movieService.getProviders());
    }


    @GetMapping(value = "/search")
    public ApiResponse<MovieListDto> searchMovies(MovieSearchDto movieSearchDto){
        return ApiResponse.success(movieService.searchMovies(movieSearchDto));
    }
}
