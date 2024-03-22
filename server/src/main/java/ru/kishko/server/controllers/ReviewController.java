package ru.kishko.server.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.server.dtos.ReviewDTO;
import ru.kishko.server.services.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO review) {
        return new ResponseEntity<>(reviewService.createReview(review), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return new ResponseEntity<>(reviewService.getAllReviews(), HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReviewById(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewDTO review) {
        return new ResponseEntity<>(reviewService.updateReviewById(reviewId, review), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@PathVariable("reviewId") Long reviewId) {
        return new ResponseEntity<>(reviewService.deleteReviewById(reviewId), HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByRestaurantId(@PathVariable("restaurantId") Long restaurantId) {
        return new ResponseEntity<>(reviewService.getReviewsByRestaurantId(restaurantId), HttpStatus.OK);
    }

}
