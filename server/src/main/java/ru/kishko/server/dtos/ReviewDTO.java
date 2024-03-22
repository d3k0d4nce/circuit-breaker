package ru.kishko.server.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDTO {

    private String description;

    private Double mark;

}
