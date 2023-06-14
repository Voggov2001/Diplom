package com.example.diplom.dto.db;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDatabaseDto {
    private Long filmId;
    private String name;
    private String reference;

    @Override
    public String toString() {

        return
                "ID: " + filmId + "\n"
                        + "Название: \"" + name + "\"\n"
                        + "Ссылка на фильм: " + reference;
    }
}
