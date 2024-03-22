package ru.kishko.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.server.entities.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewsByRestaurantId(Long restaurantId);

}
