package com.github.pavradev.dockerbay.demo.service;

import com.github.pavradev.dockerbay.demo.domain.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

interface HotelRepository extends JpaRepository<Hotel, Long> {

}
