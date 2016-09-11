package com.github.pavradev.dockerbay.demo.service;

import java.util.List;

import com.github.pavradev.dockerbay.demo.domain.Hotel;

public interface HotelService {

    Hotel getHotel(Long id);

    List<Hotel> getHotels();

    Hotel addHotel(Hotel hotel);

    Hotel updateHotel(Long id, Hotel hotel);

    Boolean deleteHotel(Long id);
}
