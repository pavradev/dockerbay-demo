package com.github.pavradev.dockerbay.demo.service;

import java.util.List;

import com.github.pavradev.dockerbay.demo.domain.Review;

public interface ReviewService {

    List<Review> getReviewsByHotel(Long hotelId);
}
