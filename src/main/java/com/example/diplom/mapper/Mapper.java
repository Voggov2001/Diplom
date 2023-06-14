package com.example.diplom.mapper;


import com.example.diplom.dto.db.FilmDatabaseDto;
import com.example.diplom.model.Film;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private final ModelMapper mapper;

    public Mapper() {
        this.mapper = new ModelMapper();
    }

    public FilmDatabaseDto toDto(Film film) {
        return mapper.map(film, FilmDatabaseDto.class);
    }
    public Film toEntity(FilmDatabaseDto dto) {
        return mapper.map(dto, Film.class);
    }
}
