package com.example.testtaskping.service;

import com.example.testtaskping.repository.PingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PingService {
    @Autowired
    private PingRepository pingRepository;


}
