package com.example.diplom.dto.top;

import com.example.diplom.dto.name.Genre;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopFilmsDto {
    private String filmId;
    private String nameRu;
    private String year;
    private List<Genre> genres;
    private String rating;
    private String posterUrl;

    @Override
    public String toString() {

        return
                "ID: " + filmId + "\n"
                        + "Название: " + nameRu + "\n"
                        + "Год: " + year + "\n"
                        + "Рейтинг: " + rating + "\n"
                        + "Жанры: " + genres + "\n"
                        + posterUrl;
    }

}
