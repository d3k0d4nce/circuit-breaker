package ru.kishko.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.client.entities.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
