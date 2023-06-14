package com.example.diplom.service;

import com.example.diplom.dto.db.FilmDatabaseDto;
import com.example.diplom.mapper.Mapper;
import com.example.diplom.model.Film;
import com.example.diplom.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {

    @Autowired
    private final FilmRepository filmRepository;
    @Autowired
    private final Mapper mapper;

    public FilmService(FilmRepository filmRepository, Mapper mapper) {
        this.filmRepository = filmRepository;
        this.mapper = mapper;
        init();
    }

    private void init(){
        if (filmRepository.findAll().size() == 0) {
            filmRepository.save(Film.builder()
                            .filmId(435L)
                            .name("Зеленая миля")
                            .reference("https://drive.google.com/file/d/1_qHSWL7ULjrLLMMXfqo8L3h-OPNIp84A/view?usp=drive_link")
                    .build());
        }
    }

    public FilmDatabaseDto getFilmById(Long id) {
        return mapper.toDto(filmRepository.findByFilmId(id));
    }

}
