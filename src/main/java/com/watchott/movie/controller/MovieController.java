package com.watchott.movie.controller;

import com.watchott.common.dto.ApiResponse;
import com.watchott.movie.dto.MovieDto;
import com.watchott.movie.dto.MovieListDto;
import com.watchott.movie.dto.MovieSearchDto;
import com.watchott.movie.dto.ProviderDto;
import com.watchott.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @GetMapping(value = "/detail/{movieId}")
    public ApiResponse<MovieDto> detail(@PathVariable(value = "movieId") Integer movieId) {
        try {
            return ApiResponse.success(movieService.findDetailByMovieId(movieId));
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

    @GetMapping(value = "/trend")
    public ApiResponse<List<MovieDto>> getMovieTrends(){
        try {
            return ApiResponse.success(movieService.getMovieTrends());
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

    @GetMapping(value = "/latest")
    public ApiResponse<List<MovieDto>> getLatestMovies()
    {
        try {
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
            return ApiResponse.success(movieService.findMovieList(movieSearchDto));
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }

    @GetMapping(value = "/providers")
    public ApiResponse<List<ProviderDto>> getProviders(){
        try {
            return ApiResponse.success(movieService.getProviders());
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }


    @GetMapping(value = "/search")
    public ApiResponse<MovieListDto> searchMovies(MovieSearchDto movieSearchDto){
        try {
            return ApiResponse.success(movieService.searchMovies(movieSearchDto));
        } catch (Exception e) {
            return ApiResponse.error();
        }
    }
}
