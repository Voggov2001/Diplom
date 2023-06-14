package com.example.diplom.dto.genre;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenresDto {
    private List<GenreDto> items;

    @Override
    public String toString() {

        return
                "Название: " + items.get(0).getNameRu() + "\n"
                        + "Год: " + items.get(0).getYear() + "\n"
                        + "Рейтинг: " + items.get(0).getRatingKinopoisk() + "\n"
                        + items.get(0).getPosterUrl();
    }
}
