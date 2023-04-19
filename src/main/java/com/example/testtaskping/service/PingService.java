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
    private final PingResultMapper pingResultMapper;

    public PingService(PingRepository pingRepository, PingResultMapper pingResultMapper) {
        this.pingRepository = pingRepository;
        this.pingResultMapper = pingResultMapper;
    }

    public List<PingResultDto> getAllPingResults(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateChecked").descending());
        Page<Ping> pingResultsPage = pingRepository.findAll(pageable);
        List<Ping> pingResults = pingResultsPage.getContent();
        return pingResults.stream()
                .map(PingResultMapper::toDto)
                .collect(Collectors.toList());
    }

    public Page<PingResultDto> search(String keyword, String fromDate, String toDate, String status, Pageable pageable) {

        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);

        Specification<Ping> specification = Specification.where(PingResultSpecification.containsKeyword(keyword))
                .and(PingResultSpecification.betweenDates(from, to))
                .and(PingResultSpecification.matchStatus(TestStatus.fromString(status)));

        Page<Ping> results = pingRepository.findAll(specification, pageable);

        List<PingResultDto> dtos = results.stream()
                .map(PingResultMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, results.getTotalElements());
    }

    public PingResultDto getPingResultById(Long id) throws PingResultNotFoundException {
        Ping pingEntity = pingRepository.findById(id)
                .orElseThrow(() -> new PingResultNotFoundException(id.toString()));

        return PingResultMapper.toDto(pingEntity);
    }
}
