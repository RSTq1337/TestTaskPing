package com.example.testtaskping.controller;

import com.example.testtaskping.model.Ping;
import com.example.testtaskping.model.PingResultDto;
import com.example.testtaskping.model.TestStatus;
import com.example.testtaskping.service.PingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PingController {
    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PingResultDto>> getAllPingResults(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        List<PingResultDto> pingResults = pingService.getAllPingResults(page, size);
        return ResponseEntity.ok(pingResults);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PingResultDto>> searchPingResults(
            @RequestParam(name = "ip", required = false) String ip,
            @RequestParam(name = "domain", required = false) String domain,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(name = "status", required = false) TestStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PingResultDto> pingResultDtos = pingService.search(ip, domain, startDate, endDate, status, pageable);
        return ResponseEntity.ok(pingResultDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PingResultDto> getPingResult(@PathVariable Long id) {
        PingResultDto pingResultDto = pingService.getPingResultById(id);
        if (pingResultDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pingResultDto);
    }
}
