package com.example.diplom.dto.top;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalTopFilmsDto {
    private Integer pagesCount;
    private List<TopFilmsDto> films;
}
