package com.example.bustrips.repository;

import com.example.bustrips.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer> {}
