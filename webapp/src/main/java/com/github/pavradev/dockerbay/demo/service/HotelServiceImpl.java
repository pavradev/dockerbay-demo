package com.github.pavradev.dockerbay.demo.service;

import java.util.List;

import com.github.pavradev.dockerbay.demo.domain.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel getHotel(Long id) {
        return this.hotelRepository.findOne(id);
    }

    @Override
    public List<Hotel> getHotels() {
        return this.hotelRepository.findAll();
    }

    @Override
    public Hotel addHotel(Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }

    @Override
    public Hotel updateHotel(Long id, Hotel hotel) {
        if(this.hotelRepository.exists(id)) {
            Hotel foundHotel = this.hotelRepository.findOne(id);
            if (hotel.getAddress() != null) {
                foundHotel.setAddress(hotel.getAddress());
            }
            if (hotel.getName() != null) {
                foundHotel.setName(hotel.getName());
            }
            if (hotel.getZip() != null) {
                foundHotel.setZip(hotel.getZip());
            }
            return this.hotelRepository.save(foundHotel);
        } else {
            return null;
        }
    }

    @Override
    public Boolean deleteHotel(Long id) {
        Hotel foundHotel = this.hotelRepository.findOne(id);
        if(foundHotel != null) {
            this.hotelRepository.delete(foundHotel);
            return true;
        }
        return false;
    }

}
