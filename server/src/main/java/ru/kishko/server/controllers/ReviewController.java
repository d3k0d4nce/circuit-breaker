package ru.kishko.server.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kishko.server.dtos.ReviewDTO;
import ru.kishko.server.services.ReviewService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    int status = 200;
    @GetMapping("/status")
    ResponseEntity<String> status(){
        String response = "Status code: " + status;
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(status));
    }

    @GetMapping("/create")
    ResponseEntity<Object> create(ReviewDTO reviewDTO){
        if (status < 400)
            return new ResponseEntity<>(reviewService.createReview(reviewDTO), HttpStatusCode.valueOf(status));

        return new ResponseEntity<>("Status code: " + status, HttpStatus.valueOf(status));

    }

    @PostMapping("/change/{status}")
    ResponseEntity<Object> changeStatus(@PathVariable Integer status){
        if (status < 100 || status >= 600)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        this.status = status;
        return new ResponseEntity<>("Status changed on: " + status,HttpStatus.OK);
    }

}
