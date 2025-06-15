package com.watchott.external.tmdb.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.watchott.movie.dto.*;
import io.micrometer.common.util.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

/**
 * packageName    : watchott.api
 * fileName       : TmdbApiModule.java
 * author         : shipowner
 * date           : 2023-09-11
 * description    : TMDB API 모듈
 */
@Component
public class TmdbClient {

    @Value("#{movieProp['tmdb.api.url']}")
    private String tmdbApiUrl;
    @Value("#{movieProp['tmdb.api.token']}")
    private String tmdbApiToken;

    private String language = "ko-KR";
    private String watch_region = "KR";

    /**
     * methodName : getMovieTrends
     * author : shipowner
     * description : 최신 인기 영화 목록 조회
     *
     */
    public List<MovieDto> getMovieTrends() {
        List<MovieDto> movieList = null;

        try {
            String baseUrl = tmdbApiUrl + "/trending/movie/week";

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("results")) {
                    String results = jsonObject.get("results").toString();

                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                    movieList = Arrays.asList(mapper.readValue(results, MovieDto[].class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieList;
    }


    /**
     * methodName : getMovieProviders
     * author : shipowner
     * description : 전체 OTT 조회
     *
     */
    public List<ProviderDto> getProviders() {
        List<ProviderDto> providerList = null;

        try {
            String baseUrl = tmdbApiUrl + "/watch/providers/movie";

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);
            queryParams.put("watch_region", watch_region);

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("results")) {
                    String results = jsonObject.get("results").toString();

                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);


                    providerList = Arrays.asList(mapper.readValue(results, ProviderDto[].class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return providerList;
    }

    /**
     * methodName : getMovieGenres
     * author : shipowner
     * description : 전체 장르 조회
     *
     */
    public List<GenresDto> getGenres(){
        List<GenresDto> genresList = null;

        try {
            String baseUrl = tmdbApiUrl + "/genre/movie/list";

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("genres")) {
                    String genres = jsonObject.get("genres").toString();

                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                    genresList = Arrays.asList(mapper.readValue(genres, GenresDto[].class));

                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return genresList;
    }


    /**
     * methodName : findMovieDetailByMovieId
     * author : shipowner
     * description : 영화 정보 상세 조회
     *
     */
    public MovieDto findDetailByMovieId(Integer movieId){
        MovieDto movieDto = null;

        try {
            String baseUrl = tmdbApiUrl + "/movie/" + movieId;

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                ObjectMapper mapper = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                movieDto = mapper.readValue(jsonData, MovieDto.class);

                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieDto;
    }

    /**
     * methodName : findAcotrsByMovieId
     * author : shipowner
     * description : 영화 출연 배우 조회
     *
     */
    public List<ActorDto> findAcotrsByMovieId(Integer movieId){
        List<ActorDto> actorList = null;

        try {
            String baseUrl = tmdbApiUrl + "/movie/" + movieId + "/credits";

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("cast")) {
                    String genres = jsonObject.get("cast").toString();

                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                    actorList = Arrays.asList(mapper.readValue(genres, ActorDto[].class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actorList;
    }

    /**
     * methodName : findProviderByMovieId
     * author : shipowner
     * description : 영화 OTT 제공 목록 조회
     *
     */
    public void findProviderByMovieId(MovieDto movieDto){
        try {
            String baseUrl = tmdbApiUrl + "/movie/"+movieDto.getId()+"/watch/providers";

            String jsonData = this.getResponseText(baseUrl, null);

            if(StringUtils.isNotBlank(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("results")) {
                    JSONObject results = jsonObject.getJSONObject("results");

                    if(results.has("KR")) {
                        JSONObject kr = results.getJSONObject("KR");

                        ObjectMapper mapper = new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                        if(kr.has("flatrate")) {
                            String flatrate = kr.get("flatrate").toString();
                            movieDto.setFlatrateList(Arrays.asList(mapper.readValue(flatrate, ProviderDto[].class)));
                        }

                        if(kr.has("buy")) {
                            String buy = kr.get("buy").toString();
                            movieDto.setBuyList(Arrays.asList(mapper.readValue(buy, ProviderDto[].class)));
                        }

                        if(kr.has("rent")) {
                            String rent = kr.get("rent").toString();
                            movieDto.setRentList(Arrays.asList(mapper.readValue(rent, ProviderDto[].class)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * methodName : findMovieList
     * author : shipowner
     * description : 필터를 통한 영화 목록 조회
     *
     */
    public List<MovieDto> findMovieList(MovieSearchDto movieSearchDto){
        List<MovieDto> movieList = null;

        try {
            String baseUrl = tmdbApiUrl + "/discover/movie";

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);

            if(StringUtils.isNotBlank(movieSearchDto.getPage()))
                queryParams.put("page",movieSearchDto.getPage());
            if(StringUtils.isNotBlank(movieSearchDto.getYear()))
                queryParams.put("year",movieSearchDto.getYear());
            if(StringUtils.isNotBlank(movieSearchDto.getRegion()))
                queryParams.put("region",movieSearchDto.getRegion());
            if(StringUtils.isNotBlank(movieSearchDto.getSortBy()))
                queryParams.put("sort_by",movieSearchDto.getSortBy());
            if(StringUtils.isNotBlank(movieSearchDto.getReleaseDateLte()))
                queryParams.put("release_date.lte",movieSearchDto.getReleaseDateLte());
            if(StringUtils.isNotBlank(movieSearchDto.getReleaseDateGte()))
                queryParams.put("release_date.gte",movieSearchDto.getReleaseDateGte());
            if(movieSearchDto.getWithGenres() != null && movieSearchDto.getWithGenres().size() > 0)
                queryParams.put("with_genres",String.join("|",movieSearchDto.getWithGenres()));
            if(movieSearchDto.getWithCompanies() != null && movieSearchDto.getWithCompanies().size() > 0)
                queryParams.put("with_companies",String.join("|",movieSearchDto.getWithCompanies()));
            if(movieSearchDto.getWithKeywords() != null && movieSearchDto.getWithKeywords().size() > 0)
                queryParams.put("with_keywords",String.join("|",movieSearchDto.getWithKeywords()));
            if(movieSearchDto.getWithWatchProviders() != null && movieSearchDto.getWithWatchProviders().size() > 0)
                queryParams.put("with_watch_providers",String.join("|",movieSearchDto.getWithWatchProviders()));

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("results")) {
                    String results = jsonObject.get("results").toString();

                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                    movieList = Arrays.asList(mapper.readValue(results, MovieDto[].class));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieList;
    }

    public MovieListDto searchMovies(MovieSearchDto movieSearchDto){
        MovieListDto movieListDto = null;

        try {
            String baseUrl = tmdbApiUrl + "/search/movie";

            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("language", language);

            if(StringUtils.isNotBlank(movieSearchDto.getQuery()))
                queryParams.put("query",movieSearchDto.getQuery());
            if(StringUtils.isNotBlank(movieSearchDto.getYear()))
                queryParams.put("year",movieSearchDto.getYear());
            if(StringUtils.isNotBlank(movieSearchDto.getPage()))
                queryParams.put("page",movieSearchDto.getPage());
            if(StringUtils.isNotBlank(movieSearchDto.getRegion()))
                queryParams.put("region",movieSearchDto.getRegion());

            String jsonData = this.getResponseText(baseUrl, queryParams);

            if(StringUtils.isNotBlank(jsonData)) {
                movieListDto = new MovieListDto();

                JSONObject jsonObject = new JSONObject(jsonData);

                if(jsonObject.has("results")) {
                    String results = jsonObject.get("results").toString();

                    ObjectMapper mapper = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                            .setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

                    movieListDto.setMovieList(Arrays.asList(mapper.readValue(results, MovieDto[].class)));

                }

                if(jsonObject.has("page")) {
                    movieListDto.setPage(jsonObject.getInt("page"));
                }

                if(jsonObject.has("total_pages")) {
                    movieListDto.setTotalPages(jsonObject.getInt("total_pages"));
                }

                if(jsonObject.has("total_results")) {
                    movieListDto.setTotalResults(jsonObject.getInt("total_results"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return movieListDto;
    }

    private String getResponseText(String baseUrl, Map<String, String> queryParams) {
        String responseText = null;

        StringBuilder uriBuilder = new StringBuilder(baseUrl);

        if(queryParams != null) {
            uriBuilder.append("?");
            queryParams.forEach((key, value) -> {
                uriBuilder.append(key).append("=").append(URLEncoder.encode(value)).append("&");
            });
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uriBuilder.toString()))
                    .header("accept", "application/json")
                    .header("Authorization", tmdbApiToken)
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseText = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseText;
    }

}
