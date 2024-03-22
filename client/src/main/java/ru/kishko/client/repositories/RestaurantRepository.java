package ru.kishko.client.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kishko.client.entities.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r ORDER BY (count(r.reviewsId)) DESC")
    List<Restaurant> findTop5RestaurantsByReviewsCount(Limit of);

}
