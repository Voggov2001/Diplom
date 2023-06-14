package com.example.diplom.dto.genre;

import com.example.diplom.dto.name.Genre;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private String kinopoiskId;
    private String nameRu;
    private List<Genre> genres;
    private String ratingKinopoisk;
    private String ratingImdb;
    private String year;
    private String posterUrl;

    @Override
    public String toString() {

        return
                "ID: " + kinopoiskId + "\n"
                        + "Название: " + nameRu + "\n"
                        + "Год: " + year + "\n"
                        + "Рейтинг кинопоиск: " + ratingKinopoisk + "\n"
                        + "Рейтинг IMdb: " + ratingImdb + "\n"
                        + "Жанры: " + genres + "\n"
                        + posterUrl;
    }
}
