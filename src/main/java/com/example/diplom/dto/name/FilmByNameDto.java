package com.example.diplom.dto.name;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmByNameDto {
    private Long filmId;
    private String nameRu;
    private String year;
    private String description;
    private String rating;
    private String posterUrl;
    private List<Genre> genres;


    @Override
    public String toString() {

        return
                "ID: " + filmId + "\n"
                        + "Название: " + nameRu + "\n"
                        + "Год: " + year + "\n"
                        + "Описание: " + description + "\n"
                        + "Рейтинг: " + rating + "\n"
                        + "Жанры: " + genres + "\n"
                        + posterUrl;
    }
}
