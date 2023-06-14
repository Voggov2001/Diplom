package com.example.diplom.dto.name;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private String genre;

    @Override
    public String toString() {
        return genre + ", ";
    }
}
