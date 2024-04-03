package ru.kishko.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kishko.server.dtos.ReviewDTO;
import ru.kishko.server.mappers.ReviewMapper;
import ru.kishko.server.repositories.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDTO createReview(ReviewDTO review) {
        reviewRepository.save(reviewMapper.map(review));
        return review;
    }
}
