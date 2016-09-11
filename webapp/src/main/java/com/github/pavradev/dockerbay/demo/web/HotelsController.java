package com.github.pavradev.dockerbay.demo.web;

import java.util.List;

import com.github.pavradev.dockerbay.demo.domain.Hotel;
import com.github.pavradev.dockerbay.demo.domain.Review;
import com.github.pavradev.dockerbay.demo.service.HotelService;
import com.github.pavradev.dockerbay.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HotelsController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReviewService reviewService;

    @RequestMapping(path = "/hotels", method = RequestMethod.GET)
    public List<Hotel> getHotels() {
        List<Hotel> hotels = this.hotelService.getHotels();
        return hotels;
    }

    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.GET)
    public Hotel getHotel(@PathVariable Long id) {
        Hotel hotel = this.hotelService.getHotel(id);
        if(hotel != null) {
            List<Review> reviewsByHotel = reviewService.getReviewsByHotel(hotel.getId());
            hotel.setReviews(reviewsByHotel);
        }
        return hotel;
    }

    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.PUT)
    public void updateHotel(@PathVariable Long id, @RequestBody Hotel hotel) {
        this.hotelService.updateHotel(id, hotel);
    }

    @RequestMapping(path = "/hotels", method = RequestMethod.POST)
    public void addHotel(@RequestBody Hotel hotel) {
        this.hotelService.addHotel(hotel);
    }

    @RequestMapping(path = "/hotels/{id}", method = RequestMethod.DELETE)
    public void deleteHotel(@PathVariable Long id) {
        this.hotelService.deleteHotel(id);
    }
}
