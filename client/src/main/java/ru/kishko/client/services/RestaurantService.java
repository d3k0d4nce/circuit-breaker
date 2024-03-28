package ru.kishko.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kishko.client.dtos.RestaurantDTO;
import ru.kishko.client.entities.Restaurant;
import ru.kishko.client.exceptions.RestaurantNotFound;
import ru.kishko.client.mappers.RestaurantMapper;
import ru.kishko.client.repositories.RestaurantRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final WebClient webReviewClient;

    public RestaurantDTO createRestaurant(RestaurantDTO restaurant) {
        restaurantRepository.save(restaurantMapper.map(restaurant));
        return restaurant;
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::map).toList();
    }

    public RestaurantDTO getRestaurantById(Long restaurantId) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RestaurantNotFound("There IS no restaurant with id: " + restaurantId)
        );

        return restaurantMapper.map(restaurant);
    }

    public RestaurantDTO updateRestaurantById(Long restaurantId, RestaurantDTO restaurant) {
        RestaurantDTO restaurantDB = getRestaurantById(restaurantId);

        if (restaurant.getName() != null && !"".equals(restaurant.getName())) {
            restaurantDB.setName(restaurant.getName());
        }

        restaurantRepository.save(restaurantMapper.map(restaurantDB));
        return restaurantDB;
    }

    public String deleteRestaurantById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
        return "successfully deleted";
    }

    public List<RestaurantDTO> findTop5RestaurantsByReviewsCount() {
        return restaurantRepository.findTop5RestaurantsByReviewsCount(Limit.of(5)).stream()
                .map(restaurantMapper::map).toList();
    }

    public String getRestaurantWithReviews(Long restaurantId) {
        String reviews = webReviewClient.get().uri("/restaurants/" + restaurantId).retrieve().bodyToMono(String.class).block();
        return getRestaurantById(restaurantId).getName() + reviews;
    }

}
