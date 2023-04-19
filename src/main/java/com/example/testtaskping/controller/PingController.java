package com.example.testtaskping.controller;

import com.example.testtaskping.model.Ping;
import com.example.testtaskping.model.PingResultDto;
import com.example.testtaskping.service.PingService;
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

    @GetMapping
    public ResponseEntity<List<PingResultDto>> getAllPingResults(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        List<PingResultDto> pingResults = pingService.getAllPingResults(page, size);
        return ResponseEntity.ok(pingResults);
    }

    @GetMapping("/search")
    public ResponseEntity<List<PingResultDto>> searchPingResults(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        List<PingResultDto> pingResultDtos = pingService.search(query, startDate, endDate, status, page);
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
