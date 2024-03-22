package ru.kishko.server.mappers;

import org.springframework.stereotype.Component;
import ru.kishko.server.dtos.ReviewDTO;
import ru.kishko.server.entities.Review;

@Component
public class ReviewMapper {

    public ReviewDTO map(Review review) {
        return ReviewDTO.builder()
                .description(review.getDescription())
                .mark(review.getMark())
                .build();
    }

    public Review map(ReviewDTO reviewDTO) {
        return Review.builder()
                .description(reviewDTO.getDescription())
                .mark(reviewDTO.getMark())
                .build();
    }

}
