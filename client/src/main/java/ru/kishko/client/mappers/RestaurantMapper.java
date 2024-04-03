package ru.kishko.client.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kishko.client.dtos.RestaurantDTO;
import ru.kishko.client.entities.Restaurant;

@Component
@RequiredArgsConstructor
public class RestaurantMapper {

    public RestaurantDTO map(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .name(restaurant.getName())
                .build();
    }

    public Restaurant map(RestaurantDTO restaurantDTO) {
        return Restaurant.builder()
                .name(restaurantDTO.getName())
                .build();
    }
}

