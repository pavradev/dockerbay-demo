package com.github.pavradev.dockerbay.demo.service;

import com.github.pavradev.dockerbay.demo.domain.Review;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private static Logger log = LoggerFactory.getLogger(ReviewServiceImpl.class);

    @Value("${reviews.server.url}")
    String reviewsServerUrl;

    @Override
    public List<Review> getReviewsByHotel(Long hotelId) {
        RestTemplate restTemplate = new RestTemplate();
        List<Review> reviews = new ArrayList<>();
        try {
            ResponseEntity<List<Review>> rateResponse = restTemplate.exchange(
                    reviewsServerUrl + "/api/reviews?hotelId=" + hotelId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Review>>() {
                    });
            reviews = rateResponse.getBody();
        } catch (Exception e) {
            log.error("Could not get reviews for hotel {} due to: {}", hotelId, e.getMessage());
        }
        return reviews;
    }
}
