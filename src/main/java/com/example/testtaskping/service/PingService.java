package com.example.testtaskping.service;

import com.example.testtaskping.exception.PingResultNotFoundException;
import com.example.testtaskping.model.Ping;
import com.example.testtaskping.model.PingResultDto;
import com.example.testtaskping.model.TestStatus;
import com.example.testtaskping.model.mapper.PingResultMapper;
import com.example.testtaskping.repository.PingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PingService {
    private final PingRepository pingRepository;

    public PingService(PingRepository pingRepository) {
        this.pingRepository = pingRepository;
    }

    public Page<PingResultDto> getAllPingResults(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("checkDate").descending());
        Page<Ping> pings = pingRepository.findAll(pageable);
        return pings.map(ping -> new PingResultDto(ping.getIpAddress(), ping.getDomainName(), ping.getTestDate(), ping.getStatus(), ping.getPingResult()));

    }

    public Page<PingResultDto> search(String ip, String domain, LocalDate startDate, LocalDate endDate, TestStatus status, int page, int size) {
        Specification<Ping> specification = Specification.where(null);

        if (ip != null && !ip.isEmpty()) {
            specification = specification.and(PingResultSpecification.containsIp(ip));
        }

        if (domain != null && !domain.isEmpty()) {
            specification = specification.and(PingResultSpecification.containsDomain(domain));
        }

        if (startDate != null) {
            specification = specification.and(PingResultSpecification.greaterThanOrEqualToStartDate(startDate));
        }

        if (endDate != null) {
            specification = specification.and(PingResultSpecification.lessThanOrEqualToEndDate(endDate));
        }

        if (status != null) {
            specification = specification.and(PingResultSpecification.matchStatus(status));
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("checkDate").descending());
        Page<Ping> pings = pingRepository.findAll(specification, pageable);
        return pings.map(PingResultMapper::toDto);
    }

    public PingResultDto getPingResultById(Long id) throws PingResultNotFoundException {
        Ping pingEntity = pingRepository.findById(id)
                .orElseThrow(() -> new PingResultNotFoundException(id.toString()));

        return PingResultMapper.toDto(pingEntity);
    }
}
