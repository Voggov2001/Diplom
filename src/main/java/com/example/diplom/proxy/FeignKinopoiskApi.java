package com.example.diplom.proxy;

import com.example.diplom.dto.genre.GenresDto;
import com.example.diplom.dto.name.FilmsDto;
import com.example.diplom.dto.top.TotalTopFilmsDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "kinoApi", url = "https://kinopoiskapiunofficial.tech/api")
@Headers("Content-Type:application/json")
public interface FeignKinopoiskApi {

    @GetMapping("v2.1/films/search-by-keyword")
    FilmsDto getByName(@RequestHeader("X-API-KEY") String token, @RequestParam("keyword") String keyword);

    @GetMapping("/v2.2/films?genres={genres}")
    GenresDto getByGenre(@RequestHeader("X-API-KEY") String token, @PathVariable Long genres);

    @GetMapping("/v2.2/films")
    GenresDto getByRating(@RequestHeader("X-API-KEY") String token, @RequestParam("ratingFrom") Integer ratingFrom);

    @GetMapping("/v2.2/films/top")
    TotalTopFilmsDto getTop(@RequestHeader("X-API-KEY") String token, @RequestParam("page") Integer page);
}
