package ru.kishko.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kishko.server.dtos.ReviewDTO;
import ru.kishko.server.entities.Review;
import ru.kishko.server.mappers.ReviewMapper;
import ru.kishko.server.repositories.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewDTO createReview(ReviewDTO review) {
        reviewRepository.save(reviewMapper.map(review));
        return review;
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::map).toList();
    }

    public ReviewDTO getReviewById(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("There is no review with id: " + reviewId)
        );

        return reviewMapper.map(review);
    }

    public ReviewDTO updateReviewById(Long reviewId, ReviewDTO review) {
        ReviewDTO restaurantDB = getReviewById(reviewId);

        if (review.getDescription() != null && !"".equals(review.getDescription())) {
            restaurantDB.setDescription(review.getDescription());
        }

        if (review.getMark() != null && review.getMark() != 0) {
            restaurantDB.setMark(review.getMark());
        }

        reviewRepository.save(reviewMapper.map(restaurantDB));
        return restaurantDB;
    }

    public String deleteReviewById(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return "successfully deleted";
    }

    public List<ReviewDTO> getReviewsByRestaurantId(Long restaurantId) {
        return reviewRepository.findReviewsByRestaurantId(restaurantId).stream()
                .map(reviewMapper::map).toList();
    }
}
