package com.example.testtaskping.repository;

import com.example.testtaskping.model.Ping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PingRepository extends JpaRepository<Ping, Long> {
}
