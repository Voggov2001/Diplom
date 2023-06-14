package com.example.diplom.dto.name;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmsDto {
    List<FilmByNameDto> films;

    @Override
    public String toString() {

        return
                "Название: " + films.get(0).getNameRu() + "\n"
                        + "Год: " + films.get(0).getYear() + "\n"
                        + "Описание: " + films.get(0).getDescription() + "\n"
                        + "Рейтинг: " + films.get(0).getRating() + "\n"
                        + films.get(0).getPosterUrl();
    }
}
