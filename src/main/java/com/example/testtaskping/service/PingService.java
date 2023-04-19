package com.example.testtaskping.service;

import com.example.testtaskping.exception.PingResultNotFoundException;
import com.example.testtaskping.model.Ping;
import com.example.testtaskping.model.PingResultDto;
import com.example.testtaskping.model.TestStatus;
import com.example.testtaskping.model.mapper.PingResultMapper;
import com.example.testtaskping.repository.PingRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PingService {
    private final PingRepository pingRepository;

    public PingService(PingRepository pingRepository, PingResultMapper pingResultMapper) {
        this.pingRepository = pingRepository;
    }

    public List<PingResultDto> getAllPingResults(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("checkDate").descending());
        Page<Ping> pingResultsPage = pingRepository.findAll(pageable);
        List<Ping> pingResults = pingResultsPage.getContent();
        return pingResults.stream()
                .map(PingResultMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<PingResultDto> search(String ip, String domain, LocalDate startDate, LocalDate endDate, TestStatus status, Pageable pageable) {
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

        Page<Ping> pings = pingRepository.findAll(specification, pageable);
        return pings.map(PingResultMapper::toDto);
    }

    public PingResultDto getPingResultById(Long id) throws PingResultNotFoundException {
        Ping pingEntity = pingRepository.findById(id)
                .orElseThrow(() -> new PingResultNotFoundException(id.toString()));

        return PingResultMapper.toDto(pingEntity);
    }
}
