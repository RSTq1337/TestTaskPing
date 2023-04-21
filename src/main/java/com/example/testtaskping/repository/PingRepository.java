package com.example.testtaskping.repository;

import com.example.testtaskping.model.Ping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PingRepository extends JpaRepository<Ping, Long> {
    Page<Ping> findAll(Specification<Ping> specification, Pageable pageable);
    Page<Ping> findAll(Pageable pageable);
}
