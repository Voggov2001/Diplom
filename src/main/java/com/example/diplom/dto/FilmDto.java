package com.example.diplom.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {
    private String nameRu;
    private String year;
    private String description;
    private String filmLength;
    private String ratingKinopoisk;
    private String posterUrl;

    @Override
    public String toString() {

       return
                "Название: " + nameRu + "\n"
                + "Год: " + year + "\n"
                + "Описание: " + description + "\n"
                + "Продолжительность: " + filmLength + "\n"
                + "Рейтинг: " + ratingKinopoisk + "\n"
                + posterUrl;
    }


}
