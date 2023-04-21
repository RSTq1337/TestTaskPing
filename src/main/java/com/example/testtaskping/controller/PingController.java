package com.example.testtaskping.controller;

import com.example.testtaskping.exception.PingResultNotFoundException;
import com.example.testtaskping.model.PingResultDto;
import com.example.testtaskping.model.SearchForm;
import com.example.testtaskping.service.PingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PingController {
    private final PingService pingService;

    public PingController(PingService pingService) {
        this.pingService = pingService;
    }

    @GetMapping("/all")
    public String getAllPingResults(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "5") int size) {
        Page<PingResultDto> pingResults = pingService.getAllPingResults(page, size);
        model.addAttribute("pingResults", pingResults.getContent());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", pingResults.getTotalPages());
        return "ping-results";
    }

    @GetMapping("/search")
    public String searchPingResults(
            @ModelAttribute("searchForm") SearchForm searchForm,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            HttpServletRequest request) {
        Page<PingResultDto> pingResultDtos = pingService.search(
                searchForm.getIp(),
                searchForm.getDomain(),
                searchForm.getStartDate(),
                searchForm.getEndDate(),
                searchForm.getStatus(),
                page,
                size);
        model.addAttribute("pingResults", pingResultDtos);
        model.addAttribute("param", request.getParameterMap());
        model.addAttribute("searchForm", new SearchForm());
        return "ping-search";
    }

    @GetMapping("/{id}")
    public String getPingResult(@PathVariable Long id, Model model) {
        PingResultDto pingResultDto;
        try {
            pingResultDto = pingService.getPingResultById(id);
        } catch (PingResultNotFoundException exception) {
            return "404";
        }
        model.addAttribute("pingResult", pingResultDto);
        return "ping-search-id";
    }
}
